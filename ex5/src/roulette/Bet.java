package roulette;

public class Bet {

	private Player p;
	private int value;
	private BetType betType;
	public Bet(Player p, int value) {
		this(p,value, BetType.SINGLE);
	}
	public Bet(Player p, int value, BetType betType) {
		this.p = p;
		this.value = value;
		this.betType=betType;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((betType == null) ? 0 : betType.hashCode());
		result = prime * result + ((p == null) ? 0 : p.hashCode());
		result = prime * result + value;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Bet other = (Bet) obj;
		if (betType == null) {
			if (other.betType != null)
				return false;
		} else if (!betType.equals(other.betType))
			return false;
		if (p == null) {
			if (other.p != null)
				return false;
		} else if (!p.equals(other.p))
			return false;
		if (value != other.value)
			return false;
		return true;
	}
	public int getValue() {
		return value;
	}

}
