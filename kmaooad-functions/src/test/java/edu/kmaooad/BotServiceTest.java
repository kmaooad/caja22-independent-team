package edu.kmaooad;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static edu.kmaooad.Constants.REQUEST_WITHOUT_MESSAGE_ID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
public class BotServiceTest {

    @Mock
    private MongoService mongoService;
    @InjectMocks
    private BotService botService;

    @Test
    public void testBotServiceWithMessageIdZero() {
        long id = 0;
        Message message = new Message(id, null);
        BotRequest botRequest = new BotRequest(id, message);

        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> botService.apply(botRequest));
        assertEquals(REQUEST_WITHOUT_MESSAGE_ID, runtimeException.getMessage());
    }

    @Test
    public void testBotServiceWithMessageTextNull() {
        long id = 7;
        Message message = new Message(id, null);
        BotRequest botRequest = new BotRequest(id, message);

        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> botService.apply(botRequest));
        assertEquals(REQUEST_WITHOUT_MESSAGE_ID, runtimeException.getMessage());
    }

    @Test
    public void testBotServiceWithCorrectMessage() {
        long id = 7;
        BotResponse expected = new BotResponse(id);

        Message message = new Message(id, "simple");
        BotRequest botRequest = new BotRequest(id, message);

        BotResponse response = assertDoesNotThrow(() -> botService.apply(botRequest));
        assertEquals(expected, response);
    }

    @Test
    public void testBotService_whenMongoServiceThrowsException_shouldRethrow() {
        long id = 7;
        Message message = new Message(id, "simple");
        BotRequest botRequest = new BotRequest(id, message);
        String exceptionMessage = "exception";

        doThrow(new RuntimeException(exceptionMessage)).when(mongoService).insertOneRequest(botRequest);

        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> botService.apply(botRequest));
        assertEquals(exceptionMessage, runtimeException.getMessage());
    }
}
