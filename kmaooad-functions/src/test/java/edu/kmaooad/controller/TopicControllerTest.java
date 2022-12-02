package edu.kmaooad.controller;

import edu.kmaooad.DTO.TopicDTO;
import edu.kmaooad.models.Topic;
import edu.kmaooad.repository.ProjectRepository;
import edu.kmaooad.repository.SkillRepository;
import edu.kmaooad.repository.SkillSetRepository;
import edu.kmaooad.repository.TopicRepository;
import edu.kmaooad.service.TopicService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TopicController.class)
class TopicControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TopicService service;
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
        Topic topic1 = new Topic("1", "Topic1", null);
        Topic topic2 = new Topic("2", "Topic2", topic1);
        Topic topic5 = new Topic("5", "Topic5", topic1);
        Topic topic6 = new Topic("6", "Topic6", topic1);

        TopicDTO topicDTO5 = new TopicDTO("Topicdto5", "1");
        TopicDTO topicDTO6 = new TopicDTO("Topicdto6", "1");

        when(service.findAllTopics()).thenReturn(List.of(topic1,topic2));
        when(service.findOptionalTopicById("1")).thenReturn(Optional.of(topic1));
        when(service.findOptionalTopicById("2")).thenReturn(Optional.of(topic2));
        when(service.findOptionalTopicById("3")).thenReturn(Optional.empty());

        when(service.createTopic(topicDTO5)).thenReturn(topic5);
        when(service.exist("5")).thenReturn(true);

        when(service.createTopic(topicDTO6)).thenReturn(topic6);
        when(service.exist("6")).thenReturn(false);

        when(service.updateTopic("5", topicDTO5)).thenReturn(true);
        when(service.updateTopic("6", topicDTO6)).thenReturn(false);

        when(service.deleteTopic("5")).thenReturn(true);
        when(service.deleteTopic("6")).thenReturn(false);
    }

    @Test
    void getAllTopics() throws Exception {

        this.mockMvc.perform(
                        get("/topic"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(containsString(
                        "[{\"topicID\":\"1\",\"topicName\":\"Topic1\",\"parentTopic\":null}," +
                                "{\"topicID\":\"2\",\"topicName\":\"Topic2\",\"parentTopic\":" +
                                "{\"topicID\":\"1\",\"topicName\":\"Topic1\",\"parentTopic\":null}}]")));
    }

    @Test
    void getTopicById2_OK() throws Exception {
        this.mockMvc.perform(
                        get("/topic/2"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(containsString(
                        "{\"topicID\":\"2\",\"topicName\":\"Topic2\",\"parentTopic\":" +
                                "{\"topicID\":\"1\",\"topicName\":\"Topic1\",\"parentTopic\":null}}")));
    }

    @Test
    void getTopicById1_OK() throws Exception {
        this.mockMvc.perform(
                        get("/topic/1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(containsString(
                        "{\"topicID\":\"1\",\"topicName\":\"Topic1\",\"parentTopic\":null}")));
    }

    @Test
    void getTopicById_BAD() throws Exception {
        this.mockMvc.perform(
                        get("/topic/3"))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(content().string(containsString("Topic with id = 3 not found")));
    }

    @Test
    void createTopic_CREATED() throws Exception {
        String content = "{" +
                "\"topicName\":\"Topicdto5\"," +
                "\"parentTopicId\":\"1\"" +
                "}";
        this.mockMvc.perform(
                        post("/topic")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(content().string(containsString("New Topic created")));
    }

    @Test
    void createTopic_BAD() throws Exception {
        String content = "{" +
                "\"topicName\":\"Topicdto6\"," +
                "\"parentTopicId\":\"1\"" +
                "}";
        this.mockMvc.perform(
                        post("/topic")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(content().string(containsString("New Topic not created")));
    }

    @Test
    void updateTopic_OK() throws Exception {
        String content = "{" +
                "\"topicName\":\"Topicdto5\"," +
                "\"parentTopicId\":\"1\"" +
                "}";
        this.mockMvc.perform(
                        put("/topic/5")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(containsString("Topic with id = 5 updated")));
    }

    @Test
    void updateTopic_BAD() throws Exception {
        String content = "{" +
                "\"topicName\":\"Topicdto6\"," +
                "\"parentTopicId\":\"1\"" +
                "}";
        this.mockMvc.perform(
                        put("/topic/6")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(content().string(containsString("Topic with id = 6 not updated")));
    }

    @Test
    void deleteTopic_OK() throws Exception {

        this.mockMvc.perform(
                        delete("/topic/5"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(containsString("Topic with id = 5 deleted")));
    }

    @Test
    void deleteTopic_BAD() throws Exception {
        this.mockMvc.perform(
                        delete("/topic/6"))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(content().string(containsString("Topic with id = 6 not deleted")));
    }
}