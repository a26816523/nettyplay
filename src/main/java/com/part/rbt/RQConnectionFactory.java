package com.part.rbt;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Address;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RQConnectionFactory{
	
	public static Connection getConnection(String mqType, RQBaseInfo ri, int i)
			throws IOException, TimeoutException  {
		
	 	ConnectionFactory factory = new ConnectionFactory(); 
		factory.setVirtualHost(ri.getVirtualHost(mqType,i));
		factory.setUsername(ri.getUName(mqType,i));
		factory.setPassword(ri.getPwd(mqType,i));
		factory.setAutomaticRecoveryEnabled(false);
		factory.setNetworkRecoveryInterval(10);
		// 在使用自动重新连接的属性时 配置心跳会出现资源不能完全回收的问题
		//factory.setRequestedHeartbeat(10);

		String hostNameStr = ri.getHostName(mqType,i);
		String[] hostNames = hostNameStr.split(",");
		
		String portStr = ri.getPort(mqType,i);
		String[] ports = portStr.split(",");
		
		Address[] address = new Address[hostNames.length];
		for(int j = 0 ;j<hostNames.length;j++){
			address[j] = new Address(hostNames[j],Integer.parseInt(ports[j]));
		}
		
		return factory.newConnection(address);
		
	}
}
