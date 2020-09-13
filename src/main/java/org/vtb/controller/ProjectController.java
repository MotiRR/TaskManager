package org.vtb.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.vtb.entity.Project;
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

    @PutMapping(consumes = "application/json", produces = "application/json")
    public void modifyProject(@RequestBody Project project) {
        if (!service.existsById(project.getId())) {
//TODO exception class
            throw new RuntimeException(String.format("Проект с id= %d не найден", project.getId()));
        }
        service.saveOrUpdate(project);
    }
//
//    @GetMapping
//    public List<Project> getAll() {
//        return service.findAll();
//    }


    // не смотреть эти методы
    @PostMapping("/create")
    public void create(@RequestBody Project project) {
        Project project1 = project;
        System.out.println(project.getTasks());
        System.out.println(project.getUsers());
        System.out.println(project.getId());
    }

    @PutMapping("/update")
    public void update(@RequestBody Project project) {
        Project project1 = project;
        System.out.println(project.getTasks());
        System.out.println(project.getUsers());
        System.out.println(project.getId());
    }

}
