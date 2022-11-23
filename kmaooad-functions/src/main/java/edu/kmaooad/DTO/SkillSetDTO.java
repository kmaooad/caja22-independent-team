package edu.kmaooad.DTO;

import edu.kmaooad.models.SkillSet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class SkillSetDTO {
    private String skillSetId;
    private String skillSetName;
    private Set<String> skillIds;
}
