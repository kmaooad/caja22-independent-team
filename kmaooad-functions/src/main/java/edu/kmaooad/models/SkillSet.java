package edu.kmaooad.models;

import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class SkillSet {

    @NotNull
    @Id
    private String skillSetID;

    //??
    private String skillSetName;

    @DBRef
    private Set<Skill> skills;

    public void addSkill(Skill input){
        if(skills == null){
            skills = new HashSet<>();
        }
        skills.add(input);
    }

    public void removeSkill(Skill input){
        if (skills != null){
            skills.remove(input);
        }
    }

}
