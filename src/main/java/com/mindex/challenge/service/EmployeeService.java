package com.mindex.challenge.service;


import java.util.ArrayList;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportStructure;

public interface EmployeeService {
    Employee create(Employee employee);
    Employee read(String id);
    Employee update(Employee employee);
    Compensation createComp(Compensation compensation, String id);
    Compensation readComp(String id);
    ArrayList<Employee> readReport(String id);
}
