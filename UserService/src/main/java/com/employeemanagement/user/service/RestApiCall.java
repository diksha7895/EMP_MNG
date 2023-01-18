package com.employeemanagement.user.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.employeemanagement.user.payload.request.JobDetail;
import com.employeemanagement.user.payload.request.RegisterRequest;
import com.employeemanagement.user.payload.request.UpdateUserDetail;
import com.employeemanagement.user.payload.response.MessageResponse;

@Component
public class RestApiCall {
	
	private static final String EMPLOYEE_URL = "http://localhost:8082/empmng/";
	private static final String JOB_URL = "http://localhost:8083/empmng/";

	 @Autowired
	 RestTemplate restTemplate;	
	
	public ResponseEntity<String> addEmployee(String url,RegisterRequest request) {
		System.out.println("Inside addEmployee in rest call : {} "+request);
		String uri = EMPLOYEE_URL + url;
		System.out.println("url : "+url);
		String result = restTemplate.postForObject(uri, request, String.class);
		return ResponseEntity.ok(result);
	}
	
	
	public ResponseEntity<String> updateEmployee(String url, UpdateUserDetail userDetail){
		String uri = EMPLOYEE_URL + url;
		String result = restTemplate.postForObject(uri, userDetail, String.class);
		return ResponseEntity.ok(result);
	}
	
	
	public ResponseEntity<?> deleteEmployee(String url){
		//String uri = EMPLOYEE_URL + "url";
		 restTemplate.delete(EMPLOYEE_URL + url);
		return ResponseEntity.ok(new MessageResponse("Employee deleted successfully."));
	}


	public ResponseEntity<Object> getAllEmployeeList(String url) {
		Object empList = restTemplate.getForObject(EMPLOYEE_URL + url,List.class);
		return ResponseEntity.ok(empList);
	}


	public ResponseEntity<Object> getEmployeeById(String url) {
		Object empDetails = restTemplate.getForObject(EMPLOYEE_URL + url,Object.class);
		return ResponseEntity.ok(empDetails);
	}


	public ResponseEntity<String> createJob(String url, JobDetail jobDetail) {
		String uri = JOB_URL + url;
		
		String jobResult = restTemplate.postForObject(uri, jobDetail, String.class);
		return ResponseEntity.ok(jobResult);
	}


	public ResponseEntity<String> modifyJob(String url, @Valid JobDetail jobDetail) {
		String uri = JOB_URL + url;
		String jobResult = restTemplate.postForObject(uri, jobDetail, String.class);
		return ResponseEntity.ok(jobResult);
	}
	

}
