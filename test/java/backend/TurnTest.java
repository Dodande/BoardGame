package test.java.backend;

import static org.junit.Assert.*;

import org.junit.Test;

import backend.Turn;
import field.Player;

public class TurnTest {

	@Test
	public void test() {
		Turn turn = new Turn();
		assertEquals(Player.First,turn.getWhoseTurn());
		turn.nextTurn();
		assertEquals(Player.Second,turn.getWhoseTurn());
		
	}

}
