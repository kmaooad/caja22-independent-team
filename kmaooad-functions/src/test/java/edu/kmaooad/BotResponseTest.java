package edu.kmaooad;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BotResponseTest {
    BotResponse botResponse = new BotResponse(123);

    @Test
    void getMessageIdTest() {
        assertEquals(123, botResponse.getMessageId());
    }

    @Test
    void setMessageIdTest() {
        BotResponse botResponse1 = new BotResponse(botResponse.getMessageId());
        botResponse1.setMessageId(1234L);

        assertEquals(1234L, botResponse1.getMessageId());
    }

    @Test
    void testEquals() {
        BotResponse botResponse1 = new BotResponse(botResponse.getMessageId());
        assertTrue(botResponse1.equals(botResponse));
        botResponse1.setMessageId(1234L);
        assertFalse(botResponse1.equals(botResponse));
    }

    @Test
    void tesHashNotEqualsNull() {
        BotResponse botResponse1 = new BotResponse(botResponse.getMessageId());
       assertNotNull(botResponse1.hashCode());
    }

}
