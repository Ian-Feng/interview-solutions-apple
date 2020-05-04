package com.apple.interview.day10;

public class Bot {
    private Integer low;
    private Integer high;
    private int size;

    public boolean addNumber(int num) {
        if (size == 2) {
            return false;
        }

        size++;
        if (low == null) {
            low = num;
        } else if (num >= low) {
            high = num;
        } else {
            high = low;
            low = num;
        }

        return true;
    }

    public boolean sendsChip() {
        if (size != 2) {
            return false;
        }
        low = null;
        high = null;
        size = 0;

        return true;
    }

    public Integer getLow() {
        return low;
    }

    public Integer getHigh() {
        return high;
    }

    public boolean isOperable() {
        return size == 2;
    }
}
