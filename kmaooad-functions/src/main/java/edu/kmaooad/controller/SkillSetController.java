package edu.kmaooad.controller;

import edu.kmaooad.DTO.SkillSetDTO;
import edu.kmaooad.models.SkillSet;
import edu.kmaooad.service.SkillSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/skillSet")
public class SkillSetController {
    @Autowired
    private SkillSetService skillSetService;

    @GetMapping
    public ResponseEntity<?> getAllSkillSets() {
        return new ResponseEntity<>(skillSetService.findAllSkillSets(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSkillSetById(@PathVariable String id) {
        Optional<SkillSet> SkillSet = skillSetService.findOptionalSkillSetById(id);
        return SkillSet.isPresent() ?
                new ResponseEntity<>(SkillSet, HttpStatus.OK) :
                new ResponseEntity<>("SkillSet with id = " + id + " not found", HttpStatus.BAD_REQUEST);
    }

    @PostMapping()
    public ResponseEntity<?> createSkillSet(@RequestBody SkillSetDTO dto) {
        return skillSetService.exist(skillSetService.createSkillSet(dto).getSkillSetID()) ?
                new ResponseEntity<>("New SkillSet created", HttpStatus.CREATED) :
                new ResponseEntity<>("New SkillSet not created", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSkillSet(@PathVariable String id,
                                           @RequestBody SkillSetDTO dto) {
         return skillSetService.updateSkillSet(id, dto) ?
                 new ResponseEntity<>("SkillSet with id = " + id + " updated", HttpStatus.OK) :
                 new ResponseEntity<>("SkillSet with id = " + id + " not updated", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSkillSet(@PathVariable String id) {
        return skillSetService.deleteSkillSet(id) ?
                new ResponseEntity<>("SkillSet with id = " + id + " deleted", HttpStatus.OK) :
                new ResponseEntity<>("SkillSet with id = " + id + " not deleted", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{skillSetId}/{skillId}")
    public ResponseEntity<?> addSkillToSkillSet(@PathVariable String skillSetId, @PathVariable String skillId) {
        return skillSetService.addSkillToSkillSet(skillSetId, skillId) ?
                new ResponseEntity<>("Skill with id = " + skillId + " added to skillset with id = " + skillSetId, HttpStatus.OK) :
                new ResponseEntity<>("Skill with id = " + skillId + " not added to skillset with id = " + skillSetId, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{skillSetId}/{skillId}")
    public ResponseEntity<?> removeSkillFromSkillSet(@PathVariable String skillSetId, @PathVariable String skillId) {
        return skillSetService.removeSkillFromSkillSet(skillSetId, skillId) ?
                new ResponseEntity<>("Skill with id = " + skillId + " removed from skillset with id = " + skillSetId, HttpStatus.OK) :
                new ResponseEntity<>("Skill with id = " + skillId + " not removed from to skillset with id = " + skillSetId, HttpStatus.BAD_REQUEST);
    }

}
