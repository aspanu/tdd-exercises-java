package roulette;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by aspanu on 2017-06-22.
 */


public class RouletteGame {

    private static final int MAX_CHIPS = 500;
    private static final int MAX_PLAYERS = 8;
    private Set<Bet> bets = new HashSet<>();
    private int totalChips;
    private int numPlayers = 0;

    public boolean placeBet(Bet newBet) {
        if (newBet.getChips() + totalChips <= MAX_CHIPS) {
            bets.add(newBet);
            totalChips += newBet.getChips();
            return true;
        }
        return false;
    }

    public Set<Bet> getBets() {
        return bets;
    }

    public int getMaxChips() {
        return MAX_CHIPS;
    }

    public boolean acceptPlayer(Player player) {
        if (numPlayers >= MAX_PLAYERS) {
            return false;
        } else {
            numPlayers++;
            return true;
        }
    }

    public int getMaxPlayers() {
        return MAX_PLAYERS;
    }
}
