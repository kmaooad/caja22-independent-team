package edu.kmaooad.repository;

import edu.kmaooad.models.Skill;
import edu.kmaooad.models.Topic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TopicRepository extends MongoRepository<Topic, String> {

    Optional<Topic> findTopicByTopicID(String id);
}
