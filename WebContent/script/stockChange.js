//跳转页面查询条件之一 默认2，入库0，出库1
var stockStatus=2;
//根据库存方式TAB显示
function tab(stockStatus1){
	stockStatus=stockStatus1;
	var productCode=$("#productCode").text();
	$.ajax({
		url:"warehouse/stockChangeTab",
		data:{
			"stockStatus":stockStatus,
			"productCode":productCode
		},
		type:"get",
		dataType:"json",
		async:true,
		success:function(results){
			$(".srtr").remove();
			$(".page").remove();
			var srs=results[0];
			for(var i=0;i<srs.length;i++){
				$td1=$('<td align="center">'+srs[i].stockTime+'</td>');
				$td2=$('<td align="center">'+srs[i].orderCode+'</td>');
				$td3=$('<td align="center">'+srs[i].createUser+'</td>');
				$td4=$('<td align="center">'+srs[i].stockNum+'</td>');
				if(srs[i].stockType==1){
					$td5=$('<td align="center">采购入库</td>');
				}else if(srs[i].stockType==2){
					$td5=$('<td align="center">销售出库</td>');
				}else if(srs[i].stockType==3){
					$td5=$('<td align="center">盘点入库</td>');
				}else if(srs[i].stockType==4){
					$td5=$('<td align="center">盘点出库</td>');
				}
				$tr=$("<tr class='srtr'></tr>")
				$tr.append($td1);
				$tr.append($td2);
				$tr.append($td3);
				$tr.append($td4);
				$tr.append($td5);
				$("#srs").append($tr);
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
	//跳转
	$("[value=跳转]").click(function(){
		var toPage=$("#page option:selected").text();
		var productCode=$("#productCode").text();
		$.ajax({
			url:"warehouse/stockChangeTo",
			data:{
				"stockStatus":stockStatus,
				"toPage":toPage,
				"productCode":productCode
			},
			type:"get",
			dataType:"json",
			async:true,
			success:function(results){
				$(".srtr").remove();
				var srs=results[0];
				for(var i=0;i<srs.length;i++){
					$td1=$('<td align="center">'+srs[i].stockTime+'</td>');
					$td2=$('<td align="center">'+srs[i].orderCode+'</td>');
					$td3=$('<td align="center">'+srs[i].createUser+'</td>');
					$td4=$('<td align="center">'+srs[i].stockNum+'</td>');
					if(srs[i].stockType==1){
						$td5=$('<td align="center">采购入库</td>');
					}else if(srs[i].stockType==2){
						$td5=$('<td align="center">销售出库</td>');
					}else if(srs[i].stockType==3){
						$td5=$('<td align="center">盘点入库</td>');
					}else if(srs[i].stockType==4){
						$td5=$('<td align="center">盘点出库</td>');
					}
					$tr=$("<tr class='srtr'></tr>")
					$tr.append($td1);
					$tr.append($td2);
					$tr.append($td3);
					$tr.append($td4);
					$tr.append($td5);
					$("#srs").append($tr);
				}
				$("#nowPage").text(toPage);
			}
		})
	})
})