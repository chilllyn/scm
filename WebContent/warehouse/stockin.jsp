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
<title>入库登记</title>
<link href="css/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="script/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="script/stockin.js"></script>
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
			<td nowrap class="title1">仓储管理 -- 入库登记</td>
		  </tr>
		</table>
		<table width="100%"  border="0" cellpadding="0" cellspacing="0">
		 <tr>
			<td nowrap="" class="toolbar">&nbsp;</td>
			<td width="60px" nowrap="" class="toolbar" onmouseover="OMO(event)" onmouseout="OMOU(event)">&nbsp;</td>
			<td width="20px" nowrap="" class="toolbar">|</td>
			<td width="80px" nowrap="" class="P" onmouseover="OMO(event)" onmouseout="OMOU(event)" onclick="tab(1)">货到付款</td>
			<td width="20px" nowrap="" class="toolbar">|</td>
			<td width="80px" nowrap="" class="P" onmouseover="OMO(event)" onmouseout="OMOU(event)" onclick="tab(2)">款到发货</td>
			<td width="20px" nowrap="" class="toolbar">|</td>
			<td width="80px" nowrap="" class="toolbar" onmouseover="OMO(event)" onmouseout="OMOU(event)" onclick="tab(3)">预付款到发货</td>
			<td width="20px" nowrap="" class="toolbar">|</td>
		  </tr>
		</table>
		
			
		<div id="dataList">
			<table width="100%" border="0" align="center" cellspacing="1" class="c" id="pos">
				<tr>
					<td class="title1">采购单编号</td>
					<td class="title1">创建时间</td>
					<td class="title1">供应商名称</td>
					<td class="title1">创建用户</td>
					<td class="title1">附加费用</td>
					<td class="title1">采购产品总价</td>
					<td class="title1">采购单总价格</td>
					<td class="title1">付款方式</td>
					<td class="title1">最低预付款金额</td>
					<td class="title1">处理状态</td>
					<td class="title1">入库操作</td>
				</tr>
			<c:forEach items="${pos }" var="po">
				<tr class="ptr">
					<td align="center"><a href="warehouse/stockinDetail?poid=${po.poid }">${po.poid }</a></td>
					<td align="center">${po.createTime }</td>
					<td align="center">${po.venderName }</td>
					<td align="center">${po.userName }</td>
					<td align="center">${po.tipFee }</td>
					<td align="center">${po.productTotal }</td>
					<td align="center">${po.poTotal }</td>
					<td align="center">
						<c:if test="${po.payType==1 }">货到付款</c:if>
						<c:if test="${po.payType==2 }">款到发货</c:if>
						<c:if test="${po.payType==3 }">预付款到发货</c:if>
					</td>
					<td align="center">${po.prePayFee }</td>
					<td align="center">
						<c:if test="${po.status==1 }">新增</c:if>
						<c:if test="${po.status==3 }">已付款</c:if>
						<c:if test="${po.status==5 }">已预付</c:if>
					</td>
					<td align="center">
						<input type="button" value="入库">
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