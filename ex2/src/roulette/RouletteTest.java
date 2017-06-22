package roulette;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by aspanu on 2017-06-22.
 */
public class RouletteTest {

    @Test
    public void testPlayerCanPlaceAnyNoChips() {

        RouletteGame rouletteGame = new RouletteGame();
        Player player =  new Player();

        int fields = 16;
        int chips = 2;
        Bet newBet = new Bet (player, fields, chips);

        rouletteGame.placeBet(newBet);

        assertTrue("Placed bet should be contained in the roulette game",
            rouletteGame.getBets().contains(newBet));
    }
}
