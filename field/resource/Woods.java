package field.resource;

import field.Player;

public class Woods extends Resource{

	//Constructor inherited
	
	public Woods(int x, int y, int health, Player player) {
				
		super(x, y, health, player);
		setResource(80); //Can be modified
		
	}

}
