package edu.kmaooad;

import javax.naming.NameNotFoundException;

import com.mongodb.MongoConfigurationException;
import edu.kmaooad.service.MongoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.function.context.test.FunctionalSpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import reactor.core.publisher.Mono;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Unit test for Function class.
 */
@FunctionalSpringBootTest
@AutoConfigureWebTestClient
@Disabled
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
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

    @Test
    public void testMongo() {
        MongoService mongoService1 = new MongoService();
        int ID = 12345;
        BotResponse expected = new BotResponse(ID);
        Message message = new Message(ID, "message");
        BotRequest botRequest = new BotRequest(ID, message);

        MongoConfigurationException thrown = Assertions.assertThrows(MongoConfigurationException.class, () -> {

            mongoService1.insertOneRequest(botRequest);
            Assertions.assertTrue(true);
        });
    }
}
