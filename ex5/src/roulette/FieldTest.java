package roulette;

import static org.junit.Assert.*;

import org.junit.Test;
public class FieldTest {

	@Test
	public void forNumber_returns_field_with_that_number_and_single_number_winning_strategy(){
		Field f=Field.forNumber(1);
		assertEquals("1", f.getFieldName());
		assertEquals(new SingleNumberWinningStrategy(1), f.winningStrategy());
	}
	@Test
	public void ODD_has_an_odd_even_winning_strategy(){
		assertEquals(new OddEvenWinningStrategy(true), Field.ODD.winningStrategy());
	}
	@Test
	public void EVEN_has_an_odd_even_winning_strategy(){
		assertEquals(new OddEvenWinningStrategy(false), Field.EVEN.winningStrategy());
	}
}
