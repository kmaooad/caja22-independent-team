package edu.kmaooad;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MongoService {

    @Autowired
    private BotRequestMapper requestMapper;

    private static final String URI = "mongodb+srv://IndependentTeam:5UF2n0PnsIjaxYLN@cluster0.50iuewp.mongodb.net/?retryWrites=true&w=majority";

    public void insertOneRequest(BotRequest request) {
        try (MongoClient mongoClient = MongoClients.create(URI)) {
            MongoDatabase database = mongoClient.getDatabase("independentDB");
            MongoCollection<Document> collection = database.getCollection("independentCollection");

            Document doc = Document.parse(requestMapper.deserialize(request));
            collection.insertOne(doc);
        }
    }
}
