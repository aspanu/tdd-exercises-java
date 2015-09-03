package roulette;

import java.util.Arrays;

public class RedBlackWinningStrategy implements WinningStrategy {
	int redNumbers[]=new int[]{1,3,5,7,9,12,14,16,18,19,21,23,25,27,30,32,34,36};
	boolean isRed;
	public RedBlackWinningStrategy(boolean isRed) {
		this.isRed=isRed;
		
	}
	@Override
	public boolean winsOn(int wheelPosition) {
		if (wheelPosition==0) return false;
		if (isRed && Arrays.binarySearch(redNumbers, wheelPosition)>=0) return true;
		if (!isRed && Arrays.binarySearch(redNumbers, wheelPosition)<0) return true;
		return false;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isRed ? 1231 : 1237);
		result = prime * result + Arrays.hashCode(redNumbers);
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
		RedBlackWinningStrategy other = (RedBlackWinningStrategy) obj;
		if (isRed != other.isRed)
			return false;
		if (!Arrays.equals(redNumbers, other.redNumbers))
			return false;
		return true;
	}

}
