<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<%@ taglib prefix="myshiro" uri="/customize-tags" %>
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>发证</title>
	<link href="${ctx}/assets/morris.js-0.4.3/morris.css" rel="stylesheet"><!-- MORRIS CHART CSS -->
	<link href="${ctx}/css/clndr.css" rel="stylesheet"><!-- CALENDER CSS -->
	<link href="${ctx}/css/style_self.css" rel="stylesheet">
	<!-- 查询页面 -->
    <link href="${ctx}/css/bootstrap.min.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/css/bootstrap-reset.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="${ctx}/assets/font-awesome/css/font-awesome.css" rel="stylesheet"><!-- FONT AWESOME ICON STYLESHEET -->
    <link href="${ctx}/assets/data-tables/DT_bootstrap.css" rel="stylesheet" ><!-- DATATABLE CSS -->
    <link href="${ctx}/css/style.css" rel="stylesheet"><!-- THEME BASIC CSS -->
    <link href="${ctx}/assets/fuelux/css/tree-style.css" rel="stylesheet">
    <link href="${ctx}/css/style-responsive.css" rel="stylesheet"><!-- THEME BASIC RESPONSIVE  CSS -->
	<link href="${ctx}/css/fileinput.min.css" media="all" rel="stylesheet" type="text/css" />
	<script src="${ctx}/js/jquery-1.8.3.min.js" ></script><!-- BASIC JS LIABRARY 1.8.3 -->
	<script src="${ctx}/js/style_self.js"></script>
	<script src="${ctx}/js/fileinput.min.js" type="text/javascript"></script>
	<script src="${ctx}/js/locales/zh.js" type="text/javascript"></script>
	<link rel="stylesheet" href="${ctx}/css/validationEngine.jquery.css">
	<script src="${ctx}/js/jquery.validationEngine-zh_CN.js"></script>
	<script src="${ctx}/js/jquery.validationEngine.js"></script>
	<script src="${ctx}/assets/bootstrap-datepicker/js/bootstrap-datepicker.js"></script><!-- 日期显示 -->
<style type="text/css">
	.label{
		line-height: unset;
	}
	a{
		cursor: pointer;
	}
	.tds{
		text-align: left;
		font-size: large;
	}
	.dropdown-menu{z-index: 10000}
</style>
<script type="text/javascript">
var temptr = $();
var nRow = null;
var taskId=null;
var titletNo=null;
var ywCode=null;
var comName=null;
var stat=null;
$(function(){
	$('#certTime').datepicker({
		format:'yyyy-mm-dd',
	});
	$('#editable-sample tbody').on('click', 'tr', function () {//选中的行
		//获取选中行的第一列的值
		//alert($(this).children("td").eq(0).text());
		nRow = $(this).parent().find("tr").index($(this)[0]);
		taskId=$(this).children("td").eq(0).text();
		titleNo=$(this).children("td").eq(1).text();
		ywCode=$(this).children("td").eq(2).text();
		comName=$(this).children("td").eq(6).text();
		stat=$(this).children("td").eq(8).text();
		temptr.css("background-color","");
        temptr = $(this);
        temptr.css("background-color","#99ffff");
	});
	$.ajaxSetup({async : false});
	$.post(
		"${ctx}/recognition/getAllTask.action",
		"pointId=fz",
		function(data){
			$.each(data,function(i,item){
				$("#result").append("<tr'>"
						+"<td class='' style='display:none;'>"+item.taskId+"</td>"
						+"<td class='' style='display:none;'>"+item.titleno+"</td>"
						+"<td class='' style='display:none;'>"+item.id+"</td>"
						/*+ "<td class=''><input type='checkbox' name='message' value='"+item.taskId+"' id='"+item.branchId+"'/></td>" */
						+"<td class=''>"+(i+1)+"</td>"
						+"<td class=''>"+item.branchName+"</td>"
						+"<td class=''>"+exchangeNull(item.titleno)+"</td>"
		             	+"<td class=''>"+item.enterprisename+"</td>"
// 		             	+"<td class=''><a id='"+item.titleno+"' onClick='savePdf(this)'><span class='btn btn-success green'>申请表下载</span></a></td>"
// 		             	+"<td class=''><a id='"+item.titleno+"' onClick='saveReview(this)'><span class='btn btn-success green'>评审表下载</span></a></td>"
		             	+"<td>"+item.enterprisekind+"</td>"
		             	+"<td class=''>"+(item.certNo==0?"<p name='fz'>未发证</p>":"<p name='fz'>已发证</p>")+"</td></tr>");
			});
		}
	);
	 $("#editable-sample tbody tr").dblclick(function(e){
		location.href='${ctx}/recognition/check/allInfoView.action?titleNo='+titleNo+'&appId='+ywCode+'&taskId='+taskId+'&comName='+comName+"&backPage=发证";
	}); 
});//初始化

