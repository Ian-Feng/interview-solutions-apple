package com.apple.interview.day10;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * --- Day 10: Balance Bots ---
 * You come upon a factory in which many robots are zooming around handing small microchips to each other.
 *
 * Upon closer examination, you notice that each bot only proceeds when it has two microchips, and once it does, it gives each one to a different bot or puts it in a marked "output" bin. Sometimes, bots take microchips from "input" bins, too.
 *
 * Inspecting one of the microchips, it seems like they each contain a single number; the bots must use some logic to decide what to do with each chip. You access the local control computer and download the bots' instructions (your puzzle input).
 *
 * Some of the instructions specify that a specific-valued microchip should be given to a specific bot; the rest of the instructions indicate what a given bot should do with its lower-value or higher-value chip.
 *
 * For example, consider the following instructions:
 *
 * value 5 goes to bot 2
 * bot 2 gives low to bot 1 and high to bot 0
 * value 3 goes to bot 1
 * bot 1 gives low to output 1 and high to bot 0
 * bot 0 gives low to output 2 and high to output 0
 * value 2 goes to bot 2
 * Initially, bot 1 starts with a value-3 chip, and bot 2 starts with a value-2 chip and a value-5 chip.
 * Because bot 2 has two microchips, it gives its lower one (2) to bot 1 and its higher one (5) to bot 0.
 * Then, bot 1 has two microchips; it puts the value-2 chip in output 1 and gives the value-3 chip to bot 0.
 * Finally, bot 0 has two microchips; it puts the 3 in output 2 and the 5 in output 0.
 * In the end, output bin 0 contains a value-5 microchip, output bin 1 contains a value-2 microchip, and output bin 2 contains a value-3 microchip. In this configuration, bot number 2 is responsible for comparing value-5 microchips with value-2 microchips.
 *
 * Based on your instructions, what is the number of the bot that is responsible for comparing value-61 microchips with value-17 microchips?
 *
 * Your puzzle answer was 73.
 *
 * --- Part Two ---
 * What do you get if you multiply together the values of one chip in each of outputs 0, 1, and 2?
 *
 * Your puzzle answer was 3965.
 */

public class Day10 {
    public static void main(String[] args) throws FileNotFoundException {
        Pattern p1 = Pattern.compile("bot (\\d+) gives low to ([^\\s]+) (\\d+) and high to ([^\\s]+) (\\d+)");
        Pattern p2 = Pattern.compile("value (\\d+) goes to bot (\\d+)");
        Day10 d10 = new Day10();

        List<Instruction> instructions = new ArrayList<>();

        Scanner sc = new Scanner(new File("./inputs/day10"));
        while (sc.hasNextLine()) {
            String str = sc.nextLine();
            Matcher m1 = p1.matcher(str);
            Matcher m2 = p2.matcher(str);
            Instruction instruction = new Instruction();
            if (m1.matches()) {
                instruction.setType(InstructionType.OPERATION);
                int id = Integer.parseInt(m1.group(1));
                int low = Integer.parseInt(m1.group(3));
                int high = Integer.parseInt(m1.group(5));

                instruction.setStartPoint(id);
                instruction.setLowDestination(new Destination(DestinationType.valueOf(m1.group(2).toUpperCase()), low));
                instruction.setHighDestination(new Destination(DestinationType.valueOf(m1.group(4).toUpperCase()), high));
            } else if (m2.matches()) {
                instruction.setType(InstructionType.INIT);
                int val = Integer.parseInt(m2.group(1));
                int to = Integer.parseInt(m2.group(2));
                instruction.setValue(val);
                instruction.setDestination(to);
            }

            instructions.add(instruction);
        }
        System.out.println(d10.botResponsibleForComparing(instructions, 17, 61));
        System.out.println(d10.productOfOutputBucket(instructions, 0, 1, 2));
        sc.close();
    }

