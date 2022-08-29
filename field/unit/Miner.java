package field.unit;

import field.resource.Mine;
import backend.Funds;
import field.Player;

public class Miner extends Unit{

	int healthPoint = 130;
	int miningAmount = 14; //Can be modified
	public static final Funds unitFunds = new Funds(-25,0,-25);
	
	//Constructor inherited
	
	public Miner(int x, int y, int health, Player player) {

		super(x, y, health, player);

	}
	
	//Mine from the selected mine
	
	public void mine(Mine m) {
		m.reduceResource(miningAmount);
		this.decrementAction();
	}

}
