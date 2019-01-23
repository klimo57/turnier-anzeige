package com.klimo.misc.dto;

import static com.klimo.misc.util.Functions.PLAYER_DTO_MAPPER;
import static com.klimo.misc.util.Functions.TEAM_DTO_MAPPER;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.klimo.misc.domain.AbstractParticipant;
import com.klimo.misc.domain.Format;
import com.klimo.misc.domain.Tournament;

public class TournamentDTO {

	public Long id;
	public LocalDate fixture;
	public Format format;
	public Integer teamSize;
	public boolean locked;
	public boolean finished;
	public Set<ParticipantDTO> participants;
	public List<MatchDTO> matches;

	public TournamentDTO(Tournament<? extends AbstractParticipant> t) {
		id = t.getId();
		fixture = t.getFixture();
		format = t.getFormat();
		teamSize = t.getTeamSize();
		locked = t.isLocked();
		finished = t.isFinished();
		participants = t.getParticipants()
						.parallelStream()
						.map(teamSize.intValue() == 1 ? PLAYER_DTO_MAPPER : TEAM_DTO_MAPPER)
						.collect(Collectors.toSet());
		matches = t.getMatches()
					.parallelStream()
					.map(m -> new MatchDTO(m))
					.collect(Collectors.toList());
	}

}
