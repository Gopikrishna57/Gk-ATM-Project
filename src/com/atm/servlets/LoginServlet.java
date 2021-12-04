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
import com.atm.servicelayer.LoginServiceLayer;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String uname = request.getParameter("uname");
		String upsw = request.getParameter("upsw");

		HttpSession httpSession = request.getSession();
		httpSession.setAttribute("uname", uname);
		httpSession.setAttribute("upsw", upsw);

		LoginServiceLayer service = new LoginServiceLayer();
		String loginStatus = service.loginCheck(uname, upsw);

		if (loginStatus.equals("invalid")) {
			out.println("<html>");
			out.println("<body bgcolor='OldLace'>");
			out.println(
					"<br><br><br><h2 style='color:red' align='center'>	PLEASE ENTER VALID USERNAME AND PASSWORD</h2>");
			out.println("<br><br><h3 align='center'><a  href='./login.html'>|login|</a></h3>");
			out.println("</body></html>");
		}

		if (loginStatus.equals("notexist")) {
			out.println("<htm>");
			out.println("<body bgcolor='OldLace'>");
			out.println("<br><br><br><h1 style='color:red' align='center'>INVALID ACCOUNT</h1>");
			// out.println("<br><br><a href='./loginform.html'>|login|</a>");
			out.println(
					"<h3 align='center'><a href='./registration.html'>|NewUser|  </a><a href='./loginform.html'>|login|</a></h3></body></html>");

		}

		RequestDispatcher reqDisp;

		if (loginStatus.equals("exist")) {
			reqDisp = request.getRequestDispatcher("/homepage.html");
			reqDisp.forward(request, response);
		}

	}

}
