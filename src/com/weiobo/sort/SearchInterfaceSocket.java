package com.weiobo.sort;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * 处理与界面组交互的程序
 */
public class SearchInterfaceSocket {
	public void ProcessKeyWord()
	{
		ServerSocket s = null;
		Socket socket = null;
		try
		{
			s = new ServerSocket(10086);
        	while(true)
        	{
	        	// 阻塞,直到有客户端连接
	        	socket = s.accept();
	        	Thread processkeyword=new ProcessKeyWordThread(socket);
	        	processkeyword.start();
	        }  
		}catch(Exception e)
		{
			e.printStackTrace();
		}
        	
       
	}
}
