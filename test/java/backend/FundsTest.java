package test.java.backend;

import static org.junit.Assert.*;

import org.junit.Test;

import backend.Funds;

public class FundsTest {

	@Test
	public void test() {
		Funds Fund1 = new Funds(10,10,10);
		Funds Fund2 = new Funds(0,0,0);
		
		assertEquals(10,Fund1.getGoldAmount());
		assertEquals(10,Fund1.getMeatAmount());
		assertEquals(10,Fund1.getWoodAmount());
		
		Fund2.setFunds(Fund1);
		
		assertEquals(10,Fund2.getGoldAmount());
		assertEquals(10,Fund2.getMeatAmount());
		assertEquals(10,Fund2.getWoodAmount());
		
		Fund1.setCurrentGoldAmount(20);
		Fund1.setCurrentMeatAmount(20);
		Fund1.setCurrentWodAmount(20);
		
		assertEquals(20,Fund1.getGoldAmount());
		assertEquals(20,Fund1.getMeatAmount());
		assertEquals(20,Fund1.getWoodAmount());
		
		Fund1.setCurrentGoldAmount(-5);
		Fund1.setCurrentMeatAmount(-5);
		Fund1.setCurrentWodAmount(-5);
		
		assertEquals(0,Fund1.getGoldAmount());
		assertEquals(0,Fund1.getMeatAmount());
		assertEquals(0,Fund1.getWoodAmount());
		
		
		
	}

}
