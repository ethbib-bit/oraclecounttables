package com.ethz.oraclecounttable;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class AppStarter
{
	private static final Logger logger = LogManager.getLogger();
	private static final String PROPERTY_QUERIES = "/conf/tables_queries.xml";
	private static final String PROPERTY_CONFIG = "/conf/config.xml";
	
	/**
	 * Main method to run application
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		CountTable ct = new CountTable(PROPERTY_CONFIG, PROPERTY_QUERIES);
		ct.runCountTable();
	}

}
