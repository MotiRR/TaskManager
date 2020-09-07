package org.vtb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vtb.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}