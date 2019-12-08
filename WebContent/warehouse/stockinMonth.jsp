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
<title>月度入库报表</title>
<link href="css/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="script/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="script/stockinMonth.js"></script>
<script language="javascript" src="script/common.js"></script>
<!--  <script type="text/javascript" src="script/productDiv.js"></script>-->
<script type="text/javascript" src="script/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
	<div id="m">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" id="m">
			<tr>
				<td nowrap class="title1">业务报表--月度入库报表</td>
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
			日期-年月：<input type="month" id="month" /> 
			<input type="button" value="查询" />
		</div>
		<p>入库单据数：<span id="orderNum">0</span></p>
		<p>入库产品总数量：<span id="numTotal">0</span></p>
		<p>入库产品总金额：<span id="priceTotal">0</span></p>
		<table width="100%" border="0" align="center" cellspacing="1"
			class="c" id="srs">
			<tr>
				<td class="title1">采购单号</td>
				<td class="title1">入库日期</td>
				<td class="title1">产品编号</td>
				<td class="title1">产品名称</td>
				<td class="title1">入库数</td>
				<td class="title1">入库总金额</td>
			</tr>
		</table>
		<p align="center">共<span id="totalPage">1</span>页，当前第<span id="nowPage">1</span>页 
			<select id="page">
				<option value=1 class="page">1</option>
			</select>
			<input type="button" value="跳转">
		</p>
		</form>
	</div>
</body>
</html>