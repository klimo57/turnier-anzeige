package com.klimo.misc.dto;

import com.klimo.misc.domain.AbstractParticipant;
import com.klimo.misc.domain.Match;
import com.klimo.misc.domain.Player;
import com.klimo.misc.domain.Result;
import com.klimo.misc.domain.Team;

public class MatchDTO {

	public Long id;
	public ParticipantDTO participant;
	public ParticipantDTO opponent;
	public Result result;

	public MatchDTO(Match<? extends AbstractParticipant> m) {
		id = m.getId();
		AbstractParticipant p = m.getParticipant();
		AbstractParticipant o = m.getOpponent();
		if(p instanceof Player) {
			participant = new ParticipantDTO((Player)p);
			opponent = new ParticipantDTO((Player)o);
		} else if(p instanceof Team) {
			participant = new ParticipantDTO((Team)p);
			opponent = new ParticipantDTO((Team)o);
		}
		result = m.getResult();
	}

}
