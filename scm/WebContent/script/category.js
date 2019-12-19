//新增0，修改1
var au=0;
var updateId=0;
var updateName="";
//新增
function add(){
	au=0;
	$("#m").hide();
	$("#add").show();
}
//清空新增修改表格，并隐藏
function init(){
	$("#m").show();
	$("#add").hide();
	$("#addName").val("");
	$("#addRemark").val("");
	$("#addNameTip").text("");
}
//验证新增产品类名是否重复
var updateName="";
function checkName(){
	var $addName=$("#addName").val();
	$.ajax({
		url:"warehouse/categoryCheckName",
		data:{
			"addName":$addName
		},
		type:"get",
		dataType:"json",
		async:true,
		success:function(results){
			$("#addNameTip").text(results);
			if($addName==""){
				$("#addNameTip").text("");
			}
			if(au==1&&$addName==updateName){
				$("#addNameTip").text("产品分类名称可用");
			}
		}
	})
}

$(function(){
	
	//查询
	$("[value=查询]").click(function(){
		var flag=true;
		var $categoryId=$(":text").eq(0).val();
		var $name=$(":text").eq(1).val();
		if(!(/^[0-9]*$/.test($categoryId))){
			alert("产品分类序列号请输入数字！");
			flag=false;
		}
		if(flag){
			$.ajax({
				url:"warehouse/categorySearch",
				data:{
					"categoryId":$categoryId,
					"name":$name
				},
				type:"get",
				dataType:"json",
				async:true,
				success:function(results){
					$(".ctr").remove();
					$("option").remove();
					var categories=results[0];
					for(var i=0;i<categories.length;i++){
						$td1=$("<td align='center'></td>");
						$td1.append(categories[i].categoryId);
						$td2=$("<td align='center'></td>");
						$td2.append(categories[i].name);
						$td3=$("<td align='center'></td>");
						$td3.append(categories[i].remark);
						$td4=$("<td align='center'></td>");
						$td4.append("<input type='button' value='修改' >"+
				"<input type='button' value='删除'>");
						$tr=$("<tr class='ctr'></tr>");
						$tr.append($td1);
						$tr.append($td2);
						$tr.append($td3);
						$tr.append($td4);
						$("#categories").append($tr);
					}
					var totalPage=results[1];
					for(var i=1;i<=parseInt(totalPage);i++){
						$option=$("<option></option>");
						$option.append(i);
						$("select").append($option);
					}
					$("#totalPage").text(totalPage);
					$("#nowPage").text(1);
				}
			})
		}
	})

	//跳转
	$("[value=跳转]").click(function(){
		var flag=true;
		var $categoryId=$(":text").eq(0).val();
		var $name=$(":text").eq(1).val();
		var $toPage=$("select option:checked").text();
		if(!(/^[0-9]*$/.test($categoryId))){
			alert("产品分类序列号请输入数字！");
			flag=false;
		}
		if(flag){
			$.ajax({
				url:"warehouse/categoryTo",
				data:{
					"categoryId":$categoryId,
					"name":$name,
					"toPage":$toPage
				},
				type:"get",
				dataType:"json",
				async:true,
				success:function(results){
					$(".ctr").remove();
					$("option").remove();
					var categories=results[0];
					for(var i=0;i<categories.length;i++){
						$td1=$("<td align='center'></td>");
						$td1.append(categories[i].categoryId);
						$td2=$("<td align='center'></td>");
						$td2.append(categories[i].name);
						$td3=$("<td align='center'></td>");
						$td3.append(categories[i].remark);
						$td4=$("<td align='center'></td>");
						$td4.append("<input type='button' value='修改' >"+
								"<input type='button' value='删除' >");
						$tr=$("<tr class='ctr'></tr>");
						$tr.append($td1);
						$tr.append($td2);
						$tr.append($td3);
						$tr.append($td4);
						$("#categories").append($tr);
					}
					var totalPage=results[1];
					for(var i=1;i<=parseInt(totalPage);i++){
						$option=$("<option></option>");
						$option.append(i);
						$("select").append($option);
					}
					$("#totalPage").text(totalPage);
					if($toPage>totalPage){
						$("#nowPage").text(totalPage);
					}else{
						$("#nowPage").text($toPage);
					}
				}
			})
		}
	})
	//修改
	$("form").on("click","[value=修改]",function(){
		au=1;
		$("#m").hide();
		$("#add").show();
		$tds=$(this).parent().parent().find("td");
		$("#addName").val($tds.eq(1).text());
		updateName=$tds.eq(1).text();
		updateId=$tds.eq(0).text();
		$("#addRemark").val($tds.eq(2).text());
	})
	//删除
	$("table").on("click","[value=删除]",function(){
		var confirm=window.confirm("确认删除吗？");
		//确认true，取消false
		if(confirm){
			var $categoryId=$(this).parent().parent().find("td").eq(0).text();
			var $nowPage=$("#nowPage").text();
			var $searchId=$(":text").eq(0).val();
			var $searchName=$(":text").eq(1).val();
			$.ajax({
				url:"warehouse/categoryDelete",
				data:{
					"categoryId":$categoryId,
					"nowPage":$nowPage,
					"searchId":$searchId,
					"searchName":$searchName
				},
				type:"get",
				dataType:"json",
				async:true,
				success:function(results){
					var status=results[2];
					if(status==0){
						alert("该类别下有关联产品，不能删除！");
					}else if(status==1){
						alert("删除失败");
					}else if(status==2){
						$(".ctr").remove();
						$("option").remove();
						var categories=results[0];
						for(var i=0;i<categories.length;i++){
							$td1=$("<td align='center'></td>");
							$td1.append(categories[i].categoryId);
							$td2=$("<td align='center'></td>");
							$td2.append(categories[i].name);
							$td3=$("<td align='center'></td>");
							$td3.append(categories[i].remark);
							$td4=$("<td align='center'></td>");
							$td4.append("<input type='button' value='修改' >"+
									"<input type='button' value='删除' >");
							$tr=$("<tr class='ctr'></tr>");
							$tr.append($td1);
							$tr.append($td2);
							$tr.append($td3);
							$tr.append($td4);
							$("#categories").append($tr);
						}
						var totalPage=results[1];
						$("#totalPage").text(totalPage);
						for(var i=1;i<=parseInt(totalPage);i++){
							$option=$("<option></option>");
							$option.append(i);
							$("select").append($option);
						}
						alert("删除成功！！");
					}else if(status==3){
						$(".ctr").remove();
						$("option").remove();
						var categories=results[0];
						for(var i=0;i<categories.length;i++){
							$td1=$("<td align='center'></td>");
							$td1.append(categories[i].categoryId);
							$td2=$("<td align='center'></td>");
							$td2.append(categories[i].name);
							$td3=$("<td align='center'></td>");
							$td3.append(categories[i].remark);
							$td4=$("<td align='center'></td>");
							$td4.append("<input type='button' value='修改' >"+
									"<input type='button' value='删除' >");
							$tr=$("<tr class='ctr'></tr>");
							$tr.append($td1);
							$tr.append($td2);
							$tr.append($td3);
							$tr.append($td4);
							$("#categories").append($tr);
						}
						var totalPage=results[1];
						$("#totalPage").text(totalPage);
						$("#nowPage").text(totalPage);
						for(var i=1;i<=parseInt(totalPage);i++){
							$option=$("<option></option>");
							$option.append(i);
							$("select").append($option);
						}
						alert("删除成功！！");
					}
				}
			})
		}
	})
	//新增,修改保存
	$("[value=保存]").click(function(){
		if(au==0){
			var flag=true;
			var $addName=$("#addName").val();
			var $addRemark=$("#addRemark").val();
			var $addNameTip=$("#addNameTip").text();
			if(!(/^.+$/.test($addName))||!("产品分类名称可用"==($addNameTip))){
				flag=false;
				alert("请输入合适的产品类名！");
			}
			if(flag){
				$.ajax({
					url:"warehouse/categoryAdd",
					data:{
						"addName":$addName,
						"addRemark":$addRemark
					},
					type:"get",
					dataType:"json",
					async:true,
					success:function(results){
						var $status=results[2];
						if($status==2){
							$(".ctr").remove();
							$("option").remove();
							var categories=results[0];
							for(var i=0;i<categories.length;i++){
								$td1=$("<td align='center'></td>");
								$td1.append(categories[i].categoryId);
								$td2=$("<td align='center'></td>");
								$td2.append(categories[i].name);
								$td3=$("<td align='center'></td>");
								$td3.append(categories[i].remark);
								$td4=$("<td align='center'></td>");
								$td4.append("<input type='button' value='修改' >"+
										"<input type='button' value='删除' >");
								$tr=$("<tr class='ctr'></tr>");
								$tr.append($td1);
								$tr.append($td2);
								$tr.append($td3);
								$tr.append($td4);
								$("#categories").append($tr);
							}
							var totalPage=results[1];
							$("#totalPage").text(totalPage);
							$("#nowPage").text(totalPage);
							for(var i=1;i<=parseInt(totalPage);i++){
								$option=$("<option></option>");
								$option.append(i);
								$("select").append($option);
							}
							alert("新增成功！！");
						}else if($status==1){
							alert("新增失败！！");
						}else if($status==0){
							alert("名称重复！！");
						}
						init();
					}
				})
			}
		}else if(au==1){
			var flag=true;
			var $categoryId=$(":text").eq(0).val();
			var $name=$(":text").eq(1).val();
			var $addName=$("#addName").val();
			var $addRemark=$("#addRemark").val();
			var $nowPage=$("#nowPage").text();
			var $addNameTip=$("#addNameTip").text();
			if($addName!=updateName){
				if(!(/^.+$/.test($addName))||!("产品分类名称可用"==($addNameTip))){
					flag=false;
					alert("请输入合适的产品类名！");
				}
			}
			if(flag){
				$.ajax({
					url:"warehouse/categoryUpdate",
					data:{
						"categoryId":$categoryId,
						"name":$name,
						"updateId":updateId,
						"addName":$addName,
						"addRemark":$addRemark,
						"nowPage":$nowPage
					},
					type:"get",
					dataType:"json",
					async:true,
					success:function(results){
						var $status=results[1];
						if($status==2){
							$(".ctr").remove();
							var categories=results[0];
							for(var i=0;i<categories.length;i++){
								$td1=$("<td align='center'></td>");
								$td1.append(categories[i].categoryId);
								$td2=$("<td align='center'></td>");
								$td2.append(categories[i].name);
								$td3=$("<td align='center'></td>");
								$td3.append(categories[i].remark);
								$td4=$("<td align='center'></td>");
								$td4.append("<input type='button' value='修改' >"+
										"<input type='button' value='删除' >");
								$tr=$("<tr class='ctr'></tr>");
								$tr.append($td1);
								$tr.append($td2);
								$tr.append($td3);
								$tr.append($td4);
								$("#categories").append($tr);
							}
							init();
							alert("修改成功！！");
						}else if(status==1){
							alert("修改失败！！");
						}else if(status==0){
							alert("名称重复！！");
						}
					}
				})
			}
		}
	})
})
