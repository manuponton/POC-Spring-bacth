package com.example.springbacth.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  @Autowired
  private JobLauncher jobLauncher;

  @Autowired
  private Job job;

  @GetMapping("/start")
  public String sendBacth()
      throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
    Map<String, JobParameter> maps = new HashMap<String, JobParameter>();
    maps.put("timestamp", new JobParameter(System.currentTimeMillis()));
    JobParameters jopParameters = new JobParameters(maps);
    JobExecution jobExecution = jobLauncher.run(job, jopParameters);
    return "Spring batch job" + jobExecution.getStatus();
  }
}
