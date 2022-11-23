package edu.kmaooad.repository;

import edu.kmaooad.models.Project;
import edu.kmaooad.models.Skill;
import edu.kmaooad.models.SkillSet;
import edu.kmaooad.models.Topic;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
public class ProjectRepositoryTest {
    @Autowired
    ProjectRepository projectRepository;

    public final List<Project> PROJECT_LIST = initProjectList();

    @BeforeEach
    public void initDB() {
        projectRepository.saveAll(PROJECT_LIST);
    }

    @AfterEach
    public void clearDB() {
        projectRepository.deleteAll();
    }

    @Test
    public void findProject_byExistingTitle_shouldReturnProject() {
        Project expected = PROJECT_LIST.get(0);
        Optional<Project> skillSetOptional = projectRepository.findProjectByProjectTitle(expected.getProjectTitle());
        assertTrue(skillSetOptional.isPresent());
        assertEquals(expected, skillSetOptional.get());
    }

    @Test
    public void findProject_byNonExistentTitle_shouldReturnEmptyOptional() {
        String nonexistent = "some name";
        Optional<Project> skillSetOptional = projectRepository.findProjectByProjectTitle(nonexistent);
        assertFalse(skillSetOptional.isPresent());
    }

    private List<Project> initProjectList() {
        Project project1 = new Project();
        project1.setProjectID("projectId");
        project1.setProjectTitle("Project");
        project1.setProjectDescription("Description");
        return Stream.of(project1).collect(Collectors.toList());
    }
}
