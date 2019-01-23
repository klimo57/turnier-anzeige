package com.klimo.misc.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.klimo.misc.domain.Character;
import com.klimo.misc.domain.Player;
import com.klimo.misc.domain.Tournament;
import com.klimo.misc.exception.TournamentException;

public class TournamentServiceTests {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(TournamentServiceTests.class);

	private TournamentService tournamentService = new TournamentService();

	@Test
	public void testLock() {
		// setup
		Tournament<Player> t = new Tournament<>();

		Player number1 = new Player().setCharacter(Character.BAYONETTA);
		Player number2 = new Player().setCharacter(Character.BOWSER);
		Player number3 = new Player().setCharacter(Character.BOWSER_JR);
		number1.setSeeding(1);
		number2.setSeeding(2);
		number3.setSeeding(3);

		t.addParticipant(number3);
		t.addParticipant(number1);
		t.addParticipant(number2);

		// run method
		tournamentService.lock(t);

		// check result
		List<Player> actuals = new ArrayList<>(t.getParticipants());
		Assertions.assertTrue(actuals.size() == 3);
		Assertions.assertEquals(number1, actuals.get(0));
		Assertions.assertEquals(number2, actuals.get(1));
		Assertions.assertEquals(number3, actuals.get(2));
	}

	@Test
	public void testFinish() {
		// setup
		Tournament<Player> t = new Tournament<>();

		Player number1 = new Player().setCharacter(Character.BAYONETTA);
		Player number2 = new Player().setCharacter(Character.BOWSER);
		Player number3 = new Player().setCharacter(Character.BOWSER_JR);
		number1.setSeeding(2).setPlacing(1);
		number2.setSeeding(1).setPlacing(2);
		number3.setSeeding(3).setPlacing(3);

		t.addParticipant(number3);
		t.addParticipant(number1);
		t.addParticipant(number2);

		// cant finish a tournament which is not yet locked (=started)
		Assertions.assertThrows(IllegalStateException.class, () -> tournamentService.finish(t));

		// lock tournament
		tournamentService.lock(t);

		// run method
		Player winner = tournamentService.finish(t);

		// check result
		List<Player> actuals = new ArrayList<>(t.getParticipants());
		Assertions.assertTrue(actuals.size() == 3);
		Assertions.assertEquals(number1, actuals.get(0));
		Assertions.assertEquals(number2, actuals.get(1));
		Assertions.assertEquals(number3, actuals.get(2));
		Assertions.assertEquals(number1, winner, "expected winner was not returned as winner!");

		// at least we need to check that a tournament without participants is handled correctly
		Tournament<Player> tEmpty = new Tournament<>();
		tournamentService.lock(tEmpty);
		Assertions.assertThrows(TournamentException.class, () -> tournamentService.finish(tEmpty));
	}

}
