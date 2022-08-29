package field.resource;

import field.Player;

public class Mine extends Resource{

	//Constructor inherited
	
	public Mine(int x, int y, int health, Player player) {
			
		super(x, y, health, player);
		setResource(120);//Can be modified
		
	}

}
