package com.employeemanagement.user.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.employeemanagement.user.model.Role;
import com.employeemanagement.user.model.Roles;
import com.employeemanagement.user.model.User;
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
	
	public List<User> getAllUsers(){
		List<User> users = userRepository.findAll();
		return users.stream().collect(Collectors.toList());
	}
	

	public User getUserById(Long id) throws Exception{
		Optional <User> user = userRepository.findById(id);
		return user.get();
	}
	
	public Object updateUser(UpdateUserDetail userDetail,Long id) throws Exception{
			System.out.println("Inside updateUser : {} "+id);
			Optional<User> user = userRepository.findById(id);
			System.out.println("User : "+user);
			if(userRepository.existsByUsername(userDetail.getUsername())) {
				return ResponseEntity
						.badRequest()
						.body(new MessageResponse("Error: Username is already exist."));
			}
			if(userRepository.existsByEmail(userDetail.getEmail())) {
				return ResponseEntity
						.badRequest()
						.body(new MessageResponse("Error: Email is already exist."));
			}
			if(user.isPresent() && user.get().getId() == id) {
				user.get().setUsername(userDetail.getUsername());
				user.get().setFirstname(userDetail.getFirstname());
				user.get().setLastname(userDetail.getLastname());
				user.get().setEmail(userDetail.getEmail());
				user.get().setPassword(userDetail.getPassword());
			}
			
			Set<String> strRoles = userDetail.getRole();
			System.out.println("userDetail.getRole() : "+strRoles);
			Set<Role> roles = new HashSet<>();
			if (strRoles == null) {
				Optional<User> userRole = userRepository.findByUsername(userDetail.getUsername());
				roles.addAll(userRole.get().getRoles());
		}else {
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
					
				default:
					Role analystRole = roleRepository.findByName(Roles.ROLE_ANALYST)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(analystRole);
				}
			
			});
		}
			user.get().setRoles(roles);
			userRepository.save(user.get());
			return ResponseEntity.ok(new MessageResponse("User details updated successfully."));
				
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
	
	

	public Object getAllEmployees() {
		ResponseEntity<Object> empList = restClient.getAllEmployeeList("/getAllEmployee");
		if(empList != null)
			return empList;
		return ("Some error.. Please try again later.");
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
		ResponseEntity<String> addedEmployee = restClient.addEmployee("/addEmployee",registerRequest);
		if(addedEmployee == null) {
			return ("Some error.. Please try again later.");	
		}
		return addedEmployee;
	}
	
	//update user & employee data at same time
	public Object updateEmployee(UpdateUserDetail userDetail,Long id) throws Exception {
		ResponseEntity<String> updateEmployee = restClient.updateEmployee("/updateEmployee/"+id, userDetail);
		if(updateEmployee == null) {
			return ("Some error.. Please try again later.");
		}
		return updateEmployee;
	}
	
	//delete user & employee data at same time
	public Object deleteEmployee(Long id) throws Exception {
		return restClient.deleteEmployee("/deleteEmployee/"+id);
	}


	
}
