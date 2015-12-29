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
	
	//searchWord为关键词，startTime为起始时间，endTime为结束时间，count为返回个数
	//返回结果为String类型，格式与定义的标准格式一致
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
		
		// 拼凑get请求的URL字串，使用URLEncoder.encode对特殊和不可见字符进行编码
		String getURL = GET_URL + URLEncoder.encode(searchWord+"&"+Long.toString(startTime)+"&"+Long.toString(endTime)+"&"+Integer.toString(count),"utf-8");
		String searchresult = "";
		URL getUrl = new URL(getURL);
		// 根据拼凑的URL，打开连接，URL.openConnection函数会根据URL的类型，
        // 返回不同的URLConnection子类的对象，这里URL是一个http，因此实际返回的是HttpURLConnection
		HttpURLConnection connection = (HttpURLConnection)getUrl.openConnection();
		// 进行连接，但是实际上get request要在下一句的connection.getInputStream()函数中才会真正发到
        // 服务器
        connection.connect();
        // 取得输入流，并使用Reader读取
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));//锟斤拷锟矫憋拷锟斤拷,锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
        String str;
        while ((str = reader.readLine()) != null){
            searchresult += str;
        }
        reader.close();
        // 断开连接
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
		//JSONObject jsonObj=JSONObject.fromObject(info);//这里出异常了
		if (info==null|| info.compareTo("")==0)
			return js;
		//if(info!=null&&info!="")
		js=JSONObject.fromObject(info); 
		//return jsonObj;
		return js;
	}
}
