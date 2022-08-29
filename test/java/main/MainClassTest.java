package test.java.main;

import static org.junit.Assert.*;

import org.junit.Test;

import main.Main;

public class MainClassTest {

	@Test
	public void testGlobalGetters() {
		assertNotNull(Main.getGameBoard());
		assertNull(Main.getGameMapPanel());
		assertNull(Main.getGameToolbar());
	}

}
