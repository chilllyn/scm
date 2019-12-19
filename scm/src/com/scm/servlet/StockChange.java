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

import com.scm.model.StockRecord;
import com.scm.service.StockChangeService;
/**
 * Servlet implementation class StockChange
 */
@WebServlet({ "/StockChange", "/warehouse/stockChange" })
public class StockChange extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(StockChange.class);

    /**
     * @see HttpServlet#HttpServlet()
     */
    public StockChange() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String productCode=request.getParameter("productCode");
		String name=request.getParameter("name");
		try {
			ArrayList<Object> result=new StockChangeService().first(productCode);
			ArrayList<StockRecord> srs=(ArrayList<StockRecord>) result.get(0);
			int totalPage=(int) result.get(1);
			request.setAttribute("productCode", productCode);
			request.setAttribute("name", name);
			request.setAttribute("srs", srs);
			request.setAttribute("totalPage", totalPage);
			request.getRequestDispatcher("/warehouse/stockChange.jsp").forward(request, response);
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
