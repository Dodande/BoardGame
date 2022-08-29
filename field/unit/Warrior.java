package field.unit;

import backend.Funds;
import field.Player;
import field.building.Building;

public class Warrior extends Unit{

	int healthPoint = 180;
	int damage = 25;
	public static final Funds unitFunds = new Funds(-25,-25,-25);
	
	//Constructor inherited
	
	public Warrior(int x, int y, int health, Player player) {
		
		super(x, y, health, player);
		
	}
	
	@Override
	public void attack(Unit target) {
		target.loseHealth(this.damage);
		this.decrementAction();
	}
	
	@Override
	public void attack(Building target) {
		target.loseHealth(damage);
		this.decrementAction();
	}

}
