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
import com.scm.model.Category;
import com.scm.service.CategoryService;

/**
 * Servlet implementation class CategoryUpdate
 */
@WebServlet({ "/CategoryUpdate", "/warehouse/categoryUpdate" })
public class CategoryUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(CategoryUpdate.class);

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CategoryUpdate() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String categoryId=request.getParameter("categoryId");
		String name=request.getParameter("name");
		String updateId=request.getParameter("updateId");
		String addName=request.getParameter("addName");
		String addRemark=request.getParameter("addRemark");
		String nowPage=request.getParameter("nowPage");
		try {
			ArrayList<Object> result=new CategoryService().update(categoryId, name, updateId, addName, addRemark, nowPage);
			String results=JSONArray.toJSONString(result);
			PrintWriter pw=response.getWriter();
			pw.print(results);
			pw.flush();
			pw.close();
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
