package org.vtb;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.vtb.entity.Project;
import org.vtb.service.ProjectService;

import java.util.ArrayList;
import java.util.List;

import static org.hibernate.cfg.AvailableSettings.URL;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectControllerTest {

    @Autowired
    private static final String URl = "/api/v1/projects";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ProjectService projectService;

    private Project project;

    @BeforeEach
    public void unit() {
        project = new Project(
                1L,
                "Project 1"
        );
    }

    @Test
    public void getAllProjectsTest() throws Exception {
        List<Project> projectList = new ArrayList<>();
        projectList.add(project);
        given(this.projectService.findAll())
                .willReturn(projectList);
        this.mvc.perform(get(URL)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    public void getProjectByIdTest() throws Exception {
        given(this.projectService.findById(1L))
                .willReturn(project);
        this.mvc.perform(get(URL + "{id}", 1L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }
}
