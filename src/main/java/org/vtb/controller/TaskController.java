package org.vtb.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.vtb.config.jwt.JwtProvider;
import org.vtb.controller.classes.ResponseMessage;
import org.vtb.entity.Task;
import org.vtb.service.TaskService;
import org.vtb.utills.TaskFilter;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private JwtProvider jwtProvider;

    private TaskService taskService;

    @Autowired
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return taskService.findById(id);
    }

    /*
    @GetMapping("dto/{id}")
    public List<TaskDto> getTaskByIdDto(@PathVariable Long id) {
        return taskService.findByIdDto(id);
    }*/

    @GetMapping
    public List<Task> getAllTasksByProjectId(@RequestHeader Map<String, String> headers,
                                             @RequestParam(value = "page", defaultValue = "1") Integer page,
                                             @RequestParam(required = false) MultiValueMap<String, String> params) {
        String token = headers.get("authorization").replaceFirst("Bearer ", "");
        String login = jwtProvider.getLoginFromToken(token);
        params.set("login", login);
        TaskFilter taskFilter = new TaskFilter(params);
        return taskService.findAllTasksByProject(taskFilter.getSpec(), page - 1, 5).getContent();
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseMessage> createNewTask(@RequestBody Task task) {
        String message = "";
        if (task.getId() != null) {
            task.setId(null);
        }
        try {
            Task taskSave = taskService.saveOrUpdate(task);
            message = "Задача создана с id = " + taskSave.getId();
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Задача не была создана";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public void modifyTask(@RequestBody Task task) {
        if (!taskService.existsById(task.getId())) {
            //TODO create exception class
            throw new RuntimeException(String.format("Задача с id= %d не найдена", task.getId()));
        }
        taskService.saveOrUpdate(task);
    }

    @DeleteMapping("/{id}")
    public void deleteTaskById(@RequestParam Long id) {
        if (!taskService.existsById(id)) {
            throw new RuntimeException(String.format("Задача с id= %d не найдена", id));
        }
        taskService.deleteById(id);
    }
}
