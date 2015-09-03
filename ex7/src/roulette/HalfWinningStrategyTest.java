package roulette;

import org.junit.Test;

import static org.junit.Assert.*;
public class HalfWinningStrategyTest {
	@Test
	public void lower_wins_on_low_numbers(){
		for (int i=1;i<19; i++){
			assertTrue(""+i,new HalfWinningStrategy(1).winsOn(i));
			assertFalse(""+i,new HalfWinningStrategy(2).winsOn(i));
		}
	}
	@Test
	public void both_upper_and_lower_lose_on_zero(){
		assertFalse(new HalfWinningStrategy(1).winsOn(0));
		assertFalse(new HalfWinningStrategy(2).winsOn(0));
	}
	@Test
	public void higher_wins_on_high_numbers(){
		for (int i=19;i<37;i++){
			assertFalse(""+i,new HalfWinningStrategy(1).winsOn(i));
			assertTrue(""+i,new HalfWinningStrategy(2).winsOn(i));
		}
	}
}
