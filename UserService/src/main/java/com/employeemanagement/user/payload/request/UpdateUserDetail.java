package com.employeemanagement.user.payload.request;

import java.util.List;
import java.util.Set;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UpdateUserDetail {

	

		@NotBlank
		@Size(min = 2,max = 15)
		private String firstname;
		
		@NotBlank
		@Size(min = 2,max = 15)
		private String lastname;
		
		@NotBlank
		@Size(min = 2,max = 15)
		private String username;
		
		@NotBlank
		@Size(max = 50)
		@Email
	    private String email;
		
		@NotBlank
		@Size(min = 6,message="Password must contain 6 characters.")
		private String password;
		
		//private double salary;
		
		private Set<String> role;
		
		//private List<Integer> jobid;

		

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

//		public double getSalary() {
//			return salary;
//		}
//
//		public void setSalary(double salary) {
//			this.salary = salary;
//		}
		
		public Set<String> getRole() {
			return role;
		}

		public void setRole(Set<String> role) {
			this.role = role;
		}

		

//		public List<Integer> getJobid() {
//			return jobid;
//		}
//
//		public void setJobid(List<Integer> jobid) {
//			this.jobid = jobid;
//		}

		@Override
		public String toString() {
			return "UpdateUserDetail [firstname=" + firstname + ", lastname=" + lastname + ", username=" + username
					+ ", email=" + email + ", password=" + password + ", role=" + role + "]";
		}

		
		

}
