package edu.kmaooad.service;

import edu.kmaooad.DTO.TopicDTO;
import edu.kmaooad.exeptions.TopicNotFoundException;
import edu.kmaooad.models.Skill;
import edu.kmaooad.models.Topic;
import edu.kmaooad.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TopicService {
    @Autowired
    TopicRepository topicRepository;

    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public Optional<Topic> createTopic(TopicDTO dto) {
        Topic topic = new Topic();
        topic.setTopicID(dto.getTopicId());
        topic.setTopicName(dto.getTopicName());
        Optional<Topic> parentTopic = topicRepository.findTopicByTopicID(dto.getParentTopicId());
        parentTopic.ifPresent(topic::setParentTopic);
        topicRepository.save(topic);
        return Optional.of(topic);
    }

    public boolean deleteTopic(String topicId) {
        Optional<Topic> topic = topicRepository.findTopicByTopicID(topicId);
        if (topic.isPresent()) {
            topicRepository.delete(topic.get());
            return !exist(topic.get().getTopicID());
        } else {
            throw new TopicNotFoundException("Topic not found");
        }
    }

    public boolean updateTopic(String id, TopicDTO dto) {
        Optional<Topic> topic = topicRepository.findTopicByTopicID(id);
        if (topic.isPresent()) {
            topic.get().setTopicName(dto.getTopicName());
            Optional<Topic> parentTopic = topicRepository.findTopicByTopicID(dto.getParentTopicId());

                if (!parentTopic.isPresent()) {
                    throw new TopicNotFoundException("Topic not found");
                }
            topic.get().setParentTopic(parentTopic.get());
            Topic updatedTopic = topic.get();
            topicRepository.save(updatedTopic);
            return findTopicById(updatedTopic.getTopicID()).equals(updatedTopic);
        }
        else{
            throw new TopicNotFoundException("Topic not found");
        }

    }

    public Topic findTopicById(String topicId) {
        return topicRepository.findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException("Topic with id " + topicId + " not found"));
    }
    public Optional<Topic> findOptionalTopicById(String TopicId) {
        return topicRepository.findById(TopicId);
    }

    public List<Topic> findAllTopics() {
        return topicRepository.findAll();
    }
    public boolean exist(String id) {
        return topicRepository.existsById(id);
    }
}
