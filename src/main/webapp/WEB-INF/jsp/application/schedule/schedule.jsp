<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<%@ taglib prefix="myshiro" uri="/customize-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>业务进度</title>
	<link href="${ctx}/assets/morris.js-0.4.3/morris.css" rel="stylesheet"><!-- MORRIS CHART CSS -->
	<link href="${ctx}/css/clndr.css" rel="stylesheet"><!-- CALENDER CSS -->
	<link href="${ctx}/css/style_self.css" rel="stylesheet">
	<!-- 查询页面 -->
    <link href="${ctx}/css/bootstrap.min.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="${ctx}/css/bootstrap-reset.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="${ctx}/assets/font-awesome/css/font-awesome.css" rel="stylesheet"><!-- FONT AWESOME ICON STYLESHEET -->
    <link href="${ctx}/assets/data-tables/DT_bootstrap.css" rel="stylesheet" ><!-- DATATABLE CSS -->
    <link href="${ctx}/css/style.css" rel="stylesheet"><!-- THEME BASIC CSS -->
    <link href="${ctx}/assets/fuelux/css/tree-style.css" rel="stylesheet">
    <link href="${ctx}/css/style-responsive.css" rel="stylesheet"><!-- THEME BASIC RESPONSIVE  CSS -->
<script src="${ctx}/js/jquery-1.8.3.min.js" ></script><!-- BASIC JS LIABRARY 1.8.3 -->
<script src="${ctx}/js/style_self.js"></script>
<script type="text/javascript">

