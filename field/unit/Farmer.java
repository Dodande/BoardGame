package field.unit;

import field.building.Farm;
import backend.Funds;
import field.Player;

public class Farmer extends Unit{

	int healthPoint = 150;
	int farmingAmount = 12; //Can be modified
	public static final Funds unitFunds = new Funds(-15,-15,-15);
	
	//Constructor inherited
	
	public Farmer(int x, int y, int health, Player player) {
	
		super(x, y, health, player);
	}
	
	//Farm from a selected farm
	
	public void farm(Farm f) {
		f.reduceResource(farmingAmount);
		this.decrementAction();
	}

}
