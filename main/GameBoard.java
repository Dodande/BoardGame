package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import field.Coordinate;
import field.EmptyField;
import field.Field;
import field.Player;
import field.building.Building;
import field.building.MainBuilding;
import field.resource.Mine;
import field.resource.Resource;
import field.resource.Woods;
import field.unit.Unit;

public class GameBoard {

	private Field[][] playGround = new Field[25][40];
	
	private Random r = new Random();
	
	public Field[][] getPlayground() {
		return playGround;
	}
	
	public void initializePlayground() {
		List<Resource> resources = new ArrayList<>();
		int m = r.nextInt(3);
		for (int i = 0; i < 3 + m; i++) {
			resources.add(new Mine(r.nextInt(20), r.nextInt(25), 100, Player.None));
		}
		int w = r.nextInt(6);
		for (int i = 0; i < 15 + w; i++) {
			resources.add(new Woods(r.nextInt(20), r.nextInt(25), 100, Player.None));
		}
		MainBuilding mb = new MainBuilding(2 + r.nextInt(16), 2 + r.nextInt(20), 100, Player.First);
		for (int row = 0; row < playGround.length; row++) {
			for (int col = 0; col < playGround[row].length / 2; col++) {
				playGround[row][col] = new EmptyField(col, row);
				playGround[row][39 - col] = new EmptyField(39 - col, row);
				final int fr = row;
				final int fc = col;
				Optional<Resource> opt = resources.stream()
						.filter(r -> r.getCoordinates().getYCoordinate() == fr && r.getCoordinates().getXCoordinate() == fc)
						.findAny();
				if (opt.isPresent()) {
					Resource res = opt.get();
					if (res instanceof Woods) playGround[row][39 - col] = new Woods(39 - col, row, 100, Player.None);
					if (res instanceof Mine) playGround[row][39 - col] = new Mine(39 - col, row, 100, Player.None);
					playGround[row][col] = res;
				}
				if (mb.getCoordinates().getXCoordinate() == col && mb.getCoordinates().getYCoordinate() == row) {
					playGround[row][col] = mb;
					playGround[row][39 - col] = new MainBuilding(39 - col, row, 100, Player.Second);
				}
			}
		}
		if (!Arrays.stream(playGround).flatMap(fs -> Arrays.stream(fs)).anyMatch(f -> f instanceof MainBuilding)) initializePlayground();
	}

	/**
	 * Picks a free coordinate clockwise around a selected center coordinate:
	 * <table>
	 * <tr><td>8</td><td>1</td><td>2</td></tr>
	 * <tr><td>7</td><td>C</td><td>3</td></tr>
	 * <tr><td>6</td><td>5</td><td>4</td></tr>
	 * </table>
	 * A free coordinate is an empty field.
	 * @param center coordinate to start from
	 * @return first free coordinate (may be null if there is no free)
	 */
	public Coordinate getFreeCoordinateAround(Coordinate center) {
		boolean topEnabled = (center.getYCoordinate() - 1) >= 0;
		boolean leftEnabled = (center.getXCoordinate() - 1) >= 0;
		boolean rightEnabled = (center.getXCoordinate() + 1) < playGround[0].length;
		boolean bottomEnabled = (center.getYCoordinate() + 1) < playGround.length;
		if (topEnabled && playGround[center.getYCoordinate() - 1][center.getXCoordinate()] instanceof EmptyField)
			return ((EmptyField) playGround[center.getYCoordinate() - 1][center.getXCoordinate()]).getCoordinates();
		if (topEnabled && rightEnabled && playGround[center.getYCoordinate() - 1][center.getXCoordinate() + 1] instanceof EmptyField)
			return ((EmptyField) playGround[center.getYCoordinate() - 1][center.getXCoordinate() + 1]).getCoordinates();
		if (rightEnabled && playGround[center.getYCoordinate()][center.getXCoordinate() + 1] instanceof EmptyField)
			return ((EmptyField) playGround[center.getYCoordinate()][center.getXCoordinate() + 1]).getCoordinates();
		if (rightEnabled && bottomEnabled && playGround[center.getYCoordinate() + 1][center.getXCoordinate() + 1] instanceof EmptyField)
			return ((EmptyField) playGround[center.getYCoordinate() + 1][center.getXCoordinate() + 1]).getCoordinates();
		if (bottomEnabled && playGround[center.getYCoordinate() + 1][center.getXCoordinate()] instanceof EmptyField)
			return ((EmptyField) playGround[center.getYCoordinate() + 1][center.getXCoordinate()]).getCoordinates();
		if (bottomEnabled && leftEnabled && playGround[center.getYCoordinate() + 1][center.getXCoordinate() - 1] instanceof EmptyField)
			return ((EmptyField) playGround[center.getYCoordinate() + 1][center.getXCoordinate() - 1]).getCoordinates();
		if (leftEnabled && playGround[center.getYCoordinate()][center.getXCoordinate() - 1] instanceof EmptyField)
			return ((EmptyField) playGround[center.getYCoordinate()][center.getXCoordinate() - 1]).getCoordinates();
		if (leftEnabled && topEnabled && playGround[center.getYCoordinate() - 1][center.getXCoordinate() - 1] instanceof EmptyField)
			return ((EmptyField) playGround[center.getYCoordinate() - 1][center.getXCoordinate() - 1]).getCoordinates();
		return null;
	}
	
