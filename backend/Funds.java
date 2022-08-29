package backend;

public class Funds {
	int woodAmount;
	int meatAmount;
	int goldAmount;
	
	
	
	public Funds(int woodAmount, int meatAmount, int goldAmount) {
		
		this.woodAmount=woodAmount;
		this.meatAmount=meatAmount;
		this.goldAmount=goldAmount;
	}
	
	public int getWoodAmount() {
		return woodAmount;
	}
	public int getMeatAmount() {
		return meatAmount;
	}
	public int getGoldAmount() {
		return goldAmount;
	}
	
	public void setFunds(Funds amount) {

		 int currentWoodAmount = this.woodAmount+amount.woodAmount;
		 setCurrentWodAmount(currentWoodAmount);
		
		 int currentMeatAmount = this.meatAmount+amount.meatAmount;
		 setCurrentMeatAmount(currentMeatAmount);
		 
		 int currentGoldAmount = this.goldAmount+amount.goldAmount;
		 setCurrentGoldAmount(currentGoldAmount);
		}

	public void setCurrentWodAmount(int amount) {
		this.woodAmount= (amount < 0) ? 0: amount;
	}
	
	public void setCurrentMeatAmount(int amount) {
		this.meatAmount= (amount < 0) ? 0: amount;
	}
	
	public void setCurrentGoldAmount(int amount) {
		this.goldAmount= (amount < 0) ? 0: amount;
	}
	
	

}
