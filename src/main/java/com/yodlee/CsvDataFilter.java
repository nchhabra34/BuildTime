package src.main.java.com.yodlee;

import java.util.ArrayList;

public class CsvDataFilter {

	Boolean filterNightlybuild(String[] temp)
	{
		String temp1=temp[0]; 
		System.out.println(temp1);
		if(temp1.toLowerCase().contains("tinydb"))
		{
			
			return false;
		}
		else
			return true;
		
	
		
		
	}
	
}
