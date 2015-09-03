package roulette;

import org.junit.Test;

import static org.junit.Assert.*;
public class ThirdWinningStrategyTest {
	@Test
	public void low_wins_on_low_numbers(){
		for (int i=1;i<13; i++){
			assertTrue(new ThirdWinningStrategy(1).winsOn(i));
			assertFalse(new ThirdWinningStrategy(2).winsOn(i));
			assertFalse(new ThirdWinningStrategy(3).winsOn(i));
		}
	}
	@Test
	public void mid_wins_on_mid_numbers(){
		for (int i=13;i<25; i++){
			assertFalse(new ThirdWinningStrategy(1).winsOn(i));
			assertTrue(new ThirdWinningStrategy(2).winsOn(i));
			assertFalse(new ThirdWinningStrategy(3).winsOn(i));
		}
	}
	@Test
	public void high_wins_on_high_numbers(){
		for (int i=25;i<36; i++){
			assertFalse(new ThirdWinningStrategy(1).winsOn(i));
			assertFalse(new ThirdWinningStrategy(2).winsOn(i));
			assertTrue(new ThirdWinningStrategy(3).winsOn(i));
		}
	}
	@Test
	public void all_three_thirds_lose_on_zero(){
		assertFalse(new ThirdWinningStrategy(1).winsOn(0));
		assertFalse(new ThirdWinningStrategy(2).winsOn(0));
		assertFalse(new ThirdWinningStrategy(3).winsOn(0));
	}
}
