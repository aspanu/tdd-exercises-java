package roulette;

public class OddEvenWinningStrategy implements WinningStrategy {

	private boolean isOdd;
	public OddEvenWinningStrategy(boolean isOdd) {
		this.isOdd=isOdd;
	}
	@Override
	public boolean winsOn(int wheelPosition) {
		if (wheelPosition==0) return false;
		if (wheelPosition%2==1 && isOdd) return true;
		if (wheelPosition%2==0 && (!isOdd)) return true;
		return false;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isOdd ? 1231 : 1237);
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
		OddEvenWinningStrategy other = (OddEvenWinningStrategy) obj;
		if (isOdd != other.isOdd)
			return false;
		return true;
	}
}
