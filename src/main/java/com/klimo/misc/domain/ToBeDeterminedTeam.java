package com.klimo.misc.domain;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.util.StringUtils;

public class ToBeDeterminedTeam extends Team {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "participant_id", nullable = false)
	private Match<Team> decider;

	/**
	 * indicates if the winner or looser has to be determined
	 */
	private Result decidingResult;

	@Override
	public String getName() {
		return new StringBuilder(
				StringUtils.capitalize(decidingResult.name()))
				.append(" of Match ")
				.append(decider.getId())
				.toString();
	}

	public Match<Team> getDecider() {
		return decider;
	}

	public ToBeDeterminedTeam setDecider(Match<Team> decider) {
		this.decider = decider;
		return this;
	}

	public Result getDecidingResult() {
		return decidingResult;
	}

	public ToBeDeterminedTeam setDecidingResult(Result decidingResult) {
		this.decidingResult = decidingResult;
		return this;
	}
	
}
