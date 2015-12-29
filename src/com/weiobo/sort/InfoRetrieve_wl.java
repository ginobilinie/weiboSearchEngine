package com.weiobo.sort;

import java.io.*;
import java.net.*;
import java.util.*;

import net.sf.json.JSONObject;
import net.sf.json.util.*;

/*
 *����ʹ�õ�������ͬѧһ���ṩ��������������ڷ�������ûѡ�ã�ֻ�ܱ��ز��ԣ�������Ǳ��ز����飬����ipд����localhost 
 *�˳������гɹ���ǰ��������F:\runnable\�µ�IndexAndSearch.jar��Ҳ����ģ�Ȿ�ط�����������Ҫ����
 */

public class InfoRetrieve_wl{
	//���Ի�ȡΧ����Ϣ����
	String ip="210.77.29.159";
	String getWeiboInfo_String() throws Exception
	{
		Socket clientSocket =new Socket(ip,9090);
		BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(),"UTF-8"));
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(),"UTF-8"));
		String sentences="����&-1&-1&100";
		out.write(sentences,0,sentences.length());
		out.write("\r\n");
		out.flush();
		String result=null;
		result=in.readLine();
		System.out.println(result + "++++");
	
		System.out.println("*********");
		in.close();
		out.close();
		clientSocket.close();
		return result;
	}

	//ʵ��Χ����Ϣ��ȡ����
	String getWeiboInfo_String(String keyWord,long startTime,long endTime,int num) throws Exception
	{
		Socket clientSocket =new Socket(ip,9090);
		BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(),"UTF-8"));
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(),"UTF-8"));
	
		//String sentences="����&-1&-1&100";
		//���num�������0
		num=100;
		String sentences=keyWord+"&"+startTime+"&"+endTime+"&"+num;
		out.write(sentences,0,sentences.length());
		out.write("\r\n");
		out.flush();
		String result=null;
		result=in.readLine();
		System.out.println(result + "++++");
		System.out.println("*********");
		in.close();
		out.close();
		clientSocket.close();
		return result;
	}
	JSONObject getWeiboInfo(String keyWord,long startTime,long endTime,int num) throws Exception
	{
		String info=getWeiboInfo_String(keyWord,startTime,endTime,num);
		//info =getWeiboInfo_String();
		JSONObject js=convertStringToJson(info);
		return js;
	}
	
	JSONObject convertStringToJson(String info)
	{
		System.out.println(info);
		JSONObject js=null;
		//JSONObject jsonObj=JSONObject.fromObject(info);//������쳣��
		if (info==null|| info.compareTo("")==0)
			return js;
		//if(info!=null&&info!="")
		js=JSONObject.fromObject(info); 
		//return jsonObj;
		return js;
	}
}