    public int botResponsibleForComparing(List<Instruction> instructions, int lower, int upper) {
        Map<Integer, Bot> numToBot = new HashMap<>();
        Map<Integer, Set<Integer>> numToOutput = new HashMap<>();
        Queue<Instruction> queue = new LinkedList<>(instructions);

        int previousLevel = instructions.size();

        while (!queue.isEmpty()) {

            for (int i = 0; i < previousLevel; i++) {
                Instruction curr = queue.poll();
                ResultType result = null;
                switch (curr.getType()) {
                    case INIT:
                        result = initializeBot(numToBot, curr.getValue(), curr.getDestination());
                        break;
                    case OPERATION:
                        result = handleOperation(numToBot, numToOutput, curr, lower, upper);
                        break;
                }

                if (!result.isSuccess()) {
                    queue.offer(curr);
                    continue;
                }

                if (result.getMatchPoint() != null) {
                    return result.getMatchPoint();
                }
            }

            if (queue.size() == previousLevel) {
                break;
            }

            previousLevel = queue.size();
        }
        return -1;
    }

    public int productOfOutputBucket(List<Instruction> instructions, int... buckets) {
        Map<Integer, Bot> numToBot = new HashMap<>();
        Map<Integer, Set<Integer>> numToOutput = new HashMap<>();
        Queue<Instruction> queue = new LinkedList<>(instructions);
        int previousLevel = instructions.size();

        while (!queue.isEmpty()) {

            for (int i = 0; i < previousLevel; i++) {
                Instruction curr = queue.poll();
                ResultType result = null;
                switch (curr.getType()) {
                    case INIT:
                        result = initializeBot(numToBot, curr.getValue(), curr.getDestination());
                        break;
                    case OPERATION:
                        result = handleOperation(numToBot, numToOutput, curr, 0, 0);
                        break;
                }

                // if the instruction is not valid, add it to the queue
                if (!result.isSuccess()) {
                    queue.offer(curr);
                    continue;
                }
            }

            if (queue.size() == previousLevel) {
                break;
            }

            previousLevel = queue.size();

        }

        int result = 1;
        for (int bucket: buckets) {
            if (!numToOutput.containsKey(bucket)) {
                continue;
            }
            result *= numToOutput.get(bucket).stream().reduce(1, (x, y) -> x * y);
        }

        return result;
    }

    private ResultType initializeBot(Map<Integer, Bot> numToBot, int value, int destination) {
        if (!numToBot.containsKey(destination)) {
            numToBot.put(destination, new Bot());
        }

        return new ResultType(numToBot.get(destination).addNumber(value));
    }

    private ResultType handleOperation(Map<Integer, Bot> numToBot,
                                       Map<Integer, Set<Integer>> numToOutput,
                                       Instruction curr,
                                       int lower,
                                       int upper) {

        Bot startPoint = numToBot.getOrDefault(curr.getStartPoint(), new Bot());
        ResultType result = new ResultType();
        if (!startPoint.isOperable()) {
            result.setSuccess(false);
            return result;
        }
        numToBot.putIfAbsent(curr.getStartPoint(), startPoint);

        boolean success = true;

        Destination low = curr.getLowDestination();
        Destination high = curr.getHighDestination();

        // determine if the chip goes to next bot or output
        if (low.getType() == DestinationType.BOT) {
            Bot lowDestination = numToBot.getOrDefault(low.getValue(), new Bot());
            numToBot.putIfAbsent(low.getValue(), lowDestination);
            success &= lowDestination.addNumber(startPoint.getLow());
        } else if (low.getType() == DestinationType.OUTPUT) {
            Set<Integer> output = numToOutput.getOrDefault(low.getValue(), new HashSet<Integer>());
            numToOutput.putIfAbsent(low.getValue(), output);
            output.add(startPoint.getLow());
        }

        if (high.getType() == DestinationType.BOT) {
            Bot highDestination = numToBot.getOrDefault(high.getValue(), new Bot());
            numToBot.putIfAbsent(high.getValue(), highDestination);
            success &= highDestination.addNumber(startPoint.getHigh());
        } else if (high.getType() == DestinationType.OUTPUT) {
            Set<Integer> output = numToOutput.getOrDefault(high.getValue(), new HashSet<Integer>());
            numToOutput.putIfAbsent(high.getValue(), output);
            output.add(startPoint.getHigh());
        }

        if (success && startPoint.getLow() == lower && startPoint.getHigh() == upper) {
            result.setMatchPoint(curr.getStartPoint());
        }

        result.setSuccess(startPoint.sendsChip() && success);
        return result;
    }
}
