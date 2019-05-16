package main.java.com.yodlee;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.cronutils.descriptor.CronDescriptor;
import com.cronutils.model.Cron;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.field.CronField;
import com.cronutils.model.field.CronFieldName;
import com.cronutils.parser.CronParser;

public class TestJsonObject {

	public static void main(String[] args) throws IOException, SAXException {
		ListJobs lj = new ListJobs();
		String output=lj.fetchJoblist("http://192.168.113.195:9090/api/json?pretty=true");
		Map<String, String> jobMap= lj.parseJsondata(output);
		List<JSONObject> jsonobjectlist = new ArrayList<JSONObject>();
		String jobdata=null;
		
		List<Jsondata> jsonclassdata=new ArrayList<>();
		for (Entry<String, String> entry : jobMap.entrySet()) {
		    String key = entry.getKey();
		    String thing = entry.getValue();
		    ConnectionUtility cu= new ConnectionUtility();
		    List<String> crondescription=new ArrayList<>();
		 HttpURLConnection conn = cu.createConnection(thing+"config.xml");
		 
		 //System.out.println(conn.getContentType());
		 conn.connect();
		 InputStream ips ;
		 ips= conn.getInputStream();
		 File file = new File("D:/dap1/FetchBuildTime/Resources/xml/"+key+".xml");
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
	        
		String description = null;
		new ArrayList<String[]>();
	        
	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        try{
	        DocumentBuilder builder = factory.newDocumentBuilder();
	        Document doc=builder.parse(new File("D:/dap1/FetchBuildTime/Resources/xml/"+key+".xml"));
	        doc.getDocumentElement().normalize();
	        
	      NodeList nodelist3 = doc.getElementsByTagName("spec");
	      
	     
	      Node n3 = nodelist3.item(0);
	      String timestamp;
	   
	      try{
	      timestamp=n3.getTextContent();
	      System.out.println(timestamp);
	      }
	      catch(Exception e)
	      {
	    	  
	    	  timestamp=null;
	      }
	     if(timestamp == null)
	     {
	    	//System.out.println("timestamp is empty");
	    	 
	    	
	    	 
	     }
	     else
	     {
	    String [] cronnumber=  timestamp.split("\n");
	    Jsondata jd = new Jsondata();
	    for (int i=0;i<cronnumber.length;i++)
	    {
	   CronType yahoo= CronType.CRON4J;
	  CronDefinition cronDefinition = CronDefinitionBuilder.instanceDefinitionFor(yahoo);
	  CronParser parser = new CronParser(cronDefinition);
	  CronDescriptor descriptor = CronDescriptor.instance(Locale.UK);
	  
	  try{
	  if (cronnumber[i].charAt(0) ==  '#' )
	 {
		  cronnumber[i]=cronnumber[i].substring(1);
		 
	 }
	  else
	  {
		  
	  }
	  }
	  catch(Exception e)
	  {
		  cronnumber[i].isEmpty();
		  
	  }
	  Cron quartzCron = null;
	  try
	  {
	  quartzCron= parser.parse(cronnumber[i]);
	  description = descriptor.describe(parser.parse(cronnumber[i]));
	  crondescription.add(description);
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
	System.out.println(getdays);
	
	    }
	  	
	  //System.out.println("final json array length-->"+crondescription.size());
	  jd.setJobName(key);
	  jd.setCrondescription(crondescription);
		
	  
	// jobFilter(key,jd,thing);
	  
	 fetchJobData(thing,jd);
		String jsonString = new JSONObject()
        .put("JobName",jd.getJobName())
        .put("Buildable",jd.getBuildable())
        .put("server",jd.getLabelExpression())
        .put("lastbuild", jd.getLastbuilnumber())
        .put("CronDescription", jd.getCrondescription()).toString();
		
		System.out.println(jsonString);
		 
	     }
	    jsonclassdata.add(jd);
	     }
	     
	    
	        }
	       
	       
	        catch(ParserConfigurationException e)
	        {
	        	e.printStackTrace();
	        }
	        
	
	        
	}
		Jsonobjectbuilder jsonob = new Jsonobjectbuilder();
		 jsonob.objectcreation(jsonclassdata);
		
		}
	
