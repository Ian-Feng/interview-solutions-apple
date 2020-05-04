package com.apple.interview.day1;

import java.util.HashSet;
import java.util.Set;

public class Day1_2 extends Day1 {
    @Override
    protected GridPoint computeDestination(String[] steps) {
        Set<GridPoint> visited = new HashSet<>();

        int x = 0;
        int y = 0;
        int current_direction = 0;

        for (String step: steps) {
            char next_direction = step.charAt(0);
            int dist = Integer.parseInt(step.substring(1));

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

            // add every location along the path to visited
            GridPoint duplicate = checkDuplicateAndAddToVisited(visited, x, y, dist, current_direction);
            if (duplicate != null) {
                return duplicate;
            }

            x += directions[current_direction] * dist;
            y += directions[current_direction + 1] * dist;
        }

        return new GridPoint(x, y);
    }

    private GridPoint checkDuplicateAndAddToVisited(Set<GridPoint> visited,
                                                           int x,
                                                           int y,
                                                           int dist,
                                                           int current_direction) {

        for (int i = 0; i < dist; i++) {
            x += directions[current_direction];
            y += directions[current_direction + 1];
            GridPoint current = new GridPoint(x, y);
            if (visited.contains(current)) {
                return current;
            }

            visited.add(current);
        }
        return null;
    }
}
