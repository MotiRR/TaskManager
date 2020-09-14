package org.vtb.repository.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.vtb.entity.Task;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class TaskSpecifications {
    public static Specification<Task> projectEqual(Long projectId) {
        return (Specification<Task>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("project"), projectId);
    }

    public static Specification<Task> isArchivedEqual(Boolean isArchived) {
        System.out.println("\n\n\n " + isArchived);
//        String s = String.valueOf(isArchived);
//       Specification<Task> s = new Specification<Task>() {
//           @Override
//           public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
//               System.out.println("\n\n\n " + root.get("isArchived"));
//               return criteriaBuilder.isTrue(root.get("isArchived"));
//           }
//       };
//       return null;
        return (Specification<Task>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("isArchived"), isArchived);
    }

    public static Specification<Task> isArchived1(Boolean isArchived) {
        System.out.println("\n\n\n " + isArchived);
//        (Specification<Task>) (root, criteriaQuery, criteriaBuilder) -> {
//            System.out.println(root.get("isArchived"));
//            return criteriaBuilder.isFalse(root.get("isArchived"));
//        };

        return (Specification<Task>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.isFalse(root.get("isArchived"));
    }

//    public static Specification<Task> isArchived2(Integer isArchived) {
//        System.out.println(isArchived+"\n\nn\n\n");
//        return (Specification<Task>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("isArchived"), String.format("%%%s%%", isArchived));
//    }
}
