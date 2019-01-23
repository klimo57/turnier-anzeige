package com.klimo.misc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.klimo.misc.domain.AbstractParticipant;
import com.klimo.misc.domain.Match;

public interface MatchRepository extends JpaRepository<Match<? extends AbstractParticipant>, Long> {

}
