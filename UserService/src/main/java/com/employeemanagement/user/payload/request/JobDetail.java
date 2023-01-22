package com.employeemanagement.user.payload.request;

import java.time.LocalTime;
import java.util.Set;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;

public class JobDetail {
	
	@NotBlank
	private String jobname;
	
	 @JsonFormat(pattern = "HH:mm")
	 private LocalTime starttime;

	 @JsonFormat(pattern = "HH:mm")
	 private LocalTime endtime;
	
	 private Double profit;

	 private String applicableRole;
	// private Set<String> applicableRole;

	public JobDetail() {
		super();
		// TODO Auto-generated constructor stub
	}

	public JobDetail(String jobname, LocalTime starttime, LocalTime endtime, Double profit,
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

	@Override
	public String toString() {
		return "JobDetail [jobname=" + jobname + ", starttime=" + starttime + ", endtime=" + endtime + ", profit="
				+ profit + ", applicableRole=" + applicableRole + "]";
	}
	 

}
