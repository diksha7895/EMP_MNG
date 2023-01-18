package com.employeemanagement.emp.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employeemanagement.emp.model.Employee;
import com.employeemanagement.emp.payload.request.EmployeeDetail;
import com.employeemanagement.emp.payload.request.UpdateEmployeeDetail;
import com.employeemanagement.emp.service.EmployeeService;
import com.employeemanagement.emp.payload.response.MessageResponse;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/empmng")
public class EmployeeController {
	
		
		
		@Autowired
		EmployeeService employeeService;
	
	 	@GetMapping("/getAllEmployee")
	    public ResponseEntity<List<Employee>> getAllEmployees() {
	       List<Employee> employees = employeeService.getAllEmployees();
	       if(employees.size()<=0) {
	    	   return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	       }
	       return ResponseEntity.of(Optional.of(employees));
	    }
	 	
	 	 @GetMapping("/getEmployee/{id}")
	     public ResponseEntity<?> getEmployeeById(@PathVariable(value = "id") Long employeeId){
	         
	         Employee employee = employeeService.getEmployeeById(employeeId);
	         if(employee == null) {
	        	 return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	         }
	           
	         return ResponseEntity.of(Optional.of(employee));
	     }
	 	 
	 	
	 	//**
	 	@PostMapping("/addEmployee")
	 	public ResponseEntity<?> addEmployee(@Valid @RequestBody EmployeeDetail employeeDetail){
	 		try {
	 			this.employeeService.addEmployee(employeeDetail);
	 			//return ResponseEntity.status(HttpStatus.CREATED).build();
	 			return ResponseEntity.ok(new MessageResponse("Employee added successfully."));
	 		}catch(Exception e) {
	 			e.printStackTrace();
	 			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	 		}
	 	}
	 	 
	 	@PostMapping("/updateEmployee/{id}")
	    public ResponseEntity<?> updateEmployee(@PathVariable(value = "id") Long employeeId,
	         @Valid @RequestBody UpdateEmployeeDetail employeeDetails) throws Exception {
	 		if(employeeId == null) {
	 			return ResponseEntity.badRequest().body("Invalid employee id");
	 		}
	 		if(employeeService.updateEmployee(employeeId,employeeDetails)) {
	 			return ResponseEntity.ok(new MessageResponse("Employee details updated successfully."));
	 		}
	    
	 		return ResponseEntity.badRequest().body("Employee details is not updated");
	    }
	 	
	 	@DeleteMapping("/deleteEmployee/{id}")
	 	public ResponseEntity<?> deleteEmployee(@PathVariable("id") Long employeeId){
	 		try {
	 				this.employeeService.deleteEmployee(employeeId);
	 				//return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	 				return ResponseEntity.ok(new MessageResponse("Employee details deleted successfully."));
	 		}catch(Exception e) {
	 			e.printStackTrace();
	 			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	 		}
	 	}
	 
}
