package com.ethz.oraclecounttable;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigProperties
{
	private static final Logger logger = LogManager.getLogger();
	public String configFilePath;
	public Properties configProperties;
	
	
	/**
	 * Constructor
	 * 
	 * @param configFileLocation
	 */
	public ConfigProperties(String configFileLocation)
	{
		configFilePath = Paths.get(".").toAbsolutePath().normalize().toString() + configFileLocation;
		configProperties = new Properties();
		readoutConfigFile();
	}
	
	
	/**
	 * Returns value for export directory
	 * 
	 * @return
	 */
	public String getExportDirectory()
	{
		return configProperties.getProperty("directory.export").trim();
	}
	
	
	/**
	 * Returns value for DB driver
	 * 
	 * @return
	 */
	public String getDbDriverName()
	{
		return configProperties.getProperty("db.drivername").trim();
	}
	
	
	/**
	 * Returns value for connection url
	 * 
	 * @return
	 */
	public String getDbConnectionUrl()
	{
		return configProperties.getProperty("db.connectionurl").trim();
	}	
	
	
	/**
	 * Returns value for DB user name
	 * 
	 * @return
	 */
	public String getDbUsername()
	{
		return configProperties.getProperty("db.username").trim();
	}	
	
	
	/**
	 * Returns value for DB password
	 * 
	 * @return
	 */
	public String getDbPassword()
	{
		return configProperties.getProperty("db.password").trim();
	}		
	
	
	
	/**
	 * Readout xml file and fill 
	 * 
	 */
	private void readoutConfigFile()
	{
		if(new File(configFilePath).exists())
		{
			try
			{
				configProperties.loadFromXML(new FileInputStream(configFilePath));
			}
			catch (IOException e)
			{
				logger.error(e.getMessage());
				System.exit(1);
			}	
		}
		else
		{
			logger.error(configFilePath + " not found");
			System.exit(1);
		}
	}
}
