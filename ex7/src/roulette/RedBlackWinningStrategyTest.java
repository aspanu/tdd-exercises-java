package roulette;

import org.junit.Test;

import static org.junit.Assert.*;
public class RedBlackWinningStrategyTest {
	int redNumbers[]=new int[]{1,3,5,7,9,12,14,16,18,19,21,23,25,27,30,32,34,36};
	int blackNumbers[]=new int[]{2,4,6,8,10,11,13,15,17,20,22,24,26,28,29,31,33,35};
	@Test
	public void red_wins_on_red_numbers(){
		for (int i:redNumbers){
			assertTrue(new RedBlackWinningStrategy(true).winsOn(i));
			assertFalse(new RedBlackWinningStrategy(false).winsOn(i));
		}
	}
	@Test
	public void both_red_and_black_lose_on_zero(){
		assertFalse(new RedBlackWinningStrategy(true).winsOn(0));
		assertFalse(new RedBlackWinningStrategy(false).winsOn(0));
	}
	@Test
	public void black_wins_on_black_numbers(){
		for (int i:blackNumbers){
			assertFalse(""+i,new RedBlackWinningStrategy(true).winsOn(i));
			assertTrue(""+i,new RedBlackWinningStrategy(false).winsOn(i));
		}
		
	}
}
