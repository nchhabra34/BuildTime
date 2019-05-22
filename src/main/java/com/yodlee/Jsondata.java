package src.main.java.com.yodlee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class Jsondata {
	
	int lastbuilnumber; 
	 String jobName;
	 String labelExpression = null;
	 boolean buildableboolean;
	 String buildable;
	 String url;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getLastbuilnumber() {
		return lastbuilnumber;
	}
	public void setLastbuilnumber(int lastbuilnumber) {
		this.lastbuilnumber = lastbuilnumber;
	}
	public String getLabelExpression() {
		return labelExpression;
	}
	public void setLabelExpression(String labelExpression) {
		this.labelExpression = labelExpression;
	}
	public boolean isBuildableboolean() {
		return buildableboolean;
	}
	public void setBuildableboolean(boolean buildableboolean) {
		this.buildableboolean = buildableboolean;
	}
	public String getBuildable() {
		return buildable;
	}
	public void setBuildable(String buildable) {
		this.buildable = buildable;
	}
	private String JobName;
	private List<String> crondescription=new ArrayList<>(); 
	private String lastresult ;
	private  String timestamp;
	private  String duration;
	 private String estimatedDuration;
	public void setCrondescription(List<String> crondescription) {
		this.crondescription = crondescription;
	}
	public String getLastresult() {
		return lastresult;
	}
	public void setLastresult(String lastresult) {
		this.lastresult = lastresult;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getEstimatedDuration() {
		return estimatedDuration;
	}
	public void setEstimatedDuration(String estimatedDuration) {
		this.estimatedDuration = estimatedDuration;
	}
	public String getJobName() {
		return JobName;
	}
	public List<String> getCrondescription() {
		return crondescription;
	}
	public void setJobName(String jobName) {
		JobName = jobName;
	}
	
	

}
