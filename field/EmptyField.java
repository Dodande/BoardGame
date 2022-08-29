package field;

public class EmptyField implements Field {

	Player player;
	private Coordinate c;
	
	public EmptyField(int x, int y) {
		c = new Coordinate(x, y);
	}
	
	@Override
	public void setPosition(int x, int y) {
		c.setCoordinates(x, y);
	}
	
	public Coordinate getCoordinates() {
		return c;
	}
	
	public int getX() {
		return c.getXCoordinate();
	}
	
	public int getY() {
		return c.getYCoordinate();
	}
}
