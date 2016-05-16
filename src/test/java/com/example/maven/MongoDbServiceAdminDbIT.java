package com.example.maven;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * Test interactions with admin database.
 * Use of defaults in this test allows us to debug it in a standard way from an IDE
 * with a db or container started separately by a developer.
 */
public class MongoDbServiceAdminDbIT {

	private static final String MONGO_URI;

	static {
		// get mongodb host address, use localhost by default
		String host = System.getProperty("mongodb.host", "localhost");
		// get mongodb service port for this test, use standard 27017 by default.
		String port = System.getProperty(MongoDbServiceAdminDbIT.class.getSimpleName() + ".port", "27017");
		MONGO_URI = "mongodb://" + host + ":" + port;
	}

	/**
	 * Adds a collection to the admin database.
	 * A docker image with prepopulated database can be used this method starts to take a while to execute.
	 */
	@BeforeClass
	public void setupAdminDb() {
		try (MongoClient client = new MongoClient(new MongoClientURI(MONGO_URI))) {
			MongoDatabase db = client.getDatabase("admin");
			MongoCollection<BasicDBObject> coll = db.getCollection("test_collection", BasicDBObject.class);
			BasicDBObject doc = new BasicDBObject("name", "myObject");
			coll.insertOne(doc);
		}
	}

	/**
	 * Ensures that the admin database has one collection in it.
	 * In the other test class {@link MongoDbServiceStartupLogIT} we test the opposite (admin database is empty).
	 * This is to show that each class uses a different mongodb instance.
	 */
	@Test
	public void ensureAdminDbNotEmpty() {
		Set<String> actualCollectionNames = new MongoDbService().getAdminCollections(MONGO_URI);
		Assertions.assertThat(actualCollectionNames).containsExactly("test_collection");
	}

}
