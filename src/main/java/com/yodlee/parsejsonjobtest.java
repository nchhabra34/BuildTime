package src.main.java.com.yodlee;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.sql.Date;
import java.text.DateFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class parsejsonjobtest {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		Jsondata jd = new Jsondata();
		String thing="http://192.168.113.195:9090/job/NightlyProwler_DAPGATHERER_FEATURE";

		ConnectionUtility cu= new ConnectionUtility();
		  
	    
		   HttpURLConnection conn = cu.createConnection(thing+"/api/json?pretty=true");
		    
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
	      /* JSONObject jsonObj = new JSONObject(jobdata);
	       JSONArray downstreamarray=  (JSONArray) jsonObj.get("downstreamProjects");
	       String downstreamurl = null;
	       System.out.println(downstreamarray.length());
	       for (int i=0;i<downstreamarray.length();i++)
	       {
	    	   JSONObject jsonobject = downstreamarray.getJSONObject(i);
	        downstreamurl=jsonobject.getString("url");
	       }
	      // System.out.println(downstreamurl);
		
	       fetchJobData(downstreamurl, jd);
	       
	       System.out.println();*/
	       fetchJobData(thing, jd);
		
	}
	
	public static String fetchJobData(String thing, Jsondata jd) throws IOException, JSONException
	{
		 
		
	    ConnectionUtility cu= new ConnectionUtility();
	  
	    
	   HttpURLConnection conn = cu.createConnection(thing+"/api/json?pretty=true");
	    
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
	
	
		
	static Jsondata parseJobData(String jobdata, Jsondata jd) throws JSONException, IOException
	{
		JSONObject jsonObj = new JSONObject(jobdata);
		 JSONObject jlastbuildnumber = new JSONObject(jsonObj.get("lastBuild"));
		
		 
		// System.out.println(jsonObj.get("url"));
		 
		 int lastbuilnumber; 
		 String jobName;
		 String labelExpression = null;
		 boolean buildableboolean;
		 String buildable;
		 String url=jsonObj.get("url").toString();
		 jobName=jsonObj.get("name").toString();
		 
		 if (jobName.contains("NightlyProwler"))
		    {
		    	System.out.println("entering in the nightly if condition"+jobName);
		    	//String downstreamurl=processnightlyjobs(thing, jd, currentDirectory, key, jsonclassdata);
		    	
			       JSONArray downstreamarray=  (JSONArray) jsonObj.get("downstreamProjects");
			       String downstreamurl = null;
			       for (int i=0;i<downstreamarray.length();i++)
			       {
			    	   JSONObject jsonobject = downstreamarray.getJSONObject(i);
			        downstreamurl=jsonobject.getString("url");
			      
			       }
			       System.out.println(downstreamurl);
			       fetchJobData(downstreamurl, jd);
		    }
		 else{
		 	
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
		 }
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
