package com.example.maven;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

public class MongoDbServiceIT {

	private static final String MONGO_URI = "mongodb://localhost:" + System.getProperty("mongodb.port", "27017");

	@Test
	public void ensureStartupLogPresence() {
		Set<String> actualCollectionNames = new MongoDbService().getLocalCollections(MONGO_URI);
		Assertions.assertThat(actualCollectionNames).containsExactly("startup_log");
	}

}
