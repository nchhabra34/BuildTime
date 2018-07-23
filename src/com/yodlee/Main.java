package com.yodlee;

import java.awt.List;
import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONException;
import org.xml.sax.SAXException;

public class Main {

	public static void main(String[] args) throws JSONException, IOException, ParserConfigurationException, SAXException {

ListJobs lj = new ListJobs();
String output=lj.fetchJoblist("http://192.168.113.195:9090/api/json?pretty=true");
Map<String, String> jobMap= lj.parseJsondata(output);

jobXmlParser jxp = new jobXmlParser();

jxp.jbXmlParser(jobMap);

	}

}
