package com.javasampleapproach.springbatch.config;

import java.util.List;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.util.CollectionUtils;


public class SampleJobExecutionDecider implements  JobExecutionDecider {

	private static int iterationCount = 0;
	
	@Override
	public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
		//String step1Message = stepExecution.getJobExecution().getExecutionContext().getString("next");
		List<String> triggerFiles =(List<String>) stepExecution.getJobExecution().getExecutionContext().get("triggerFiles");
		
		
		/*if("Continue".equalsIgnoreCase(stepExecution.getJobExecution().getExecutionContext().getString("next"))) {
			
		}*/
		if(!CollectionUtils.isEmpty(triggerFiles)) {
		 stepExecution.getJobExecution().getExecutionContext().put("processingFile", triggerFiles.get(0));
		 triggerFiles.remove(0);
		 return new FlowExecutionStatus("Continue");
		}else {
			return new FlowExecutionStatus("Fail");	
		}
	}

}
