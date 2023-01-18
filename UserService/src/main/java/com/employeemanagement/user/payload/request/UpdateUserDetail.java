package com.employeemanagement.user.payload.request;


import java.util.Set;


import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class UpdateUserDetail {

		@Size(min = 2,max = 15)
		private String firstname;
		
		
		@Size(min = 2,max = 15)
		private String lastname;
		
		
		@Size(min = 3,max = 15)
		private String username;
		
		
		@Size(max = 50)
		@Email
	    private String email;
		
		
		@Size(min = 6,message="Password must contain 6 characters.")
		private String password;
		
		private Set<String> role;
		
		

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

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
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

		
		public Set<String> getRole() {
			return role;
		}

		public void setRole(Set<String> role) {
			this.role = role;
		}

		@Override
		public String toString() {
			return "UpdateUserDetail [firstname=" + firstname + ", lastname=" + lastname + ", username=" + username
					+ ", email=" + email + ", password=" + password + ", role=" + role + "]";
		}

		
		

}
