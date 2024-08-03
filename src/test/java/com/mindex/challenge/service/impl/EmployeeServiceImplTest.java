package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeServiceImplTest {

    private String employeeUrl;
    private String employeeIdUrl;

    @Autowired
    private EmployeeService employeeService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        employeeUrl = "http://localhost:" + port + "/employee";
        employeeIdUrl = "http://localhost:" + port + "/employee/{id}";
    }

    @Test
    public void testCreateReadUpdateEmployee() {
        Employee testEmployee = new Employee();
        Employee testEmployee2 = new Employee();
        Employee testEmployee3 = new Employee();
        
        Compensation comp = new Compensation();
        comp.setEffectiveDate("02/02/2020");
        comp.setSalary("$100/yr");
        comp.setEmployeeId(testEmployee.getEmployeeId());
        
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");
        testEmployee.setDepartment("Engineering");
        testEmployee.setPosition("Developer");
        testEmployee.setCompensation(comp);
        
        testEmployee2.setFirstName("James");
        testEmployee2.setLastName("Bond");
        testEmployee2.setDepartment("MI6");
        testEmployee2.setPosition("Spy");
        
        testEmployee3.setFirstName("Shrek");
        testEmployee3.setLastName("Shrekerson");
        testEmployee3.setDepartment("Swamp");
        testEmployee3.setPosition("Ogre");
        
        List<Employee> directReports = new ArrayList<Employee>();
        directReports.add(testEmployee2);
        directReports.add(testEmployee3);
        testEmployee.setDirectReports(directReports);

        // Create checks
        Employee createdEmployee = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class).getBody();

        assertNotNull(createdEmployee.getEmployeeId());
        assertEmployeeEquivalence(testEmployee, createdEmployee);
        


        // Read checks
        Employee readEmployee = restTemplate.getForEntity(employeeIdUrl, Employee.class, createdEmployee.getEmployeeId()).getBody();
        assertEquals(createdEmployee.getEmployeeId(), readEmployee.getEmployeeId());
        assertEmployeeEquivalence(createdEmployee, readEmployee);


        // Update checks
        readEmployee.setPosition("Development Manager");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Employee updatedEmployee =
                restTemplate.exchange(employeeIdUrl,
                        HttpMethod.PUT,
                        new HttpEntity<Employee>(readEmployee, headers),
                        Employee.class,
                        readEmployee.getEmployeeId()).getBody();

        assertEmployeeEquivalence(readEmployee, updatedEmployee);
    }
    

    private static void assertEmployeeEquivalence(Employee expected, Employee actual) {
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getDepartment(), actual.getDepartment());
        assertEquals(expected.getPosition(), actual.getPosition());
        assertEquals(expected.getCompensation().getEffectiveDate(), actual.getCompensation().getEffectiveDate());
        //Assert.assertEquals(expected, actual);
        assertEquals(expected.getDirectReports().size(), actual.getDirectReports().size());
        for(int i = 0; i < expected.getDirectReports().size(); i++)
        {
        	assertEquals(expected.getDirectReports().get(i).getFirstName(), actual.getDirectReports().get(i).getFirstName());
        }
        //Assert.assertEquals(expected.getDirectReports(), actual.getDirectReports());
    }
    
}
