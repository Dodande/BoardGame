package field.unit;

import backend.Funds;
import field.Coordinate;
import field.Field;
import field.Player;
import field.building.Building;

public abstract class Unit implements Field{
	
	//Attributes
	
	Player player;	
	int healthPoint;
	Coordinate c;
	int baseDamage = 10;
	static Funds unitFunds;
	boolean isDead = false;
	boolean isRetired = false;
	int action =2;
	//Constructor
	
	public Unit(int x, int y, int health, Player player) {
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
	
	public Player getPlayer() {
		return player;
	}
	
	public int getAction() {
		return this.action;
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
	
	
	public void loseHealth(int amount) {
		if (amount < getHealth()) {
			this.healthPoint -= amount;
		}
		else {
			setHealth(0);
			die();
		}
	}
	
	public void die() {
		this.isDead = true;
	}
	
	public void retire() {
		this.isRetired = true;
	}
		
	public void move(int x, int y) {
		setPosition(x,y);
	}
	
	public void attack(Unit target) {
		target.loseHealth(this.baseDamage);
	}

	public void decrementAction() {
		--this.action;
		this.action=(this.action<0)? 0:this.action;
	}
	public void resetAction() {
		this.action=2;
	}
	
	public void attack(Building target) {
		target.loseHealth(this.baseDamage);
	}
	
	public boolean isAlive() {
		return !isDead;

	}

}
