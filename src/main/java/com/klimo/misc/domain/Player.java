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
public class Player extends AbstractParticipant {

	@Id
	@GeneratedValue
	private Long id;

	@Enumerated(EnumType.STRING)
	private Character character;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team_id")
	private Team team;

	@Override
	public String toString() {
		return new StringBuilder().append("Player [ id=").append(id).append(" , name=").append(getName()).append(" , character=").append(character.name()).append(" , placing=")
				.append(getPlacing()).append(" ]").toString();
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public Player setId(Long id) {
		this.id = id;
		return this;
	}

	public Character getCharacter() {
		return character;
	}

	public Player setCharacter(Character character) {
		this.character = character;
		return this;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

}
