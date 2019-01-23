package com.klimo.misc.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.klimo.misc.dto.MatchDTO;
import com.klimo.misc.repository.MatchRepository;
import com.klimo.misc.service.MatchService;

@RestController
@RequestMapping("/match")
public class MatchController {

	@Autowired
	private MatchRepository repo;

	@Autowired
	private MatchService service;

	@GetMapping
	public List<MatchDTO> getAll() {
		return repo.findAll()
					.parallelStream()
					.map(m -> new MatchDTO(m))
					.collect(Collectors.toList());
	}

	@GetMapping("/{id}")
	public MatchDTO get(@PathVariable("id") Long matchId) {
		return new MatchDTO(repo.findById(matchId).get());
	}

	@PatchMapping("/{matchId}/{participantId}")
	public void setWinner(@PathVariable("matchId") Long matchId, @PathVariable("participantId") Long participantId) {
		service.setResult(repo.findById(matchId).get(), participantId);
	}

}
