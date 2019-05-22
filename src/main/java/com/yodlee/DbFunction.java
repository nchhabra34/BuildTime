package src.main.java.com.yodlee;

import java.util.List;

import org.json.JSONObject;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

public class DbFunction {

	public MongoClient mongoConnection()
	{
		
		MongoClient mongoClient = new MongoClient("192.168.113.247" , 27017 );
		
		
		
		return mongoClient;
		
		
	}
	
	public void dbInsert(MongoClient mongoClient, List <JSONObject> list, String collectionname)
	{
		
		DB db = mongoClient.getDB("BuildTimeApp");
	DBCollection coll=	db.getCollection(collectionname);
	coll.find();
		
	System.out.println("Enter in Db Function");
		coll.drop();
		
		for(int i=0;i< list.size();i++)
		{
			JSONObject jsonObj = new JSONObject( list.get(i).toString() );
			DBObject dbObject = (DBObject) JSON.parse(jsonObj.toString());
	        coll.insert(dbObject);
		}
		mongoClient.close();
	}
}
