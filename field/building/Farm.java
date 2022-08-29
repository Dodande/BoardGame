package field.building;

import backend.Funds;
import field.Player;

public class Farm extends Building{

	int resourceAmount;
	int maxResource = 100;
	
	//Constructor inherited
	
	public Farm(int x, int y, int health, Player player) {
	
		super(x, y, health, player);
		setResource(maxResource);
	}
	
	//Getter
	
	public int currentResource() {
		return resourceAmount;
	}
	
	//Setter
	
	public void setResource(int amount) {
		this.resourceAmount = amount;
	}
		
	public void reduceResource(int amount) {
		if (amount < this.resourceAmount) {
			this.resourceAmount -= amount;
		}
		else {
			setResource(0);
			collapse();
		}
	}

}
