package com.mindex.challenge.service;


import java.util.ArrayList;
import java.util.List;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportStructure;

public interface EmployeeService {
    Employee create(Employee employee);
    Employee read(String id);
    Employee update(Employee employee);
    List<Employee> readReport(String id);
    ReportStructure readReportStructure(String id);
}
