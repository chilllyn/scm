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
import com.scm.model.Poitem;
import com.scm.service.StockinService;

/**
 * Servlet implementation class StockinDetailServlet
 */
@WebServlet({ "/StockinDetailServlet", "/warehouse/stockinDetail" })
public class StockinDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(StockinDetailServlet.class);

    /**
     * @see HttpServlet#HttpServlet()
     */
    public StockinDetailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String poid=request.getParameter("poid");
		try {
			ArrayList<Object> result=new StockinService().detail(Long.parseLong(poid));
			PO po=(PO) result.get(0);
			ArrayList<Poitem> poitems=(ArrayList<Poitem>) result.get(1);
			request.setAttribute("po", po);
			request.setAttribute("poitems", poitems);
			request.getRequestDispatcher("/warehouse/stockinDetail.jsp").forward(request, response);
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
