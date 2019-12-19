package com.scm.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.scm.model.User;
import com.scm.service.LoginService;
/**
 * Servlet implementation class LoginServlet
 */
@WebServlet({ "/LoginServlet", "/log/in" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(LoginServlet.class);

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String account=request.getParameter("account");
		String password=request.getParameter("password");
		try {
			User user=new LoginService().getUser(account, password);
			if(user==null) {
				request.setAttribute("error", "账号或密码错误");
				request.getRequestDispatcher("/login.jsp").forward(request, response);
			}else {
				if(user.getStatus()==1) {
					request.setAttribute("error", "账号已被锁定");
					request.getRequestDispatcher("/login.jsp").forward(request, response);
				}else {
					request.getSession().setAttribute("user", user);
					response.sendRedirect(request.getContextPath()+"/index.jsp");
				}
			}
		} catch (SQLException e) {
			LOGGER.error("error", e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
