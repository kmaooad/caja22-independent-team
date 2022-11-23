package edu.kmaooad.models;

import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Project {

    @NotNull
    @Id
    private String projectID;
    @NotNull
    private String projectTitle;
    @NotNull
    private String projectDescription;
    @NotNull
    @DBRef
    private Set<Topic> topics;
    @NotNull
    @DBRef
    private Set<Skill> skills;
    @NotNull
    @DBRef
    private Set<SkillSet> skillSets;


}
