package org.vtb.entity.dto;

import org.vtb.entity.enums.Priority;
import org.vtb.entity.enums.Status;

import java.time.LocalDate;
import java.util.List;

public interface TaskDto {

    Long getId();

    String getTitle();

    String getLeaderLogin();

    String getDescription();

    String getProject();

    Priority getPriority();

    Status getStatus();

    LocalDate getDeadLine();

    Long getUser();

    Long getFile();

    List<Long> getUsers();//Set<User> users;

    List<Long> getFiles(); //List<Integer> files;
}

