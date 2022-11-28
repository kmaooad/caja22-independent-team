package edu.kmaooad.ModelTest;

import edu.kmaooad.models.Skill;
import edu.kmaooad.models.SkillSet;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

//null?
public class SkillSetTest {

    SkillSet skillSet = new SkillSet("1234", "test", new HashSet<>());

    @Test
    void testSkillSetBuilder(){
        SkillSet skillSetBuilder = SkillSet.builder()
                .skillSetID("1234")
                .skillSetName("test")
                .skills(new HashSet<>()).build();

        assertEquals(skillSet,skillSetBuilder);
    }
    @Test
    void getSkillSetId(){
        assertEquals("1234", skillSet.getSkillSetID());
    }

    @Test
    void setSkillSetId(){
        skillSet.setSkillSetID("newID");
        assertEquals("newID", skillSet.getSkillSetID());
    }

    @Test
    void getSkillName(){
        assertEquals("test", skillSet.getSkillSetName());
    }

    @Test
    void setSkillName(){
        skillSet.setSkillSetName("new name");
        assertEquals("new name", skillSet.getSkillSetName());
    }

    @Test
    void getSkillSet(){
        assertEquals( new HashSet<>(), skillSet.getSkills());
    }

    @Test
    void setSkillSet(){

        Set<Skill> newSkillSet = new HashSet<>();
        newSkillSet.add(new Skill("1234","test"));

        skillSet.setSkills(newSkillSet);
        assertEquals(newSkillSet, skillSet.getSkills());
    }

    @Test
    void addSkill_test(){
        skillSet.addSkill(new Skill("1234","test"));

        Set<Skill> check = new HashSet<>();
        check.add(new Skill("1234","test"));
        assertEquals(check,skillSet.getSkills());

        SkillSet test = new SkillSet("1234", "test", null);
        test.addSkill(new Skill("1234","test"));

        assertEquals(check,test.getSkills());


    }

    @Test
    void removeSkill_test(){
        skillSet.addSkill(new Skill("1234","test-1"));
        skillSet.addSkill(new Skill("1235","test-2"));


        Set<Skill> check = new HashSet<>();
        check.add(new Skill("1234","test-1"));

        skillSet.removeSkill(new Skill("1235","test-2"));

        assertEquals(check,skillSet.getSkills());

        SkillSet test = new SkillSet("1234", "test", null);
        test.removeSkill(new Skill("1234","test"));

        assertNull(test.getSkills());
    }

    @Test
    void skillSetToString(){
        assertEquals("SkillSet(skillSetID=1234, skillSetName=test, skills=[])", skillSet.toString());
    }

    @Test
    void testSkillSetEqualsAndHashCode(){
        SkillSet equalSkillSet = new SkillSet("1234", "test", new HashSet<>());

        assertEquals(skillSet, equalSkillSet);
        assertEquals(skillSet.hashCode(), equalSkillSet.hashCode());
    }
}
