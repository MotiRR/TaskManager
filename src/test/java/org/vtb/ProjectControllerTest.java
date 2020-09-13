package org.vtb;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.vtb.entity.Project;
import org.vtb.repository.ProjectRepository;
import org.vtb.service.ProjectService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class ProjectControllerTest {

    @MockBean
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectService projectService;

    private Project project;

    @BeforeEach
    public void init() {
        project = new Project();
        project.setId(1L);
        project.setTitle("Project");
    }

    @Test
    public void findById() throws Exception {
        given(this.projectRepository.findById(any()))
                .willReturn(Optional.of(project));
        Project project = projectService.findById(1L);
        assertEquals(1, project.getId());
    }

    @Test
    public void saveOrUpdate() throws Exception {
        Project project2 = new Project();
        project2.setId(1L);
        project2.setTitle("Project1");

        given(this.projectRepository.save(any()))
                .willReturn(project2);
        project = projectService.saveOrUpdate(project2);
        assertEquals("Project1", project.getTitle());
    }
}