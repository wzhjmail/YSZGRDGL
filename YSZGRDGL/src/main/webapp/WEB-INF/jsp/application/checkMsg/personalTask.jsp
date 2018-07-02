<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<%@ taglib prefix="myshiro" uri="/customize-tags" %>
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编码中心核准</title>
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
$(function(){
	getTasks();
	 $("#psb").click(
		function(){
			if(nRow==null){
				wzj.alert('温馨提示','请选择一个任务！');
			}else if(selectId==""){
				wzj.alert('温馨提示','数据为空！');
				return false;
			}else{
				$("#toShow").click();
			}
		}		
	); 
	$("#editable-sample tbody tr").dblclick(function(e){
		showAll();
	});
	
	$('#editable-sample tbody').on('click', 'tr', function () {//选中的行
		//获取选中行的第一列的值
		nRow = $(this).parent().find("tr").index($(this)[0]);
		selectId=$(this).children("td").eq(1).text();
		taskId1=$(this).children("td").eq(0).text();
		selectNo=$(this).children("td").eq(4).text();
		comName=$(this).children("td").eq(6).text();
		temptr.css("background-color","");
        temptr = $(this);
        temptr.css("background-color","#99ffff");
	});
});//初始化
function logout(){
	wzj.confirm('温馨提醒', '您确定退出本系统吗?', function(flag) {
		if(flag) {
			location.href='${ctx}/logout.action';
		}
	});
}

