package edu.kmaooad.DTO;

import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;


@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class TopicDTO {

    @NotNull
    private String topicName;

    @DBRef
    private TopicDTO parentTopic;

    public TopicDTO( String topicName){
        this.topicName = topicName;
    }

}
