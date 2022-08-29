package field.building;

import backend.Funds;
import field.Player;
import field.unit.Warrior;

public class Barrack extends Building{

	//Constructor inherited
	
	public Barrack(int x, int y, int health, Player player) {
		
		super(x, y, health, player);
		
	}
	
	//Needs implementation
	public Warrior createWarrior(int x, int y, int health,Player player) {
		Warrior w = new Warrior(x,y,health,player);
		return w;
	}

}
