package com.part.rbt;

public class RQBaseInfo {
	

	protected String getHostName(String mqType,int i){
		return "192.168.1.247";
	}
	
	protected String getPort(String mqType,int i){
		return "5672";
	}
	protected String getUName(String mqType,int i){	
		return "cms";
		
		
	}
	protected String getPwd(String mqType,int i){
		return "cms123";
	}
	
	protected String getVirtualHost(String mqType,int i){
		return "testzx";
	}
}
