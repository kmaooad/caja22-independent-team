package edu.kmaooad.service;

import edu.kmaooad.exeptions.SkillNotFoundException;
import edu.kmaooad.exeptions.TopicNotFoundException;
import edu.kmaooad.models.Skill;
import edu.kmaooad.models.Topic;
import edu.kmaooad.repository.SkillRepository;
import edu.kmaooad.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class SkillService {
    @Autowired
    SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public Skill createSkill(String skillName, String parentId) {
        Skill skill = new Skill();
        skill.setSkillName(skillName);
        Optional<Skill> parentTopic = skillRepository.findSkillBySkillID(parentId);
        if (parentTopic.isPresent()) {
            skill.setParentSkill(parentTopic.get());
        }
        skillRepository.save(skill);
        return skill;
    }

    public void deleteSkill(String skillId) {
        Optional<Skill> skill = skillRepository.findSkillBySkillID(skillId);
        if (skill.isPresent()) {
            skillRepository.delete(skill.get());
        } else {
            throw new SkillNotFoundException("Skill not found");
        }
    }
    public Skill updateSkill(String id, String skillName, String parentId) {
        Optional<Skill> skill = skillRepository.findSkillBySkillID(id);
        if (skill.isPresent()) {
            skill.get().setSkillName(skillName);
            Optional<Skill> parentSkill = skillRepository.findSkillBySkillID(parentId);

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



}
