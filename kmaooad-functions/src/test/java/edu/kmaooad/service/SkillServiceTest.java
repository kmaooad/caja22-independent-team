package edu.kmaooad.service;

import edu.kmaooad.DTO.SkillDTO;
import edu.kmaooad.models.Skill;
import edu.kmaooad.repository.SkillRepository;
import lombok.AllArgsConstructor;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class SkillServiceTest {
    @Mock
    private SkillRepository skillRepository;
    @InjectMocks
    private SkillService skillService;
    public final List<Skill> PROJECT_LIST = initSkill();

    SkillDTO skillDTO = new SkillDTO("skillId","skillName","parentSkillID");

    @BeforeEach
    public void initDB() {
        skillRepository.saveAll(PROJECT_LIST);
    }

    @AfterEach
    public void clearDB() {
        skillRepository.deleteAll();
    }

    private List<Skill> initSkill() {
        skillRepository = Mockito.mock(SkillRepository.class);
        skillService = new SkillService(skillRepository);

        Skill parentSkill = new Skill();
        parentSkill.setSkillID("parentSkillID");
        parentSkill.setSkillName("parentSkillName");
        parentSkill.setParentSkill(null);

        Skill childSkill = new Skill();
        childSkill.setSkillID("skillId");
        childSkill.setSkillName("skillName");
        childSkill.setParentSkill(parentSkill);
        return Stream.of(parentSkill , childSkill).collect(Collectors.toList());
    }

    @Test
    public void creteSkill() {
        Skill expected = PROJECT_LIST.get(1);
        expected.setParentSkill(null);
        Optional<Skill> skillOptional = skillService.createSkill(skillDTO);
        assertTrue(skillOptional.isPresent());
        assertEquals(expected, skillOptional.get());
    }

}
