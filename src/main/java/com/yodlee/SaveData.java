package src.main.java.com.yodlee;

import java.awt.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.opencsv.CSVWriter;

public class SaveData {
	
	
	
	//FileWriter outputfile = new FileWriter(FILE); 
	//List<String[]> data = new ArrayList<String[]>();
	//ArrayList<String[]> xmldata = new ArrayList<String[]>();
	
	void savexmldate(String jobname,String cronnumber,String crondescription,String enable, ArrayList<String[]> writerlist)
	{
		
		System.out.println(jobname+"-->"+cronnumber+"-->"+crondescription+"-->"+enable);
		
		
		writerlist.add(new String[] { jobname, cronnumber, crondescription,enable }); 
       
		
	
		
	}
	
	void savejsondate(String jobName, String url, String estimatedDuration ,String lastresult , String timestamp ,String buildable ,String labelExpression, ArrayList<String[]> jsondata)
	{
		
		System.out.println(jobName+"-->"+url+"-->"+estimatedDuration+"-->"+estimatedDuration + timestamp + labelExpression);
		
		
        jsondata.add(new String[] { jobName, url, estimatedDuration,lastresult,timestamp,labelExpression,buildable }); 
       
		
	
		
	}
	
	void writeDataAtOnce(String FILE , ArrayList<String[]> writerobject) 
	{ 
	  
	    // first create file object for file placed at location 
	    // specified by filepath 
	    File file = new File(FILE); 
	  
	    try { 
	        // create FileWriter object with file as parameter 
	        FileWriter outputfile = new FileWriter(file); 
	  
	        // create CSVWriter object filewriter object as parameter 
	        CSVWriter writer = new CSVWriter(outputfile); 
	  
	        // create a List which contains String array 
	        
	        writer.writeAll(writerobject); 
	  
	        // closing writer connection 
	        writer.close(); 
	    } 
	    catch (IOException e) { 
	        // TODO Auto-generated catch block 
	        e.printStackTrace(); 
	    } 
	} 

}
