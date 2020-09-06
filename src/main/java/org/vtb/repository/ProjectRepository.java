package org.vtb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vtb.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}