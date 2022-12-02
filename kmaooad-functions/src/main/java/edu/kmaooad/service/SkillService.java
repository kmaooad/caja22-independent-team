package edu.kmaooad.service;

import edu.kmaooad.DTO.SkillDTO;
import edu.kmaooad.exeptions.SkillNotFoundException;
import edu.kmaooad.exeptions.TopicNotFoundException;
import edu.kmaooad.models.Skill;
import edu.kmaooad.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SkillService {
    @Autowired
    SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public Skill createSkill(SkillDTO dto) {
        Skill skill = new Skill();
        skill.setSkillName(dto.getSkillName());
        Optional<Skill> parentTopic = skillRepository.findSkillBySkillID(dto.getParentSkillID());
        if (parentTopic.isPresent()) {
            skill.setParentSkill(parentTopic.get());
        }
        skillRepository.save(skill);
        return skill;
    }

    public boolean deleteSkill(String skillId) {
        Optional<Skill> skill = skillRepository.findSkillBySkillID(skillId);
        if (skill.isPresent()) {
            skillRepository.delete(skill.get());
            return exist(skill.get().getSkillID());
        } else {
            throw new SkillNotFoundException("Skill not found");
        }
    }
    public boolean updateSkill(String id,SkillDTO dto) {
        Optional<Skill> skill = skillRepository.findSkillBySkillID(id);
        if (skill.isPresent()) {
            skill.get().setSkillName(dto.getSkillName());
            Optional<Skill> parentSkill = skillRepository.findSkillBySkillID(dto.getParentSkillID());
            if (!parentSkill.isPresent()) {
                throw new SkillNotFoundException("Skill not found");
            }
            skill.get().setParentSkill(parentSkill.get());
            skillRepository.save(skill.get());
            return exist(skill.get().getSkillID());
        }
        else{
            throw new TopicNotFoundException("Skill not found");
        }

    }

    public Skill findSkillById(String skillId) {
        return skillRepository.findById(skillId)
                .orElseThrow(() -> new SkillNotFoundException("Skill with id " + skillId + " not found"));
    }

    public Optional<Skill> findOptionalSkillById(String skillId) {
        return skillRepository.findById(skillId);
    }

    public List<Skill> findAllSkills() {
        return skillRepository.findAll();
    }

    public boolean exist(String id) {
        return skillRepository.existsById(id);
    }
}
