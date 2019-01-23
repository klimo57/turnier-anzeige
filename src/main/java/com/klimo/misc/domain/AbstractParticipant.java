package com.klimo.misc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractParticipant {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tournament_id")
	private Tournament<? extends AbstractParticipant> tournament;

	private String name;

	@Column(nullable = false)
	private Integer seeding;

	private Integer placing = 0;

	@Override
	public int hashCode() {
		return 31;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(!(obj instanceof AbstractParticipant))
			return false;
		return id != null && id.equals(((AbstractParticipant) obj).getId());
	}

	public Long getId() {
		return id;
	}

	public AbstractParticipant setId(Long id) {
		this.id = id;
		return this;
	}

	public Tournament<? extends AbstractParticipant> getTournament() {
		return tournament;
	}

	public AbstractParticipant setTournament(Tournament<? extends AbstractParticipant> tournament) {
		this.tournament = tournament;
		return this;
	}

	public String getName() {
		return name;
	}

	public AbstractParticipant setName(String name) {
		this.name = name;
		return this;
	}

	public Integer getSeeding() {
		return seeding;
	}

	public AbstractParticipant setSeeding(Integer seeding) {
		this.seeding = seeding;
		return this;
	}

	public Integer getPlacing() {
		return placing;
	}

	public AbstractParticipant setPlacing(Integer placing) {
		this.placing = placing;
		return this;
	}

}
