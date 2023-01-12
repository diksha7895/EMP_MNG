package com.employeemanagement.emp.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.employeemanagement.emp.model.Employee;
import com.employeemanagement.emp.payload.response.MessageResponse;
import com.employeemanagement.emp.repository.EmployeeRepository;
import com.employeemanagement.emp.service.EmployeeService;

@RestController
public class EmployeeController {
	
		@Autowired
	    private EmployeeRepository employeeRepository;
		
		@Autowired
		EmployeeService employeeService;
	
	 	@GetMapping("/EmployeeDetail")
	    public ResponseEntity<List<Employee>> getAllEmployees() {
	       List<Employee> employees = employeeService.getAllEmployees();
	       if(employees.size()<=0) {
	    	   return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	       }
	       return ResponseEntity.of(Optional.of(employees));
	    }
	 	
	 	 @GetMapping("/EmployeeDetail/{id}")
	     public ResponseEntity<?> getEmployeeById(@PathVariable(value = "id") Long employeeId){
	         
	         Employee employee = employeeService.getEmployeeById(employeeId);
	         if(employee == null) {
	        	 return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	         }
	           
	         return ResponseEntity.of(Optional.of(employee));
	     }
	 	 
	 	 
	 	@PutMapping("/employees/{id}")
	    public ResponseEntity<?> updateEmployee(@PathVariable(value = "id") Long employeeId,
	         @Valid @RequestBody Employee employeeDetails) throws Exception {
	        Employee employee = employeeService.getEmployeeById(employeeId);
	        
	        if(employee == null) {
	        	 return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	         }
	        employee.setFirstname(employeeDetails.getFirstname());
	        employee.setLastname(employeeDetails.getLastname());
	        employee.setEmail(employeeDetails.getEmail());
	        final Employee updatedEmployee = employeeRepository.save(employee);
	        return ResponseEntity.ok(updatedEmployee);
	    }
	 
}
