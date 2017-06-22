package roulette;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by aspanu on 2017-06-22.
 */
public class RouletteTest {


    private RouletteGame rouletteGame;
    private Player player;

    @Before
    public void setUp() {
        rouletteGame = new RouletteGame();
        player = new Player();
    }

    @Test
    public void testPlayerCanPlaceAnyNoChips() {
        int fields = 16;
        int chips = 2;
        Bet newBet = new Bet (player, fields, chips);

        assertTrue("Bet should be accepted", rouletteGame.placeBet(newBet));

        assertTrue(
            "Placed bet should be contained in the roulette game", /* message */
            rouletteGame.getBets().contains(newBet)
        );
    }

    @Test
    public void test_Any_Number_Of_Bets_On_Any_Number_Of_Fields_For_Any_Number_Of_Chips() {
        for (int i = 1; i <= 36; i++) {
            Bet newBet = new Bet(player, i, randomBetween(1, 13));
            assertTrue("Bet should be accepted", rouletteGame.placeBet(newBet));
            assertTrue(rouletteGame.getBets().contains(newBet));
        }
        assertEquals(36, rouletteGame.getBets().size());
    }

    @Test
    public void testMaxChipsAndRefuseBet() {
        int field = randomBetween(1, 36);

        Bet newBet = new Bet(player, field, rouletteGame.getMaxChips() + 1);
        assertFalse("Bet with too many chips should be rejected", rouletteGame.placeBet(newBet));
        assertFalse("Bet with too many chips should not be saved", rouletteGame.getBets().contains(newBet));
    }

    @Test
    public void testLimitedChipsAndRefuseBet() {
        Bet newBet = new Bet(player, randomBetween(1, 36), 123);
        assertTrue("Normal bet should be accepted", rouletteGame.placeBet(newBet));
        assertTrue("Normal bet should be saved", rouletteGame.getBets().contains(newBet));

        newBet = new Bet(player, randomBetween(1, 36), rouletteGame.getMaxChips() - 123 + 1);
        assertFalse("Bet with too many chips should be rejected", rouletteGame.placeBet(newBet));
        assertFalse("Bet with too many chips should not be saved", rouletteGame.getBets().contains(newBet));
    }

    @Test
    public void test_enforce_max_players() {
        for (int i = 0; i < rouletteGame.getMaxPlayers(); i++) {
            assertTrue("New players should be accepted.", rouletteGame.acceptPlayer(new Player()));
        }
        assertFalse("9th player should be rejected.", rouletteGame.acceptPlayer(new Player()));
    }

    @Test
    public void eachPlayerGetsDifferentColor() {
        for (int i = 0; i < rouletteGame.getMaxPlayers(); i++) {


        }
    }

    @Test
    public void test_random_between() {
        int random = randomBetween(0, 10);
        assertTrue(random >= 0);
        assertTrue(random <= 10);
    }

    private int randomBetween(int start, int end) {
        return (int) (Math.random() * end + start);
    }

}
