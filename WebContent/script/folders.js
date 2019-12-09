///////////////////////////////////////////////////////////////// INIT ////////////////////////////////////////////////////////////

//  Init!

var OutlookBar={
"format":
   {"heightpanel":25, "imagewidth":50, "imageheight":50, "arrowheight":17,"heightcell":100,"coloroutlook":"#DBEAF5","arrange_text":"bottom", "rollback":false, "img_arrows_up":"images/pic/arup2.gif","img_arrows_dn":"images/pic/ardn2.gif"},
"panels":
[
	{"text":"业务报表", "panelCSS":"panel" ,"textCSS":"textpanel",
		"items":
			[
				{"text":"月度出库报表","textCSS":"a1", "image":'images/pic/13.gif',    "url":'warehouse/stockoutMonth.jsp', "classCSS":"imgstyle", "overclassCSS":"imgstyle_over"},
				{"text":"月度入库报表","textCSS":"a1", "image":'images/pic/13.gif',    "url":'warehouse/stockinMonth.jsp', "classCSS":"imgstyle", "overclassCSS":"imgstyle_over"},
				{"text":"产品库存报表","textCSS":"a1", "image":'images/pic/13.gif',    "url":'warehouse/stockMonth.jsp', "classCSS":"imgstyle", "overclassCSS":"imgstyle_over"},
				{"text":"月度采购报表","textCSS":"a1", "image":'images/pic/11.gif',    "url":'purchase/spxx.htm', "classCSS":"imgstyle", "overclassCSS":"imgstyle_over"},
				{"text":"月度收支登记表","textCSS":"a1", "image":'images/pic/12.gif',    "url":'finance/splb.htm', "classCSS":"imgstyle", "overclassCSS":"imgstyle_over"},
				{"text":"月度销售报表","textCSS":"a1", "image":'images/pic/13.gif',    "url":'sale/dlqy.htm', "classCSS":"imgstyle", "overclassCSS":"imgstyle_over"}
			]
	},
	{"text":"仓储管理", "panelCSS":"panel" ,"textCSS":"textpanel",
		"items":
			[
				{"text":"库存查询","textCSS":"a1", "image":'images/pic/10.gif',    "url":'warehouse/stockSearch', "classCSS":"imgstyle", "overclassCSS":"imgstyle_over"},
				{"text":"出库登记","textCSS":"a1", "image":'images/pic/9.gif',    "url":'warehouse/stockout', "classCSS":"imgstyle", "overclassCSS":"imgstyle_over"},
				{"text":"入库登记","textCSS":"a1", "image":'images/pic/8.gif',    "url":'warehouse/stockin', "classCSS":"imgstyle", "overclassCSS":"imgstyle_over"},
				{"text":"产品管理","textCSS":"a1", "image":'images/pic/12.gif',    "url":'warehouse/product', "classCSS":"imgstyle", "overclassCSS":"imgstyle_over"},
				{"text":"产品分类管理","textCSS":"a1", "image":'images/pic/11.gif',    "url":'warehouse/category', "classCSS":"imgstyle", "overclassCSS":"imgstyle_over"}
			]
	},
   {"text":"系统管理", "panelCSS":"panel" ,"textCSS":"textpanel",
       "items":
       [
          {"text":"用户管理","textCSS":"a1", "image":'images/pic/1.gif',    "url":'system/scmuser.html', "classCSS":"imgstyle", "overclassCSS":"imgstyle_over"},
//          {"text":"退出系统","textCSS":"a1", "image":'images/pic/3.gif',    "url":'#', "classCSS":"imgstyle", "overclassCSS":"imgstyle_over"}
          
      ]
   },
    {"text":"采购管理", "panelCSS":"panel" ,"textCSS":"textpanel",
       "items":
       [
          {"text":"供应商管理","textCSS":"a1", "image":'images/pic/1.gif',    "url":'purchase/pomain.html', "classCSS":"imgstyle", "overclassCSS":"imgstyle_over"},
          {"text":"新添采购单","textCSS":"a1","image":'images/pic/2.gif',    "url":'purchase/pomain.html', "classCSS":"imgstyle", "overclassCSS":"imgstyle_over"},
          {"text":"采购单了结","textCSS":"a1", "image":'images/pic/3.gif',    "url":'purchase/pomain_end.html', "classCSS":"imgstyle", "overclassCSS":"imgstyle_over"},
          {"text":"采购单查询","textCSS":"a1", "image":'images/pic/4.gif',    "url":'purchase/js.htm', "classCSS":"imgstyle", "overclassCSS":"imgstyle_over"},
          
      ]
   },
   {"text":"财务收支", "panelCSS":"panel" ,"textCSS":"textpanel",
       "items":
       [
          {"text":"收款登记","textCSS":"a1", "image":'images/pic/11.gif',    "url":'finance/inMoney.htm', "classCSS":"imgstyle", "overclassCSS":"imgstyle_over"},
          {"text":"付款登记","textCSS":"a1", "image":'images/pic/12.gif',    "url":'finance/splb.htm', "classCSS":"imgstyle", "overclassCSS":"imgstyle_over"},
          {"text":"收支查询","textCSS":"a1", "image":'images/pic/13.gif',    "url":'finance/dlqy.htm', "classCSS":"imgstyle", "overclassCSS":"imgstyle_over"},
          {"text":"库存盘点","textCSS":"a1", "image":'images/pic/9.gif',    "url":'finance/count.htm', "classCSS":"imgstyle", "overclassCSS":"imgstyle_over"}
         
      ]
   }
   ,
   {"text":"销售管理", "panelCSS":"panel" ,"textCSS":"textpanel",
       "items":
       [
          
          {"text":"客户管理","textCSS":"a1", "image":'images/pic/13.gif',    "url":'sale/dlqy.htm', "classCSS":"imgstyle", "overclassCSS":"imgstyle_over"}
		  ,
		   {"text":"新添销售单","textCSS":"a1", "image":'images/pic/13.gif',    "url":'sale/addSale.htm', "classCSS":"imgstyle", "overclassCSS":"imgstyle_over"}
		   ,
		    {"text":"销售单了结","textCSS":"a1", "image":'images/pic/13.gif',    "url":'sale/dlqy.htm', "classCSS":"imgstyle", "overclassCSS":"imgstyle_over"}
		,
			  {"text":"销售单查询","textCSS":"a1", "image":'images/pic/13.gif',    "url":'sale/dlqy.htm', "classCSS":"imgstyle", "overclassCSS":"imgstyle_over"}
         
      ]
   }
]
}