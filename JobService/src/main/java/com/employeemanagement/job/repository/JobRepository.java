package com.employeemanagement.job.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.employeemanagement.job.model.Job;

@Repository
public interface JobRepository extends JpaRepository<Job,Long> {
	List<Job> findByEmployeeId(Long employeeId);
}
