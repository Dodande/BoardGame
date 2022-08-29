package test.java.main;

import static org.junit.Assert.*;

import java.util.*;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import field.Coordinate;
import field.EmptyField;
import field.Field;
import field.Player;
import field.building.*;
import field.resource.*;
import field.unit.*;
import main.GameBoard;
import main.Main;

public class GameBoardTest {
	
	private GameBoard nonInitialized = new GameBoard();
	private GameBoard testBoard = new GameBoard();
	
	@Before
	public void before() {
		testBoard.initializePlayground();
	}

	@Test
	public void testEmptyBoard() {
		assertNotNull(nonInitialized.getPlayground());
		assertEquals(25, nonInitialized.getPlayground().length);
		for(Field[] row: nonInitialized.getPlayground()) {
			assertEquals(40, row.length);
			for (Field field: row) {
				assertNull(field);
			}
		}
	}
	
	@Test
	public void testInitializedBoard() {
		int mines = 0;
		int woods = 0;
		int empty = 0;
		MainBuilding mbFst = null;
		MainBuilding mbSnd = null;
		for(Field[] row: testBoard.getPlayground()) {
			assertEquals(40, row.length);
			for (Field field: row) {
				assertNotNull(field);
				if (field instanceof EmptyField) empty++;
				if (field instanceof Woods) woods++;
				if (field instanceof Mine) mines++;
				if (field instanceof MainBuilding) {
					MainBuilding mb = (MainBuilding) field;
					if (mb.getPlayer() == Player.First) mbFst = mb;
					if (mb.getPlayer() == Player.Second) mbSnd = mb;
				}
			}
		}
		assertEquals(25 * 40, 2 + mines + woods + empty);
		assertNotNull(mbFst);
		assertNotNull(mbSnd);
		assertEquals(mbFst.getCoordinates().getYCoordinate(), mbSnd.getCoordinates().getYCoordinate());
		assertTrue(mines >= 4);
		assertTrue(mines <= 10);
		assertTrue(woods >= 20);
		assertTrue(woods <= 40);
		assertFalse(testBoard.isWinning(mbFst.getPlayer()));
		assertFalse(testBoard.isWinning(mbSnd.getPlayer()));
	}
	
	@Test
	public void testFreeCoordinates() {
		Optional<Field> optMB = Arrays.<Field[]>stream(testBoard.getPlayground())
				.flatMap(row -> Arrays.<Field>stream(row)).filter(field -> field instanceof MainBuilding).findAny();
		assertTrue(optMB.isPresent());
		MainBuilding mb = (MainBuilding) optMB.get();
		List<Coordinate> freeCoordinates = testBoard.getNearbyFreeCoordinates(mb.getCoordinates());
		if (!freeCoordinates.isEmpty()) {
			assertTrue(freeCoordinates.size() <= 8);
			Coordinate free = testBoard.getFreeCoordinateAround(mb.getCoordinates());
			assertTrue(freeCoordinates.contains(free));
			int diffX = free.getXCoordinate() - mb.getCoordinates().getXCoordinate();
			int diffY = free.getYCoordinate() - mb.getCoordinates().getYCoordinate();
			assertTrue(diffX >= -1);
			assertTrue(diffX <= 1);
			assertTrue(diffY >= -1);
			assertTrue(diffY <= 1);
		} else assertNull(testBoard.getFreeCoordinateAround(mb.getCoordinates()));
		int[] cornersY = new int[]{0, 0, 24, 24};
		int[] cornersX = new int[]{0, 39, 39, 0};
		for (int i = 0; i < 4; i++) {
			Field corner = testBoard.getPlayground()[cornersY[i]][cornersX[i]];
			if (corner instanceof EmptyField) {
				freeCoordinates = testBoard.getNearbyFreeCoordinates(((EmptyField) corner).getCoordinates());
				assertTrue(freeCoordinates.size() <= 3);
				Coordinate free = testBoard.getFreeCoordinateAround(((EmptyField) corner).getCoordinates());
				if (free != null) assertTrue(freeCoordinates.contains(free));
				else assertTrue(freeCoordinates.isEmpty());
			} else if (corner instanceof Resource) {
				freeCoordinates = testBoard.getNearbyFreeCoordinates(((Resource) corner).getCoordinates());
				assertTrue(freeCoordinates.size() <= 3);
				Coordinate free = testBoard.getFreeCoordinateAround(((Resource) corner).getCoordinates());
				if (free != null) assertTrue(freeCoordinates.contains(free));
				else assertTrue(freeCoordinates.isEmpty());
			}
		}
		int[] edgesY = new int[]{0, 12, 24, 12};
		int[] edgesX = new int[]{19, 39, 20, 0};
		for (int i = 0; i < 4; i++) {
			Field corner = testBoard.getPlayground()[edgesY[i]][edgesX[i]];
			if (corner instanceof EmptyField) {
				freeCoordinates = testBoard.getNearbyFreeCoordinates(((EmptyField) corner).getCoordinates());
				assertTrue(freeCoordinates.size() <= 5);
				Coordinate free = testBoard.getFreeCoordinateAround(((EmptyField) corner).getCoordinates());
				if (free != null) assertTrue(freeCoordinates.contains(free));
				else assertTrue(freeCoordinates.isEmpty());
			} else if (corner instanceof Resource) {
				freeCoordinates = testBoard.getNearbyFreeCoordinates(((Resource) corner).getCoordinates());
				assertTrue(freeCoordinates.size() <= 5);
				Coordinate free = testBoard.getFreeCoordinateAround(((Resource) corner).getCoordinates());
				if (free != null) assertTrue(freeCoordinates.contains(free));
				else assertTrue(freeCoordinates.isEmpty());
			}
		}
	}
	
