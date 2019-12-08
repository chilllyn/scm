$(function(){
	//查询
	$("[value=查询]").click(function(){
		var productCode=$("#productCode").val();
		var name=$("#name").val();
		var min=$("#min").val();
		var max=$("#max").val();
		var flag=true;
		if(min==""){
			min=0;
		}
		if(max==""){
			max=999999999;
		}
		if(!/^\d$|^[1-9]\d+$/.test(min)){
			flag=false;
			alert("库存数量min必须为非负整数！");
		}
		if(!/^\d$|^[1-9]\d+$/.test(max)){
			flag=false;
			alert("库存数量max必须为非负整数！");
		}
		if(parseInt(max)<parseInt(min)){
			flag=false;
			alert("库存数量max必须不小于min！");
		}
		if(flag){
			$.ajax({
				url:"warehouse/stockSearchSearch",
				data:{
					"productCode":productCode,
					"name":name,
					"min":min,
					"max":max
				},
				type:"get",
				dataType:"json",
				async:true,
				success:function(results){
					var status=results[2];
					if(status==0){
						alert("库存数量范围错误，请检查！");
					}else if(status==1){
						$(".ptr").remove();
						$(".page").remove();
						var products=results[0];
						for(var i=0;i<products.length;i++){
							$td1=$('<td align="center"></td>');
							$td1.append('<a href="warehouse/stockDetail?productCode='+products[i].productCode+'">'+products[i].productCode+'</a>');
							$td2=$('<td align="center"></td>');
							$td2.append(products[i].name);
							$td3=$('<td align="center"></td>');
							$td3.append(products[i].num);
							$td4=$('<td align="center"></td>');
							$td4.append(products[i].poNum);
							$td5=$('<td align="center"></td>');
							$td5.append(products[i].soNum);
							$tr=$("<tr class='ptr'></tr>");
							$tr.append($td1);
							$tr.append($td2);
							$tr.append($td3);
							$tr.append($td4);
							$tr.append($td5);
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
					}
				}
			})
		}
	})
	//分页跳转
	$("[value=跳转]").click(function(){
		var productCode=$("#productCode").val();
		var name=$("#name").val();
		var min=$("#min").val();
		var max=$("#max").val();
		var toPage=$("#page option:selected").text();
		$.ajax({
			url:"warehouse/stockSearchTo",
			data:{
				"productCode":productCode,
				"name":name,
				"min":min,
				"max":max,
				"toPage":toPage
			},
			type:"get",
			dataType:"json",
			async:true,
			success:function(results){
				$(".ptr").remove();
				var products=results[0];
				for(var i=0;i<products.length;i++){
					$td1=$('<td align="center"></td>');
					$td1.append('<a href="warehouse/stockDetail?productCode='+products[i].productCode+'">'+products[i].productCode+'</a>');
					$td2=$('<td align="center"></td>');
					$td2.append(products[i].name);
					$td3=$('<td align="center"></td>');
					$td3.append(products[i].num);
					$td4=$('<td align="center"></td>');
					$td4.append(products[i].poNum);
					$td5=$('<td align="center"></td>');
					$td5.append(products[i].soNum);
					$tr=$("<tr class='ptr'></tr>");
					$tr.append($td1);
					$tr.append($td2);
					$tr.append($td3);
					$tr.append($td4);
					$tr.append($td5);
					$("#products").append($tr);
				}
				$("#nowPage").text(toPage);
			}
		})
	})
})