package roulette;

public class Bet {

	private Player p;
	private int value;
	public Bet(Player p, int value) {
		this.p = p;
		this.value = value;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
