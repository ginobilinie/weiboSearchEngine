package com.weiobo.sort;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utility {
	 public static long ConvertFullFormart2DateTime(String time)
	 {
		 DateFormat fullFormat = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH); 
		 Date date =null;
		 try 
		 {
			 date  = fullFormat.parse(time);
		 }
		 catch (Exception e)
		 {
			e.printStackTrace(); 
		 }
	     
		 long dd=0;
		 if (date!=null)
			 dd = date.getTime(); 
	     return dd;
	 }
	 
	 public static String ConvertDateTime2FullFormat(long time)
	 {
		 Date date=new Date(time);
		 /*
		 DateFormat fullFormat = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH); 
		 Date date =null;
		 try 
		 {
			 date  = fullFormat.parse(time);
		 }
		 catch (Exception e)
		 {
			e.printStackTrace(); 
		 }
	     
		 long dd=0;
		 if (date!=null)
			 dd = date.getTime(); 
	     return dd;
	     */
		 return date.toLocaleString();
	 }
	 public static long ConvertDateTime2Long(String time)
	 {
		return new Date(time).getTime();
	 }
}
