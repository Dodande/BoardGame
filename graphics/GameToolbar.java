package graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import backend.BuildingType;
import backend.Control;
import backend.Funds;
import field.Coordinate;
import field.EmptyField;
import field.Field;
import field.Player;
import field.building.*;
import field.resource.*;
import field.unit.*;
import main.GameBoard;
import main.Main;

@SuppressWarnings("serial")
public class GameToolbar extends JPanel {
	
	//private JPanel statistics;
	private JPanel rStatistics;
	private JPanel bStatistics;
	private JPanel turnCount;
	private JPanel selection;
	private JPanel buttonBar;
	private JButton exitButton;
	private GameBoard gameBoard;
	private Unit actionUnit;
	private Action action = Action.NONE;
	
	
	
	private static final Color btnColor = Color.decode("#00C4D0");
	private static final Color bgColor = Color.decode("#C59D6A");
	
	private JButton endTurnBtn= new JButton("End Turn");
	private JLabel turnLabel = new JLabel("Red Turn");
	private Player hasTurn = Player.First;
	
	private JLabel rUnitAmountLabel = new JLabel("Pop: 0");
	private JLabel rWoodAmountLabel = new JLabel("Wood: 100");
	private JLabel rMeatAmountLabel = new JLabel("Meat: 100");
	private JLabel rGoldAmountLabel = new JLabel("Gold: 100");
	
	private JLabel bUnitAmountLabel = new JLabel("Pop: 0");
	private JLabel bWoodAmountLabel = new JLabel("Wood: 100");
	private JLabel bMeatAmountLabel = new JLabel("Meat: 100");
	private JLabel bGoldAmountLabel = new JLabel("Gold: 100");
	
	
	
