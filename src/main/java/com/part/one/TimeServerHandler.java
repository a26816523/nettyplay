package com.part.one;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

public class TimeServerHandler implements Runnable{
	
	private Socket socket = null;
	
	public TimeServerHandler(Socket socket){
		this.socket = socket;
	}
	

	public void run() {
		BufferedReader in = null;
		PrintWriter out = null;
		
		try{
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(),true);
			String currentTime = null;
			String body = null;
			while(true){
				body = in.readLine();
				
				if(body ==null){
					break;
				}
				
				currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) 
						? new Date(System.currentTimeMillis()).toString():"Bad ORDER";
				out.print(currentTime);
			}
			
			
		}catch(Exception e){
			if(in!=null){	
				try {
					in.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
			if(out !=null){
				out.close();
				out = null;
			}
			
			if(socket!=null){
				try {
					socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				socket = null;
			}
			
		}
		
		
	}

}
