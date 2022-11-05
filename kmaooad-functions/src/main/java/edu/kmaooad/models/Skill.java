package edu.kmaooad.models;

import com.sun.istack.internal.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Builder
@Getter
@Setter
@AllArgsConstructor
@ToString
public class Skill {
    @NotNull
    @Id
    private String skillID;
    @NotNull
    private String skillName;

    @DBRef
    private Topic parentSkill;

    Skill (String skillID, String skillName){
        this.skillID = skillID;
        this.skillName = skillName;
    }

}
