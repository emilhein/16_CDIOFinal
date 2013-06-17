	Connector connector = null;

		try {

			// Connect
			connector = new Connector("sql-lab1.cc.dtu.dk", 3306, "s123115", "s123115", "F5iCtVPs4rtHu4oM");

			// Drop
			for (String table : tables) {
				try {
					connector.doUpdate("DROP TABLE " + table + ";");
				} catch (DALException e) {
					e.printStackTrace();
				}
			}
						
			// Create
			connector.doUpdate("CREATE TABLE operator(oprId INTEGER NOT NULL, oprName VARCHAR(20), ini VARCHAR(4), cpr VARCHAR(10) NOT NULL, oprPassword VARCHAR(10) NOT NULL, rights INTEGER NOT NULL, PRIMARY KEY(oprId)) ENGINE=innoDB;");
			connector.doUpdate("CREATE TABLE commodity(commodityId INTEGER NOT NULL, commodityName VARCHAR(20), supplier VARCHAR(20), PRIMARY KEY(commodityId)) ENGINE=innoDB;");
			connector.doUpdate("CREATE TABLE commodityBatch(cbId INTEGER NOT NULL, commodityId INTEGER, quantity REAL NOT NULL, PRIMARY KEY(cbId), FOREIGN KEY (commodityId) REFERENCES commodity(commodityId)) ENGINE=innoDB;");
			connector.doUpdate("CREATE TABLE recipe(recipeId INTEGER NOT NULL, recipeName VARCHAR(20), PRIMARY KEY(recipeId)) ENGINE=innoDB;");
			connector.doUpdate("CREATE TABLE recipeComponent(recipeId INTEGER, commodityId INTEGER, nomNetto REAL NOT NULL, tolerance REAL NOT NULL, PRIMARY KEY(recipeId, commodityId), FOREIGN KEY(recipeId) REFERENCES recipe(recipeId), FOREIGN KEY(commodityId) REFERENCES commodity(commodityId)) ENGINE=innoDB;");
			connector.doUpdate("CREATE TABLE productBatch(pbId INTEGER NOT NULL, recipeId INTEGER, ts VARCHAR(30), state INTEGER NOT NULL, PRIMARY KEY(pbId), FOREIGN KEY(recipeId) REFERENCES recipe(recipeId)) ENGINE=innoDB;");
			connector.doUpdate("CREATE TABLE productBatchComponent(pbId INTEGER, cbId INTEGER, tara REAL NOT NULL, netto REAL NOT NULL, oprId INTEGER, PRIMARY KEY(pbId, cbId), FOREIGN KEY(pbId) REFERENCES productBatch(pbId), FOREIGN KEY(cbId) REFERENCES commodityBatch(cbId), FOREIGN KEY(oprId) REFERENCES operator(oprId)) ENGINE=innoDB;");

			
			
			//operator --- oprId: int, oprName: varchar, initialer: varchar, cpr: varchar, password: varchar, rights: int.
			//commodity --- commodityID: int, commodityName: Varchar, supplier: varchar. 
			//commoditybatch --- cbId: int, commodityId: int, quantity: real.
			//recipe --- recipeId: int, recipeName: varchar, 
			//recipeComponent --- recipeId: int, commodityId int, nomNetto: Real, Tolerance: real.
			//productbatch --- pbId: int, recipeId: int, ts: VARCHAR(30), state: int.
			//productsbatchComponent --- pbId: int, cbId: int, tara: real, netto: real, oprId: int.
			
			
			// inds√¶t operatoere.
			connector.doUpdate("INSERT INTO operator VALUES(1,'Mathias','MEL','2404922559','123456',1)");
			connector.doUpdate("INSERT INTO operator VALUES(2,'Emil','EHE','2404922559','123456',2)");
			connector.doUpdate("INSERT INTO operator VALUES(3,'Jens','JWN','2404922559','123456',3)");
			connector.doUpdate("INSERT INTO operator VALUES(4,'Khan','KN','2404922559','123456',4)");
			connector.doUpdate("INSERT INTO operator VALUES(5,'Kim','KIM','2404922559','123456',1)");
			
			// inds√¶t commodity.
			connector.doUpdate("INSERT INTO commodity VALUES(1,'Citron','Spain')");
			connector.doUpdate("INSERT INTO commodity VALUES(2,'salt','Samsoe')");
			connector.doUpdate("INSERT INTO commodity VALUES(3,'vand','Norge')");
			
			// inds√¶t commodityBatch.
			connector.doUpdate("INSERT INTO commodityBatch VALUES(1,1,2.3)");
			connector.doUpdate("INSERT INTO commodityBatch VALUES(2,3,25.3)");
			connector.doUpdate("INSERT INTO commodityBatch VALUES(3,2,1.0)");
			
			// inds√¶t recipe.
			connector.doUpdate("INSERT INTO recipe VALUES(1,'Citronvand')");
			connector.doUpdate("INSERT INTO recipe VALUES(2,'Citronsalt')");
			connector.doUpdate("INSERT INTO recipe VALUES(3,'CitronMedSalt')");
			
			// inds√¶t recipeComponent.
			connector.doUpdate("INSERT INTO recipeComponent VALUES(1,1, 1.2, 0.7)");
			connector.doUpdate("INSERT INTO recipeComponent VALUES(1,2, 12.2, 0.4)");
			
			// inds√¶t productBatch.
			connector.doUpdate("INSERT INTO productBatch VALUES(1,1,'" + new Date().toString() + "',1)");
			
			// inds√¶t productBatchComponent.
			//connector.doUpdate("INSERT INTO productBatchComponent VALUES(1,1, 12.2,12.0,1)");
			
			
		} finally {

			// Close
			try {
				connector.Close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

