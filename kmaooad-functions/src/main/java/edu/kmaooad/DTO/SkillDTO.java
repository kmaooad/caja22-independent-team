package edu.kmaooad.DTO;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode

public class SkillDTO {

    private String skillId;
    private String skillName;

    private String parentSkillID;
    public SkillDTO(String skillID){
        this.skillName = skillName;
    }

}
