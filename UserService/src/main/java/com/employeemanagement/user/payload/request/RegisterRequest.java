package com.employeemanagement.user.payload.request;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RegisterRequest {
	
	@NotBlank(message = "User name is mandatory.")
	@Size(min = 2,max = 15,message="User name must be between 2 - 15 characters.")
	private String username;
	 
	@NotBlank(message = "First name is mandatory.")
	@Size(min = 2,max = 15,message="First name must be between 2 - 15 characters.")
	private String firstname;
	
	@NotBlank(message = "Last name is mandatory.")
	@Size(min = 2,max = 15,message="Last name must be between 2 - 15 characters.")
	private String lastname;
	
	@NotBlank(message="Email is mandatory.")
	@Pattern(regexp="^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message="Invalid Email.")
	@Email
	private  String email;
	
	@NotBlank(message = "Password can not be blank.")
	@Size(min = 6,message="Password must contain 6 characters.")
	//@Pattern(regexp="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\\\S+$)$", message="Invalid Password.")
	private String password;
	
	//private Set<String> role;


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


//	public Set<String> getRole() {
//		return role;
//	}
//
//	public void setRole(Set<String> role) {
//		this.role = role;
//	}
	
	@Override
	public String toString() {
		return "RegisterRequest [username=" + username + ", firstname=" + firstname + ", lastname=" + lastname
				+ ", email=" + email + ", password=" + password + "]";
	}
	
	
	
}
