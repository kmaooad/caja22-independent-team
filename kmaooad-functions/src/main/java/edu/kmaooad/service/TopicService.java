package edu.kmaooad.service;

import edu.kmaooad.DTO.TopicDTO;
import edu.kmaooad.exeptions.TopicNotFoundException;
import edu.kmaooad.models.Topic;
import edu.kmaooad.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class TopicService {
    @Autowired
    TopicRepository topicRepository;

    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public Topic createTopic(String topicName, String parentId) {
        Topic topic = new Topic();
        topic.setTopicName(topicName);
        Optional<Topic> parentTopic = topicRepository.findTopicByTopicID(parentId);
        if (parentTopic.isPresent()) {
            topic.setParentTopic(parentTopic.get());
        }
        topicRepository.save(topic);
        return topic;
    }

    public void deleteTopic(String topicId) {
        Optional<Topic> topic = topicRepository.findTopicByTopicID(topicId);
        if (topic.isPresent()) {
            topicRepository.delete(topic.get());
        } else {
            throw new TopicNotFoundException("Topic not found");
        }
    }

    public Topic updateTopic(String id, String topicName, String parentId) {
        Optional<Topic> topic = topicRepository.findTopicByTopicID(id);
        if (topic.isPresent()) {
            topic.get().setTopicName(topicName);
            Optional<Topic> parentTopic = topicRepository.findTopicByTopicID(parentId);

                if (!parentTopic.isPresent()) {
                    throw new TopicNotFoundException("Topic not found");
                }
            topic.get().setParentTopic(parentTopic.get());
            topicRepository.save(topic.get());
            return topic.get();
        }
        else{
            throw new TopicNotFoundException("Topic not found");
        }

    }



}
