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
import com.scm.service.StockinMonthService;

/**
 * Servlet implementation class StockinMonthTo
 */
@WebServlet({ "/StockinMonthTo", "/warehouse/stockinMonthTo" })
public class StockinMonthTo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(StockinMonthTo.class);

    /**
     * @see HttpServlet#HttpServlet()
     */
    public StockinMonthTo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String month=request.getParameter("month");
		String toPage=request.getParameter("toPage");
		try {
			ArrayList<Object> result=new StockinMonthService().search(month,Integer.parseInt(toPage));
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
