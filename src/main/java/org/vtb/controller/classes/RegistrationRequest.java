package org.vtb.controller.classes;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

@Data
public class RegistrationRequest {

    @NotEmpty
    private String login;

    @NotEmpty
    private String password;

    private String lastName;

    private String name;

    private String secondName;

    private String phone;

    private String email;
}
