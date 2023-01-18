package com.employeemanagement.job.payload.request;

import java.time.LocalTime;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;

public class UpdateJobDetail {

	@NotBlank
	private String jobname;
	
	 @JsonFormat(pattern = "HH:mm")
	 private LocalTime starttime;

	 @JsonFormat(pattern = "HH:mm")
	 private LocalTime endtime;
	
	 private Double profit;

	 private String applicableRole;

	public UpdateJobDetail() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UpdateJobDetail(@NotBlank String jobname, LocalTime starttime, LocalTime endtime, Double profit,
			String applicableRole) {
		super();
		this.jobname = jobname;
		this.starttime = starttime;
		this.endtime = endtime;
		this.profit = profit;
		this.applicableRole = applicableRole;
	}

	public String getJobname() {
		return jobname;
	}

	public void setJobname(String jobname) {
		this.jobname = jobname;
	}

	public LocalTime getStarttime() {
		return starttime;
	}

	public void setStarttime(LocalTime starttime) {
		this.starttime = starttime;
	}

	public LocalTime getEndtime() {
		return endtime;
	}

	public void setEndtime(LocalTime endtime) {
		this.endtime = endtime;
	}

	public Double getProfit() {
		return profit;
	}

	public void setProfit(Double profit) {
		this.profit = profit;
	}

	public String getApplicableRole() {
		return applicableRole;
	}

	public void setApplicableRole(String applicableRole) {
		this.applicableRole = applicableRole;
	}
	 
	 
}
