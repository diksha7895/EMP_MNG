package com.employeemanagement.job.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
	
	@Autowired
	RestTemplate restTemplate;
	
	private static final String EMPLOYEE_URL = "http://localhost:8082/empmng";
	

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


	public ResponseEntity<?> processJob(Long jobid, Long userid, String status, String role) {
		 Optional<Job> job = jobRepository.findById(jobid);
		 if (job.isPresent() && role!=null && !role.isEmpty() && job.get().getApplicableRole().equals(role)) {
	            if(status !=null && !status.isEmpty() && userid !=null) {
	                switch (status) {
	                case "Allocate":
                        if(job.get().getStatus()==Status.NOT_STARTED){
                        List<Job> existingJob = jobRepository.findByEmployeeId(userid);
                        if (!existingJob.isEmpty()) {
                            boolean result = false;
                            for (int i = 0; i < existingJob.size(); i++) {
                                result = job.get().getStarttime().isBefore(existingJob.get(i).getEndtime());
                                if (result) {
                                    return ResponseEntity
                                    		.badRequest()
                                    		.body(new MessageResponse("Job time overlapped."));
                                }
                            }
                            job.get().setJobstarttime(LocalTime.now());
                            job.get().setStatus(Status.IN_PROGRESS);
                            job.get().setEmpid(userid);
                            
	                }else {
	                	job.get().setJobstarttime(LocalTime.now());
                        job.get().setStatus(Status.IN_PROGRESS);
                        job.get().setEmpid(userid);
	                }
	                }
                        else {
                        	return ResponseEntity
                            		.badRequest()
                            		.body(new MessageResponse("Job is already in progress or aborted by someone."));
                        }
                        break;
                        
	                case "Abort":
                        if(job.get().getStatus().equals(Status.IN_PROGRESS)){
                            LocalTime time = job.get().getJobstarttime();
                            LocalTime presentTime = LocalTime.now();
                            long value= time.until(presentTime, ChronoUnit.MINUTES);
                            System.out.println("The time difference in minutes is"+ value);
                            if(value < 20) {
                                job.get().setStatus(Status.NOT_STARTED);
                                job.get().setEmpid(null);
                            }
                            else{
                                job.get().setStatus(Status.ABORTED);
                                job.get().setEmpid(null);
                            }
                        }
                        else{
                            return ResponseEntity.ok(new MessageResponse("Job is not aborted"));
                        }
                        break;
                        
	                case "Complete":
                        if(job.get().getStatus().equals(Status.IN_PROGRESS)) {
                            job.get().setStatus(Status.COMPLETED);
                            String url = "/updateSalary/"+userid+"/"+job.get().getProfit();
                            System.out.println(url);
                            String result = restTemplate.getForObject(EMPLOYEE_URL + url,String.class);
                            assert result != null;
                            job.get().setEmpid(null);
                        }
                        else{
                        	return ResponseEntity.ok(new MessageResponse("Job can't be completed"));
                        }
                        break;

	}
	
	}
	            jobRepository.save(job.get());
	            //return ResponseEntity.ok(new MessageResponse("Job processed successfully."));
	            return ResponseEntity.ok(new MessageResponse("Job "+ status));
		 }else {
			 return ResponseEntity
             		.badRequest()
             		.body(new MessageResponse("Job not processed.. ERROR : No role is assigned Or selected job is not applicable for your role."));
		 }
	}
	            

}
