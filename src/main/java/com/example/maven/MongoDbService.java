/*******************************************************************************
 * Copyright (C) 2010 - 2013 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com
 * 
 * Unless you have purchased a commercial license agreement from Jaspersoft, 
 * the following license terms apply:
 * 
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Jaspersoft Studio Team - initial API and implementation
 ******************************************************************************/
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