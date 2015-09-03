package roulette;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
public class RouletteTest {

	Player p;
	RouletteTable rt;
	@Before
	public void setUp(){
		p=new Player();
		rt=new RouletteTable(10000);
	}
	@Test
	public void player_can_place_bets_on_number_fields() throws TooManyChipsException, TableFullException{
		rt.placeBet(p,17,200);
		assertEquals(new Bet(p,200),rt.betsByField(17).get(0));
	}
	@Test
	public void player_can_place_bets_on_different_fields_and_with_different_values() throws TooManyChipsException, TableFullException{
		rt.placeBet(p,17,200);
		rt.placeBet(p,2,600);
		rt.placeBet(p,30,1000);
		assertEquals(new Bet(p,200), rt.betsByField(17).get(0));
		assertEquals(new Bet(p,600),rt.betsByField(2).get(0));
		assertEquals(new Bet(p,1000), rt.betsByField(30).get(0));
	}
	@Test
	public void Total_number_of_chips_is_limited_by_the_table_and_the_table_can_refuse_a_bet_if_there_are_too_many_chips() throws TooManyChipsException, TableFullException{ 
		RouletteTable rt=new RouletteTable(300);
		Player p=new Player();
		rt.placeBet(p,17,200);
		boolean exceptionThrown=false;
		try{
		 rt.placeBet(new Player(), 20,200);
		}
		catch(TooManyChipsException e){
		 exceptionThrown=true;
		}
		// the following should be true
		assertEquals(0,rt.betsByField(20).size());
		assertTrue(exceptionThrown);
	}
	@Test (expected=TableFullException.class)
	public void up_to_eight_players_can_join_a_game() throws TooManyChipsException, TableFullException{
			Player[] players=new Player[8];
			for (int i=0; i<8; i++){
				players[i]=new Player();
				rt.placeBet(players[i],20,100);
			}
			Player nine=new Player();
			rt.placeBet(nine,20,100);	
	}
	@Test
	public void each_player_is_assigned_a_different_colour() throws TooManyChipsException, TableFullException{
		Player[] players=new Player[8];
		for (int i=0; i<8; i++){
			players[i]=new Player();
			rt.placeBet(players[i],20,100);
		}
		for (int i=0; i<players.length-1; i++)
			 for (int j=i+1; j<players.length; j++)
				assertTrue(rt.getColour(players[i])!=rt.getColour(players[j]));
	}
}

