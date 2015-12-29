package com.weiobo.sort;

import java.util.List;

public class Sort {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		/*
		String inString="今天你吃饭和苹果了吗，大哥";
		List<String> words=MySegmentation.Segment(inString);
		for(int i=0;i<words.size();i++)
		{
			System.out.println(words.get(i));
		}
		*/
		SearchInterfaceSocket pkw=new SearchInterfaceSocket();
		 pkw.ProcessKeyWord();//获取关键词,并在ProcessKeyWordThread.java里处理关键词
		// TODO Auto-generated method stub

	}
	

}
