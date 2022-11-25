package edu.kmaooad.service;

import edu.kmaooad.DTO.SkillSetDTO;
import edu.kmaooad.exeptions.SkillSetNotFoundException;
import edu.kmaooad.models.Skill;
import edu.kmaooad.models.SkillSet;
import edu.kmaooad.repository.SkillSetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SkillSetServiceTest {
    @Mock
    private SkillSetRepository skillSetRepository;
    @Mock
    private SkillService skillService;
    @InjectMocks
    private SkillSetService skillSetService;

    private static final SkillSet SKILL_SET = initTestObject();
    private static final SkillSetDTO SKILL_SET_DTO = initTestDto();
    private static final RuntimeException EXCEPTION = new RuntimeException("Just test");

    @Test
    void saveSkillSet_RepositoryFine_shouldSave() {
        when(skillSetRepository.save(any())).thenReturn(SKILL_SET);

        SkillSet res = assertDoesNotThrow(() -> skillSetService.createSkillSet(SKILL_SET_DTO));
        assertEquals(SKILL_SET, res);
    }

    @Test
    void saveSkillSet_RepositoryThrowsException_shouldRethrowException() {
        when(skillSetRepository.save(any())).thenThrow(EXCEPTION);

        Exception res = assertThrows(EXCEPTION.getClass(), () -> skillSetService.createSkillSet(SKILL_SET_DTO));
        assertEquals(EXCEPTION, res);
    }

    @Test
    void deleteSkillSet_RepositoryFine_shouldDelete() {
        assertDoesNotThrow(() -> skillSetService.deleteSkillSet(SKILL_SET_DTO.getSkillSetId()));
    }

    @Test
    void deleteSkillSet_RepositoryThrowsException_shouldRethrowException() {
        doThrow(EXCEPTION).when(skillSetRepository).deleteById(any());

        Exception res = assertThrows(EXCEPTION.getClass(), () -> skillSetService.deleteSkillSet(SKILL_SET_DTO.getSkillSetId()));
        assertEquals(EXCEPTION, res);
    }

    @Test
    void updateSkillSet_skillSetExists_shouldUpdate() {
        when(skillSetRepository.findById(any())).thenReturn(Optional.of(SKILL_SET));

        SkillSetDTO updated = new SkillSetDTO(SKILL_SET_DTO.getSkillSetId(), "neeew name",
                SKILL_SET_DTO.getSkillIds());
        SkillSet skillSet = skillSetService.mapFromDtoToEntity(updated);

        assertDoesNotThrow(() -> skillSetService.updateSkillSet(SKILL_SET_DTO.getSkillSetId(), updated));
        verify(skillSetRepository).save(skillSet);
    }

    @Test
    void updateSkillSet_skillSetDoesNotExists_shouldThrowExceptionAndNotUpdate() {
        when(skillSetRepository.findById(any())).thenReturn(Optional.empty());

        SkillSetDTO updated = new SkillSetDTO(SKILL_SET_DTO.getSkillSetId(), "neeew name",
                SKILL_SET_DTO.getSkillIds());
        String unexistingId = "unexistingId";
        Exception res = assertThrows(SkillSetNotFoundException.class,
                () -> skillSetService.updateSkillSet(unexistingId, updated));
        verify(skillSetRepository, never()).save(any());

        assertTrue(res.getMessage().contains(unexistingId));
    }

    @Test
    void addSkill_skillAndSkillSetExists_shouldAdd() {
        String skillId = "10";
        when(skillSetRepository.findById(any())).thenReturn(Optional.of(SKILL_SET));
        when(skillService.findSkillById(any())).thenReturn(new Skill(skillId, "name", null));

        assertDoesNotThrow(() -> skillSetService.addSkillToSkillSet(SKILL_SET_DTO.getSkillSetId(), skillId));
        verify(skillSetRepository).save(any());
    }

    @Test
    void addSkill_skillServiceThrowsException_shouldRethrow() {
        when(skillService.findSkillById(any())).thenThrow(EXCEPTION);
        assertThrows(EXCEPTION.getClass(), () -> skillSetService.addSkillToSkillSet(SKILL_SET_DTO.getSkillSetId(), "12"));
        verify(skillSetRepository, never()).save(any());
    }

    @Test
    void addSkill_skillSetDoesNotExists_shouldThrowException() {
        String skillId = "10";
        when(skillSetRepository.findById(any())).thenReturn(Optional.empty());
        when(skillService.findSkillById(any())).thenReturn(new Skill(skillId, "name", null));
        Exception res = assertThrows(SkillSetNotFoundException.class, () -> skillSetService.addSkillToSkillSet(SKILL_SET_DTO.getSkillSetId(), skillId));
        assertTrue(res.getMessage().contains(SKILL_SET_DTO.getSkillSetId()));
        verify(skillSetRepository, never()).save(any());
    }

    @Test
    void removeSkill_skillAndSkillSetExists_shouldAdd() {
        String skillId = "10";
        when(skillSetRepository.findById(any())).thenReturn(Optional.of(SKILL_SET));
        when(skillService.findSkillById(any())).thenReturn(new Skill(skillId, "name", null));

        assertDoesNotThrow(() -> skillSetService.removeSkillFromSkillSet(SKILL_SET_DTO.getSkillSetId(), skillId));
        verify(skillSetRepository).save(any());
    }

    @Test
    void removeSkill_skillServiceThrowsException_shouldRethrow() {
        when(skillService.findSkillById(any())).thenThrow(EXCEPTION);
        assertThrows(EXCEPTION.getClass(), () -> skillSetService.removeSkillFromSkillSet(SKILL_SET_DTO.getSkillSetId(), "12"));
        verify(skillSetRepository, never()).save(any());
    }

    @Test
    void removeSkill_skillSetDoesNotExists_shouldThrowException() {
        String skillId = "10";
        when(skillSetRepository.findById(any())).thenReturn(Optional.empty());
        when(skillService.findSkillById(any())).thenReturn(new Skill(skillId, "name", null));
        Exception res = assertThrows(SkillSetNotFoundException.class, () -> skillSetService.removeSkillFromSkillSet(SKILL_SET_DTO.getSkillSetId(), skillId));
        assertTrue(res.getMessage().contains(SKILL_SET_DTO.getSkillSetId()));
        verify(skillSetRepository, never()).save(any());
    }

    @Test
    void findSkillSetById_skillSetExists_shouldReturnSkillSet() {
        when(skillSetRepository.findById(any())).thenReturn(Optional.of(SKILL_SET));

        SkillSet res = assertDoesNotThrow(() -> skillSetService.findSkillSetById(SKILL_SET_DTO.getSkillSetId()));
        assertEquals(SKILL_SET, res);
    }

    @Test
    void findSkillSetById_skillSetDoesNotExists_shouldThrowException() {
        when(skillSetRepository.findById(any())).thenReturn(Optional.empty());

        String unexistingId = "unexistingId";
        Exception res = assertThrows(SkillSetNotFoundException.class,
                () -> skillSetService.findSkillSetById(unexistingId));

        assertTrue(res.getMessage().contains(unexistingId));
    }

    private static SkillSet initTestObject() {
        Skill skill = new Skill("1", "Skill", null);
        Skill skill1 = new Skill("2", "sk1", skill);
        Set<Skill> skills = Stream.of(skill1, skill).collect(Collectors.toSet());
        return new SkillSet("1", "SkillSetName", skills);
    }

    private static SkillSetDTO initTestDto() {
        Set<String> ids = Stream.of("1", "2").collect(Collectors.toSet());
        return new SkillSetDTO("1", "SkillSetName", ids);
    }

}
