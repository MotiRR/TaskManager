package org.vtb.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vtb.controller.classes.ResponseMessage;
import org.vtb.entity.Project;
import org.vtb.entity.Task;
import org.vtb.service.ProjectService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/projects")
public class ProjectController {
    private ProjectService service;

    @Autowired
    public void setService(ProjectService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public Project getProjectById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping
    public List<Project> getAll(@RequestParam(value = "page", defaultValue = "1") Integer page) {
        return service.findAll(page - 1, 5).getContent();
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseMessage> createNewTask(@RequestBody Project project) {
        String message = "";
        if (project.getId() != null) {
            project.setId(null);
        }
        try {
            Project taskSave = service.saveOrUpdate(project);
            message = "Проект создан с id = " + taskSave.getId();
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Проект не был создана";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public void modifyProject(@RequestBody Project project) {
        if (!service.existsById(project.getId())) {
            //TODO exception class
            throw new RuntimeException(String.format("Проект с id= %d не найден", project.getId()));
        }
        service.saveOrUpdate(project);
    }


}
