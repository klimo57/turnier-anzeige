package com.klimo.misc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.klimo.misc.domain.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {

}
