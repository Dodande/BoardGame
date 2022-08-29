package field.building;

import backend.Funds;
import field.Coordinate;
import field.EmptyField;
import field.Field;
import field.Player;
import main.Main;

public abstract class Building implements Field{
	
	//Attributes
	
	Player player;
	int healthPoint;
	int maxHealth;
	Coordinate c;
	boolean isCollapsed = false;
	
	//Constructor
	
	public Building(int x, int y, int health, Player player) {
		c = new Coordinate(x,y);
		setHealth(health);
		setPlayer(player);
		maxHealth = health; //Indicates that each building starts with max HP, needed to avoid over the max HPs
	}
	
	//Getters
	
	public int getHealth() {
		return healthPoint;
	}
	

	public Coordinate getCoordinates() {
		return c;
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
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public void restoreHealth(int amount) {
		if(getHealth() + amount >= maxHealth) {
			this.healthPoint = maxHealth;
		}
		else {
			this.healthPoint += amount;
		}
	}
	
	public void loseHealth(int amount) {
		if (amount < this.healthPoint) {
			this.healthPoint -= amount;
		}
		else {
			setHealth(0);
			collapse();
		}
	}
	
	public void collapse() {
		this.isCollapsed = true;
		Main.getGameBoard().getPlayground()[c.getYCoordinate()][c.getXCoordinate()] = new EmptyField(c.getXCoordinate(), c.getYCoordinate());
	}
	
	

}
