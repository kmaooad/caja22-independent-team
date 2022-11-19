package edu.kmaooad.models;

import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Builder
@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Skill {
    @NotNull
    @Id
    private String skillID;
    @NotNull
    private String skillName;

    @DBRef
    private Skill parentSkill;

    public Skill(String skillID, String skillName){
        this.skillID = skillID;
        this.skillName = skillName;
    }
    public Skill(){

    }


}
