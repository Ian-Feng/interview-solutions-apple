package com.apple.test.day1;

import com.apple.interview.day1.Day1;
import com.apple.interview.day1.Day1_1;
import com.apple.interview.day1.Day1_2;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Day1Spec {

    @Test
    public void testComputeDistanceV1() {
        Day1 d1 = new Day1_1();
        int result = d1.computeDistance("R5, L5, R5, R3");
        assertEquals(12, result);
    }

    @Test
    public void testComputeDistanceV2() {
        Day1 d1 = new Day1_2();
        int result = d1.computeDistance("R8, R4, R4, R8");
        assertEquals(4, result);
    }
}
