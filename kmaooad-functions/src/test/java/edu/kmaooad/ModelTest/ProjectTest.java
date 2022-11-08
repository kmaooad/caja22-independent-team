package edu.kmaooad.ModelTest;

import edu.kmaooad.models.Project;
import edu.kmaooad.models.Skill;
import edu.kmaooad.models.SkillSet;
import edu.kmaooad.models.Topic;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectTest {
    Set<Skill> newSkillSet = new HashSet<>();

    Project project = new Project("1234","independent","independent description", new Topic("1","topic"),new Skill("2","skill"),new SkillSet("3","skill set", newSkillSet));

    @Test
    void testProjectBuilder(){
        Project projectBuild =
                Project.builder()
                        .projectID("1234")
                        .projectTitle("independent")
                        .projectDescription("independent description")
                        .topic(new Topic("1","topic"))
                        .skill(new Skill("2","skill"))
                        .skillSet(new SkillSet("3","skill set", newSkillSet)).build();


        assertEquals(project,projectBuild);

    }
    @Test
    void getProjectId(){
        assertEquals("1234", project.getProjectID());
    }

    @Test
    void setProjectId(){
        project.setProjectID("newID");
        assertEquals("newID", project.getProjectID());
    }

    @Test
    void getProjectTitle(){
        assertEquals("independent", project.getProjectTitle());
    }

    @Test
    void setProjectTitle(){
        project.setProjectTitle("new title");
        assertEquals("new title", project.getProjectTitle());
    }

    @Test
    void getProjectDescription(){
        assertEquals("independent description", project.getProjectDescription());
    }

    @Test
    void setProjectDescription(){
        project.setProjectDescription("new desc");
        assertEquals("new desc", project.getProjectDescription());
    }

    @Test
    void getProjectTopic(){
        assertEquals( new Topic("1","topic"), project.getTopic());
    }

    @Test
    void setProjectTopic(){
        project.setTopic( new Topic("2","new topic"));
        assertEquals( new Topic("2","new topic"), project.getTopic());
    }

    @Test
    void getProjectSkill(){
        assertEquals( new Skill("2","skill"), project.getSkill());
    }

    @Test
    void setProjectSkill(){
        project.setSkill( new Skill("2","new skill"));
        assertEquals( new Skill("2","new skill"), project.getSkill());
    }

    @Test
    void getProjectSetSkill(){
        assertEquals(new SkillSet("3","skill set", newSkillSet), project.getSkillSet());
    }

    @Test
    void setProjectSetSkill(){
        project.setSkillSet( new SkillSet("4","new skill set", newSkillSet));
        assertEquals( new SkillSet("4","new skill set", newSkillSet), project.getSkillSet());
    }


    @Test
    void projectToString(){
        assertEquals("Project(projectID=1234, projectTitle=independent, projectDescription=independent description, topic=Topic(topicID=1, topicName=topic, parentTopic=null), skill=Skill(skillID=2, skillName=skill, parentSkill=null), skillSet=SkillSet(skillSetID=3, skillSetName=skill set, skillSet=[]))", project.toString());
    }

    @Test
    void testProjectEqualsAndHashCode(){
        Project equalProject = new Project("1234","independent","independent description", new Topic("1","topic"),new Skill("2","skill"),new SkillSet("3","skill set", newSkillSet));

        assertTrue(project.equals(equalProject));
        assertTrue(project.hashCode() == equalProject.hashCode());
    }

}
