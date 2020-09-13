package org.vtb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "leader_id")
    private Long leaderId;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @AllArgsConstructor
    @Getter
    public enum Priority {
        PLANNING("В планах"), VERY_LOW("Очень низкий"), LOW("Низкий"),
        MEDIUM("Средний"), HIGH("Высокий"), VERY_HIGH("Очень высокий");

        private String rus;
    }

    @Column(name = "priority")
    @Enumerated(EnumType.STRING)
    private Priority priority;

    @AllArgsConstructor
    @Getter
    public enum Status {
        CREATED("Создана"), INPROGRESS("В работе"), CHECKING("Передана на проверку"),
        RETURNED("Возвращена на доработку"), COMPLETED("Завершена"), CANCELED("Отменена");

        private String rus;
    }

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "deadline")
    private LocalDate deadLine;

    @ManyToMany
    @JoinTable(name = "users_tasks",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users;

    @OneToMany(mappedBy = "task")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JsonIgnore
    private List<Comment> comments;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}