package org.vtb.entity.dto;

import lombok.Data;
import org.vtb.entity.Project;
import org.vtb.entity.User;
import org.vtb.entity.enums.Priority;
import org.vtb.entity.enums.Status;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
public class TaskDto {

    private Long id;

    private String title;

    private String leaderLogin;

    private String description;

    private String project;

    private Priority priority;

    private Status status;

    private LocalDate deadLine;

    private Set<User> users;

    private List<String> filesUrl;
}

