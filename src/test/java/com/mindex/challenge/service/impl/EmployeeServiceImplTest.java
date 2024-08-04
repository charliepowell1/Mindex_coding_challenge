package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportStructure;
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
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeServiceImplTest {

    private String employeeUrl;
    private String employeeIdUrl;
    private String reportIdUrl;

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
        reportIdUrl = "http://localhost:" + port + "/reportStructure/{id}";
    }

    @Test
    public void testCreateReadUpdateEmployee() {
        Employee testEmployee = new Employee();
        Employee testEmployee2 = new Employee();
        Employee testEmployee3 = new Employee();
        
        Compensation comp = new Compensation();
        comp.setEffectiveDate("01/01/2021");
        comp.setSalary("$100/yr");
        comp.setEmployeeId(testEmployee.getEmployeeId());
        
        Compensation comp2 = new Compensation();
        comp.setEffectiveDate("02/02/2022");
        comp.setSalary("$200/yr");
        comp.setEmployeeId(testEmployee2.getEmployeeId());
        
        Compensation comp3 = new Compensation();
        comp.setEffectiveDate("03/03/2023");
        comp.setSalary("$300/yr");
        comp.setEmployeeId(testEmployee3.getEmployeeId());
        
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");
        testEmployee.setDepartment("Engineering");
        testEmployee.setPosition("Developer");
        testEmployee.setCompensation(comp);
        
        testEmployee2.setFirstName("James");
        testEmployee2.setLastName("Bond");
        testEmployee2.setDepartment("MI6");
        testEmployee2.setPosition("Spy");
        testEmployee2.setCompensation(comp2);
        
        testEmployee3.setFirstName("Shrek");
        testEmployee3.setLastName("Shrekerson");
        testEmployee3.setDepartment("Swamp");
        testEmployee3.setPosition("Ogre");
        testEmployee3.setCompensation(comp3);
        
        List<Employee> directReports = new ArrayList<Employee>();
        directReports.add(testEmployee2);
        directReports.add(testEmployee3);
        testEmployee.setDirectReports(directReports);

        // Create checks
        Employee createdEmployee = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class).getBody();
        Employee createdEmployee2 = restTemplate.postForEntity(employeeUrl, testEmployee2, Employee.class).getBody();
        Employee createdEmployee3 = restTemplate.postForEntity(employeeUrl, testEmployee3, Employee.class).getBody();

        assertNotNull(createdEmployee.getEmployeeId());
        assertEmployeeEquivalence(testEmployee, createdEmployee);
        assertEmployeeEquivalence(testEmployee2, createdEmployee2);
        assertEmployeeEquivalence(testEmployee3, createdEmployee3);
        
        // Read checks
        Employee readEmployee = restTemplate.getForEntity(employeeIdUrl, Employee.class, createdEmployee.getEmployeeId()).getBody();
        assertEquals(createdEmployee.getEmployeeId(), readEmployee.getEmployeeId());
        assertEmployeeEquivalence(createdEmployee, readEmployee);

        //Read Report Structure Checks
        ReportStructure createdReportStructure = new ReportStructure(createdEmployee.getDirectReports(), createdEmployee.getDirectReportsSize());
        ReportStructure readReportStructure = restTemplate.getForEntity(reportIdUrl, ReportStructure.class, createdEmployee.getEmployeeId()).getBody();
        
        ReportStructure createdReportStructure2 = new ReportStructure(createdEmployee2.getDirectReports(), createdEmployee2.getDirectReportsSize());
        ReportStructure readReportStructure2 = restTemplate.getForEntity(reportIdUrl, ReportStructure.class, createdEmployee2.getEmployeeId()).getBody();
        
        ReportStructure createdReportStructure3 = new ReportStructure(createdEmployee3.getDirectReports(), createdEmployee3.getDirectReportsSize());
        ReportStructure readReportStructure3  = restTemplate.getForEntity(reportIdUrl, ReportStructure.class, createdEmployee3.getEmployeeId()).getBody();
        
        assertReportStructureEquivalence(readReportStructure, createdReportStructure);
        assertReportStructureEquivalence(readReportStructure2, createdReportStructure2);
        assertReportStructureEquivalence(readReportStructure3, createdReportStructure3);

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
        assertEquals(expected.getCompensation().getSalary(), actual.getCompensation().getSalary());
    }
    
    private static void assertReportStructureEquivalence(ReportStructure expected, ReportStructure actual) {
    	int index = 0;
    	
    	for(Employee empExpected : expected.getEmployee()) {
    		//Employee empActual = actual.getEmployee().indexOf(index);
    		assertEmployeeEquivalence(empExpected, actual.getEmployee().get(index));
    		index++;
    	}
    	//assertIterableEquals(expected.getEmployee(), actual.getEmployee());
    	assertEquals(expected.getNumberOfReports(), actual.getNumberOfReports());
    	
    }
    
}
