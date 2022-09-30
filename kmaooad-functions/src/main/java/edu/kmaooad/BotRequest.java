package edu.kmaooad;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BotRequest {
    private long update_id;
    private Message message;
}
