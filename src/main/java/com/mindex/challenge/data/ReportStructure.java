package com.mindex.challenge.data;
/**
 * ReportStructure class. Consists of list of employees under a certain employee and the count of those employees. 
 */

import java.util.List;

public class ReportStructure{
	private Employee employee;
	private List <Employee> reports;
	private int numberOfReports;
	
	public ReportStructure(Employee employee, List <Employee> reports, int numberOfReports)
	{
		this.employee = employee;
		this.reports = reports;
		this.numberOfReports = numberOfReports;
		
	}
	public List <Employee> getReports() {
		return reports;
	}

	public void setReports(List <Employee> reports) {
		this.reports = reports;
	}

	public int getNumberOfReports() {
		return numberOfReports;
	}

	public void setNumberOfReports(int numberOfReports) {
		this.numberOfReports = numberOfReports;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	
	
	
	
	
}