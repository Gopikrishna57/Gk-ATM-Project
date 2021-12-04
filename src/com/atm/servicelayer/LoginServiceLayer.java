package com.atm.servicelayer;

import com.atm.dblayer.LoginDBLayer;

public class LoginServiceLayer {
	public String loginCheck(String uname, String upsw) {
		String status = null;
		LoginDBLayer dblayer;
		try {
			System.out.println(uname + "--" + upsw);
			if (!((uname == null || uname == "") && (upsw == null || upsw == ""))) {
				dblayer = new LoginDBLayer();
				status = dblayer.userExistOrNot(uname, upsw);
			} else {
				status = "invalid";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(status);
		return status;
	}

}
