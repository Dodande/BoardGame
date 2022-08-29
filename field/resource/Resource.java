package field.resource;

import field.Coordinate;
import field.EmptyField;
import field.Field;
import field.Player;
import main.Main;

public abstract class Resource implements Field{
	
	//Attributes
	
	Player player;
	int healthPoint; //Van vajon értelme az életnek resourcenál? Az ellenfél is inkább kizsákmányolni akarná az elpusztítás helyett
	Coordinate c;
	int resourceAmount;
	boolean isCollapsed = false;
	
	//Constructor
	
	public Resource(int x, int y, int health, Player player) {
		c = new Coordinate(x,y);
		setHealth(health);
		setPlayer(player);
	}
	
	//Getters
	
	public int getHealth() {
		return healthPoint;
	}
	
	
	public Coordinate getCoordinates() {
		return c;
	}
	
	public int currentResource() {
		return resourceAmount;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	//Setters
	
	public void setHealth(int health) {
		this.healthPoint = health;
	}
	
	public void setPosition(int x, int y) {
		c.setCoordinates(x, y);
	}
	
	public void setResource(int amount) {
		this.resourceAmount = amount;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
		
	public void reduceResource(int amount) {
		if (amount < currentResource()) {
			this.resourceAmount -= amount;
		}
		else {
			setResource(0);
			collapse();
		}
	}
	
	public void collapse() {
		this.isCollapsed = true;
		Main.getGameBoard().getPlayground()[c.getYCoordinate()][c.getXCoordinate()] = new EmptyField(c.getXCoordinate(), c.getYCoordinate());
		Main.getGameMapPanel().refresh();
	}
	
	

}
