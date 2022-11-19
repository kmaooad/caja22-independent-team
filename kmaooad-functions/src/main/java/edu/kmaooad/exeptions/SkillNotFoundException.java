package edu.kmaooad.exeptions;

public class SkillNotFoundException extends RuntimeException{
    public SkillNotFoundException(String message) {
        super(message);
    }
}