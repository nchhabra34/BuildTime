package main.java.com.yodlee;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;



public class CsvMerge {

	
	String xmlfile="D:/dap1/FetchBuildTime/Resources/xml/xmldata.csv";
	String jsonfile="D:/dap1/FetchBuildTime/Resources/json/jsondata.csv";
	ArrayList<String[]> finaldata = new ArrayList<String[]>();
	String[] finalarray ;
	ArrayList<String[]> filereader() throws IOException
	{
		FileReader frj = new FileReader (jsonfile);
	
	
	CSVReader crj = new CSVReader(frj);
	int finallength;
	String[] nextrecrod;
	String[] jsonrecord;
	while((jsonrecord =crj.readNext())!= null)
	{
		
		FileReader frx = new  FileReader("D:/dap1/FetchBuildTime/Resources/xml/xmldata.csv");
		CSVReader crx = new CSVReader(frx); 
		while((nextrecrod=crx.readNext())!=null)
		{
			System.out.println(crx.getLinesRead());
			System.out.println("xmlrecord 2nd while loop"+nextrecrod[0]);
			if (nextrecrod[0].matches(jsonrecord[0]))
			{
				
				finallength=nextrecrod.length+jsonrecord.length-1;
				System.out.println(finallength);
				int nextrecordlenght=nextrecrod.length;
				System.out.println(nextrecordlenght);
				int jsonrecordlenght=jsonrecord.length;
				System.out.println(jsonrecordlenght);
				finalarray = new String[finallength];
				System.out.println(finalarray.length);
				for(int i=0;i<nextrecordlenght;i++)
				{
					
					finalarray[i]=nextrecrod[i];
					System.out.println("adding xml array");
				}
				for(int i=0;i<jsonrecordlenght-1;i++)
				{
					finalarray[i+nextrecordlenght]=jsonrecord[i+1];
					System.out.println("adding json array");
				}
				finaldata.add(finalarray);
			}
		
	}
		crx.close();
		frx.close();
	
	}
frj.close();	
crj.close();
return finaldata;
	}
	
	
	
	void addfinaldata(ArrayList<String []> finaldata)
	{
	String File="D:/dap1/FetchBuildTime/Resources/xml/finaldata.csv";
	File file = new File(File); 
	  
    try { 
        // create FileWriter object with file as parameter 
        FileWriter outputfile = new FileWriter(file); 
  
        // create CSVWriter object filewriter object as parameter 
        CSVWriter writer = new CSVWriter(outputfile); 
  
        // create a List which contains String array 
        
        writer.writeAll(finaldata); 
  
        // closing writer connection 
        writer.close(); 
    } 
    catch (IOException e) { 
        // TODO Auto-generated catch block 
        e.printStackTrace(); 
    }
	
	
	}
}
