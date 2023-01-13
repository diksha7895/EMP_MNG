package com.employeemanagement.user.payload.request;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class LoginRequest {
	
	@NotBlank(message = "User name is mandatory.")
	@Size(min = 2,max = 15,message="User name must be between 2 - 15 characters.")
	private String username;
	
	@NotBlank(message = "Password can not be blank.")
	@Size(min = 6,message="Password must contain 6 characters.")
	//@Pattern(regexp="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\\\S+$).{6,8}$", message="Invalid Password.")
	private String password;


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
