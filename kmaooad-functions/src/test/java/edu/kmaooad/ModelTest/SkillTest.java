package edu.kmaooad.ModelTest;

import edu.kmaooad.models.Skill;
import edu.kmaooad.models.Topic;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SkillTest {

    Skill skill = new Skill("1234","junit-test", new Skill("1233","test"));


    @Test
    void testSkillBuilder(){
        Skill skillBuilder = Skill.builder()
                .skillID("1234")
                .skillName("junit-test")
                .parentSkill(Skill.builder().skillID("1233").skillName("test").build())
                .build();

        assertEquals(skill,skillBuilder);
    }
    @Test
    void getSkillId(){
        assertEquals("1234", skill.getSkillID());
    }

    @Test
    void setSkillId(){
        skill.setSkillID("newID");
        assertEquals("newID", skill.getSkillID());
    }

    @Test
    void getSkillName(){
        assertEquals("junit-test", skill.getSkillName());
    }

    @Test
    void setSkillName(){
        skill.setSkillName("new name");
        assertEquals("new name", skill.getSkillName());
    }

    @Test
    void getParentSkill(){
        assertEquals(new Skill("1233","test"), skill.getParentSkill());
    }

    @Test
    void setParentSkill(){
        skill.setParentSkill(new Skill("new parent","new parent"));
        assertEquals(new Skill("new parent","new parent"), skill.getParentSkill());
    }

    @Test
    void skillToString(){
        assertEquals("Skill(skillID=1234, skillName=junit-test, parentSkill=Skill(skillID=1233, skillName=test, parentSkill=null))", skill.toString());
    }

    @Test
    void testSkillEqualsAndHashCode(){
        Skill equalSkill = new Skill("1234","junit-test", new Skill("1233","test"));

        assertTrue(skill.equals(equalSkill));
        assertTrue(skill.hashCode() == equalSkill.hashCode());
    }
}
