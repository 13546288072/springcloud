package com.ymt.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

import com.ymt.myRule.MySelfRule;

@SpringBootApplication
@EnableEurekaClient     //本服务启动后会自动注册进eureka服务中
// @RibbonClient  在启动该微服务的时候就能去加载我们的自定义Ribbon配置类，从而使配置生效，形如：
@RibbonClient(name="MICROSERVICECLOUD-DEPT",configuration=MySelfRule.class)
public class DeptConsumer80_App {

	public static void main(String[] args) {
		SpringApplication.run(DeptConsumer80_App.class, args);
	}
	
}
