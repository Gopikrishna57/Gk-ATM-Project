package com.atm.dblayer;

import java.sql.Connection;
import java.sql.Statement;

public class RegDBLayer extends DBConnection {
	public String insertNewUserData(String uname, String upsw, String uadhr, int ubalance) {
		String user = null;
		try {

			LoginDBLayer dblayer = new LoginDBLayer();
			user = dblayer.userExistOrNot(uname, upsw);

			if (user.equals("exist")) {
				user = "exist";
			}
			if (user.equals("notexist")) {
				Connection con = getConnection();
				Statement st = con.createStatement();

				String query = "insert into customer values('" + uname + "','" + upsw + "','" + uadhr + "','" + ubalance
						+ "')";
				int rowCount = st.executeUpdate(query);
				if (rowCount > 0) {
					user = "success";
				} else {
					user = "failure";
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;
	}

}
