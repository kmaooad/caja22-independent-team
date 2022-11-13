package edu.kmaooad.repository;

import edu.kmaooad.models.Skill;
import edu.kmaooad.models.Topic;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SkillRepository extends MongoRepository<Skill , String> {

    Optional<Skill> findSkillBySkillID(String id);
}
