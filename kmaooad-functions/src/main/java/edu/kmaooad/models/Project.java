package edu.kmaooad.models;

import com.sun.istack.internal.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
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
    private Topic topic;
    @NotNull
    private Skill skill;
    @NotNull
    private SkillSet skillSet;


}
