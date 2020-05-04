package com.apple.test.day10;

import com.apple.interview.day10.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class Day10Spec {

    private Day10 day10;

    @Before
    public void setUp() {
        day10 = new Day10();
    }

    @Test
    public void testBotResponsibleForComparing() {
        List<Instruction> instructionList = Arrays.asList(
                new Instruction(InstructionType.OPERATION, 2, new Destination(DestinationType.BOT, 1), new Destination(DestinationType.BOT, 0)),
                new Instruction(InstructionType.OPERATION, 1, new Destination(DestinationType.OUTPUT, 1), new Destination(DestinationType.BOT, 0)),
                new Instruction(InstructionType.OPERATION, 0, new Destination(DestinationType.OUTPUT, 2), new Destination(DestinationType.OUTPUT, 0)),
                new Instruction(InstructionType.INIT, 5, 2),
                new Instruction(InstructionType.INIT, 2, 2));

        assertEquals(day10.botResponsibleForComparing(instructionList, 2, 5), 2);
    }

    @Test
    public void testBotResponsibleForComparingWithInvalidInstructions() {
        List<Instruction> instructionList = Arrays.asList(
                new Instruction(InstructionType.OPERATION, 2, new Destination(DestinationType.BOT, 1), new Destination(DestinationType.BOT, 0)),
                new Instruction(InstructionType.OPERATION, 1, new Destination(DestinationType.OUTPUT, 1), new Destination(DestinationType.BOT, 0)),
                new Instruction(InstructionType.OPERATION, 0, new Destination(DestinationType.OUTPUT, 2), new Destination(DestinationType.OUTPUT, 0)),
                new Instruction(InstructionType.INIT, 5, 2),
                new Instruction(InstructionType.INIT, 2, 0));

        assertEquals(day10.botResponsibleForComparing(instructionList, 2, 5), -1);
    }

    @Test
    public void testProductOfOutputBucket() {
        List<Instruction> instructionList = Arrays.asList(
                new Instruction(InstructionType.OPERATION, 2, new Destination(DestinationType.BOT, 1), new Destination(DestinationType.BOT, 0)),
                new Instruction(InstructionType.OPERATION, 1, new Destination(DestinationType.OUTPUT, 1), new Destination(DestinationType.BOT, 0)),
                new Instruction(InstructionType.OPERATION, 0, new Destination(DestinationType.OUTPUT, 2), new Destination(DestinationType.OUTPUT, 0)),
                new Instruction(InstructionType.INIT, 5, 2),
                new Instruction(InstructionType.INIT, 2, 2),
                new Instruction(InstructionType.INIT, 3, 1));

        assertEquals(day10.productOfOutputBucket(instructionList, 0, 1), 10);
    }

    @Test
    public void testProductOfOutputBucketWithInvalidInstructions() {
        List<Instruction> instructionList = Arrays.asList(
                new Instruction(InstructionType.OPERATION, 2, new Destination(DestinationType.BOT, 1), new Destination(DestinationType.BOT, 0)),
                new Instruction(InstructionType.OPERATION, 1, new Destination(DestinationType.OUTPUT, 1), new Destination(DestinationType.BOT, 0)),
                new Instruction(InstructionType.OPERATION, 0, new Destination(DestinationType.OUTPUT, 2), new Destination(DestinationType.OUTPUT, 0)),
                new Instruction(InstructionType.INIT, 5, 2),
                new Instruction(InstructionType.INIT, 2, 0));

        assertEquals(day10.productOfOutputBucket(instructionList, 2, 5), 1);
    }
}
