package roulette;

public interface WalletService {

	void adjustBalance(Player p, int amount);

	boolean isAvailable(Player p, int amount);

}
