package roulette;

/**
 * Created by aspanu on 2017-06-22.
 */
public enum Category {
    SINGLE(0),
    SPLIT(1),
    EVEN(37),
    ODD(38);

    private int fieldValue;

    Category(int fieldValue) {
        this.fieldValue = fieldValue;
    }

    public int getFieldValue() {
        return fieldValue;
    }
}
