package edu.kmaooad;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import org.springframework.cloud.function.adapter.azure.FunctionInvoker;

import java.util.Optional;

import static edu.kmaooad.Constants.APPLICATION_OBJECT_MAPPER;

/**
 * Azure Functions with HTTP Trigger.
 */
public class BotFunction extends FunctionInvoker<BotRequest, BotResponse> {
    /**
     * This function listens at endpoint "/api/TelegramWebhook". To invoke it using "curl" command in bash:
     * curl -d "HTTP Body" {your host}/api/TelegramWebhook
     */
    @FunctionName("TelegramWebhook")
    public HttpResponseMessage run(
            @HttpTrigger(
                    name = "req",
                    methods = {HttpMethod.POST},
                    authLevel = AuthorizationLevel.FUNCTION)
                    HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {
        context.getLogger().info("Java HTTP trigger processed a request.");

        final String body = request.getBody().orElse(null);

        //Doesn't work with Spring, have to add ExceptionHandler probably
//        if (body == null) {
//            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body(BAD_RESPONSE_BODY).build();
//        }

        BotRequest botRequest = new BotRequestMapper(APPLICATION_OBJECT_MAPPER).mapFrom(body);

        return request.createResponseBuilder(HttpStatus.OK)
                .body(handleRequest(botRequest, context))
                .build();

    }
}
