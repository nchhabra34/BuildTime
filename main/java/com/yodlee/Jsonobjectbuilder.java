package main.java.com.yodlee;

import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

public class Jsonobjectbuilder {

	
	List<JSONObject> jsonlistfsb = new ArrayList<JSONObject>();
	List<JSONObject> jsonlistci = new ArrayList<JSONObject>();
	
	public void objectcreation(List<Jsondata> jsonclassdata)
	{
		for (int i = 0; i < jsonclassdata.size();i++)
		{
		 Jsondata jd =jsonclassdata.get(i);
		 
		
		String ciregex="(?i)(.*)(_CI$)";
		String nightregex="(?i)^(nightlyprowler_.*_*)";
		Pattern cip = Pattern.compile(ciregex);
		Pattern nightp= Pattern.compile(nightregex);
		Matcher cim ;
		Matcher nightm;
		
			
			
			
			
			cim= cip.matcher(jd.getJobName().toString());
			nightm=nightp.matcher(jd.getJobName().toString());
			
			if (cim.matches())
			{
				JSONObject jo = new JSONObject();
				jo.put("JobName", jd.getJobName());
				jo.put("CronDescription", jd.getCrondescription());
				jo.put("server", jd.getLabelExpression());
				jo.put("lastbuildnumber", jd.getLastbuilnumber());
				jo.put("estimatedtime", jd.getEstimatedDuration());
				jo.put("lastresult", jd.getLastresult());
				jo.put("JobEnable", jd.getBuildable());
				jo.put("url", jd.getUrl());
				
				jsonlistci.add(jo);
				
			}
			else if(nightm.matches())
			{
				
				//Boolean result= jd.getJobName()
				//System.out.println(result);
				String temp1=jd.getJobName().toString();
				String swap=temp1.replaceAll("(?i)NightlyProwler_", "");
				System.out.println(swap);
				temp1=swap;
				jd.setJobName(temp1);
				JSONObject jo = new JSONObject();
				jo.put("JobName", jd.getJobName());
				jo.put("CronDescription", jd.getCrondescription());
				jo.put("server", jd.getLabelExpression());
				jo.put("lastbuildnumber", jd.getLastbuilnumber());
				jo.put("estimatedtime", jd.getEstimatedDuration());
				jo.put("lastresult", jd.getLastresult());
				jo.put("JobEnable", jd.getBuildable());
				jo.put("url", jd.getUrl());
				
				jsonlistfsb.add(jo);
			}
			else
			{
				JSONObject jo = new JSONObject();
				jo.put("JobName", jd.getJobName());
				jo.put("CronDescription", jd.getCrondescription());
				jo.put("server", jd.getLabelExpression());
				jo.put("lastbuildnumber", jd.getLastbuilnumber());
				jo.put("estimatedtime", jd.getEstimatedDuration());
				jo.put("lastresult", jd.getLastresult());
				jo.put("JobEnable", jd.getBuildable());
				jo.put("url", jd.getUrl());
				
				
				jsonlistfsb.add(jo);
			}
				
			
			
		
		
		
		}
		
	DbFunction db = new DbFunction();
		db.dbInsert(db.mongoConnection(), jsonlistci,"cidatas");
		db.dbInsert(db.mongoConnection(), jsonlistfsb, "nightdatas");
		
		
	}
}
