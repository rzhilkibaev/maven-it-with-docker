package com.example.maven;

import java.util.HashSet;
import java.util.Set;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class MongoDbService {

	public Set<String> getLocalCollections(String mongoUri) {
		return getCollections(mongoUri, "local");
	}

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
