package field;

public class Coordinate {
	int x;
	int y;
	
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	//Getters
	
	public int getXCoordinate() {
		
		return this.x;
	}
	
	public int getYCoordinate() {
		return this.y;
	}
	
	//Setter
	
	public void setCoordinates(int x, int y) {
		
		this.x = x;
		this.y = y;
	}
}
