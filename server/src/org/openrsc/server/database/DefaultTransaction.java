/*
 * Copyright (C) openrsc 2009-13 All Rights Reserved
 * 
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * 
 * Written by openrsc Team <dev@openrsc.com>, January, 2013
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
 
package org.openrsc.server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.openrsc.server.Config;

/**
 * A default <code>Transaction</code> implementation that simply uses 
 * a single, static connection from the default server bootstrap 
 * configuration.  <b>It should be noted that using connection pooling 
 * may vastly enhance performance.</b>
 * 
 * @author Zilent
 * 
 * @version 1.1, 1.27.2013
 * 
 * @since 3.0
 *
 */
 public abstract class DefaultTransaction
	implements
		Transaction
{
	

	/// The connection that is used in all <code>DefaultTransactions</code>
	private static Connection connection;

	// If enabled, causes error "Loading class `com.mysql.jdbc.Driver'. This is deprecated. The new driver class is `com.mysql.cj.jdbc.Driver'. The driver is automatically registered via the SPI and manual loading of the driver class is generally unnecessary."
        // Leaving this code in just in-case we encounter bugs due to it not being manually loaded.
        /*static
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			
		}
		catch(ClassNotFoundException e)
		{
			throw new RuntimeException(e);
		}
	}*/
		
	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public Connection getConnection()
	{
		try
		{
			if(connection == null || !connection.isValid(0))
			{
				connection = ConnectionFactory.getDbConnection();
			}
		}
		catch (SQLException e)
		{
			connection = null;
		}
		catch(Throwable t)
		{
			t.printStackTrace();
		}
		return connection;
	}
}
