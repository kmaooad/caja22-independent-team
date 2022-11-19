package edu.kmaooad;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.function.context.test.FunctionalSpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


/**
 * Unit test for Function class.
 */
@FunctionalSpringBootTest
@AutoConfigureWebTestClient
@Disabled
public class FunctionTest {
    @Autowired
    private WebTestClient client;

    @MockBean
    private MongoService mongoService;

    @Test
    public void testFunctionWithoutBody() {
        client.post().uri("/api/TelegramWebhook")
                .exchange()
                .expectStatus()
                .is5xxServerError();
    }
    @Test
    public void testFunctionWithMessageWithoutMessageIdInBody() {
        long id = 7;
        BotRequest botRequest = new BotRequest(id, null);
        client.post().uri("/api/TelegramWebhook")
                .body(Mono.just(botRequest), BotRequest.class)
                .exchange()
                .expectStatus()
                .is5xxServerError();
    }

    @Test
    public void testFunctionWithMessageIdWithoutMessageInBody() {
        long id = 7;
        Message message = new Message(id, null);
        BotRequest botRequest = new BotRequest(id, message);
        client.post().uri("/api/TelegramWebhook")
                .body(Mono.just(botRequest), BotRequest.class)
                .exchange()
                .expectStatus()
                .is5xxServerError();
    }

    @Test
    public void testFunctionWithCorrectBody() {
        int ID = 12345;
        BotResponse expected = new BotResponse(ID);
        Message message = new Message(ID, "message");
        BotRequest botRequest = new BotRequest(ID, message);

        doNothing().when(mongoService).insertOneRequest(any(BotRequest.class));

        client.post().uri("/api/TelegramWebhook")
                .body(Mono.just(botRequest), BotRequest.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(BotResponse.class)
                .isEqualTo(expected);
    }

}
