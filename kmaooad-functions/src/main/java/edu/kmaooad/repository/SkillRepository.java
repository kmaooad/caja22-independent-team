package edu.kmaooad.repository;

import edu.kmaooad.models.Skill;
import edu.kmaooad.models.SkillSet;
import edu.kmaooad.models.Topic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SkillRepository extends MongoRepository<Skill , String> {
    Optional<Skill> findSkillBySkillName(String skillName);
    Optional<Skill> findSkillBySkillID(String id);
}
