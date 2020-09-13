package org.vtb;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.vtb.entity.User;
import org.vtb.service.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.hibernate.cfg.AvailableSettings.URL;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private static final String URl = "/api/v1/users";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserService userService;

    private User user;

    @BeforeEach
    public void unit() {
        user = new User(
                1L,
                "admin",
                "123",
                "admin@mail.ru"
        );
    }

    @Test
    public void getAllUsersTest() throws Exception {
        List<User> userList = new ArrayList<>();
        userList.add(user);
        given(this.userService.findAll())
                .willReturn(userList);
        this.mvc.perform(get(URL)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    public void getUserByIdTest() throws Exception {
        given(this.userService.findById(1L))
                .willReturn(user);
        this.mvc.perform(get(URL + "{id}", 1L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }
}
