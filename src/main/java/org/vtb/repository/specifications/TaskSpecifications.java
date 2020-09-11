package org.vtb.repository.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.vtb.entity.Task;

public class TaskSpecifications {
    public static Specification<Task> projectEqual(Long projectId) {
        return (Specification<Task>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("project"), projectId);
    }
}
