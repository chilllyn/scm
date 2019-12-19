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
import com.scm.service.ProductService;

/**
 * Servlet implementation class ProductServlet
 */
@WebServlet({ "/ProductServlet", "/warehouse/product" })
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(ProductServlet.class);
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ArrayList<Object> result=new ProductService().first();
			ArrayList<Product> products=(ArrayList<Product>) result.get(0);
			ArrayList<Category> categories=(ArrayList<Category>) result.get(2);
			int totalPage=(int) result.get(1);
			request.setAttribute("products", products);
			request.setAttribute("totalPage", totalPage);
			request.setAttribute("categories", categories);
			request.getRequestDispatcher("/warehouse/product.jsp").forward(request, response);
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
