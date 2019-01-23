package com.klimo.misc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.klimo.misc.domain.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {

}
