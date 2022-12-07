package edu.kmaooad.DTO;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class TopicDTO {
    private String topicId;
    private String topicName;

    private String parentTopicId;
    public TopicDTO( String topicName){
        this.topicName = topicName;
    }

}
