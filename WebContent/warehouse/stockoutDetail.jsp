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
<title>销售单详情</title>
<link href="css/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="script/jquery-3.4.1.min.js"></script>
<script language="javascript" src="script/common.js" ></script>
<script type="text/javascript" src="script/productDiv.js" ></script>
<script type="text/javascript" src="script/My97DatePicker/WdatePicker.js" ></script>
</head>
<body>
<div id="m">
	<table width="100%"  border="0" cellpadding="0" cellspacing="0" id="m">
	  <tr>
		<td nowrap class="title1">出库登记--销售单详情</td>
	  </tr>
	</table>
	<table width="100%"  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="30px" nowrap class="toolbar">&nbsp;</td>
	<td nowrap class="toolbar">&nbsp;</td>
  </tr>
</table>

<table id="headTable" width="100%"  border="0" align="center" class="a1">
  <tr align="justify">
    <td>销售单编号</td>
    <td>${so.soid }</td>
    <td>创建时间</td>
    <td>${so.createTime }</td>
    <td>客户名称</td>
    <td>${so.customerName }</td>
    <td>创建用户</td>
    <td>${so.userName }</td>
  </tr>
  <tr align="justify">
    <td>附加费用</td>
    <td>${so.tipFee }</td>
    <td>产品总价</td>
    <td>${so.productTotal }</td>
    <td>付款方式</td>
    <td>
    	<c:if test="${so.payType==1 }">货到付款</c:if>
		<c:if test="${so.payType==2 }">款到发货</c:if>
		<c:if test="${so.payType==3 }">预付款到发货</c:if>
    </td>
    <td>最低预付款金额</td>
    <td>${so.prePayFee }</td>
  </tr>
  <tr align="justify">
    <td colspan="8">&nbsp;</td>
  </tr>
  <tr align="justify">
    <td>处理状态</td>
    <td>
    	<c:if test="${so.status==1 }">新增</c:if>
		<c:if test="${so.status==3 }">已付款</c:if>
		<c:if test="${so.status==5 }">已预付</c:if>
	</td>
    <td>入库登记时间</td>
    <td></td>
    <td>入库登记用户</td>
    <td></td>
    <td>付款登记时间</td>
    <td></td>
  </tr>
  <tr align="justify">
    <td>付款登记用户</td>
    <td></td>
    <td>预付登记时间</td>
    <td></td>
    <td>预付登记用户</td>
    <td></td>
    <td>了结时间</td>
    <td></td>
  </tr>
  <tr align="justify">
    <td>了结用户</td>
    <td></td>
    <td>备注</td>
    <td colspan="5"></td>
   </tr>
  <tr>
  	<td class="title2"></td>
  </tr>
</table>

<table width="100%"  border="0" align="center" cellspacing="1" id="detailTable">
  <tr>
    <td class="toolbar">序号 </td>
    <td class="toolbar">产品编号 </td>
    <td class="toolbar">产品名称 </td>
    <td class="toolbar">数量单位 </td>
    <td class="toolbar">产品数量 </td>
    <td class="toolbar">销售单价</td>
    <td class="toolbar">明细总价</td>
  </tr>
  
  <c:forEach items="${soitems }" var="s" varStatus="vs">
  	<tr align="center">
  		<td>${vs.count }</td>
  		<td>${s.productCode }</td>
  		<td>${s.name }</td>
		<td>${s.unitName }</td>
		<td>${s.num }</td> 
		<td>${s.unitPrice }</td> 
		<td>${s.itemPrice }</td>
  	</tr>
  </c:forEach>
</table>
<table width="100%"  border="0" align="center" cellspacing="1">
  <tr>
    <td class="title2"></td>
  </tr>
</table>
</div>
</body>
</html>