package com.employeemanagement.emp.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employeemanagement.emp.model.Employee;
import com.employeemanagement.emp.repository.EmployeeRepository;

@Service
public class EmployeeService {
	
	@Autowired
	EmployeeRepository employeeRepository;

	public List<Employee> getAllEmployees(){
		List<Employee> allEmployees = employeeRepository.findAll();
		return allEmployees.stream().collect(Collectors.toList());
	}
	
	public Employee getEmployeeById(Long id) {
		Employee employee = null;
		try {
			employee = getAllEmployees().stream().filter(e->e.getId()== id).findFirst().get();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return employee;
	}
}
