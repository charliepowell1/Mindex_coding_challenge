package com.mindex.challenge.data;

import java.util.List;

public class ReportStructure{
	private List <Employee> employee;
	private int numberOfReports;
	
	public ReportStructure(List <Employee> employee, int numberOfReports)
	{
		this.employee = employee;
		this.numberOfReports = numberOfReports;
		
	}
	public List <Employee> getEmployee() {
		return employee;
	}

	public void setEmployee(List <Employee> employee) {
		this.employee = employee;
	}

	public int getNumberOfReports() {
		return numberOfReports;
	}

	public void setNumberOfReports(int numberOfReports) {
		this.numberOfReports = numberOfReports;
	}

	
	
	
	
	
}