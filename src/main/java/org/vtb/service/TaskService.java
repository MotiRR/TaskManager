package org.vtb.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.vtb.entity.Task;
import org.vtb.entity.dto.TaskDto;
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

    public List<TaskDto> findByIdDto(long id) {
//        List<TaskDto> taskDtos = taskRepository.findByIdDto(id);
//        if(taskDtos.isEmpty()) return null;
        return taskRepository.findByIdDto(id);
    }

    public Page<Task> findAllTasksByProject(Specification<Task> spec, int page, int size) {
        return taskRepository.findAll(spec, PageRequest.of(page, size));
    }



    public Task saveOrUpdate(Task task) {
        return taskRepository.save(task);
    }

    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return taskRepository.existsById(id);
    }

}
