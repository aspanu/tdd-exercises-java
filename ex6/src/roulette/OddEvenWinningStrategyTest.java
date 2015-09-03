package roulette;

import org.junit.Test;
import static org.junit.Assert.*;
public class OddEvenWinningStrategyTest {
	@Test
	public void wins_on_even_if_odd_was_false(){
		OddEvenWinningStrategy strategy=new OddEvenWinningStrategy(false);
		for (int i=1; i<36; i+=2){
			assertTrue(strategy.winsOn(i+1));
			assertFalse(strategy.winsOn(i));
		}
	}
	@Test
	public void wins_on_odd_if_odd_was_true(){
		OddEvenWinningStrategy strategy=new OddEvenWinningStrategy(true);
		for (int i=1; i<36; i+=2){
			assertTrue(strategy.winsOn(i));
			assertFalse(strategy.winsOn(i+1));
		}
	}
	@Test
	public void loses_on_zero_regardless_of_odd(){
		assertFalse(new OddEvenWinningStrategy(true).winsOn(0));
		assertFalse(new OddEvenWinningStrategy(false).winsOn(0));
	}
}
