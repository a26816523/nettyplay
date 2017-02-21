package com.part.one;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;



public class TimeServer {
	
	
	
	public static void main(String[] args){
		
		int port = 9090;
		
		if(args!=null && args.length>0){
			
			try{
				port = Integer.valueOf(args[0]);
			}catch(NumberFormatException e){
			}
		}
		
		ServerSocket server = null;
		
		try{
			server = new ServerSocket(port);
			Socket socket =null;
			while(true){
				socket = server.accept();
				new Thread(new TimeServerHandler(socket)).start();
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(server !=null){
				try {
					server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				server = null;
			}
		}
		
	}
	
}
