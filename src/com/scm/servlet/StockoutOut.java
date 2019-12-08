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
import com.scm.model.User;
import com.scm.service.StockoutService;

/**
 * Servlet implementation class StockoutOut
 */
@WebServlet({ "/StockoutOut", "/warehouse/stockoutOut" })
public class StockoutOut extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(StockoutOut.class);

    /**
     * @see HttpServlet#HttpServlet()
     */
    public StockoutOut() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String soid=request.getParameter("soid");
		String nowPage=request.getParameter("nowPage");
		String payType0=request.getParameter("payType0");
		String userAccount=((User)request.getSession().getAttribute("user")).getAccount();
		try {
			ArrayList<Object> result=new StockoutService().out(Long.parseLong(soid), userAccount, Integer.parseInt(nowPage), Integer.parseInt(payType0));
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
