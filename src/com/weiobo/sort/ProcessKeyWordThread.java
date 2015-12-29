package com.weiobo.sort;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/*
 * ����keyword���̣߳�������Χ����Ϣ��������
 * 
*/
public class ProcessKeyWordThread extends Thread{
	Socket socket=null;
	Map<Long,Long> clickHistory=null;
	InfoRetrieve_wl ir_wl=new InfoRetrieve_wl();
	InfoRetrieve_WebService ir_ws=new InfoRetrieve_WebService();//������������������񾭳��ң����ȸ���webService
	public ProcessKeyWordThread(Socket clientSocket)
	{
		this.socket=clientSocket;
	}
	public void run()
	{
	        try { 
	    
	
		            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
		            PrintWriter out = new PrintWriter(new BufferedWriter(
		            new OutputStreamWriter(socket.getOutputStream(),"UTF-8")), true);
		            //�õ�����time_flag��keyword
		            String str = "";
		            String time_flag = "";
		            String keyword = "";
		            String time = "";
		           // Map<Long,Long> clickHistory=null;
		            try
		            {
			            str = in.readLine();
						time_flag = str.substring(0,str.indexOf("_"));
						keyword = str.substring(str.indexOf("_")+1);
						//���ﻹҪ�Թؼ������ķִ�
						//String inString="������Է���ƻ�����𣬴��";
						String token=" ";
						List<String> words=MySegmentation.Segment(keyword);
						keyword="";
						for(int i=0;i<words.size()-1;i++)
						{
							keyword+=words.get(i)+token;
							System.out.println(words.get(i));
						}
						if (words.size()>0)
							keyword+=words.get(words.size()-1);
			            System.out.println(keyword);
			            //�˴�����ʹ��time_flag��keywordȥ��ѯ������װ�����
			    		
			    		//�˴���week��Щ��ʾ����ʱ�䣬��Ϊ������װ�������ݣ���Ҫ���ֳ�time_flag�����Ծ���������档
			    		//�鳤������Ҫд�ɸ�΢���ľ���ʱ�䡣
			            
			            Date date=new Date();
			            long endTime=date.getTime();//����
			            long startTime=-1;
			            long interval=0;
			    		switch (Integer.parseInt(time_flag))
			    		{
			    		case 3:
			    			//time = "week";
			    			interval=7*24*60*60*1000;
			    			break;
			    		case 1:
			    			//time = "1 hours";
			    			interval=1*60*60*1000;
			    			break;
			    		case 2:
			    			//time = "24 hours"; 
			    			interval=24*60*60*1000;
			    			break;
			    		default:
			    			time = "any time";
			    			break;
			    		}//switch
			    		if (interval!=0)
			    			startTime=endTime-interval;
			    		else
			    			endTime=-1;
			    		int num=200;//��ȡ��Χ������
			    		
			    		List wsa=new ArrayList(); //����ǽ����õĶ�Ӧ�ؼ��ʵ�Χ����Ϣ����
			    		try 
			    		{
			    			JSONArray  jsArray=null;
			    			//JSONObject js=ir_wl.getWeiboInfo(keyword, startTime, endTime, num);
			    			JSONObject js=ir_ws.getWeiboInfo(keyword, startTime, endTime, num);//���Ѿ��ĳɽ���ΰ��һ���webservice����
			    			if (js!=null)
				    		{
				    			jsArray=js.getJSONArray("statuses");
				    			for (int i=0;i<jsArray.size();i++)
				    			{
				    				WeiboStatus ws= parseJSONObject(jsArray.getJSONObject(i));
				    				wsa.add(ws);
				    			}
			    			}
			    		}
			    		catch (Exception e)
			    		{
			    			e.printStackTrace();
			    		}
			    		
			    		//���ڴ�����ʷ��¼��һ���֣���ÿ��΢�������������ʷ��¼��
			    		HistoryRecord hr=new HistoryRecord();
			    		clickHistory=hr.getClickHistory();//id->�����
			    		clickHistory=normlizeClickHistory(clickHistory);
			    		
			    		/******************************************************
			    		 * ����������
			    		 * ****************************************************/
			    		List sortedWeiboStatus=sortWeiboStatus(wsa);
			    		
			    		
	
						//�����Ƿ������ݵĲ���
			            for (int i = 0; i < sortedWeiboStatus.size(); i++) 
			            {
			            	WeiboStatus ws=((WeiboStatus)sortedWeiboStatus.get(i));
			    			out.println("<TEXT>");
			    			//int index = (int)(Math.random()*1000);
			    			String id=ws.id;//Χ��id
			    			out.println("<ID>"+id+"</ID>");
			    			out.println("<AUTHOR>"+ws.user.name+"</AUTHOR>");
			    			out.println("<AT>"+id+"</AT>");
			    			out.println("<TIME>"+Utility.ConvertDateTime2FullFormat(ws.created_at)+"</TIME>");
			    			out.println("<CONTENT>"+ws.text+"</CONTENT>");
			    			out.println("</TEXT>");
			    		}//for
			            //out.println(str);
			            out.flush();
			            out.close();
					} catch (RuntimeException e)//try
					{
						e.printStackTrace();
					}finally
					{
						if(socket!=null)
							try 
							{
								socket.close();
							} catch (IOException e) 
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						
					}//finally 	
		}catch (IOException e)//try
		{
				e.printStackTrace();
		} finally 
		{
				if(socket!=null)
				try 
				{
					socket.close();
				} catch (IOException e) {
						// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(socket!=null)
				try 
				{
						socket.close();
				} catch (IOException e) 
				{
						e.printStackTrace();
				}
		}				
	}
	
	WeiboStatus parseJSONObject(JSONObject js)
	{
		WeiboStatus ws=null;
		if (js!=null)
		{	ws=new WeiboStatus();
		    ws.created_at= Utility.ConvertDateTime2Long(js.getString("created_at"));//����ΰ�ĳ�������ֱ���õ�time��ʽ�����������������õ�long�����������ý���ΰ�ĳ���
		    ws.comments_count=Integer.parseInt(js.getString("comments_count"));
		    ws.favorited=js.getString("favorited");
		    //ws.geo=js.getString("geo");
		    ws.id=js.getString("id");
		    ws.in_reply_to_screen_name=js.getString("in_reply_to_screen_name");
		    ws.in_reply_to_status_id=js.getString("in_reply_to_status_id");
		    ws.in_reply_to_user_id=js.getString("in_reply_to_user_id");
		    ws.lucenescore=Double.parseDouble(js.getString("lucenescore"));//���������鶨���score���ǽ���ΰ�������ﶨ���lucencescore
		    ws.mid=js.getString("mid");
		    ws.reposts_count=Integer.parseInt(js.getString("reposts_count"));
		    //ws.source=js.getString("source");//δ�ṩ�ýӿ�
		    ws.text=js.getString("text");
		    ws.truncated=js.getString("truncated");
		    
		    JSONObject userJson=js.getJSONObject("user");
		    User user=new User();
		    user.allow_all_act_msg=userJson.getString("allow_all_act_msg");
		    user.allow_all_comment=userJson.getString("allow_all_comment");
		    //user.avatar_large=userJson.getString("avatar_large");//δ�ṩ
		    user.bi_followers_count=userJson.getString("bi_followers_count");
		    user.city=userJson.getString("city");
		    user.created_at=userJson.getString("created_at");
		    user.description=userJson.getString("description");
		    user.domain=userJson.getString("domain");
		    user.favourites_count=userJson.getString("favourites_count");
		    //user.follow_me=userJson.getString("follow_me");//δ�ṩ
		    user.followers_count=Integer.parseInt(userJson.getString("followers_count"));
		    //user.following=userJson.getString("following");//δ�ṩ
		    user.friends_count=Integer.parseInt(userJson.getString("friends_count"));
		    user.gender=userJson.getString("gender");
		    user.geo_enabled=userJson.getString("geo_enabled");
		    user.id=userJson.getString("id");
		    user.location=userJson.getString("location");
		    user.name=userJson.getString("name");
		    //user.online_status=userJson.getString("online_status");
		    //user.profile_image_url=userJson.getString("profile_image_url");
		    user.province=userJson.getString("province");
		    //user.remark=userJson.getString("remark");
		    user.screen_name=userJson.getString("screen_name");
		    user.statuses_count=userJson.getString("statuses_count");
		    user.url=userJson.getString("url");
		    user.verified=userJson.getString("verified");
		    user.verified_reason=userJson.getString("verified_reason");
		    
		    ws.user=user;
		    
		} 
		return ws;
	}
		
//����ĵ�������	
 List sortWeiboStatus(List WeiboStatusArray)
 {
	 List sortStatusArray=new ArrayList();
	 Map<String,Double> maxs=null;
	 Map<String,Double> mins=null;
	 maxs=getMaxValue(WeiboStatusArray);
	 mins=getMinValue(WeiboStatusArray);
	 //��÷�
	 for(int i=0;i<WeiboStatusArray.size();i++)
	 {
		 WeiboStatus ws= (WeiboStatus)WeiboStatusArray.get(i);
		 //��ȡ��һ��ֵ
		 double created_at=normalize("created_at",ws.created_at,maxs,mins);
		 double comments_count=normalize("comments_count",ws.comments_count,maxs,mins);
		 double lucenescore=normalize("lucenescore",ws.lucenescore,maxs,mins);
		 double followers_count=normalize("followers_count",ws.user.followers_count,maxs,mins);
		 double friends_count=normalize("friends_count",ws.user.friends_count,maxs,mins);
		 double reposts_count=normalize("reposts_count",ws.reposts_count,maxs,mins);
		 double score=0.1*created_at+0.3*comments_count+0.2*reposts_count+0.2*lucenescore+0.1*followers_count+0.1*friends_count;
		 //ws.score=score;//���¸���Χ���ĵ÷�
		
		 if(clickHistory.containsKey(ws.id))
			 ws.score=0.8*score+0.2*clickHistory.get(ws.id);
			 else
			 ws.score=0.8*score;
		 
		 WeiboStatusArray.set(i, ws);
	 }
	 //��Χ���÷ֽ�������
	 Collections.sort(WeiboStatusArray,comparator);
	 return WeiboStatusArray;
 }

 //��ȡָ�����Ե����ֵ
 Map<String,Double> getMaxValue(List wsa)
 {
	 
	 Map<String,Double> maxs=new HashMap<String,Double>();
	// double[] maxs=new double[5] ;
	 maxs.put("created_at",0.0);
	 maxs.put("comments_count", 0.0);
	 maxs.put("lucenescore",0.0);
	 maxs.put("followers_count",0.0);
	 maxs.put("friends_count",0.0);
	 maxs.put("reposts_count",0.0);
	 
	 for (int i=0;i<wsa.size();i++)
	 {
		 WeiboStatus ws=(WeiboStatus)wsa.get(i);
		 if(ws.created_at>maxs.get("created_at"))
			 maxs.put("created_at", 1.0*ws.created_at);
		 if (ws.comments_count>maxs.get("comments_count"))
			 maxs.put("comments_count", 1.0*ws.comments_count);
		 if (ws.lucenescore>maxs.get("lucenescore"))
			 maxs.put("lucenescore", 1.0*ws.lucenescore);
		 if (ws.user.followers_count >maxs.get("followers_count"))
			 maxs.put("followers_count", 1.0*ws.user.followers_count);
		 if (ws.user.friends_count >maxs.get("friends_count"))
			 maxs.put("friends_count", 1.0*ws.user.friends_count);
		 if (ws.reposts_count>maxs.get("reposts_count"))
			 maxs.put("reposts_count", 1.0*ws.reposts_count);
	 }
	 return maxs;
 }
 
 //��ȡָ�����Ե���Сֵ
 Map<String,Double> getMinValue(List wsa)
 {
	 Map<String,Double> mins=new HashMap<String,Double>();
		// double[] maxs=new double[5] ;
	 Double min=100000000000000.0;
		 mins.put("created_at",min);
		 mins.put("comments_count", min);
		 mins.put("lucenescore",min);
		 mins.put("followers_count",min);
		 mins.put("friends_count",min);
		 mins.put("reposts_count",min);
		 
		 for (int i=0;i<wsa.size();i++)
		 {
			 WeiboStatus ws=(WeiboStatus)wsa.get(i);
			 if(ws.created_at<mins.get("created_at"))
				 mins.put("created_at", 1.0*ws.created_at);
			 if (ws.comments_count<mins.get("comments_count"))
				 mins.put("comments_count", 1.0*ws.comments_count);
			 if (ws.lucenescore<mins.get("lucenescore"))
				 mins.put("lucenescore", 1.0*ws.lucenescore);
			 if (ws.user.followers_count <mins.get("followers_count"))
				 mins.put("followers_count", 1.0*ws.user.followers_count);
			 if (ws.user.friends_count <mins.get("friends_count"))
				 mins.put("friends_count", 1.0*ws.user.friends_count);
			 if (ws.reposts_count<mins.get("reposts_count"))
				 mins.put("reposts_count", 1.0*ws.reposts_count);
		 }
		 return mins;
 }
 //��һ������ֵ
 double normalize(String key, double value,Map<String,Double> maxs,Map<String,Double>mins)
 {
	 double min=mins.get(key);
	 double max=maxs.get(key);
	 double res=0;
	 if (max!=min)
		 res=(value-mins.get(key))/(max-min);
	 
	 return res;
 }
 
 
 Map<Long,Long> normlizeClickHistory(Map<Long,Long> clickHistory)
 {
	 Long min=new Long(100000000);
	 Long max=new Long(0);
	 
	 for(Long i:clickHistory.keySet())
	 {   
		 Long value=clickHistory.get(i);
		 if(min>value)
		 min=value;
		 if(max<value)
		 max=value; 
		 
	 }
	 
	 
	 for(Long i:clickHistory.keySet())
	 {   
		 Long value=clickHistory.get(i);
		 Long normValue=new Long(0);
		 if(min!=max)
		 { 
			 normValue=(value-min)/(max-min);
		 }
		 
		 clickHistory.put(i,normValue);
		 
		 
	 }
	 
	 return clickHistory;
 }
 
 
 //���÷�������
 Comparator<WeiboStatus> comparator = new Comparator<WeiboStatus>()
 {
	 public int compare(WeiboStatus w1, WeiboStatus w2)
	 {
		 int cc=0;
		 if (w1.score<w2.score)
			 cc=-1;
		 else if (w1.score>w2.score)
			 cc=1;
		 else
			 cc=0;
		 return cc;
	 }
 };
 
 

}
