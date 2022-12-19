package edu.kmaooad.service;

import edu.kmaooad.DTO.SkillSetDTO;
import edu.kmaooad.exeptions.SkillNotFoundException;
import edu.kmaooad.exeptions.SkillSetNotFoundException;
import edu.kmaooad.models.Skill;
import edu.kmaooad.models.SkillSet;
import edu.kmaooad.repository.SkillSetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SkillSetService {
    @Autowired
    private SkillSetRepository skillSetRepository;
    @Autowired
    private SkillService skillService;

    public SkillSet createSkillSet(SkillSetDTO dto) {
        return skillSetRepository.save(mapFromDtoToEntity(dto));
    }

    public boolean deleteSkillSet(String skillSetId) {
        skillSetRepository.deleteById(skillSetId);
        return !exist(skillSetId);
    }

    public boolean updateSkillSet(String id, SkillSetDTO dto) {
        Optional<SkillSet> skillSet = skillSetRepository.findById(id);
        if (skillSet.isPresent()) {
            SkillSet updated = mapFromDtoToEntity(dto);
            updated.setSkillSetID(id);
            skillSetRepository.save(updated);
            return updated.equals(findSkillSetById(updated.getSkillSetID()));
        } else {
            throw new SkillSetNotFoundException(id);
        }
    }

    public void addSkillToSkillSet(String skillSetId, String skillId) {
        Skill skillToAdd = skillService.findSkillById(skillId);
        SkillSet skillSet = skillSetRepository.findById(skillSetId)
                .orElseThrow(() -> new SkillSetNotFoundException(skillSetId));
        skillSet.getSkills().add(skillToAdd);
        skillSetRepository.save(skillSet);
    }

    public void removeSkillFromSkillSet(String skillSetId, String skillId) {
        Skill skillToRemove = skillService.findSkillById(skillId);
        SkillSet skillSet = skillSetRepository.findById(skillSetId)
                .orElseThrow(() -> new SkillSetNotFoundException(skillSetId));
        skillSet.getSkills().remove(skillToRemove);
        skillSetRepository.save(skillSet);
    }

    protected SkillSet mapFromDtoToEntity(SkillSetDTO skillSetDTO) {
        SkillSet res = new SkillSet();
        res.setSkillSetID(skillSetDTO.getSkillSetId());
        res.setSkillSetName(skillSetDTO.getSkillSetName());
        Set<Skill> skillList = skillSetDTO.getSkillIds().stream()
                .map(skillId -> {
                    try {
                        return skillService.findSkillById(skillId);
                    } catch (SkillNotFoundException skillNotFoundException) {
                        log.warn("Skill with id {} will not be added to skill set as it doesn't exists", skillId);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        res.setSkills(skillList);
        return res;
    }

    public SkillSet findSkillSetById(String skillSetId) {
        return skillSetRepository.findById(skillSetId)
                .orElseThrow(() -> new SkillSetNotFoundException(skillSetId));
    }

    public Optional<SkillSet> findOptionalSkillSetById(String skillSetId) {
        return skillSetRepository.findById(skillSetId);
    }

    public List<SkillSet> findAllSkillSets() {
        return skillSetRepository.findAll();
    }

    public boolean exist(String id) {
        return skillSetRepository.existsById(id);
    }
}
