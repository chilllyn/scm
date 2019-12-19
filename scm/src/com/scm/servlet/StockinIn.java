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
import com.scm.service.StockinService;

/**
 * Servlet implementation class StockinIn
 */
@WebServlet({ "/StockinIn", "/warehouse/stockinIn" })
public class StockinIn extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(StockinIn.class);

    /**
     * @see HttpServlet#HttpServlet()
     */
    public StockinIn() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String poid=request.getParameter("poid");
		String nowPage=request.getParameter("nowPage");
		String payType0=request.getParameter("payType0");
		String userAccount=((User)request.getSession().getAttribute("user")).getAccount();
		try {
			ArrayList<Object> result=new StockinService().in(Long.parseLong(poid), userAccount, Integer.parseInt(nowPage), Integer.parseInt(payType0));
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
