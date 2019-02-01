package com.javasampleapproach.springbatch.step;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class MyStepListner implements StepExecutionListener {

	@Override
	public void beforeStep(StepExecution stepExecution) {
		System.out.println("StepExecutionListener - beforeStep");
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		System.out.println("StepExecutionListener - afterStep");
		
		System.out.println("Commit Count "+stepExecution.getCommitCount());
		System.out.println("Read Count "+stepExecution.getReadCount());
		System.out.println("Write Count "+stepExecution.getWriteCount());
		System.out.println("Summary Count "+stepExecution.getSummary());
		
		stepExecution.getJobExecution().getExecutionContext().put("Next", "Continue");
		
		return stepExecution.getExitStatus();
	}
}
