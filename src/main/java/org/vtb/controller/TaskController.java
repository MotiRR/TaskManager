package org.vtb.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.vtb.entity.Task;
import org.vtb.service.TaskService;
import org.vtb.utills.TaskFilter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/tasks")
public class TaskController {
    private TaskService taskService;

    @Autowired
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return taskService.findById(id);
    }

    @GetMapping
    public List<Task> getAllTasksByProjectId(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                             @RequestParam(required = false) MultiValueMap<String, String> params) {
        TaskFilter taskFilter = new TaskFilter(params);
        return taskService.findAllTasksByProject(taskFilter.getSpec(), page - 1, 5).getContent();
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Task createNewTask(@RequestBody Task task) {
        if (task.getId() != null) {
            task.setId(null);
        }
        return taskService.saveOrUpdate(task);
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
