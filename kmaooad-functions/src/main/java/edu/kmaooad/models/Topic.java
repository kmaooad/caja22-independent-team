package edu.kmaooad.models;

import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Builder
@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Topic {
    @NotNull
    @Id
    private String topicID;
    @NotNull
    private String topicName;

    @DBRef
    private Topic parentTopic;

    public Topic(String topicID, String topicName){
        this.topicID = topicID;
        this.topicName = topicName;
    }

}
