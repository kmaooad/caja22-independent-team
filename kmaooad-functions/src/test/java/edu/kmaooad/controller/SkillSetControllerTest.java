package edu.kmaooad.controller;

import edu.kmaooad.DTO.SkillSetDTO;
import edu.kmaooad.models.Skill;
import edu.kmaooad.models.SkillSet;
import edu.kmaooad.repository.ProjectRepository;
import edu.kmaooad.repository.SkillRepository;
import edu.kmaooad.repository.SkillSetRepository;
import edu.kmaooad.repository.TopicRepository;
import edu.kmaooad.service.SkillSetService;
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

@WebMvcTest(SkillSetController.class)
class SkillSetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SkillSetService service;
    @MockBean
    private ProjectRepository projectRepository;
    @MockBean
    private TopicRepository topicRepository;
    @MockBean
    private SkillSetRepository skillSetRepository;
    @MockBean
    private SkillRepository skillRepository;

    @PostConstruct
    public void loadMock() {
        Skill skill1 = new Skill("1", "skill1", null);
        Skill skill2 = new Skill("2", "skill2", skill1);

        SkillSet skillSet1 = new SkillSet("1", "SkillSet1", new HashSet<>());
        SkillSet skillSet2 = new SkillSet("2", "SkillSet2", Set.of(skill2));
        SkillSet skillSet5 = new SkillSet("5", "SkillSet5", new HashSet<>());
        SkillSet skillSet6 = new SkillSet("6", "SkillSet6", Set.of(skill1));

        SkillSetDTO skillSetDTO5 = new SkillSetDTO("5", "SkillSetDTO5", new HashSet<>());
        SkillSetDTO skillSetDTO6 = new SkillSetDTO("6", "SkillSetDTO6", new HashSet<>());

        when(service.findAllSkillSets()).thenReturn(List.of(skillSet1,skillSet2));
        when(service.findOptionalSkillSetById("1")).thenReturn(Optional.of(skillSet1));
        when(service.findOptionalSkillSetById("2")).thenReturn(Optional.of(skillSet2));
        when(service.findOptionalSkillSetById("3")).thenReturn(Optional.empty());

        when(service.createSkillSet(skillSetDTO5)).thenReturn(skillSet5);
        when(service.exist("5")).thenReturn(true);

        when(service.createSkillSet(skillSetDTO6)).thenReturn(skillSet6);
        when(service.exist("6")).thenReturn(false);

        when(service.updateSkillSet("5", skillSetDTO5)).thenReturn(true);
        when(service.updateSkillSet("6", skillSetDTO6)).thenReturn(false);

        when(service.deleteSkillSet("5")).thenReturn(true);
        when(service.deleteSkillSet("6")).thenReturn(false);
    }

    @Test
    void getAllSkillSets() throws Exception {

        this.mockMvc.perform(
                        get("/skillSet"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(containsString(
                        "[{\"skillSetID\":\"1\",\"skillSetName\":\"SkillSet1\",\"skills\":[]}," +
                                "{\"skillSetID\":\"2\",\"skillSetName\":\"SkillSet2\",\"skills\":" +
                                "[{\"skillID\":\"2\",\"skillName\":\"skill2\",\"parentSkill\":" +
                                "{\"skillID\":\"1\",\"skillName\":\"skill1\",\"parentSkill\":null}}]}]")));
    }

    @Test
    void getSkillSetById2_OK() throws Exception {
        this.mockMvc.perform(
                        get("/skillSet/2"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(containsString(
                        "{\"skillSetID\":\"2\",\"skillSetName\":\"SkillSet2\",\"skills\":" +
                                "[{\"skillID\":\"2\",\"skillName\":\"skill2\",\"parentSkill\":" +
                                "{\"skillID\":\"1\",\"skillName\":\"skill1\",\"parentSkill\":null}}]}")));
    }

    @Test
    void getSkillSetById1_OK() throws Exception {
        this.mockMvc.perform(
                        get("/skillSet/1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(containsString(
                        "{\"skillSetID\":\"1\",\"skillSetName\":\"SkillSet1\",\"skills\":[]}")));
    }

    @Test
    void getSkillSetById_BAD() throws Exception {
        this.mockMvc.perform(
                        get("/skillSet/3"))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(content().string(containsString("SkillSet with id = 3 not found")));
    }

    @Test
    void createSkillSet_CREATED() throws Exception {
        String content = "{" +
                "\"skillSetId\":\"5\"," +
                "\"skillSetName\":\"SkillSetDTO5\"," +
                "\"skillIds\":[]" +
                "}";
        this.mockMvc.perform(
                        post("/skillSet")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(content().string(containsString("New SkillSet created")));
    }

    @Test
    void createSkillSet_BAD() throws Exception {
        String content = "{" +
                "\"skillSetId\":\"6\"," +
                "\"skillSetName\":\"SkillSetDTO6\"," +
                "\"skillIds\":[]" +
                "}";
        this.mockMvc.perform(
                        post("/skillSet")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(content().string(containsString("New SkillSet not created")));
    }

    @Test
    void updateSkillSet_OK() throws Exception {
        String content = "{" +
                "\"skillSetId\":\"5\"," +
                "\"skillSetName\":\"SkillSetDTO5\"," +
                "\"skillIds\":[]" +
                "}";
        this.mockMvc.perform(
                        put("/skillSet/5")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(containsString("SkillSet with id = 5 updated")));
    }

    @Test
    void updateSkillSet_BAD() throws Exception {
        String content = "{" +
                "\"skillSetId\":\"6\"," +
                "\"skillSetName\":\"SkillSetDTO5\"," +
                "\"skillIds\":[]" +
                "}";
        this.mockMvc.perform(
                        put("/skillSet/6")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(content().string(containsString("SkillSet with id = 6 not updated")));
    }

    @Test
    void deleteSkillSet_OK() throws Exception {
        this.mockMvc.perform(
                        delete("/skillSet/5"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(containsString("SkillSet with id = 5 deleted")));
    }

    @Test
    void deleteSkillSet_BAD() throws Exception {
        this.mockMvc.perform(
                        delete("/skillSet/6"))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(content().string(containsString("SkillSet with id = 6 not deleted")));
    }

    @Test
    void addSkillToSkillSet_OK() throws Exception {
        this.mockMvc.perform(
                put("/skillSet/5/skill/1")));
    }

    @Test
    void removeSkillFromSkillSet_OK() throws Exception {
        this.mockMvc.perform(
                put("/skillSet/5/skill/1")));
    }
}