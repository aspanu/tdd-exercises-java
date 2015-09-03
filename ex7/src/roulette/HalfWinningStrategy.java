package roulette;

public class HalfWinningStrategy implements WinningStrategy {
	int half;
	public HalfWinningStrategy (int half){
		this.half=half;
	}
	@Override
	public boolean winsOn(int wheelPosition) {
		if (wheelPosition==0) return false;
		return ((wheelPosition-1)/18)+1==half;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + half;
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
		HalfWinningStrategy other = (HalfWinningStrategy) obj;
		if (half != other.half)
			return false;
		return true;
	}

}
