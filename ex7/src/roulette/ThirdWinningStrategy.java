package roulette;

public class ThirdWinningStrategy implements WinningStrategy {
	private int third;
	public ThirdWinningStrategy (int third){
		this.third=third;
	}
	@Override
	public boolean winsOn(int wheelPosition) {
		if (wheelPosition==0) return false;
		return ((wheelPosition-1)/12)+1==third;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + third;
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
		ThirdWinningStrategy other = (ThirdWinningStrategy) obj;
		if (third != other.third)
			return false;
		return true;
	}

}
