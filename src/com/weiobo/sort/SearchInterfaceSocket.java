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
 * ����������齻���ĳ���
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
	        	// ����,ֱ���пͻ�������
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
