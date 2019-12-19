//跳转页面查询条件之一
var payType0=0;
//根据付款方式TAB显示
function tab(payType){
	payType0=payType;
	$.ajax({
		url:"warehouse/stockoutTab",
		data:{
			"payType0":payType0,
		},
		type:"get",
		dataType:"json",
		async:true,
		success:function(results){
			$(".str").remove();
			$(".page").remove();
			var sos=results[0];
			for(var i=0;i<sos.length;i++){
				$td1=$('<td align="center"><a href="warehouse/stockoutDetail?soid='+sos[i].soid+'">'+sos[i].soid+'</a></td>');
				$td2=$('<td align="center">'+sos[i].createTime+'</td>');
				$td3=$('<td align="center">'+sos[i].customerName+'</td>');
				$td4=$('<td align="center">'+sos[i].userName+'</td>');
				$td5=$('<td align="center">'+sos[i].tipFee+'</td>');
				$td6=$('<td align="center">'+sos[i].productTotal+'</td>');
				$td7=$('<td align="center">'+sos[i].soTotal+'</td>');
				if(sos[i].payType==1){
					$td8=$('<td align="center">货到付款</td>');
				}else if(sos[i].payType==2){
					$td8=$('<td align="center">款到发货</td>');
				}else if(sos[i].payType==3){
					$td8=$('<td align="center">预付款到发货</td>');
				}
				$td9=$('<td align="center">'+sos[i].prePayFee+'</td>');
				if(sos[i].status==1){
					$td10=$('<td align="center">新增</td>');
				}else if(sos[i].status==3){
					$td10=$('<td align="center">已付款</td>');
				}else if(sos[i].status==5){
					$td10=$('<td align="center">已预付</td>');
				}
				$td11=$('<td align="center"><input type="button" value="出库"></td>');
				$tr=$("<tr class='str'></tr>")
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
				$tr.append($td11);
				$("#sos").append($tr);
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

$(function(){
	//出库
	$("table").on("click","[value=出库]",function(){
		var confirm=window.confirm("确认出库吗？");
		if(confirm){
			var soid=$(this).parent().parent().find("td").eq(0).text();
			var nowPage=$("#nowPage").text();
			$.ajax({
				url:"warehouse/stockoutOut",
				data:{
					"soid":soid,
					"nowPage":nowPage,
					"payType0":payType0
				},
				type:"get",
				dataType:"json",
				async:true,
				success:function(results){
					var status=results[2];
					if(status==0){
						alert("出库失败！！");
					}else if(status==1){
						$(".str").remove();
						$(".page").remove();
						var sos=results[0];
						for(var i=0;i<sos.length;i++){
							$td1=$('<td align="center"><a href="warehouse/stockoutDetail?soid='+sos[i].soid+'">'+sos[i].soid+'</a></td>');
							$td2=$('<td align="center">'+sos[i].createTime+'</td>');
							$td3=$('<td align="center">'+sos[i].customerName+'</td>');
							$td4=$('<td align="center">'+sos[i].userName+'</td>');
							$td5=$('<td align="center">'+sos[i].tipFee+'</td>');
							$td6=$('<td align="center">'+sos[i].productTotal+'</td>');
							$td7=$('<td align="center">'+sos[i].soTotal+'</td>');
							if(sos[i].payType==1){
								$td8=$('<td align="center">货到付款</td>');
							}else if(sos[i].payType==2){
								$td8=$('<td align="center">款到发货</td>');
							}else if(sos[i].payType==3){
								$td8=$('<td align="center">预付款到发货</td>');
							}
							$td9=$('<td align="center">'+sos[i].prePayFee+'</td>');
							if(sos[i].status==1){
								$td10=$('<td align="center">新增</td>');
							}else if(sos[i].status==3){
								$td10=$('<td align="center">已付款</td>');
							}else if(sos[i].status==5){
								$td10=$('<td align="center">已预付</td>');
							}
							$td11=$('<td align="center"><input type="button" value="出库"></td>');
							$tr=$("<tr class='str'></tr>")
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
							$tr.append($td11);
							$("#sos").append($tr);
						}
						var totalPage=results[1];
						for(var i=1;i<=parseInt(totalPage);i++){
							$option=$("<option class='page'></option>");
							$option.append(i);
							$("#page").append($option);
						}
						$("#totalPage").text(totalPage);
						if(nowPage>totalPage){
							$("#nowPage").text(totalPage);
						}
						alert("出库成功！！");
					}
				}
			})
		}
	})
	//跳转
	$("[value=跳转]").click(function(){
		var toPage=$("#page option:selected").text();
		$.ajax({
			url:"warehouse/stockoutTo",
			data:{
				"payType0":payType0,
				"toPage":toPage
			},
			type:"get",
			dataType:"json",
			async:true,
			success:function(results){
				$(".str").remove();
				var sos=results[0];
				for(var i=0;i<sos.length;i++){
					$td1=$('<td align="center"><a href="warehouse/stockoutDetail?soid='+sos[i].soid+'">'+sos[i].soid+'</a></td>');
					$td2=$('<td align="center">'+sos[i].createTime+'</td>');
					$td3=$('<td align="center">'+sos[i].customerName+'</td>');
					$td4=$('<td align="center">'+sos[i].userName+'</td>');
					$td5=$('<td align="center">'+sos[i].tipFee+'</td>');
					$td6=$('<td align="center">'+sos[i].productTotal+'</td>');
					$td7=$('<td align="center">'+sos[i].soTotal+'</td>');
					if(sos[i].payType==1){
						$td8=$('<td align="center">货到付款</td>');
					}else if(sos[i].payType==2){
						$td8=$('<td align="center">款到发货</td>');
					}else if(sos[i].payType==3){
						$td8=$('<td align="center">预付款到发货</td>');
					}
					$td9=$('<td align="center">'+sos[i].prePayFee+'</td>');
					if(sos[i].status==1){
						$td10=$('<td align="center">新增</td>');
					}else if(sos[i].status==3){
						$td10=$('<td align="center">已付款</td>');
					}else if(sos[i].status==5){
						$td10=$('<td align="center">已预付</td>');
					}
					$td11=$('<td align="center"><input type="button" value="出库"></td>');
					$tr=$("<tr class='str'></tr>")
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
					$tr.append($td11);
					$("#sos").append($tr);
				}
				$("#nowPage").text(toPage);
			}
		})
	})
})