package com.klimo.misc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klimo.misc.domain.AbstractParticipant;
import com.klimo.misc.domain.Player;
import com.klimo.misc.domain.Team;
import com.klimo.misc.domain.Tournament;
import com.klimo.misc.repository.TeamRepository;
import com.klimo.misc.repository.TournamentRepository;

@Service
public class ParticipantService {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(ParticipantService.class);

	@Autowired
	private TeamRepository teamRepo;

	@Autowired
	private TournamentRepository tournamentRepo;

	public <T extends AbstractParticipant> void addParticipantToTournament(Tournament<T> tournament, T participant) {
		if(!(participant instanceof Player) && !(participant instanceof Team))
			throw new IllegalArgumentException("Unknown class for participant: " + participant.getClass().getName());

		if(participant instanceof Player && tournament.getTeamSize().intValue() != 1)
			throw new IllegalArgumentException("This tournament is not for single players!");
		else if(participant instanceof Team && tournament.getTeamSize().intValue() != ((Team)participant).getPlayers().size())
			throw new IllegalArgumentException("This player conflicts with another player already participating!");

		if(!tournament.addParticipant(participant))
			throw new IllegalArgumentException("This player conflicts with another player already participating!");

		tournamentRepo.saveAndFlush(tournament);
	}

	public <T extends AbstractParticipant> void removeParticipantFromTournament(Tournament<T> tournament, T participant) {
		tournament.removeParticipant(participant);
		// TODO check if there are any matches
		tournamentRepo.saveAndFlush(tournament);
	}

	public void addPlayerToTeam(Team team, Player player) {
		if(!team.addPlayer(player))
			throw new IllegalArgumentException("This player conflicts with another player already in this team!");
		teamRepo.saveAndFlush(team);
	}

	public void removePlayerFromTeam(Team team, Player player) {
		team.removePlayer(player);
		teamRepo.saveAndFlush(team);
	}

}
