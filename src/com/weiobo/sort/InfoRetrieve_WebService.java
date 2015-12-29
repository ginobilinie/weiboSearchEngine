package com.weiobo.sort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import net.sf.json.JSONObject;

public class InfoRetrieve_WebService {
	private String searchWord;
	private long startTime;
	private long endTime;
	private int count;
	
	public static final String GET_URL = "http://210.77.29.159:80/web.lucene/SearchController?searchWord=";
	
	//searchWordΪ�ؼ��ʣ�startTimeΪ��ʼʱ�䣬endTimeΪ����ʱ�䣬countΪ���ظ���
	//���ؽ��ΪString���ͣ���ʽ�붨��ı�׼��ʽһ��
	public InfoRetrieve_WebService(String searchWord, long startTime, long endTime, int count){
		this.searchWord = searchWord;
		this.startTime = startTime;
		this.endTime = endTime;
		this.count = count;
	}
	
	public InfoRetrieve_WebService()
	{
	}
	
	String getWeiboInfo_String(String keyWord,long startTime,long endTime,int num) throws Exception
	{
		this.searchWord = keyWord;
		this.startTime = startTime;
		this.endTime = endTime;
		this.count = num;
		return search();
	}
	public String search() throws IOException{
		
		// ƴ��get�����URL�ִ���ʹ��URLEncoder.encode������Ͳ��ɼ��ַ����б���
		String getURL = GET_URL + URLEncoder.encode(searchWord+"&"+Long.toString(startTime)+"&"+Long.toString(endTime)+"&"+Integer.toString(count),"utf-8");
		String searchresult = "";
		URL getUrl = new URL(getURL);
		// ����ƴ�յ�URL�������ӣ�URL.openConnection���������URL�����ͣ�
        // ���ز�ͬ��URLConnection����Ķ�������URL��һ��http�����ʵ�ʷ��ص���HttpURLConnection
		HttpURLConnection connection = (HttpURLConnection)getUrl.openConnection();
		// �������ӣ�����ʵ����get requestҪ����һ���connection.getInputStream()�����вŻ���������
        // ������
        connection.connect();
        // ȡ������������ʹ��Reader��ȡ
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));//���ñ���,������������
        String str;
        while ((str = reader.readLine()) != null){
            searchresult += str;
        }
        reader.close();
        // �Ͽ�����
        connection.disconnect();
		return searchresult;
		
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
