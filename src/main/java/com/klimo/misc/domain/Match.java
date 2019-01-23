package com.klimo.misc.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Match<T extends AbstractParticipant> {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = AbstractParticipant.class)
	@JoinColumn(name = "tournament_id")
	private Tournament<T> tournament;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = AbstractParticipant.class)
	@JoinColumn(name = "participant_id", nullable = false)
	private T participant;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = AbstractParticipant.class)
	@JoinColumn(name = "opponent_id", nullable = false)
	private T opponent;

	/**
	 * the result of this match (win or loss) from the perspective of the {@link Match#participant}
	 */
	@Enumerated(EnumType.STRING)
	private Result result = Result.NOT_FINISHED;

	public Long getId() {
		return id;
	}

	public Match<T> setId(Long id) {
		this.id = id;
		return this;
	}

	public Tournament<T> getTournament() {
		return tournament;
	}

	public Match<T> setTournament(Tournament<T> tournament) {
		this.tournament = tournament;
		return this;
	}

	public AbstractParticipant getParticipant() {
		return participant;
	}

	public Match<T> setParticipant(T participant) {
		this.participant = participant;
		return this;
	}

	public AbstractParticipant getOpponent() {
		return opponent;
	}

	public Match<T> setOpponent(T opponent) {
		this.opponent = opponent;
		return this;
	}

	public Result getResult() {
		return result;
	}

	public Match<T> setResult(Result result) {
		this.result = result;
		return this;
	}

}
