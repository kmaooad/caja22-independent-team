package edu.kmaooad.service;

import edu.kmaooad.DTO.SkillSetDTO;
import edu.kmaooad.DTO.TopicDTO;
import edu.kmaooad.exeptions.TopicNotFoundException;
import edu.kmaooad.models.SkillSet;
import edu.kmaooad.models.Topic;
import edu.kmaooad.repository.TopicRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TopicServiceTest {
    @Mock
    private TopicRepository topicRepository;
    @Mock
    private SkillService skillService;
    @Mock
    private SkillSetService skillSetService;
    @InjectMocks
    private TopicService topicService;

    private static final TopicNotFoundException EXCEPTION = new TopicNotFoundException("Topic not found");

    private static final Topic TOPIC = initTestObject();
    private static final TopicDTO TOPIC_DTO = initTestDto();
    @Test
    public void updateParentTopic(){
        Topic  topic = initTestObject();
        Optional<Topic>  parentTopic = initParentTestObject();

        if (!parentTopic.isPresent()) {
            throw new TopicNotFoundException("Topic not found");
        }
        topic.setParentTopic(parentTopic.get());
        topicRepository.save(topic);
        assertEquals(topic , new Topic("1","Name1",parentTopic.get()));
    }
    @Test
    public void createTopic(){
        Optional<Topic> topic = Optional.of(new Topic("1", "Name1", null));
        Optional<Topic> createdTopic = topicService.createTopic(initTestDto());
        assertEquals(topic,createdTopic);

    }
    @Test
    void deleteTopic() {
        assertDoesNotThrow(() -> topicService.deleteTopic(TOPIC_DTO.getTopicId()));
    }

    @Test
    void deleteTopicException() {
        doThrow(EXCEPTION).when(topicRepository).deleteById(any());

        TopicNotFoundException res = assertThrows(EXCEPTION.getClass(), () -> topicService.deleteTopic(TOPIC_DTO.getTopicId()));

        assertSame(EXCEPTION, res);
    }
    @Test
    void findTopic() {
        when(topicRepository.findById(any())).thenReturn(Optional.of(TOPIC));

        Topic res = assertDoesNotThrow(() -> topicService.findTopicById(TOPIC.getTopicID()));
        assertEquals(TOPIC, res);
    }
    private static Topic initTestObject() {
        return new Topic("1", "Name1", null);
    }
    private static Optional<Topic> initParentTestObject() {
        return Optional.of(new Topic("2", "Name2", null));
    }
    private static TopicDTO initTestDto() {
        return new TopicDTO("1", "Name1", "null");
    }

}
