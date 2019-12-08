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
<title>库存变更记录</title>
<link href="css/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="script/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="script/stockChange.js"></script>
<script language="javascript" src="script/common.js" ></script>
<script type="text/javascript" src="script/productDiv.js" ></script>
<script type="text/javascript" src="script/My97DatePicker/WdatePicker.js" ></script>

<style type="text/css">
	div.product{
		position: absolute;
		top:2px;
		left: 2px;
		width:100%;
		height: 98%;
		background-color: #fffffe;
	}
</style>
</head>
<body>
	<div id="m">
		<table width="100%"  border="0" cellpadding="0" cellspacing="0" id="m">
		  <tr>
			<td nowrap class="title1">仓储管理--库存查询--<span id='productCode'>${productCode }</span>_${name }</td>
		  </tr>
		</table>
		<table width="100%"  border="0" cellpadding="0" cellspacing="0">
		 <tr>
			<td nowrap="" class="toolbar">&nbsp;</td>
			<td width="60px" nowrap="" class="toolbar" onmouseover="OMO(event)" onmouseout="OMOU(event)">&nbsp;</td>
			<td width="20px" nowrap="" class="toolbar"></td>
			<td width="80px" nowrap="" class="P" onmouseover="OMO(event)" onmouseout="OMOU(event)" ></td>
			<td width="20px" nowrap="" class="toolbar"></td>
			<td width="80px" nowrap="" class="P" onmouseover="OMO(event)" onmouseout="OMOU(event)" onclick="tab(0)">入库信息</td>
			<td width="20px" nowrap="" class="toolbar">|</td>
			<td width="80px" nowrap="" class="toolbar" onmouseover="OMO(event)" onmouseout="OMOU(event)" onclick="tab(1)">出库信息</td>
			<td width="20px" nowrap="" class="toolbar">|</td>
		  </tr>
		</table>
		
			
		<div id="dataList">
			<table width="100%" border="0" align="center" cellspacing="1" class="c" id="srs">
				<tr>
					<td class="title1">库存变更时间</td>
					<td class="title1">相关单号</td>
					<td class="title1">经手人</td>
					<td class="title1">数量</td>
					<td class="title1">类型</td>
				</tr>
			<c:forEach items="${srs }" var="sr">
				<tr class="srtr">
					<td align="center">${sr.stockTime }</td>
					<td align="center">${sr.orderCode }</td>
					<td align="center">${sr.createUser }</td>
					<td align="center">${sr.stockNum }</td>
					<td align="center">
						<c:if test="${sr.stockType==1 }">采购入库</c:if>
						<c:if test="${sr.stockType==2 }">销售出库</c:if>
						<c:if test="${sr.stockType==3 }">盘点入库</c:if>
						<c:if test="${sr.stockType==4 }">盘点出库</c:if>
					</td>
				</tr>
			</c:forEach>
				<tr style="display:none"><td colspan="10" name="detail"></td></tr>
			</table>
			<div class="pageDiv">
				<p align="center">共<span id="totalPage">${totalPage }</span>页，当前第<span id="nowPage">1</span>页 
				<select id="page">
				<c:forEach begin="1" end="${totalPage }" var="i"> 
					<option value=${i } class="page">${i }</option>
				</c:forEach>
				</select>
				<input type="button" value="跳转">
				</p>
			</div>
		</div>
	</div>
</body>
</html>