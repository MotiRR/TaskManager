package org.vtb.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vtb.entity.enums.Priority;
import org.vtb.entity.enums.Status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/storage")
public class StorageController {

    /*@GetMapping("/priority")
    public List<String> getAllPriority() {
        List<String> priorities = new ArrayList<>();
        for(Priority status : Priority.values())
            priorities.add(status.getRus());
        return priorities;
    }

    @GetMapping("/status")
    public List<String> getAllStatus() {
        List<String> statuses = new ArrayList<>();
        for(Status status : Status.values())
            statuses.add(status.getRus());
        return statuses;
    }*/

    @GetMapping("/priority")
    public List<Priority> getAllPriority() {
        return Arrays.asList(Priority.values());
    }

    @GetMapping("/status")
    public List<Status> getAllStatus() {
        return Arrays.asList(Status.values());
    }


}
