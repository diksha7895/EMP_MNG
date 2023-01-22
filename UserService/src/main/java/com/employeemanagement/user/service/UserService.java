package com.employeemanagement.user.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.employeemanagement.user.model.Role;
import com.employeemanagement.user.model.Roles;
import com.employeemanagement.user.model.User;
import com.employeemanagement.user.payload.request.EmployeeDetail;
import com.employeemanagement.user.payload.request.JobDetail;
import com.employeemanagement.user.payload.request.RegisterRequest;
import com.employeemanagement.user.payload.request.UpdateUserDetail;
import com.employeemanagement.user.payload.response.MessageResponse;
import com.employeemanagement.user.repository.RoleRepository;
import com.employeemanagement.user.repository.UserRepository;

@Service
public class UserService {

	@Value("${employeeservice.host}")
	private String employeeServiceHost;

	
	@Autowired
	RestApiCall restClient;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	public List<User> getAllUsers(){
		List<User> users = userRepository.findAll();
		
			return users;
		//return users.stream().collect(Collectors.toList());	
	}
	

	public User getUserById(Long id) throws Exception{
		Optional <User> user = userRepository.findById(id);
		return user.get();
	}
	
	public Object updateUser(UpdateUserDetail userDetail,Long id) throws Exception{
		System.out.println("Inside updateUser : {} "+id);
		Optional<User> user = userRepository.findById(id);
		System.out.println("User : "+user);
	if(user.isPresent())	{
		System.out.println("Inside if");
		if(userRepository.existsByUsername(userDetail.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already exist."));
		}
//		if(userRepository.existsByEmail(userDetail.getEmail())) {
//			System.out.println("userDetail.getEmail()"+userDetail.getEmail());
//			return ResponseEntity
//					.badRequest()
//					.body(new MessageResponse("Error: Email is already exist."));
//		}
		
		if(Objects.equals(user.get().getId(),id)) {
			if(userDetail.getUsername() != null && !userDetail.getUsername().isEmpty()) {
				user.get().setUsername(userDetail.getUsername());
			}
			if(userDetail.getFirstname() != null && !userDetail.getFirstname().isEmpty()) {
				user.get().setFirstname(userDetail.getFirstname());
			}
			if(userDetail.getLastname() != null && !userDetail.getLastname().isEmpty()) {
				user.get().setLastname(userDetail.getLastname());
			}
			if(userDetail.getEmail() != null && !userDetail.getEmail().isEmpty()) {
				user.get().setEmail(userDetail.getEmail());
			}
			if(userDetail.getPassword() != null && !userDetail.getPassword().isEmpty()) {
			user.get().setPassword(encoder.encode(userDetail.getPassword()));
			}
		}
	
		
		Set<String> strRoles = userDetail.getRole();
		System.out.println("userDetail.getRole() : "+strRoles);
		Set<Role> roles = new HashSet<>();
		if (strRoles == null) {
			Optional<User> userRole = userRepository.findByUsername(userDetail.getUsername());
			if(userRole.isPresent() && userRole.get().getRoles() != null && !userRole.get().getRoles().isEmpty()) {
				roles.addAll(userRole.get().getRoles());
			}
		}
	else {
		strRoles.forEach(role -> {
			switch (role) {
			case "trainee":
				Role traineeRole = roleRepository.findByName(Roles.ROLE_TRAINEE)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(traineeRole);
				break;
				
			case "tester":
				Role testerRole = roleRepository.findByName(Roles.ROLE_TESTER)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(testerRole);

				break;
			
			case "developer":
				Role devRole = roleRepository.findByName(Roles.ROLE_DEVELOPER)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(devRole);

				break;
				
			case "engineer":
				Role erRole = roleRepository.findByName(Roles.ROLE_ENGINEER)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(erRole);

				break;
				
			case "analyst":
				Role analystRole = roleRepository.findByName(Roles.ROLE_ANALYST)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(analystRole);
				break;
			}
		
		});
		user.get().setRoles(roles);
	  }
		//user.get().setRoles(roles);
		userRepository.save(user.get());
		return ResponseEntity.ok(new MessageResponse("User details updated successfully."));
	}
		
		else {
			return ResponseEntity.badRequest().body("User not found.");
		}
			
 }


	
		public Object deleteUser(Long id) throws Exception{
		Optional<User> user = userRepository.findById(id);
		if(user.isPresent()) {
			userRepository.delete(user.get());
			return ResponseEntity.ok(new MessageResponse("User deleted successfully."));
		}else {
			return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	
		
	public List<EmployeeDetail> getAllEmployees() {
		List<EmployeeDetail> empList = restClient.getAllEmployeeList("/EmployeeDetail");
			return empList;
	}

	public Object getEmployeeById(Long id) {
		ResponseEntity<Object> employee = restClient.getEmployeeById("/getEmployee/"+id);
		if(employee != null)
			return employee;
		return ("Some error.. Please try again later.");
	}

		
	// saving data in employee table at same time when user registers
	public Object addEmployee(@Valid RegisterRequest registerRequest) throws Exception {
		System.out.println("Inside addEmployee : {} "+registerRequest);
		ResponseEntity<?> addedEmployee = restClient.addEmployee("/addEmployee",registerRequest);
		if(addedEmployee == null) {
			return ("Some error.. Please try again later.");	
		}
		return addedEmployee;
	}
	
	//update user & employee data at same time
	public Object updateEmployee(UpdateUserDetail userDetail,Long id) throws Exception {
		ResponseEntity<?> updateEmployee = restClient.updateEmployee("/updateEmployee/"+id, userDetail);
		if(updateEmployee == null) {
			return ("Some error.. Please try again later.");
		}
		return updateEmployee;
	}
	
	//delete user & employee data at same time
	public Object deleteEmployee(Long id) throws Exception {
		return restClient.deleteEmployee("/deleteEmployee/"+id);
	}


	public ResponseEntity<?> createJob(JobDetail jobDetail) throws Exception {
		System.out.println("Inside createJob in UserService : {} "+jobDetail);
		//setting role for job creation
		Set<String> strRoles = Collections.singleton(jobDetail.getApplicableRole());
		//String strRoles = jobDetail.getApplicableRole();
		System.out.println("jobDetail.getApplicableRole() : "+strRoles);
		
		
		strRoles.forEach(role -> {
			switch (role) {
			case "trainee":
				Optional<Role> traineeRole = roleRepository.findByName(Roles.ROLE_TRAINEE);
				if(traineeRole.isPresent()) {
					jobDetail.setApplicableRole(String.valueOf(Roles.ROLE_TRAINEE)); 
				}
				
				break;
				
			case "tester":
				Optional<Role> testerRole = roleRepository.findByName(Roles.ROLE_TESTER);
				if(testerRole.isPresent()) {
					jobDetail.setApplicableRole(String.valueOf(Roles.ROLE_TESTER));
				}

				break;
			
			case "developer":
				Optional<Role> devRole = roleRepository.findByName(Roles.ROLE_DEVELOPER);
				if(devRole.isPresent()) {
					jobDetail.setApplicableRole(String.valueOf(Roles.ROLE_DEVELOPER));
				}
				break;
				
			case "engineer":
				Optional<Role> erRole = roleRepository.findByName(Roles.ROLE_ENGINEER);
				if(erRole.isPresent()) {
					jobDetail.setApplicableRole(String.valueOf(Roles.ROLE_ENGINEER));
				}
				break;
				
			case "analyst":
				Optional<Role> analystRole = roleRepository.findByName(Roles.ROLE_ANALYST);
				if(analystRole.isPresent()) {
					jobDetail.setApplicableRole(String.valueOf(Roles.ROLE_ANALYST));
				}
				break;
			}
		
		});
		System.out.println("After setting role for create job in UserService : {}"+jobDetail);
		ResponseEntity<?> addJob = restClient.createJob("createJob",jobDetail);
		if(addJob == null) {
			return ResponseEntity.badRequest().body("User not found.");	
		}
		return addJob;
	}


	public Object modifyJob(@Valid JobDetail jobDetail, Long id) {
		Set<String> strRoles = Collections.singleton(jobDetail.getApplicableRole());
		

		strRoles.forEach(role -> {
			switch (role) {
			case "trainee":
				Optional<Role> traineeRole = roleRepository.findByName(Roles.ROLE_TRAINEE);
				if(traineeRole.isPresent()) {
					jobDetail.setApplicableRole(String.valueOf(Roles.ROLE_TRAINEE)); 
				}
				
				break;
				
			case "tester":
				Optional<Role> testerRole = roleRepository.findByName(Roles.ROLE_TESTER);
				if(testerRole.isPresent()) {
					jobDetail.setApplicableRole(String.valueOf(Roles.ROLE_TESTER));
				}

				break;
			
			case "developer":
				Optional<Role> devRole = roleRepository.findByName(Roles.ROLE_DEVELOPER);
				if(devRole.isPresent()) {
					jobDetail.setApplicableRole(String.valueOf(Roles.ROLE_DEVELOPER));
				}
				break;
				
			case "engineer":
				Optional<Role> erRole = roleRepository.findByName(Roles.ROLE_ENGINEER);
				if(erRole.isPresent()) {
					jobDetail.setApplicableRole(String.valueOf(Roles.ROLE_ENGINEER));
				}
				break;
				
			case "analyst":
				Optional<Role> analystRole = roleRepository.findByName(Roles.ROLE_ANALYST);
				if(analystRole.isPresent()) {
					jobDetail.setApplicableRole(String.valueOf(Roles.ROLE_ANALYST));
				}
				break;
			}
		
		});
		
		ResponseEntity<?> updateJob = restClient.modifyJob("modifyJob/"+id, jobDetail);
		if(updateJob == null) {
			return ("Some error.. Please try again later.");
		}
		return updateJob;
	}


	public List<JobDetail> getAllJobs() {
		List<JobDetail> jobList = restClient.getAllJobList("/Jobs");
		return jobList;
		
	}


	public Object processJob(Long jobid, Long userid, String status, String role) {
		ResponseEntity<?> processJob = restClient.processJob("processJobs/"+ jobid + "/"+ userid + "/"+ status + "/" + role);
		if(processJob!=null) {
			return processJob;
		}
		return ResponseEntity.badRequest().body("Job not processed.. ERROR : selected job not is not applicable for your role.");
		
	}
	
	


	
}
