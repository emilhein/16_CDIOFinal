package test;

import static org.junit.Assert.*;
import org.junit.Test;
import database.DALException;
import database.DatabaseAccess;

public class Database {

	@Test
	public void createDatabaseAccess() {

		try {
			new DatabaseAccess();
		} catch (DALException e) {
			fail();
		}
	
	}

}
