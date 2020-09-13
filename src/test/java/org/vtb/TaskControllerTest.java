package org.vtb;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.vtb.entity.Task;
import org.vtb.repository.TaskRepository;
import org.vtb.service.TaskService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class TaskControllerTest {

    @MockBean
    private TaskRepository taskRepository;

    @Autowired
    private TaskService taskService;

    private Task task;

    public TaskControllerTest() {
    }

    @BeforeEach
    public void init() {
        task = new Task();
        task.setId(1L);
        task.setTitle("Task");
    }

    @Test
    public void userFindById() throws Exception {
        given(this.taskRepository.findById(any()))
                .willReturn(Optional.of(task));
        Task task = taskService.findById(1L);
        assertEquals(1, task.getId());

    }

    @Test
    public void saveOrUpdate() throws Exception {
        Task task2 = new Task();
        task2.setId(1L);
        task2.setTitle("Task2");

        given(this.taskRepository.save(any()))
                .willReturn(task2);
        task = taskService.saveOrUpdate(task2);
        assertEquals("Task2",task.getTitle());
    }
}