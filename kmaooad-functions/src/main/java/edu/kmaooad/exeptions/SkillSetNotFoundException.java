package edu.kmaooad.exeptions;

public class SkillSetNotFoundException extends RuntimeException {
    public SkillSetNotFoundException(String skillSetId) {
        super(String.format("Skill set with id %s not found", skillSetId));
    }
}
