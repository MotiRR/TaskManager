package org.vtb.utills;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.MultiValueMap;
import org.vtb.entity.Task;
import org.vtb.entity.User;
import org.vtb.repository.UserRepository;
import org.vtb.repository.specifications.TaskSpecifications;

@Getter
public class TaskFilter {

    private UserRepository userRepository;

    private Specification<Task> spec;
    private String filterParams;

    //Можно добавить циклы внутри каждого, что бы выбирать таски несскольких проектов
    public TaskFilter(MultiValueMap<String, String> params) {
        spec = Specification.where(null);
        if (params.containsKey("is_archived")) {
            spec = spec.and(TaskSpecifications.isArchivedEqual(Boolean.parseBoolean(params.getFirst("is_archived"))));
            System.out.println(params.getFirst("is_archived"));
        }
        if (params.containsKey("is_visible")) {
            User user = userRepository.findByLogin(params.getFirst("login"));
            spec = spec.and(TaskSpecifications.isVisible(Boolean.parseBoolean(params.getFirst("is_visible"))))
                    .and(TaskSpecifications.userLeaderEqual(user).or(TaskSpecifications.userExecutorEqual(user)));
            System.out.println(params.getFirst("is_visible"));
        }
        if (params.containsKey("project")) {
            spec = spec.and(TaskSpecifications.projectEqual(Long.parseLong(params.getFirst("project"))));
        }
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
