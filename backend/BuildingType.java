package backend;


public enum BuildingType {
	Barrack (new Funds (-15,-15,-15)),
	Farm (new Funds (-10,-10,-10));
	Funds funds;
	private BuildingType(Funds funds) {
		this.funds = funds;
	}
	
	public Funds getGetFunds() {
		return this.funds;
	}
	
	
}