function showAll(){
	if(nRow==null){
	wzj.alert('温馨提示','请选择一个任务！');
	}else if(selectId==""){
	wzj.alert('温馨提示','数据为空！');
	return false;
	}else{
	location.href='${ctx}/application/expressMessage/ratify.action?titleNo='+selectNo
			+'&appId='+selectId+'&taskId='+taskId1+'&comName='+comName;
	}
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
//处理null值
function exchangeNull(data){
	if(data==null){
		data="";
	}else{
		data=data;
	}
	return data;
}
function SetTableStyle() {
	var oTable = $('#editable-sample').dataTable();
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
            oTable.$('td.td_selected').removeClass('td_selected');
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
        oTable.$('td.td_selected').removeClass('td_selected');
        if ($(e.target).hasClass('td_selected')) {
            $(e.target).removeClass('td_selected');
        } else {
            $(e.target).addClass('td_selected');
        }
        if ($(this).hasClass('row_selected')) {
            $(this).removeClass('row_selected');
        }
        else {
            oTable.$('tr.row_selected').removeClass('row_selected');
            $(this).addClass('row_selected');
        }
    });
}
function GetRightMenu(index,value) { 
  
    var imageMenuData = new Array();
    var imageMenuData2 = new Array(); 
    imageMenuData.push({
        "text": "查看", "func": function () {
        	$("#dialog").click();
        }
    }); 
    imageMenuData.push({
        "text": "快递", "func": function () {
        	if(selectId==null){
        		wzj.alert('温馨提示', '请选择数据！')
        		return false;
        	}else if(selectId==""){
        		wzj.alert('温馨提示','数据为空！');
        		return false;
        	}else{
        		$("#dialog1").attr("href","#Modal_show2");
   				$("#dialog1").click();
   			}	
        }
    });
    imageMenuData.push({
        "text": "审核", "func": function () {
   			if(nRow==null){
   				wzj.alert('温馨提示','请选择一个任务！');
   			}else if(selectId==""){
        		wzj.alert('温馨提示','数据为空！');
        		return false;
        	}else{
   				$("#toShow").click();
   			}	
        }
    });
    imageMenuData.push({
        "text": "查看意见", "func": function () {
        	if(nRow==null){
				wzj.alert('温馨提示','请选择一个任务！');
			}else if(selectId==""){
        		wzj.alert('温馨提示','数据为空！');
        		return false;
        	}else{
				$("#suggest").attr("href","#Modal_show3");
				$("#suggest").click();
			}
        }
    });
    imageMenuData.push({
        "text": "申请表下载", "func": function () {
        	if(nRow==null){
				wzj.alert('温馨提示','请选择一个任务！');
			}else if(selectId==""){
				wzj.alert('温馨提示','数据为空！');
				return false;
			}else{
				exportPDF();
			}
        }
    });
    imageMenuData.push({
        "text": "评审表下载", "func": function () {
        	if(nRow==null){
    			wzj.alert('温馨提示','请选择一个任务！');
    		}else if(selectId==""){
    			wzj.alert('温馨提示','数据为空！');
    			return false;
    		}else{
    			$("#pdf").click();
    		}
        }
        }); 
    imageMenuData2.push(imageMenuData);
    return imageMenuData2;
}
//以上是右键特效的js
var temptr = $();
var nRow = null;//选中的行数
var selectId=null;//选中的业务编号
var taskId1=null;//选中的任务编号
var comName=null;//选中的企业名称
var selectNo=null;//选中的流水号
var comName=null;//企业名称
window.onload=function(){//给表添加滚动条
	$("#editable-sample").wrap('<div style="overflow-x:auto;width:100%"></div>');
	SetTableStyle();
};
function getTasks(){
	$.ajaxSetup({async : false});
	$.post(
		"${ctx}/application/common/getAllTask.action",
		"pointId=zlsh",
		function(data){
			$.each(data,function(i,item){
				var result="<tr ";
				if(item.status==15)
				result+="style='color:red'"
				result+="><td style='display:none;'>"+item.taskId+"</td>"
						+"<td style='display:none;'>"+item.id+"</td>"
						+"<td style='display:none;'>"+item.companyId+"</td>"
						+"<td>"+(i+1)+"</td>"
						+"<td>"+exchangeNull(item.titleno)+"</td>"
						+"<td>"+item.branchName+"</td>"
		             	+"<td>"+item.enterprisename+"</td>"
		             	+"<td>"+item.enterprisekind+"</td>"
		             	+"<td>"+item.address+"</td>"
		             	+"<td>"+item.postalcode+"</td>"
		             	+"<td></td>"
		             	+"<td></td>"
		             	+"<td>"+item.businessno+"</td>"
		             	+"<td>"+item.registercapital+"</td>"
		             	+"<td>"+item.artificialperson+"</td>"
		             	+"<td>"+item.apjob+"</td>"
		             	+"<td>"+item.fax+"</td>"
		             	+"<td>"+item.aptel+"</td>"
		             	+"<td>"+item.linkman+"</td>"
		             	+"<td>"+item.ljob+"</td>"
		             	+"<td>"+item.ltel+"</td>"
		             	+"<td>"+item.mainbusiness+"</td>"
		             	+"<td>"+item.restbusiness+"</td>"
		             	+"<td>"+item.employeetotal+"</td>"
		             	+"<td>"+item.techniciantotal+"</td>"
		             	+"<td>"+item.bcprincipal+"</td>"
		             	+"<td>"+item.tpbusiness+"</td>"
		             	+"<td>"+item.tpopost+"</td>"
		             	+"<td><input disabled='disabled' type='checkbox' "+(item.flat==true?"checked='true'":"")+" readonly='readonly'/></td>"
		             	+"<td><input disabled='disabled' type='checkbox' "+(item.gravure==true?"checked='true'":"")+"/></td>"
		             	+"<td><input disabled='disabled' type='checkbox' "+(item.webby==true?"checked='true'":"")+"/></td>"
		             	+"<td><input disabled='disabled' type='checkbox' "+(item.flexible==true?"checked='true'":"")+"/></td>"
		             	+"<td>"+exchangeNull(item.elsetype)+"</td>"
		             	+"<td><input disabled='disabled' type='checkbox' "+(item.papery==true?"checked='true'":"")+"/></td>"
		             	+"<td><input disabled='disabled' type='checkbox' "+(item.pastern==true?"checked='true'":"")+"/></td>"
		             	+"<td><input disabled='disabled' type='checkbox' "+(item.frilling==true?"checked='true'":"")+"/></td>"
		             	+"<td><input disabled='disabled' type='checkbox' "+(item.metal==true?"checked='true'":"")+"/></td>"
		             	+"<td><input disabled='disabled' type='checkbox' "+(item.plastic==true?"checked='true'":"")+"/></td>"
		             	+"<td>"+exchangeNull(item.elsematerial)+"</td>"
		             	/* +"<td>"+item.printEquipment+"</td>"
		             	+"<td>"+item.testEquipment+"</td>" */
		             	+"<td>"+item.remarks+"</td>"
		             	+"<td>"+DateHandle(item.createdate)+"</td>"
// 		             	+"<td></td>"
// 		             	+"<td></td>"
		             	+"</tr>";
						$("#result").append(result);
						result="";
			});
		}
	);
	
}
function view(){
	if(selectId==null){
		wzj.alert('温馨提示', '请选择数据！')
		return false;
	}else if(selectId==""){
		wzj.alert('温馨提示','数据为空！');
		return false;
	}else{
		//$("#dialog").attr("href","#Modal_show1");
		// $("#person").attr("src","${ctx}/application/view.action?id="+selectId);
		location.href = "${ctx}/application/view.action?id="+selectId
	}
}
function downZip(){
	if(selectId==null){
		wzj.alert('温馨提示', '请选择一个任务！')
		return false;
	}else if(selectId==""){
		wzj.alert('温馨提示','数据为空！');
		return false;
	}else{
		window.open("${ctx}/commons/download.action?titleNo="+selectNo);
	}
}
function show(){
	if(selectId==null){
		wzj.alert('温馨提示', '请选择一条数据！')
		return false;
	}else if(selectId==""){
		wzj.alert('温馨提示','数据为空！');
		return false;
	}else{
		$.post(
			"${ctx}/application/expressMessage/getExpress.action",
			"companyId="+selectId,
			function(data){
				$("#name").html(data.name);
				$("#num").html(data.number);
				$("#concat").html(data.contact);
				$("#tel").html(data.phoneNumber);
			}
		);
		$("#dialog1").attr("href","#Modal_show2");
	}
}
//查看意见
function getComment(){
	if(selectId==null){
		wzj.alert('温馨提示', '请选择数据！')
		return false;
	}else if(selectId==""){
		wzj.alert('温馨提示','数据为空！');
		return false;
	}else{
		$("#suggest").attr("href","#Modal_show3");
		var taskId=taskId1;
		var companyName=comName;
		$("#comment1").empty();//清空
		$("#comment1").append("<tr><td>时间</td><td>意见人</td><td>意见</td></tr>");
		$.post(
			"${ctx}/application/getComment",
			"taskId="+taskId+"&companyName="+companyName,
			function(data){
				$.each(data,function(i,item){
					var str=DateHandle(item.time);
					$("#comment1").append(
						"<tr><td>"+str+"</td><td>"
						+item.userId+"</td><td>"
						+item.fullMessage+"</td></tr>");
				});
			}
		);
	}
}
function exportPDF(){
	if(nRow==null){
		wzj.alert('温馨提示','请选择一个任务！');
	}else if(selectId==""){
		wzj.alert('温馨提示','数据为空！');
		return false;
	}else{
	window.open('${ctx}/common/exportPDF.action?type=1&id='+selectId);	
	}
}
function savePdf(){
	if(nRow==null){
		wzj.alert('温馨提示','请选择一个任务！');
	}else if(selectId==""){
		wzj.alert('温馨提示','数据为空！');
		return false;
	}else{
		window.open('${ctx}/common/downPdf.action?type=1&titleNo='+selectNo);	
	}
}
function saveReview(){
	if(nRow==null){
		wzj.alert('温馨提示','请选择一个任务！');
	}else if(selectId==""){
		wzj.alert('温馨提示','数据为空！');
		return false;
	}else{
		window.open('${ctx}/common/downPdf.action?type=4&titleNo='+selectNo);	
	}
}
function viewReview(){
	if(nRow==null){
		wzj.alert('温馨提示','请选择一个任务！');
	}else if(selectId==""){
		wzj.alert('温馨提示','数据为空！');
		return false;
	}else{
		$('#viewR').prop('href','../../viewPDF.jsp?code=xb_ps_'+selectId);
	}
}
function showSample(){
	if(nRow==null){
		wzj.alert('温馨提示','请选择一个任务！');
	}else if(selectId==""){
		wzj.alert('温馨提示','数据为空！');
		return false;
	}else{
		location.href ='${ctx}/application/report/toShowSample.action?comName='+comName;
	}
}
// //盖章申请表下载
// function saveSQ(){
// 	if(nRow==null){
// 		wzj.alert('温馨提示','请选择一个任务！');
// 	}else if(selectId==""){
// 		wzj.alert('温馨提示','数据为空！');
// 		return false;
// 	}else{
// 		window.open('${ctx}/common/downSeal.action?type=1&titleNo='+selectNo+'&appId='+selectId);	
// 	}
// }

