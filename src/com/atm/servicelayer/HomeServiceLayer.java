package com.atm.servicelayer;

import java.sql.ResultSet;
import com.atm.dblayer.HomePageDBLayer;

public class HomeServiceLayer {
	HomePageDBLayer dblayer = new HomePageDBLayer();

	public int getBalance(String upsw) {
		int Balance = 0;
		ResultSet rs = null;

		try {
			if (!(upsw == null || upsw == "")) {

				rs = dblayer.getUserDetails(upsw);
				while (rs.next()) {
					rs.getString("uname");
					rs.getString("upsw");
					rs.getString("uadhr");
					Balance = rs.getInt("ubalance");
				}

			}
			if (rs == null) {
				Balance = -1;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		// System.out.println("Balance : "+totalBalance);

		return Balance;
	}

	public String checkDepositAmountValidOrNot(String upsw, int depositBal) {
		String depositStatus = null;
		try {
			// UserDBLayer dblayer=new UserDBLayer();
			ResultSet getD = dblayer.getUserDetails(upsw);

			int ubalance = 0;
			while (getD.next()) {
				ubalance = getD.getInt("ubalance");
			}
			System.out.println("UBalance : " + ubalance);

			if (depositBal > 0) {
				int rowCount = dblayer.depositAmount(upsw, depositBal);
				System.out.println("DepositCount :" + rowCount);

				if (rowCount == 1) {
					depositStatus = "success";
				} else {
					depositStatus = "fail";
				}

			} else {
				depositStatus = "invalid";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return depositStatus;
	}

	public String checkWithdrawAmount(String upsw, int withdrawBal) {
		String withdrawStatus = null;
		try {
			HomePageDBLayer dblayer = new HomePageDBLayer();
			ResultSet getD = dblayer.getUserDetails(upsw);
			int ubalance = 0;
			while (getD.next()) {
				ubalance = getD.getInt("ubalance");
			}

			if (ubalance >= withdrawBal && withdrawBal > 0) {
				int rowCount = dblayer.withdrawAmount(upsw, withdrawBal);
				System.out.println("DepositCount :" + rowCount);

				if (rowCount == 1) {
					withdrawStatus = "success";
				} else {
					withdrawStatus = "fail";
				}

			} else {
				withdrawStatus = "invalid";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return withdrawStatus;

	}

	public String transferAmount(String uname, String uadhr, int transferBal, String upsw) {
		String transactionStatus = null;
		System.out.println(uname + "--" + uadhr + "--" + transferBal + "--" + upsw);
		try {
			if (!((uname == null || uname == "") && (uadhr == null || uadhr == "") && (upsw == null || upsw == ""))) {
				int myBal = getBalance(upsw);
				if (myBal >= transferBal && transferBal > 0) {
					System.out.println(myBal + "-" + uname + "-" + "-" + uadhr + "-" + upsw);
					HomePageDBLayer dblayer = new HomePageDBLayer();
					transactionStatus = dblayer.checkReceiverAndTransfer(uname, uadhr, transferBal, upsw);

				} else {
					transactionStatus = "invalid";
				}
			} else {
				transactionStatus = "invalid-input";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return transactionStatus;
	}

	public String checkChangePassword(String upswold, String upswnew) {
		String changePswStatus = null;
		try {
			HomePageDBLayer dblayer = new HomePageDBLayer();
			changePswStatus = dblayer.changePassword(upswold, upswnew);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return changePswStatus;
	}

	public String checkDeleteAccountData(String upsw, String uadhr) {
		String status = null;
		try {
			if (!(uadhr.equals(null) || uadhr.equals(""))) {
				HomePageDBLayer dblayer = new HomePageDBLayer();
				ResultSet getData = dblayer.getUserDetails(upsw);
				String uadhr2 = null;
				while (getData.next()) {
					uadhr2 = getData.getString("uadhr");
				}
				System.out.println("deletAdhrstatus " + uadhr2);
				if (uadhr2.equals(uadhr)) {
					status = "exist";
				} else {
					status = "notexist";
				}
				System.out.println("Deletestatus " + status);
			} else {
				status = "invalid";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

}