package edu.kmaooad.repository;

import edu.kmaooad.models.Skill;
import edu.kmaooad.models.SkillSet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SkillSetRepository extends MongoRepository<SkillSet, String> {
    Optional<SkillSet> findSkillSetBySkillSetName(String skillSetName);
    Optional<SkillSet> findSkillSetBySkillsContaining(Skill skill);

}
