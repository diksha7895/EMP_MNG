package com.employeemanagement.job.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import com.employeemanagement.job.model.Job;
import com.employeemanagement.job.model.Status;
import com.employeemanagement.job.payload.request.JobDetail;
import com.employeemanagement.job.payload.request.UpdateJobDetail;
import com.employeemanagement.job.payload.response.MessageResponse;
import com.employeemanagement.job.repository.JobRepository;

@Service
public class JobService {
	
	@Autowired
	JobRepository jobRepository;
	

	public ResponseEntity<?> createJob(@Valid JobDetail jobDetail) {
			try {
				Job job = new Job();
				job.setJobname(jobDetail.getJobname());
				job.setStarttime(jobDetail.getStarttime());
				job.setEndtime(jobDetail.getEndtime());
				job.setProfit(jobDetail.getProfit());
				job.setApplicableRole(jobDetail.getApplicableRole());
				job.setStatus(Status.NOT_STARTED);
				jobRepository.save(job);
			}catch (Exception exception) {
				return ResponseEntity.internalServerError().body("Error: "+exception.getMessage());
			}
			//return ResponseEntity.status(HttpStatus.CREATED).build();
			return ResponseEntity.ok(new MessageResponse("Job created successfully."));
	}

	public boolean modifyJob(JobDetail updateJob, Long id) {
		Optional<Job> job = jobRepository.findById(id);
		if(Boolean.TRUE.equals(job.isPresent() && updateJob != null)) {
			
			if(updateJob.getJobname() != null && !updateJob.getJobname().isEmpty()) {
				job.get().setJobname(updateJob.getJobname());
			}
			if(updateJob.getStarttime() != null) {
				job.get().setStarttime(updateJob.getStarttime());
			}
			if(updateJob.getEndtime() != null) {
				job.get().setEndtime(updateJob.getEndtime());
			}
			if(updateJob.getProfit() != 0) {
				job.get().setProfit(updateJob.getProfit());
			}
			if(updateJob.getApplicableRole() != null && !updateJob.getApplicableRole().isEmpty()) {
				job.get().setApplicableRole(updateJob.getApplicableRole());
			}
			job.get().setJobUpdatedDate(LocalDate.now());
			jobRepository.save(job.get());
			return true;
			
		}
		return false;
	}

	public List<Job> findAllJobs() {
		List<Job> allJobs = jobRepository.findAll();
		return allJobs.stream().collect(Collectors.toList());
	}

	public boolean processJob(JobDetail jobDetail, Long id) {
		
		return false;
	}
	
	

}
