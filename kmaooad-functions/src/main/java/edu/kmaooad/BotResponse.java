package edu.kmaooad;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BotResponse {
    @JsonProperty("message_id")
    private long messageId;

    @Override
    public int hashCode() {
        return Long.hashCode(messageId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof BotResponse) {
            BotResponse request = (BotResponse) obj;
            return request.messageId == messageId;
        }
        return false;
    }
}
