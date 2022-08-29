package backend;

import field.Player;

public class Turn {
	private int turnCounter=0;
	private Player whoseTurn=Player.First;

	public void nextTurn() {
		++turnCounter;	
		whoseTurn=(turnCounter % 2 ==0 )? Player.First : Player.Second;
	}
	
	public Player getWhoseTurn() {
		return whoseTurn;
	}
	
}