var oTable = {
		"bFilter":true,
	    "bDestroy" : true,
	    "bRetrieve":true,
		"bProcessing": true,
	    "aLengthMenu": [
	                    [5, 15, 20, -1],
	                    [5, 15, 20, "All"]
	                ],
	    "iDisplayLength": 15,
	    "sDom": "<'row'<'col-lg-6'l><'col-lg-6'f>r>t<'row'<'col-lg-6'i><'col-lg-6'p>>",
	    "sPaginationType": "bootstrap",
	    "oLanguage": {
	        "sLengthMenu": "_MENU_ 条记录每页",
	        "sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
	        "oPaginate": {
	            "sPrevious": "上一页",
	            "sNext": "下一页"
	        }
	    },
	    "aoColumnDefs": [{
	        'bSortable': false,
	        'aTargets': [0]
	    }],

	}

	$(function(){
		getTasks(status);
		 $('#editable-sample').dataTable(oTable)
		$('#editable-sample tbody').on('click', 'tr', function () {//选中的行
			//获取选中行的第一列的值
			//alert($(this).children("td").eq(0).text());
			nRow = $(this).parent().find("tr").index($(this)[0]);
			//selectId=$(this).eq(nRow).children("td").eq(0).text();
			selectId=$(this).children("td").eq(0).text();
			taskId=$(this).children("td").eq(0).text();
			temptr.css("background-color","");
	        temptr = $(this);
	        temptr.css("background-color","#99ffff");
		});
	});
	
	function GetRightMenu(index,value) { 
	    var imageMenuData = new Array();
	    var imageMenuData2 = new Array(); 
	  	
	    imageMenuData.push({
	        "text": "查看", "func": function () {
	        	$("#dialog").click();
	        }
	    });
	    
	       imageMenuData2.push(imageMenuData);
	       
	       return imageMenuData2;
	}
	//以上是右键特效的js
	var temptr = $();
	var nRow = null;
	var selectId=null;
	var taskId=null;
	var status=null;
	window.onload=function(){//给表添加滚动条
		$("#editable-sample").wrap('<div style="overflow-x:auto;width:100%"></div>');
		SetTableStyle();
	};
	
	function SetTableStyle() {
		var aTable = $('#editable-sample').dataTable();
	    $("#editable-sample tbody tr").unbind("mouserover")
	    $("#editable-sample tbody tr").bind("mousedown", (function (e) {
	        if (e.which == 3) {
	            var opertionn = {
	                name: "",
	                offsetX: 2,
	                offsetY: 2,
	                textLimit: 30,
	                beforeShow: $.noop,
	                afterShow: $.noop
	            };
	            var menudata = GetRightMenu(e.target.cellIndex, e.target.innerHTML);
	            aTable.$('td.td_selected').removeClass('td_selected');
	            if ($(e.target).hasClass('td_selected')) {
	                $(e.target).removeClass('td_selected');
	            } else {
	                $(e.target).addClass('td_selected');
	            }
	            $.smartMenu.remove();
	            $(this).smartMenu(menudata, opertionn);
	        }
	         
	    }));
	    $("#editable-sample tbody tr").mouseover(function (e) { 
	        aTable.$('td.td_selected').removeClass('td_selected');
	        if ($(e.target).hasClass('td_selected')) {
	            $(e.target).removeClass('td_selected');
	        } else {
	            $(e.target).addClass('td_selected');
	        }
	        if ($(this).hasClass('row_selected')) {
	            $(this).removeClass('row_selected');
	        }
	        else {
	            aTable.$('tr.row_selected').removeClass('row_selected');
	            $(this).addClass('row_selected');
	        }
	    });
	}
	//处理null值
	function exchangeNull(data){
		if(data==null){
			data="";
		}else{
			data=data;
		}
		return data;
	}
	function getTasks(status){
		$("#result").empty();
		var begin=1;
		var end=16;
		param="begin="+begin+"&end="+end+"&status="+status;
		$.ajaxSetup({async : false});
		$.post(
			"${ctx}/application/getOngoing.action",
			param,
			function(data){
				console.log(data)
				$.each(data,function(i,item){
					var result="<tr>"
							+"<td style='display:none;'>"+item.id+"</td>"
							+"<td>"+(i+1)+"</td>";
							switch(item.status)
							{
							case 1:
								result+="<td>申请中</td>";
								break;
							case 2:
			             		result+="<td>申请退回</td>";
			             		break;
							case 3:
				             	result+="<td>业务受理中</td>";
				             	break;
							case 4:
				             	result+="<td>业务受理退回</td>";
				             	break;
							case 5:
				             	result+="<td>现场评审中</td>";
				             	break;
							case 6:
				             	result+="<td>现场评审退回</td>";
				             	break;
							case 7:
				             	result+="<td>录取样品信息中</td>";
				             	break;
							case 8:
				             	result+="<td>录取样品信息退回</td>";
				             	break;
							case 9:
				             	result+="<td>上传检验报告中</td>";
				             	break;
							case 10:
				             	result+="<td>上传检验报告退回</td>";
				             	break;
							case 11:
				             	result+="<td>分中心审批中</td>";
				             	break;
							case 12:
				             	result+="<td>中心核准中</td>";
				             	break;
							case 13:
				             	result+="<td>分中心审批退回</td>";
				             	break;
							case 14:
				             	result+="<td>中心批准中</td>";
				             	break;
							case 15:
				             	result+="<td>中心核准退回</td>";
				             	break;
							case 16:
				             	result+="<td>发证</td>";
				             	break;
							default:
							result+="<td></td>";
							}
							result+="<td>"+exchangeNull(item.titleno)+"</td>"
							+"<td>"+exchangeNull(item.branchName)+"</td>"
							+"<td>"+exchangeNull(item.enterprisename)+"</td>"
			             	+"<td>"+exchangeNull(item.enterprisekind)+"</td>"
			             	+"<td>"+exchangeNull(item.address)+"</td>"
			             	+"<td>"+exchangeNull(item.postalcode)+"</td>"
			             	+"<td>"+exchangeNull(item.businessno)+"</td>"
			             	+"<td>"+exchangeNull(item.registercapital)+"</td>"
			             	+"<td>"+exchangeNull(item.artificialperson)+"</td>"
			             	+"<td>"+exchangeNull(item.apjob)+"</td>"
			             	+"<td>"+exchangeNull(item.fax)+"</td>"
			             	+"<td>"+exchangeNull(item.aptel)+"</td>"
			             	+"<td>"+exchangeNull(item.linkman)+"</td>"
			             	+"<td>"+exchangeNull(item.ljob)+"</td>"
			             	+"<td>"+exchangeNull(item.ltel)+"</td>"
			             	+"<td>"+exchangeNull(item.mainbusiness)+"</td>"
			             	+"<td>"+exchangeNull(item.restbusiness)+"</td>"
			             	+"<td>"+exchangeNull(item.employeetotal)+"</td>"
			             	+"<td>"+exchangeNull(item.techniciantotal)+"</td>"
			             	+"<td>"+exchangeNull(item.bcprincipal)+"</td>"
			             	+"<td>"+exchangeNull(item.tpbusiness)+"</td>"
			             	+"<td>"+exchangeNull(item.tpopost)+"</td>";
			             	if(item.flat==true){
			             		result+="<td><input disabled='disabled' type='checkbox' checked='true'/></td>"
			             	}else{
			             		result+="<td><input disabled='disabled' type='checkbox'/></td>";
			             	}
			             	if(item.gravure==true){
			             		result+="<td><input disabled='disabled' type='checkbox' checked='true'/></td>";
			             	}else{
			             		result+="<td><input disabled='disabled' type='checkbox'/></td>";
			             	}
			             	if(item.webby==true){
			             		result+="<td><input disabled='disabled' type='checkbox' checked='true'/></td>";
			             	}else{
			             		result+="<td><input disabled='disabled' type='checkbox'/></td>";
			             	}
			             	if(item.flexible==true){
			             		result+="<td><input disabled='disabled' type='checkbox' checked='true'/></td>";
			             	}else{
			             		result+="<td><input disabled='disabled' type='checkbox'/></td>";
			             	}
			             	result+="<td>"+exchangeNull(item.elsetype)+"</td>";
			             	if(item.papery==true){
			             		result+="<td><input disabled='disabled' type='checkbox' checked='true'/></td>";
			             	}else{
			             		result+="<td><input disabled='disabled' type='checkbox'/></td>";
			             	}
			             	if(item.pastern==true){
			             		result+="<td><input disabled='disabled' type='checkbox' checked='true'/></td>";
			             	}else{
			             		result+="<td><input disabled='disabled' type='checkbox'/></td>";
			             	}
			             	if(item.frilling==true){
			             		result+="<td><input disabled='disabled' type='checkbox' checked='true'/></td>";
			             	}else{
			             		result+="<td><input disabled='disabled' type='checkbox'/></td>";
			             	}
			             	if(item.metal==true){
			             		result+="<td><input disabled='disabled' type='checkbox' checked='true'/></td>";
			             	}else{
			             		result+="<td><input disabled='disabled' type='checkbox'/></td>";
			             	}
			             	if(item.plastic==true){
			             		result+="<td><input disabled='disabled' type='checkbox' checked='checked'/></td>";
			             	}else{
			             		result+="<td><input disabled='disabled' type='checkbox'/></td>";
			             	}
			             	result+="<td>"+exchangeNull(item.elsematerial)+"</td>"
			             	/* +"<td>"+item.printEquipment+"</td>"
			             	+"<td>"+item.testEquipment+"</td>" */
			             	+"<td>"+exchangeNull(item.remarks)+"</td>"
			             	+"<td>"+exchangeNull(DateHandle(item.createdate))+"</td>"
// 			             	+"<td></td>"
// 			             	+"<td></td>"
			             	+"</tr>";
							$("#result").append(result);
							result="";
				});
			}
		);
	}
	Date.prototype.Format = function (fmt) { //author: meizz 
	    var o = {
	        "M+": this.getMonth() + 1, //月份 
	        "d+": this.getDate(), //日 
	        "h+": this.getHours(), //小时 
	        "m+": this.getMinutes(), //分 
	        "s+": this.getSeconds(), //秒 
	        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	        "S": this.getMilliseconds() //毫秒 
	    };
	    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	    for (var k in o)
	    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	    return fmt;
	}
	function DateHandle(obj) {  
		  if(obj!=null){
		    var objDate=new Date(obj).Format("yyyy-MM-dd"); //创建一个日期对象表示当前时间     
		    return objDate; 
		  }else{
		    	return null;
		  }
		} 
	function exportRecord(){
		window.open('${ctx}/log/exportRecord.action');	
	}
	
	function checkBusiness(){
		if(selectId==null){
			wzj.alert('温馨提示', '请选择数据！')
			return false;
		}else if(selectId==""){
			wzj.alert('温馨提示','数据为空！');
			return false;
		}else{
			$("#dialog").attr("href","#Modal_show");
			$("#onGoing").attr("src","${ctx}/application/business/view.action?id="+selectId);
		}
	}
