package org.vtb.repository.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.vtb.entity.Task;
import org.vtb.entity.User;

public class TaskSpecifications {
    public static Specification<Task> projectEqual(Long projectId) {
        return (Specification<Task>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("project"), projectId);
    }

    public static Specification<Task> isArchivedEqual(Boolean isArchived) {
        return (Specification<Task>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("isArchived"), isArchived);
    }

    public static Specification<Task> isVisible(Boolean isVisible) {
        return (Specification<Task>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("isVisible"), isVisible);
    }

    public static Specification<Task> userLeaderEqual(User user) {
        return (Specification<Task>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("leaderId"), user.getId());
    }

    public static Specification<Task> userExecutorEqual(User user) {
        return (Specification<Task>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("users"), user);
    }

}
