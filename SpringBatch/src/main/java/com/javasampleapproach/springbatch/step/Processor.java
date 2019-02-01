package com.javasampleapproach.springbatch.step;
 
import org.springframework.batch.item.ItemProcessor;
 
public class Processor implements ItemProcessor<User, User>{
 
    @Override
    public User process(User content) throws Exception {
        content.setName(content.getName().toUpperCase());
        System.out.println("Processor :: "+Thread.activeCount() + "               " +Thread.currentThread().getName()+"         ## I am sleeping for 0ms " + content.getName());
        Thread.sleep(1);
        return content ;
    }
 
}