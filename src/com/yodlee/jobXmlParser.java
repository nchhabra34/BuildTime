package com.yodlee;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.nio.Buffer;
import java.nio.charset.Charset;
import java.security.DomainCombiner;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.text.html.HTMLEditorKit.Parser;
import javax.swing.text.html.HTMLEditorKit.ParserCallback;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;

import java.io.*;

public class jobXmlParser {

	public void jbXmlParser(Map<String, String> jobmap) throws IOException, ParserConfigurationException, SAXException
	{
		
		for (Entry<String,String> entry : jobmap.entrySet()) {
		    String key = entry.getKey();
		    String thing = entry.getValue();
		    
		    ConnectionUtility cu= new ConnectionUtility();
		 HttpURLConnection conn = cu.createConnection(thing+"config.xml");
		 
		
		 //InputStream in = uc.getInputStream();
		 InputStream ips;
		 BufferedReader buf;
		 StringBuffer sb = new StringBuffer();
		 String s;
		 ips = conn.getInputStream();
		 
		 buf = new BufferedReader(new InputStreamReader(ips, Charset.forName("UTF-8")));
		 
		 while ((s=buf.readLine()) != null)
		 {
			 
			 sb.append(s+'\n');
			 
		 }
		 buf.close();
		 
		 	s=sb.toString();
		 	//System.out.println(s);
		    DocumentBuilderFactory dbFactory =   DocumentBuilderFactory.newInstance();	
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         Document doc = dBuilder.parse(s);
	         
	         doc.getDocumentElement().normalize();
	        // System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		   
		}
		
			
		
		//return jobmap;	
	}
}

