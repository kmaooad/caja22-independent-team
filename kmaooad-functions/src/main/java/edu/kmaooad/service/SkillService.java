package edu.kmaooad.service;

import edu.kmaooad.DTO.SkillDTO;
import edu.kmaooad.exeptions.SkillNotFoundException;
import edu.kmaooad.exeptions.TopicNotFoundException;
import edu.kmaooad.models.Skill;
import edu.kmaooad.models.Topic;
import edu.kmaooad.repository.SkillRepository;
import edu.kmaooad.repository.TopicRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SkillService {
    @Autowired
    SkillRepository skillRepository;

    public Optional<Skill> createSkill(SkillDTO dto) {
        Skill skill = new Skill();
        skill.setSkillID(dto.getSkillId());
        skill.setSkillName(dto.getSkillName());
        Optional<Skill> parentTopic = skillRepository.findSkillBySkillID(dto.getParentSkillID());
        if (parentTopic.isPresent()) {
            skill.setParentSkill(parentTopic.get());
        }
        skillRepository.save(skill);
        return Optional.of(skill);
    }

    public void deleteSkill(String skillId) {
        Optional<Skill> skill = skillRepository.findSkillBySkillID(skillId);
        if (skill.isPresent()) {
            skillRepository.delete(skill.get());
        } else {
            throw new SkillNotFoundException("Skill not found");
        }
    }
    public Skill updateSkill(String id,SkillDTO dto) {
        Optional<Skill> skill = skillRepository.findSkillBySkillID(id);
        if (skill.isPresent()) {
            skill.get().setSkillName(dto.getSkillName());
            Optional<Skill> parentSkill = skillRepository.findSkillBySkillID(dto.getParentSkillID());
                if (!parentSkill.isPresent()) {
                    throw new SkillNotFoundException("Skill not found");
                }
            skill.get().setParentSkill(parentSkill.get());
            skillRepository.save(skill.get());
            return skill.get();
        }
        else{
            throw new TopicNotFoundException("Skill not found");
        }

    }

    public Skill findSkillById(String skillId) {
        return skillRepository.findById(skillId)
                .orElseThrow(() -> new SkillNotFoundException("Skill with id " + skillId + " not found"));
    }

}
