package org.vtb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.vtb.entity.Task;
import org.vtb.entity.dto.TaskDto;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {

    @Query(value = "SELECT tk.id as id, tk.title as title, u.login as leaderLogin, tk.description as description, " +
            "pr.title as project, tk.priority as priority, tk.status as status, tk.deadline as deadline, au.id as user, f.id as file FROM task_manager.tasks as tk inner join task_manager.users as u on tk.leader_id = u.id inner join task_manager.projects\n" +
            "as pr on tk.project_id = pr.id left join task_manager.files as f on tk.id = f.task_id left join task_manager.users_tasks as ut \n" +
            "on ut.task_id = tk.id left join task_manager.users as au on ut.user_id = au.id  where tk.id = ?1",
            nativeQuery = true)
    List<TaskDto> findByIdDto(long id);

//    @Query("select t.id as id, t.title as title, t.leader.id as leaderId, t.leader.username as leaderUsername, " +
//            "t.description as description, t.project.id as projectId, t.project.title as projectTitle, " +
//            "t.priority as priority, t.status as status, t.deadLine as deadline from Task t where t.project.id = :id")
//    Page<Task> findAllByProjectId(Long id, Pageable pageable);
//
//    @Query("select t.id as id, t.title as title, t.leader.id as leaderId, t.leader.username as leaderUsername, " +
//            "t.description as description, t.project.id as projectId, t.project.title as projectTitle, " +
//            "t.priority as priority, t.status as status, t.deadLine as deadline from Task t")
//    Page<TaskDto> findAllTasks(PageRequest of, Specification<TaskDto> spec);
}