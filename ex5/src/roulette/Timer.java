package roulette;

public interface Timer {

	public abstract void callBack(long howMuchLaterInMillis, Runnable what);

	public abstract long getTimeInMillis();

}