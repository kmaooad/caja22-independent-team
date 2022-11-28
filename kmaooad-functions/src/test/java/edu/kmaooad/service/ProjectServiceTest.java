package edu.kmaooad.service;

import edu.kmaooad.DTO.ProjectDTO;
import edu.kmaooad.DTO.SkillSetDTO;
import edu.kmaooad.exeptions.ProjectNotFoundException;
import edu.kmaooad.exeptions.SkillSetNotFoundException;
import edu.kmaooad.models.Project;
import edu.kmaooad.models.Skill;
import edu.kmaooad.models.SkillSet;
import edu.kmaooad.models.Topic;
import edu.kmaooad.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private SkillService skillService;
    @Mock
    private SkillSetService skillSetService;
    @Mock
    private TopicService topicService;
    @InjectMocks
    private ProjectService projectService;

    private static final Project PROJECT = initTestObject();
    private static final ProjectDTO PROJECT_DTO = initTestDto();
    private static final RuntimeException EXCEPTION = new RuntimeException("Just test");

    @Test
    void saveProject_RepositoryFine_shouldSave() {
        when(projectRepository.save(any())).thenReturn(PROJECT);

        Project res = assertDoesNotThrow(() -> projectService.createProject(PROJECT_DTO));
        assertEquals(PROJECT, res);
    }

    @Test
    void saveProject_RepositoryThrowsException_shouldRethrowException() {
        when(projectRepository.save(any())).thenThrow(EXCEPTION);

        Exception res = assertThrows(EXCEPTION.getClass(), () -> projectService.createProject(PROJECT_DTO));
        assertEquals(EXCEPTION, res);
    }

    @Test
    void deleteProject_RepositoryFine_shouldDelete() {
        assertDoesNotThrow(() -> projectService.deleteProject(PROJECT_DTO.getProjectId()));
    }

    @Test
    void deleteProject_RepositoryThrowsException_shouldRethrowException() {
        doThrow(EXCEPTION).when(projectRepository).deleteById(any());

        Exception res = assertThrows(EXCEPTION.getClass(), () -> projectService.deleteProject(PROJECT_DTO.getProjectId()));
        assertEquals(EXCEPTION, res);
    }

    @Test
    void updateProject_projectExists_shouldUpdate() {
        when(projectRepository.findById(any())).thenReturn(Optional.of(PROJECT));

        ProjectDTO updated = new ProjectDTO(PROJECT_DTO.getProjectId(), "neeew name", "new description",
                PROJECT_DTO.getTopicIds(), PROJECT_DTO.getSkillIds(), PROJECT_DTO.getSkillSetIds());
        Project project = projectService.mapFromDtoToEntity(updated);

        assertDoesNotThrow(() -> projectService.updateProject(PROJECT_DTO.getProjectId(), updated));
        verify(projectRepository).save(project);
    }

    @Test
    void updateProject_projectDoesNotExists_shouldThrowExceptionAndNotUpdate() {
        when(projectRepository.findById(any())).thenReturn(Optional.empty());

        ProjectDTO updated = new ProjectDTO(PROJECT_DTO.getProjectId(), "neeew name", "new description",
                PROJECT_DTO.getTopicIds(), PROJECT_DTO.getSkillIds(), PROJECT_DTO.getSkillSetIds());
        String unexistingId = "unexistingId";
        Exception res = assertThrows(ProjectNotFoundException.class,
                () -> projectService.updateProject(unexistingId, updated));
        verify(projectRepository, never()).save(any());

        assertTrue(res.getMessage().contains(unexistingId));
    }

    private static Project initTestObject() {
        Set<Topic> topicSet = Stream.of(new Topic("1", "topic")).collect(Collectors.toSet());
        Set<Skill> skills = Stream.of(new Skill("1", "skill")).collect(Collectors.toSet());
        Set<SkillSet> skillSet = Stream.of(new SkillSet("1", "skillSet", skills)).collect(Collectors.toSet());
        return new Project("1", "Name", "Description", topicSet, skills, skillSet);
    }

    private static ProjectDTO initTestDto() {
        Set<String> ids = Stream.of("1").collect(Collectors.toSet());
        return new ProjectDTO("1", "Name", "Description", ids, ids, ids);
    }

}
