package com.scm.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

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
 * Servlet implementation class ProductAddServlet
 */
@WebServlet({ "/ProductAddServlet", "/warehouse/productAdd" })
public class ProductAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(ProductAddServlet.class);

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductAddServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String productCode=request.getParameter("productCode");
		String name=request.getParameter("name");
		String categoryId=request.getParameter("categoryId");
		String price=request.getParameter("price");
		String unitName=request.getParameter("unitName");
		String createDate=request.getParameter("createDate");
		String remark=request.getParameter("remark");
		HashMap<String, String> p=new HashMap<String, String>();
		p.put("productCode", productCode);
		p.put("name", name);
		p.put("categoryId",categoryId );
		p.put("price",price );
		p.put("unitName",unitName );
		p.put("createDate",createDate );
		p.put("remark", remark);
		try {
			ArrayList<Object> result=new ProductService().add(p);
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
