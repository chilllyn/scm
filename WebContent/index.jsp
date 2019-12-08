<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<base href="<%=basePath %>" >
<title>SCM</title>
</head>
<frameset rows="23,10,*" cols="*" frameborder="NO" border="0" framespacing="0" id="controlRv">
  <frame src="title.jsp" name="topFrame" scrolling="NO" noresize >
  <frame src="dynamic_bar_h.htm" scrolling="no" name="sidebar_r" noresize>
  <frameset cols="120,10,*" frameborder="NO" border="0" framespacing="0"  id="controlFv">
    <frame src="catalog.htm" name="leftFrame" scrolling="NO" noresize>
	<frame src="dynamic_bar_v.htm" scrolling="no"  name="sidebar_v" noresize>
    <frame src="" name="mainFrame" scrolling="auto">
  </frameset>
</frameset>
<noframes></noframes>
</html>