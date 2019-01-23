package com.klimo.misc.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Tournament<T extends AbstractParticipant> {

	@Id
	@GeneratedValue
	private Long id;

	private LocalDate fixture = LocalDate.now();

	@Enumerated(EnumType.STRING)
	private Format format = Format.DOUBLE_ELIMINATION_BRACKET;

	private Integer teamSize = 1;

	private boolean locked = false;
	private boolean finished = false;

	@OneToMany(
			mappedBy = "tournament",
			cascade = CascadeType.ALL,
			orphanRemoval = true,
			targetEntity = AbstractParticipant.class
	)
	private Set<T> participants = new HashSet<>();

	@OneToMany(
			mappedBy = "tournament",
			cascade = CascadeType.ALL,
			orphanRemoval = true,
			targetEntity = Match.class
	)
	private List<Match<T>> matches = new ArrayList<>();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		Tournament<?> other = (Tournament<?>)obj;
		if(id == null) {
			if(other.id != null)
				return false;
		} else if(!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return new StringBuilder().append("Tournament [ id=").append(id).append(", fixture=").append(fixture).append(", format=").append(format).append(", teamSize=")
				.append(teamSize).append(", locked=").append(locked).append(", finished=").append(finished).append(" ]").toString();
	}

	public Long getId() {
		return id;
	}

	public Tournament<T> setId(long id) {
		this.id = id;
		return this;
	}

	public LocalDate getFixture() {
		return fixture;
	}

	public Tournament<T> setFixture(LocalDate fixture) {
		this.fixture = fixture;
		return this;
	}

	public Format getFormat() {
		return format;
	}

	public Tournament<T> setFormat(Format format) {
		this.format = format;
		return this;
	}

	public Integer getTeamSize() {
		return teamSize;
	}

	public Tournament<T> setTeamSize(Integer teamSize) {
		this.teamSize = teamSize;
		return this;
	}

	public boolean isLocked() {
		return locked;
	}

	public Tournament<T> setLocked(boolean locked) {
		this.locked = locked;
		return this;
	}

	public boolean isFinished() {
		return finished;
	}

	public Tournament<T> setFinished(boolean finished) {
		this.finished = finished;
		return this;
	}

	public Set<T> getParticipants() {
		return participants;
	}

	public Tournament<T> setParticipants(Set<T> participants) {
		this.participants = participants;
		return this;
	}

	public boolean addParticipant(T participant) {
		participant.setTournament(this);
		return getParticipants().add(participant);
	}

	public boolean removeParticipant(T participant) {
		participant.setTournament(null);
		return getParticipants().remove(participant);
	}

	public List<Match<T>> getMatches() {
		return matches;
	}

	public boolean addMatch(Match<T> match) {
		match.setTournament(this);
		return getMatches().add(match);
	}

	public boolean removeMatch(Match<T> match) {
		match.setTournament(null);
		return getMatches().remove(match);
	}

}
