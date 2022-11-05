package edu.kmaooad;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

import static edu.kmaooad.Constants.REQUEST_WITHOUT_MESSAGE_ID;

@Component
@AllArgsConstructor
public class BotService implements Function<BotRequest, BotResponse> {
    @Autowired
    private MongoService mongoService;

    @Override
    public BotResponse apply(BotRequest botRequest) {

        if (botRequest.getMessage().getMessage_id() == 0 || botRequest.getMessage().getText() == null) {
            throw new RuntimeException(REQUEST_WITHOUT_MESSAGE_ID);
        }

        mongoService.insertOneRequest(botRequest);

        return new BotResponse(botRequest.getMessage().getMessage_id());
    }
}
