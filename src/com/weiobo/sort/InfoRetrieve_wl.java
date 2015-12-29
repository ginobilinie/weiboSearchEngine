package com.weiobo.sort;

import java.io.*;
import java.net.*;
import java.util.*;

import net.sf.json.JSONObject;
import net.sf.json.util.*;

/*
 *现在使用的是王龙同学一组提供的索引组程序，由于服务器还没选好，只能本地测试，这里就是本地测试组，所以ip写的是localhost 
 *此程序运行成功的前提是运行F:\runnable\下的IndexAndSearch.jar，也就是模拟本地服务器程序先要运行
 */

public class InfoRetrieve_wl{
	//测试获取围脖信息函数
	String ip="210.77.29.159";
	String getWeiboInfo_String() throws Exception
	{
		Socket clientSocket =new Socket(ip,9090);
		BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(),"UTF-8"));
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(),"UTF-8"));
		String sentences="世界&-1&-1&100";
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

	//实用围脖信息获取函数
	String getWeiboInfo_String(String keyWord,long startTime,long endTime,int num) throws Exception
	{
		Socket clientSocket =new Socket(ip,9090);
		BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(),"UTF-8"));
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(),"UTF-8"));
	
		//String sentences="世界&-1&-1&100";
		//这个num必须大于0
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
		//JSONObject jsonObj=JSONObject.fromObject(info);//这里出异常了
		if (info==null|| info.compareTo("")==0)
			return js;
		//if(info!=null&&info!="")
		js=JSONObject.fromObject(info); 
		//return jsonObj;
		return js;
	}
}
