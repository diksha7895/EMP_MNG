package com.employeemanagement.emp.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employeemanagement.emp.model.Employee;
import com.employeemanagement.emp.payload.request.EmployeeDetail;
import com.employeemanagement.emp.payload.request.UpdateEmployeeDetail;
import com.employeemanagement.emp.repository.EmployeeRepository;


@Service
public class EmployeeService {
	
	
	
	@Autowired
	EmployeeRepository employeeRepository;

	//getting all employee list
	public List<Employee> getAllEmployees(){
		List<Employee> allEmployees = employeeRepository.findAll();
		return allEmployees.stream().collect(Collectors.toList());
	}
	
	//getting employee details by id
	public Employee getEmployeeById(Long id) {
		Optional<Employee> employee = null;
		try {
			employee = employeeRepository.findById(id);
			//employee = getAllEmployees().stream().filter(e->e.getId()== id).findFirst().get();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return employee.get();
	}
	
	//create employee
	public Object addEmployee(@Valid EmployeeDetail employeeDetail) {
		if(employeeDetail == null) {
			return ("Some error.. Please try again later.");
		}
		Employee employee = new Employee();
		employee.setFirstname(employeeDetail.getFirstname());
		employee.setLastname(employeeDetail.getLastname());
		//employee.setUsername(employeeDetail.getUsername());
		employee.setEmail(employeeDetail.getEmail());
		employeeRepository.save(employee);
		
		return employee;
		
	}
	
	public boolean updateEmployee(Long employeeId, @Valid UpdateEmployeeDetail employeeDetails) {
		Optional<Employee> employee = employeeRepository.findById(employeeId);
		
		if(Boolean.TRUE.equals(employee.isPresent() && employeeDetails != null)) {
			
			if(employeeDetails.getFirstname() != null && !employeeDetails.getFirstname().isEmpty()) {
				employee.get().setFirstname(employeeDetails.getFirstname());
			}
			
			if(employeeDetails.getLastname() != null && !employeeDetails.getLastname().isEmpty()) {
				employee.get().setLastname(employeeDetails.getLastname());
			}
			
			if(employeeDetails.getEmail() != null && !employeeDetails.getEmail().isEmpty()) {
				employee.get().setEmail(employeeDetails.getEmail());
			}
			employeeRepository.save(employee.get());
			return true;
			
		}
		return false;
	}
	


	//delete existing employee
		public boolean deleteEmployee(Long empid) {
			Optional<Employee> employee = employeeRepository.findById(empid);
			
					if(employee.isPresent()) {
						employeeRepository.delete(employee.get());
						return true;
					}
					else {
						return false;
			}
		}

		
	
}
