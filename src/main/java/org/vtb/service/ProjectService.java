package org.vtb.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.vtb.entity.Project;
import org.vtb.entity.Task;
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
        //TODO exception class
        return repo.findById(id).orElseThrow(RuntimeException::new);
    }

    public Page<Project> findAll(int page, int size) {
        return repo.findAll(PageRequest.of(page, size));
    }


    public Project saveOrUpdate(Project project){
        return repo.save(project);
    }

    public boolean existsById(Long id) {
        return repo.existsById(id);
    }
}
