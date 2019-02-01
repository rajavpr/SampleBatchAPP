package com.javasampleapproach.springbatch.step;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
 
 
public class Reader  {
 
    private String[] messages = {"Hello World!", "Welcome to Spring Batch!"};
     
    private int count=0;
     
    Logger logger = LoggerFactory.getLogger(this.getClass());
     
    
    public FlatFileItemReader<User> read( @Value("#{stepExecutionContext[fileName]}") String filename) {
         System.out.println("File Name @ reader "+filename);
    	
    	FlatFileItemReader<User> reader = new FlatFileItemReader<User>();
    	reader.setResource(new ClassPathResource(filename));
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
      /*  if(count < messages.length){
            return messages[count++];
        }else{
            count=0;
        }
        return null;*/
    	
    	return reader;
    }
 
    
    public static void main(String arg[]) {
    	
    	
    	LineMapper<User> lineMapper = new DefaultLineMapper<User>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setNames(new String[] { "name", "zip", "age"});
					}
				});
				setFieldSetMapper(new BeanWrapperFieldSetMapper<User>() {
					{
						setTargetType(User.class);
					}
				});
			}
		};
		User user = null;
		try {
			user = lineMapper.mapLine("Raja,1-2,123-", 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(user);
    }
    
}