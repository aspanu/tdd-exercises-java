package roulette;/**
 * Created by eapopei on 23.06.17.
 */

/**
 * TODO description
 * Copyright © 2016 by Raisin GmbH. All rights reserved.
 */

public class RandomProviderStub implements RandomProvider {
    private int nextNumber = 17;
    private int low;
    private int high;

    public void setNextRandom(int nextRandom) {
        this.nextNumber = nextRandom;
    }

    @Override
    public int getNextNumber(int l, int h) {
        this.low = l;
        this.high = h;
        return nextNumber;
    }

    public int getLow() {
        return low;
    }

    public int getHigh() {
        return high;
    }

}
