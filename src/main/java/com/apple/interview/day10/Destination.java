package com.apple.interview.day10;

public class Destination {
    private DestinationType type;
    private int value;

    public Destination(DestinationType type, int value) {
        this.type = type;
        this.value = value;
    }

    public DestinationType getType() {
        return type;
    }

    public void setType(DestinationType type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