	public List<Coordinate> getNearbyFreeCoordinates(Coordinate center) {
		List<Coordinate> coordinates = new ArrayList<>();
		boolean topEnabled = (center.getYCoordinate() - 1) >= 0;
		boolean leftEnabled = (center.getXCoordinate() - 1) >= 0;
		boolean rightEnabled = (center.getXCoordinate() + 1) < playGround[0].length;
		boolean bottomEnabled = (center.getYCoordinate() + 1) < playGround.length;
		if (topEnabled && playGround[center.getYCoordinate() - 1][center.getXCoordinate()] instanceof EmptyField)
			coordinates.add(((EmptyField) playGround[center.getYCoordinate() - 1][center.getXCoordinate()]).getCoordinates());
		if (topEnabled && rightEnabled && playGround[center.getYCoordinate() - 1][center.getXCoordinate() + 1] instanceof EmptyField)
			coordinates.add(((EmptyField) playGround[center.getYCoordinate() - 1][center.getXCoordinate() + 1]).getCoordinates());
		if (rightEnabled && playGround[center.getYCoordinate()][center.getXCoordinate() + 1] instanceof EmptyField)
			coordinates.add(((EmptyField) playGround[center.getYCoordinate()][center.getXCoordinate() + 1]).getCoordinates());
		if (rightEnabled && bottomEnabled && playGround[center.getYCoordinate() + 1][center.getXCoordinate() + 1] instanceof EmptyField)
			coordinates.add(((EmptyField) playGround[center.getYCoordinate() + 1][center.getXCoordinate() + 1]).getCoordinates());
		if (bottomEnabled && playGround[center.getYCoordinate() + 1][center.getXCoordinate()] instanceof EmptyField)
			coordinates.add(((EmptyField) playGround[center.getYCoordinate() + 1][center.getXCoordinate()]).getCoordinates());
		if (bottomEnabled && leftEnabled && playGround[center.getYCoordinate() + 1][center.getXCoordinate() - 1] instanceof EmptyField)
			coordinates.add(((EmptyField) playGround[center.getYCoordinate() + 1][center.getXCoordinate() - 1]).getCoordinates());
		if (leftEnabled && playGround[center.getYCoordinate()][center.getXCoordinate() - 1] instanceof EmptyField)
			coordinates.add(((EmptyField) playGround[center.getYCoordinate()][center.getXCoordinate() - 1]).getCoordinates());
		if (leftEnabled && topEnabled && playGround[center.getYCoordinate() - 1][center.getXCoordinate() - 1] instanceof EmptyField)
			coordinates.add(((EmptyField) playGround[center.getYCoordinate() - 1][center.getXCoordinate() - 1]).getCoordinates());
		return coordinates;
	}
	
	public boolean isResourceNearUnit(Unit unit, Resource res) {
		int ux = unit.getCoordinates().getXCoordinate();
		int uy = unit.getCoordinates().getYCoordinate();
		int rx = res.getCoordinates().getXCoordinate();
		int ry = res.getCoordinates().getYCoordinate();
		return Math.abs(ry - uy) < 2 && Math.abs(rx - ux) < 2;
	}
	
	public boolean isBuildingNearUnit(Unit unit, Building building) {
		int ux = unit.getCoordinates().getXCoordinate();
		int uy = unit.getCoordinates().getYCoordinate();
		int bx = building.getCoordinates().getXCoordinate();
		int by = building.getCoordinates().getYCoordinate();
		return Math.abs(by - uy) < 2 && Math.abs(bx - ux) < 2;
	}
	
	public boolean isUnitNearUnit(Unit unit, Unit other) {
		int ux = unit.getCoordinates().getXCoordinate();
		int uy = unit.getCoordinates().getYCoordinate();
		int ox = other.getCoordinates().getXCoordinate();
		int oy = other.getCoordinates().getYCoordinate();
		return Math.abs(oy - uy) < 2 && Math.abs(ox - ux) < 2;
	}
	
	public boolean isWinning(Player player) {
		boolean othersMainBuildingCollapsed = true;
		for (Field[] row: playGround) {
			for (Field field: row) {
				if (field instanceof MainBuilding && ((MainBuilding) field).getPlayer() != player) othersMainBuildingCollapsed = false;
			}
		}
		return othersMainBuildingCollapsed;
	}
	
}
