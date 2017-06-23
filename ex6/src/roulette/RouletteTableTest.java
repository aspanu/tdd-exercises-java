package roulette;

import java.util.*;

import org.junit.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RouletteTableTest {

    Player p;
    RouletteTable rt;
    RandomNumberGenerator rng;
    TestTimer timer;

    @Before
    public void setUp() {
        p = new Player();
        rng = mock(RandomNumberGenerator.class);
        timer = new TestTimer();
        rt = new RouletteTable(10000, rng, timer);
    }

    @Test
    public void player_can_place_bets_on_number_fields() throws Exception {
        rt.placeBet(p, Field.forNumber(17), 200);
        assertEquals(new Bet(p, 200), rt.betsByField(Field.forNumber(17)).get(0));
    }

    @Test
    public void player_can_place_bets_on_different_fields_and_with_different_values() throws Exception {
        rt.placeBet(p, Field.forNumber(17), 200);
        rt.placeBet(p, Field.forNumber(2), 600);
        rt.placeBet(p, Field.forNumber(30), 1000);
        assertEquals(new Bet(p, 200), rt.betsByField(Field.forNumber(17)).get(0));
        assertEquals(new Bet(p, 600), rt.betsByField(Field.forNumber(2)).get(0));
        assertEquals(new Bet(p, 1000), rt.betsByField(Field.forNumber(30)).get(0));
    }

    @Test
    public void Total_number_of_chips_is_limited_by_the_table_and_the_table_can_refuse_a_bet_if_there_are_too_many_chips()
        throws Exception {
        RouletteTable rt = new RouletteTable(300, rng, timer);
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
    public void up_to_eight_players_can_join_a_game() throws Exception {
        Player[] players = new Player[8];
        for (int i = 0; i < 8; i++) {
            players[i] = new Player();
            rt.placeBet(players[i], Field.forNumber(20), 100);
        }
        Player nine = new Player();
        rt.placeBet(nine, Field.forNumber(20), 100);
    }

    @Test
    public void each_player_is_assigned_a_different_colour() throws Exception {
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
    public void player_can_place_bet_on_ODD_and_EVEN() throws Exception {
        rt.placeBet(p, Field.ODD, 10);
        assertEquals(new Bet(p, 10), rt.betsByField(Field.ODD).get(0));
    }

    @Test
    public void player_can_place_split_bets_which_cover_both_fields() throws Exception {
        rt.placeBet(p, new Field[]{Field.forNumber(1), Field.forNumber(2)}, 10);
        assertEquals(new Bet(p, 10, BetType.SPLIT), rt.betsByField(Field.forNumber(1)).get(0));
        assertEquals(new Bet(p, 10, BetType.SPLIT), rt.betsByField(Field.forNumber(2)).get(0));
    }

    @Test
    public void ball_starts_rolling_when_all_players_signal_done() throws Exception {
        //arrange
        Player p2 = new Player();
        Player p3 = new Player();
        rt.placeBet(p, Field.forNumber(1), 10);
        rt.placeBet(p2, Field.forNumber(2), 10);
        rt.placeBet(p3, Field.forNumber(3), 10);
        //act
        rt.done(p);
        rt.done(p2);
        rt.done(p3);
        assertTrue(rt.isBallRolling());
    }

    @Test
    public void ball_rolls_for_a_random_time_between_30_and_40_seconds() throws Exception {
        when(rng.generate(30, 40)).thenReturn(33);
        rt.roll();
        timer.moveTime(33001);
        assertFalse(rt.isBallRolling());
    }

    @Test
    public void game_gets_a_random_outcome_when_the_ball_stops_rolling() {
        //act
        when(rng.generate(0, 36)).thenReturn(13);
        rt.stopRolling();
        //assert
        assertEquals(13, rt.getBallPosition());
    }

    @Test
    public void winning_bets_contain_all_bets_with_field_winning_strategy_covering_ball_position() throws Exception {
        //arrange
        Player p2 = new Player();
        Player p3 = new Player();

        WinningStrategy loses = mock(WinningStrategy.class);
        when(loses.winsOn(anyInt())).thenReturn(false);

        WinningStrategy wins = mock(WinningStrategy.class);
        when(wins.winsOn(anyInt())).thenReturn(true);

        rt.placeBet(p, new Field("A", loses), 1);
        rt.placeBet(p2, new Field("B", wins), 2);
        rt.placeBet(p3, new Field("C", loses), 3);
        //act
        rt.stopRolling();
        //assert
        Set<Bet> winningBets = new HashSet<Bet>();
        winningBets.add(new Bet(p2, 2));
        assertEquals(winningBets, rt.getWinningBets());
    }

    @Test
    public void players_can_place_bets_when_the_ball_starts_rolling_up_to_10_seconds() throws Exception {
        rt.roll();
        timer.moveTime(5000);
        rt.placeBet(p, Field.forNumber(1), 1);
    }

    @Test(expected = NoMoreBetsException.class)
    public void players_cannot_place_bets_when_the_ball_starts_rolling_after_10_seconds() throws Exception {
        when(rng.generate(30, 40)).thenReturn(33);
        rt.roll();
        timer.moveTime(10001);
        rt.placeBet(p, Field.forNumber(1), 1);
    }

    @Test
    public void testChipsAreBeingDeducted() throws TableFullException, NoMoreBetsException, TooManyChipsException {
        Bank bankMock = mock(Bank.class);
        when(bankMock.deductForPlayer(p, 200)).thenReturn(true);

        rt = new RouletteTable(10000, rng, timer, bankMock);

        rt.placeBet(p, Field.forNumber(17), 200);
        verify(bankMock).deductForPlayer(p, 200);
    }

    @Test(expected = TooManyChipsException.class)
    public void testRefuseBetForTooManyChips() throws TableFullException, NoMoreBetsException, TooManyChipsException {
        //arrange
        Bank bankMock = mock(Bank.class);
        rt = new RouletteTable(10000, rng, timer, bankMock);
        when(bankMock.deductForPlayer(p, 200)).thenReturn(false);

        //act
        rt.placeBet(p, Field.forNumber(17), 200);

        assertTrue(rt.betsByField(Field.forNumber(17)).isEmpty());

    }
}