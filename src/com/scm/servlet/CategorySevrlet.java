package com.scm.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.scm.service.CategoryService;
/**
 * Servlet implementation class CategorySevrlet
 */
@WebServlet({ "/CategorySevrlet", "/warehouse/category" })
public class CategorySevrlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(CategorySevrlet.class);
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CategorySevrlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ArrayList<Object> result=new CategoryService().first();
			request.setAttribute("categories", result.get(0));
			request.setAttribute("totalPage", result.get(1));
			request.setAttribute("nowPage", result.get(2));
			request.getRequestDispatcher("/warehouse/category.jsp").forward(request, response);
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
