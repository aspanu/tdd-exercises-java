package roulette;

import java.util.List;

import org.junit.*;

import static org.junit.Assert.*;

public class RouletteTest {

    Player p;
    RouletteTable rt;

    @Before
    public void setUp() {
        p = new Player();
        rt = new RouletteTable(new RandomProviderStub(), 10000);
    }

    @Test
    public void player_can_place_bets_on_number_fields() throws TooManyChipsException, TableFullException {
        rt.placeBet(p, Field.forNumber(17), 200);
        assertEquals(new Bet(p, 200), rt.betsByField(Field.forNumber(17)).get(0));
    }

    @Test
    public void player_can_place_bets_on_different_fields_and_with_different_values()
        throws TooManyChipsException, TableFullException {
        rt.placeBet(p, Field.forNumber(17), 200);
        rt.placeBet(p, Field.forNumber(2), 600);
        rt.placeBet(p, Field.forNumber(30), 1000);
        assertEquals(new Bet(p, 200), rt.betsByField(Field.forNumber(17)).get(0));
        assertEquals(new Bet(p, 600), rt.betsByField(Field.forNumber(2)).get(0));
        assertEquals(new Bet(p, 1000), rt.betsByField(Field.forNumber(30)).get(0));
    }

    @Test
    public void Total_number_of_chips_is_limited_by_the_table_and_the_table_can_refuse_a_bet_if_there_are_too_many_chips()
        throws TooManyChipsException, TableFullException {
        RouletteTable rt = new RouletteTable(new RandomProviderStub(), 300);
        Player p = new Player();
        rt.placeBet(p, Field.forNumber(17), 200);
        boolean exceptionThrown = false;
        try {
            rt.placeBet(new Player(), Field.forNumber(20), 200);
        } catch (TooManyChipsException e) {
            exceptionThrown = true;
        }
        // the following should be true
        assertEquals(0, rt.betsByField(Field.forNumber(20)).size());
        assertTrue(exceptionThrown);
    }

    @Test(expected = TableFullException.class)
    public void up_to_eight_players_can_join_a_game() throws TooManyChipsException, TableFullException {
        Player[] players = new Player[8];
        for (int i = 0; i < 8; i++) {
            players[i] = new Player();
            rt.placeBet(players[i], Field.forNumber(20), 100);
        }
        Player nine = new Player();
        rt.placeBet(nine, Field.forNumber(20), 100);
    }

    @Test
    public void each_player_is_assigned_a_different_colour() throws TooManyChipsException, TableFullException {
        Player[] players = new Player[8];
        for (int i = 0; i < 8; i++) {
            players[i] = new Player();
            rt.placeBet(players[i], Field.forNumber(17), 100);
        }
        for (int i = 0; i < players.length - 1; i++)
            for (int j = i + 1; j < players.length; j++)
                assertTrue(i + ":" + rt.getColour(players[i]) + " == " + j + ":" + rt.getColour(players[j]),
                           rt.getColour(players[i]) != rt.getColour(players[j]));
    }

    @Test
    public void player_can_place_bet_on_ODD_and_EVEN() throws TooManyChipsException, TableFullException {
        rt.placeBet(p, Field.ODD, 10);
        assertEquals(new Bet(p, 10), rt.betsByField(Field.ODD).get(0));
    }

    @Test
    public void player_can_place_split_bets_which_cover_both_fields() throws TooManyChipsException, TableFullException {
        rt.placeBet(p, new Field[]{Field.forNumber(1), Field.forNumber(2)}, 10);
        assertEquals(new Bet(p, 10, BetType.SPLIT), rt.betsByField(Field.forNumber(1)).get(0));
        assertEquals(new Bet(p, 10, BetType.SPLIT), rt.betsByField(Field.forNumber(2)).get(0));
    }

    @Test
    public void rollBall_easy() throws TooManyChipsException, TableFullException {
        Player p = new Player();
        rt.placeBet(p, Field.forNumber(17), 100);
        rt.finishRound(p);

        int ballStopLocation = rt.getLastRoll();
        assertTrue("Picked location should be bigger or eg to 0", ballStopLocation >= 0);
        assertTrue(ballStopLocation <= 36);
    }

    @Test
    public void rollBall_hard() throws TooManyChipsException, TableFullException {
        Player[] players = new Player[8];
        for (int i = 0; i < 8; i++) {
            Player p = new Player();
            rt.placeBet(p, Field.forNumber(17), 100);
        }

        for (int i = 0; i < 8; i++) {
            rt.finishRound(p);
        }

        int ballStopLocation = rt.getLastRoll();
        assertTrue("Picked location should be bigger or eg to 0", ballStopLocation >= 0);
        assertTrue("Picked location should be smaller than 37", ballStopLocation <= 36);
    }

    @Test
    public void rollBall_lastChance() throws TooManyChipsException, TableFullException, InterruptedException {

        Player p = new Player();
        rt.placeBet(p, Field.forNumber(17), 100);
        rt.finishRound(p);

//        rt.setCountdownForwardBy(5);

        rt.placeBet(p, Field.forNumber(18), 100);

        List<Bet> b = rt.betsByField(Field.forNumber(18));

        assertTrue("ONly one bet placed", b.size() == 1);
        assertTrue(b.get(0).equals(new Bet(p, 100, BetType.SINGLE)));
    }

//    @Test
//    public void rollBall_OutsideOfTime() throws TooManyChipsException, TableFullException, InterruptedException {
//	    Player p = new Player();
//	    rt.placeBet(p, Field.forNumber(10), 100);
//	    rt.finishRound(p);
//
////	    rt.setCountdownForwardBy(11);
//
//	    rt.placeBet(p, Field.forNumber(11), 150);
//
//	    List<Bet> b = rt.betsByField(Field.forNumber(11));
//
//	    assertTrue("Bet should not be placed.", b.isEmpty());
//    }

    @Test
    public void systemRollsBallRandomly()
        throws TooManyChipsException, TableFullException {

        RandomProviderStub rp = new RandomProviderStub();
        rp.setNextRandom(19);
        rt = new RouletteTable(rp);

        Player p = new Player();
        rt.placeBet(p, Field.forNumber(10), 100);

        rt.finishRound(p);
        assertEquals(19, rt.getLastRoll());
    }

    @Test
    public void doISuckAgain() throws TooManyChipsException, TableFullException {
        RandomProviderStub rp = new RandomProviderStub();
        rp.setNextRandom(18);
        rt = new RouletteTable(rp);

        Player p = new Player();
        rt.placeBet(p, Field.forNumber(10), 100);

        rt.finishRound(p);
        assertEquals(18, rt.getLastRoll());

        assertEquals(0, rp.getLow());
        assertEquals(36, rp.getHigh());
    }


    @Test
    public void testPlacingBetWindow() throws TooManyChipsException, TableFullException {
        Player p = new Player();
        rt.placeBet(p, Field.forNumber(10), 100);
        rt.finishRound(p);
//
//        TimerProviderStub tp = new TimerProviderStub();
//        rt.setTimer(tp);
//        tp.setTimeSinceStart(5);

        Player p = new Player();
        rt.placeBet(p, Field.forNumber(18), 100);

        List<Bet> b = rt.betsByField(Field.forNumber(18));
        assertTrue("ONly one bet placed", b.size() == 1);
        assertTrue(b.get(0).equals(new Bet(p, 100, BetType.SINGLE)));

    }

}

