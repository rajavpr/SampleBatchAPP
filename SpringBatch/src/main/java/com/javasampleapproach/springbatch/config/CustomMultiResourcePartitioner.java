package com.javasampleapproach.springbatch.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

public class CustomMultiResourcePartitioner implements Partitioner {
  
private Resource[] resources = new Resource[0];
	
	private static final String DEFAULT_KEY_NAME = "fileName";

	private static final String PARTITION_KEY = "partition";
	
	private String keyName = DEFAULT_KEY_NAME;
	
	private String[] fileNames;
	
	public void setFileNames(String[] fileNames) {
		this.fileNames = fileNames;
	}

	public void setResources(Resource[] resources) {
		this.resources = resources;
	}
	
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	
	
    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
    	
    	System.out.println("****************** GRID SiZE ************** "+ gridSize);
    	
        Map<String, ExecutionContext> map = new HashMap<>(gridSize);
        int i = 0, k = 1;
       /* for (Resource resource : resources) {
            ExecutionContext context = new ExecutionContext();
            Assert.state(resource.exists(), "Resource does not exist: "
              + resource);
            context.putString(keyName, resource.getFilename());
            context.putString("opFileName", "output"+k+++".xml");
            map.put(PARTITION_KEY + i, context);
            i++;
        }*/
        
        for (String fileName : fileNames) {
            ExecutionContext context = new ExecutionContext();
            /*Assert.state(resource.exists(), "Resource does not exist: "
              + resource);*/
            context.putString(keyName, fileName);
            context.putString("opFileName", "output"+k+++".xml");
            map.put(PARTITION_KEY + i, context);
            i++;
        }
        return map;
    }

}