</script>
</head>
<body>
               <section class="panel">
                  <div class="panel-body">
                     <div class="adv-table editable-table ">
                        <div class="clearfix">
                        	<div class="btn-group col-sm-2 nopaddingleft">
<%--                         	${ctx}/application/toApp.action --%>
<!--                                <button onclick="checkBusiness()" class="btn btn-info" data-toggle='modal' href='' id="dialog"> -->
<!--                             	查看 <i class="fa fa-eye"></i> -->
<!--                               </a> -->
                           </div>
                        </div>
                        
                        <div class="space15"></div>
                        <table class="table table-bordered" style="width:460%" id="editable-sample">
                           <thead>
                              <tr>
                                 <th class="" style="display:none;">任务编号</th>
                                 <th class="">编号</th>
                                 <th class="">当前进度</th>
                                 <th class="">流水号</th>
                                 <th class="">分支机构</th>
                                 <th class="">企业名称</th>
                                 <th class="">企业性质</th>
                                 <th class="">详细地址</th>
                                 <th class="">邮政编码</th>
                                 <th class="">营业执照号码</th>
                                 <th class="">注册资本</th>
                                 <th class="">法人代表</th>
                                 <th class="">法人职务</th>
                                 <th class="">传真</th>
                                 <th class="">法人电话</th>
                                 <th class="">联系人</th>
                                 <th class="">联系人职务</th>
                                 <th class="">联系人电话</th>
                                 <th class="">主营</th>
                                 <th class="">兼营</th>
                                 <th class="">职工人数</th>
                                 <th class="">技术人员数</th>
                                 <th class="">条码印刷技术负责人</th>
                                 <th class="">技术负责人职务</th>
                                 <th class="">技术负责人职称</th>
                                 <th class="">平板印刷</th>
                                 <th class="">凹版印刷</th>
                                 <th class="">丝网印刷</th>
                                 <th class="">柔性版印刷</th>
                                 <th class="">其他印刷</th>
                                 <th class="">纸质</th>
                                 <th class="">不干胶</th>
                                 <th class="">瓦楞纸</th>
                                 <th class="">金属</th>
                                 <th class="">塑料</th>
                                 <th class="">其他材料</th>
                                 <!-- <th class="">主要印刷设备</th>
                                 <th class="">条码检测设备</th> -->
                                 <th class="">备注</th>
                                 <th class="">创建日期</th>
