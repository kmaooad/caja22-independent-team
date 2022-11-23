package edu.kmaooad.repository;

import edu.kmaooad.models.Skill;
import edu.kmaooad.models.SkillSet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
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
public class SkillSetRepositoryTest {

    @Autowired
    SkillSetRepository skillSetRepository;

    public final List<SkillSet> SKILL_SETS = initSkillSetList();

    @BeforeEach
    public void initDB() {
        skillSetRepository.saveAll(SKILL_SETS);
    }

    @AfterEach
    public void clearDB() {
        skillSetRepository.deleteAll();
    }

    @Test
    public void findSkillSet_byExistingName_shouldReturnSkill() {
        SkillSet expected = SKILL_SETS.get(0);
        Optional<SkillSet> skillSetOptional = skillSetRepository.findSkillSetBySkillSetName(expected.getSkillSetName());
        assertTrue(skillSetOptional.isPresent());
        assertEquals(expected, skillSetOptional.get());
    }

    @Test
    public void findSkillSet_byNonExistentName_shouldReturnEmptyOptional() {
        String nonexistent = "some name";
        Optional<SkillSet> skillSetOptional = skillSetRepository.findSkillSetBySkillSetName(nonexistent);
        assertFalse(skillSetOptional.isPresent());
    }

    private List<SkillSet> initSkillSetList() {
        SkillSet skillSet = new SkillSet();
        skillSet.setSkillSetID("skillsetId");
        skillSet.setSkillSetName("SkillSet1");
        SkillSet skillSet1 = new SkillSet();
        skillSet1.setSkillSetID("skillsetId1");
        skillSet1.setSkillSetName("SkillSet2");
        return Stream.of(skillSet1, skillSet).collect(Collectors.toList());
    }
}
