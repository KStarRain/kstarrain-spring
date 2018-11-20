package com.kstarrain.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyElasticJob implements SimpleJob {
	private static Logger logger = LoggerFactory.getLogger(MyElasticJob.class);
	
	@Override
	public void execute(ShardingContext context) {
		System.out.println("hahaha"+System.currentTimeMillis());
		// TODO Auto-generated method stub
		switch (context.getShardingItem()) {
	        case 0: 
	        	logger.info("-- UserElasticJob execute context ShardingItem = " + context.getShardingItem()
	        			+ " | ShardingParameter = " + context.getShardingParameter());
	            break;
	        case 1: 
	        	logger.info("-- UserElasticJob execute context ShardingItem = " + context.getShardingItem()
	        			+ " | ShardingParameter = " + context.getShardingParameter());
	            break;
	        case 2: 
	        	logger.info("-- UserElasticJob execute context ShardingItem= " + context.getShardingItem()
	        			+ " | ShardingParameter = " + context.getShardingParameter());
	            break;
	        // case n: ...
	    }
	}

}
