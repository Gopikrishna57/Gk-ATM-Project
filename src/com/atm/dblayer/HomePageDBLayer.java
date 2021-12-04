package com.atm.dblayer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class HomePageDBLayer extends DBConnection {

	public ResultSet getUserDetails(String upsw) {
		ResultSet getData = null;
		try {
			Connection con = getConnection();
			Statement st = con.createStatement();

			String query = "select * from customer where upsw='" + upsw + "'";
			getData = st.executeQuery(query);
			boolean b = getData.next();
			if (b == true) {
				String uadhr = getData.getString("uadhr");
				String uname = getData.getString("uname");

				String query2 = "select * from customer where uadhr='" + uadhr + "' and uname='" + uname + "'";
				getData = st.executeQuery(query2);
			} else {
				getData = null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return getData;
	}

	public int depositAmount(String upsw, int depositBal) {
		// String depositStatus=null;
		int rowCount = 0;
		try {
			Connection con = getConnection();
			Statement st = con.createStatement();

			ResultSet get = getUserDetails(upsw);
			String uadhr = null;
			while (get.next()) {
				uadhr = get.getString("uadhr");
				System.out.println("Adhar : " + uadhr);
			}
			String query = "update customer SET ubalance=ubalance+'" + depositBal + "' where uadhr=" + uadhr + "";
			rowCount = st.executeUpdate(query);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return rowCount;
	}

	public int withdrawAmount(String upsw, int withdrawBal) {
		int rowCount = 0;
		try {
			Connection con = getConnection();
			Statement st = con.createStatement();

			ResultSet get = getUserDetails(upsw);

			String uadhr = null;
			while (get.next()) {
				uadhr = get.getString("uadhr");
			}
			String query = "update customer SET ubalance=ubalance-'" + withdrawBal + "' where uadhr=" + uadhr + "";
			rowCount = st.executeUpdate(query);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return rowCount;
	}

	public String checkReceiverAndTransfer(String uname, String uadhr, int transferBal, String upsw) {
		String transferStatus = null;
		try {
			Connection con = getConnection();
			Statement st = con.createStatement();

			String sql = "select * from customer where uname='" + uname + "' and uadhr='" + uadhr + "'";
			ResultSet recData = st.executeQuery(sql);
			boolean b = recData.next();
			System.out.println(uname + "--" + uadhr + "--" + transferBal);
			if (b == true) {
				System.out.println(b + "--" + uname + "--" + uadhr + "--" + transferBal);
				String recSql = "update customer SET ubalance=ubalance+'" + transferBal + "' where uadhr=" + uadhr + "";
				int recCount = st.executeUpdate(recSql);

				System.out.println(recCount + "--" + upsw);

				String sql2 = "update customer SET ubalance=ubalance-'" + transferBal + "' where upsw=" + upsw + "";
				int count2 = st.executeUpdate(sql2);

				System.out.println(count2 + "--" + upsw);

				if (recCount == count2) {
					transferStatus = "success";
				} else {
					transferStatus = "fail";
				}

			} else {
				transferStatus = "notexist";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return transferStatus;
	}

	public String changePassword(String upswold, String upswnew) {
		String status = null;
		try {
			Connection con = getConnection();
			Statement st = con.createStatement();

			ResultSet getData = getUserDetails(upswold);
			String uadhr = null;
			boolean b = getData.next();
			System.out.println("changePswIsExist= " + b);

			uadhr = getData.getString("uadhr");
			System.out.println("changePsw= " + uadhr);

			System.out.println("adhar =" + uadhr);
			if (b == true) {
				String sql = "update customer SET upsw='" + upswnew + "' where uadhr=" + uadhr + "";
				int count = st.executeUpdate(sql);
				if (count > 0) {
					status = "success";
				} else {
					status = "fail";
				}
			} else {
				status = "fail";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	public String deleteAccount(String upsw, String uadhr) {
		String deleteStatus = null;
		try {

			Connection con = getConnection();
			Statement st = con.createStatement();

			String sql = "delete from customer where upsw='" + upsw + "' and uadhr='" + uadhr + "'";
			int count = st.executeUpdate(sql);
			if (count > 0) {
				deleteStatus = "success";
				st.close();
				con.close();
			} else {
				deleteStatus = "fail";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return deleteStatus;
	}
}
