package com.klimo.misc.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.klimo.misc.domain.AbstractParticipant;
import com.klimo.misc.domain.Tournament;
import com.klimo.misc.dto.TournamentDTO;
import com.klimo.misc.repository.TournamentRepository;

@RestController
@RequestMapping("/tournament")
public class TournamentController {

	@Autowired
	private TournamentRepository repo;

	@GetMapping
	public List<TournamentDTO> getAll() {
		return repo.findAll()
				.parallelStream()
				.map(t -> new TournamentDTO(t))
				.collect(Collectors.toList());
	}

	@GetMapping("/{id}")
	public TournamentDTO get(@PathVariable("id") Long tournamentId) {
		return new TournamentDTO(repo.findById(tournamentId).get());
	}

	@PostMapping
	public void create(@Valid Tournament<? extends AbstractParticipant> tournament) {
		repo.saveAndFlush(tournament);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Long tournamentId) {
		repo.deleteById(tournamentId);
	}

}
