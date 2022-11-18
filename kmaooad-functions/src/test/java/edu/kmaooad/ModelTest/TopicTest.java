package edu.kmaooad.ModelTest;

import edu.kmaooad.models.Skill;
import edu.kmaooad.models.Topic;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TopicTest {

    Topic topic = new Topic("1234","junit-test", new Topic("1233","test"));

    @Test
    void testTopicBuilder(){
        Topic topicBuilder = Topic.builder()
                .topicID("1234")
                .topicName("junit-test")
                .parentTopic(Topic.builder().topicID("1233").topicName("test").build())
                .build();

        assertEquals(topic,topicBuilder);
    }
    @Test
    void getTopicId(){
        assertEquals("1234", topic.getTopicID());
    }

    @Test
    void setTopicId(){
        topic.setTopicID("newID");
        assertEquals("newID", topic.getTopicID());
    }

    @Test
    void getTopicName(){
        assertEquals("junit-test", topic.getTopicName());
    }

    @Test
    void setTopicName(){
        topic.setTopicName("new name");
        assertEquals("new name", topic.getTopicName());
    }

    @Test
    void getParentTopic(){
        assertEquals(new Topic("1233","test"), topic.getParentTopic());
    }

    @Test
    void setParentTopic(){
        topic.setParentTopic(new Topic("new parent","new parent"));
        assertEquals(new Topic("new parent","new parent"), topic.getParentTopic());
    }

    @Test
    void topicToString(){
        assertEquals("Topic(topicID=1234, topicName=junit-test, parentTopic=Topic(topicID=1233, topicName=test, parentTopic=null))", topic.toString());
    }

    @Test
    void testTopicEqualsAndHashCode(){
        Topic equalTopic = new Topic("1234","junit-test", new Topic("1233","test"));

        assertTrue(topic.equals(equalTopic));
        assertTrue(topic.hashCode() == equalTopic.hashCode());
    }

}
