//新增0，修改1
var au=0;
//日期时间补0
function addZero(s){
	return s<10?'0'+s:s;
}
//新增
function add(){
	au=0;
	var date=new Date();
	var year=date.getFullYear();
	var month=addZero(date.getMonth()+1);
	var day=addZero(date.getDate());
	var now=year+"-"+month+"-"+day;
	$("#addDate").val(now);
	$("#m").hide();
	$("#add").show();
}
//清空新增修改表格并隐藏
function init(){
	$("#addProductCode").val("");
	$("#addProductCode").removeAttr("readonly");
	$("#addName").val("");
	$("#addPrice").val(0);
	$("#addUnit").val("");
	$("#addRemark").val("");
	$("#m").show();
	$("#add").hide();
	$("#allch").prop("checked",false);
}
//删除
function del(searchProductCode,searchName,searchCategory,nowPage,productCode){
	$.ajax({
		url:"warehouse/productDel",
		data:{
			"searchProductCode":searchProductCode,
			"searchName":searchName,
			"searchCategory":searchCategory,
			"nowPage":nowPage,
			"productCode":productCode
		},
		dataType:"json",
		type:"get",
		async:true,
		success:function(results){
			var status=results[2];
			if(status==0){
				alert(productCode+":删除失败！");
			}else{
				$(".ptr").remove();
				$(".page").remove();
				var products=results[0];
				for(var i=0;i<products.length;i++){
					$td1=$('<td align="center"><input type="checkbox" class="subch"></td>');
					$td2=$('<td align="center"></td>');
					$td2.append(products[i].productCode);
					$td3=$('<td align="center"></td>');
					$td3.append(products[i].name);
					$td4=$('<td align="center"></td>');
					$td4.append(products[i].unitName);
					$td5=$('<td align="center" style="display:none"></td>');
					$td5.append(products[i].categoryId);
					$td6=$('<td align="center"></td>');
					$td6.append(products[i].categoryName);
					$td7=$('<td align="center"></td>');
					$td7.append(products[i].createDate);
					$td8=$('<td align="center"></td>');
					$td8.append(products[i].price);
					$td9=$('<td align="center"></td>');
					$td9.append(products[i].remark);
					$td10=$('<td align="center"><input type="button" value="修改" ><input type="button" value="删除" ></td>');
					$tr=$("<tr class='ptr'></tr>");
					$tr.append($td1);
					$tr.append($td2);
					$tr.append($td3);
					$tr.append($td4);
					$tr.append($td5);
					$tr.append($td6);
					$tr.append($td7);
					$tr.append($td8);
					$tr.append($td9);
					$tr.append($td10);
					$("#products").append($tr);
				}
				var totalPage=results[1];
				for(var i=1;i<=parseInt(totalPage);i++){
					$option=$("<option class='page'></option>");
					$option.append(i);
					$("#page").append($option);
				}
				$("#totalPage").text(totalPage);
				if(status==1){
					$("#nowPage").text(totalPage);
				}
				alert(productCode+":删除成功！");
				$("#allch").prop("checked",false);
			}
		}
	})
}
//删除选中
function deletech(){
	var confirm=window.confirm("确认删除吗？");
	if(confirm){
		var searchProductCode=$("#productCode").val();
		var searchName=$("#name").val();
		var searchCategory=$("#searchCategory option:checked").text();
		var nowPage=$("#nowPage").text();
		var chs=$(".subch:checked");
		for(var i=0;i<chs.length;i++){
			var productCode=chs.eq(i).parent().parent().find("td").eq(1).text();
			del(searchProductCode,searchName,searchCategory,nowPage,productCode);
		}
	}
}
$(function(){
	//删除
	$("table").on("click","[value=删除]",function(){
		var confirm=window.confirm("确认删除吗？");
		if(confirm){
			var searchProductCode=$("#productCode").val();
			var searchName=$("#name").val();
			var searchCategory=$("#searchCategory option:checked").text();
			var nowPage=$("#nowPage").text();
			var productCode=$(this).parent().parent().find("td").eq(1).text();
			del(searchProductCode,searchName,searchCategory,nowPage,productCode);
		}
	})
	//修改
	$("table").on("click","[value=修改]",function(){
		au=1;
		var tds=$(this).parent().parent().find("td");
		$("#addProductCode").val(tds.eq(1).text());
		$("#addProductCode").attr("readonly","readonly");
		$("#addName").val(tds.eq(2).text());
		$("#addCategory option[value="+tds.eq(4).text()+"]").prop("selected",true);
		$("#addDate").val(tds.eq(6).text())
		$("#addPrice").val(tds.eq(7).text());
		$("#addUnit").val(tds.eq(3).text());
		$("#addRemark").val(tds.eq(8).text());
		$("#m").hide();
		$("#add").show();
	})
	//新增，修改保存
	$("[value=保存]").click(function(){
		if(au==0){
			var flag=true;
			var productCode=$("#addProductCode").val();
			var name=$("#addName").val();
			var categoryId=$("#addCategory option:checked").val();
			var price=$("#addPrice").val()
			var unitName=$("#addUnit").val();
			var createDate=$("#addDate").val();
			var remark=$("#addRemark").val();
			if(!/^[0-9a-zA-Z]{4,20}$/.test(productCode)){
				flag=false;
				alert("产品编码格式错误！");
			}
			if(!/^[\w\u4E00-\u9FA5]{1,100}$/.test(name)){
				flag=false;
				alert("产品名称格式错误！");
			}
			if(!/^\d$|^[1-9]\d+$|^\d+\.\d+$/.test(price)){
				flag=false;
				alert("销售价必须为非负数！");
			}
			if(!/^[\w\u4E00-\u9FA5]{1,5}$/.test(unitName)){
				flag=false;
				alert("数量单位格式错误！");
			}
			if(flag){
				$.ajax({
					url:"warehouse/productAdd",
					data:{
						"productCode":productCode,
						"name":name,
						"categoryId":categoryId,
						"price":price,
						"unitName":unitName,
						"createDate":createDate,
						"remark":remark
					},
					type:"get",
					dataType:"json",
					async:true,
					success:function(results){
						var status=results[2];
						if(status==0){
							alert("s:产品编码格式错误！")
						}else if(status==1){
							alert("s:产品名称格式错误！");
						}else if(status==2){
							alert("s:销售价必须为非负数！");
						}else if(status==3){
							alert("s:数量单位格式错误！");
						}else if(status==4){
							alert("s:新增失败！");
						}else if(status==5){
							$(".ptr").remove();
							$(".page").remove();
							var products=results[0];
							for(var i=0;i<products.length;i++){
								$td1=$('<td align="center"><input type="checkbox" class="subch"></td>');
								$td2=$('<td align="center"></td>');
								$td2.append(products[i].productCode);
								$td3=$('<td align="center"></td>');
								$td3.append(products[i].name);
								$td4=$('<td align="center"></td>');
								$td4.append(products[i].unitName);
								$td5=$('<td align="center" style="display:none"></td>');
								$td5.append(products[i].categoryId);
								$td6=$('<td align="center"></td>');
								$td6.append(products[i].categoryName);
								$td7=$('<td align="center"></td>');
								$td7.append(products[i].createDate);
								$td8=$('<td align="center"></td>');
								$td8.append(products[i].price);
								$td9=$('<td align="center"></td>');
								$td9.append(products[i].remark);
								$td10=$('<td align="center"><input type="button" value="修改" ><input type="button" value="删除" ></td>');
								$tr=$("<tr class='ptr'></tr>");
								$tr.append($td1);
								$tr.append($td2);
								$tr.append($td3);
								$tr.append($td4);
								$tr.append($td5);
								$tr.append($td6);
								$tr.append($td7);
								$tr.append($td8);
								$tr.append($td9);
								$tr.append($td10);
								$("#products").append($tr);
							}
							var totalPage=results[1];
							for(var i=1;i<=parseInt(totalPage);i++){
								$option=$("<option class='page'></option>");
								$option.append(i);
								$("#page").append($option);
							}
							$("#totalPage").text(totalPage);
							$("#nowPage").text(totalPage);
							alert("新增成功");
							init();
						}
					}
				})
			}
		}else if(au==1){
			var flag=true;
			var searchProductCode=$("#productCode").val();
			var searchName=$("#name").val();
			var searchCategory=$("#searchCategory option:checked").text();
			var nowPage=$("#nowPage").text();
			var productCode=$("#addProductCode").val();
			var name=$("#addName").val();
			var categoryId=$("#addCategory option:checked").val();
			var price=$("#addPrice").val()
			var unitName=$("#addUnit").val();
			var createDate=$("#addDate").val();
			var remark=$("#addRemark").val();
			if(!/^[\w\u4E00-\u9FA5]{1,100}$/.test(name)){
				flag=false;
				alert("产品名称格式错误！");
			}
			if(!/^\d$|^[1-9]\d+$|^\d+\.\d+$/.test(price)){
				flag=false;
				alert("销售价必须为非负数！");
			}
			if(!/^[\w\u4E00-\u9FA5]{1,5}$/.test(unitName)){
				flag=false;
				alert("数量单位格式错误！");
			}
			if(flag){
				$.ajax({
					url:"warehouse/productUpdate",
					data:{
						"searchProductCode":searchProductCode,
						"searchName":searchName,
						"searchCategory":searchCategory,
						"nowPage":nowPage,
						"productCode":productCode,
						"name":name,
						"categoryId":categoryId,
						"price":price,
						"unitName":unitName,
						"createDate":createDate,
						"remark":remark
					},
					type:"get",
					dataType:"json",
					async:true,
					success:function(results){
						var status=results[1];
						if(status==0){
							alert("s:产品名称格式错误！");
						}else if(status==1){
							alert("s:销售价必须为非负数！");
						}else if(status==2){
							alert("s:数量单位格式错误！");
						}else if(status==3){
							alert("s:修改失败！");
						}else if(status==4){
							$(".ptr").remove();
							var products=results[0];
							for(var i=0;i<products.length;i++){
								$td1=$('<td align="center"><input type="checkbox" class="subch"></td>');
								$td2=$('<td align="center"></td>');
								$td2.append(products[i].productCode);
								$td3=$('<td align="center"></td>');
								$td3.append(products[i].name);
								$td4=$('<td align="center"></td>');
								$td4.append(products[i].unitName);
								$td5=$('<td align="center" style="display:none"></td>');
								$td5.append(products[i].categoryId);
								$td6=$('<td align="center"></td>');
								$td6.append(products[i].categoryName);
								$td7=$('<td align="center"></td>');
								$td7.append(products[i].createDate);
								$td8=$('<td align="center"></td>');
								$td8.append(products[i].price);
								$td9=$('<td align="center"></td>');
								$td9.append(products[i].remark);
								$td10=$('<td align="center"><input type="button" value="修改" ><input type="button" value="删除" ></td>');
								$tr=$("<tr class='ptr'></tr>");
								$tr.append($td1);
								$tr.append($td2);
								$tr.append($td3);
								$tr.append($td4);
								$tr.append($td5);
								$tr.append($td6);
								$tr.append($td7);
								$tr.append($td8);
								$tr.append($td9);
								$tr.append($td10);
								$("#products").append($tr);
							}
							alert("修改成功！");
							init();
						}
					}
				})
			}
		}
	})
	//分页跳转
	$("[value=跳转]").click(function(){
		var productCode=$("#productCode").val();
		var name=$("#name").val();
		var searchCategory=$("#searchCategory option:checked").text();
		var toPage=$("#page option:selected").text();
		$.ajax({
			url:"warehouse/productTo",
			data:{
				"productCode":productCode,
				"name":name,
				"searchCategory":searchCategory,
				"toPage":toPage
			},
			type:"get",
			dataType:"json",
			async:true,
			success:function(results){
				$(".ptr").remove();
				var products=results[0];
				for(var i=0;i<products.length;i++){
					$td1=$('<td align="center"><input type="checkbox" class="subch"></td>');
					$td2=$('<td align="center"></td>');
					$td2.append(products[i].productCode);
					$td3=$('<td align="center"></td>');
					$td3.append(products[i].name);
					$td4=$('<td align="center"></td>');
					$td4.append(products[i].unitName);
					$td5=$('<td align="center" style="display:none"></td>');
					$td5.append(products[i].categoryId);
					$td6=$('<td align="center"></td>');
					$td6.append(products[i].categoryName);
					$td7=$('<td align="center"></td>');
					$td7.append(products[i].createDate);
					$td8=$('<td align="center"></td>');
					$td8.append(products[i].price);
					$td9=$('<td align="center"></td>');
					$td9.append(products[i].remark);
					$td10=$('<td align="center"><input type="button" value="修改" ><input type="button" value="删除" ></td>');
					$tr=$("<tr class='ptr'></tr>");
					$tr.append($td1);
					$tr.append($td2);
					$tr.append($td3);
					$tr.append($td4);
					$tr.append($td5);
					$tr.append($td6);
					$tr.append($td7);
					$tr.append($td8);
					$tr.append($td9);
					$tr.append($td10);
					$("#products").append($tr);
				}
				$("#nowPage").text(toPage);
				$("#allch").prop("checked",false);
			}
		})
	})
	//查询
	$("[value=查询]").click(function(){
		var productCode=$("#productCode").val();
		var name=$("#name").val();
		var searchCategory=$("#searchCategory option:checked").text();
		$.ajax({
			url:"warehouse/productSearch",
			data:{
				"productCode":productCode,
				"name":name,
				"searchCategory":searchCategory
			},
			type:"get",
			dataType:"json",
			async:true,
			success:function(results){
				$(".ptr").remove();
				$(".page").remove();
				var products=results[0];
				for(var i=0;i<products.length;i++){
					$td1=$('<td align="center"><input type="checkbox" class="subch"></td>');
					$td2=$('<td align="center"></td>');
					$td2.append(products[i].productCode);
					$td3=$('<td align="center"></td>');
					$td3.append(products[i].name);
					$td4=$('<td align="center"></td>');
					$td4.append(products[i].unitName);
					$td5=$('<td align="center" style="display:none"></td>');
					$td5.append(products[i].categoryId);
					$td6=$('<td align="center"></td>');
					$td6.append(products[i].categoryName);
					$td7=$('<td align="center"></td>');
					$td7.append(products[i].createDate);
					$td8=$('<td align="center"></td>');
					$td8.append(products[i].price);
					$td9=$('<td align="center"></td>');
					$td9.append(products[i].remark);
					$td10=$('<td align="center"><input type="button" value="修改" ><input type="button" value="删除" ></td>');
					$tr=$("<tr class='ptr'></tr>");
					$tr.append($td1);
					$tr.append($td2);
					$tr.append($td3);
					$tr.append($td4);
					$tr.append($td5);
					$tr.append($td6);
					$tr.append($td7);
					$tr.append($td8);
					$tr.append($td9);
					$tr.append($td10);
					$("#products").append($tr);
				}
				var totalPage=results[1];
				for(var i=1;i<=parseInt(totalPage);i++){
					$option=$("<option class='page'></option>");
					$option.append(i);
					$("#page").append($option);
				}
				$("#totalPage").text(totalPage);
				$("#nowPage").text(1);
				$("#allch").prop("checked",false);
			}
		})
	})
	//全选
	$("#allch").click(function(){
		if($(this).prop("checked")){
			$(".subch").prop("checked",true);
		}else{
			$(".subch").prop("checked",false);
		}
	})
	//子复选框
	$("table").on("click",".subch",function(){
		if($(this).prop("checked")){
			if($(".subch:checked").length==$(this).parent().parent().parent().find("tr").length-1){
				$("#allch").prop("checked",true);
			}
		}else{
			$("#allch").prop("checked",false);
		}
	})
})