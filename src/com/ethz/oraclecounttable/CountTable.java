package com.ethz.oraclecounttable;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CountTable
{
	private static final Logger logger = LogManager.getLogger();
	private String queryXmlFilePath;
	private Properties queryProperties;
	private ConfigProperties configProperties;
	private Connection oracleConn = null;
	
	/**
	 * Constructor for correct setup
	 * 
	 * @param configFilePath
	 * @param queryFilePath
	 */
	public CountTable(String configFilePath, String queryFilePath)
	{
		configProperties = new ConfigProperties(configFilePath);
		
		queryXmlFilePath = System.getProperty("user.dir") + queryFilePath;
		queryProperties = new Properties();		
		readoutQueryFile();
	}
	
	/**
	 * Start actual DB queries and write to files
	 * 
	 */
	public void runCountTable()
	{
		Set<String> keys = queryProperties.stringPropertyNames();
		
		for(String fileName : keys)
		{
			try
			{
				setupConnection();
				
				logger.debug(configProperties.getExportDirectory() + fileName + ": " + queryProperties.getProperty(fileName));
				logger.debug(getOracleQueryResult(queryProperties.getProperty(fileName)));
				

				Files.write(Paths.get(configProperties.getExportDirectory() + fileName), 
								getOracleQueryResult(queryProperties.getProperty(fileName)).getBytes());
			}
			catch (IOException e)
			{
				logger.error(e.getMessage());
				System.exit(1);
			}
			finally
			{
				closeConnection();
			}
		}
	}
	
	/**
	 * Get results from Oracle with provided queries
	 * 
	 * @param query
	 * @return
	 */
	private String getOracleQueryResult(String query)
	{
		String returnValue = "";
		ResultSet rs = null;
		
		try
		{
			PreparedStatement stmt = oracleConn.prepareStatement(query);
			rs = stmt.executeQuery();
			if(rs.next())
			{
				returnValue = rs.getString(1);
			}
		}
		catch (SQLException e)
		{
			logger.error(e.getMessage());
			System.exit(1);
		}
		
		return returnValue;
	}
	
	
	/**
	 * Readout query xml file and fill queryProperties object
	 * 
	 */
	private void readoutQueryFile()
	{
		if(new File(queryXmlFilePath).exists())
		{
			try
			{
				queryProperties.loadFromXML(new FileInputStream(queryXmlFilePath));
			}
			catch (IOException e)
			{
				logger.error(e.getMessage());
				System.exit(1);
			}	
		}
		else
		{
			logger.error(queryXmlFilePath + " not found");
			System.exit(1);
		}
	}	
	
	
	/**
	 * Opens JDBC Oracle connection
	 * 
	 */
	private void setupConnection()
	{

		try
		{
        	Class.forName(configProperties.getDbDriverName());			
        	oracleConn = DriverManager.getConnection(configProperties.getDbConnectionUrl(), configProperties.getDbUsername(), configProperties.getDbPassword());
		}
		catch (ClassNotFoundException e)
		{
			logger.fatal(e.getMessage());
			System.exit(1);
		}
		catch(SQLException e)
		{
			logger.fatal(e.getMessage());
			System.exit(1);
		}
	}		
	
	
	
	/**
	 * Closes JDBC Oracle connection
	 * 
	 */
	private void closeConnection()
	{
		try
		{
			oracleConn.close();
		}
		catch (SQLException e)
		{
			logger.error(e.getMessage());
		}
	}	

}
