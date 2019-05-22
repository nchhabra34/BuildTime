package src.main.java.com.yodlee;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONException;
import org.xml.sax.SAXException; 

import com.mongodb.MongoClient;





public class Main {

	public static void main(String[] args) throws JSONException, IOException, ParserConfigurationException, SAXException {

ListJobs lj = new ListJobs();
String output=lj.fetchJoblist("http://192.168.113.195:9090/api/json?pretty=true");
Map<String, String> jobMap= lj.parseJsondata(output);

jobXmlParser jxp = new jobXmlParser();
jxp.jbXmlParser(jobMap);
JobJsonParsing jjp = new JobJsonParsing();
jjp.fetchJobData(jobMap);
CsvMerge cm = new CsvMerge();
ArrayList<String[]> finaldata= cm.filereader();
//cm.addfinaldata(finaldata);

ConnectionUtility cu = new ConnectionUtility();
MongoClient mc=(MongoClient) cu.mongoConnection();


System.out.println(mc.getDatabase("BuildTimeApp").getCollection("ci_data").find());





	}
	

}
