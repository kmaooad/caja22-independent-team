package edu.kmaooad.DTO;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class ProjectDTO {
    private String projectId;
    private String projectTitle;
    private String projectDescription;
    private Set<String> topicIds;
    private Set<String> skillIds;
    private Set<String> skillSetIds;
}
