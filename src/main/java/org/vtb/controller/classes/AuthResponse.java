package org.vtb.controller.classes;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AuthResponse {

    private int status;
    private String token;
    private List<String> messages;
}
