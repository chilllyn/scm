package com.scm.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.scm.service.CategoryService;

/**
 * Servlet implementation class CategoryCheckName
 */
@WebServlet({ "/CategoryCheckName", "/warehouse/categoryCheckName" })
public class CategoryCheckName extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(CategoryCheckName.class);  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CategoryCheckName() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String addName=request.getParameter("addName");
		String result="";
		try {
			if(new CategoryService().checkName(addName)) {
				result="产品分类名称可用";
			}else {
				result="产品分类名称重复";
			}
			String results=JSONObject.toJSONString(result);
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
