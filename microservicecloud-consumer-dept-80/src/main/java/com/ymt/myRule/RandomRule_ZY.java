package com.ymt.myRule;

import java.util.List;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

/**
 * 新建自定义Robbin  策略
 *  策略:轮询     每个服务器要求被调用5次
 */
public class RandomRule_ZY extends AbstractLoadBalancerRule {
	
	private int total = 0;    //总共被调用的次数，目前要求每台被调用5次
	private int currentIndex = 0;//当前提供服务的机器号
	
	@Override
	public Server choose(Object key) {
		// TODO Auto-generated method stub
		return choose(getLoadBalancer(), key);
	}

	@Override
	public void initWithNiwsConfig(IClientConfig arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public Server choose(ILoadBalancer lb, Object key) {
		if (lb == null) {
			return null;
	    }
	    Server server = null;
		while (server == null) {
			if (Thread.interrupted()) {
				return null;
		    }
		    List<?> upList = lb.getReachableServers();
			List<?> allList = lb.getAllServers();
			int serverCount = allList.size();
			if (serverCount == 0) {
				return null;
		    }
			
			if(total < 5) {
		            
		            server = (Server) upList.get(currentIndex);
		            total++;
	        }else {
		            total = 0;
		            currentIndex++;
	            	if(currentIndex >= upList.size()) {
		              currentIndex = 0;
		            }
	        }
			if (server == null) {
				/*
		         * The only time this should happen is if the server list were
		         * somehow trimmed. This is a transient condition. Retry after
		         * yielding.
		         */
			   Thread.yield();
			   continue;
	        }
			if (server.isAlive()) {
				return (server);
	        }
			// Shouldn't actually happen.. but must be transient or a bug.
			server = null;
            Thread.yield();
        }
		return server;
    }


}
