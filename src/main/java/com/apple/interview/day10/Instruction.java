package com.apple.interview.day10;

public class Instruction {
    private InstructionType type;

    private int startPoint;
    private Destination lowDestination;
    private Destination highDestination;

    private int value;
    private int destination;

    public Instruction() {
    }

    public Instruction(InstructionType type, int startPoint, Destination lowDestination, Destination highDestination) {
        this.type = type;
        this.startPoint = startPoint;
        this.lowDestination = lowDestination;
        this.highDestination = highDestination;
    }

    public Instruction(InstructionType type, int value, int destination) {
        this.type = type;
        this.value = value;
        this.destination = destination;
    }

    public InstructionType getType() {
        return type;
    }

    public void setType(InstructionType type) {
        this.type = type;
    }

    public int getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(int startPoint) {
        this.startPoint = startPoint;
    }

    public Destination getLowDestination() {
        return lowDestination;
    }

    public void setLowDestination(Destination lowDestination) {
        this.lowDestination = lowDestination;
    }

    public Destination getHighDestination() {
        return highDestination;
    }

    public void setHighDestination(Destination highDestination) {
        this.highDestination = highDestination;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }
}
