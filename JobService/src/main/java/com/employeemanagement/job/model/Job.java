package com.employeemanagement.job.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "jobs")
public class Job {

	private Long id;
	
	private String jobName;
	
	private Double profit;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public Double getProfit() {
		return profit;
	}

	public void setProfit(Double profit) {
		this.profit = profit;
	}
	
	
}
