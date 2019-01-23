package com.klimo.misc.controller;

import static com.klimo.misc.util.Functions.TEAM_DTO_MAPPER;

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

import com.klimo.misc.domain.Team;
import com.klimo.misc.domain.Tournament;
import com.klimo.misc.dto.ParticipantDTO;
import com.klimo.misc.repository.PlayerRepository;
import com.klimo.misc.repository.TeamRepository;
import com.klimo.misc.repository.TournamentRepository;
import com.klimo.misc.service.ParticipantService;

@RestController
@RequestMapping("team")
public class TeamController {

	@Autowired
	private TeamRepository repo;

	@Autowired
	private ParticipantService service;

	@Autowired
	private PlayerRepository playerRepo;

	@Autowired
	private TournamentRepository tournamentRepo;

	@GetMapping
	public List<ParticipantDTO> getAll() {
		return repo.findAll()
					.parallelStream()
					.map(TEAM_DTO_MAPPER)
					.collect(Collectors.toList());
	}

	@GetMapping("/{id}")
	public ParticipantDTO get(@PathVariable("id") Long teamId) {
		return new ParticipantDTO(repo.findById(teamId).get());
	}

	@PostMapping
	public void create(@Valid Team team) {
		repo.saveAndFlush(team);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Long teamId) {
		repo.deleteById(teamId);
	}

	@PatchMapping("{teamId}/add/{playerId}")
	public void addToTeam(@PathVariable("teamId") Long teamId, @PathVariable("playerId") Long playerId) {
		service.addPlayerToTeam(repo.findById(teamId).get(), playerRepo.findById(playerId).get());
	}

	@PatchMapping("{teamId}/remove/{playerId}")
	public void removeFromTeam(@PathVariable("teamId") Long teamId, @PathVariable("playerId") Long playerId) {
		service.removePlayerFromTeam(repo.findById(teamId).get(), playerRepo.findById(playerId).get());
	}

	@PatchMapping("/{teamId}/participate/{tournamentId}")
	public void addToTournament(@PathVariable("teamId") Long teamId, @PathVariable("tournamentId") Long tournamentId) {
		@SuppressWarnings("unchecked")
		Tournament<Team> tournament = (Tournament<Team>)tournamentRepo.findById(tournamentId).get();
		service.addParticipantToTournament(tournament, repo.findById(teamId).get());
	}

	@PatchMapping("/{teamId}/withdraw/{tournamentId}")
	public void removeFromTournament(@PathVariable("teamId") Long teamId, @PathVariable("tournamentId") Long tournamentId) {
		@SuppressWarnings("unchecked")
		Tournament<Team> tournament = (Tournament<Team>)tournamentRepo.findById(tournamentId).get();
		service.removeParticipantFromTournament(tournament, repo.findById(teamId).get());
	}

}
