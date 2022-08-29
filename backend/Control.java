package backend;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import field.Coordinate;
import field.Player;
import field.building.Barrack;
import field.building.Building;
import field.building.Farm;
import field.building.MainBuilding;
import field.unit.Builder;
import field.unit.Farmer;
import field.unit.Lumberjack;
import field.unit.Miner;
import field.unit.Unit;
import field.unit.Warrior;
import graphics.GameToolbar;
import main.GameBoard;
import main.Main;

public class Control {
	private GameToolbar theGameToolbar;
	private Turn theTurn;
	private Gamer theFirstGamer;
	private Gamer theSecondGamer;
	private static Control control;
	
	private Control (GameToolbar theGameToolbar, Turn theTurn, Gamer firstGamer, Gamer secondGamer) {
		
		this.theGameToolbar = theGameToolbar;
		this.theTurn = theTurn;
		this.theFirstGamer = firstGamer;
		this.theSecondGamer = secondGamer;
		
		this.theGameToolbar.addEndTurnListener(new endTurnListener());
	}
	
	class endTurnListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			theTurn.nextTurn();
			theGameToolbar.setWhoseTurn(theTurn.getWhoseTurn());
			resetAction();
		}
	}
	
	public static Control getControl() {
		return control;
	}	
	
	
	public static Control getControl(GameToolbar theGameToolbar, Turn theTurn, Gamer firstGamer, Gamer secondGamer) {
		if (control==null) {
			control=new Control(theGameToolbar, theTurn, firstGamer, secondGamer);
		}
		return control;
	}
	
	public Builder createBuilder(MainBuilding mb, Coordinate free) {
		Gamer currentGamer;
		Builder builder = null;
		
		if (theTurn.getWhoseTurn() == Player.First) {
			currentGamer = theFirstGamer ;
		}else {
			currentGamer = theSecondGamer;
			}
		if (free!=null && currentGamer.hasEnoughFunds(Builder.unitFunds) && currentGamer.unitAmount <= currentGamer.maximumUnits) {
			builder = mb.createBuilder(free.getXCoordinate(), free.getYCoordinate(), 100, mb.getPlayer());
			currentGamer.setGamerFunds(Builder.unitFunds);
			theGameToolbar.setPlayerFunds(theTurn.getWhoseTurn(),currentGamer.getGamerFunds());
			currentGamer.unitAmount += 1;
			theGameToolbar.updateUnitStats(theTurn.getWhoseTurn(), currentGamer.unitAmount);
		}
		return builder;
	}
	
	public Lumberjack createLumberjack(MainBuilding mb, Coordinate free) {
		Gamer currentGamer;
		Lumberjack lumberjack = null;
		
		if (theTurn.getWhoseTurn() == Player.First) {
			currentGamer = theFirstGamer ;
		}else {
			currentGamer = theSecondGamer;
			}
		if (free!=null && currentGamer.hasEnoughFunds(Lumberjack.unitFunds) && currentGamer.unitAmount <= currentGamer.maximumUnits) {
			lumberjack = mb.createLumberjack(free.getXCoordinate(), free.getYCoordinate(), 100, mb.getPlayer());
			currentGamer.setGamerFunds(Lumberjack.unitFunds);
			theGameToolbar.setPlayerFunds(theTurn.getWhoseTurn(),currentGamer.getGamerFunds());
			currentGamer.unitAmount += 1;
			theGameToolbar.updateUnitStats(theTurn.getWhoseTurn(), currentGamer.unitAmount);
		}
		return lumberjack;
	}
	
	public Miner createMiner(MainBuilding mb, Coordinate free) {
		Gamer currentGamer;
		Miner miner = null;
		
		if (theTurn.getWhoseTurn() == Player.First) {
			currentGamer = theFirstGamer ;
		}else {
			currentGamer = theSecondGamer;
			}
		if (free!=null && currentGamer.hasEnoughFunds(Miner.unitFunds) && currentGamer.unitAmount <= currentGamer.maximumUnits) {
			miner = mb.createMiner(free.getXCoordinate(), free.getYCoordinate(), 100, mb.getPlayer());
			currentGamer.setGamerFunds(Miner.unitFunds);
			theGameToolbar.setPlayerFunds(theTurn.getWhoseTurn(),currentGamer.getGamerFunds());
			currentGamer.unitAmount += 1;
			theGameToolbar.updateUnitStats(theTurn.getWhoseTurn(), currentGamer.unitAmount);
		}
		return miner;
	}
	
	public Farmer createFarmer(MainBuilding mb, Coordinate free) {
		Gamer currentGamer;
		Farmer farmer = null;
		
		if (theTurn.getWhoseTurn() == Player.First) {
			currentGamer = theFirstGamer ;
		}else {
			currentGamer = theSecondGamer;
			}
		if (free!=null && currentGamer.hasEnoughFunds(Farmer.unitFunds) && currentGamer.unitAmount <= currentGamer.maximumUnits) {
			farmer = mb.createFarmer(free.getXCoordinate(), free.getYCoordinate(), 100, mb.getPlayer());
			currentGamer.setGamerFunds(Farmer.unitFunds);
			theGameToolbar.setPlayerFunds(theTurn.getWhoseTurn(),currentGamer.getGamerFunds());
			currentGamer.unitAmount += 1;
			theGameToolbar.updateUnitStats(theTurn.getWhoseTurn(), currentGamer.unitAmount);
		}
		return farmer;
	}
	
	public Warrior createWarrior(Barrack barrack, Coordinate free) {
		Gamer currentGamer;
		Warrior warrior = null;
		
		if (theTurn.getWhoseTurn() == Player.First) {
			currentGamer = theFirstGamer ;
		}else {
			currentGamer = theSecondGamer;
			}
		if (free!=null && currentGamer.hasEnoughFunds(Warrior.unitFunds) && currentGamer.unitAmount <= currentGamer.maximumUnits) {
			warrior = barrack.createWarrior(free.getXCoordinate(), free.getYCoordinate(), 100, barrack.getPlayer());
			currentGamer.setGamerFunds(Warrior.unitFunds);
			theGameToolbar.setPlayerFunds(theTurn.getWhoseTurn(),currentGamer.getGamerFunds());
			currentGamer.unitAmount += 1;
			theGameToolbar.updateUnitStats(theTurn.getWhoseTurn(), currentGamer.unitAmount);
		}
		return warrior;
	}
	
	public Building buildBuilding(Builder builder, Coordinate buildCoordinate, BuildingType buildingType) {
		Gamer currentGamer;
		Building building = null;
		boolean hasAction = 0 < builder.getAction();
		if (theTurn.getWhoseTurn() == Player.First) {
			currentGamer = theFirstGamer ;
		}else {
			currentGamer = theSecondGamer;
			}
		
		if (buildCoordinate!=null && currentGamer.hasEnoughFunds(buildingType.getGetFunds()) && hasAction) {
			building = builder.buildBuilding(buildCoordinate.getXCoordinate(), buildCoordinate.getYCoordinate(), 100, builder.getPlayer(),buildingType);
			currentGamer.setGamerFunds(buildingType.getGetFunds());
			theGameToolbar.setPlayerFunds(theTurn.getWhoseTurn(),currentGamer.getGamerFunds());
			builder.decrementAction();

		}
		return building;
	}
	
	public void decreasePopulation(Player whose) {
		Gamer gamer;
		if (whose == Player.First) gamer = theFirstGamer;
		else gamer = theSecondGamer;
		gamer.unitAmount -= 1;
		theGameToolbar.updateUnitStats(whose, gamer.unitAmount);
	}
	
	public void addFunds(Player player, Funds funds) {
		Gamer currentGamer;
		if (player == Player.First) currentGamer = theFirstGamer;
		else currentGamer = theSecondGamer; 
		currentGamer.setGamerFunds(funds);
		theGameToolbar.setPlayerFunds(player, currentGamer.getGamerFunds());
	}
	public void move(Unit unit, int x, int y ) {
        unit.decrementAction();
            unit.move(x, y);
    }

    public boolean hasAction(Unit unit){
        return unit.getAction()>0;
    }
    
    
	
	private void resetAction() {
		GameBoard gameBoard = Main.getGameBoard();
		Arrays.stream(gameBoard.getPlayground())
        .flatMap(Arrays::stream)
        .filter(field -> field instanceof Unit)
        .map(field -> (Unit)field)
        .forEach(Unit::resetAction);

		/*for(int i=0;i<gameBoard.getPlayground().length;++i) {
			for(int j=0;j<gameBoard.getPlayground()[i].length;++j) {
				if (gameBoard.getPlayground()[i][j] instanceof Unit) {
					Unit asd=((Unit)gameBoard.getPlayground()[i][j]);
							asd.resetAction();
				}
				
			}
			
		}*/

	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}