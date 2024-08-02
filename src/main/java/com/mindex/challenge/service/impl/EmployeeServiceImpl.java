package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CompensationRepository compensationRepository;

    @Override
    public Employee create(Employee employee) {
        LOG.debug("Creating employee [{}]", employee);

        employee.setEmployeeId(UUID.randomUUID().toString());
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
    
    @Override
    public Compensation createComp(Compensation compensation, String id) {
        LOG.debug("Creating compensation [{}]", compensation);
        
        
        //employee.setCompensation(compensation);
        

        return compensationRepository.save(compensation);
    }
    
    public Compensation readComp(String id) {
    Compensation comp = compensationRepository.findByEmployeeId(id);
    	
    	if (comp == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }
    	//LOG.debug("WHY AINT THERE NOTHIN: " + comp.getCompensation());
    	
    	return comp;
    }
    
    public ArrayList<Employee> readReport(String id) {
    	Employee employee = read(id);
    	
    	if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }
    	LOG.debug("DIRECTOOOOOO: " + employee.getDirectReports());
    	ArrayList<Employee> finalList = new ArrayList<Employee>();
    	
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
