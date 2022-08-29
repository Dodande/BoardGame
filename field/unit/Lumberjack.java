package field.unit;

import backend.Funds;
import field.Player;
import field.resource.Woods;

public class Lumberjack extends Unit{

	int healthPoint = 120;
	int cuttingAmount = 15; //Can be modified
	public static final Funds unitFunds = new Funds(0,-25,-25);
	
	//Constructor inherited
	
	public Lumberjack(int x, int y, int health, Player player) {
		
		super(x, y, health, player);		
	}
	
	//Cut trees from the selected woods
	
	public void cutTree(Woods w) {
		w.reduceResource(cuttingAmount);
		this.decrementAction();
	}

}
