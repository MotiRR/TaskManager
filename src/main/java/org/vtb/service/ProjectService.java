package org.vtb.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vtb.entity.Project;
import org.vtb.repository.ProjectRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ProjectService {
    private ProjectRepository repo;
    @Autowired
    public void setRepo(ProjectRepository repo) {
        this.repo = repo;
    }

    public Project findById(long id) {
        //TODO
        return repo.findById(id).orElseThrow(RuntimeException::new);
    }

    public List<Project> findAll() {
        return repo.findAll();
    }
}
