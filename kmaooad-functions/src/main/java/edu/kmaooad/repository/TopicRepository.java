package edu.kmaooad.repository;

import edu.kmaooad.models.Skill;
import edu.kmaooad.models.Topic;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TopicRepository extends MongoRepository<Topic, String> {

    Optional<Topic> findTopicByTopicID(String id);
}
