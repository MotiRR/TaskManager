package org.vtb.controller.classes;

import lombok.Data;

@Data
public class AuthRequest {
    private String login;
    private String password;
}
