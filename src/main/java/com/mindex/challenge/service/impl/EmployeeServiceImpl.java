package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportStructure;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee create(Employee employee) {
        LOG.debug("Creating employee [{}]", employee);

        //employee.setEmployeeId(UUID.randomUUID().toString());
        employeeRepository.insert(employee);

        return employee;
    }

    @Override
    public Employee read(String id) {
        LOG.debug("Creating employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        return employee;
    }

    @Override
    public Employee update(Employee employee) {
        LOG.debug("Updating employee [{}]", employee);

        return employeeRepository.save(employee);
    }
    
    /**
     * Logic handler for gathering all direct and indirect employees. 
     * Recursively adds to a list of employees by checking if the given employee
     * has direct reports. If so the method is called again to examine those direct reports.
     */
    public List<Employee> readReport(String id) {
    	Employee employee = read(id);
    	
    	if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }
    	//LOG.debug("DIRECTOOOOOO: " + employee.getDirectReports());
    	List<Employee> finalList = new ArrayList<Employee>();
    	
    		if(employee.getDirectReports() == null) {
    			finalList.add(employee);
    		}
    		else
    		{
    			for(Employee emp : employee.getDirectReports())
    			{
    				emp = read(emp.getEmployeeId());
    				if(emp.getDirectReports() == null){
    					finalList.add(emp);
    				}
    				else {
    					finalList.add(emp);
    					finalList.addAll(readReport(emp.getEmployeeId()));
    				}
    			
    			}
    		}
    	
    	return finalList;
    	
    	
    }
    /**
     * Primary method called by controller. 
     * Separate from the readReport structure to enable recursive addition to ArrayList.
     * Mainly used to create ReportStructure object out of the list of reports.
     */
    public ReportStructure readReportStructure(String id)
    {
    	List <Employee> reports = new ArrayList <Employee>();
    	reports = readReport(id);
    	int size = reports.size();
    	if(size == 1)
    	{
    		size = 0;
    	}
    	ReportStructure rs = new ReportStructure(reports, reports.size());
    	return rs;
    }
//    public int helper(Employee employee, int count)
//    {
//    	
//    	/**if(employee.getDirectReports() == null || employee.getDirectReports().isEmpty()) {
//    		return 0;
//    		//return count;
//    	}
//    	for(Employee emp : employee.getDirectReports())
//    	{
//    		//emp = read(employee.getEmployeeId());
//    		count++;
//    		count += helper(emp, count);
//    	}
//    	
//    	return count;**/
//    	return 0;
//    }
   
    
   
}
