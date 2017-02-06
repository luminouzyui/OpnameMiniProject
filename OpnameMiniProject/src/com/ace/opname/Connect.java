package com.ace.opname;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.ace.example.util.DataSourceUtils;


public class Connect {
	private static Connect connect = new Connect();
	private Connection conn = null;
	private Statement stmt;
	private CallableStatement clStmt;
	private ResultSet rs;
	
	private Connect() {
		//singleton
	}
	public static Connect getInstance(){
		if(connect == null) connect = new Connect();
		return connect;
	}
	public void openConnection(){
		try {
			if(conn == null){
				//Class.forName("oracle.jdbc.driver.OracleDriver");
				//conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","opname","opname");
				conn = DataSourceUtils.getConnection();
			}
		//} catch (ClassNotFoundException e) {
		//	e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void closeConnection(){
		try {
			if(conn!= null) conn.close();
			conn = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ResultSet executeQuery(String sql) {
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return rs;
	}
	
	public int executeUpdate(String sql) {
		int res = 0;
		try {
			stmt = conn.createStatement();
			res = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public PreparedStatement prepStmt(String sql) {
		try {
			return conn.prepareStatement(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
	public CallableStatement prepCall(String sql) {
		try {
			return conn.prepareCall(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return null;
		}
		
	}
	
}
