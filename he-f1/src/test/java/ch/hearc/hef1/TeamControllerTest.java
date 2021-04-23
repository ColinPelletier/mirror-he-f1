package ch.hearc.hef1;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import ch.hearc.hef1.controller.TeamController;
import ch.hearc.hef1.service.TeamService;

@RunWith(SpringRunner.class)
@WebMvcTest(TeamController.class)
public class TeamControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private TeamService service;

    @Test
    public void givenEmployees_whenGetEmployees_thenReturnJsonArray() throws Exception {

        // var collector = Collectors.
        // Employee alex = new Employee("alex");

        // List<Employee> allEmployees = Arrays.asList(alex);

        // given(service.getAllEmployees()).willReturn(allEmployees);

        // mvc.perform(get("/api/employees").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
        // .andExpect(jsonPath("$", hasSize(1))).andExpect(jsonPath("$[0].name",
        // is(alex.getName())));
    }
}
