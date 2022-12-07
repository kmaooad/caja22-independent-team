package edu.kmaooad.repository;

import edu.kmaooad.models.Skill;
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
public class SkillRepositoryTest {
    @Autowired
    SkillRepository skillRepository;

    public final List<Skill> PROJECT_LIST = initSkill();

    @BeforeEach
    public void initDB() {
        skillRepository.saveAll(PROJECT_LIST);
    }

    @AfterEach
    public void clearDB() {
        skillRepository.deleteAll();
    }

    @Test
    public void findSkillbyName() {
        Skill expected = PROJECT_LIST.get(0);
        Optional<Skill> skillOptional = skillRepository.findSkillBySkillName(expected.getSkillName());
        assertTrue(skillOptional.isPresent());
        assertEquals(expected, skillOptional.get());
    }

    @Test
    public void findSkillById() {
        Skill expected = PROJECT_LIST.get(0);
        Optional<Skill> skillOptional = skillRepository.findSkillBySkillID(expected.getSkillID());
        assertTrue(skillOptional.isPresent());
        assertEquals(expected, skillOptional.get());
    }
    private List<Skill> initSkill() {
        Skill skill1 = new Skill();
        skill1.setSkillID("skillId");
        skill1.setSkillName("skill");
        return Stream.of(skill1).collect(Collectors.toList());
    }
}
