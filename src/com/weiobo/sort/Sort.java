package com.weiobo.sort;

import java.util.List;

public class Sort {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		/*
		String inString="������Է���ƻ�����𣬴��";
		List<String> words=MySegmentation.Segment(inString);
		for(int i=0;i<words.size();i++)
		{
			System.out.println(words.get(i));
		}
		*/
		SearchInterfaceSocket pkw=new SearchInterfaceSocket();
		 pkw.ProcessKeyWord();//��ȡ�ؼ���,����ProcessKeyWordThread.java�ﴦ��ؼ���
		// TODO Auto-generated method stub

	}
	

}
