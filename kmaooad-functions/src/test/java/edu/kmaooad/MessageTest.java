package edu.kmaooad;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageTest {
    Message message = new Message(4L,"Bot");

    @Test
    void getMessage_idTest() {
        assertEquals(4L, message.getMessage_id());
    }

    @Test
    void getTextTest() {
        assertEquals("Bot", message.getText());
    }

    @Test
    void setMessage_idTest() {
        Message message2 = new Message(message.getMessage_id(), message.getText());
        message2.setMessage_id(5L);

        assertEquals(5L, message2.getMessage_id());
        assertNotEquals(message.getMessage_id(), message2.getMessage_id());
    }

    @Test
    void setTextTest() {
        Message message2 = new Message(message.getMessage_id(), message.getText());
        message2.setText("Bot2");

        assertEquals("Bot2", message2.getText());
        assertNotEquals(message.getText(), message2.getText());
    }

    @Test
    void testEqualsAndHashCodeTest() {
        Message message2 = new Message(message.getMessage_id(), message.getText());
        assertEquals(message2, message);
        assertTrue(message2.equals(message));

        //test hash code
        assertTrue(message.hashCode() == message2.hashCode());

        message2.setText("NOT BOT");
        assertNotEquals(message2, message);
        assertFalse(message2.equals(message));

    }

    @Test
    void testToStringTest() {
        assertEquals("Message(message_id=4, text=Bot)", message.toString());
    }

    @Test
    void testConstants(){
        assertEquals(Constants.BAD_RESPONSE_BODY,"Request body error. Please fix your request body");
        assertNotNull(Constants.APPLICATION_OBJECT_MAPPER);
        assertEquals(Constants.REQUEST_WITHOUT_MESSAGE_ID,"Illegal request, message_id not present");
    }
}
