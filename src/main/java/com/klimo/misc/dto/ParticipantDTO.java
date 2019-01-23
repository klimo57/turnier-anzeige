package com.klimo.misc.dto;

import java.util.ArrayList;
import java.util.List;

import com.klimo.misc.domain.Character;
import com.klimo.misc.domain.Player;
import com.klimo.misc.domain.Result;
import com.klimo.misc.domain.Team;
import com.klimo.misc.domain.ToBeDeterminedPlayer;

public class ParticipantDTO {

	// common fields

	public Long id;
	public Long tournament;
	public String name;
	public Integer seeding;
	public Integer placing;
	public Integer teamSize;

	// player fields

	public Character character;
	public Long team;

	// team fields

	public List<ParticipantDTO> players;

	// tbd fields

	public MatchDTO decider;
	public Result decidingResult;

	public ParticipantDTO(Player player) {
		this(player.getId(), player.getTournament().getId(), player.getPlacing(), player.getSeeding(), 1);

		character = player.getCharacter();
		name = player.getName();
	}

	public ParticipantDTO(Team team) {
		this(team.getId(), team.getTournament().getId(), team.getPlacing(), team.getSeeding(), team.getPlayers().size());

		players = new ArrayList<>();
		team.getPlayers()
		.parallelStream()
				.forEach(p -> players.add(new ParticipantDTO(p)));
	}

	public ParticipantDTO(ToBeDeterminedPlayer tbdPlayer) {
		this(tbdPlayer.getId(), tbdPlayer.getTournament().getId(), tbdPlayer.getPlacing(), tbdPlayer.getSeeding(), tbdPlayer.getTournament().getTeamSize());

		decider = new MatchDTO(tbdPlayer.getDecider());
		decidingResult = tbdPlayer.getDecidingResult();
	}

	private ParticipantDTO(Long id, Long tournament, Integer placing, Integer seeding, Integer teamSize) {
		this.id = id;
		this.tournament = tournament;
		this.placing = placing;
		this.seeding = seeding;
		this.teamSize = teamSize;
	}

}
