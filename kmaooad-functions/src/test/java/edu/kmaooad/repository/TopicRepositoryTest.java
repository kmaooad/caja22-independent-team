package edu.kmaooad.repository;

import edu.kmaooad.models.Skill;
import edu.kmaooad.models.Topic;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataMongoTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
public class TopicRepositoryTest {
    @Autowired
    TopicRepository topicRepository;

    public final List<Topic> PROJECT_LIST = initSkill();

    @BeforeEach
    public void initDB() {
        topicRepository.saveAll(PROJECT_LIST);
    }

    @AfterEach
    public void clearDB() {
        topicRepository.deleteAll();
    }

    @Test
    public void findTopicByName() {
        Topic expected = PROJECT_LIST.get(0);
        Optional<Topic> topicOptional = topicRepository.findTopicByTopicName(expected.getTopicName());
        assertTrue(topicOptional.isPresent());
        assertEquals(expected, topicOptional.get());
    }

    private List<Topic> initSkill() {
        Topic topic1 = new Topic();
        topic1.setTopicID("skillId");
        topic1.setTopicName("skill");
        return Stream.of(topic1).collect(Collectors.toList());
    }
}
