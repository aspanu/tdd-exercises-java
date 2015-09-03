package roulette;

public class Field {
	private String fieldName;
	private WinningStrategy winningStrategy;
	private int payoutCoeficient;
	Field(String s, WinningStrategy winningStrategy, int payoutCoefficient){
		this.fieldName=s;
		this.winningStrategy=winningStrategy;
		this.payoutCoeficient=payoutCoefficient;
	}
	public static Field forNumber(int number){
		return new Field(String.valueOf(number),new SingleNumberWinningStrategy(number),36);
	}
	public static Field ODD=new Field("ODD",new OddEvenWinningStrategy(true),2);
	public static Field EVEN=new Field("EVEN",new OddEvenWinningStrategy(false),2);
	public static Field RED=new Field("RED",new RedBlackWinningStrategy(true),2);
	public static Field BLACK=new Field("BLACK",new RedBlackWinningStrategy(false),2);
	public static Field ONE_EIGHTEEN=new Field("1-18",new HalfWinningStrategy(1),2);
	public static Field NINETEEN_THIRTYSIX=new Field("19-36",new HalfWinningStrategy(2),2);
	public static Field ONE_TWELVE = new Field("1-12",new ThirdWinningStrategy(1),3);
	public static Field THIRTEEN_TWENTYFOUR = new Field("13-24",new ThirdWinningStrategy(2),3);
	public static Field TWENTYFIVE_THIRTYSIX = new Field("25-36",new ThirdWinningStrategy(3),3);
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fieldName == null) ? 0 : fieldName.hashCode());
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
		final Field other = (Field) obj;
		if (fieldName == null) {
			if (other.fieldName != null)
				return false;
		} else if (!fieldName.equals(other.fieldName))
			return false;
		return true;
	}
	public WinningStrategy winningStrategy() {
		return winningStrategy;
	}
	public String getFieldName() {
		return fieldName;
	}
	public int getPayoutCoeficient() {
		return payoutCoeficient;
	}
}
