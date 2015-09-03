package roulette;

import static org.junit.Assert.*;

import org.junit.Test;
public class SingleNumberWinningStrategyTest {
	@Test
	public void wins_only_on_a_particular_single_number(){
		SingleNumberWinningStrategy strategy=new SingleNumberWinningStrategy(2);
		assertFalse(strategy.winsOn(0));
		assertFalse(strategy.winsOn(1));
		assertTrue(strategy.winsOn(2));
		for (int i=3; i<37; i++)
			assertFalse(strategy.winsOn(i));
	}
}
