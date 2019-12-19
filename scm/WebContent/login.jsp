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
<title>登录</title>
<link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body bgcolor="#ffffff" >
	<form action="log/in">
		<table width="100%"  border="0"  >
			<tr>
				<td class="title1">&nbsp;</td>
			</tr>
		  <tr>
			<td width="50%" align="right">用户名&nbsp;&nbsp;</td>
			<td width="50%"><input type="text" name="account"/></td>
		  </tr>
		  <tr>
			<td align="right">密码&nbsp;&nbsp;&nbsp;</td>
			<td><input type="password" name="password"/></td>
		  </tr>
		  <tr>
			<td class="title1"><div align="right"><input type="submit" value="登录"/></div></td>
			<td class="title1"><div align="left"><input type="reset" value="重置"/></div></td>
		  </tr>
		</table>
		<p style="color:red" align="center">${error }</p>
	</form>
</body>
</html>