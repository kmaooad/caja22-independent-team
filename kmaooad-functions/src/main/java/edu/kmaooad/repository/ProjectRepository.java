package edu.kmaooad.repository;

import edu.kmaooad.models.Project;
import edu.kmaooad.models.Skill;
import edu.kmaooad.models.SkillSet;
import edu.kmaooad.models.Topic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends MongoRepository<Project, String> {
    Optional<Project> findProjectByProjectTitle(String projectTitle);
    Optional<Project> findProjectByTopicsContains(Topic topic);
    Optional<Project> findProjectBySkillsContains(Skill skill);
    Optional<Project> findProjectBySkillSetsContains(SkillSet skillSet);
}
