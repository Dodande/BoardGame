package test.java.field.unit;

import static org.junit.Assert.*;

import org.junit.Test;

import field.Player;
import field.building.Farm;
import field.unit.Farmer;

public class FarmerTest {

	
	Farmer temp = new Farmer(1,2,50,Player.First);

	@Test
	public void testCreation() {
		assertEquals(1,temp.getCoordinates().getXCoordinate());
		assertEquals(2,temp.getCoordinates().getYCoordinate());
		assertEquals(50,temp.getHealth());
		assertEquals(Player.First,temp.getPlayer());
	}
	
	@Test
	public void testHealthChange() {
		temp.setHealth(50);
		assertEquals(50,temp.getHealth());
	}
	
	@Test
	public void testPositionsChange() {
		temp.setPosition(5, 6);
		assertEquals(5,temp.getCoordinates().getXCoordinate());
		assertEquals(6,temp.getCoordinates().getYCoordinate());
	}
	
	@Test
	public void testHealthLoss() {
		temp.setHealth(60);
		temp.loseHealth(20);
		assertEquals(40,temp.getHealth());
		
	}
	
	@Test
	public void testHealthLossUnderZero() {
		temp.loseHealth(300);
		assertEquals(0,temp.getHealth());
	}
	
	@Test
	public void testIfDies() {
		temp.loseHealth(300);
		assertEquals(false,temp.isAlive());
	}
	
	@Test
	public void testFarming() {
		Farm f = new Farm(6,7,50,Player.First);
		f.setResource(80);
		temp.farm(f);
	    assertEquals(68,f.currentResource());
	}

}
