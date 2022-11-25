package edu.kmaooad.service;

import edu.kmaooad.DTO.ProjectDTO;
import edu.kmaooad.exeptions.ProjectNotFoundException;
import edu.kmaooad.exeptions.SkillNotFoundException;
import edu.kmaooad.exeptions.SkillSetNotFoundException;
import edu.kmaooad.exeptions.TopicNotFoundException;
import edu.kmaooad.models.Project;
import edu.kmaooad.models.Skill;
import edu.kmaooad.models.SkillSet;
import edu.kmaooad.models.Topic;
import edu.kmaooad.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private SkillService skillService;
    @Autowired
    private SkillSetService skillSetService;
    @Autowired
    private TopicService topicService;

    public Project createProject(ProjectDTO dto) {
        return projectRepository.save(mapFromDtoToEntity(dto));
    }

    public void deleteProject(String projectId) {
        projectRepository.deleteById(projectId);
    }

    public void updateProject(String id, ProjectDTO dto) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isPresent()) {
            Project updated = mapFromDtoToEntity(dto);
            updated.setProjectID(id);
            projectRepository.save(updated);
        } else {
            throw new ProjectNotFoundException(id);
        }
    }

    protected Project mapFromDtoToEntity(ProjectDTO dto) {
        Project res = new Project();
        res.setProjectID(dto.getProjectId());
        res.setProjectTitle(dto.getProjectTitle());
        res.setProjectDescription(dto.getProjectDescription());
        Set<Skill> skills = getSkills(dto);
        Set<SkillSet> skillSets = getSkillSets(dto);
        Set<Topic> topics = getTopics(dto);
        res.setSkills(skills);
        res.setSkillSets(skillSets);
        res.setTopics(topics);
        return res;
    }

    private Set<Topic> getTopics(ProjectDTO dto) {
        return dto.getTopicIds().stream()
                .map(topicId -> {
                    try {
                        return topicService.findTopicById(topicId);
                    } catch (TopicNotFoundException topicNotFoundException) {
                        log.warn("Topic with id {} will not be added to project as it doesn't exists", topicId);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    private Set<SkillSet> getSkillSets(ProjectDTO dto) {
        return dto.getSkillSetIds().stream()
                .map(skillSetId -> {
                    try {
                        return skillSetService.findSkillSetById(skillSetId);
                    } catch (SkillSetNotFoundException skillNotFoundException) {
                        log.warn("Skill set with id {} will not be added to project as it doesn't exists", skillSetId);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    private Set<Skill> getSkills(ProjectDTO dto) {
        return dto.getSkillIds().stream()
                .map(skillId -> {
                    try {
                        return skillService.findSkillById(skillId);
                    } catch (SkillNotFoundException skillNotFoundException) {
                        log.warn("Skill with id {} will not be added to project as it doesn't exists", skillId);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

}
