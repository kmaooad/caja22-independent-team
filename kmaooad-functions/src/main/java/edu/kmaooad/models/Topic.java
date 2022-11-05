package edu.kmaooad.models;

import com.sun.istack.internal.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Builder
@Getter
@Setter
@AllArgsConstructor
@ToString
public class Topic {
    @NotNull
    @Id
    private String topicID;
    @NotNull
    private String topicName;

    @DBRef
    private Topic parentTopic;

    Topic (String topicID, String topicName){
        this.topicID = topicID;
        this.topicName = topicName;
    }

}
