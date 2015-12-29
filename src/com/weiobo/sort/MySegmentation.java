package com.weiobo.sort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.wltea.analyzer.*;

public class MySegmentation {
	
	public MySegmentation(){}
	
	public static List<String> Segment(String inString)
	{
		BufferedReader br = new BufferedReader(new StringReader(inString));
		IKSegmentation seg=new IKSegmentation(br, true);
		
		List<String> result=new ArrayList<String>();
		Lexeme lexeme;
		try {
			while((lexeme=seg.next())!= null)
			{
				result.add(lexeme.getLexemeText());
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
		
	}

}
