package com.atm.dblayer;

import java.sql.Connection;
import java.sql.Statement;

public class LoginDBLayer extends DBConnection {
	public String userExistOrNot(String uname, String upsw) // throws ClassNotFoundException, SQLException
	{
		String status = null;
		try {

			Connection con = getConnection();
			Statement st = con.createStatement();

			String sql = "select * from customer where uname='" + uname + "' and upsw='" + upsw + "'";
			System.out.println(sql);
			int hasExists = st.executeUpdate(sql);

			System.out.println(uname + "--" + upsw);

			if (hasExists > 0) {
				status = "exist";
			} else {
				status = "notexist";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(status);
		return status;
	}

}
