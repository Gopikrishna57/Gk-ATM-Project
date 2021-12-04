package com.atm.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.atm.servicelayer.RegServiceLayer;

@WebServlet("/reg")
public class RegServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String uname = request.getParameter("uname");
		String uadhr = request.getParameter("uadhr");
		String upsw = request.getParameter("upsw");
		String upsw2 = request.getParameter("upsw2");
		int ubalance = Integer.parseInt(request.getParameter("ubalance"));

		HttpSession httpSession = request.getSession();
		httpSession.setAttribute("uname", uname);
		httpSession.setAttribute("uadhr", uadhr);
		httpSession.setAttribute("upsw", upsw);
		httpSession.setMaxInactiveInterval(ubalance);

		RequestDispatcher requestDispatcher;
		if (upsw.equals(upsw2)) {
			RegServiceLayer serviceLayer = new RegServiceLayer();
			String regStatus = serviceLayer.insertNewUser(uname, upsw, uadhr, ubalance);

			if (regStatus.equals("invalid")) {
				requestDispatcher = request.getRequestDispatcher("/invalid.html");
				requestDispatcher.forward(request, response);
			}
			if (regStatus.equals("failure")) {
				requestDispatcher = request.getRequestDispatcher("/failure.html");
				requestDispatcher.forward(request, response);

			}
			if (regStatus.equals("success")) {
				requestDispatcher = request.getRequestDispatcher("/success.html");
				requestDispatcher.forward(request, response);
			}
			if (regStatus.equals("exist")) {
				requestDispatcher = request.getRequestDispatcher("/userexist.html");
				requestDispatcher.forward(request, response);

			}
		} else {
			requestDispatcher = request.getRequestDispatcher("/pswmatch.html");
			requestDispatcher.forward(request, response);

		}

	}

}
