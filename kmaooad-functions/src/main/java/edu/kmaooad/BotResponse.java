package edu.kmaooad;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BotResponse {
    @JsonProperty("message_id")
    private long messageId;

    public BotResponse(long messageId) {
        this.messageId = messageId;
    }
}