	@Test
	public void testUnitCloseToFields() {
		for (int y = 0; y < 25; y++) {
			for (int x = 0; x < 40; x++) {
				nonInitialized.getPlayground()[y][x] = new EmptyField(x, y);
			}
		}
		Woods w = new Woods(0, 0, 0, Player.None);
		nonInitialized.getPlayground()[0][0] = w;
		Mine m = new Mine(2, 0, 0, Player.None);
		nonInitialized.getPlayground()[0][2] = m;
		Builder b1 = new Builder(0, 1, 0, Player.None);
		nonInitialized.getPlayground()[1][0] = b1;
		Builder b2 = new Builder(2, 1, 0, Player.None);
		nonInitialized.getPlayground()[1][2] = b2;
		Builder b3 = new Builder(1, 1, 0, Player.None);
		nonInitialized.getPlayground()[1][1] = b3;
		Farm f1 = new Farm(0, 2, 0, Player.None);
		nonInitialized.getPlayground()[2][0] = f1;
		Farm f2 = new Farm(2, 2, 0, Player.None);
		nonInitialized.getPlayground()[2][2] = f2;
		assertTrue(nonInitialized.isResourceNearUnit(b3, m));
		assertTrue(nonInitialized.isResourceNearUnit(b3, w));
		assertTrue(nonInitialized.isResourceNearUnit(b2, m));
		assertTrue(nonInitialized.isResourceNearUnit(b1, w));
		assertFalse(nonInitialized.isResourceNearUnit(b1, m));
		assertFalse(nonInitialized.isResourceNearUnit(b2, w));
		assertTrue(nonInitialized.isUnitNearUnit(b1, b3));
		assertTrue(nonInitialized.isUnitNearUnit(b3, b1));
		assertTrue(nonInitialized.isUnitNearUnit(b2, b3));
		assertTrue(nonInitialized.isUnitNearUnit(b3, b2));
		assertFalse(nonInitialized.isUnitNearUnit(b2, b1));
		assertFalse(nonInitialized.isUnitNearUnit(b1, b2));
		assertTrue(nonInitialized.isBuildingNearUnit(b3, f1));
		assertTrue(nonInitialized.isBuildingNearUnit(b3, f2));
		assertTrue(nonInitialized.isBuildingNearUnit(b1, f1));
		assertTrue(nonInitialized.isBuildingNearUnit(b2, f2));
		assertFalse(nonInitialized.isBuildingNearUnit(b1, f2));
		assertFalse(nonInitialized.isBuildingNearUnit(b2, f1));
	}
	
	@Test
	public void testWinning() {
		Main.getGameBoard().initializePlayground(); // FIXME bad dependency
		List<MainBuilding> mbs = Arrays.<Field[]>stream(Main.getGameBoard().getPlayground())
				.flatMap(row -> Arrays.<Field>stream(row)).filter(field -> field instanceof MainBuilding)
				.map(field -> (MainBuilding) field).collect(Collectors.toList());
		assertEquals(2, mbs.size());
		MainBuilding toWin = mbs.get(0);
		MainBuilding toFall = mbs.get(1);
		assertNotEquals(toWin.getPlayer(), toFall.getPlayer());
		toFall.collapse(); // FIXME bad dependency
		assertTrue(Main.getGameBoard().getPlayground()[toFall.getCoordinates().getYCoordinate()][toFall.getCoordinates().getXCoordinate()] instanceof EmptyField);
		assertTrue(Main.getGameBoard().isWinning(toWin.getPlayer()));
		assertFalse(Main.getGameBoard().isWinning(toFall.getPlayer()));
	}

}
