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
import com.scm.model.Product;
import com.scm.service.ProductService;

/**
 * Servlet implementation class ProductDel
 */
@WebServlet({ "/ProductDel", "/warehouse/productDel" })
public class ProductDel extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(ProductDel.class);

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductDel() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String searchProductCode=request.getParameter("searchProductCode");
		String searchName=request.getParameter("searchName");
		String searchCategory=request.getParameter("searchCategory");
		String nowPage=request.getParameter("nowPage");
		Product p1=new Product(searchProductCode, searchCategory, searchName);
		String productCode=request.getParameter("productCode");
		try {
			ArrayList<Object> result=new ProductService().delete(productCode, p1, nowPage);
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
