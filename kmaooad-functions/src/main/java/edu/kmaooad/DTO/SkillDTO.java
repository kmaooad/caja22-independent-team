package edu.kmaooad.DTO;

import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;


@Getter
@Setter
public class SkillDTO {


    private String skillName;


    private String parentSkillID;

    public SkillDTO(String skillID){
             this.skillName = skillName;
    }

}
