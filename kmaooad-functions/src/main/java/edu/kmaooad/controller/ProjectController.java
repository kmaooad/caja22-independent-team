package edu.kmaooad.controller;

import edu.kmaooad.DTO.ProjectDTO;
import edu.kmaooad.models.Project;
import edu.kmaooad.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @GetMapping
    public ResponseEntity<?> getAllProjects() {
        return new ResponseEntity<>(projectService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProjectById(@PathVariable String id) {
        Optional<Project> project = projectService.findById(id);
        return project.isPresent() ?
                new ResponseEntity<>(project, HttpStatus.OK) :
                new ResponseEntity<>("Project with id = " + id + " not found", HttpStatus.BAD_REQUEST);
    }

    @PostMapping()
    public ResponseEntity<?> createProject(@RequestBody ProjectDTO dto) {
        return projectService.exist(projectService.createProject(dto).getProjectID()) ?
                new ResponseEntity<>("New Project created", HttpStatus.CREATED) :
                new ResponseEntity<>("New Project not created", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProject(@PathVariable String id,
                                           @RequestBody ProjectDTO dto) {
         return projectService.updateProject(id, dto) ?
                 new ResponseEntity<>("Project with id = " + id + " updated", HttpStatus.OK) :
                 new ResponseEntity<>("Project with id = " + id + " not updated", HttpStatus.BAD_REQUEST);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable String id) {
        return projectService.deleteProject(id) ?
                new ResponseEntity<>("Project with id = " + id + " deleted", HttpStatus.OK) :
                new ResponseEntity<>("Project with id = " + id + " not deleted", HttpStatus.BAD_REQUEST);
    }


}
