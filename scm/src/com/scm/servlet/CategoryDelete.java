package com.scm.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.scm.service.CategoryService;

/**
 * Servlet implementation class CategoryDelete
 */
@WebServlet({ "/CategoryDelete", "/warehouse/categoryDelete" })
public class CategoryDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(CategoryDelete.class);
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CategoryDelete() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String categoryId=request.getParameter("categoryId");
		String nowPage=request.getParameter("nowPage");
		String searchId=request.getParameter("searchId");
		String searchName=request.getParameter("searchName");
		ArrayList<Object> result=new ArrayList<Object>();
		try {
			result = new CategoryService().delete(categoryId, nowPage,searchId,searchName);
		} catch (NumberFormatException e) {
			LOGGER.error("error", e);
		} catch (SQLException e) {
			LOGGER.error("error", e);
		}
		String results=JSONArray.toJSONString(result);
		PrintWriter pw=response.getWriter();
		pw.print(results);
		pw.flush();
		pw.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
