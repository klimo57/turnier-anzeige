package com.klimo.misc.service;

import static com.klimo.misc.util.Functions.PLACING_COMPARATOR;
import static com.klimo.misc.util.Functions.SEEDING_COMPARATOR;

import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klimo.misc.domain.AbstractParticipant;
import com.klimo.misc.domain.Tournament;
import com.klimo.misc.exception.TournamentException;
import com.klimo.misc.repository.TournamentRepository;

@Service
public class TournamentService {

	@Autowired
	private TournamentRepository repo;

	public <T extends AbstractParticipant> void start(Tournament<T> tournament) {
		// TODO lock tournament/teams/etc for changes
		// TODO create matches
		// TODO create matchplan (order of playing, so that everyone plays in turns)
	}

	public <T extends AbstractParticipant> void lock(Tournament<T> tournament) {
		// TODO maybe check players/teams?
		Set<T> seededParticipants = new TreeSet<>(SEEDING_COMPARATOR);
		seededParticipants.addAll(tournament.getParticipants());
		tournament.setParticipants(seededParticipants).setLocked(true);
	}

	public <T extends AbstractParticipant> T finish(Tournament<T> tournament) {
		if(!tournament.isLocked())
			throw new IllegalStateException("Cannot finish an unlocked tournament!");

		Set<T> rankedParticipants = new TreeSet<>(PLACING_COMPARATOR);
		rankedParticipants.addAll(tournament.getParticipants());
		tournament.setParticipants(rankedParticipants).setFinished(true);

		Optional<T> winner = tournament.getParticipants()
				.stream()
				.filter(t -> t.getPlacing().intValue() == 1)
				.findAny();

		return winner.orElseGet(() -> {
			throw new TournamentException("Winner could not be determined! # of participants: " + tournament.getParticipants().size());
		});
	}

}
