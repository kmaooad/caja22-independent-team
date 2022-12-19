package edu.kmaooad.DTO;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class SkillSetDTO {
    private String skillSetId;
    private String skillSetName;
    private Set<String> skillIds;
}
