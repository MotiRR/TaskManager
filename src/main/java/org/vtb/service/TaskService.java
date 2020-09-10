package org.vtb.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vtb.entity.Task;
import org.vtb.repository.TaskRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {
    private TaskRepository taskRepository;

    @Autowired
    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task findById(long id) {
        return taskRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }
}
