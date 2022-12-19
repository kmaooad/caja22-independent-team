package edu.kmaooad.repository;

import edu.kmaooad.DTO.SkillDTO;
import edu.kmaooad.DTO.TopicDTO;
import edu.kmaooad.exeptions.SkillNotFoundException;
import edu.kmaooad.exeptions.TopicNotFoundException;
import edu.kmaooad.models.Skill;
import edu.kmaooad.service.SkillService;
import edu.kmaooad.service.TopicService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
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
    @Test
    public void createSkill() {

        SkillService skillService = new SkillService(skillRepository);
        SkillDTO dto = new SkillDTO("topic");
        dto.setSkillId(String.valueOf(12));
        skillService.createSkill(dto);
        assertNotNull(skillService.findSkillById(String.valueOf(12)));
    }

    @Test
    public void createTopic2() {

        SkillService topicService = new SkillService(skillRepository);
        SkillDTO dto = new SkillDTO("16", "fd", "fdfd");
        assertEquals("16", dto.getSkillId());
        assertTrue(dto.getSkillId().equals("16"));
        topicService.createSkill(dto);
        assertNotNull(topicService.findSkillById(String.valueOf(16)));
    }

    @Test
    public void createTopic3() {

        SkillService skillService = new SkillService(skillRepository);
        SkillDTO dto = new SkillDTO("16", "fd", "fdfd");
        assertNotEquals("15", dto.getSkillName());
        assertFalse(dto.getSkillId().equals("1532"));
        skillService.createSkill(dto);
        SkillNotFoundException thrown = Assertions.assertThrows(SkillNotFoundException.class, () -> {
            skillService.findSkillById(String.valueOf(124));
        });
    }

    @Test
    public void testDeleteTopic() {

        SkillService skillService = new SkillService(skillRepository);
        SkillDTO dto = new SkillDTO("16", "fd", "fdfd");
        skillService.createSkill(dto);
        SkillNotFoundException thrown = Assertions.assertThrows(SkillNotFoundException.class, () -> {
            skillService.updateSkill(String.valueOf(16),dto);
        });

        assertNotEquals(skillService.findSkillById(String.valueOf(16)), dto);

        skillService.deleteSkill(String.valueOf(16));
        SkillNotFoundException thrown2 = Assertions.assertThrows(SkillNotFoundException.class, () -> {
            skillService.findSkillById(String.valueOf(16));
        });

    }

    private List<Skill> initSkill() {
        Skill skill1 = new Skill();
        skill1.setSkillID("skillId");
        skill1.setSkillName("skill");
        return Stream.of(skill1).collect(Collectors.toList());
    }
}
