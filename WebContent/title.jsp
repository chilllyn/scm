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
<title>TITLE</title>
<link href="css/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="script/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="script/common.js"></script>
<!-- <script type="text/javascript">
	function closeAlert() {
		question = confirm("您要退出系统，确定吗？");
		if (question != "0"){
			parent.close();
		}
	}
</script> -->
</head>
<!-- <body topmargin=0 leftmargin=0>
	<table width="100%" border="0" align="right" cellpadding="0" cellspacing="0" height="25">
	  <tr>
		<td class="toolbar">&nbsp;</td>
		<td class="toolbar" width="45px" onMouseOver="OMO()" onMouseOut="OMOU()" onClick="window.open('temp.htm', 'mainFrame')">
		  <img src="images/jrxt.jpg" border="0">登录</td>
		<td width="20px" nowrap class="toolbar">|</td>
		<td class="toolbar" width="45px" onMouseOver="OMO()" onMouseOut="OMOU()" onClick="closeAlert()">
		  <img src="images/lkxt.jpg" border="0">离开</td>
		<td width="20px" nowrap class="toolbar">|</td>
	  </tr>
	</table>
</body> -->
<body>
	<table width="100%" border="0" align="right" cellpadding="0" cellspacing="0" height="25">
	  <tr>
		<td class="toolbar">&nbsp;</td>
		<td class="toolbar" width="60px">
		<c:if test="${empty user }">
			<img src="images/jrxt.jpg" border="0"><a href="login.jsp" target="_parent">登录</a>
		</c:if>
		<c:if test="${not empty user }">
			<img src="images/jrxt.jpg" border="0">${user.name }
		</c:if>
		 </td>
		<td width="20px" nowrap class="toolbar">|</td>
		<td class="toolbar" width="45px">
		<c:if test="${empty user }">
			<img src="images/lkxt.jpg" border="0">离开
		</c:if>
		<c:if test="${not empty user }">
			<img src="images/lkxt.jpg" border="0"><a href="log/out">离开</a>
		</c:if>
		 </td>
		<td width="20px" nowrap class="toolbar">|</td>
	  </tr>
	</table>
</body>
</html>