package org.vtb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor
@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "from_user")
    private Long fromUser;

    @Column(name = "to_user")
    private Long toUser;

    @Column(name = "task_id")
    private Long taskId;

    @Column(name = "message")
    private String message;

    @Column(name = "is_notification")
    private Boolean isNotification;

    public Notification() {}
}
