package com.example.maven;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

/**
 * Test interactions with local database.
 * Use of defaults in this test allows us to debug it in a standard way from an IDE
 * with a db or container started separately by a developer.
 */
public class MongoDbServiceStartupLogIT {

	private static final String MONGO_URI;

	static {
		// get mongodb host address, use localhost by default
		String host = System.getProperty("mongodb.host", "localhost");
		// get mongodb service port for this test, use standard 27017 by default.
		String port = System.getProperty(MongoDbServiceStartupLogIT.class.getSimpleName() + ".port", "27017");
		MONGO_URI = "mongodb://" + host + ":" + port;
	}

	/**
	 * Tests that the local database contains startup_log collection.
	 */
	@Test
	public void ensureStartupLogPresence() {
		Set<String> actualCollectionNames = new MongoDbService().getLocalCollections(MONGO_URI);
		Assertions.assertThat(actualCollectionNames).containsExactly("startup_log");
	}

	/**
	 * Ensures that the admin database has no collections in it.
	 * In the other test class {@link MongoDbServiceAdminDbIT} we test the opposite (admin database is not empty).
	 * This is to show that each class uses a different mongodb instance.
	 */
	@Test
	public void ensureAdminDbEmpty() {
		Set<String> actualCollectionNames = new MongoDbService().getAdminCollections(MONGO_URI);
		Assertions.assertThat(actualCollectionNames).isEmpty();
	}

}
