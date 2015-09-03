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
	@Test
	public void RED_has_a_red_black_winning_strategy(){
		assertEquals(new RedBlackWinningStrategy(true), Field.RED.winningStrategy());
	}
	@Test
	public void BLACK_has_a_red_black_winning_strategy(){
		assertEquals(new RedBlackWinningStrategy(false), Field.BLACK.winningStrategy());
	}
	@Test
	public void ONE_EIGHTEEN_has_a_half_winning_strategy(){
		assertEquals(new HalfWinningStrategy(1), Field.ONE_EIGHTEEN.winningStrategy());
	}
	@Test
	public void NINETEEN_TIRTYSIX_has_a_half_winning_strategy(){
		assertEquals(new HalfWinningStrategy(2), Field.NINETEEN_THIRTYSIX.winningStrategy());
	}
	@Test
	public void ONE_TWELVE_has_a_third_winning_strategy(){
		assertEquals(new ThirdWinningStrategy(1), Field.ONE_TWELVE.winningStrategy());
	}
	@Test
	public void THIRTEEN_TWENTYFOUR_has_a_third_winning_strategy(){
		assertEquals(new ThirdWinningStrategy(2), Field.THIRTEEN_TWENTYFOUR.winningStrategy());
	}
	@Test
	public void TWENTYFIVE_THIRTYSIX_has_a_third_winning_strategy(){
		assertEquals(new ThirdWinningStrategy(3), Field.TWENTYFIVE_THIRTYSIX.winningStrategy());
	}
}
