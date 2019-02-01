package com.javasampleapproach.springbatch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.javasampleapproach.springbatch.config.LoopBatchConfig;

import junit.framework.Assert;


@RunWith(SpringRunner.class)
@SpringBatchTest
@ContextConfiguration(classes=LoopBatchConfig.class)
public class SpringBatchApplicationTests {

/*	@Test
	public void contextLoads() {
	}*/
	
	
	@Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
	
	
	@Test
    public void testJob() throws Exception {
		JobExecution jobExecution = jobLauncherTestUtils.launchJob();


		Assert.assertEquals("COMPLETED", jobExecution.getExitStatus().getExitCode());
	}

}
