package com.atm.servicelayer;

import com.atm.dblayer.RegDBLayer;

public class RegServiceLayer {
	public String insertNewUser(String uname, String upsw, String uadhr, int ubalance) {
		String status = null;

		if ((uname.equals(null) || uname.equals("")) && (upsw.equals(null) || upsw.equals(""))
				&& (uadhr.equals(null) || uadhr.equals("")) && (ubalance > 0)) {

			status = "invalid";
			return status;
		}
		if (upsw == null || upsw == "") {
			status = "invalid";
			return status;
		}
		if (uadhr == null || uadhr == "") {
			status = "invalid";
			return status;
		}
		if (ubalance < 0) {
			status = "invalid";
			return status;
		}

		RegDBLayer dblayer = new RegDBLayer();
		status = dblayer.insertNewUserData(uname, upsw, uadhr, ubalance);

		return status;
	}
}
