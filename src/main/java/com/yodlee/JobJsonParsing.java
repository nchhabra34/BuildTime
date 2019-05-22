package src.main.java.com.yodlee;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;

import javax.swing.JButton;
import javax.swing.text.html.HTMLDocument.Iterator;

import jdk.nashorn.internal.parser.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class JobJsonParsing {
		
	    String jobdata = null;
	    //List<SaveData> jobDataList= null;
	    String FILE="D:/dap1/FetchBuildTime/Resources/json/jsondata.csv";
	    SaveData sd = new SaveData();
	    ArrayList<String[]> jsondata = new ArrayList<String[]>();
		public void fetchJobData(Map<String,String> jobMap) throws IOException, JSONException
		{
			
			for (Entry<String,String> entry : jobMap.entrySet()) {
		    String key = entry.getKey();
		    String thing = entry.getValue();
		    
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
	        jobdata=sb.toString();
	        File file = new File("D:/dap1/FetchBuildTime/Resources/json/"+key+".json");
	        BufferedWriter writer = null;
	        try {
	            writer = new BufferedWriter(new FileWriter(file));
	            writer.write(sb.toString());
	        } finally {
	            if (writer != null) writer.close();
	        }
	        cu.closeConnection(conn);
			
	        System.out.println("caling parsejobData");
	        parseJobData(jobdata);  
		    
	        sd.writeDataAtOnce(FILE, jsondata);
		}
		
			
			
		}
		
		
		void parseJobData(String jobdata) throws JSONException
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
			 
			 if ( (jsonObj.isNull("lastBuild"))) {
				 
				 System.out.println("Last build number found is null");
				 lastbuilnumber=0;
				 jobName=jsonObj.getString("name");
				 //healthReport="0";
				 try{
				 labelExpression=jsonObj.getString("labelExpression");
				 }
				 catch(Exception e)
				 {
					 System.out.println("not found");
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
			 
			 
		
		 fetchJobLastBuildData(lastbuilnumber, jobName, buildable,url,labelExpression);
			
	}
		 
		 void fetchJobLastBuildData(int lastbuilnumber, String jobName,  String buildable, String url, String labelExpression) throws JSONException
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
						//System.out.println("jobName	"+"url	"+"estimatedDuration	"+"lastresult"+"timestamp"+"buildable"+"labelExpression");
						//System.out.println(jobName+url+estimatedDuration+lastresult+timestamp+buildable+labelExpression);
						
						
						sd.savejsondate(jobName, url, estimatedDuration , lastresult , timestamp , buildable , labelExpression,jsondata );
		 	}

		
		 String timeStampConverter (String temptimestamp)
		 {
		 	
		 	
		 	long millis=Long.parseLong(temptimestamp);
		
		 	Date epoc= new Date(millis);
		 	DateFormat df2 = DateFormat.getDateInstance(DateFormat.MEDIUM);
		 	String s2 = df2.format(epoc);
		 	System.out.println(s2);
		 	return s2;
		 	
		 }
		 
		
}
