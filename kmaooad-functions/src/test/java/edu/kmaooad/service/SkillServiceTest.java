package edu.kmaooad.service;

import edu.kmaooad.DTO.SkillDTO;
import edu.kmaooad.DTO.TopicDTO;
import edu.kmaooad.exeptions.SkillNotFoundException;
import edu.kmaooad.exeptions.TopicNotFoundException;
import edu.kmaooad.models.Skill;
import edu.kmaooad.models.Topic;
import edu.kmaooad.repository.SkillRepository;
import edu.kmaooad.repository.TopicRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SkillServiceTest {
    @Mock
    private SkillRepository skillRepository;

    @Mock
    private SkillSetService skillSetService;
    @InjectMocks
    private SkillService skillService;

    private static final SkillNotFoundException EXCEPTION = new SkillNotFoundException("Skill not found");

    private static final Skill SKILL = initTestObject();

    private static final SkillDTO SKILL_DTO = initTestDto();
    @Test
    public void updateParentSkill(){
        Skill  skill = initTestObject();
        Optional<Skill>  parentSkill = initParentTestObject();

        if (!parentSkill.isPresent()) {
            throw new SkillNotFoundException("Skill not found");
        }
        skill.setParentSkill(parentSkill.get());
        skillRepository.save(skill);
        assertEquals(skill , new Skill("1","Name1",parentSkill.get()));
    }
    @Test
    public void createSkill(){
        Optional<Skill> topic = Optional.of(new Skill("1", "Name1", null));
        Optional<Skill> createdTopic = skillService.createSkill(initTestDto());
        assertEquals(topic,createdTopic);

    }
    @Test
    void deleteSkill() {
        assertDoesNotThrow(() -> skillService.deleteSkill(SKILL_DTO.getSkillId()));
    }

    @Test
    void deleteSkillException() {
        doThrow(EXCEPTION).when(skillRepository).deleteById(any());

        SkillNotFoundException res = assertThrows(EXCEPTION.getClass(), () -> skillService.deleteSkill(SKILL_DTO.getSkillId()));

        assertSame(EXCEPTION, res);
    }
    @Test
    void findSkill() {
        when(skillRepository.findById(any())).thenReturn(Optional.of(SKILL));

        Skill res = assertDoesNotThrow(() -> skillService.findSkillById(SKILL.getSkillID()));
        assertEquals(SKILL, res);
    }
    private static Skill initTestObject() {
        return new Skill("1", "Name1", null);
    }
    private static Optional<Skill> initParentTestObject() {
        return Optional.of(new Skill("2", "Name2", null));
    }
    private static SkillDTO initTestDto() {
        return new SkillDTO("1", "Name1", "null");
    }

}
