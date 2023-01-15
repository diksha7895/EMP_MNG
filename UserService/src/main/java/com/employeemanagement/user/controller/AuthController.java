package com.employeemanagement.user.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employeemanagement.user.jwt.JwtUtils;
import com.employeemanagement.user.jwt.UserDetailsImpl;
import com.employeemanagement.user.model.User;
import com.employeemanagement.user.payload.request.LoginRequest;
import com.employeemanagement.user.payload.request.RegisterRequest;
import com.employeemanagement.user.payload.request.UpdateUserDetail;
import com.employeemanagement.user.payload.response.JwtResponse;
import com.employeemanagement.user.payload.response.MessageResponse;
import com.employeemanagement.user.repository.UserRepository;
import com.employeemanagement.user.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/empmng")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	JwtUtils jwtUtils;
	

	@Autowired
	private UserService userService;
	
	String updateUser= "http://localhost:8082/empmng/manager/";
	
	//new user registration
	
	@PostMapping("/RegisterUser")
	public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) throws Exception {
		System.out.println("RegisterRequest : " +registerRequest);
		if (userRepository.existsByUsername(registerRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already exist."));
		}

		if (userRepository.existsByEmail(registerRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already exist."));
		}

		// Create new user's account
		User user = new User(registerRequest.getFirstname(),
							registerRequest.getLastname(),
							registerRequest.getUsername(),
							registerRequest.getEmail(),
							encoder.encode(registerRequest.getPassword()));

		userRepository.save(user);
		
		userService.addEmployee(registerRequest);

		return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("User registered successfully!"));
	}
	
	
	//for user login
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, 
												 userDetails.getId(), 
												 userDetails.getFirstname(),
												 userDetails.getLastname(),
												 userDetails.getUsername(), 
												 userDetails.getEmail(), 
												 roles));
	}
	
	

	//update user details for users & emp table
	@PostMapping("/updateUser/{id}")
	public ResponseEntity<?> updateUser(@Valid @RequestBody UpdateUserDetail userDetail,
			@PathVariable(value = "id") Long id) throws Exception{
		System.out.println("Inside updateUser : "+id);
		Object updateUser = userService.updateUser(userDetail, id);
		Object updateEmp = userService.updateEmployee(userDetail, id);
		System.out.println("Inside updateUser : updateUser "+updateUser);
		System.out.println("Inside updateUser : updateEmp "+updateEmp);
		if(updateUser == null && updateEmp == null) {
			return ResponseEntity.badRequest().body("User details is not updated");
		}
		
		return ResponseEntity.ok(new MessageResponse("User Updated Successfully."));
	}
	
	//delete user details from users & emp table
	@DeleteMapping("/deleteUser/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Long id) throws Exception{
		
			Object deleterUserResponse = userService.deleteUser(id);
			Object deleteEmpResponse = userService.deleteEmployee(id);
			if(deleterUserResponse != null && deleteEmpResponse != null) {
				return ResponseEntity.ok(new MessageResponse("User deleted Successfully."));
			}
			
				return ResponseEntity.badRequest().body("User is not deleted.");
	}
	
	@GetMapping("/getUsers")
	public ResponseEntity<?> getAllUsers() throws Exception{
		List<User> users = userService.getAllUsers();
	       if(users.size()<=0) {
	    	   return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	       }
	       return ResponseEntity.of(Optional.of(users));
	}

	@GetMapping("/getUser/{id}")
	public ResponseEntity<?> getUserById(@PathVariable(value = "id")Long id) throws Exception{
		System.out.println("Inside getUserById id : "+id);
		Object user = userService.getUserById(id);
		System.out.println("user : {} "+user);
        if(user == null) {
       	 return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
          
        return ResponseEntity.of(Optional.of(user));
	}
	
	@GetMapping("/EmployeeDetail")
    public ResponseEntity<?> getAllEmployees() throws Exception{
       Object employees = userService.getAllEmployees();
       if(employees == null) {
    	   return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
       }
       return ResponseEntity.of(Optional.of(employees));
    }
 	
 	 @GetMapping("/EmployeeDetail/{id}")
     public ResponseEntity<?> getEmployeeById(@PathVariable(value = "id") Long id){
 		System.out.println("Inside getEmployeeById id : "+id);
         Object employee = userService.getEmployeeById(id);
         if(employee == null) {
        	 return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
         }
           
         return ResponseEntity.of(Optional.of(employee));
     }
}
