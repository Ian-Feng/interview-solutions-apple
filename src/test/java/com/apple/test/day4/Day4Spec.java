package com.apple.test.day4;

import com.apple.interview.day4.Day4;
import com.apple.interview.day4.RoomInput;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.*;

public class Day4Spec {

    private Day4 day4;

    @Before
    public void setUp() {
        day4 = new Day4();
    }

    @Test
    public void testCheckRoomSuccess() throws Exception{

        RoomInput input = new RoomInput("aaaaa-bbb-z-y-x", "123", "abxyz");
        boolean result = Whitebox.invokeMethod(day4, "checkRoom", input);

        assertTrue(result);
    }

    @Test
    public void testCheckRoomFail() throws Exception{
        RoomInput input = new RoomInput("totally-real-room", "200", "decoy");
        boolean result = Whitebox.invokeMethod(day4, "checkRoom", input);

        assertFalse(result);
    }

    @Test
    public void testDecrypt() {
        assertEquals(day4.decrypt("qzmt-zixmtkozy-ivhz", "343"), "very encrypted name");
    }
}
