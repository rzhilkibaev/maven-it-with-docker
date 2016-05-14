package com.example.maven;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

public class MongoDbServiceStartupLogIT {

	private static final String MONGO_URI;

	static {
		String host = System.getProperty("mongodb.host", "localhost");
		String port = System.getProperty(MongoDbServiceStartupLogIT.class.getSimpleName() + ".port", "27017");
		MONGO_URI = "mongodb://" + host + ":" + port;
	}

	@Test
	public void ensureStartupLogPresence() {
		Set<String> actualCollectionNames = new MongoDbService().getLocalCollections(MONGO_URI);
		Assertions.assertThat(actualCollectionNames).containsExactly("startup_log");
	}

}
