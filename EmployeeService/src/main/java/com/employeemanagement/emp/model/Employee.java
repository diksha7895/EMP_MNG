package com.employeemanagement.emp.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "employees")
public class Employee {

	  @Id
	  @GeneratedValue(strategy = GenerationType.AUTO)
	  private long id;

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
		
		private double salary;
		
		private List<Integer> jobid;

		public Employee() {
			
		}

		public Employee(long id,String firstname,
				String lastname,String username,
				String email, double salary,List<Integer> jobid) {
			super();
			this.id = id;
			this.firstname = firstname;
			this.lastname = lastname;
			this.username = username;
			this.email = email;
			this.salary = salary;
			this.jobid = jobid;
		}
		

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
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

		public double getSalary() {
			return salary;
		}

		public void setSalary(double salary) {
			this.salary = salary;
		}

		public List<Integer> getJobid() {
			return jobid;
		}

		public void setJobid(List<Integer> jobid) {
			this.jobid = jobid;
		}

		@Override
		public String toString() {
			return "Employee [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", username="
					+ username + ", email=" + email + ", salary=" + salary + ", jobid=" + jobid + "]";
		}

		
		
		
}
