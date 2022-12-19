package edu.kmaooad.repository;

import edu.kmaooad.DTO.TopicDTO;
import edu.kmaooad.exeptions.TopicNotFoundException;
import edu.kmaooad.models.Skill;
import edu.kmaooad.models.Topic;
import edu.kmaooad.service.SkillService;
import edu.kmaooad.service.TopicService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

    @Test
    public void createTopic() {

        TopicService topicService = new TopicService(topicRepository);
        TopicDTO dto = new TopicDTO("topic");
        dto.setTopicId(String.valueOf(12));
        topicService.createTopic(dto);
        assertNotNull(topicService.findTopicById(String.valueOf(12)));
    }

    @Test
    public void createTopic2() {

        TopicService topicService = new TopicService(topicRepository);
        TopicDTO dto = new TopicDTO("16", "fd", "fdfd");
        assertEquals("16", dto.getTopicId());
        assertTrue(dto.getTopicId().equals("16"));
        topicService.createTopic(dto);
        assertNotNull(topicService.findTopicById(String.valueOf(16)));
    }

    @Test
    public void createTopic3() {

        TopicService topicService = new TopicService(topicRepository);
        TopicDTO dto = new TopicDTO("16", "fd", "fdfd");
        assertNotEquals("15", dto.getTopicId());
        assertFalse(dto.getTopicId().equals("15"));
        topicService.createTopic(dto);
        TopicNotFoundException thrown = Assertions.assertThrows(TopicNotFoundException.class, () -> {
            topicService.findTopicById(String.valueOf(124));
        });
    }

    @Test
    public void testDeleteTopic() {

        TopicService topicService = new TopicService(topicRepository);
        TopicDTO dto = new TopicDTO("16", "fd", "fdfd");
        topicService.createTopic(dto);
        TopicNotFoundException thrown = Assertions.assertThrows(TopicNotFoundException.class, () -> {
            topicService.updateTopic(String.valueOf(16),dto);
        });

        assertNotEquals(topicService.findTopicById(String.valueOf(16)), dto);

        topicService.deleteTopic(String.valueOf(16));
        TopicNotFoundException thrown2 = Assertions.assertThrows(TopicNotFoundException.class, () -> {
            topicService.findTopicById(String.valueOf(16));
        });

    }


    private List<Topic> initSkill() {
        Topic topic1 = new Topic();
        topic1.setTopicID("skillId");
        topic1.setTopicName("skill");
        return Stream.of(topic1).collect(Collectors.toList());
    }
}
