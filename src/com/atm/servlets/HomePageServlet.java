package com.atm.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.atm.dblayer.HomePageDBLayer;
import com.atm.servicelayer.HomeServiceLayer;

@WebServlet("/home")
public class HomePageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String key = request.getParameter("key");
		if (key.equals("depositkey")) {
			String upsw2 = request.getParameter("upsw");
			int depositBal = Integer.parseInt(request.getParameter("depositBal"));

			HttpSession httpSession = request.getSession(false);
			String upsw1 = (String) httpSession.getAttribute("upsw");

			HomeServiceLayer service = new HomeServiceLayer();

			if (upsw1.equals(upsw2)) {

				String depStatus = service.checkDepositAmountValidOrNot(upsw2, depositBal);
				if (depStatus.equals("invalid")) {
					out.println("<html>");
					out.println("<body bgcolor='OldLace'><br><br><br>");
					out.println("<h1 style='color:red' align='center'>INVALID AMOUNT</h1>");
					out.println("<h3 style='color:white'><a hresf='./depositform.html'>|Retry|</a></h3>");
					out.println("</body></html>");
				}
				if (depStatus.equals("fail")) {
					out.println("<html>");
					out.println("<body bgcolor='OldLace'><br><br><br>");
					out.println("<h1 style='color:red' align='center'>DEPOSIT FAILED</h1>");
					out.println("<h3 style='color:white'><a hresf='./depositform.html'>|Retry|</a></h3>");
					out.println("</body></html>");

				}
				if (depStatus.equals("success")) {
					out.println("<html>");
					out.println("<body bgcolor='OldLace'><br><br><br>");
					out.println("<h1 style='color:green' align='center'>DEPOSIT SUCCESS</h1>");
					out.println("<h3 style='color:white'><a hresf='./depositform.html'>|Retry|</a></h3>");
					out.println("</body></html>");

				}
			} else {
				out.println("<html>");
				out.println("<body bgcolor='OldLace'><br><br><br>");
				out.println("<h1 style='color:red' align='center'>INCORRECT PASSWORD</h1>");
				out.println("<h3 style='color:white'><a hresf='./depositform.html'>|Retry|</a></h3>");
				out.println("</body></html>");
			}

		} else if (key.equals("checkkey")) {

			String upsw1 = request.getParameter("upsw");

			HomeServiceLayer service = new HomeServiceLayer();
			int balance = service.getBalance(upsw1);

			HttpSession httpSession = request.getSession(false);
			String upsw2 = (String) httpSession.getAttribute("upsw");

			if (balance >= 0 && upsw1.equals(upsw2)) {
				out.println("<html>");
				out.println("<body bgcolor='oldlace'><br><br><br>");
				out.println(
						"<h1 style='color:darkred' align='center'>Clear Balance :" + balance + "</h1></body></html>");

			} else {
				out.println("<html>");
				out.println("<body><br><br>");
				out.println("<h1 style='color:red' align='center'>INCORRECT PASSWORD</h1>");
				out.println("<h3 align='center'><a href='./checkbalance.html'>|Retry|</a></h3>");
			}
		} else if (key.equals("withdrawkey")) {
			String upsw2 = request.getParameter("upsw");
			String bal = request.getParameter("withdrawBal");

			if (bal.equals(null) || bal.equals("")) {
				out.println("<html>");
				out.println("<body>");
				out.println("<h1 style='color:red' align='center'> INVALID AMOUNT</h1>");
				out.println("</body></html>");

			}
			int withdrawBal = Integer.parseInt(request.getParameter("withdrawBal"));

			HttpSession httpSession = request.getSession(false);
			String upsw1 = (String) httpSession.getAttribute("upsw");

			if (upsw1.equals(upsw2)) {
				HomeServiceLayer service = new HomeServiceLayer();
				String depStatus = service.checkWithdrawAmount(upsw2, withdrawBal);
				if (depStatus.equals("invalid")) {
					out.println("<html>");
					out.println("<body bgcolor='oldlace'><br><br><br>");
					out.println("<h1 style='color:red' align='center'>YOU ENTERED INVALID AMOUNT</h1>");
					out.println("<h3 align='center'style='color:white'><a hresf='./depositform.html'>|Retry|</a></h3>");
					out.println("</body></html>");
				}
				if (depStatus.equals("fail")) {
					out.println("<html>");
					out.println("<body bgcolor='oldlace'><br><br><br>");
					out.println("<h1 style='color:red' align='center'>PROCES FAILED</h1>");
					out.println("<h3 align='center'style='color:white'><a hresf='./depositform.html'>|Retry|</a></h3>");
					out.println("</body></html>");

				}
				if (depStatus.equals("success")) {
					out.println("<html>");
					out.println("<body bgcolor='oldlace'><br><br><br>");
					out.println("<h1 style='color:green' align='center'>WITHDRAW SUCCESS</h1>");
					out.println("<h3 align='center'style='color:white'><a hresf='./depositform.html'>|Retry|</a></h3>");
					out.println("</body></html>");

				}
			} else {
				out.println("<html>");
				out.println("<body bgcolor='oldlace'><br><br><br>");
				out.println("<h1 style='color:red' align='center'>INCORRECT PASSWORD</h1>");
				out.println("<h3 align='center'style='color:white'><a hresf='./depositform.html'>|Retry|</a></h3>");
				out.println("</body></html>");
			}

		}

		else if (key.equals("transferkey")) {
			String uname = request.getParameter("uname");
			String uadhr = request.getParameter("uadhr");
			String upsw1 = request.getParameter("upsw");
			int transferBal = Integer.parseInt(request.getParameter("transferBal"));

			HttpSession httpSession = request.getSession();
			String upsw2 = (String) httpSession.getAttribute("upsw");

			if (upsw1.equals(upsw2)) {
				HomeServiceLayer service = new HomeServiceLayer();
				String transferStatus = service.transferAmount(uname, uadhr, transferBal, upsw1);

				if (transferStatus.equals("invalid-input")) {
					out.println("<html>");
					out.println("<body bgcolor='oldlace'><br><br><br>");
					out.println("<h1 style='color:red' align='center'>YOU HAVE TO FILL ALL DETAILS CORRECTLY </h1>");
					out.println("<h3 style='color:white'><a href='./transfer.html'>|Retry|</a></h3>");
					out.println("</body></html>");

				}
				if (transferStatus.equals("invalid")) {
					out.println("<html>");
					out.println("<body bgcolor='oldlace'><br><br><br>");
					out.println("<h1 style='color:red' align='center'>AMOUNT IS NOT AVAILABLE</h1>");
					out.println("<h3 style='color:white'><a href='./transfer.html'>|Retry|</a></h3>");
					out.println("</body></html>");

				}
				if (transferStatus.equals("notexist")) {
					out.println("<html>");
					out.println("<body bgcolor='oldlace'><br><br><br>");
					out.println("<h1 style='color:red' align='center'>INVALID ACCOUNT</h1>");
					out.println("<h3 style='color:white'><a href='./transfer.html'>|Retry|</a></h3>");
					out.println("</body></html>");
				}
				if (transferStatus.equals("fail")) {
					out.println("<html>");
					out.println("<body bgcolor='oldlace'><br><br><br>");
					out.println("<h1 style='color:red' align='center'>YOUR TRANSACTION FAILED</h1>");
					out.println("<h3 style='color:white'><a href='./transfer.html'>|Retry|</a></h3>");
					out.println("</body></html>");
				}
				if (transferStatus.equals("success")) {
					out.println("<html>");
					out.println("<body bgcolor='oldlace'><br><br><br>");
					out.println("<h1 style='color:green' align='center'>SUCCESSFULLY TRANSFERED</h1>");
					// out.println("<h3 style='color:white'><a
					// href='./checkbalance.html'>|checkbalance|</a></h3>");
					out.println("</body></html>");

				}
			} else {
				out.println("<html>");
				out.println("<body bgcolor='oldlace'><br><br><br>");
				out.println("<h1 style='color:red' align='center'>PLEASE ENTER CORRECT PASSWORD</h1>");
				out.println("<h3 style='color:balck'align='center'><a href='./transferform.html'>|Retry|</a></h3>");
				out.println("</body></html>");

			}

		} else if (key.equals("changepswkey")) {
			String upswold1 = request.getParameter("upswold");
			String upswnew1 = request.getParameter("upswnew1");
			String upswnew2 = request.getParameter("upswnew2");

			if (upswnew1.equals(upswnew2)) {
				HttpSession httpSession = request.getSession();
				String upswold2 = (String) httpSession.getAttribute("upsw");
				System.out.println(upswold1 + "-" + upswold2);
				if (upswold1.equals(upswold2)) {
					System.out.println(upswold1 + "-" + upswold2);
					HomeServiceLayer service = new HomeServiceLayer();
					String changePswStatus = service.checkChangePassword(upswold1, upswnew1);

					if (changePswStatus.equals("fail")) {
						out.println("<html>");
						out.println("<body bgcolor='OldLace'><br><br><br>");
						out.println("<h1 align='center'style='color:red'>Something Went Wrong</h1>");
						out.println(
								"<h3 align='center'style='color:white'><a hresf='./changepsw.html'>|Retry|</a></h3>");
						out.println("</body></html>");
					}
					if (changePswStatus.equals("success")) {
						out.println("<html>");
						out.println("<body bgcolor='OldLace'><br><br><br>");
						out.println("<h1 align='center'style='color:green'>YOUR PASSWORD SUCCESSFULLY UPDATED</h1>");
						out.println("<h3 align='center'style='color:white'><a hresf='./login.html'>|Retry|</a></h3>");
						out.println("</body></html>");

						httpSession.setAttribute("upsw", upswnew1);
					}
				} else {
					out.println("<html>");
					out.println("<body bgcolor='OldLace'><br><br><br>");
					out.println("<h1 align='center'style='color:red'>INCORRECT OLD PASSWORD</h1>");
					out.println("<h3 align='center'style='color:white'><a hresf='./changepsw.html'>|Retry|</a></h3>");
					out.println("</body></html>");

				}
			} else {
				out.println("<html>");
				out.println("<body bgcolor='OldLace'><br><br><br>");
				out.println("<h1 align='center'style='color:red'>MISMATCHING NEW PASSWORDS</h1>");
				out.println("<h3 align='center'style='color:white'><a hresf='./changepsw.html'>|Retry|</a></h3>");
				out.println("</body></html>");
			}
		} else if (key.equals("deletekey1")) {
			String upsw = request.getParameter("upsw");
			String uadhr = request.getParameter("uadhr");

			HttpSession httpSession = request.getSession();
			String upsw2 = (String) httpSession.getAttribute("upsw");
			httpSession.setAttribute("uadhr", uadhr);

			System.out.println("upsw " + upsw2);

			if (upsw.equals(upsw2)) {

				HomeServiceLayer service = new HomeServiceLayer();
				String checkDeleteAcc = service.checkDeleteAccountData(upsw, uadhr);

				if (checkDeleteAcc.equals("invalid")) {
					out.println("<html>");
					out.println("<body><br><br><br>");
					out.println("<h1 style='color:red' align='center'>INSERT VALID DATA</h1>");
					out.println("<h3 style='color:black'><a href='./changepswform.html'>|Retry|</a></h3>");
					out.println("</body></html>");
				}
				if (checkDeleteAcc.equals("notexist")) {
					out.println("<html>");
					out.println("<body><br><br><br>");
					out.println("<h1 style='color:red' align='center'>INCORRECT DATA </h1>");
					out.println("<h3 style='color:black'><a hresf='./changepswform.html'>|Retry|</a></h3>");
					out.println("</body></html>");
				}

				RequestDispatcher requestDispatcher;

				if (checkDeleteAcc.equals("exist")) {
					requestDispatcher = request.getRequestDispatcher("/deleteverify.html");
					requestDispatcher.forward(request, response);
				}
			} else {
				out.println("<html>");
				out.println("<body>");
				out.println("<h1 style='color:red' align='center'>INCORRECT PASSWORD</h1>");
				out.println("<h3 style='color:black'><a hresf='./changepswform.html'>|Retry|</a></h3>");
				out.println("</body></html>");

			}
		} else if (key.equals("deletekey2")) {
			HttpSession httpSession = request.getSession();
			String uadhr = (String) httpSession.getAttribute("uadhr");
			String upsw = (String) httpSession.getAttribute("upsw");

			HomePageDBLayer dblayer = new HomePageDBLayer();
			String deleteStatus = dblayer.deleteAccount(upsw, uadhr);

			if (deleteStatus.equals("success")) {
				out.println("<html>");
				out.println("<body bgcolor='OldLace' ><br><br><br>");
				out.println("<h1 style='color:green' align='center'>YOUR ACCOUNT WILL BE DELETED SOON..</h1>");
				out.println(
						"<h3 style='color:black' align='center'><a href='./loginform.html'><input type='submit' target='body' value='EXIT'></a></h3>");
				out.println("</body></html>");

			}

			if (deleteStatus.equals("fail")) {
				out.println("<html>");
				out.println("<body bgcolor='OldLace'><br><br><br>");
				out.println("<h1 style='color:red' align='center'>PROCESS FAILED</h1>");
				out.println("<h3 style='color:black'><a href='./deleteaccform.html>|Tryagain|</a></h3>");
				out.println("</body></html>");

			}
		}

	}
}