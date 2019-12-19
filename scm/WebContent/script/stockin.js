//跳转页面查询条件之一
var payType0=0;
//根据付款方式TAB显示
function tab(payType){
	payType0=payType;
	$.ajax({
		url:"warehouse/stockinTab",
		data:{
			"payType0":payType0,
		},
		type:"get",
		dataType:"json",
		async:true,
		success:function(results){
			$(".ptr").remove();
			$(".page").remove();
			var pos=results[0];
			for(var i=0;i<pos.length;i++){
				$td1=$('<td align="center"><a href="warehouse/stockinDetail?poid='+pos[i].poid+'">'+pos[i].poid+'</a></td>');
				$td2=$('<td align="center">'+pos[i].createTime+'</td>');
				$td3=$('<td align="center">'+pos[i].venderName+'</td>');
				$td4=$('<td align="center">'+pos[i].userName+'</td>');
				$td5=$('<td align="center">'+pos[i].tipFee+'</td>');
				$td6=$('<td align="center">'+pos[i].productTotal+'</td>');
				$td7=$('<td align="center">'+pos[i].poTotal+'</td>');
				if(pos[i].payType==1){
					$td8=$('<td align="center">货到付款</td>');
				}else if(pos[i].payType==2){
					$td8=$('<td align="center">款到发货</td>');
				}else if(pos[i].payType==3){
					$td8=$('<td align="center">预付款到发货</td>');
				}
				$td9=$('<td align="center">'+pos[i].prePayFee+'</td>');
				if(pos[i].status==1){
					$td10=$('<td align="center">新增</td>');
				}else if(pos[i].status==3){
					$td10=$('<td align="center">已付款</td>');
				}else if(pos[i].status==5){
					$td10=$('<td align="center">已预付</td>');
				}
				$td11=$('<td align="center"><input type="button" value="入库"></td>');
				$tr=$("<tr class='ptr'></tr>")
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
				$("#pos").append($tr);
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
	//入库
	$("table").on("click","[value=入库]",function(){
		var confirm=window.confirm("确认入库吗？");
		if(confirm){
			var poid=$(this).parent().parent().find("td").eq(0).text();
			var nowPage=$("#nowPage").text();
			$.ajax({
				url:"warehouse/stockinIn",
				data:{
					"poid":poid,
					"nowPage":nowPage,
					"payType0":payType0
				},
				type:"get",
				dataType:"json",
				async:true,
				success:function(results){
					var status=results[2];
					if(status==0){
						alert("入库失败！！");
					}else if(status==1){
						$(".ptr").remove();
						$(".page").remove();
						var pos=results[0];
						for(var i=0;i<pos.length;i++){
							$td1=$('<td align="center"><a href="warehouse/stockinDetail?poid='+pos[i].poid+'">'+pos[i].poid+'</a></td>');
							$td2=$('<td align="center">'+pos[i].createTime+'</td>');
							$td3=$('<td align="center">'+pos[i].venderName+'</td>');
							$td4=$('<td align="center">'+pos[i].userName+'</td>');
							$td5=$('<td align="center">'+pos[i].tipFee+'</td>');
							$td6=$('<td align="center">'+pos[i].productTotal+'</td>');
							$td7=$('<td align="center">'+pos[i].poTotal+'</td>');
							if(pos[i].payType==1){
								$td8=$('<td align="center">货到付款</td>');
							}else if(pos[i].payType==2){
								$td8=$('<td align="center">款到发货</td>');
							}else if(pos[i].payType==3){
								$td8=$('<td align="center">预付款到发货</td>');
							}
							$td9=$('<td align="center">'+pos[i].prePayFee+'</td>');
							if(pos[i].status==1){
								$td10=$('<td align="center">新增</td>');
							}else if(pos[i].status==3){
								$td10=$('<td align="center">已付款</td>');
							}else if(pos[i].status==5){
								$td10=$('<td align="center">已预付</td>');
							}
							$td11=$('<td align="center"><input type="button" value="入库"></td>');
							$tr=$("<tr class='ptr'></tr>")
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
							$("#pos").append($tr);
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
						alert("入库成功！！");
					}
				}
			})
		}
	})
	//跳转
	$("[value=跳转]").click(function(){
		var toPage=$("#page option:selected").text();
		$.ajax({
			url:"warehouse/stockinTo",
			data:{
				"payType0":payType0,
				"toPage":toPage
			},
			type:"get",
			dataType:"json",
			async:true,
			success:function(results){
				$(".ptr").remove();
				var pos=results[0];
				for(var i=0;i<pos.length;i++){
					$td1=$('<td align="center"><a href="warehouse/stockinDetail?poid='+pos[i].poid+'">'+pos[i].poid+'</a></td>');
					$td2=$('<td align="center">'+pos[i].createTime+'</td>');
					$td3=$('<td align="center">'+pos[i].venderName+'</td>');
					$td4=$('<td align="center">'+pos[i].userName+'</td>');
					$td5=$('<td align="center">'+pos[i].tipFee+'</td>');
					$td6=$('<td align="center">'+pos[i].productTotal+'</td>');
					$td7=$('<td align="center">'+pos[i].poTotal+'</td>');
					if(pos[i].payType==1){
						$td8=$('<td align="center">货到付款</td>');
					}else if(pos[i].payType==2){
						$td8=$('<td align="center">款到发货</td>');
					}else if(pos[i].payType==3){
						$td8=$('<td align="center">预付款到发货</td>');
					}
					$td9=$('<td align="center">'+pos[i].prePayFee+'</td>');
					if(pos[i].status==1){
						$td10=$('<td align="center">新增</td>');
					}else if(pos[i].status==3){
						$td10=$('<td align="center">已付款</td>');
					}else if(pos[i].status==5){
						$td10=$('<td align="center">已预付</td>');
					}
					$td11=$('<td align="center"><input type="button" value="入库"></td>');
					$tr=$("<tr class='ptr'></tr>")
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
					$("#pos").append($tr);
				}
				$("#nowPage").text(toPage);
			}
		})
	})
})