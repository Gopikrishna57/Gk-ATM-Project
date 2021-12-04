package com.atm.dblayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	public Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("oracle.jdbc.OracleDriver");
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "System", "gk517");

		return con;

	}
}
