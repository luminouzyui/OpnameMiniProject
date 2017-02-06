package com.ace.example.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DSUtilTest {
	public static void main(String[] args) {
		testConnection();
	}
	
	public static void testConnection(){
		Connection connection = null;
		try {
			connection = DataSourceUtils.getConnection();
			Statement stmt = connection.createStatement();
			stmt.execute("select 1 from dual");
			System.out.println("Success");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(connection!= null)
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		
	}
}