	// Adding the job filter to  get downstream from nightly prowler jobs
	/*static Jsondata jobFilter(String key,Jsondata jd, String thing) throws JSONException, IOException
	{
		
		String nightregex="(?i)^(nightlyprowler_.*_*)";
		
		Pattern nightp= Pattern.compile(nightregex);
		Matcher cim ;
		Matcher nightm;
		
			
			nightm=nightp.matcher(key);
			if(nightm.matches())
			{
				
				ConnectionUtility cu= new ConnectionUtility();
				  
			    
				   HttpURLConnection conn = cu.createConnection(thing+"api/json?pretty=true");
				    
				   InputStream ips;
					
					BufferedReader buf ;

					StringBuilder sb = new StringBuilder();
				    String s;
				    ips=  conn.getInputStream();
					
				    buf = new BufferedReader(new InputStreamReader(ips));

			        
			       
			       
			        while ((s=buf.readLine()) != null) {
			        	
			            
			          
			           sb.append(s+'\r');
			          
			        		
			        }   
			        buf.close();
			       String jobdata=sb.toString();
			       
			       
			}
			else
			{
				fetchJobData(key,jd);
				
			}
		
		return jd;
	}*/
	public static String fetchJobData(String thing, Jsondata jd) throws IOException, JSONException
	{
	    ConnectionUtility cu= new ConnectionUtility();
	  
	    
	   HttpURLConnection conn = cu.createConnection(thing+"api/json?pretty=true");
	    
	   InputStream ips;
		
		BufferedReader buf ;

		StringBuilder sb = new StringBuilder();
	    String s;
	    ips=  conn.getInputStream();
		
	    buf = new BufferedReader(new InputStreamReader(ips));

        
       
       
        while ((s=buf.readLine()) != null) {
        	
            
          
           sb.append(s+'\r');
          
        		
        }   
        buf.close();
       String jobdata=sb.toString();
     
        parseJobData(jobdata,jd);  
        
        return jobdata;
	    
        
	}
	
		
	static Jsondata parseJobData(String jobdata, Jsondata jd) throws JSONException
	{
		JSONObject jsonObj = new JSONObject(jobdata);
		 JSONObject jlastbuildnumber = new JSONObject(jsonObj.get("lastBuild"));
		
		 
		 System.out.println(jsonObj.get("url"));
		 
		 int lastbuilnumber; 
		 String jobName;
		 String labelExpression = null;
		 boolean buildableboolean;
		 String buildable;
		 String url=jsonObj.get("url").toString();
		 jobName=jsonObj.get("name").toString();
		 // Condtion to check if job name has nightlyprowler so we can take downstream of that job to get server and sucess failure
		
		 
			 if ( (jsonObj.isNull("lastBuild"))) {
			 
			 //System.out.println("Last build number found is null");
			 lastbuilnumber=0;
			 jobName=jsonObj.getString("name");
			
			 try{
			 labelExpression=jsonObj.getString("labelExpression");
			 }
			 catch(Exception e)
			 {
				// System.out.println("not found");
			 }
			 buildableboolean=jsonObj.getBoolean("buildable");
			 }
			 else
			 {
			 System.out.println("calling else ");
			 jlastbuildnumber=(JSONObject) jsonObj.get("lastBuild");
			 lastbuilnumber=(int) jlastbuildnumber.get("number");
			 jobName=jsonObj.getString("name");
			
			 buildableboolean=jsonObj.getBoolean("buildable");
			 
			 url=jsonObj.get("url").toString();
			 labelExpression=jsonObj.getString("labelExpression");
			 			 }
		 if(buildableboolean == true)
		 {
			 
			 buildable="true";
		 }
		 else
		 {
			 buildable="false";
		 }
		 
		 fetchJobLastBuildData(lastbuilnumber,jobName,buildable,url,labelExpression,jd);
		 jd.setBuildableboolean(buildableboolean);
		 jd.setLastbuilnumber(lastbuilnumber);
		 jd.setLabelExpression(labelExpression);
		 
		return jd;
		 
		

	
		
}
	
	static Jsondata fetchJobLastBuildData(int lastbuilnumber, String jobName,  String buildable, String url, String labelExpression, Jsondata jd) throws JSONException
	{
	 	// We have extracted the JOb URl and last build number. Now we will use this to create the URl and get the other information.	
	 		// We will fetch the data for each run.// We have extracted the JOb URl and last build number. Now we will use this to create the URl and get the other information.	
	 		
	 		
	 		
	 		String lastresult = null;
			 String timestamp = null;
			 String duration;
			 String estimatedDuration = null;
			
			
			 //Creating the connection to URl
			ConnectionUtility cu = new ConnectionUtility();
			HttpURLConnection connection = null;
				String output2=null;
					System.out.println(url);
					if(lastbuilnumber == 0)
					{
					System.out.println("No build has ran");
					lastresult=null;
					
					}
					else
					{
					try{
						url=url+lastbuilnumber;
					 connection= cu.createConnection(url+"/api/json?pretty=true");
					 int code = connection.getResponseCode();
						InputStream ips=  connection.getInputStream();
						
						BufferedReader buf = new BufferedReader(new InputStreamReader(ips));
						StringBuilder sb = new StringBuilder();
				        String s;
				        
				        while ((s=buf.readLine()) != null) {
				        	
				            
				           
				           sb.append(s+'\n');
				           
				        		
				        }   
				        buf.close();
				        output2=sb.toString();
				        JSONObject jjobdata = new JSONObject(output2);
				        System.out.println(jjobdata.toString());
						duration=jjobdata.get("duration").toString();
						estimatedDuration=jjobdata.get("estimatedDuration").toString();
					lastresult=jjobdata.get("result").toString();
						String tempstamp = jjobdata.get("timestamp").toString();
						timestamp=timeStampConverter(tempstamp);
					
						} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
					
					jd.setTimestamp(timestamp);
					jd.setLastresult(lastresult);
					jd.setBuildable(buildable);
					jd.setLabelExpression(labelExpression);
					jd.setTimestamp(timestamp);
					jd.setEstimatedDuration(estimatedDuration);
					jd.setUrl(url);
					
					
					return jd;
					
	 	}

	
	 static String timeStampConverter (String temptimestamp)
	 {
	 	
	 	
	 	long millis=Long.parseLong(temptimestamp);
	
	 	Date epoc= new Date(millis);
	 	DateFormat df2 = DateFormat.getDateInstance(DateFormat.MEDIUM);
	 	String s2 = df2.format(epoc);
	 	System.out.println(s2);
	 	return s2;
	 	
	 }

	}


