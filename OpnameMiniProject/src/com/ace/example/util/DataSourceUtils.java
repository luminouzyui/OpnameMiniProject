package com.ace.example.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DataSourceUtils {
	private static DataSource dataSource;
	static{
		Context context = null;
		Hashtable<String, Object> environment = new Hashtable<String, Object>();
		environment.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
		environment.put(Context.PROVIDER_URL,"t3://localhost:7001");
		environment.put(Context.SECURITY_PRINCIPAL, "weblogic");
		environment.put(Context.SECURITY_CREDENTIALS, "weblogic12");
		
		try {
			context = new InitialContext(environment);
			dataSource = (DataSource) context.lookup("opname_ds");
		} catch (NamingException e) {
			e.printStackTrace();
		} finally {
			if(context!=null)	
				try {
					context.close();
				} catch (NamingException e) {
					e.printStackTrace();
				}
		}
		
	}
	
	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
	
}
