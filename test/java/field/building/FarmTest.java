package test.java.field.building;

import static org.junit.Assert.*;

import org.junit.Test;

import field.Player;
import field.building.Barrack;
import field.building.Farm;

public class FarmTest {

	Farm temp = new Farm(1,2,100,Player.First);

	@Test
	public void testCreation() {
		assertEquals(1,temp.getCoordinates().getXCoordinate());
		assertEquals(2,temp.getCoordinates().getYCoordinate());
		assertEquals(100,temp.getHealth());
		assertEquals(Player.First,temp.getPlayer());
		assertEquals(100,temp.currentResource());
	}
	
	@Test
	public void testResourceChange() {
		temp.setResource(50);
		assertEquals(50,temp.currentResource());
	}
	
	@Test
	public void testResourceReduction() {
		temp.reduceResource(20);
		assertEquals(80,temp.currentResource());
	}
	
	@Test
	public void testResourceIfAlreadyZero() {
		temp.reduceResource(120);
		assertEquals(0,temp.currentResource());
	}
	
	@Test
	public void testPositionsChange() {
		temp.setPosition(5, 6);
		assertEquals(5,temp.getCoordinates().getXCoordinate());
		assertEquals(6,temp.getCoordinates().getYCoordinate());
	}

	@Test
	public void testHealthChange() {
		temp.setHealth(50);
		assertEquals(50,temp.getHealth());
	}
	
	@Test
	public void testHealing() {
		temp.setHealth(50);
		temp.restoreHealth(20);
		assertEquals(70,temp.getHealth());
	}
	
	@Test
	public void testHealingIfAlreadyMax() {
		temp.restoreHealth(20);
		assertEquals(100,temp.getHealth());
	}
	
	@Test
	public void testHealthLoss() {
		temp.loseHealth(20);
		assertEquals(80,temp.getHealth());
	}
	
	@Test
	public void testHealthLossIfAlreadyZero() {
		temp.loseHealth(110);
		assertEquals(0,temp.getHealth());
	}

}
