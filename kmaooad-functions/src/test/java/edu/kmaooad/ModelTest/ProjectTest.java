package edu.kmaooad.ModelTest;

import edu.kmaooad.models.Project;
import edu.kmaooad.models.Skill;
import edu.kmaooad.models.SkillSet;
import edu.kmaooad.models.Topic;
import edu.kmaooad.service.TopicService;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectTest {
    Set<Skill> newSkillSet = new HashSet<>();
    Set<Topic> topics = Stream.of(new Topic("1", "topic")).collect(Collectors.toSet());
    Set<Skill> skills = Stream.of(new Skill("2", "skill")).collect(Collectors.toSet());
    Set<SkillSet> skillSets = Stream.of(new SkillSet("3", "skill set", newSkillSet)).collect(Collectors.toSet());
    Project project = new Project("1234", "independent", "independent description", topics, skills, skillSets);

    @Test
    void testProjectBuilder() {
        Project projectBuild =
                Project.builder()
                        .projectID("1234")
                        .projectTitle("independent")
                        .projectDescription("independent description")
                        .topics(topics)
                        .skills(skills)
                        .skillSets(skillSets).build();


        assertEquals(project, projectBuild);

    }

    @Test
    void getProjectId() {
        assertEquals("1234", project.getProjectID());
    }

    @Test
    void setProjectId() {
        project.setProjectID("newID");
        assertEquals("newID", project.getProjectID());
    }

    @Test
    void getProjectTitle() {
        assertEquals("independent", project.getProjectTitle());
    }

    @Test
    void setProjectTitle() {
        project.setProjectTitle("new title");
        assertEquals("new title", project.getProjectTitle());
    }

    @Test
    void getProjectDescription() {
        assertEquals("independent description", project.getProjectDescription());
    }

    @Test
    void setProjectDescription() {
        project.setProjectDescription("new desc");
        assertEquals("new desc", project.getProjectDescription());
    }

    @Test
    void getProjectTopic() {
        Topic res = project.getTopics().stream().findFirst().orElse(null);
        assertEquals(new Topic("1", "topic"), res);
    }

    @Test
    void setProjectTopic() {
        Set<Topic> topics = Stream.of(new Topic("2", "new topic")).collect(Collectors.toSet());
        project.setTopics(topics);
        assertEquals(topics, project.getTopics());
    }

    @Test
    void getProjectSkill() {
        Skill res = project.getSkills().stream().findFirst().orElse(null);
        assertEquals(new Skill("2", "skill"), res);
    }

    @Test
    void setProjectSkill() {
        Set<Skill> skills = Stream.of(new Skill("2", "new skill")).collect(Collectors.toSet());
        project.setSkills(skills);
        assertEquals(skills, project.getSkills());
    }

    @Test
    void getProjectSetSkill() {
        SkillSet res = project.getSkillSets().stream().findFirst().orElse(null);
        assertEquals(new SkillSet("3", "skill set", newSkillSet), res);
    }

    @Test
    void setProjectSetSkill() {
        Set<SkillSet> skillSets = Stream.of(new SkillSet("4", "new skill set", newSkillSet)).collect(Collectors.toSet());
        project.setSkillSets(skillSets);
        assertEquals(skillSets, project.getSkillSets());
    }


    @Test
    void projectToString() {
        assertEquals("Project(projectID=1234, projectTitle=independent, projectDescription=independent description, topics=[Topic(topicID=1, topicName=topic, parentTopic=null)], skills=[Skill(skillID=2, skillName=skill, parentSkill=null)], skillSets=[SkillSet(skillSetID=3, skillSetName=skill set, skills=[])])", project.toString());
    }

    @Test
    void testProjectEqualsAndHashCode() {
        Project equalProject = new Project("1234", "independent", "independent description", topics, skills, skillSets);

        assertEquals(project, equalProject);
        assertEquals(project.hashCode(), equalProject.hashCode());
    }

}
