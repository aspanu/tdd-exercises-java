package roulette;

public class Field {
	private String fieldName;
	private WinningStrategy winningStrategy;
	Field(String s, WinningStrategy winningStrategy){
		this.fieldName=s;
		this.winningStrategy=winningStrategy;
	}
	public static Field forNumber(int number){
		return new Field(String.valueOf(number),new SingleNumberWinningStrategy(number));
	}
	public static Field ODD=new Field("ODD",new OddEvenWinningStrategy(true));
	public static Field EVEN=new Field("EVEN",new OddEvenWinningStrategy(false));
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
	
}
