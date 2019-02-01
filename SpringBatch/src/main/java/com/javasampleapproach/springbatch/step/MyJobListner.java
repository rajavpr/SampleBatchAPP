package com.javasampleapproach.springbatch.step;

import java.util.List;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.StepExecution;

public class MyJobListner implements JobExecutionListener {

	@Override
	public void beforeJob(JobExecution jobExecution) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		List<StepExecution> stepExecutions =(List) jobExecution.getStepExecutions();
		System.out.println(stepExecutions.size());
		for(StepExecution stepExecution : stepExecutions) {
			if(stepExecution.getExecutionContext().get("fileName") != null) {
			System.out.println(" ********************************************* ");
			
			System.out.println("File Name "+stepExecution.getExecutionContext().get("fileName"));
			System.out.println("Step ID "+stepExecution.getId());
			System.out.println("Step Name "+stepExecution.getStepName());
			System.out.println("Commit Count "+stepExecution.getCommitCount());
			System.out.println("Read Count "+stepExecution.getReadCount());
			System.out.println("Write Count "+stepExecution.getWriteCount());
			System.out.println("Summary Count "+stepExecution.getSummary());
			System.out.println("Execution Time "+(stepExecution.getEndTime().getTime()-stepExecution.getStartTime().getTime()));
			System.out.println(" ********************************************* ");
			}
		}
	}

}
