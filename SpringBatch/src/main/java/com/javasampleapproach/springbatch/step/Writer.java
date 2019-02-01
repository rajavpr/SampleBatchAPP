package com.javasampleapproach.springbatch.step;
 
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
 
public class Writer implements ItemWriter<User> {
 
    Logger logger = LoggerFactory.getLogger(this.getClass());
     
    @Override
    public void write(List<? extends User> messages) throws Exception {
        for(User user : messages){
            System.out.println(new Date() + "           "+Thread.currentThread().getName()+":     #Writer Step:         " + user.getName());
        }
    }
     
}