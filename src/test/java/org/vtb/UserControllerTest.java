package org.vtb;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.vtb.entity.User;
import org.vtb.repository.UserRepository;
import org.vtb.service.UserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private User user;

    @BeforeEach
    public void init() {
        user = new User();
        user.setId(1L);
        user.setName("admin");
        user.setLogin("adminLogin");
    }

    @Test
    public void findById() throws Exception {
        given(this.userRepository.findById(any()))
                .willReturn(Optional.of(user));
        User user = userService.findById(1L);
        assertEquals(1, user.getId());

    }

    @Test
    public void findByLogin() throws Exception {
        given(this.userRepository.findByLogin(any()))
                .willReturn(user);
        User user = userService.findByLogin("adminLogin");
        assertEquals("adminLogin", user.getLogin());
    }

    @Test
    public void saveOrUpdate() throws Exception {
        User user2 = new User();
        user2.setId(2L);
        user2.setName("User2");

        given(this.userRepository.save(any()))
                .willReturn(user2);
        user = userService.saveOrUpdate(user2);
        assertEquals("User2", user.getName());
    }
}