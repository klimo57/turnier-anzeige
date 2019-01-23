package com.klimo.misc.service;

import java.time.LocalDate;
import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klimo.misc.domain.AbstractParticipant;
import com.klimo.misc.domain.Character;
import com.klimo.misc.domain.Format;
import com.klimo.misc.domain.Match;
import com.klimo.misc.domain.Player;
import com.klimo.misc.domain.Result;
import com.klimo.misc.domain.Team;
import com.klimo.misc.domain.ToBeDeterminedPlayer;
import com.klimo.misc.domain.Tournament;
import com.klimo.misc.repository.MatchRepository;
import com.klimo.misc.repository.PlayerRepository;
import com.klimo.misc.repository.TeamRepository;
import com.klimo.misc.repository.TournamentRepository;

@Service
public class ApplicationService {

	@Autowired
	private TournamentRepository tournamentRepo;

	@Autowired
	private PlayerRepository playerRepo;

	@Autowired
	private TeamRepository teamRepo;

	@Autowired
	private MatchRepository matchRepo;

	@PostConstruct
	public void initDB() {
		Player player1 = playerRepo.save(createPlayer(Character.DONKEY_KONG, "Player One", 4));
		Player player2 = playerRepo.save(createPlayer(Character.SAMUS, "Player Two", 2));
		Player player3 = playerRepo.save(createPlayer(Character.MARIO, "Player Three", 1));
		Player player4 = playerRepo.save(createPlayer(Character.KIRBY, "Player Four", 3));
		playerRepo.flush();

		Tournament<Player> tournament1 = tournamentRepo.saveAndFlush(createTournament(LocalDate.now(), 1, player1, player2, player3, player4));

		Match<Player> match1 = matchRepo.save(new Match<Player>()
								.setId(0L)
								.setParticipant(player3)
				.setOpponent(player1));
		Match<Player> match2 = matchRepo.save(new Match<Player>()
								.setId(1L)
								.setParticipant(player2)
				.setOpponent(player4));
		Match<Player> match3 = matchRepo.save(new Match<Player>()
								.setId(2L)
								.setParticipant(
										new ToBeDeterminedPlayer()
										.setDecider(match1)
										.setDecidingResult(Result.WIN)
								).setOpponent(
										new ToBeDeterminedPlayer()
										.setDecider(match2)
										.setDecidingResult(Result.WIN)
				));
		matchRepo.flush();

		tournament1.addMatch(match1);
		tournament1.addMatch(match2);
		tournament1.addMatch(match3);

		tournamentRepo.saveAndFlush(tournament1);

		Team team1 = teamRepo.save(createTeam(1, player1, player3));
		Team team2 = teamRepo.save(createTeam(2, player2, player4));
		teamRepo.flush();

		Tournament<Team> tournament2 = tournamentRepo.saveAndFlush(createTournament(LocalDate.now(), 2, team1, team2));
		
		Match<Team> match4 = matchRepo.saveAndFlush(new Match<Team>()
								.setId(3L)
								.setParticipant(team1)
								.setOpponent(team2));
		
		tournament2.addMatch(match4);

		tournamentRepo.saveAndFlush(tournament2);
		tournamentRepo.flush();
	}

	@SafeVarargs
	private final <T extends AbstractParticipant> Tournament<T> createTournament(LocalDate fixture, int teamSize, T... participants) {
		return createTournament(Format.DOUBLE_ELIMINATION_BRACKET, fixture, teamSize, participants);
	}

	@SafeVarargs
	private final <T extends AbstractParticipant> Tournament<T> createTournament(Format format, LocalDate fixture, int teamSize, T... participants) {
		Tournament<T> tournament = new Tournament<>();
		tournament.setFormat(format).setFixture(fixture).setTeamSize(teamSize);
		Arrays.stream(participants)
			.parallel()
			.forEach(tournament::addParticipant);
		return tournament;
	}

	private Player createPlayer(Character character, String name, int seed) {
		Player player = new Player();
		player.setCharacter(character).setName(name).setSeeding(seed);
		return player;
	}

	private Team createTeam(int seed, Player... players) {
		Team team = new Team();
		team.setSeeding(seed);
		Arrays.stream(players).parallel().forEach(team::addPlayer);
		return team;
	}

}
