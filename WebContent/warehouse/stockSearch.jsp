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
<title>库存查询</title>
<link href="css/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="script/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="script/stockSearch.js"></script>
<script language="javascript" src="script/common.js"></script>
<!--  <script type="text/javascript" src="script/productDiv.js"></script>-->
<script type="text/javascript" src="script/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
	<div id="m">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" id="m">
			<tr>
				<td nowrap class="title1">您的位置：库存查询</td>
			</tr>
		</table>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="30px" nowrap class="toolbar">&nbsp;</td>
				<td width="40px" nowrap class="toolbar" onMouseOver="OMO(event)"
					onMouseOut="OMOU(event)" onclick="add()"></td>
				<td width="90px" nowrap class="toolbar" onMouseOver="OMO(event)"
					onMouseOut="OMOU(event)" onclick="deletech()">&nbsp;</td>
				<td nowrap class="toolbar">&nbsp;</td>
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
		<form action="" id="">
		<div class="query_div">
			产品编号：<input type="text" id="productCode" /> 
			产品名称：<input type="text" id="name" /> 
			库存数量范围：<input type="text" id="min" value="0">-<input type="text" id="max">
			<input type="button" value="查询" />
		</div>
		<table width="100%" border="0" align="center" cellspacing="1"
			class="c" id="products">
			<tr>
				<td class="title1">产品编号</td>
				<td class="title1">产品名称</td>
				<td class="title1">库存数量</td>
				<td class="title1">采购在途数</td>
				<td class="title1">销售待发数</td>
			</tr>
			<c:forEach items="${products }" var="p">
			<tr class="ptr">
				<td align="center"><a href="warehouse/stockDetail?productCode=${p.productCode }">${p.productCode }</a></td>
				<td align="center">${p.name }</td>
				<td align="center">${p.num }</td>
				<td align="center">${p.poNum }</td>
				<td align="center">${p.soNum }</td>
			</tr>
			</c:forEach>
		</table>
		<p align="center">共<span id="totalPage">${totalPage }</span>页，当前第<span id="nowPage">1</span>页 
			<select id="page">
			<c:forEach begin="1" end="${totalPage }" var="i"> 
				<option value=${i } class="page">${i }</option>
			</c:forEach>
			</select>
			<input type="button" value="跳转">
		</p>
		</form>
	</div>
</body>
</html>