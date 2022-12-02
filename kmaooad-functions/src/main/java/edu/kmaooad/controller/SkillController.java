package edu.kmaooad.controller;

import edu.kmaooad.DTO.SkillDTO;
import edu.kmaooad.models.Skill;
import edu.kmaooad.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/skill")
public class SkillController {
    @Autowired
    private SkillService skillService;

    @GetMapping
    public ResponseEntity<?> getAllSkills() {
        return new ResponseEntity<>(skillService.findAllSkills(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSkillById(@PathVariable String id) {
        Optional<Skill> Skill = skillService.findOptionalSkillById(id);
        return Skill.isPresent() ?
                new ResponseEntity<>(Skill, HttpStatus.OK) :
                new ResponseEntity<>("Skill with id = " + id + " not found", HttpStatus.BAD_REQUEST);
    }

    @PostMapping()
    public ResponseEntity<?> createSkill(@RequestBody SkillDTO dto) {
        return skillService.exist(skillService.createSkill(dto).getSkillID()) ?
                new ResponseEntity<>("New Skill created", HttpStatus.CREATED) :
                new ResponseEntity<>("New Skill not created", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSkill(@PathVariable String id,
                                           @RequestBody SkillDTO dto) {
         return skillService.updateSkill(id, dto) ?
                 new ResponseEntity<>("Skill with id = " + id + " updated", HttpStatus.OK) :
                 new ResponseEntity<>("Skill with id = " + id + " not updated", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSkill(@PathVariable String id) {
        return skillService.deleteSkill(id) ?
                new ResponseEntity<>("Skill with id = " + id + " deleted", HttpStatus.OK) :
                new ResponseEntity<>("Skill with id = " + id + " not deleted", HttpStatus.BAD_REQUEST);
    }

}
