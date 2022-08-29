package test.java.field.unit;

import static org.junit.Assert.*;

import org.junit.Test;

import backend.BuildingType;
import field.Player;
import field.building.Barrack;
import field.building.Farm;
import field.unit.Builder;

public class BuilderTest {
	
	Builder temp = new Builder(1,2,50,Player.First);

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
	public void testBuilding() {
		Farm m = (Farm) temp.buildBuilding(2, 3, 50, Player.First, BuildingType.Farm);
		assertEquals(2,m.getCoordinates().getXCoordinate());
		assertEquals(3,m.getCoordinates().getYCoordinate());
		assertEquals(50,m.getHealth());
		assertEquals(Player.First,m.getPlayer());		
	}
	
	@Test
	public void testHealing() {
		Barrack b = new Barrack(4,5,100,Player.First);
		b.setHealth(80);
		b.loseHealth(30);
		temp.healBuilding(b);
		assertEquals(68,b.getHealth());
		
	}


}
