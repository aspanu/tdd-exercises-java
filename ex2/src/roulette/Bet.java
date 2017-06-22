package roulette;

/**
 * Created by aspanu on 2017-06-22.
 */
public class Bet {
    private int chips;

    public Bet(Player player, int fields, int chips) {
        this.chips = chips;
    }

    public int getChips() {
        return chips;
    }

    public void setChips(int chips) {
        this.chips = chips;
    }
}
