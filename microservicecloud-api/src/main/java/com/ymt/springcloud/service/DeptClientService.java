package com.ymt.springcloud.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ymt.springcloud.entities.Dept;



/**
 * @author Administrator
 *	 
 *  服务端provider已经down了，执行fallbackFactory 后的方法;
 *  我们做了服务降级处理，让客户端在服务端不可用时也会获得提示信息而不会挂起耗死服务器
 * 
 */
@FeignClient(value = "MICROSERVICECLOUD-DEPT",fallbackFactory=DeptClientServiceFallbackFactory.class)
public interface DeptClientService {
	@RequestMapping(value = "/dept/get/{id}",method = RequestMethod.GET)
	public Dept get(@PathVariable("id") long id);
	@RequestMapping(value = "/dept/list",method = RequestMethod.GET)
	public List<Dept> list();
	@RequestMapping(value = "/dept/add",method = RequestMethod.POST)
	public boolean add(Dept dept);
}
