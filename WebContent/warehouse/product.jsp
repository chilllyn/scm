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
<title>Product</title>
<link href="css/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="script/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="script/product.js"></script>
<script language="javascript" src="script/common.js"></script>
<!--  <script type="text/javascript" src="script/productDiv.js"></script>-->
<script type="text/javascript" src="script/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
	<div id="m">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" id="m">
			<tr>
				<td nowrap class="title1">仓储管理--产品管理</td>
			</tr>
		</table>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="30px" nowrap class="toolbar">&nbsp;</td>
				<td width="40px" nowrap class="toolbar" onMouseOver="OMO(event)"
					onMouseOut="OMOU(event)" onclick="add()">
					<img src="images/new.gif">新增</td>
				<td width="90px" nowrap class="toolbar" onMouseOver="OMO(event)"
					onMouseOut="OMOU(event)" onclick="deletech()">&nbsp;
					<img src="images/delete.gif">删除选中</td>
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
			所属分类: <select id="searchCategory">
				<option></option>
				<c:forEach items="${categories }" var="c">
					<option value="${c.categoryId }">${c.name }</option>
				</c:forEach>
			</select>
			<input type="button" value="查询" />
		</div>
		<table width="100%" border="0" align="center" cellspacing="1"
			class="c" id="products">
			<tr>
				<td class="title1">全选<input type="checkbox" id="allch"></td>
				<td class="title1">产品编号</td>
				<td class="title1">产品名称</td>
				<td class="title1">数量单位</td>
				<td class="title1" style="display:none">分类编号</td>
				<td class="title1">分类名称</td>
				<td class="title1">添加日期</td>
				<td class="title1">销售价</td>
				<td class="title1">产品描述</td>
				<td class="title1">操作</td>
			</tr>
			<c:forEach items="${products }" var="product">
			<tr class="ptr">
				<td align="center"><input type="checkbox" class="subch"></td>
				<td align="center">${product.productCode }</td>
				<td align="center">${product.name }</td>
				<td align="center">${product.unitName }</td>
				<td align="center" style="display:none">${product.categoryId }</td>
				<td align="center">${product.categoryName }</td>
				<td align="center">${product.createDate }</td>
				<td align="center">${product.price }</td>
				<td align="center">${product.remark }</td>
				<td align="center">
					<input type="button" value="修改" >
					<input type="button" value="删除" >
				</td>
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
	<div id="add" class="hidd">
		<form action="">
		<table width="100%" border="0" align="center" cellspacing="1"
			class="c">
			<tr>
				<td class="title1"></td>
				<td class="title1"></td>
			</tr>
			<tr>
				<td align="center">产品编号</td>
				<td align="left">
					<input type="text" id="addProductCode" /><br>
					<span>4~20字符，字母和数字</span>
				</td>
			</tr>
			<tr>
				<td align="center">产品名称</td>
				<td align="left">
					<input type="text" id="addName" ><br>
					<span>不为空，不超过100字符</span>
				</td>
			</tr>
			<tr>
				<td align="center">产品分类</td>
				<td align="left">
					<select id="addCategory">
						<c:forEach items="${categories }" var="c">
							<option value="${c.categoryId }">${c.name }</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td align="center">销售价</td>
				<td align="left">
					<input type="text" id="addPrice" value="0"/><br>
				</td>
			</tr>
			<tr>
				<td align="center">数量单位</td>
				<td align="left">
					<input type="text" id="addUnit" /><br>
					<span>必填,最多5个字符</span>
				</td>
			</tr>
			<tr>
				<td align="center">添加日期</td>
				<td align="left">
					<input type="text" id="addDate" readOnly='readOnly'/><br>
				</td>
			</tr>
			<tr>
				<td align="center">描述</td>
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