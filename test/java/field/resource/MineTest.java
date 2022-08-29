package test.java.field.resource;

import static org.junit.Assert.*;

import org.junit.Test;

import field.Player;
import field.resource.Mine;

public class MineTest {
	
	Mine temp = new Mine(1,2,100,Player.First);
	
	@Test
	public void testCreation() {
		assertEquals(1,temp.getCoordinates().getXCoordinate());
		assertEquals(2,temp.getCoordinates().getYCoordinate());
		assertEquals(100,temp.getHealth());
		assertEquals(Player.First,temp.getPlayer());
		assertEquals(120,temp.currentResource());
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
	public void testSetResource() {
		temp.setResource(80);
		assertEquals(80,temp.currentResource());
	}
	
	@Test
	public void testResourceReduction() {
		temp.reduceResource(20);
		assertEquals(100,temp.currentResource());
	}
	
	public void testResourceReductionIfAlreadyZero() {
		temp.reduceResource(200);
		assertEquals(0,temp.currentResource());
	}

}
