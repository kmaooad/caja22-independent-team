package edu.kmaooad.controller;

import edu.kmaooad.DTO.SkillDTO;
import edu.kmaooad.models.Skill;
import edu.kmaooad.models.Skill;
import edu.kmaooad.models.SkillSet;
import edu.kmaooad.models.Topic;
import edu.kmaooad.repository.*;
import edu.kmaooad.repository.SkillRepository;
import edu.kmaooad.service.SkillService;
import edu.kmaooad.service.SkillService;
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

@WebMvcTest(SkillController.class)
class SkillControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SkillService service;
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
        Skill skill1 = new Skill("1", "skill1", null);
        Skill skill2 = new Skill("2", "skill2", skill1);
        Skill skill5 = new Skill("5", "skill5", skill1);
        Skill skill6 = new Skill("6", "skill6", skill1);

        SkillDTO skillDTO5 = new SkillDTO("skilldto5", "1");
        SkillDTO skillDTO6 = new SkillDTO("skilldto6", "1");

        when(service.findAllSkills()).thenReturn(List.of(skill1,skill2));
        when(service.findOptionalSkillById("1")).thenReturn(Optional.of(skill1));
        when(service.findOptionalSkillById("2")).thenReturn(Optional.of(skill2));
        when(service.findOptionalSkillById("3")).thenReturn(Optional.empty());

        when(service.createSkill(skillDTO5)).thenReturn(skill5);
        when(service.exist("5")).thenReturn(true);

        when(service.createSkill(skillDTO6)).thenReturn(skill6);
        when(service.exist("6")).thenReturn(false);

        when(service.updateSkill("5", skillDTO5)).thenReturn(true);
        when(service.updateSkill("6", skillDTO6)).thenReturn(false);

        when(service.deleteSkill("5")).thenReturn(true);
        when(service.deleteSkill("6")).thenReturn(false);
    }

    @Test
    void getAllSkills() throws Exception {

        this.mockMvc.perform(
                        get("/skill"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(containsString(
                        "[{\"skillID\":\"1\",\"skillName\":\"skill1\",\"parentSkill\":null}," +
                                "{\"skillID\":\"2\",\"skillName\":\"skill2\",\"parentSkill\":" +
                                    "{\"skillID\":\"1\",\"skillName\":\"skill1\",\"parentSkill\":null}}]")));
    }

    @Test
    void getSkillById2_OK() throws Exception {
        this.mockMvc.perform(
                        get("/skill/2"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(containsString(
                        "{\"skillID\":\"2\",\"skillName\":\"skill2\",\"parentSkill\":" +
                                "{\"skillID\":\"1\",\"skillName\":\"skill1\",\"parentSkill\":null}}")));
    }

    @Test
    void getSkillById1_OK() throws Exception {
        this.mockMvc.perform(
                        get("/skill/1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(containsString(
                        "{\"skillID\":\"1\",\"skillName\":\"skill1\",\"parentSkill\":null}")));
    }

    @Test
    void getSkillById_BAD() throws Exception {
        this.mockMvc.perform(
                        get("/skill/3"))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(content().string(containsString("Skill with id = 3 not found")));
    }

    @Test
    void createSkill_CREATED() throws Exception {
        String content = "{" +
                "\"skillName\":\"skilldto5\"," +
                "\"parentSkillID\":\"1\"" +
                "}";
        this.mockMvc.perform(
                        post("/skill")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(content().string(containsString("New Skill created")));
    }

    @Test
    void createSkill_BAD() throws Exception {
        String content = "{" +
                "\"skillName\":\"skilldto6\"," +
                "\"parentSkillID\":\"1\"" +
                "}";
        this.mockMvc.perform(
                        post("/skill")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(content().string(containsString("New Skill not created")));
    }

    @Test
    void updateSkill_OK() throws Exception {
        String content = "{" +
                "\"skillName\":\"skilldto5\"," +
                "\"parentSkillID\":\"1\"" +
                "}";
        this.mockMvc.perform(
                        put("/skill/5")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(containsString("Skill with id = 5 updated")));
    }

    @Test
    void updateSkill_BAD() throws Exception {
        String content = "{" +
                "\"skillName\":\"skilldto6\"," +
                "\"parentSkillID\":\"1\"" +
                "}";
        this.mockMvc.perform(
                        put("/skill/6")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(content().string(containsString("Skill with id = 6 not updated")));
    }

    @Test
    void deleteSkill_OK() throws Exception {

        this.mockMvc.perform(
                        delete("/skill/5"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(containsString("Skill with id = 5 deleted")));
    }

    @Test
    void deleteSkill_BAD() throws Exception {
        this.mockMvc.perform(
                        delete("/skill/6"))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(content().string(containsString("Skill with id = 6 not deleted")));
    }
}