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
				url:"warehouse/stockMonthTo",
				data:{
					"month":month,
					"toPage":toPage
				},
				dataType:"json",
				type:"get",
				async:true,
				success:function(results){
					$(".ptr").remove();
					$(".page").remove();
					var ps=results[0];
					for(var i=0;i<ps.length;i++){
						$td1=$('<td align="center">'+ps[i].productCode+'</td>');
						$td2=$('<td align="center">'+ps[i].name+'</td>');
						$td3=$('<td align="center">'+ps[i].poNum+'</td>');
						$td4=$('<td align="center">'+ps[i].soNum+'</td>');
						$td5=$('<td align="center">'+ps[i].num+'</td>');
						$tr=$("<tr class='ptr'></tr>")
						$tr.append($td1);
						$tr.append($td2);
						$tr.append($td3);
						$tr.append($td4);
						$tr.append($td5);
						$("#ps").append($tr);
					}
					var totalPage=results[1];
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
				url:"warehouse/stockMonthSearch",
				data:{
					"month":month
				},
				dataType:"json",
				type:"get",
				async:true,
				success:function(results){
					$(".ptr").remove();
					$(".page").remove();
					var ps=results[0];
					for(var i=0;i<ps.length;i++){
						$td1=$('<td align="center">'+ps[i].productCode+'</td>');
						$td2=$('<td align="center">'+ps[i].name+'</td>');
						$td3=$('<td align="center">'+ps[i].poNum+'</td>');
						$td4=$('<td align="center">'+ps[i].soNum+'</td>');
						$td5=$('<td align="center">'+ps[i].num+'</td>');
						$tr=$("<tr class='ptr'></tr>")
						$tr.append($td1);
						$tr.append($td2);
						$tr.append($td3);
						$tr.append($td4);
						$tr.append($td5);
						$("#ps").append($tr);
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
			})
		}
	})
})