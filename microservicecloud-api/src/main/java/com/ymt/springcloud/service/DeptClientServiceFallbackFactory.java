package com.ymt.springcloud.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ymt.springcloud.entities.Dept;

import feign.hystrix.FallbackFactory;

/**
 * @author Administrator
 * 
 * 此时服务端provider已经down了，但是我们做了服务降级处理，
 * 让客户端在服务端不可用时也会获得提示信息而不会挂起耗死服务器
 */
@Component//不要忘记添加，不要忘记添加
public class DeptClientServiceFallbackFactory implements FallbackFactory<Object> {
	@Override
	public DeptClientService create(Throwable throwable) {
		  
		return new DeptClientService() {
			@Override
			public Dept get(long id) {
			     
					return new Dept().setDeptno(id)
			               .setDname("该ID："+id+"没有没有对应的信息,Consumer客户端提供的降级信息,此刻服务Provider已经关闭")
			               .setDb_source("no this database in MySQL");
			}
			
			@Override
			public List<Dept> list() {
				return null;
		    }
			
			@Override
			public boolean add(Dept dept) {
				return false;
		    }
			
		};
	  }
}

