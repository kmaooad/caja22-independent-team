package edu.kmaooad;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BotRequestTest {
    BotRequest botRequest = new BotRequest(123L,new Message(4L,"Bot"));

    @Test
    void getUpdate_idTest() {
        assertEquals(123L, botRequest.getUpdate_id());
    }

    @Test
    void getMessageTest() {
        assertEquals(new Message(4L,"Bot"), botRequest.getMessage());
    }

    @Test
    void setUpdate_idTest() {
        BotRequest botRequest2 = new BotRequest(botRequest.getUpdate_id(), botRequest.getMessage());
        botRequest2.setUpdate_id(1234L);

        assertEquals(1234L, botRequest2.getUpdate_id());
        assertNotEquals(botRequest2.getUpdate_id(), botRequest.getUpdate_id());
    }

    @Test
    void setMessageTest() {
        BotRequest botRequest2 = new BotRequest(botRequest.getUpdate_id(), botRequest.getMessage());
        botRequest2.setMessage(new Message(42L,"Bot2"));

        assertEquals(new Message(42L,"Bot2"), botRequest2.getMessage());
        assertNotEquals(botRequest2.getMessage(), botRequest.getMessage());
    }

    @Test
    void testEqualsTest() {
        BotRequest botRequest2 = new BotRequest(botRequest.getUpdate_id(), botRequest.getMessage());
        assertTrue(botRequest.equals(botRequest2));
        botRequest2.setUpdate_id(1234L);
        assertFalse(botRequest.equals(botRequest2));
    }

    @Test
    void testToStringTest() {
        assertEquals("BotRequest(update_id=123, message=Message(message_id=4, text=Bot))", botRequest.toString());
    }
}