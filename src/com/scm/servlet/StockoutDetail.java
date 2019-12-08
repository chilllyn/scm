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

import com.scm.model.SO;
import com.scm.model.Soitem;
import com.scm.service.StockoutService;

/**
 * Servlet implementation class StockoutDetail
 */
@WebServlet({ "/StockoutDetail", "/warehouse/stockoutDetail" })
public class StockoutDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(StockoutDetail.class);

    /**
     * @see HttpServlet#HttpServlet()
     */
    public StockoutDetail() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String soid=request.getParameter("soid");
		try {
			ArrayList<Object> result=new StockoutService().detail(Long.parseLong(soid));
			SO so=(SO) result.get(0);
			ArrayList<Soitem> soitems=(ArrayList<Soitem>) result.get(1);
			request.setAttribute("so", so);
			request.setAttribute("soitems", soitems);
			request.getRequestDispatcher("/warehouse/stockoutDetail.jsp").forward(request, response);
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
