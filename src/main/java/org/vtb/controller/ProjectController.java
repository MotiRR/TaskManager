package org.vtb.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping
    public List<Project> getAll() {
        return service.findAll();
    }

}
