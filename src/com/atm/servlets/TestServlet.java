package com.atm.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
		public void init() throws ServletException {
			System.out.println("Servlet Initilization");
		}
	@Override
		protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			// TODO Auto-generated method stub
			super.service(req, resp);
			System.out.println("Servlet processing");
			destroy();
		}
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
		System.out.println("Servlet destroy");
	}
}
