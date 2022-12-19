package edu.kmaooad.service;

import edu.kmaooad.DTO.SkillSetDTO;
import edu.kmaooad.DTO.TopicDTO;
import edu.kmaooad.exeptions.TopicNotFoundException;
import edu.kmaooad.models.SkillSet;
import edu.kmaooad.models.Topic;
import edu.kmaooad.repository.TopicRepository;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
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
    @Order(1)
    public void createTopic(){
        Optional<Topic> topic = Optional.of(new Topic("1", "Name1", null));
        Optional<Topic> createdTopic = topicService.createTopic(initTestDto());
        assertEquals(topic,createdTopic);

    }
    @Test
    void deleteTopic_Ok() {
        when(topicRepository.findTopicByTopicID(TOPIC_DTO.getTopicId())).thenReturn(Optional.of(TOPIC));
        assertTrue(topicService.deleteTopic(TOPIC_DTO.getTopicId()));
    }
    @Test
    void deleteTopic() {
        when(topicRepository.findTopicByTopicID(TOPIC_DTO.getTopicId())).thenReturn(Optional.of(TOPIC));
        when(topicService.deleteTopic(TOPIC_DTO.getTopicId())).thenReturn(true);
        assertDoesNotThrow(() -> topicService.deleteTopic(TOPIC_DTO.getTopicId()));
    }
    @Test
    void deleteTopicException() {
        when(topicRepository.findTopicByTopicID(TOPIC_DTO.getTopicId())).thenReturn(Optional.of(TOPIC));
        when(topicService.deleteTopic(TOPIC_DTO.getTopicId())).thenThrow(EXCEPTION);

        assertThrows(EXCEPTION.getClass(), () -> topicService.deleteTopic(TOPIC_DTO.getTopicId()));
    }
    @Test
    void findTopic() {
        when(topicRepository.findById(any())).thenReturn(Optional.of(TOPIC));

        Topic res = assertDoesNotThrow(() -> topicService.findTopicById(TOPIC.getTopicID()));
        assertEquals(TOPIC, res);
    }
    @Test
    void findOptionalTopicById() {
        when(topicRepository.findById("1")).thenReturn(Optional.of(TOPIC));
        assertEquals(Optional.of(TOPIC), topicService.findOptionalTopicById("1"));
    }

    @Test
    void findAllTopics() {
        when(topicRepository.findAll()).thenReturn(List.of(TOPIC));
        assertEquals(List.of(TOPIC).size(), topicService.findAllTopics().size());
        assertEquals(List.of(TOPIC), topicService.findAllTopics());
    }
    @Test
    void exist_Ok() {
        when(topicRepository.existsById("1")).thenReturn(true);
        assertTrue(topicService.exist("1"));
    }
    @Test
    void exist_Bad() {
        when(topicRepository.existsById("2")).thenReturn(false);
        assertFalse(topicService.exist("2"));
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
