package com.employeemanagement.job.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employeemanagement.job.model.Job;
import com.employeemanagement.job.payload.request.JobDetail;
import com.employeemanagement.job.payload.request.UpdateJobDetail;
import com.employeemanagement.job.repository.JobRepository;
import com.employeemanagement.job.service.JobService;
import com.employeemanagement.job.payload.response.MessageResponse;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/empmng")
public class JobController {
	
	@Autowired
	JobService jobService;
	
	@Autowired
	JobRepository jobRepository;
	
	
	@GetMapping("/Jobs")
	public ResponseEntity<List<Job>> getAllJobs() {
		List<Job> jobs = jobService.findAllJobs();
		if(jobs.size()<=0) {
	    	   return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	       }
	       return ResponseEntity.of(Optional.of(jobs));
	}
	
	@PostMapping("/createJob")
	public ResponseEntity<?> createJob(@Valid @RequestBody JobDetail jobDetail) {
			return jobService.createJob(jobDetail);
		
	}
	

	@PostMapping("/modifyJob/{id}")
	public ResponseEntity<?> modifyJob(@RequestBody JobDetail updateJob,
			@PathVariable(value = "id") Long id){
		if(id == null)
			return ResponseEntity.badRequest().body("Invalid job id.");
		if(jobService.modifyJob(updateJob, id)) {
			return new ResponseEntity<>("Job updated successfully!", HttpStatus.OK);
		}
		return ResponseEntity.badRequest().body("Job is not updated.");
	}
	
	@GetMapping("/processJobs/{jobid}/{userid}/{status}/{role}")
	public ResponseEntity<?> processJob(@PathVariable Long jobid, @PathVariable Long userid,
			@PathVariable String status,@PathVariable String role){
		System.out.println("Inside processJob in JobController : userid : "+userid+"jobid :"+jobid+"role :"+role+"status :"+status);
		if(jobid == null)
			return ResponseEntity.badRequest().body("Invalid job id.");
		if(jobService.processJob(jobid,userid,status,role) != null) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.badRequest().body("Job not processed.. ERROR : selected job not is not applicable for your role.");
	}
	
	

}
