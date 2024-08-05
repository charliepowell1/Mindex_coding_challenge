package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {

    private String compensationIdUrl;

    @Autowired
    private CompensationService compensationService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        compensationIdUrl = "http://localhost:" + port + "/compensation{id}";
    }

    
    @Test
    public void testCreateReadCompensation() {
    	Compensation testComp = new Compensation();
    	testComp.setEffectiveDate("01/01/2024");
    	testComp.setSalary("$1");
    	testComp.setEmployeeId("16a596ae-edd3-4847-99fe-c4518e82c86f");
    	
    	//service creation test
    	Compensation serviceComp = compensationService.createComp(testComp, compensationIdUrl);
    	
    	assertNotNull(serviceComp.getEmployeeId());
        assertCompensationEquivalence(testComp, serviceComp);
        
        //service read test
        Compensation readServiceComp = compensationService.readComp(testComp.getEmployeeId());
        assertCompensationEquivalence(testComp, readServiceComp);
    }
    
    

    
    private static void assertCompensationEquivalence(Compensation expected, Compensation actual)
    {
    	assertEquals(expected.getEffectiveDate(), actual.getEffectiveDate());
    	assertEquals(expected.getSalary(), actual.getSalary());
    	assertEquals(expected.getEmployeeId(), actual.getEmployeeId());
    }
}
