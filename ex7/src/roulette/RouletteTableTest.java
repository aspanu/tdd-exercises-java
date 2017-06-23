package roulette;

import java.util.*;

import org.jetbrains.annotations.NotNull;
import org.junit.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RouletteTableTest {

    Player p;
    RouletteTable rt;
    RandomNumberGenerator rng;
    TestTimer timer;
    WalletService walletService;

    @Before
    public void setUp() {
        p = new Player();
        rng = mock(RandomNumberGenerator.class);
        walletService = mock(WalletService.class);
        timer = new TestTimer();
        rt = new RouletteTable(10000, rng, timer, walletService);
        when(walletService.isAvailable((Player) anyObject(), anyInt())).thenReturn(true);
    }


    @Test
    public void player_can_place_bets_on_number_fields() throws Exception {
        NewBet bet = new NewBet(BetType.SINGLE, 100, p, getLocations(17));
        rt.placeBet(bet);
        assertTrue(rt.getBets().contains(bet));
    }

    @NotNull private HashSet<Integer> getLocations(Integer... locations) {
        return new HashSet<>(Arrays.asList(locations));
    }

    @Test
    public void player_can_place_bets_on_different_fields_and_with_different_values() throws Exception {
        NewBet bet1 = new NewBet(BetType.SINGLE, 100, p, getLocations(17));
        rt.placeBet(bet1);
        NewBet bet2 = new NewBet(BetType.SINGLE, 600, p, getLocations(2));
        rt.placeBet(bet2);
        NewBet bet3 = new NewBet(BetType.SINGLE, 1000, p, getLocations(30));
        rt.placeBet(bet3);

        assertTrue(rt.getBets().contains(bet1));
        assertTrue(rt.getBets().contains(bet2));
        assertTrue(rt.getBets().contains(bet3));
    }


    @Test
    public void placingOfBetsReducesNoChips() throws Exception {
        RouletteTable rt = new RouletteTable(300, rng, timer, walletService);
        Player p = new Player();
        NewBet bet1 = new NewBet(BetType.SINGLE, 100, p, getLocations(17));
        rt.placeBet(bet1);
        assertEquals(100, rt.currentChipsOnTable());
    }

    @Test
    public void Total_number_of_chips_is_limited_by_the_table_and_the_table_can_refuse_a_bet_if_there_are_too_many_chips()
        throws Exception {
        RouletteTable rt = new RouletteTable(300, rng, timer, walletService);
        Player p = new Player();
        rt.placeBet(new NewBet(BetType.SINGLE, 200, p, getLocations(17)));

        boolean exceptionThrown = false;
        try {
            rt.placeBet(new NewBet(BetType.SINGLE, 200, p, getLocations(17)));
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
            rt.placeBet(new NewBet(BetType.SINGLE, 100, players[i], getLocations(20)));
        }
        Player nine = new Player();
        rt.placeBet(new NewBet(BetType.SINGLE, 100, nine, getLocations(20)));
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
        when(rng.generate(30, 40)).thenReturn(33);
        rt.roll();
        timer.moveTime(34000);
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

        rt.placeBet(p, new Field("A", loses, 36), 1);
        rt.placeBet(p2, new Field("B", wins, 36), 2);
        rt.placeBet(p3, new Field("C", loses, 36), 3);
        //act
        when(rng.generate(30, 40)).thenReturn(33);
        rt.roll();
        timer.moveTime(34000);
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
    public void system_will_deduct_any_chips_placed_on_the_table_from_the_available_amount() throws Exception {
        rt.placeBet(p, Field.forNumber(10), 10);
        verify(walletService).adjustBalance(p, -10);
    }

    @Test(expected = NotEnoughChipsException.class)
    public void if_a_player_attempts_to_place_more_chips_on_the_table_than_available_the_system_refuses_the_bet()
        throws Exception {
        when(walletService.isAvailable(p, 10)).thenReturn(false);
        rt.placeBet(p, Field.forNumber(10), 10);

    }

    @Test
    public void system_will_automatically_pay_out_winning_bets_with_multiplier_on_field() throws Exception {
        WinningStrategy wins = mock(WinningStrategy.class);
        when(wins.winsOn(anyInt())).thenReturn(true);
        rt.placeBet(p, new Field("B", wins, 3), 2);
        when(rng.generate(30, 40)).thenReturn(33);
        rt.roll();
        timer.moveTime(34000);
        verify(walletService).adjustBalance(p, -2);
        verify(walletService).adjustBalance(p, 6);
    }

    @Test
    public void system_will_not_pay_out_anything_for_losing_bets() throws Exception {
        WinningStrategy loses = mock(WinningStrategy.class);
        when(loses.winsOn(anyInt())).thenReturn(false);
        rt.placeBet(p, new Field("B", loses, 3), 2);
        when(rng.generate(30, 40)).thenReturn(33);
        rt.roll();
        timer.moveTime(34000);
        verify(walletService, times(1)).adjustBalance(eq(p), anyInt());

    }

    @Test
    public void multibets_paid_out_divided_by_number_of_fields() throws Exception {
        WinningStrategy loses = mock(WinningStrategy.class);
        when(loses.winsOn(anyInt())).thenReturn(false);

        WinningStrategy wins = mock(WinningStrategy.class);
        when(wins.winsOn(anyInt())).thenReturn(true);

        rt.placeBet(p,
                    new Field[]{new Field("1", wins, 36), new Field("2", loses, 36)},
                    1);
        when(rng.generate(30, 40)).thenReturn(33);

        rt.roll();
        timer.moveTime(34000);
        verify(walletService).adjustBalance(p, -1);
        verify(walletService).adjustBalance(p, 18);
    }


}