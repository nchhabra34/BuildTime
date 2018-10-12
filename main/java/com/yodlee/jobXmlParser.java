package main.java.com.yodlee;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.opencsv.CSVReader;

import javax.xml.parsers.*;

import java.io.*;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;

public class jobXmlParser {

	public void jbXmlParser(Map<String, String> jobmap) throws IOException, ParserConfigurationException, SAXException
	{
			
		String enable = null;
		SaveData sd = new SaveData();
		String description = null;
		String FILE="D:/dap1/FetchBuildTime/Resources/xml/xmldata.csv";
		ArrayList<String[]> xmldata = new ArrayList<String[]>();
		System.out.println("Entrted n jobxml parser class");
		Pattern p = Pattern.compile("(?i).*OAUTHCLIENT.*");
		Matcher m;
		boolean b=false;
		for (Entry<String, String> entry : jobmap.entrySet()) {
		    String key = entry.getKey();
		    String thing = entry.getValue();
		    m=p.matcher(key);
		    b=m.matches();
		    if (b == true){
		    ConnectionUtility cu= new ConnectionUtility();
		    
		    System.out.println(thing+"config.xml");
		 HttpURLConnection conn = cu.createConnection(thing+"config.xml");
		 
		 System.out.println(conn.getContentType());
		 conn.connect();
		 InputStream ips ;
		 ips= conn.getInputStream();
		 File file = new File("D:/dap1/FetchBuildTime/Resources/"+key+".xml");
		 URL url = new URL(thing+"config.xml");
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
	        Document doc=builder.parse(new File("D:/dap1/FetchBuildTime/Resources/"+key+".xml"));
	        doc.getDocumentElement().normalize();
	        
	      NodeList nodelist3 = doc.getElementsByTagName("spec");
	      
	     
	      Node n3 = nodelist3.item(0);
	      String timestamp;
	   
	      try{
	      timestamp=n3.getTextContent();
	      }
	      catch(Exception e)
	      {
	    	  
	    	  timestamp=null;
	      }
	     if(timestamp == null)
	     {
	    	// System.out.println("timestamp is empty");
	    	 
	    	 sd.savexmldate(key, null, null, null, xmldata);
	    	 
	     }
	     else
	     {
	    String [] cronnumber=  timestamp.split("\n");
	    
	    for (int i=0;i<cronnumber.length;i++)
	    {
	    	
	    	//System.out.println("String number-->"+i+cronnumber[i]);
	    	
	    
	  
	   CronType yahoo= CronType.CRON4J;
	  CronDefinition cronDefinition = CronDefinitionBuilder.instanceDefinitionFor(yahoo);
	  CronParser parser = new CronParser(cronDefinition);
	  CronDescriptor descriptor = CronDescriptor.instance(Locale.UK);
	  
	  
	
	  System.out.println(cronnumber[i].charAt(0));
	  if (cronnumber[i].charAt(0) ==  '#' )
	 {
		  enable="false";
		  
		  cronnumber[i]=cronnumber[i].substring(1);
		  //System.out.println(cronnumber[i]);
	 }
	  else
	  {
		  enable="true";
		  
	  }
	  Cron quartzCron = null;
	  try
	  {
	  quartzCron= parser.parse(cronnumber[i]);
	  description = descriptor.describe(parser.parse(cronnumber[i]));
	  System.out.println(description);
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
	  CronFieldName Month=CronFieldName.DAY_OF_MONTH;
	  CronFieldName year=CronFieldName.DAY_OF_YEAR;
	  
	 CronField minute= quartzCron.retrieve(Minute);
	 CronField hour = quartzCron.retrieve(Hour);
	 CronField days = quartzCron.retrieve(Days);
	String minutefiled=minute.getExpression().asString();
	String hourfiled=days.getExpression().asString();
	String monthfiled=days.getExpression().asString();
	String yearfiled=days.getExpression().asString();
	String getdays=days.getExpression().asString();
	
//	System.out.println("Get Days As-->"+getdays);
	String numOfdays [] = getdays.split(",");
	
	for(int i1=0;i1<numOfdays.length;i1++)
	{
		
		//System.out.println(numOfdays[i1]);
	}
	String daysinweek[] = new String[numOfdays.length] ;
	
		for (int i1=0;i1<numOfdays.length;i1++)
		{
			//System.out.println(numOfdays[i1]);
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
			//System.out.println(daysinweek[i1]);
			if(makedayslist == null)
			{
				makedayslist=daysinweek[i1];
			}
			else
			{
			makedayslist=makedayslist+","+daysinweek[i1];
			}
		}
		//System.out.println(minute.getExpression().asString());
		//System.out.println(hour.getExpression().asString());
		//System.out.println(days.getExpression().asString());
		//System.out.println(makedayslist);
		
		sd.savexmldate(key, cronnumber[i], description, enable,xmldata);
	
	    }
	  
	     }
	     }
	     
	        }
	       
	        catch(ParserConfigurationException e)
	        {
	        	e.printStackTrace();
	        }
	        }
		    else
		    {System.out.println("not matched");}
		}
	sd.writeDataAtOnce(FILE, xmldata);
}
}

