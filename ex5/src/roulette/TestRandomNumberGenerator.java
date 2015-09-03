package roulette;

public class TestRandomNumberGenerator implements RandomNumberGenerator {

	int nextOutcome;
	@Override
	public int generate(int from, int to) {
		return nextOutcome;
	}

	public void setNextOutcome(int i) {
		nextOutcome=i;
	}
	

}
