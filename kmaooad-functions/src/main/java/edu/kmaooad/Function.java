package edu.kmaooad;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Optional;

/**
 * Azure Functions with HTTP Trigger.
 */
public class Function {
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

        if (body == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Please pass a name in the request body").build();
        }

        BotRequest botRequest = new BotRequestMapper(new ObjectMapper()).mapFrom(body);

        long messageId = botRequest.getMessage().getMessage_id();

        String uri = "mongodb+srv://IndependentTeam:5UF2n0PnsIjaxYLN@cluster0.50iuewp.mongodb.net/?retryWrites=true&w=majority";

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("independentDB");
            MongoCollection<Document> collection = database.getCollection("independentCollection");

            Document doc = Document.parse(body);
            collection.insertOne(doc);
        }

        return request.createResponseBuilder(HttpStatus.OK).body("message_id :  " + messageId).build();

    }
}
