package edu.kmaooad.ModelTest;

import edu.kmaooad.models.Skill;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SkillTest {

    Skill skill = new Skill("1234","junit-test", new Skill("1233","test"));

    // toString, equals
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

}
