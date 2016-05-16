package com.example.maven;

import java.util.HashSet;
import java.util.Set;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

/**
 * This service let's us talk to MongoDB.
 */
public class MongoDbService {

	/**
	 * Get collections in local database.
	 */
	public Set<String> getLocalCollections(String mongoUri) {
		return getCollections(mongoUri, "local");
	}

	/**
	 * Get collections in admin database.
	 */
	public Set<String> getAdminCollections(String mongoUri) {
		return getCollections(mongoUri, "admin");
	}

	private Set<String> getCollections(String mongoUri, String dbName) {
		try (MongoClient client = new MongoClient(new MongoClientURI(mongoUri))) {
			Set<String> names = new HashSet<String>();
			for (String name : client.getDatabase(dbName).listCollectionNames()) {
				names.add(name);
			}
			return names;
		}
	}

}
