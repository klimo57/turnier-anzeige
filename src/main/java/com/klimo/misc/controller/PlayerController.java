package com.klimo.misc.controller;

import static com.klimo.misc.util.Functions.PLAYER_DTO_MAPPER;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.klimo.misc.domain.Player;
import com.klimo.misc.domain.Tournament;
import com.klimo.misc.dto.ParticipantDTO;
import com.klimo.misc.repository.PlayerRepository;
import com.klimo.misc.repository.TournamentRepository;
import com.klimo.misc.service.ParticipantService;

@RestController
@RequestMapping("player")
public class PlayerController {

	@Autowired
	private PlayerRepository repo;

	@Autowired
	private ParticipantService service;

	@Autowired
	private TournamentRepository tournamentRepo;

	@GetMapping
	public List<ParticipantDTO> getAll() {
		return repo.findAll()
					.parallelStream()
					.map(PLAYER_DTO_MAPPER)
					.collect(Collectors.toList());
	}

	@GetMapping("/{id}")
	public ParticipantDTO get(@PathVariable("id") Long playerId) {
		return new ParticipantDTO(repo.findById(playerId).get());
	}

	@PostMapping
	public void create(@Valid Player player) {
		repo.saveAndFlush(player);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Long playerId) {
		repo.deleteById(playerId);
	}

	@PatchMapping("/{playerId}/participate/{tournamentId}")
	public void addToTournament(@PathVariable("playerId") Long playerId, @PathVariable("tournamentId") Long tournamentId) {
		@SuppressWarnings("unchecked")
		Tournament<Player> tournament = (Tournament<Player>)tournamentRepo.findById(tournamentId).get();
		service.addParticipantToTournament(tournament, repo.findById(playerId).get());
	}

	@PatchMapping("/{playerId}/withdraw/{tournamentId}")
	public void removeFromTournament(@PathVariable("playerId") Long playerId, @PathVariable("tournamentId") Long tournamentId) {
		@SuppressWarnings("unchecked")
		Tournament<Player> tournament = (Tournament<Player>)tournamentRepo.findById(tournamentId).get();
		service.removeParticipantFromTournament(tournament, repo.findById(playerId).get());
	}

}
