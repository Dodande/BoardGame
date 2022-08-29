package field.unit;

import backend.BuildingType;
import backend.Funds;
import field.Player;
import field.building.Barrack;
import field.building.Building;
import field.building.Farm;

public class Builder extends Unit{

	int healthPoint = 140;
	int repairAmount = 18;
	public static final Funds unitFunds = new Funds(-25,-25,0);
	//Constructor inherited
	
	public Builder(int x, int y, int health, Player player) {
		
		super(x, y, health, player);
		
		
	}
	
	//Needs implementation
	
	public Building buildBuilding(int x, int y, int health, Player player,BuildingType buildingType) {
		Building building =null;
		if	(buildingType==BuildingType.Farm) {
			building=new Farm(x, y, health, player);
		}else {
			building=new Barrack(x, y, health, player);
		}
		
		return building;
	}
	
	//Heal the selected building
	
	public void healBuilding(Building b) {
		b.restoreHealth(repairAmount);
		this.decrementAction();
	}

}
