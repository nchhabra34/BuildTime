package com.yodlee;

import java.awt.List;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import java.util.Map;

import org.quartz.core.QuartzScheduler;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import com.cronutils.descriptor.CronDescriptor;
import com.cronutils.model.Cron;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.field.CronField;
import com.cronutils.model.field.CronFieldName;
import com.cronutils.model.field.constraint.FieldConstraints;
import com.cronutils.model.field.definition.DayOfWeekFieldDefinition;
import com.cronutils.parser.CronParser;

import javax.xml.parsers.*;

import java.io.*;

public class jobXmlParser {

	public void jbXmlParser(Map<String, String> jobmap) throws IOException, ParserConfigurationException, SAXException
	{
		Map<String, List> cronmap;
		List cronlist;
		Boolean enable=true;
		
		/*for (Entry<String,String> entry : jobmap.entrySet()) {
		    String key = entry.getKey();
		    String thing = entry.getValue();*/
		    
		    ConnectionUtility cu= new ConnectionUtility();
		 HttpURLConnection conn = cu.createConnection("http://192.168.113.195:9090/job/NightlyProwler_OAUTHCLIENT_PSD/"+"config.xml");
		 
		 System.out.println(conn.getContentType());
		 conn.connect();
		 
		  	/*BufferedReader reader = new  BufferedReader(new InputStreamReader(conn.getInputStream()));
		  	
		  	String inputline;
		  	
		  	
		  	BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		  	
		  	FileOutputStream fis = new FileOutputStream(file);
		  	byte[] buffer = new byte[1024];
		  	int count=0;
		  	while((inputline=reader.readLine()) != null)
		  	{
		  		 System.out.println(inputline);
		  		writer.write(inputline);
		  		
		  	}
		  	reader.close();*/
		 InputStream ips ;
		 ips= conn.getInputStream();
		 File file = new File("D:/dap1/FetchBuildTime/Resources/requested.xml");
		 URL url = new URL("http://192.168.113.195:9090/job/NightlyProwler_OAUTHCLIENT_PSD/"+"config.xml");
		 BufferedInputStream bis = new BufferedInputStream(ips);
	        FileOutputStream fis = new FileOutputStream(file);
	        
	        byte[] buffer = new byte[1024];
	        int count=0;
	        while((count = bis.read(buffer,0,1024)) != -1)
	        {
	            fis.write(buffer, 0, count);
	        }
	        fis.close();
	        bis.close();
	        
	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        try{
	        DocumentBuilder builder = factory.newDocumentBuilder();
	        Document doc=builder.parse(new File("D:/dap1/FetchBuildTime/Resources/requested.xml"));
	        doc.getDocumentElement().normalize();
	        
	      NodeList nodelist3 = doc.getElementsByTagName("spec");
	      
	     
	      Node n3 = nodelist3.item(0);
	   
	      
	      String timestamp=n3.getTextContent();
	      
	     if(timestamp.isEmpty())
	     {
	    	 System.out.println("timestamp is empty");
	    	 
	     }
	     else
	     {
	    String [] s1=  timestamp.split("\n");
	    for (int i=0;i<s1.length;i++)
	    {
	    	
	    	System.out.println("String number-->"+i+s1[i]);
	    	
	    
	  
	   CronType yahoo= CronType.CRON4J;
	  CronDefinition cronDefinition = CronDefinitionBuilder.instanceDefinitionFor(yahoo);
	  CronParser parser = new CronParser(cronDefinition);
	  
	  
	
	  System.out.println(s1[i].charAt(0));
	  if (s1[i].charAt(0) ==  '#' )
	 {
		  enable=false;
		  
		  s1[i]=s1[i].substring(1);
		  System.out.println(s1[i]);
	 }
	  Cron quartzCron = null;
	  try
	  {
	  quartzCron= parser.parse(s1[i]);
	  }
	  catch(Exception e)
	  {
		  quartzCron = null;
		  
	  }
	  //quartzCron.validate();
	  if(quartzCron == null)
	  {
		  
		  System.out.println("invalid cron");
	  }
	  else
	  {
	  System.out.println(quartzCron.asString());
	 // quartzCron.
	  CronFieldName Minute= CronFieldName.MINUTE;
	  CronFieldName Hour= CronFieldName.HOUR;
	  CronFieldName Days=CronFieldName.DAY_OF_WEEK;
	

	 CronField minute= quartzCron.retrieve(Minute);
	 CronField hour = quartzCron.retrieve(Hour);
	 CronField days = quartzCron.retrieve(Days);
	System.out.println(minute.getExpression().asString());
	System.out.println(hour.getExpression().asString());
	System.out.println(days.getExpression().asString());
	String getdays=days.getExpression().asString();
	
	String numOfdays [] = getdays.split(",");
	
	for(int i1=0;i1<numOfdays.length;i1++)
	{
		
		System.out.println(numOfdays[i1]);
	}
	String daysinweek[] = new String[numOfdays.length] ;
	
		for (int i1=0;i1<numOfdays.length;i1++)
		{
			System.out.println(numOfdays[i1]);
			switch (numOfdays[i1]) {
			case "1":
				daysinweek[i1]="Monday";
				break;
			case "2":
				daysinweek[i1]="Tuesday";
				break;
			case "3":
				daysinweek[i1]="Wednesday";
				break;
			case "4":
				daysinweek[i1]="Thursday";
				break;
			case "5":
				daysinweek[i1]="Friday";
				break;
			case "6":
				daysinweek[i1]="Saturday";
				break;
			case "7":
				daysinweek[i1]="Sunday";
				break;
			default:
				break;
			}
			
			
			
		}
		String makedayslist = null;
		for (int i1=0;i1<daysinweek.length;i1++)
		{
			System.out.println(daysinweek[i1]);
			if(makedayslist == null)
			{
				makedayslist=daysinweek[i1];
			}
			else
			{
			makedayslist=makedayslist+","+daysinweek[i1];
			}
		}
		System.out.println(minute.getExpression().asString());
		System.out.println(hour.getExpression().asString());
		System.out.println(days.getExpression().asString());
		System.out.println(makedayslist);
	
	    }
	  
	     }
	     }
	     
	        }
	       
	        catch(ParserConfigurationException e)
	        {
	        	e.printStackTrace();
	        }
	        }
		
	
}

