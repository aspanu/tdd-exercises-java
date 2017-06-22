package roulette;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by aspanu on 2017-06-22.
 */


public class RouletteGame {

    private Set<Bet> bets = new HashSet<>();

    public void placeBet(Bet newBet) {
        bets.add(newBet);

    }

    public Set<Bet> getBets() {
        return bets;
    }
}
