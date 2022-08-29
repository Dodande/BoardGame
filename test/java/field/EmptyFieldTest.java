package test.java.field;

import static org.junit.Assert.*;

import org.junit.Test;

import field.EmptyField;

public class EmptyFieldTest {
	EmptyField ef = new EmptyField(3,4);

	@Test
	public void testCreation() {
		assertEquals(3,ef.getX());
		assertEquals(4,ef.getY());		
	}
	
	@Test
	public void testGetCoordinate() {
		assertEquals(3,ef.getCoordinates().getXCoordinate());
		assertEquals(4,ef.getCoordinates().getYCoordinate());
	}
	
	@Test
	public void testPositionChange() {
		ef.setPosition(5, 6);
		assertEquals(5,ef.getX());
		assertEquals(6,ef.getY());
	}
	

}
