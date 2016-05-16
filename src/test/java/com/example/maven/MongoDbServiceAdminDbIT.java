package com.example.maven;

import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class MongoDbServiceAdminDbIT {

	private static final String MONGO_URI;

	static {
		String host = System.getProperty("mongodb.host", "localhost");
		String port = System.getProperty(MongoDbServiceAdminDbIT.class.getSimpleName() + ".port", "27017");
		MONGO_URI = "mongodb://" + host + ":" + port;
	}

    @BeforeClass
    public void setupAdminDb() {
		try (MongoClient client = new MongoClient(new MongoClientURI(MONGO_URI))) {
            MongoDatabase db = client.getDatabase("admin");
            MongoCollection<BasicDBObject> coll = db.getCollection("test_collection", BasicDBObject.class);
            BasicDBObject doc = new BasicDBObject("name", "myObject");
            coll.insertOne(doc);
		}
    }

	@Test
	public void ensureAdminDbNotEmpty() {
		Set<String> actualCollectionNames = new MongoDbService().getAdminCollections(MONGO_URI);
		Assertions.assertThat(actualCollectionNames).containsExactly("test_collection");
	}

}
