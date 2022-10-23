package edu.kmaooad;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
public class BotRequestMapper {
    ObjectMapper objectMapper;

    public BotRequestMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @SneakyThrows
    public BotRequest mapFrom (String json) {
        return objectMapper.readValue(json, BotRequest.class);
    }

    @SneakyThrows
    public String deserialize(BotRequest request) {
        return objectMapper.writeValueAsString(request);
    }

}
