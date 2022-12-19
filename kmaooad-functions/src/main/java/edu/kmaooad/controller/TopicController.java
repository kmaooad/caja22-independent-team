package edu.kmaooad.controller;

import edu.kmaooad.DTO.TopicDTO;
import edu.kmaooad.models.Skill;
import edu.kmaooad.models.Topic;
import edu.kmaooad.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/topic")
public class TopicController {
    @Autowired
    private TopicService topicService;

    @GetMapping
    public ResponseEntity<?> getAllTopics() {
        return new ResponseEntity<>(topicService.findAllTopics(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTopicById(@PathVariable String id) {
        Optional<Topic> Topic = topicService.findOptionalTopicById(id);
        return Topic.isPresent() ?
                new ResponseEntity<>(Topic, HttpStatus.OK) :
                new ResponseEntity<>("Topic with id = " + id + " not found", HttpStatus.BAD_REQUEST);
    }

    @PostMapping()
    public ResponseEntity<?> createTopic(@RequestBody TopicDTO dto) {
        Optional<Topic> newTopic = topicService.createTopic(dto);
        return newTopic.isPresent() && topicService.exist(newTopic.get().getTopicID()) ?
                new ResponseEntity<>("New Topic created", HttpStatus.CREATED) :
                new ResponseEntity<>("New Topic not created", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTopic(@PathVariable String id,
                                           @RequestBody TopicDTO dto) {
         return topicService.updateTopic(id, dto) ?
                 new ResponseEntity<>("Topic with id = " + id + " updated", HttpStatus.OK) :
                 new ResponseEntity<>("Topic with id = " + id + " not updated", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTopic(@PathVariable String id) {
        return topicService.deleteTopic(id) ?
                new ResponseEntity<>("Topic with id = " + id + " deleted", HttpStatus.OK) :
                new ResponseEntity<>("Topic with id = " + id + " not deleted", HttpStatus.BAD_REQUEST);
    }

}
