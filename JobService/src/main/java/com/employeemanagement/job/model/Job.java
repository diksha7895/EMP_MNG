package com.employeemanagement.job.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "jobs")
public class Job {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "jobid")
	private Long id;
	
	@NotBlank
	private String jobname;
	
	 @JsonFormat(pattern = "HH:mm")
	 private LocalTime starttime;

	 @JsonFormat(pattern = "HH:mm")
	 private LocalTime endtime;
	
	 private Double profit;

	 private String applicableRole;
	 
	 @JsonFormat(pattern = "dd-MM-yyyy")
	 private LocalDate jobStartDate;

	 @JsonFormat(pattern = "dd-MM-yyyy")
	 private LocalDate jobUpdatedDate;

	 @Enumerated(EnumType.STRING)
	 private Status status;
	 
	 @Column(name = "employeeId")
	 private Long employeeId;
	 
	 private LocalTime jobstarttime;
	 
	 public Job() {
			super();
			// TODO Auto-generated constructor stub
		}

		public Job(Long id, @NotBlank String jobname, LocalTime starttime, LocalTime endtime, Double profit,
				String applicableRole, LocalDate jobStartDate, LocalDate jobUpdatedDate, Status status,Long employeeId) {
			super();
			this.id = id;
			this.jobname = jobname;
			this.starttime = starttime;
			this.endtime = endtime;
			this.profit = profit;
			this.applicableRole = applicableRole;
			this.jobStartDate = jobStartDate;
			this.jobUpdatedDate = jobUpdatedDate;
			this.status = status;
			this.employeeId = employeeId;
		}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public LocalDate getJobStartDate() {
		return jobStartDate;
	}

	public void setJobStartDate(LocalDate jobStartDate) {
		this.jobStartDate = jobStartDate;
	}

	public LocalDate getJobUpdatedDate() {
		return jobUpdatedDate;
	}

	public void setJobUpdatedDate(LocalDate jobUpdatedDate) {
		this.jobUpdatedDate = jobUpdatedDate;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	

	public Long getEmpid() {
		return employeeId;
	}

	public void setEmpid(Long empid) {
		this.employeeId = empid;
	}

	public LocalTime getJobstarttime() {
		return jobstarttime;
	}

	public void setJobstarttime(LocalTime jobstarttime) {
		this.jobstarttime = jobstarttime;
	}

	@Override
	public String toString() {
		return "Job [id=" + id + ", jobname=" + jobname + ", starttime=" + starttime + ", endtime=" + endtime
				+ ", profit=" + profit + ", applicableRole=" + applicableRole + ", jobStartDate=" + jobStartDate
				+ ", jobUpdatedDate=" + jobUpdatedDate + ", status=" + status + ", empid=" + employeeId + ", jobstarttime="
				+ jobstarttime + "]";
	}

	
	 
	 
	
}
