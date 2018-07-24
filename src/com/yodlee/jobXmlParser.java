package com.yodlee;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;
import java.nio.charset.Charset;
import java.security.DomainCombiner;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.text.html.HTMLDocument.HTMLReader.SpecialAction;
import javax.swing.text.html.HTMLEditorKit.Parser;
import javax.swing.text.html.HTMLEditorKit.ParserCallback;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;

import java.io.*;

public class jobXmlParser {

	public void jbXmlParser(Map<String, String> jobmap) throws IOException, ParserConfigurationException, SAXException
	{
		
		/*for (Entry<String,String> entry : jobmap.entrySet()) {
		    String key = entry.getKey();
		    String thing = entry.getValue();*/
		    
		    ConnectionUtility cu= new ConnectionUtility();
		 HttpURLConnection conn = cu.createConnection("http://192.168.113.195:9090/job/NightlyProwler_OAUTHCLIENT_PSD/"+"config.xml");
		 
		 System.out.println(conn.getContentType());
		 conn.connect();
		 
		  	/*BufferedReader reader = new  BufferedReader(new InputStreamReader(conn.getInputStream()));
		  	
		  	String inputline;
		  	
		  	
		  	BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		  	
		  	FileOutputStream fis = new FileOutputStream(file);
		  	byte[] buffer = new byte[1024];
		  	int count=0;
		  	while((inputline=reader.readLine()) != null)
		  	{
		  		 System.out.println(inputline);
		  		writer.write(inputline);
		  		
		  	}
		  	reader.close();*/
		 InputStream ips ;
		 ips= conn.getInputStream();
		 File file = new File("D:/dap1/FetchBuildTime/Resources/requested.xml");
		 URL url = new URL("http://192.168.113.195:9090/job/NightlyProwler_OAUTHCLIENT_PSD/"+"config.xml");
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
	        Document doc=builder.parse(new File("D:/dap1/FetchBuildTime/Resources/requested.xml"));
	        doc.getDocumentElement().normalize();
	        //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
	        //NodeList nodeList = doc.getElementsByTagName("triggers");
	        //System.out.println(nodeList);
	        //NodeList nodeList2 = doc.getElementsByTagName("hudson.triggers.TimerTrigger");
	       //Node n =nodeList2.item(0);
	       //System.out.println(n.getNodeName());
	       //Element element =  (Element) n.getChildNodes();
	       //System.out.println("\nCurrent Element :" + n.getNodeName());
	      
	      NodeList nodelist3 = doc.getElementsByTagName("spec");
	      
	      //System.out.println(nodelist3.getLength());
	      Node n3 = nodelist3.item(0);
	     // System.out.println(n3.getTextContent());
	      
	      String timestamp=n3.getTextContent();
	      
	      //System.out.println(timestamp);
	    String [] s1=  timestamp.split("\n");
	    for (int i=0;i<s1.length;i++)
	    {
	    	
	    	System.out.println("String number-->"+i+s1[i]);
	    	
	    }
	    String [] sepratecron = s1[0].split("\t");
	    for(int i=0;i<sepratecron.length;i++)
	    {
	    	
	    	System.out.println("---"+sepratecron[i]);
	    }
	    
	    
	      
	      //System.out.println(n3.get);
	      // System.out.println(spec);
	       
	      // No= (Element) n.getChildNodes();deList node = doc.getChildNodes();
	      //  System.out.println(node.getLength());
	      //  System.out.println(node.item(0));
	      //  System.out.println(doc.getElementsByTagName("triggers").getLength());
	      /*  NodeList trigernode = (NodeList) doc.getElementsByTagName("triggers");
	        for(int i=0;i<=trigernode.getLength();i++)
	        {
	        	Element line = (Element) trigernode.item(i);
	        	
	        	
	        			
	        	
	        	
	        }*/
	        
	        
	    }
	        catch(ParserConfigurationException e)
	        {
	        	e.printStackTrace();
	        }
	        }

	private String getTagValue(String string, Element element) {
		// TODO Auto-generated method stub
		return null;
	}
	
}

