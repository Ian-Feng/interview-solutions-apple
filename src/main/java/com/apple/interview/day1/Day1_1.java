package com.apple.interview.day1;

public class Day1_1 extends Day1{

    @Override
    protected GridPoint computeDestination(String[] steps) {
        int x = 0;
        int y = 0;
        int current_direction = 0;

        for (String step: steps) {
            char next_direction = step.charAt(0);
            int dist = Integer.parseInt(step.substring(1));

            // either left or right
            if (next_direction == 'L') {
                current_direction --;
                if (current_direction < 0) {
                    current_direction = 3;
                }
            } else {
                current_direction ++;
                if (current_direction > 3) {
                    current_direction = 0;
                }
            }

            x += directions[current_direction] * dist;
            y += directions[current_direction + 1] * dist;

        }

        return new GridPoint(x, y);
    }
}
