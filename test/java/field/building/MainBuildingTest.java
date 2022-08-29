package test.java.field.building;

import static org.junit.Assert.*;

import org.junit.Test;

import field.Player;
import field.building.MainBuilding;
import field.unit.Builder;
import field.unit.Farmer;
import field.unit.Lumberjack;
import field.unit.Miner;

public class MainBuildingTest {

	MainBuilding temp = new MainBuilding(1,2,100,Player.First);

	@Test
	public void testCreation() {
		assertEquals(1,temp.getCoordinates().getXCoordinate());
		assertEquals(2,temp.getCoordinates().getYCoordinate());
		assertEquals(100,temp.getHealth());
		assertEquals(Player.First,temp.getPlayer());
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
	
	@Test
	public void testBuilderCreation() {
		Builder b = temp.createBuilder(3, 4, 50, Player.First);
		assertEquals(3,b.getCoordinates().getXCoordinate());
		assertEquals(4,b.getCoordinates().getYCoordinate());
		assertEquals(50,b.getHealth());
		assertEquals(Player.First,b.getPlayer());
	}
	
	@Test
	public void testLumberjackCreation() {
		Lumberjack l = temp.createLumberjack(3, 4, 50, Player.First);
		assertEquals(3,l.getCoordinates().getXCoordinate());
		assertEquals(4,l.getCoordinates().getYCoordinate());
		assertEquals(50,l.getHealth());
		assertEquals(Player.First,l.getPlayer());
	}
	
	@Test
	public void testMinerCreation() {
		Miner m = temp.createMiner(3, 4, 50, Player.First);
		assertEquals(3,m.getCoordinates().getXCoordinate());
		assertEquals(4,m.getCoordinates().getYCoordinate());
		assertEquals(50,m.getHealth());
		assertEquals(Player.First,m.getPlayer());
	}
	
	@Test
	public void testFarmerCreation() {
		Farmer f = temp.createFarmer(3, 4, 50, Player.First);
		assertEquals(3,f.getCoordinates().getXCoordinate());
		assertEquals(4,f.getCoordinates().getYCoordinate());
		assertEquals(50,f.getHealth());
		assertEquals(Player.First,f.getPlayer());
	}

}
