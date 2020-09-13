package org.vtb;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.vtb.entity.Task;
import org.vtb.service.TaskService;

import java.util.ArrayList;
import java.util.List;

import static org.hibernate.cfg.AvailableSettings.URL;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskControllerTest {

    @Autowired
    private static final String URl = "/api/v1/tasks";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TaskService taskService;

    private Task task;

    @BeforeEach
    public void unit() {
        task = new Task(
                1L,
                "Задание 1",
                "Выполнить тест"
        );
    }

    @Test
    public void getAllTasksTest() throws Exception {
        List<Task> tasksList = new ArrayList<>();
        tasksList.add(task);
        given(this.taskService.findAll())
                .willReturn(tasksList);
        this.mvc.perform(get(URL)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    public void getTaskByIdTest() throws Exception {
        given(this.taskService.findById(1L))
                .willReturn(task);
        this.mvc.perform(get(URL + "{id}", 1L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }
}
