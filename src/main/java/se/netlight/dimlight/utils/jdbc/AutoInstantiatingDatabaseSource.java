package se.netlight.dimlight.utils.jdbc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

public class AutoInstantiatingDatabaseSource extends BasicDataSource {
	private boolean initialized;
	private String preparationFile;
	
	private Logger logger = Logger.getLogger(AutoInstantiatingDatabaseSource.class); 

	@Override
	public Connection getConnection() throws SQLException {
		Connection ret = super.getConnection();
		checkInitialization(ret);
		return ret;
	}
	
	@Override
	public Connection getConnection(String user, String pass) throws SQLException {
		Connection ret = super.getConnection(user, pass);
		checkInitialization(ret);
		return ret;
	}

	public void setPreparationFile(String filename) {
		this.preparationFile = filename;
	}
	
	private void checkInitialization(Connection ret) throws SQLException {
		if (initialized)
			return;
		Statement statement = ret.createStatement();
		try {
			logger.info("Database not yet initialised, populating from " + preparationFile);
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream(preparationFile);
			if (inputStream == null)
				throw new FileNotFoundException("File not found");
			
			BufferedReader sqlReader = new BufferedReader(new InputStreamReader(inputStream));
			int lineNumber = 1;
			for (String line; (line = sqlReader.readLine()) != null; ) {
				try {
					statement.execute(line);
				} catch (SQLException e) {
					throw new SQLException("Failure in line " + lineNumber, e);
				}
				++lineNumber;
			}
			logger.info("Sucessfully inserted " + lineNumber + " lines");
			sqlReader.close();
		} catch (IOException e) {
			throw new RuntimeException("Failed to load file " + preparationFile + " from the classpath", e);
		}
		statement.close();
		initialized = true;
	}
}
