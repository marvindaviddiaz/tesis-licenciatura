package com.github.marvindaviddiaz;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.SessionCookieConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "/test" })
public class HomeServlet extends HttpServlet {

	public HomeServlet() {
		super();
		System.out.print("################## New Instance HomeServlet()");
	}

	private static final long serialVersionUID = -8205786946111596936L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		SessionCookieConfig sessionCookieConfig = req.getServletContext().getSessionCookieConfig();
//		String info = "Calling HomeServlet.doGet -- sessionCookieConfig name:" + sessionCookieConfig.getName() + " comment: " + sessionCookieConfig.getComment() + " dommain: "
//				+ sessionCookieConfig.getDomain() + " maxAge: " + sessionCookieConfig.getMaxAge() + " path: " + sessionCookieConfig.getPath() + " httpOnly: " + sessionCookieConfig.isHttpOnly()
//				+ " secure: " + sessionCookieConfig.isSecure();

//		System.out.println(info);

		resp.getOutputStream().write("Hello World".getBytes());
	}

}
