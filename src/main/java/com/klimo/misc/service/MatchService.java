package com.klimo.misc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klimo.misc.domain.AbstractParticipant;
import com.klimo.misc.domain.Match;
import com.klimo.misc.domain.Result;
import com.klimo.misc.repository.MatchRepository;

@Service
public class MatchService {

	@Autowired
	private MatchRepository repo;

	public <T extends AbstractParticipant> void setResult(Match<T> match, Long winnerId) {
		if(winnerId.longValue() == match.getParticipant().getId().longValue())
			match.setResult(Result.WIN);
		else if(winnerId.longValue() == match.getOpponent().getId().longValue())
			match.setResult(Result.LOSS);
		else
			throw new IllegalArgumentException("No participant in this match with the id " + winnerId.longValue());

		repo.saveAndFlush(match);
	}

}
