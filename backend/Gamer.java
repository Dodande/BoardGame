package backend;

public class Gamer {

	//Attributes
	
	static final int MAXIMUM_UNITS=30;
	int unitAmount;
	int maximumUnits;
	Funds gamerFunds;
	boolean isRoundOver;
	
	public Gamer(){
		this.gamerFunds= new Funds(100,100,100);
		this.maximumUnits=MAXIMUM_UNITS;
		this.unitAmount=0;
	}	
	//needs implementation
	
	/*public static void finishRound() {
		Turn.nextTurn();
		}
	
	public boolean unitCreatingEnabler() {
		boolean x;
		if (this.unitAmount < this.maximumUnits) {
			x=true;
			unitCounterPlus();
		}
		else {
			x=false;
		}
		return x;
	}
	
	public void unitCounterPlus() {
			this.unitAmount += 1;
	}
	
	public void unitCounterMinus() {
		this.unitAmount -= 1;
	}*/

	
	
	
	//Getters
	
	public Funds getGamerFunds() {
		return gamerFunds;
	}
	/*
	public int getCurrentUnitAmount() {
		return unitAmount;
	}*/
	//Setters

	public void setGamerFunds(Funds amount) {
		
		gamerFunds.setFunds(amount);
	}
	
	public boolean hasEnoughFunds(Funds amount) {
		return(	gamerFunds.woodAmount+amount.woodAmount>=0 &&
				gamerFunds.meatAmount+amount.meatAmount>=0 &&
				gamerFunds.goldAmount+amount.goldAmount>=0);
	}
}
