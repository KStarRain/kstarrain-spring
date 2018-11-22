package com.kstarrain.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyElasticJob implements SimpleJob {
	private static Logger logger = LoggerFactory.getLogger(MyElasticJob.class);
	
	@Override
	public void execute(ShardingContext context) {


        System.out.println("hahaha");
        logger.info(context.getJobParameter());
    }

}
