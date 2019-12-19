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

import com.scm.model.PO;
import com.scm.service.StockinService;
/**
 * Servlet implementation class StockinServlet
 */
@WebServlet({ "/StockinServlet", "/warehouse/stockin" })
public class StockinServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(StockinServlet.class);

    /**
     * @see HttpServlet#HttpServlet()
     */
    public StockinServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ArrayList<Object> result=new StockinService().first();
			ArrayList<PO> pos=(ArrayList<PO>) result.get(0);
			int totalPage=(int) result.get(1);
			request.setAttribute("pos", pos);
			request.setAttribute("totalPage", totalPage);
			request.getRequestDispatcher("/warehouse/stockin.jsp").forward(request, response);
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
