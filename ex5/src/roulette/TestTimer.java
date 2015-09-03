package roulette;

public class TestTimer implements Timer {
	long currentMillis;
	long executeRunnableMillis;
	Runnable runnableToExecute;
	public void callBack(long howMuchLaterInMillis, Runnable what){
		executeRunnableMillis=howMuchLaterInMillis+currentMillis;
		runnableToExecute=what;
	}
	public long getTimeInMillis(){
		return currentMillis;
	}
	public void moveTime(long millis){
		currentMillis+=millis;
		if (runnableToExecute!=null && executeRunnableMillis<=currentMillis){
			runnableToExecute.run();
			runnableToExecute=null;
			executeRunnableMillis=0;
		}
	}
}
