package com.klimo.misc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.klimo.misc.domain.AbstractParticipant;
import com.klimo.misc.domain.Tournament;

public interface TournamentRepository extends JpaRepository<Tournament<? extends AbstractParticipant>, Long> {

}
