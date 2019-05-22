package src.main.java.com.yodlee;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import org.bson.BSONObject;
import org.w3c.dom.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;

public class MainTest {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		ConnectionUtility cu = new ConnectionUtility();
		//cu.mongoConnection();


		System.out.println(cu.mongoConnection().getDatabase("BuildTimeApp").getCollection("cidatas"));
	DB db=cu.mongoConnection().getDB("BuildTimeApp");
	DBCollection col= db.getCollection("nightdatas");
	java.util.List ldlst= col.distinct("JobName");
	
	String[] stockArr = new String[ldlst.size()] ;
	
	
	for (int i=0;i<ldlst.size();i++)
	{
		
		String temp=ldlst.get(i).toString();
		System.out.println(temp);
		stockArr[i]=temp;
		
	}
	for(int i=0;i <stockArr.length;i++)
	{
		BasicDBObject Db = new BasicDBObject();
		Db.put("JobName", stockArr[i]);
		
		DBCursor cursor = col.find(Db);
		int j=0;
		int cursorsize = cursor.count();
		String[] temparry;
	    while (cursorsize !=0) {
	    	j++;
	    	cursor.getCursorId();
	        System.out.println(cursor.next().get("CronTime"));
	        cursorsize--;
	    }
	    System.out.println("number of jobs"+j);
		
	}
	
	
	
	/*DBCursor cursor = col.find();
	while(cursor.hasNext()) {
	    System.out.println(cursor.next().get("_id"));
	}*/
	
	

}
	}
	
