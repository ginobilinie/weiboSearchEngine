package com.weiobo.sort;


import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.*;
import java.util.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;

//获取关键词的点击记录的类
public class HistoryRecord {
	public HistoryRecord()
	{
	
	}
	public String GetRecord()
	{
		String addr="http://210.77.29.159/WebMining/GiveMeLog";
		String result = "";
		HttpURLConnection url_con = null;
        try {
        	String req = new String(addr);
        	//此处的ip地址在系统布到内网后才能确定下来。目前是在我的服务器上跑着。
			URL url = new URL(req);
			url_con = (HttpURLConnection) url.openConnection();
		    
			//InputStream in = url_con.getInputStream();
			InputStream in = (InputStream)url_con.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(in));
			String line = "";
			while ((line = rd.readLine()) != null) {
			    result+=line+"\n";
			}
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
            if (url_con != null)
                url_con.disconnect();
        }
		//System.out.println(result);
		return result;
	}
	
	//
	public Map<Long,Long> parseRecord(String record) {
			
			if (record=="")
				return null;
		

			Map<Long,Long> map=new HashMap<Long,Long>();
			long lasting = System.currentTimeMillis();
			
			 //的意思是以UTF-8的编码取得字节 
		         try {
					record=new String(record.getBytes("UTF-8"),"UTF-8");
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			try {

				// File f = new File("data_10k.xml");

				DocumentBuilderFactory factory = DocumentBuilderFactory
						.newInstance();

				DocumentBuilder builder = factory.newDocumentBuilder();

				Document doc = builder.parse(new ByteArrayInputStream(record.getBytes("UTF-8")));

				NodeList nl = doc.getElementsByTagName("RESULTLIST");

				//System.out.println(nl.item(0).getFirstChild().getNodeValue());
				
				
				int k=0;
				for (int j=0;j<nl.getLength();j++){
					String str = nl.item(j).getFirstChild().getNodeValue();
					String[] str2 = str.split(";");
					Long[] test = new Long[100000];//原来的10000会报越界异常，我改成了100000
					for (int i = 0; i < str2.length; i++) {
						test[k++] = Long.parseLong(str2[i]);
						//System.out.println(test[k-1]);
						if (!map.containsKey(test[k-1])){
							map.put(test[k-1], 1l);
						}else{
							map.put(test[k-1], map.get(test[k-1])+1);
						}
					}	
				}
				
				
				
				Iterator iter = map.entrySet().iterator(); 
				int i=0;
				while (iter.hasNext()) { 
					i++;
				    Map.Entry entry = (Map.Entry) iter.next(); 
				   // System.out.println(i+" "+ entry.getKey()+" "+entry.getValue()); 
				  
				} 
				
				
			} catch (Exception e) {

				e.printStackTrace();
			}
			return map;

		}
	
	public Map<Long,Long> getClickHistory()
	{
		String record=GetRecord();
		Map<Long,Long> map=null;
		map=parseRecord(record);
		return map;
	}
	
}
