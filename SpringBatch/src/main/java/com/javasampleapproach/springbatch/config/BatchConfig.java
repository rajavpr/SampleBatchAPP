package com.javasampleapproach.springbatch.config;
 
import java.io.File;
import java.util.concurrent.Future;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
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

import com.javasampleapproach.springbatch.step.MyJobListner;
import com.javasampleapproach.springbatch.step.MyStepListner;
import com.javasampleapproach.springbatch.step.Processor;
import com.javasampleapproach.springbatch.step.User;
import com.javasampleapproach.springbatch.step.Writer;
 
//@Configuration
//@EnableBatchProcessing
public class BatchConfig {
 /*
    @Autowired
    public JobBuilderFactory jobBuilderFactory;
 
    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
	private ResourcePatternResolver resourcePatternResolver;
    
    @Autowired
    private JobRepository jobRepository;
     
    @Bean
    public Job job() throws Exception {
        return jobBuilderFactory.get("job")
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end()
                .listener(new MyJobListner())
                //.start(slaveStep())
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
    public Step step1() throws Exception {
        return stepBuilderFactory.get("step1")
               .partitioner("slaveStep", partitioner())
               .step(slaveStep()).taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public Step slaveStep() 
      throws Exception {
    	System.out.println("Building Slave Step!!!");
        return stepBuilderFactory.get("slaveStep").<User, User>chunk(10)
          .reader(read(null))
          .processor(new Processor())
          .writer(new Writer())
          .taskExecutor(taskExecutor())
          .listener(new MyStepListner())
          .build();
    }
    
    
    // Spring integeration 
    
    @Bean
    public ItemProcessor<User, User> asyncItemProcessor(){
        AsyncItemProcessor<User, User> asyncItemProcessor = new AsyncItemProcessor<>();
        asyncItemProcessor.setDelegate(new Processor());
        return asyncItemProcessor;
    }

    @Bean
    public ItemWriter<User> asyncItemWriter(){
        AsyncItemWriter<User> asyncItemWriter = new AsyncItemWriter<>();
        asyncItemWriter.setDelegate(writer());
        return asyncItemWriter;
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
    
    
    @StepScope
    @Bean
    public CustomMultiResourcePartitioner partitioner() {
        CustomMultiResourcePartitioner partitioner 
          = new CustomMultiResourcePartitioner();
  
        File file = new File("D:/files/");
        	File[] files = file.listFiles();
        	String[] fileNames = new String[files.length];
        	int i = 0;
        	for(File file1 : files) {
        		fileNames[i++] = file1.getName();
        		System.out.println("*********** " +file1.getName());
        	}
        	
        	partitioner.setFileNames(fileNames);
        	
            System.out.println("**************************************************************************************");
            
        return partitioner;
    }
    
    
   @Bean
   @StepScope
    public FlatFileItemReader<User> read( @Value("#{stepExecutionContext[fileName]}") String filename) {
        System.out.println("File Name @ reader "+filename);
   	//filename = "sample.txt";
   	FlatFileItemReader<User> reader = new FlatFileItemReader<User>();
		reader.setResource(new FileSystemResource("D:/files/"+filename));
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
       if(count < messages.length){
           return messages[count++];
       }else{
           count=0;
       }
       return null;
   	
   	return reader;
   }
*/    
}
