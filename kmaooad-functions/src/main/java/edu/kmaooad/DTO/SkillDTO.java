package edu.kmaooad.DTO;

import lombok.*;


@Getter
@Setter
public class SkillDTO {


    private String skillName;


    private String parentSkillID;

    public SkillDTO(String skillID){
        this.skillName = skillName;
    }

}
