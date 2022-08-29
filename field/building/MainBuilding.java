package field.building;

import field.Player;
import field.unit.Builder;
import field.unit.Farmer;
import field.unit.Lumberjack;
import field.unit.Miner;
import main.Main;

public class MainBuilding extends Building{

	//Constructor inherited
	
	public MainBuilding(int x, int y, int health, Player player) {
		
		super(x, y, health, player);
		
	}
	
	//These can be assigned to a map field
	//Create a builder
	
	public Builder createBuilder(int x, int y, int health, Player player){
		
		Builder b = new Builder(x,y,health,player);
		return b;
	}
	
	//Create a lumberjack
	
	public Lumberjack createLumberjack(int x, int y, int health, Player player){
		
		Lumberjack l = new Lumberjack(x,y,health,player);
		return l;
	}
	
	//Create a miner
	
	public Miner createMiner(int x, int y, int health, Player player){
	
		Miner m = new Miner(x,y,health,player);
		return m;
	}
	
	//Create a farmer
	
	public Farmer createFarmer(int x, int y, int health, Player player) {
		
		Farmer f = new Farmer(x,y,health,player);
		return f;
		
	}

}
