package com.yodlee;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class ListJobs {

	 public String  fetchJoblist(String jenurl) throws IOException
	{
	ConnectionUtility cu = new ConnectionUtility();
	HttpURLConnection conn=cu.createConnection(jenurl);

	int code = conn.getResponseCode();
	System.out.println(code);
	InputStream ips=  conn.getInputStream();
	
	BufferedReader buf = new BufferedReader(new InputStreamReader(ips));

    StringBuilder sb = new StringBuilder();
    String s;
    String output = null;
    while ((s=buf.readLine()) != null) {
    	
        
        //System.out.println(s);
       sb.append(s+'\n');
       
    		
    }   
    buf.close();
    output=sb.toString();
    File file = new File("D:/dap1/FetchBuildTime/src/com/yodlee/output.txt");
    BufferedWriter writer = null;
    try {
        writer = new BufferedWriter(new FileWriter(file));
        writer.write(sb.toString());
    } finally {
        if (writer != null) writer.close();
    }
    cu.closeConnection(conn);
	return output;
}

public Map parseJsondata(String output) throws JSONException
{

	JSONObject jsonObj = new JSONObject(output);

	
		//System.out.println(jsonObj.get("jobs"));
	
		JSONArray ja = jsonObj.getJSONArray("jobs");
		JSONObject jsonObj1 = new JSONObject(ja);
		//System.out.println(ja.toString());
		//System.out.println(ja.getString(1));
		int count=ja.length();
		//System.out.println(count);
		String [] NameArray= new String[count];
		String [] urlArray= new String[count];
		Map<String,String> jobMap = new HashMap<>();
		
			
		for (int i=0;i<count;i++)
		{
			JSONObject jobj= (JSONObject) ja.get(i);
			//NameArray[i]= jobj.get("name").toString();
			
			JSONObject jbohurl= (JSONObject) ja.get(i);
			// urlArray[i] =jbohurl.get("url").toString();
			jobMap.put(jobj.get("name").toString(), jbohurl.get("url").toString());
		}
		
		for (Entry<String,String> entry : jobMap.entrySet()) {
		    String key = entry.getKey();
		    String thing = entry.getValue();
		    System.out.println(key+"-->"+thing);
		}
		return jobMap;

}

	
}
