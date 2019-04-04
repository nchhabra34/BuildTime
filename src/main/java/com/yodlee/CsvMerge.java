package main.java.com.yodlee;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.StyledEditorKit.BoldAction;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;




public class CsvMerge {

	
	String xmlfile="D:/dap1/FetchBuildTime/Resources/xml/xmldata.csv";
	String jsonfile="D:/dap1/FetchBuildTime/Resources/json/jsondata.csv";
	ArrayList<String[]> finaldata = new ArrayList<String[]>();
	String[] finalarray ;
	ArrayList<String[]> filtercidata = new ArrayList<String[]>();
	ArrayList<String[]> filternightlydata = new ArrayList<String[]>();
	ArrayList<String[]> otherJobs = new ArrayList<String[]>();
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
csvDataFilter(finaldata);
return finaldata;
	}
	
	// Merging the diffrent jobs to single jobs
	
	
	// Filtering the CI And Nightly Data Seprately
void csvDataFilter(ArrayList<String []> finaldata)
	{
	
	String ciregex="(?i)(.*)(_CI$)";
	String nightregex="(?i)^(nightlyprowler_.*_*)";
	Pattern cip = Pattern.compile(ciregex);
	Pattern nightp= Pattern.compile(nightregex);
	Matcher cim ;
	Matcher nightm;
	for(int i=0;i < finaldata.size(); i++)
	{
		String[] temp=finaldata.get(i);
		
		//System.out.println(temp[0]);
		
		cim= cip.matcher(temp[0]);
		nightm=nightp.matcher(temp[0]);
		
		if (cim.matches())
		{
			filtercidata.add(finaldata.get(i));
			
		}
		else if (nightm.matches())
		{
			CsvDataFilter nightfilter = new CsvDataFilter();
			
			Boolean result= nightfilter.filterNightlybuild(finaldata.get(i));
			System.out.println(result);
			String[] temp1=finaldata.get(i);
			String swap=temp1[0].replaceAll("(?i)NightlyProwler_", "");
			System.out.println(swap);
			temp1[0]=swap;
			finaldata.set(i, temp1);
			if(result)
			{
				filternightlydata.add(finaldata.get(i));
			}
			
		}
		else
		{
			String[] temp1=finaldata.get(i);
			if(temp1[0].toLowerCase().contains("ci") || temp1[0].toLowerCase().contains("deployment") || temp[0].toLowerCase().contains("package"))
			{
				otherJobs.add(finaldata.get(i));
				
			}
			else
			{
				filternightlydata.add(finaldata.get(i));
			}
			
			
		}
		
		
		
	}
	
	System.out.println(filtercidata.size());
	System.out.println(filternightlydata.size());
	System.out.println(otherJobs.size());
	System.out.println(finaldata.size());
       
    addfinaldata(filtercidata, filternightlydata, otherJobs);
       
    }

	
	
	void addfinaldata( ArrayList<String []> filtercidata,ArrayList<String []> filternightlydata,ArrayList<String []> otherJobs)
	{
		ArrayList[] File={filtercidata,filternightlydata,otherJobs};
		
		
		for (int i=0;i<File.length;i++)
		{
			File file = new File("D:/dap1/FetchBuildTime/Resources/"+"file"+i+".csv"); 
			  
		    try { 
		        // create FileWriter object with file as parameter 
		        FileWriter outputfile = new FileWriter(file); 
		  
		        // create CSVWriter object filewriter object as parameter 
		        CSVWriter writer = new CSVWriter(outputfile); 
		  
		        String[] entry={"JobName","CronTime","TimeDescription","CronStatus","URL","Timetaken","LastJobStatus","LastJobDate","Server","JobStatus"};
		       writer.writeNext(entry);
		        
		        writer.writeAll(File[i]); 
		  
		        // closing writer connection 
		        writer.close(); 
		    } 
		    catch (IOException e) { 
		        // TODO Auto-generated catch block 
		        e.printStackTrace(); 
		    }
			
		}
	
	
	
	}
}
