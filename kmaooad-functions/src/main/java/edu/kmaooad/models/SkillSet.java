package edu.kmaooad.models;

import com.sun.istack.internal.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class SkillSet {

    @NotNull
    @Id
    private String skillSetID;

    //??
    private String skillSetName;

    private Set<Skill> skillSet;

    public void addSkill(Skill input){

        if(skillSet==null){
            skillSet = new HashSet<Skill>();
        }

        skillSet.add(input);

    }

    public void removeSkill(Skill input){
        if (skillSet==null){
            return;
        }

        skillSet.remove(input);
    }


}
