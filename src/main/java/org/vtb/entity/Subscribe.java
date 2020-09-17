package org.vtb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor
@Entity
@Table(name = "subscribe")
public class Subscribe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long subscriber;

    @Column(name = "task_id")
    private Long task;

    @Column(name = "is_subscribe")
    private Boolean isSubscribe;

    public Subscribe() {}
}