	public GameToolbar() {
		super();
		gameBoard = Main.getGameBoard();
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setPreferredSize(new Dimension(200, 1000));
		setMinimumSize(getPreferredSize());
		setBackground(bgColor);
		
		rStatistics = new JPanel();
		rStatistics.setBackground(bgColor);
		rStatistics.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.RED), "Red Statistics"));
		
		rStatistics.add(rUnitAmountLabel);
		rStatistics.add(rWoodAmountLabel);
		rStatistics.add(rMeatAmountLabel);
		rStatistics.add(rGoldAmountLabel);
		
		bStatistics = new JPanel();
		bStatistics.setBackground(bgColor);
		bStatistics.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE), "Blue Statistics"));
		
		bStatistics.add(bUnitAmountLabel);
		bStatistics.add(bWoodAmountLabel);
		bStatistics.add(bMeatAmountLabel);
		bStatistics.add(bGoldAmountLabel);

		add(rStatistics);
		add(bStatistics);
		
		turnCount = new JPanel();
		turnCount.setBackground(bgColor);
		turnCount.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Turn Count"));
		
		
		endTurnBtn.setBackground(Color.decode("#00C4D0"));

		turnCount.add(endTurnBtn);	
		turnCount.add(turnLabel);
		add(turnCount);
		
		selection = new JPanel();
		selection.setBackground(bgColor);
		selection.setLayout(new BoxLayout(selection, BoxLayout.PAGE_AXIS));
		add(selection);
		buttonBar = new JPanel();
		buttonBar.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Actions"));
		buttonBar.setBackground(bgColor);
		add(buttonBar);
		add(Box.createVerticalGlue());
		exitButton = new JButton("EXIT");
		exitButton.setBackground(Color.decode("#D00031"));
		exitButton.addActionListener(e -> Main.exit());
		add(exitButton);
		validate();
	}
	
	public void displaySelectedField(Field field) {
		selection.removeAll();
		buttonBar.removeAll();
		buttonBar.setVisible(false);
		if (field != null && !(field instanceof EmptyField)) {
			String title = null;
			Color border = Color.black;
			if (field instanceof Building) {
				Building building = (Building) field;
				if (action == Action.ATTACK && building.getPlayer() != actionUnit.getPlayer() && gameBoard.isBuildingNearUnit(actionUnit, building) && actionUnit.getAction()>0) {
					actionUnit.attack(building);
					refreshGameMap();
					if (building instanceof MainBuilding && gameBoard.isWinning(actionUnit.getPlayer())) {
						Main.displayWinner(actionUnit.getPlayer());
					}
					action = Action.NONE;
					displaySelectedField(actionUnit);
				} else {
					if (building.getPlayer() == Player.First) border = Color.red;
					else if (building.getPlayer() == Player.Second) border = Color.blue;
					if (field instanceof MainBuilding) {
						title = "Main Building";
						MainBuilding mb = (MainBuilding) field;
						selection.add(new JLabel("Health:"));
						JProgressBar health = new JProgressBar();
						health.setForeground(border);
						health.setValue(mb.getHealth());
						selection.add(health);
						displayMainBuilding(mb);
					} else if (field instanceof Farm) {
						Farm farm = (Farm) field;
						title = "Farm";
						selection.add(new JLabel("Food left:"));
						JProgressBar foodLeft = new JProgressBar();
						foodLeft.setForeground(border);
						foodLeft.setValue(farm.currentResource());
						selection.add(foodLeft);
						selection.add(new JLabel("Health:"));
						JProgressBar health = new JProgressBar();
						health.setValue(farm.getHealth());
						selection.add(health);
						if (action == Action.FARM_FOOD) {
							if (gameBoard.isBuildingNearUnit((Unit) actionUnit, (Building) field)) {
								((Farmer) actionUnit).farm((Farm) field);
								Control.getControl().addFunds(actionUnit.getPlayer(), new Funds(0, 12, 0)); // TODO access resource extraction amount
							}
							actionUnit = null;
							action = Action.NONE;
						}
					} else if (field instanceof Barrack) {
						title = "Barrack";
						Barrack barrack = (Barrack) field;
						selection.add(new JLabel("Health:"));
						JProgressBar health = new JProgressBar();
						health.setForeground(border);
						health.setValue(barrack.getHealth());
						selection.add(health);
						displayBarrackActions(barrack);
					}
					if (action == Action.REPAIR_BUILDING && actionUnit.getAction()>0) {
						if (gameBoard.isBuildingNearUnit((Unit) actionUnit, (Building) field)) 
							((Builder) actionUnit).healBuilding((Building) field);
						action = Action.NONE;
						displaySelectedField(actionUnit);
					}
				}
			} else if (field instanceof Resource) {
				JProgressBar health = new JProgressBar();
				if (field instanceof Woods) {
					border = Color.decode("#1B8000");
					title = "Woods";
					selection.add(new JLabel("Wood left:"));
					health.setMaximum(80);
					if (action == Action.CUT_WOOD) {
						if (gameBoard.isResourceNearUnit((Unit) actionUnit, (Resource) field) && actionUnit.getAction()>0) {
							((Lumberjack) actionUnit).cutTree((Woods) field);
							Control.getControl().addFunds(actionUnit.getPlayer(), new Funds(15, 0, 0));
						}
						actionUnit = null;
						action = Action.NONE;
					}
				}
				else if (field instanceof Mine) {
					border = Color.orange;
					title = "Mine";
					selection.add(new JLabel("Gold left:"));
					health.setMaximum(120);
					if (action == Action.MINE_GOLD) {
						if (gameBoard.isResourceNearUnit((Unit) actionUnit, (Resource) field) && actionUnit.getAction()>0) {
							((Miner) actionUnit).mine((Mine) field);
							Control.getControl().addFunds(actionUnit.getPlayer(), new Funds(0, 0, 14));
						}
						actionUnit = null;
						action = Action.NONE;
					}
				}
				health.setForeground(border);
				health.setValue(((Resource) field).currentResource());
				selection.add(health);
				buttonBar.setVisible(false);
			} else if (field instanceof Farm) {
				Farm farm = (Farm) field;
				if (farm.getPlayer() == Player.First) border = Color.red;
				else if (farm.getPlayer() == Player.Second) border = Color.blue;
				title = "Farm";
				selection.add(new JLabel("Food left:"));
				JProgressBar foodLeft = new JProgressBar();
				foodLeft.setForeground(border);
				foodLeft.setValue(farm.currentResource());
				selection.add(foodLeft);
				selection.add(new JLabel("Health:"));
				JProgressBar health = new JProgressBar();
				health.setValue(farm.getHealth());
				selection.add(health);
				if (action == Action.FARM_FOOD) {
					if (gameBoard.isBuildingNearUnit((Unit) actionUnit, (Building) field) && actionUnit.getAction()>0) {
						((Farmer) actionUnit).farm((Farm) field);
						Control.getControl().addFunds(actionUnit.getPlayer(), new Funds(0, 12, 0)); // TODO access resource extraction amount
					}
					actionUnit = null;
					action = Action.NONE;
				} else if (action == Action.REPAIR_BUILDING) {
					if (gameBoard.isBuildingNearUnit((Unit) actionUnit, (Building) field)) 
						((Builder) actionUnit).healBuilding((Building) field);
					actionUnit = null;
					action = Action.NONE;
				}
			} else if (field instanceof Barrack) {
				title = "Barrack";
				Barrack barrack = (Barrack) field;
				if (barrack.getPlayer() == Player.First) border = Color.red;
				else if (barrack.getPlayer() == Player.Second) border = Color.blue;
				selection.add(new JLabel("Health:"));
				JProgressBar health = new JProgressBar();
				health.setForeground(border);
				health.setValue(barrack.getHealth());
				selection.add(health);
				displayBarrackActions(barrack);
				if (action == Action.REPAIR_BUILDING) {
					if (gameBoard.isBuildingNearUnit((Unit) actionUnit, (Building) field)) 
						((Builder) actionUnit).healBuilding((Building) field);
					actionUnit = null;
					action = Action.NONE;
				}
			} else if (field instanceof Unit) {
				Unit unit = (Unit) field;
				
				if (action == Action.ATTACK && actionUnit.getPlayer() != unit.getPlayer() && gameBoard.isUnitNearUnit(actionUnit, unit) && actionUnit.getAction()>0) {
					actionUnit.attack(unit);
					if (!unit.isAlive()) {
						gameBoard.getPlayground()[unit.getCoordinates().getYCoordinate()][unit.getCoordinates().getXCoordinate()] = new EmptyField(unit.getCoordinates().getXCoordinate(), unit.getCoordinates().getYCoordinate());
						Control.getControl().decreasePopulation(unit.getPlayer());
					}
					action = Action.NONE;
					displaySelectedField(actionUnit);
				}else {

				
				if (unit.getPlayer() == Player.First) border = Color.red;
				else if (unit.getPlayer() == Player.Second) border = Color.blue;
				selection.add(new JLabel("Health:"));
				JProgressBar health = new JProgressBar();
				health.setForeground(border);
				health.setValue(unit.getHealth());
				selection.add(health);
				JLabel actionLeft = new JLabel();
				actionLeft.setText(("Action: " + Integer.toString(unit.getAction())));
				selection.add(actionLeft);
				JButton moveBtn = new JButton("Move");
				moveBtn.setBackground(btnColor);
				moveBtn.addActionListener(e -> {
					actionUnit = unit;
					action = Action.MOVE;
				});
				buttonBar.add(moveBtn);
				if (unit instanceof Builder) {
					title = "Builder";
					displayBuilderActions((Builder) unit);
				}
				else if (unit instanceof Lumberjack) {
					title = "Lumberjack";
					displayLumberjackActions((Lumberjack) unit);
				}
				else if (unit instanceof Miner) {
					title = "Miner";
					displayMinerActions((Miner) unit);
				}
				else if (unit instanceof Farmer) {
					title = "Farmer";
					displayFarmerActions((Farmer) unit);
				}
				else if (unit instanceof Warrior) {
					title = "Warrior";
					displayWarriorActions((Warrior) unit);
				}
				JButton retireBtn = new JButton("Retire " + title);
				retireBtn.setBackground(btnColor);
				retireBtn.addActionListener(e -> {
					EmptyField empty = new EmptyField(unit.getCoordinates().getXCoordinate(), unit.getCoordinates().getYCoordinate());
					gameBoard.getPlayground()[unit.getCoordinates().getYCoordinate()][unit.getCoordinates().getXCoordinate()] = empty;
					unit.retire();
					Control.getControl().decreasePopulation(unit.getPlayer());
					displaySelectedField(empty);
					refreshGameMap();
				});
				buttonBar.add(retireBtn);
				}
			}
			selection.add(Box.createVerticalGlue());
			selection.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(border), title));
		} else {
			if (field != null) {
				if (actionUnit != null) {
					if (action == Action.MOVE) performMove((EmptyField) field);
					if (action == Action.BUILD_FARM) buildFarm((EmptyField) field);
					if (action == Action.BUILD_BARRACKS) buildBarracks((EmptyField) field);
				}
			}
			selection.setBorder(null);
		}
		revalidate();
	}

	private void displayMainBuilding(MainBuilding mb) {
		Control theController= Control.getControl();
		buttonBar.setLayout(new GridLayout(4, 1));
		
		JButton createBuilderBtn = new JButton();
		createBuilderBtn.setLayout(new BorderLayout());
		JLabel builderLabel = new JLabel("Create Builder");
		JLabel builderCostLabel = new JLabel("Wood: 25 Meat: 25 Gold: 0");
		builderLabel.setHorizontalAlignment(JLabel.CENTER);
		builderCostLabel.setHorizontalAlignment(JLabel.CENTER);
		createBuilderBtn.add(BorderLayout.CENTER,builderLabel);
		createBuilderBtn.add(BorderLayout.SOUTH,builderCostLabel);
		
		createBuilderBtn.addActionListener(e -> {
			Coordinate free = gameBoard.getFreeCoordinateAround(mb.getCoordinates());
			Builder builder = theController.createBuilder(mb, free);
			if (builder!=null) {
				
				gameBoard.getPlayground()[free.getYCoordinate()][free.getXCoordinate()] = builder;
				refreshGameMap();
			}
		});
		createBuilderBtn.setBackground(btnColor);
		buttonBar.add(createBuilderBtn);
		
		JButton createLumberJBtn = new JButton();
		createLumberJBtn.setLayout(new BorderLayout());
		JLabel lumberLabel = new JLabel("Create Lumberjack");
		JLabel lumberCostLabel = new JLabel("Wood: 0 Meat: 25 Gold: 25");
		lumberLabel.setHorizontalAlignment(JLabel.CENTER);
		lumberCostLabel.setHorizontalAlignment(JLabel.CENTER);
		createLumberJBtn.add(BorderLayout.CENTER,lumberLabel);
		createLumberJBtn.add(BorderLayout.SOUTH,lumberCostLabel);
		
		createLumberJBtn.addActionListener(e -> {
			Coordinate free = gameBoard.getFreeCoordinateAround(mb.getCoordinates());
			Lumberjack lumberjack = theController.createLumberjack(mb, free);
			if (free != null) {
				
				gameBoard.getPlayground()[free.getYCoordinate()][free.getXCoordinate()] = lumberjack;
				refreshGameMap();
			}
		});
		createLumberJBtn.setBackground(btnColor);
		buttonBar.add(createLumberJBtn);
		
		JButton createMinerBtn = new JButton();
		createMinerBtn.setLayout(new BorderLayout());
		JLabel minerLabel = new JLabel("Create Miner");
		JLabel minerCostLabel = new JLabel("Wood: 25 Meat: 0 Gold: 25");
		minerLabel.setHorizontalAlignment(JLabel.CENTER);
		minerCostLabel.setHorizontalAlignment(JLabel.CENTER);
		createMinerBtn.add(BorderLayout.CENTER,minerLabel);
		createMinerBtn.add(BorderLayout.SOUTH,minerCostLabel);
		
		createMinerBtn.addActionListener(e -> {
			Coordinate free = gameBoard.getFreeCoordinateAround(mb.getCoordinates());
			Miner miner = theController.createMiner(mb, free);
			if (free != null) {
				
				gameBoard.getPlayground()[free.getYCoordinate()][free.getXCoordinate()] = miner;
				refreshGameMap();
			}
		});
		createMinerBtn.setBackground(btnColor);
		buttonBar.add(createMinerBtn);
		
		JButton createFarmerBtn = new JButton();
		createFarmerBtn.setLayout(new BorderLayout());
		JLabel farmerLabel = new JLabel("Create Farmer");
		JLabel farmerCostLabel = new JLabel("Wood: 15 Meat: 15 Gold: 15");
		farmerLabel.setHorizontalAlignment(JLabel.CENTER);
		farmerCostLabel.setHorizontalAlignment(JLabel.CENTER);
		createFarmerBtn.add(BorderLayout.CENTER,farmerLabel);
		createFarmerBtn.add(BorderLayout.SOUTH,farmerCostLabel);
		
		createFarmerBtn.addActionListener(e -> {
			Coordinate free = gameBoard.getFreeCoordinateAround(mb.getCoordinates());
			Farmer farmer = theController.createFarmer(mb, free);
			if (free != null) {
				
				gameBoard.getPlayground()[free.getYCoordinate()][free.getXCoordinate()] = farmer;
				refreshGameMap();
			}
		});
		createFarmerBtn.setBackground(btnColor);
		buttonBar.add(createFarmerBtn);
		
		buttonBar.setVisible(hasTurn == mb.getPlayer());
	}
	
	private void displayBarrackActions(Barrack barrack) {
		Control theController= Control.getControl();
		buttonBar.setLayout(new GridLayout(1, 1));
		
		JButton createWarriorBtn = new JButton();
		createWarriorBtn.setLayout(new BorderLayout());
		JLabel warriorLabel = new JLabel("Create Warrior");
		JLabel warriorCostLabel = new JLabel("Wood: 25 Meat: 25 Gold: 25");
		warriorLabel.setHorizontalAlignment(JLabel.CENTER);
		warriorCostLabel.setHorizontalAlignment(JLabel.CENTER);
		createWarriorBtn.add(BorderLayout.CENTER,warriorLabel);
		createWarriorBtn.add(BorderLayout.SOUTH,warriorCostLabel);
		
		createWarriorBtn.setBackground(btnColor);
		buttonBar.add(createWarriorBtn);
		createWarriorBtn.addActionListener(e -> {
			Coordinate free = gameBoard.getFreeCoordinateAround(barrack.getCoordinates());
			Warrior warrior = theController.createWarrior(barrack, free);
			if (free != null) {
				
				gameBoard.getPlayground()[free.getYCoordinate()][free.getXCoordinate()] = warrior;
				refreshGameMap();
			}
		});
		buttonBar.setVisible(barrack.getPlayer() == hasTurn);
	}
	
	private void displayBuilderActions(Builder builder) {
		buttonBar.setLayout(new GridLayout(5, 1));
		
		JButton farmBtn = new JButton();
		farmBtn.setLayout(new BorderLayout());
		JLabel farmLabel = new JLabel("Build a Farm");
		JLabel farmCostLabel = new JLabel("Wood: 10 Meat: 10 Gold: 10");
		farmLabel.setHorizontalAlignment(JLabel.CENTER);
		farmCostLabel.setHorizontalAlignment(JLabel.CENTER);
		farmBtn.add(BorderLayout.CENTER,farmLabel);
		farmBtn.add(BorderLayout.SOUTH,farmCostLabel);
		
		farmBtn.addActionListener(e -> {
			actionUnit = builder;
			action = Action.BUILD_FARM;
		});
		farmBtn.setBackground(btnColor);
		buttonBar.add(farmBtn);
		
		JButton barracksBtn = new JButton();
		barracksBtn.setLayout(new BorderLayout());
		JLabel barracksLabel = new JLabel("Build Barracks");
		JLabel barracksCostLabel = new JLabel("Wood: 15 Meat: 15 Gold: 15");
		barracksLabel.setHorizontalAlignment(JLabel.CENTER);
		barracksCostLabel.setHorizontalAlignment(JLabel.CENTER);
		barracksBtn.add(BorderLayout.CENTER,barracksLabel);
		barracksBtn.add(BorderLayout.SOUTH,barracksCostLabel);
		
		barracksBtn.addActionListener(e -> {
			actionUnit = builder;
			action = Action.BUILD_BARRACKS;
		});
		barracksBtn.setBackground(btnColor);
		buttonBar.add(barracksBtn);
		JButton repairBtn = new JButton("Repair Damage");
		repairBtn.addActionListener(e -> {
			actionUnit = builder;
			action = Action.REPAIR_BUILDING;
		});
		repairBtn.setBackground(btnColor);
		buttonBar.add(repairBtn);
		buttonBar.setVisible(builder.getPlayer() == hasTurn);
	}
	
	private void displayLumberjackActions(Lumberjack lumberjack) {
		buttonBar.setLayout(new GridLayout(3, 1));
		JButton cutWoodBtn = new JButton("Cut Wood");
		cutWoodBtn.addActionListener(e -> {
			actionUnit = lumberjack;
			action = Action.CUT_WOOD;
		});
		cutWoodBtn.setBackground(btnColor);
		buttonBar.add(cutWoodBtn);
		buttonBar.setVisible(lumberjack.getPlayer() == hasTurn);
	}
	
	private void displayMinerActions(Miner miner) {
		buttonBar.setLayout(new GridLayout(3, 1));
		JButton mineGoldBtn = new JButton("Mine Gold");
		mineGoldBtn.addActionListener(e -> {
			actionUnit = miner;
			action = Action.MINE_GOLD;
		});
		mineGoldBtn.setBackground(btnColor);
		buttonBar.add(mineGoldBtn);
		buttonBar.setVisible(miner.getPlayer() == hasTurn);
	}

	private void displayFarmerActions(Farmer farmer) {
		buttonBar.setLayout(new GridLayout(3, 1));
		JButton farmFoodBtn = new JButton("Farm Food");
		farmFoodBtn.addActionListener(e -> {
			actionUnit = farmer;
			action = Action.FARM_FOOD;
		});
		farmFoodBtn.setBackground(btnColor);
		buttonBar.add(farmFoodBtn);
		buttonBar.setVisible(farmer.getPlayer() == hasTurn);
	}
	
	private void displayWarriorActions(Warrior warrior) {
		buttonBar.setLayout(new GridLayout(3, 1));
		JButton attackBtn = new JButton("Attack");
		attackBtn.addActionListener(e -> {
			actionUnit = warrior;
			action = Action.ATTACK;
		});
		attackBtn.setBackground(btnColor);
		buttonBar.add(attackBtn);
		buttonBar.setVisible(warrior.getPlayer() == hasTurn);
	}
	
	private void performMove(EmptyField toMove) {
        Control theController = Control.getControl();
        if (gameBoard.getNearbyFreeCoordinates(actionUnit.getCoordinates()).stream()
                .anyMatch(c -> c.getXCoordinate() == toMove.getX() && c.getYCoordinate() == toMove.getY())
                && theController.hasAction(actionUnit)) {
            gameBoard.getPlayground()[toMove.getY()][toMove.getX()] = actionUnit;
            gameBoard.getPlayground()[actionUnit.getCoordinates().getYCoordinate()][actionUnit.getCoordinates().getXCoordinate()]
                    = new EmptyField(actionUnit.getCoordinates().getXCoordinate(), actionUnit.getCoordinates().getYCoordinate());
            theController.move(actionUnit, toMove.getX(), toMove.getY());
            refreshGameMap();
        }
        action = Action.NONE;
        displaySelectedField(actionUnit);
    }
	
	private void buildBarracks(EmptyField toBuildOn) {
		Control theController= Control.getControl();
		action = Action.NONE;
		if (gameBoard.getNearbyFreeCoordinates(actionUnit.getCoordinates()).stream()
				.anyMatch(c -> c.getXCoordinate() == toBuildOn.getX() && c.getYCoordinate() == toBuildOn.getY())) {
			Coordinate buildCoordinate = new Coordinate (toBuildOn.getX(), toBuildOn.getY());
			Barrack barrack=(Barrack)theController.buildBuilding((Builder)actionUnit, buildCoordinate, BuildingType.Barrack);
			gameBoard.getPlayground()[toBuildOn.getY()][toBuildOn.getX()] = barrack;
			refreshGameMap();
			actionUnit = null;
		} else {
			actionUnit = null;
		}
	}
	
	private void buildFarm(EmptyField toBuildOn) {
		Control theController= Control.getControl();
		action = Action.NONE;
		if (gameBoard.getNearbyFreeCoordinates(actionUnit.getCoordinates()).stream()
				.anyMatch(c -> c.getXCoordinate() == toBuildOn.getX() && c.getYCoordinate() == toBuildOn.getY())) {
			Coordinate buildCoordinate = new Coordinate (toBuildOn.getX(), toBuildOn.getY());
			Farm farm=(Farm)theController.buildBuilding((Builder)actionUnit, buildCoordinate,BuildingType.Farm);
			gameBoard.getPlayground()[toBuildOn.getY()][toBuildOn.getX()] = farm;
			refreshGameMap();
			actionUnit = null;
			//displaySelectedField(farm);
		} else {
			actionUnit = null;
		}
	}

	private void refreshGameMap() {
		Main.getGameMapPanel().setVisible(false);
		Main.getGameMapPanel().setVisible(true);
	}
	
	private enum Action { NONE, MOVE, BUILD_FARM, BUILD_BARRACKS, REPAIR_BUILDING, CUT_WOOD, FARM_FOOD, MINE_GOLD, ATTACK; }
	
	public void addEndTurnListener(ActionListener listenForEndTurnBtn) {
		endTurnBtn.addActionListener(listenForEndTurnBtn);
	}

	
	public void setWhoseTurn(Player whoseTurn) {
		if (whoseTurn==Player.First){

			turnLabel.setText("Red Turn");
			hasTurn = Player.First;
			
		}else {

			turnLabel.setText("Blue Turn");
			hasTurn = Player.Second;
			
		}
		actionUnit = null;
		action = Action.NONE;
		displaySelectedField(null);
	}
	public void setPlayerFunds(Player whoseTurn,Funds amount) {
		
		String woodAmount = Integer.toString(amount.getWoodAmount()); 
		String meatAmount = Integer.toString(amount.getMeatAmount());
		String goldAmount = Integer.toString(amount.getGoldAmount()); 
		
		
		if (whoseTurn==Player.First){
			
			rWoodAmountLabel.setText("Wood: "+woodAmount);
			rMeatAmountLabel.setText("Meat: "+meatAmount);
			rGoldAmountLabel.setText("Gold: "+goldAmount);
		}else {

			bWoodAmountLabel.setText("Wood: "+woodAmount);
			bMeatAmountLabel.setText("Meat: "+meatAmount);
			bGoldAmountLabel.setText("Gold: "+goldAmount);
		}
	}
	
	public void updateUnitStats(Player whose, int count) {
		if (whose == Player.First) rUnitAmountLabel.setText("Pop: " + count);
		else bUnitAmountLabel.setText("Pop: " + count);
		revalidate();
	}

	
}
