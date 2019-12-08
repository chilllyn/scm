<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<base href="<%=basePath%>">
<title>Category</title>
<link href="css/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="script/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="script/category.js"></script>
<script language="javascript" src="script/common.js"></script>
<!--  <script type="text/javascript" src="script/productDiv.js"></script>-->
<script type="text/javascript" src="script/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
	<div id="m">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" id="m">
			<tr>
				<td nowrap class="title1">您的位置：产品分类管理</td>
			</tr>
		</table>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="30px" nowrap class="toolbar">&nbsp;</td>
				<td width="40px" nowrap class="toolbar" onMouseOver="OMO(event)"
					onMouseOut="OMOU(event)" onClick="add()"><img
					src="images/new.gif">新增</td>
				<td nowrap class="toolbar">&nbsp;</td>
				<td width="60px" nowrap class="toolbar" onMouseOver="OMO(event)"
					onMouseOut="OMOU(event)">&nbsp;</td>
				<td width="20px" nowrap class="toolbar">&nbsp;</td>
				<td width="60px" nowrap class="toolbar" onMouseOver="OMO(event)"
					onMouseOut="OMOU(event)">&nbsp;</td>
				<td width="20px" nowrap class="toolbar">&nbsp;</td>
				<td width="60px" nowrap class="toolbar" onMouseOver="OMO(event)"
					onMouseOut="OMOU(event)">&nbsp;</td>
				<td width="20px" nowrap class="toolbar">&nbsp;</td>
				<td width="60px" nowrap class="toolbar" onMouseOver="OMO(event)"
					onMouseOut="OMOU(event)">&nbsp;</td>
				<td width="20px" nowrap class="toolbar">&nbsp;</td>
			</tr>
		</table>
		<form action="warehouse/category" id="category">
		<div class="query_div">
			产品分类序列号：<input type="text" name="categoryId" /> 
			产品分类名称：<input type="text" name="name" /> 
			<input type="button" value="查询" />
		</div>
		<table width="100%" border="0" align="center" cellspacing="1"
			class="c" id="categories">
			<tr>
				<td class="title1">序列号</td>
				<td class="title1">名称</td>
				<td class="title1">描述</td>
				<td class="title1">操作</td>
			</tr>
			<c:forEach items="${categories }" var="category">
			<tr class="ctr">
				<td align="center">${category.categoryId }</td>
				<td align="center">${category.name }</td>
				<td align="center">${category.remark }</td>
				<td align="center">
					<input type="button" value="修改" >
					<input type="button" value="删除" >
				</td>
			</tr>
			</c:forEach>
		</table>
		<p align="center">共<span id="totalPage">${totalPage }</span>页，当前第<span id="nowPage">${nowPage }</span>页 
			<select>
			<c:forEach begin="1" end="${totalPage }" var="i"> 
				<option value=${i }>${i }</option>
			</c:forEach>
			</select>
			<input type="button" value="跳转">
		</p>
		</form>
	</div>
	<div id="add" class="hidd">
		<form action="">
		<table width="100%" border="0" align="center" cellspacing="1"
			class="c">
			<tr>
				<td class="title1"></td>
				<td class="title1"></td>
			</tr>
			<tr>
				<td align="center">产品分类名称</td>
				<td align="left">
					<input type="text" id="addName" onkeyup="checkName()"/><br/>
					<span id="addNameTip"></span>
				</td>
			</tr>
			<tr>
				<td align="center">备注</td>
				<td align="left"><input type="text" id="addRemark"/></td>
			</tr>
			<tr>
				<td height="18" colspan="2" align="center">
					<input type="reset"/> <input type="button" value="保存"/>
				</td>
			</tr>
		</table>
		</form>
	</div>
</body>
</html>