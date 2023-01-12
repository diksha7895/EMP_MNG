package com.employeemanagement.emp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.employeemanagement.emp.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
	
}
