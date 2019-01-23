package com.klimo.misc.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Team extends AbstractParticipant {

	@OneToMany(
			mappedBy = "team",
			cascade = CascadeType.ALL,
			orphanRemoval = true
	)
	private List<Player> players = new ArrayList<>();

	@Override
	public String getName() {
		return getPlayers()
				.parallelStream()
				.map(p -> p.getName())
				.collect(Collectors.joining(" | "));
	}
	
	public List<Player> getPlayers() {
		return players;
	}

	public boolean addPlayer(Player player) {
		player.setTeam(this);
		return getPlayers().add(player);
	}

	public boolean removePlayer(Player player) {
		player.setTeam(null);
		return getPlayers().remove(player);
	}

}
