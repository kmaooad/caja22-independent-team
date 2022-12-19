package edu.kmaooad.controller;

import edu.kmaooad.DTO.ProjectDTO;
import edu.kmaooad.models.Project;
import edu.kmaooad.models.Skill;
import edu.kmaooad.models.SkillSet;
import edu.kmaooad.models.Topic;
import edu.kmaooad.repository.ProjectRepository;
import edu.kmaooad.repository.SkillRepository;
import edu.kmaooad.repository.SkillSetRepository;
import edu.kmaooad.repository.TopicRepository;
import edu.kmaooad.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProjectController.class)
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService service;
    @MockBean
    private ProjectRepository projectRepository;
    @MockBean
    private TopicRepository topicRepository;
    @MockBean
    private SkillRepository skillRepository;
    @MockBean
    private SkillSetRepository skillSetRepository;

    @PostConstruct
    public void loadMock() {
        Project project1 = new Project("1", "proj1", "...", Set.of(new Topic()), Set.of(new Skill()), Set.of(new SkillSet()) );
        Project project2 = new Project("2", "proj2", "...", Set.of(new Topic()), Set.of(new Skill()), Set.of(new SkillSet()) );
        Project project5 = new Project("5", "title", "...", new HashSet<>(), new HashSet<>(), new HashSet<>());
        Project project6 = new Project("6", "", "", new HashSet<>(), new HashSet<>(), new HashSet<>());

        ProjectDTO projectDTO5 = new ProjectDTO("5", "title", "...", new HashSet<>(), new HashSet<>(),new HashSet<>());
        ProjectDTO projectDTO6 = new ProjectDTO("6", "title", "...", new HashSet<>(), new HashSet<>(),new HashSet<>());

        when(service.findAll()).thenReturn(List.of(project1,project2));
        when(service.findById("1")).thenReturn(Optional.of(project1));
        when(service.findById("3")).thenReturn(Optional.empty());

        when(service.createProject(projectDTO5)).thenReturn(project5);
        when(service.exist("5")).thenReturn(true);

        when(service.createProject(projectDTO6)).thenReturn(project6);
        when(service.exist("6")).thenReturn(false);

        when(service.updateProject("5", projectDTO5)).thenReturn(true);
        when(service.updateProject("6", projectDTO6)).thenReturn(false);

        when(service.deleteProject("5")).thenReturn(true);
        when(service.deleteProject("6")).thenReturn(false);
    }

    @Test
    void getAllProjects() throws Exception {

        this.mockMvc.perform(
                get("/project"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "[{\"projectID\":\"1\",\"projectTitle\":\"proj1\",\"projectDescription\":\"...\"," +
                        "\"topics\":[{\"topicID\":null,\"topicName\":null,\"parentTopic\":null}]," +
                        "\"skills\":[{\"skillID\":null,\"skillName\":null,\"parentSkill\":null}]," +
                        "\"skillSets\":[{\"skillSetID\":null,\"skillSetName\":null,\"skills\":null}]}," +
                        "{\"projectID\":\"2\",\"projectTitle\":\"proj2\",\"projectDescription\":\"...\"," +
                        "\"topics\":[{\"topicID\":null,\"topicName\":null,\"parentTopic\":null}]," +
                        "\"skills\":[{\"skillID\":null,\"skillName\":null,\"parentSkill\":null}]," +
                        "\"skillSets\":[{\"skillSetID\":null,\"skillSetName\":null,\"skills\":null}]}]")));
    }

    @Test
    void getProjectById_OK() throws Exception {
        this.mockMvc.perform(
                get("/project/1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(containsString(
                        "{\"projectID\":\"1\",\"projectTitle\":\"proj1\",\"projectDescription\":\"...\"," +
                                "\"topics\":[{\"topicID\":null,\"topicName\":null,\"parentTopic\":null}]," +
                                "\"skills\":[{\"skillID\":null,\"skillName\":null,\"parentSkill\":null}]," +
                                "\"skillSets\":[{\"skillSetID\":null,\"skillSetName\":null,\"skills\":null}]}")));
    }

    @Test
    void getProjectById_BAD() throws Exception {
        this.mockMvc.perform(
                get("/project/3"))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(content().string(containsString("Project with id = 3 not found")));
    }

    @Test
    void createProject_CREATED() throws Exception {
        String content = "{" +
                "\"projectId\":\"5\"," +
                "\"projectTitle\":\"title\"," +
                "\"projectDescription\":\"...\"," +
                "\"topicIds\":[]," +
                "\"skillIds\": []," +
                "\"skillSetIds\": []" +
                "}";
        this.mockMvc.perform(
                post("/project")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(content().string(containsString("New Project created")));
    }

    @Test
    void createProject_BAD() throws Exception {
        String content = "{" +
                "\"projectId\":\"6\"," +
                "\"projectTitle\":\"title\"," +
                "\"projectDescription\":\"...\"," +
                "\"topicIds\":[]," +
                "\"skillIds\": []," +
                "\"skillSetIds\": []" +
                "}";
        this.mockMvc.perform(
                        post("/project")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(content().string(containsString("New Project not created")));
    }

    @Test
    void updateProject_OK() throws Exception {
        String content = "{" +
                "\"projectId\":\"5\"," +
                "\"projectTitle\":\"title\"," +
                "\"projectDescription\":\"...\"," +
                "\"topicIds\":[]," +
                "\"skillIds\": []," +
                "\"skillSetIds\": []" +
                "}";
        this.mockMvc.perform(
                        put("/project/5")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(containsString("Project with id = 5 updated")));
    }

    @Test
    void updateProject_BAD() throws Exception {
        String content = "{" +
                "\"projectId\":\"6\"," +
                "\"projectTitle\":\"title\"," +
                "\"projectDescription\":\"...\"," +
                "\"topicIds\":[]," +
                "\"skillIds\": []," +
                "\"skillSetIds\": []" +
                "}";
        this.mockMvc.perform(
                        put("/project/6")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(content().string(containsString("Project with id = 6 not updated")));
    }

    @Test
    void deleteProject_OK() throws Exception {
        this.mockMvc.perform(
                        delete("/project/5"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(containsString("Project with id = 5 deleted")));
    }

    @Test
    void deleteProject_BAD() throws Exception {
        this.mockMvc.perform(
                        delete("/project/6"))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(content().string(containsString("Project with id = 6 not deleted")));
    }
}