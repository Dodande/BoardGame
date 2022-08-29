package test.java.backend;

import static org.junit.Assert.*;

import org.junit.Test;

import backend.Funds;
import backend.Gamer;

public class GamerTest {

	@Test
	public void testInit() {
		Gamer g = new Gamer();
		assertNotNull(g.getGamerFunds());
		assertEquals(100, g.getGamerFunds().getGoldAmount());
		assertEquals(100, g.getGamerFunds().getMeatAmount());
		assertEquals(100, g.getGamerFunds().getWoodAmount());
	}

	@Test
	public void testSettingFunds() {
		Gamer g = new Gamer();
		g.setGamerFunds(new Funds(-10, -20, -30));
		assertEquals(90, g.getGamerFunds().getWoodAmount());
		assertEquals(80, g.getGamerFunds().getMeatAmount());
		assertEquals(70, g.getGamerFunds().getGoldAmount());
	}
	
	@Test
	public void testHasFunds() {
		Gamer g = new Gamer();
		assertTrue(g.hasEnoughFunds(new Funds(-10, -10, -10)));
		assertFalse(g.hasEnoughFunds(new Funds(-200, 0, 0)));
		assertFalse(g.hasEnoughFunds(new Funds(0, -200, 0)));
		assertFalse(g.hasEnoughFunds(new Funds(0, 0, -200)));
	}
	
}
