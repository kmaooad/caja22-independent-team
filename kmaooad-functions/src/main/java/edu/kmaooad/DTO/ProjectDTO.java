package edu.kmaooad.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ProjectDTO {
    private String projectId;
    private String projectTitle;
    private String projectDescription;
    private Set<String> topicIds;
    private Set<String> skillIds;
    private Set<String> skillSetIds;
}
