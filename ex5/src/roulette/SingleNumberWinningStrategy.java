package roulette;

public class SingleNumberWinningStrategy implements WinningStrategy{
	private int wheelPosition;
	public SingleNumberWinningStrategy(int wheelPosition) {
		this.wheelPosition=wheelPosition;
	}

	@Override
	public boolean winsOn(int wheelPosition) {
		return wheelPosition==this.wheelPosition;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + wheelPosition;
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
		SingleNumberWinningStrategy other = (SingleNumberWinningStrategy) obj;
		if (wheelPosition != other.wheelPosition)
			return false;
		return true;
	}
}
