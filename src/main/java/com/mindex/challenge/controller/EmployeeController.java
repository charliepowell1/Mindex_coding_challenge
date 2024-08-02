package com.mindex.challenge.controller;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportStructure;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.EmployeeService;
//import com.mindex.challenge.service.CompensationService;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;
    /**@Autowired
    private CompensationService compensationService;**/

    @PostMapping("/employee")
    public Employee create(@RequestBody Employee employee) {
        LOG.debug("Received employee create request for [{}]", employee);

        return employeeService.create(employee);
    }

    @GetMapping("/employee/{id}")
    public Employee read(@PathVariable String id) {
        LOG.debug("Received employee create request for id [{}]", id);

        return employeeService.read(id);
    }

    @PutMapping("/employee/{id}")
    public Employee update(@PathVariable String id, @RequestBody Employee employee) {
        LOG.debug("Received employee create request for id [{}] and employee [{}]", id, employee);

        employee.setEmployeeId(id);
        return employeeService.update(employee);
    }
    
    @PostMapping("/compensation/{id}")
    public Compensation createComp(@RequestBody Compensation compensation, @PathVariable String id) {
    	LOG.debug("Received compensation create request for id [{}] and employee [{}]", compensation, id);

        return employeeService.createComp(compensation, id);
    }
    
    @GetMapping("/compensation/{id}")
    public Compensation readComp(@PathVariable String id) {
        LOG.debug("Received compensation search request for id [{}]", id);

        return employeeService.readComp(id);
    }
    
    @GetMapping("/reportStructure/{id}")
    public ReportStructure readReport(@PathVariable String id) {
        LOG.debug("Received direct search request for id [{}]", id);

        ArrayList<Employee> employeeList = employeeService.readReport(id);
        ReportStructure rs = new ReportStructure(employeeList, employeeList.size());
        return rs;
    }
    
}