// //盖章评审表下载
// function savePS(){
// 	if(nRow==null){
// 		wzj.alert('温馨提示','请选择一个任务！');
// 	}else if(selectId==""){
// 		wzj.alert('温馨提示','数据为空！');
// 		return false;
// 	}else{
// 		window.open('${ctx}/common/downSeal.action?type=2&titleNo='+selectNo+'&appId='+selectId);	
// 	}
// }
</script>
</head>
<body>
               <section class="panel">
                  <div class="panel-body">
                     <div class="adv-table editable-table">
                        <div class="clearfix">
                        	<div class="btn-group nopaddingleft">
                              <button id="suggest" class="btn btn-info" onClick='showAll()'>
                            	 信息核准 <i class="fa fa-comment"></i>
                              </button>
                           </div>
                        	<div class="btn-group  ">
                             <button onclick="downZip()" class="btn btn-info" data-toggle='modal' href='' id="dialog">
                            	 资料下载 <i class="fa fa-eye"></i>
                              </button>
                           </div>
                           
  <!--                         <div class="btn-group  nopaddingleft">
                             <button onclick="view()" class="btn btn-info" data-toggle='modal' href='' id="dialog">
                            	  查看 <i class="fa fa-eye"></i>
                              </button>
                           </div>
                           <div class="btn-group ">
                              <button id="suggest" class="btn btn-info" onClick='getComment()' data-toggle='modal' href=''>
                            	  查看意见 <i class="fa fa-comment"></i>
                              </button>
                           </div>
                           <div class="btn-group ">
                             <button onclick="show()" class="btn btn-info" data-toggle='modal' href='' id="dialog1">
                            	 快递 <i class="fa fa-envelope"></i>
                              </button>
                           </div>
                           <div class="btn-group ">
                              <button id="psb" class="btn btn-info">
                            	  审核 <i class="fa fa-laptop"></i>
                              </button>
                              <a id="toShow" data-toggle="modal" href="#Modal_show" style="visibility:hidden"></a>
                           </div>
                           <div class="btn-group ">
                           <a id = "viewR" onclick="viewReview()" class="btn btn-info"  target="_blank">
                            	评审表 <i class="fa fa-book"></i>
                           </a>
                           </div>
                           <div class="btn-group ">
                              <button onclick="showSample()" class="btn btn-info" >
                            	 样品 <i class="fa  fa-puzzle-piece"></i>
                              </button>
                           </div>
                          <div class="btn-group pull-right"> 
                              <button class="btn btn-primary dropdown-toggle" data-toggle="dropdown">下载<i class="fa fa-angle-down"></i>
                              </button>
                              <ul class="dropdown-menu pull-right">
                              <li>
                              <a onclick="savePdf()">
                            	 申请表下载</i>
                              </a>
                              </li>
                              <li>
							  <a onclick="saveReview()" id = "pdf">
                            	 评审表下载</i>
                              </a>
                              </li>
                              <li>
							  <a onclick="saveSQ()">
                            	 申请表下载(盖章)</i>
                              </a>
                              </li>
                              <li>
							  <a onclick="savePS()">
                            	 评审表下载(盖章)</i>
                              </a>
                              </li>
                                 <li><a href="">导出到Excel</a></li>
                              </ul>
                           </div> -->
                        </div>
                        <div class="space15"></div>
                        <table class="table table-bordered" style="width:460%" id="editable-sample">
                           <thead>
                              <tr>
                                 <th class="" style="display:none;">任务编号</th>
                                 <th class="" style="display:none;">申请编号</th>
                                 <th class="" style="display:none;">企业编号</th>
                                 <th class="">编号</th>
                                 <th class="">流水号</th>
                                 <th class="">分支机构</th>
                                 <th class="">企业名称</th>
                                 <th class="">企业性质</th>
                                 <th class="">详细地址</th>
                                 <th class="">邮政编码</th>
                                 <th class="">测评结果</th>
                                 <th class="">评审结果</th>
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
                <div id="Modal_show1" class="modal fade">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                  &times;
                </button>
                <h4 class="modal-title">分支机构审核个人任务</h4>
              </div>
              <div class="modal-body">
                <table border="0" cellspacing="100px" align="center" style="height: 770px;width:100%">
					<tr id="trs"><td style="height: 100%"><iframe id="person" src="" frameborder="0" width="100%" scrolling="Yes" height="100%" leftmargin="0" topmargin="0"></iframe></td></tr>
				</table>
	         </div>
	         <div class="modal-footer" style="margin-top:0px;">
                <button data-dismiss="modal" class="btn btn-success" type="button">
                 	确定
                </button>
              </div>
              </div>
            </div>
          </div>
	    <!-- END SECTION -->
		<div id="Modal_show" class="modal fade">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                  &times;
                </button>
                <h4 class="modal-title">审核任务</h4>
              </div>
              <div class="modal-body">
              	<p><center>编码中心核对材料意见</center></p>
                <textarea id="comment" autocomplete="off" class="form-control placeholder-no-fix" placeholder="无"></textarea>
	         </div>
	         <div class="modal-footer">
	         	<button data-dismiss="modal" class="btn btn-default" type="button" onclick="processTask(false)">
                  	退回
                </button>
                <button data-dismiss="modal" class="btn btn-success" type="button" onclick="processTask(true)">
                 	通过
                </button>
              </div>
              </div>
            </div>
          </div>
          <div id="Modal_show2" class="modal fade">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                  &times;
                </button>
                <h4 class="modal-title">快递信息</h4>
              </div>
              <div class="modal-body">
                <table class="table table-bordered" style="width:100%">
					<tr><td>快递名</td><td>单号</td><td>联系人</td><td>联系方式</td></tr>
					<tr><td id="name"></td><td id="num"></td><td id="concat"></td><td id="tel"></td></tr>
				</table>
	         </div>
	         <div class="modal-footer" style="margin-top:0px;">
                <button data-dismiss="modal" class="btn btn-success" type="button">
                 	确定
                </button>
              </div>
              </div>
            </div>
          </div>
          <div id="Modal_show3" class="modal fade">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                  &times;
                </button>
                <h4 class="modal-title">审核意见</h4>
              </div>
              <div class="modal-body" style="overflow-y: scroll;max-height: 300px;">
                <table id="comment1" class="table table-bordered" style="width:100%">
                	
                </table>
	         </div>
	         <div class="modal-footer">
                <button data-dismiss="modal" class="btn btn-success" type="button">确定</button>
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
	<script src="${ctx}/js/editable-table.js" ></script><!--  EDITABLE TABLE JS  -->
     <!-- EDITABLE TABLE FUNCTION  -->
     <script>
        jQuery(document).ready(function() {
            EditableTable.init();
        });
        
        function processTask(result){//初审任务的处理
        	var taskId=$("#result").children("tr").eq(nRow).children("td").eq(0).text();
            var comment=$("#comment").val();
            var status=0;
            var role="中心审核人员";
            var candidate="bmzx";
            if(result){
            	status=7;
            }else{
            	status=8;
            	role="分中心审核人员";
            	candidate="fzjg";
            	$.post(//编码中心审核不通过的话删除寄送上传的信息
           			"${ctx}/application/expressMessage/delExpress",
           			"companyId="+selectId,
           			function(data){}
               	);
            }
            $.post(
            	"${ctx}/application/climeAndcompleteTask.action",
            	"taskId="+taskId+"&comment="+comment+"&role="+role+"&candidate="+candidate
            	+"&result="+result+"&status="+status,
            	function(data){
            		window.location.reload();
            	}
            );
        }
     </script>
  <!-- END JS --> 
    <!-- END JS -->
</body>
</html>