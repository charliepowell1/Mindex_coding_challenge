package com.mindex.challenge.data;
/**
 * Compensation class. Consists of a date, salary and an id specific to an employee object
 */

public class Compensation {
	private String effectiveDate;
	private String salary;
	private String employeeId;

	public Compensation() {
		
	}
	
	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}
	
}