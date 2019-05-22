package src.main.java.com.yodlee;

import java.io.IOException;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;
import com.mongodb.MongoCredential;
import com.mongodb.MongoClientOptions;

public class ConnectionUtility {

	public HttpURLConnection createConnection(String jenurl) throws IOException
	{
		
		URL url = new URL(jenurl);
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setRequestMethod("GET");
		 String userpass = "builder" + ":" + "CIAdmin4dev";
		 String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());
		 connection.setRequestProperty("Accept", "application/xml");
		 connection.setRequestProperty("Content-Type", "application/xml");


		 connection.setRequestProperty ("Authorization", basicAuth);
		connection.connect();
		return connection;
	}

	public void closeConnection(HttpURLConnection connection)
	{
		connection.disconnect();
			
	}
	public MongoClient mongoConnection()
	{
		
		MongoClient mongoClient = new MongoClient("127.0.0.1" , 27017 );
		
		
		
		return mongoClient;
		
		
	}
	
	
	
}
