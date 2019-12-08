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

import com.scm.model.Category;
import com.scm.model.Product;
import com.scm.service.StockSearchService;

/**
 * Servlet implementation class StockSearch
 */
@WebServlet({ "/StockSearch", "/warehouse/stockSearch" })
public class StockSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(StockSearch.class);
 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StockSearch() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ArrayList<Object> result=new StockSearchService().first();
			ArrayList<Product> products=(ArrayList<Product>) result.get(0);
			int totalPage=(int) result.get(1);
			request.setAttribute("products", products);
			request.setAttribute("totalPage", totalPage);
			request.getRequestDispatcher("/warehouse/stockSearch.jsp").forward(request, response);
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