<!--                                  <th class="">发证日期</th> -->
<!--                                  <th class="">到期日期</th> -->
                              </tr>
                           </thead>
                           <tbody id="result">
                              <!-- 循环显示查询出来的结果 -->
                             
                           </tbody>
                        </table>
                     </div>
                  </div>
               </section>
                <div id="Modal_show" class="modal fade">
          <div class="modal-dialog">
            <div class="modal-content" style="height: 600px;width:150%;margin-left: -25%;">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                  &times;
                </button>
                <h4 class="modal-title">新申请业务进度</h4>
              </div>
              <div class="modal-body" style="height:80%">
                <iframe id="onGoing" src="" frameborder="0" width="100%" scrolling="No" height="80%" leftmargin="0" topmargin="0"></iframe>
	         </div>
	         <div class="modal-footer" style="margin-top:0px;">
                <button data-dismiss="modal" class="btn btn-success" type="button">
                 	确定
                </button>
              </div>
              </div>
            </div>
          </div>
	 	 <!-- BEGIN JS -->
    <script src="${ctx}/js/jquery.sparkline.js"></script><!-- SPARKLINE JS -->
    <script src="${ctx}/js/sparkline-chart.js"></script><!-- SPARKLINE CHART JS -->
    <script src="${ctx}/js/count.js"></script><!-- COUNT JS -->
	<script src="${ctx}/js/bootstrap.min.js" ></script><!-- BOOTSTRAP JS  -->
	<script src="${ctx}/js/jquery.dcjqaccordion.2.7.js"></script><!-- ACCORDING JS -->
	<script src="${ctx}/js/jquery.scrollTo.min.js" ></script><!-- SCROLLTO JS  -->
	<script src="${ctx}/js/jquery.nicescroll.js" > </script><!-- NICESCROLL JS  -->
	<script src="${ctx}/assets/data-tables/jquery.dataTables.js"></script><!-- DATATABLE JS  -->
	<script src="${ctx}/assets/data-tables/DT_bootstrap.js"></script><!-- DATATABLE JS  -->
	<script src="${ctx}/js/respond.min.js" ></script><!-- RESPOND JS  -->
	<script src="${ctx}/js/common-scripts.js" ></script><!-- BASIC COMMON JS  -->
     <!-- EDITABLE TABLE FUNCTION  -->
       <script>
        jQuery(document).ready(function() {
        	addall("all")
            //$('#editable-sample_length').append(str)
 			$('.sorting').css('width','')
        }); 
        function addall(status){
        	 $('#editable-sample_wrapper .col-lg-6').addClass('col-sm-4').removeClass('col-lg-6')
             $('#editable-sample_wrapper .col-sm-4:last').addClass('col-lg-6').removeClass('col-sm-4')
             $('#editable-sample_wrapper .col-sm-4:last').addClass('col-lg-6').removeClass('col-sm-4')
             var str = '<div class="col-sm-4" style="padding:15px 0"><lable> 业务进度查询：</lable><select id="all" onchange="change()">'+
             		'<option value="all">全部</option><option value="1">申请中</option>'+
             		'<option value="2">申请退回</option><option value="3">业务受理中</option>'+
 					'<option value="4">业务受理退回</option><option value="5">现场评审中</option>'+
 					'<option value="6">现场评审退回</option>'+
 					'<option value="7">录取样品信息中</option><option value="8">录取样品信息退回</option>'+
 					'<option value="9">上传检验报告中</option><option value="10">上传检验报告退回</option>'+
 					'<option value="11">分中心审批中</option><option value="13">分中心审批退回</option>'+
 					'<option value="12">中心核准中</option><option value="15">中心核准退回</option>'+
 					'<option value="14">中心批准中</option><option value="16">发证</option></select></div>'
 					$('#editable-sample_wrapper .col-sm-4:eq(0)').after(str)
 			var all=document.getElementById("all");

 			$.each(all.options, function(i,n){      
 				if($(n).val()==status){
 					$(n).attr("selected",true);
 	 			}
 			})
        }
        function change(){ 
         var all=document.getElementById("all");
       		 status=all.options[all.selectedIndex].value;
       		$('#editable-sample').dataTable().fnClearTable();
       		$('#editable-sample').dataTable().fnDestroy();
       		 getTasks(status);
       		$('#editable-sample').dataTable(oTable);
       		addall(status)
       		$("#editable-sample").wrap('<div style="overflow-x:auto;width:100%"></div>');
    		SetTableStyle();
    		$('.sorting').css('width','')
       	}
     </script>
  <!-- END JS --> 
</body>
</html>