window.onload=function(){//给表添加滚动条
	$("#editable-sample").wrap('<div style="overflow-x:auto;width:100%"></div>');
	//SetTableStyle();
	$("body").delegate("#editable-sample tbody tr","click",function(){
		//SetTableStyle();
	});
};
function savePdf(){
	if(nRow==null){
		wzj.alert('温馨提示','请选择一个任务！');
	}else if(titleNo==""){
		wzj.alert('温馨提示','数据为空！');
		return false;
	}else{
		window.open('${ctx}/common/downPdf.action?type=1&titleNo='+titleNo);	
	}
}
function saveReview(){
	if(nRow==null){
		wzj.alert('温馨提示','请选择一个任务！');
	}else if(titleNo==""){
		wzj.alert('温馨提示','数据为空！');
		return false;
	}else{
		window.open('${ctx}/common/downPdf.action?type=4&titleNo='+titleNo);	
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

function getComment(element){
	var taskId=element.id;
	var companyName=element.name;
	$("#commentBody").empty();//清空
	$.post(
		"${ctx}/application/getComment",
		"taskId="+taskId+"&companyName="+companyName,
		function(data){
			$.each(data,function(i,item){
				var str=DateHandle(item.time);
				$("#commentBody").append(
					"<tr><td>"+str+"</td><td>"
					+item.userId+"</td><td>"
					+item.fullMessage+"</td></tr>");
			});
			$('#comment').dataTable(oTable)
			$('#comment').parent().find('.row:first').hide()
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
function issue(){
	/* 原发证代码（多选）
	var str=document.getElementsByName("taskId");
	var objarray=str.length;
	var chestr="";
	for (i=0;i<objarray;i++){
		if(str[i].checked == true) {  
			chestr+=str[i].value+",";
		}
	}
	if(chestr == ""){
		wzj.alert('温馨提示', '请选择数据！')
	}else{
		$("#send").attr("href","#Modal_show2");
	} */
	if(nRow==null){
		wzj.alert('温馨提示','请选择一个任务！');
	}else if(titleNo==""){
		wzj.alert('温馨提示','数据为空！');
		return false;
	}else if(stat=='已发证'){
		$.post(
				"${ctx}/application/check/getCertInfo.action",
				"appId="+ywCode,
				function(data){
	 				$("#certNo").html(PrefixInteger(data.serial,6));
	 				$("#certTime").val(DateHandle(data.date));
	 				$("#Modal_show1").show();
	 				$("#shows").click();
				}
			);	
	}else{
		$.post(
			"${ctx}/recognition/check/issuing.action",
			"taskId="+taskId+"&companyName="+comName,
			function(data){
				$("#result tr:eq("+nRow+") td:eq(8)").html("<p name='fz'>已发证</p>");
				$("#certNo").text(PrefixInteger(data.serial,6));
				$("#certTime").val(DateHandle(data.date));
				$("#shows").click();
				stat='已发证'
			}
		);  
	}
}
function PrefixInteger(num, n) {
    return (Array(n).join(0) + num).slice(-n);
}
function upCheckResult(){//审核结果上传
	if(nRow==null){
		wzj.alert('温馨提示','请选择一个任务！');
	}else if(titleNo==""){
		wzj.alert('温馨提示','数据为空！');
		return false;
	}else{
	$("#upCheckResult").attr("href","#Modal_show3");
	$("#mulfile").fileinput({
		uploadUrl:"${ctx}/commons/PLuploadSampleFile?titleNo="+titleNo+"&describe=中心审核结果&sampleId=12",
	    fileType: "any",
		language : 'zh',
		enctype : 'multipart/form-data',
	});
	}
}
var jsxx=true;
function sendMsg(){//寄送信息
	$("#sends").attr("href","");
	$("#td1").val("");
	$("#td2").val("");
	$("#td5").val("");
	$("#td6").val("");
	$("#td7").val("");
	$("#td8").val("");
	var str=document.getElementsByName("message");
	var objarray1=str.length;
	var chestr1="";
	var cids1=[];
	var cids2=[];
	for (var i=0;i<objarray1;i++){
		if(str[i].checked == true) { 
			chestr1+=str[i].value+",";
			cids1.push(str[i].id);
			}
	}
	for(j in cids1){
		if(cids1[0]==cids1[j]){
			cids2.push(cids1[j])
		}
	}
	var fz=document.getElementsByName("fz");
	var objarray=str.length;
	var chestr="";
	for (i=0;i<objarray;i++){
		if(str[i].checked == true) {  
			chestr+=str[i].value+",";
			if(name=="未发证")
				jsxx=false;
		}
	}
	if(chestr == ""){
		wzj.alert('温馨提示', '请勾选任务！')
	}else{
		if(jsxx){
// 			$("#sends").attr("href","#Modal_show2");
			if(cids1.length!=0){
				if(cids2.length==cids1.length){
					$("#sends").attr("href","#Modal_show2");
				}  else{
					 wzj.alert('温馨提示','请选择相同分中心！');
						return false;
				} 
			}
			$.post(
					"${ctx}/sysUser/getSendUser.action",
					"branchId="+cids1[0],
					function(data){
						$("#td6").val(data.divisionlinkman);
						$("#td7").val(data.divisionphone1);
						$("#td8").val(data.divisionaddress);
						$("#td10").val(data.divisionname);
					}
				);
			if(chestr == ""){
				wzj.alert('温馨提示', '请勾选数据！')
			}else{
				$("#taskId").val(chestr);
				$.post(
						"${ctx}/sysUser/getSendUser.action",
						"branchId=0000",
						function(data){
							$("#td3").val(data.divisionlinkman);
							$("#td4").val(data.divisionphone1);
							$("#td5").val(data.divisionaddress);
							$("#td9").val(data.divisionname);
						}
					);
				document.getElementById("kd").options[0].selected = true; 
			}
			
		}else{
			wzj.alert('温馨提示', '含有未发证！')
		}
		
	} 
}
//寄送信息上传+发证
// function msgAdd(){
// 	var str=document.getElementsByName("taskId");
// 	var objarray=str.length;
// 	var chestr="";
// 	for (i=0;i<objarray;i++){
// 		if(str[i].checked == true) {  
// 			chestr+=str[i].value+",";
// 			}
// 	}
// 	$("#msg").attr("action","${ctx}/application/check/issuing.action?taskId="+chestr);
// 	$("#msg").submit();
// 	$('#yesbtn').attr("data-dismiss","modal")
// 	 $.post(
// 		"${ctx}/application/check/issuing.action",
// 		"taskId="+chestr,
// 		function(data){
// 			if(data!=null){
// 	 			$("#certNo").text(data.serial);
// 	 			$("#certTime").text(DateHandle(data.date));
// 	 			$("#shows").click();}else{
// 				window.location.reload();
// 			}
// 		}
// 	); 
// }
function msgAdd2(){
	if($('#msg').validationEngine('validate')){
		if(stat=='未发证'){
			wzj.alert('温馨提示','未发证，不可寄送信息！');	
		}else{
			var str=document.getElementsByName("taskId");
			var objarray=str.length;
			var chestr="";
			for (i=0;i<objarray;i++){
				if(str[i].checked == true) {  
					chestr+=str[i].value+",";
				}
			}
			$("#msg").attr("action","${ctx}/recognition/check/msgAdd.action?taskId="+chestr);
			$("#msg").submit();
			$('#yesbtn').attr("data-dismiss","modal");
		}
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
function showSample(){
	if(nRow==null){
		wzj.alert('温馨提示','请选择一个任务！');
	}else if(ywCode==""){
		wzj.alert('温馨提示','数据为空！');
		return false;
	}else{
		location.href ='${ctx}/application/report/toShowSample.action?comName='+comName;
	}
}
function viewReview(){
	if(nRow==null){
		wzj.alert('温馨提示','请选择一个任务！');
	}else if(ywCode==""){
		wzj.alert('温馨提示','数据为空！');
		return false;
	}else{
		$('#viewR').prop('href','../../viewPDF.jsp?code=xb_ps_'+ywCode);
	}
}
//盖章申请表下载
function saveSQ(){
	if(nRow==null){
		wzj.alert('温馨提示','请选择一个任务！');
	}else if(ywCode==""){
		wzj.alert('温馨提示','数据为空！');
		return false;
	}else{
		window.open('${ctx}/common/downSeal.action?type=1&titleNo='+titleNo+'&appId='+ywCode);	
	}
}
//盖章评审表下载
function savePS(){
	if(nRow==null){
		wzj.alert('温馨提示','请选择一个任务！');
	}else if(ywCode==""){
		wzj.alert('温馨提示','数据为空！');
		return false;
	}else{
		window.open('${ctx}/common/downSeal.action?type=2&titleNo='+titleNo+'&appId='+ywCode);	
	}
}
function downZip(){
	if(nRow==null){
		wzj.alert('温馨提示', '请选择一个任务！')
		return false;
	}else if(ywCode==""){
		wzj.alert('温馨提示','数据为空！');
		return false;
	}else{
		window.open("${ctx}/commons/download.action?titleNo="+titleNo);
	}
}
function changeSend(){
	var sendType=$("#kd option:selected").val();
	$("#td1").val(sendType);
}
//寄送信息
function msgAdd(){
	var str=document.getElementsByName("message");
	var objarray=str.length;
	var chestr="";
	var cids1=[];
	for (var i=0;i<objarray;i++){
		if(str[i].checked == true) { 
			cids1.push(str[i].value);
			}
	}
	$.post(
			"${ctx}/application/check/checkIssue.action",
			"ids="+cids1,
			function(data){
				if(data){
					if($('#msg').validationEngine('validate')){
// 				 		if(stat=='未发证'){
// 				 			wzj.alert('温馨提示','未发证，不可寄送信息！');	
// 				 		}else{
				         	var taskId = $("#taskId").val();
				         	$("#msg").attr("action","${ctx}/recognition/check/msgAdd.action?taskId="+taskId);
				         	$("#msg").submit();
				         	$('#yesbtn').attr("data-dismiss","modal")
// 				 		}
				     }
				}else{
					wzj.alert('温馨提示', '未发证项请先发证！')
				}
			}
		);
}
//打印
function print(){
	var str=document.getElementsByName("message");
	var objarray=str.length;
	var chestr="";
	var cids1=[];
	for (var i=0;i<objarray;i++){
		if(str[i].checked == true) { 
			cids1.push(str[i].value);
			}
	}
	$.post(
			"${ctx}/application/check/checkIssue.action",
			"ids="+cids1,
			function(data){
				if(data){
					if($('#msg').validationEngine('validate')){
						$("#msg").attr("action","${ctx}/application/printSendMsg.action?companyName="+comName);
						$("#msg").attr("target","nm_iframe");
						$("#msg").submit();
						$("#Modal_show2").show();
					}
				}else{
					wzj.alert('温馨提示', '未发证项请先发证！')
				}
			}
		);
	
}
</script>
</head>
<body>
               <section class="panel">
                  <div class="panel-body">
                     <div class="adv-table editable-table">
                        <div class="clearfix">
                        <div class="btn-group nopaddingleft">
                           <button onclick="issue()" class="btn btn-info" id="send" href="" data-toggle='modal'>
                            	 发证 <i class="fa fa-bars"></i>
                              </button>
                           </div>
                           <!-- <div class="btn-group ">
                           <a onclick="sendMsg()" class="btn btn-info" id="sends" href="" data-toggle='modal'>
                            	 寄送信息 <i class="fa fa-envelope"></i>
                           </a>
                           </div> -->
                           <div class="btn-group ">
                           <a onclick="upCheckResult()" class="btn btn-info" id="upCheckResult" href="" data-toggle='modal'>
                            	 审核结果上传<i class="fa fa-upload"></i>
                           </a>
                           </div>
                           <div class="btn-group">
                             <button onclick="downZip()" class="btn btn-info" data-toggle='modal' href='' id="dialog">
                            	 资料下载 <i class="fa fa-download"></i>
                              </button>
                           </div>
                        </div>
                        <div class="space15"><a id="shows" href="#Modal_show1" data-toggle='modal' style="display:none;"></a></div>
                        <table class="table table-bordered" style="width:100%" id="editable-sample">
                           <thead>
                              <tr>
                                 <th class="" style="display:none">任务编号</th>
                                 <th class="" style="display:none">状态编号</th>
                                 <th class="" style="display:none">业务编号</th>
                                 <!-- <th class="">发证</th> -->
                                 <th class="">编号</th>
                                 <th class="">分支机构</th>
                                 <th class="">流水号</th>
                                 <th class="">企业名称</th>
                                 <th class="">企业性质</th>
                                 <th class="">状态</th>
                              </tr>
                           </thead>
                           <tbody id="result">
                              <!-- 循环显示查询出来的结果 -->
                             
                           </tbody>
                        </table>
                        </div>
                     </div>
               </section>
            </section>
            <div id="Modal_show" class="modal fade">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                  &times;
                </button>
                <h4 class="modal-title">评审意见</h4>
              </div>
              <div class="modal-body" style="overflow-y: hidden;max-height: 320px;">
                <table id="comment" class="table table-bordered" style="width:100%">
                	<thead><tr>
                  	<td>时间</td>
                  	<td>意见人</td>
                  	<td>意见</td>
                  </tr></thead>
                  <tbody id="commentBody">
                  </tbody>
                </table>
	         </div>
	         <div class="modal-footer">
                <button data-dismiss="modal" class="btn btn-success" type="button">确定</button>
              </div>
              </div>
            </div>
          </div>
          <div id="Modal_show1" class="modal fade">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                  &times;
                </button>
                <h4 class="modal-title">证书信息</h4>
              </div>
              <div class="modal-body" style="max-height: 300px;">
                <table style="margin: 0 auto">
                	<tr><td class="tds">证书编号：</td><td id="certNo" class="tds"></td></tr>
                	<tr><td class="tds">发证日期：</td><td><input id="certTime" class="tds form-control validate[custom[date]]"></td></tr>
                </table>
	         </div>
	         <div class="modal-footer">
                <button data-dismiss="modal" id="closed" class="btn btn-success" onclick="refre()" type="button">确定</button>
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
                <h4 class="modal-title">材料寄送信息</h4>
              </div>
              <div class="modal-body">
              <iframe id="id_iframe" name="nm_iframe" style="display:none;">
				</iframe>
              <form id="msg" method="post">
              <div class="row" style="width: 100%; margin: 5px 0px;">
				<label class="col-sm-2 labels nopaddingright" style="text-align: right;">快递名称<span style="color: red">*</span></label>
            		<div class="col-sm-4">
                		<select id="kd" onchange="changeSend()" class="col-sm-12 validate[required]">
		                	<option value="EMS" selected="selected">EMS</option>
		                	<option value="中通">中通</option>
		                	<option value="申通">申通</option>
		                	<option value="圆通">圆通</option>
		                	<option value="顺丰">顺丰</option>
		                	<option value="韵达">韵达</option>
	                	</select>
                	</div>
                	<input id="taskId" value="" type="hidden"/>
                	<input class="form-control" id="td1" value="" name="name" style="width: 100%" type="hidden"/>
              	<label class="col-sm-2 labels nopaddingright" style="text-align: right;">订单号<b class="red">*</b></label>
                	<div class="col-sm-4">
                  		<input class="form-control validate[required]" id="td2" value="" name="number" style="width: 100%"/>
                	</div>
				</div>
              <div class="row" style="width: 100%; margin: 5px 0px;">
				<label class="col-sm-2 labels nopaddingright" style="text-align: right;">联系人</label>
            		<div class="col-sm-4">
            			<input class="form-control" id="td3" value="" name="contact" style="width: 100%"/>
            		</div>
              	<label class="col-sm-2 labels nopaddingright" style="text-align: right;">联系人电话</label>
                	<div class="col-sm-4">
                  		<input class="form-control validate[custom[telephone]]" id="td4" value="" name="phoneNumber" style="width: 100%"/>
                	</div>
				</div>
              <div class="row" style="width: 100%; margin: 5px 0px;">
				<label class="col-sm-2 labels nopaddingright" style="text-align: right;">联系人地址</label>
            		<div class="col-sm-10">
            			<input class="form-control" id="td5" value="" name="conAddress" style="width: 100%"/>
            		</div>
				</div>
<!-- 				<div class="row" style="width: 100%; margin: 5px 0px;"> -->
<!-- 				<label class="col-sm-2 labels nopaddingright" style="text-align: right;">收件人</label> -->
<!--             		<div class="col-sm-4"> -->
<!--             			<select id="recivePerson" onchange="changeUser()" style="margin: 5px 0" class="col-sm-12"></select> -->
<!--             		</div> -->
<!-- 				</div> -->
				<div class="row" style="width: 100%; margin: 5px 0px;">
				<label class="col-sm-2 labels nopaddingright" style="text-align: right;">收件人电话<b class="red">*</b></label>
            		<div class="col-sm-4">
            			<input class="form-control validate[required,custom[telephone]]" id="td7" value="" name="recivePhoneNum" style="width: 100%"/>
            		</div>
              	<label class="col-sm-2 labels nopaddingright" style="text-align: right;">收件人姓名<b class="red">*</b></label>
                	<div class="col-sm-4">
                  		<input class="form-control validate[required]" id="td6" value="" name="reciveName" style="width: 100%"/>
                	</div>
				</div>
				<div class="row" style="width: 100%; margin: 5px 0px;">
				<label class="col-sm-2 labels nopaddingright" style="text-align: right;">收件人地址<b class="red">*</b></label>
                	<div class="col-sm-10">
                  		<input class="form-control validate[required]" id="td8" value="" name="reciveAddress" style="width: 100%"/>
                		<input type="hidden" class="form-control" id="td9" value="" name="sendUnit" style="width: 100%"/>
                  		<input type="hidden" class="form-control" id="td10" value="" name="reciveUnit" style="width: 100%"/>
                	</div>
				</div>
				</form>
	         </div>
	         <div class="modal-footer" style="margin-top:0px;">
	         	<button class="btn btn-success" type="button" onclick="print()">
                 	打印
                </button>
                <button class="btn btn-success" type="button" id="yesbtn" onclick="msgAdd()">
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
	                <h4 class="modal-title">附件</h4>
	              </div>
	              <form action="" method="post" enctype="multipart/form-data" id="uploadForm">
                  <div class="form-group" id="file-content" style="padding: 15px">
                    <input id="mulfile" name="mfile" type="file" class="file-loading" multiple="multiple">
                  </div>	
               	  </form>
		         <div class="modal-footer" style="margin-top:0px;">
	                <button data-dismiss="modal" class="btn btn-success text-right" onclick="clearFile()">
	                	确定
	                </button>
	              </div>
	              </div>
	            </div>
	          </div> 
			<!-- END WRAPPER  -->
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
	<script src="${ctx}/js/editable-paging.js" ></script>
	<script src="${ctx}/js/editable-table.js" ></script><!--  EDITABLE TABLE JS  -->
    <script src="${ctx}/assets/fuelux/js/tree.min.js"></script><!-- TREE JS  -->
    <script src="${ctx}/js/editable-table.js"></script>
		<script src="${ctx}/js/commentTable.js" ></script>
     <!-- EDITABLE TABLE FUNCTION  -->
     <script>
        jQuery(document).ready(function() {
            EditableTable.init();
            $("#closed").click(
            	function(){
            		window.location.reload();
            	}		
            ); 
    	    $("#msg").validationEngine({
    	        scrollOffset: 98,//必须设置，因为Toolbar position为Fixed
    	        promptPosition: 'topLeft',
    	        autoHidePrompt: true,
    	    });
        });
        function refre(){
        	var time=$("#certTime").val().replace(/-/g, '/');
        	$.ajaxSetup({async:false});
        	$.post(
        		"${ctx}/application/check/updateDate.action",
        		"appId="+ywCode+"&time="+time,
        		function(data){//完成任务
        			$.post(
        				"${ctx}/recognition/check/completeTask.action",
        	        	"taskId="+taskId,
        	        	function(data){//完成任务	
        	       			window.location.reload();
        				}
           			);
        		}
        	);
        }
        $.validationEngineLanguage.allRules.telephone = {  
	    	      "regex": /^1[3|4|5|7|8][0-9]{9}$|^(0[0-9]{2})-\d{7,8}$|^(0[0-9]{3}-(\d{7,8}))$|^\d{8}$|^\d{7}$/,  
	    	      "alertText": "* 请输入正确的电话号码"  
	    	  }; 
        $.validationEngineLanguage.allRules.date = {  
	    	      "regex": /^(19[0-9]{2}|20[0-9]{2})-(0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-9]|3[0-1])$/,
	    	      "alertText": "* 请输入正确的日期格式"  
	   	}; 
     </script>
  <!-- END JS --> 
    <!-- END JS -->
</body>
</html>