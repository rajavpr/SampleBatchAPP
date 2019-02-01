package com.javasampleapproach.springbatch.config;
 
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;

import com.javasampleapproach.springbatch.step.User;
 
@Configuration
@EnableBatchProcessing
public class LoopBatchConfig {
 
    @Autowired
    public JobBuilderFactory jobBuilderFactory;
 
    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
	private ResourcePatternResolver resourcePatternResolver;
    
    @Autowired
    private JobRepository jobRepository;
     
    @Bean
    public Job loopJob() throws Exception {
    	SampleJobExecutionDecider loopDecider = new SampleJobExecutionDecider();
    	 FlowBuilder<Flow> flowBuilder = new FlowBuilder<Flow>("loopflow1");
    	 
/*    	 Flow flow = flowBuilder.start(step1())
    			 	.next(step2())
    	            .next(loopDecider)
    	            .on("Continue")
    	            .to(step2())
    	            .from(loopDecider)
    	            .on("Fail")
    	            .end()
    	            .build();
    	 
    	 return jobBuilderFactory.get("loopJob")
         .incrementer(new RunIdIncrementer())
         .start(flow)
         .end()
         .build();
*/
    	 
    	/* return jobBuilderFactory.get("Global Job")
    	            .incrementer(new RunIdIncrementer())
    	            .flow(step1())
    	            .next(loopDecider).on("Fail").end()
    	            .next(step2())
    	            .next(loopDecider).on("Continue").to(step2())
    	            .end()
    	            .build();*/
    	 
    	/* return jobBuilderFactory.get("Global Job")
    	            .incrementer(new RunIdIncrementer())
    	            .flow(step1())
    	            .next(loopDecider).on("Continue").to(step2())
    	            .next(loopDecider).on("Fail").end()

    	            .next(step2())
    	            .next(loopDecider).on("Continue").to(step2())

    	            .end()
    	            .build();*/
    	 
    	 Flow flow = flowBuilder.start(step2a()).start(step2()).build();
    	 
    	 
    	 return jobBuilderFactory.get("GlobalJob")
 	            .incrementer(new RunIdIncrementer())
 	            .flow(step1())
 	            .next(loopDecider).on("Continue").to(step1a()).next(step2()).next(step2a())
 	            .next(loopDecider).on("Fail").end()
 	            .next(step1a()).next(step2()).next(step2a())
 	            .next(loopDecider).on("Continue").to(step1a()).next(step2()).next(step2a())

 	            .end()
 	            .build();

    	
    }
 
    @Bean
    public JobLauncher asyncJobLauncher() {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
        return jobLauncher;
    }

    @Bean
    public Step step1() 
      throws Exception {
    	System.out.println("Building Slave Step!!!");
        return stepBuilderFactory.get("step1").tasklet(new Tasklet() {

			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				
				List<String> tiggerFiles = new ArrayList<>();
				tiggerFiles.add("sample.txt");
				tiggerFiles.add("sample1.txt");
				tiggerFiles.add("sample2.txt");
				/*tiggerFiles.add("sample3.txt");
				tiggerFiles.add("sample4.txt");*/
				chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("triggerFiles", tiggerFiles);
				//chunkContext.getStepContext().getJobExecutionContext().put("triggerFiles", "Files1.txt,Files2.txt");
				return RepeatStatus.FINISHED;
			}
        }).listener(new StepExecutionListener() {

			@Override
			public void beforeStep(StepExecution stepExecution) {
				// Do Nothing
			}

			@Override
			public ExitStatus afterStep(StepExecution stepExecution) {
				
				stepExecution.getJobExecution().getExecutionContext().put("next", "Continue");
				return 	ExitStatus.COMPLETED;
			}
			}).build();
    }
    
    
    @Bean
    public Tasklet tasklet1() 
      throws Exception {
    	System.out.println("Building Tasklet1A!!!");
        return new Tasklet() {

			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

				System.out.println("Tasklet1A !!!!");
				
				return RepeatStatus.FINISHED;
			}
        };
    } 
    
    @Bean
    public Tasklet tasklet2() 
      throws Exception {
    	System.out.println("Building Tasklet2A!!!");
        return new Tasklet() {

			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

				System.out.println("Tasklet2A !!!!");
				
				return RepeatStatus.FINISHED;
			}
        };
    } 
    @Bean
    public Step step1a() 
      throws Exception {
    	System.out.println("Building Slave Step!!!");
        return stepBuilderFactory.get("step1a").tasklet(tasklet1())
          .build();
    }

    
    @Bean
    public Step step2a() 
      throws Exception {
    	System.out.println("Building Slave Step!!!");
        return stepBuilderFactory.get("step2a").tasklet(tasklet2())
          .build();
    }

    
    @Bean
    public Step step2() 
      throws Exception {
    	System.out.println("Building Slave Step!!!");
        return stepBuilderFactory.get("loopstep1").<User, User>chunk(10)
          .reader(read(null))
          .processor(processor())
          //.writer(writer())
          //.taskExecutor(taskExecutor())
          //.listener(new MyStepListner())
          .build();
    }
    
  
    @Bean
    public TaskExecutor taskExecutor() {
    	SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
    	taskExecutor.setConcurrencyLimit(10);
    	return taskExecutor;
    }
    
    @Bean
	public DataSource dataSource() {
		
		// no need shutdown, EmbeddedDatabaseFactoryBean will take care of this
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		EmbeddedDatabase db = builder
			.setType(EmbeddedDatabaseType.HSQL) //.H2 or .DERBY
			.build();
		return db;
	}
    
	@Bean
	@StepScope
	public ItemProcessor<User, User> processor() {
		return new ItemProcessor<User, User>() {

			@Override
			public User process(User content) throws Exception {
				content.setName(content.getName().toUpperCase());
				System.out.println("Processor :: " + Thread.activeCount() + "               "
						+ Thread.currentThread().getName() + "         ## I am sleeping for 0ms " + content.getName());
				Thread.sleep(1);
				return content;
			}
		};
	}
   @Bean
   @StepScope
    public FlatFileItemReader<User> read( @Value("#{jobExecutionContext[processingFile]}") String fileName) {
        System.out.println("File Name @ reader "+fileName);
    	FlatFileItemReader<User> reader = new FlatFileItemReader<User>();
		reader.setResource(new FileSystemResource("D:/files/"+fileName));
   	reader.setLineMapper(new DefaultLineMapper<User>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setNames(new String[] { "name"});
					}
				});
				setFieldSetMapper(new BeanWrapperFieldSetMapper<User>() {
					{
						setTargetType(User.class);
					}
				});
			}
		});
      
        System.out.println("inside the reader");
   	
   	return reader;
   }
    
}
