$(function(){
	//跳转
	$("[value=跳转]").click(function(){
		var month=$("#month").val();
		var toPage=$("#page option:selected").text();
		//不选或者只选择年或月，返回空串，正确返回2019-12
		var flag=true;
		if(month==""){
			alert("请选择日期年月！");
			flag=false;
		}
		if(flag){
			$.ajax({
				url:"warehouse/stockoutMonthTo",
				data:{
					"month":month,
					"toPage":toPage
				},
				dataType:"json",
				type:"get",
				async:true,
				success:function(results){
					var orderNum=results[0];
					var numTotal=results[1];
					var priceTotal=results[2];
					$("#orderNum").text(orderNum);
					$("#numTotal").text(numTotal);
					$("#priceTotal").text(-priceTotal);
					$(".srtr").remove();
					$(".page").remove();
					var srs=results[3];
					for(var i=0;i<srs.length;i++){
						$td1=$('<td align="center">'+srs[i].orderCode+'</td>');
						$td2=$('<td align="center">'+srs[i].stockTime+'</td>');
						$td3=$('<td align="center">'+srs[i].productCode+'</td>');
						$td4=$('<td align="center">'+srs[i].productName+'</td>');
						$td5=$('<td align="center">'+srs[i].stockNum+'</td>');
						$td6=$('<td align="center">'+-srs[i].totalPrice+'</td>');
						$tr=$("<tr class='srtr'></tr>")
						$tr.append($td1);
						$tr.append($td2);
						$tr.append($td3);
						$tr.append($td4);
						$tr.append($td5);
						$tr.append($td6);
						$("#srs").append($tr);
					}
					var totalPage=results[4];
					for(var i=1;i<=parseInt(totalPage);i++){
						$option=$("<option class='page'></option>");
						$option.append(i);
						$("#page").append($option);
					}
					$("#totalPage").text(totalPage);
					$("#nowPage").text(toPage);
				}
			})
		}
	})
	//查询
	$("[value=查询]").click(function(){
		var month=$("#month").val();
		//不选或者只选择年或月，返回空串，正确返回2019-12
		var flag=true;
		if(month==""){
			alert("请选择日期年月！");
			flag=false;
		}
		if(flag){
			$.ajax({
				url:"warehouse/stockoutMonthSearch",
				data:{
					"month":month
				},
				dataType:"json",
				type:"get",
				async:true,
				success:function(results){
					var orderNum=results[0];
					var numTotal=results[1];
					var priceTotal=results[2];
					$("#orderNum").text(orderNum);
					$("#numTotal").text(numTotal);
					$("#priceTotal").text(-priceTotal);
					$(".srtr").remove();
					$(".page").remove();
					var srs=results[3];
					for(var i=0;i<srs.length;i++){
						$td1=$('<td align="center">'+srs[i].orderCode+'</td>');
						$td2=$('<td align="center">'+srs[i].stockTime+'</td>');
						$td3=$('<td align="center">'+srs[i].productCode+'</td>');
						$td4=$('<td align="center">'+srs[i].productName+'</td>');
						$td5=$('<td align="center">'+srs[i].stockNum+'</td>');
						$td6=$('<td align="center">'+-srs[i].totalPrice+'</td>');
						$tr=$("<tr class='srtr'></tr>")
						$tr.append($td1);
						$tr.append($td2);
						$tr.append($td3);
						$tr.append($td4);
						$tr.append($td5);
						$tr.append($td6);
						$("#srs").append($tr);
					}
					var totalPage=results[4];
					for(var i=1;i<=parseInt(totalPage);i++){
						$option=$("<option class='page'></option>");
						$option.append(i);
						$("#page").append($option);
					}
					$("#totalPage").text(totalPage);
					$("#nowPage").text(1);
				}
			})
		}
	})
})