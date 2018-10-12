package main.java.com.yodlee;

import java.io.IOException;
import java.util.ArrayList;

public class MainTest {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		
		CsvMerge cm = new CsvMerge();
		ArrayList<String[]> finaldata= cm.filereader();
		cm.addfinaldata(finaldata);
	}

}
