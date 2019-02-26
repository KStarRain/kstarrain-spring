package com.kstarrain.elasticjob.autoconfigure;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.kstarrain.elasticjob.autoconfigure.properties.ZookeeperProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 任务自动配置
 */
@Configuration
//@EnableConfigurationProperties(ZookeeperProperties.class)
public class ZookeeperAutoConfig {

	@Autowired
	private ZookeeperProperties zookeeperProperties;

	/**
	 * 初始化Zookeeper注册中心
	 * @return
	 */
	@Bean(initMethod = "init")
    public ZookeeperRegistryCenter zookeeperRegistryCenter() {
		ZookeeperConfiguration zkConfig = new ZookeeperConfiguration(zookeeperProperties.getAddresses(), zookeeperProperties.getNamespace());
		zkConfig.setBaseSleepTimeMilliseconds(zookeeperProperties.getBaseSleepTimeMilliseconds());
		zkConfig.setConnectionTimeoutMilliseconds(zookeeperProperties.getConnectionTimeoutMilliseconds());
		zkConfig.setDigest(zookeeperProperties.getDigest());
		zkConfig.setMaxRetries(zookeeperProperties.getMaxRetries());
		zkConfig.setMaxSleepTimeMilliseconds(zookeeperProperties.getMaxSleepTimeMilliseconds());
		zkConfig.setSessionTimeoutMilliseconds(zookeeperProperties.getSessionTimeoutMilliseconds());
		return new ZookeeperRegistryCenter(zkConfig);
    }


}
