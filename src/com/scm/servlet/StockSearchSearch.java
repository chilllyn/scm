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
import com.scm.service.StockSearchService;

/**
 * Servlet implementation class StockSearchSearch
 */
@WebServlet({ "/StockSearchSearch", "/warehouse/stockSearchSearch" })
public class StockSearchSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(StockSearchSearch.class);

    /**
     * @see HttpServlet#HttpServlet()
     */
    public StockSearchSearch() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String productCode=request.getParameter("productCode");
		String name=request.getParameter("name");
		String min=request.getParameter("min");
		String max=request.getParameter("max");
		try {
			ArrayList<Object> result=new StockSearchService().search(new Product(productCode, name, min, max));
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
