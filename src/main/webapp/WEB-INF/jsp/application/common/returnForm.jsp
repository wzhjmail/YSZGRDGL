<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<%@ taglib prefix="myshiro" uri="/customize-tags" %>
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
	<link href="${ctx}/assets/morris.js-0.4.3/morris.css" rel="stylesheet"><!-- MORRIS CHART CSS -->
	<link href="${ctx}/css/clndr.css" rel="stylesheet"><!-- CALENDER CSS -->
	<!-- 查询页面 -->
    <link href="${ctx}/css/bootstrap.min.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="${ctx}/css/bootstrap-reset.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="${ctx}/assets/font-awesome/css/font-awesome.css" rel="stylesheet"><!-- FONT AWESOME ICON STYLESHEET -->
    <link href="${ctx}/assets/data-tables/DT_bootstrap.css" rel="stylesheet" ><!-- DATATABLE CSS -->
    <link href="${ctx}/css/style.css" rel="stylesheet"><!-- THEME BASIC CSS -->
    <link href="${ctx}/css/style-responsive.css" rel="stylesheet"><!-- THEME BASIC RESPONSIVE  CSS -->
	<link href="${ctx}/css/style_self.css" rel="stylesheet"><!-- 右键特效的css -->
	<link rel="stylesheet" href="${ctx}/css/validationEngine.jquery.css">
	<script src="${ctx}/js/jquery-1.8.3.min.js"></script><!-- BASIC JS LIABRARY 1.8.3 -->
	<script src="${ctx}/js/jquery.form.js"></script>
	<script src="${ctx}/js/style_self.js"></script><!-- 右键特效的js -->
	<script src="${ctx}/js/jquery.validationEngine-zh_CN.js"></script>
	<script src="${ctx}/js/jquery.validationEngine.js"></script>
	<script src="${ctx}/js/jquery.mloading.js"></script>
	<link href="${ctx}/css/jquery.mloading.css" rel="stylesheet">
	<script src="${ctx}/assets/bootstrap-datepicker/js/bootstrap-datepicker.js"></script><!-- 日期显示 -->
	<link rel="stylesheet" type="text/css" href="${ctx}/assets/bootstrap-datepicker/css/datepicker.css">
	<style type="text/css">
		.change{
		    position: absolute;
		    overflow: hidden;
		    right: 0;
		    top: 0;
		    width: 0px;
		    opacity: 0;
		}
		#title{
			position: fixed;
			top: 0;
			left: 1%;
			z-index: 99999;
			width: 101%;
			background: white;
			border: 1px solid #e5e6e6;	
		}
	</style>
<script type="text/javascript">
$(function(){
	$('#ttime').datepicker({
		format:'yyyy-mm-dd',
	});
	$('#ttime1').datepicker({
		format:'yyyy-mm-dd',
	});
	$('.labels').css('text-align','right');
	getTask();
});

function getPrintEquipment(companyName) {//查看主要印刷设备
    $.post(
        "${ctx}/application/printEquipment/getByCompanyName.action",
        "companyName="+companyName,
        function (data) {
            if(data != null) {
                $("#printing thead").show();
                $.each(data, function(i,item) {
                    $("#printing").append("<tr><td style='display:none'>"+item.id+"</td><td>"+ item.printName+"</td>"+
                        "<td>"+ item.printModel+"</td><td>"+item.printPlace+"</td><td>"+item.printNumber+"</td><td>"+item.printNotes+
                        "</td><td><button onclick='toUpdatePrinting(this)' class='btn btn-info'>修改</button></td>"+
                        "<td><button onclick='deletePrinting(this)' class='btn btn-danger'>删除</button></td></tr>");
                });
            }
        }
    );
}
function toUpdatePrinting(that){
    $("#updateTestingId").val(that);
    var printingId=$(that).parents("tr").children("td").eq(0).text();
    $("#showUpdatePrinting").attr('href',"#Modal_show2");
    $("#showUpdatePrinting").click();
    $.post(
        "${ctx}/application/printEquipment/getById",
        "id="+printingId,
        function(data){
            $("#pId").val(printingId);
            $("#uPrintName").val(data.printName);
            $("#uPrintModel").val(data.printModel);
            $("#uPrintPlace").val(data.printPlace);
            $("#uPrintNumber").val(data.printNumber);
            $("#uPrintNotes").val(data.printNotes);
        }
    );
}
function updatePrinting(){
	$('#updatebtn').removeAttr('data-dismiss',"modal")
	if($('#updatePrint').validationEngine('validate')){
		$('#updatebtn').removeAttr('data-dismiss',"modal")
	    var pId=$("#pId").val();
	    var uPrintName=$("#uPrintName").val();
	    var uPrintModel=$("#uPrintModel").val();
	    var uPrintPlace=$("#uPrintPlace").val();
	    var uPrintNumber=$("#uPrintNumber").val();
	    var	uPrintNotes=$("#uPrintNotes").val();
	    $.post(
	        "${ctx}/application/printEquipment/updateById.action",
	        "id="+pId+"&printName="+uPrintName+"&printModel="+uPrintModel+"&printPlace="+uPrintPlace+"&printNumber="+uPrintNumber+"&printNotes="+uPrintNotes,
	        function(data){
	            $($('#printing tbody tr td:hidden')).each(function(){
	                if($(this).html()==pId){
	                    $(this).parent().children("td").eq(1).text(uPrintName);
	                    $(this).parent().children("td").eq(2).text(uPrintModel);
	                    $(this).parent().children("td").eq(3).text(uPrintPlace);
	                    $(this).parent().children("td").eq(4).text(uPrintNumber);
	                    $(this).parent().children("td").eq(5).text(uPrintNotes);
	                    $("#Modal_show2 .close").click();
	                }
	            });
	        }
	    );
	}
}
function deletePrinting(that) {
    var printId = $(that).parents("tr").children("td").eq(0).text();
    console.log($(that))
    $.post(
        "${ctx}/application/printEquipment/deleteById.action",
        "id="+printId,
        function (data) {
            if(data > 0) {
                $(that).parent().parent().remove();
            }
        }
    );
}

function getTestingEquip(companyName){
	$.post(
		"${ctx}/application/testingEquip/getByCompanyName.action",
		"companyName="+companyName,
		function(data){
			if(data!=null){
				$("#testing thead").show()
				$.each(data,function(i,item){
					$("#testing").append("<tr><td style='display:none'>"+item.id+"</td><td>"+
							item.name+"</td><td>"+item.testingModel+"</td><td>"+item.count+"</td><td>"+(new Date(parseFloat(item.time))).format("yyyy-MM-dd")+"</td><td>"+item.remark+
							"</td><td><button onclick='toUpdateTesting(this)' class='btn btn-info'>修改</button></td>"+
							"<td><button onclick='deleteTesting(this)' class='btn btn-danger'>删除</button></td></tr>");
				});
			}
		}
	);
}
function toUpdateTesting(that){
	$("#updateTestingId").val(that);
	var testingId=$(that).parents("tr").children("td").eq(0).text();
	$("#showUpdateTesting").attr('href',"#Modal_show7");
	$("#showUpdateTesting").click();
	$.post(
		"${ctx}/application/testingEquip/getById",
		"id="+testingId,
		function(data){
			$("#tid1").val(testingId);
			$("#tname1").val(data.name);
			$("#tmodel1").val(data.testingModel);
			$("#tcount1").val(data.count);
			$("#ttime1").val((new Date(parseFloat(data.time))).format("yyyy-MM-dd"));
			$("#tremark1").val(data.remark);
		}
	);
}
function updateTesting(){
	$('#updatetest').removeAttr('data-dismiss',"modal")
	if($('#updateTesting').validationEngine('validate')){
		$('#updatetest').attr('data-dismiss',"modal")
		var tid1=$("#tid1").val();
		var tname1=$("#tname1").val();
		var tmodel1=$("#tmodel1").val();
		var tcount1=$("#tcount1").val();
		var ttime1=$("#ttime1").val();
		var time=ttime1.replace(/-/g, '/');
		var	tremark1=$("#tremark1").val();
		$.post(
			"${ctx}/application/testingEquip/updateById.action",
			"id="+tid1+"&name="+tname1+"&testingModel="+tmodel1+"&count="+tcount1+"&time="+time+"&remark="+tremark1,
			function(data){
				  $($('#testing tbody tr td:hidden')).each(function(){
					  	if($(this).html()==tid1){
					  		$(this).parent().children("td").eq(1).text(tname1);
					  		$(this).parent().children("td").eq(2).text(tmodel1);
					  		$(this).parent().children("td").eq(3).text(tcount1);
					  		$(this).parent().children("td").eq(4).text(ttime1);
					  		$(this).parent().children("td").eq(5).text(tremark1);
						}
					});
			}
		);
	}
}
function deleteTesting(that){//检测设备的删除
	var testingId=$(that).parents("tr").children("td").eq(0).text();
	console.log($(that))
	$.post(
		"${ctx}/application/testingEquip/deleteById.action",
		"id="+testingId,
		function(data){
			if(data>0)
				$(that).parent().parent().remove();
		}
	);
}
function getTask(){
	var id=$("#id").val();
	if(id!=null){//如果id存在
		param="id="+id;
		$.ajaxSetup({async : false });
		$.post(
				"${ctx}/application/getApp.action",
				param,
				function(data){
					$("#enterprisename").val(data.enterprisename);
                    getPrintEquipment(data.enterprisename);//根据企业名称获取主要印刷设备
					getTestingEquip(data.enterprisename);//根据企业名称获取条码检测设备。
					$("#englishname").val(data.englishname);
					$("#fax").val(data.fax);
					$("#address").val(data.address);
					$("#postalcode").val(data.postalcode);
					$("#businessno").val(data.businessno);
					$("#enterprisekind").val(data.enterprisekind);
					$("#registercapital").val(data.registercapital);
					$("#artificialperson").val(data.artificialperson);
					$("#apjob").val(data.apjob);
					$("#aptel").val(data.aptel);
					$("#mainbusiness").val(data.mainbusiness);
					$("#linkman").val(data.linkman);
					$("#ljob").val(data.ljob);
					$("#ltel").val(data.ltel);
					$("#restbusiness").val(data.restbusiness);
					$("#employeetotal").val(data.employeetotal);
					$("#techniciantotal").val(data.techniciantotal);
					$("#bcprincipal").val(data.bcprincipal);
					$("#tpbusiness").val(data.tpbusiness);
					$("#tpopost").val(data.tpopost);
					$("#flat").attr("checked",data.flat);
					$("#gravure").attr("checked",data.gravure);
					$("#webby").attr("checked",data.webby);
					$("#flexible").attr("checked",data.flexible);
					$("#elsetype").val(data.elsetype);
					$("#papery").attr("checked",data.papery);
					$("#pastern").attr("checked",data.pastern);
					$("#frilling").attr("checked",data.frilling);
					$("#metal").attr("checked",data.metal);
					$("#plastic").attr("checked",data.plastic);
					$("#elsematerial").val(data.elsematerial);
					$("#offshootorganiz").val(data.offshootorganiz);
					$("#evaluatingresult").val(data.evaluatingresult);
					$("#certificateno").val(data.certificateno);
					$("#certappdate").val(data.certappdate);
					$("#printEquipment").val(data.printEquipment);
					$("#testEquipment").val(data.testEquipment);
					$("#remarks").val(data.remarks);
					$("#qualityno").val(data.qualityno);
					$("#pro").val(data.province);
					$("#cit").val(data.city);
					$("#are").val(data.area);
// 					$("#certtodate").val(data.certtodate);
// 					$("#createdate").val(data.createdate);
// 					$("#isrepeat").val(data.isrepeat);
// 					$("#status").val(data.status);
// 					$("#email").val(data.email);
// 					$("#bakdate").val(data.bakdate);
// 					$("#oldcertcode").val(data.oldcertcode);
// 					$("#cdate").val(data.cdate);
// 					$("#appdate").val(data.appdate);
// 					$("#zhuxiao").val(data.zhuxiao);
// 					$("#zhuxiaodate").val(data.zhuxiaodate);
// 					$("#pdate").val(data.pdate);
// 					$("#lastid").val(data.lastid);
					fileName="${ctx}/application/uploadDisplay.action?code="+$("#qualityno").val();
				}
			);
	}
}
Date.prototype.format = function(format) {
    var o = {
        "M+" : this.getMonth() + 1,// month
        "d+" : this.getDate(),// day
        "h+" : this.getHours(),// hour
        "m+" : this.getMinutes(),// minute
        "s+" : this.getSeconds(),// second
        "q+" : Math.floor((this.getMonth() + 3) / 3),// quarter
        "S" : this.getMilliseconds()
    };
    if (/(y+)/.test(format) || /(Y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for ( var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
};

function check(show){
	var $file;
	var imgObj;
	var $img;
	if(show.getAttribute("id")=="dialog1"){
		$file = $("#select1");
		$img = $("#display");
		imgObj = document.getElementById("display");
		window.open('../viewPic.jsp?flag=1&titleNo='+$('#titleno').val()+'&type=1')
	}else if(show.getAttribute("id")=="dialog2"){
		$file = $("#select2");
		$img = $("#display1");
		imgObj = document.getElementById("display1");
		window.open('../viewPic.jsp?flag=1&titleNo='+$('#titleno').val()+'&type=2')
	}else if(show.getAttribute("id")=="dialog3"){
		$file = $("#select3");
		$img = $("#display2");
		imgObj = document.getElementById("display2");
		window.open('../viewPic.jsp?flag=1&titleNo='+$('#titleno').val()+'&type=3')
	}
}

function checks(show){
	var $file;
	var imgObj;
	var $img;
	if(show.getAttribute("id")=="dialog1"){
		$file = $("#select1");
		$img = $("#display");
		imgObj = document.getElementById("display");
		window.open('../viewPic.jsp?flag=0&code='+getblob($file,$img,imgObj))
	}else if(show.getAttribute("id")=="dialog2"){
		$file = $("#select2");
		$img = $("#display1");
		imgObj = document.getElementById("display1");
		window.open('../viewPic.jsp?flag=0&code='+getblob($file,$img,imgObj))
	}else if(show.getAttribute("id")=="dialog3"){
		$file = $("#select3");
		$img = $("#display2");
		imgObj = document.getElementById("display2");
		window.open('../viewPic.jsp?flag=0&code='+getblob($file,$img,imgObj))
	}
}

function getblob($file,$img,imgObj){
	var fileObj = $file[0];
	var windowURL = window.URL || window.webkitURL;
	var dataURL;
	if(fileObj && fileObj.files && fileObj.files[0]){
		dataURL = windowURL.createObjectURL(fileObj.files[0]);
		return dataURL
	}else{
		return null
	}
}
//下载质量手册
function downloadQuality(){
	$file = $("#select3");
	var fileObj = $file[0];
	var windowURL = window.URL || window.webkitURL;
	var dataURL;
	var $img = $("#display1");
	if(typeof(fileObj.files[0])=="undefined"){
		window.open('${ctx}/application/uploadDisplay.action?code='+$("#qualityno").val());	
	}else{
		if(fileObj && fileObj.files && fileObj.files[0]){
			dataURL = windowURL.createObjectURL(fileObj.files[0]);
			$img.attr('src',dataURL);
			}else{
			dataURL = $file.val();
			var imgObj = document.getElementById("display1");
			imgObj.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
	}
}
}
function toAddPrinting() {
    if($("#enterprisename").val()=="") {
        wzj.alert("温馨提示","请先填写企业名称！");
        return;
    }
    $("#iPrintName").val("");
    $("#iPrintModel").val("");
    $("#iPrintPlace").val("");
    $("#iPrintNumber").val("");
    $("#iPrintNotes").val("");
//     $("#enterprisename").attr("readonly","readonly");
    $("#addPrinting").attr("href","#Modal_show3");
    $("#Modal_show3").show();
    $("#Modal_show3").find("input").val("");
}
function addPrinting() {
	$('#okbtns').removeAttr('data-dismiss',"modal")
	if($('#addPrint').validationEngine('validate')){
		$('#okbtns').attr('data-dismiss',"modal")
	    var printName = $("#iPrintName").val();
	    var printModel = $("#iPrintModel").val();
	    var printPlace = $("#iPrintPlace").val();
	    var printNumber = $("#iPrintNumber").val();
	    var printNotes = $("#iPrintNotes").val();
	    var companyName=$("#enterprisename").val();
	    var param="companyName="+companyName+"&printName="+printName
	        +"&printModel="+printModel+"&printPlace="+printPlace
	        +"&printNumber="+printNumber+"&printNotes=" +printNotes;
	    $.post(
	        "${ctx}/application/printEquipment/insert.action",
	        param,
	        function(data){
	            if(data > 0){
	            	$("#printing thead").show()
	                $("#printing").append("<tr><td style='display:none'>"+data+"</td><td>"+printName+"</td>" +
	                    "<td>"+printModel+"</td><td>"+printPlace+"</td>" +
	                    "<td>"+printNumber+"</td>" + "<td>"+printNotes+ "</td>" +
	                    "<td><button onclick='toUpdatePrinting(this)' class='btn btn-info'>修改</button></td>"+
	                    "<td><button onclick='deletePrinting(this)' class='btn btn-danger'>删除</button></td></tr>");
	            }
	        }
	    );
	}
}
function toAddTesting(){
	if($("#enterprisename").val()==""){
		wzj.alert("温馨提示","请先填写企业名称！");
		return;
	}
	$("#tid1").val();
	$("#tname1").val();
	$("#tmodel1").val();
	$("#tcount1").val();
	$("#ttime1").val();
	$("#tremark1").val();
// 	$("#enterprisename").attr("readonly","readonly");
	$("#addTesting").attr("href","#Modal_show4");
	$("#Modal_show4").show();
	$("#Modal_show4").find("input").val("");
}
function addTesting(){
	$('#oktest').removeAttr('data-dismiss',"modal")
	if($('#toAddTesting').validationEngine('validate')){
		$('#oktest').attr('data-dismiss',"modal")
		var name=$("#tname").val();
		var model=$("#tmodel").val();
		var count=$("#tcount").val();
		var time=$("#ttime").val().replace(/-/g, '/');
		var times=$("#ttime").val();
		var remark=$("#tremark").val();
		var companyName=$("#enterprisename").val();
		var param="name="+name+"&testingModel="+model+"&count="+count+"&time="+time+"&remark="
			+remark+"&companyName="+companyName;
		$.post(
				"${ctx}/application/testingEquip/insert.action",
				param,
				function(data){
					if(data>0){
						$("#testing thead").show()
						$("#testing").append("<tr><td style='display:none'>"+data+"</td><td>"+
								name+"</td><td>"+model+"</td><td>"+count+"</td><td>"+times+"</td><td>"+remark+
								"</td><td><button onclick='toUpdateTesting(this)' class='btn btn-info'>修改</button></td>"+
								"<td><button onclick='deleteTesting(this)' class='btn btn-danger'>删除</button></td></tr>");
					}
				}
		);
	}
}
</script>
</head>
<body>
<iframe id="id_iframe" name="nm_iframe" style="display:none;">
</iframe>
 <div id="foo"> 
			</div>
				<div class="row" id="title"> 
                        <div class="panel-body">
                           <span class="form-horizontal tasi-form">
                              <div class="form-group">
                                 <ul class="nav nav-pills" id="bar" style="margin-left: 1%">
								  <li class="active"><a href="#">1.填写申请表</a></li>
								  <li class="disabled"><a href="#">2.资料上传</a></li>
								 <!--  <li class="disabled"><a href="#">上传</a></li> -->
								</ul>
                              </div>							 
                           </span>
                        </div>
               </div>
	    <!-- BEGIN HEADER --> 
         <form id="appForm" action="" method="post" target="nm_iframe" enctype="multipart/form-data" style="margin-top:8%">
               <section class="panel">
                  <section class="wrapper">
			  <center style="margin-top:-70px;color:#2a8ba7"><h1>申请表</h1></center>
		<!-- 	  <div class="Noprint">${ctx}/application/insertReturn.action
			  <div class="btn-group pull-right" style="margin-top:-45px;margin-bottom:10px;">
                  <button class="btn btn-primary dropdown-toggle" data-toggle="dropdown">下载
                  <i class="fa fa-angle-down"></i>
                  </button>
                  <ul class="dropdown-menu pull-right">
                     <li><a href="javascript:printABC();">打印</a></li>
                     <li><a href="#">Save as PDF</a></li>
                     <li><a href="#">export to Excel</a></li>
                  </ul>
               </div>
               </div> -->
			   <!-- BEGIN ROW  -->
               <div class="row">
                  <div class="col-lg-12">
                     <section class="panel">
                        <header class="panel-heading">
                           <span class="label label-primary">企业信息</span>
                           <span class="tools pull-right" style="margin-top:-8px;">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                            <input type="hidden" name="id" id="id" value="${id}">
                            <input type="hidden" name="taskId" id="taskId" value="${taskId}">
                            <input type="hidden" name="titleno" id="titleno" value="${item.titleno}">
                            <input type="hidden" name="qualityno" id="qualityno" value="">
                            <input type="hidden" id="pro" value="">
                            <input type="hidden" id="cit" value="">
                            <input type="hidden" id="are" value="">
                        </header>
                        <div class="panel-body">
                           <span class="form-horizontal tasi-form">
                              <div class="form-group">
                                 <label class="col-sm-2 control-label labels">企业名称（中文）<b class="red">*</b></label>
                                 <div class="col-sm-4">
                                    <input type="text" id="enterprisename" name="enterprisename" value="${item.enterprisename}" class="form-control validate[required,custom[firmname]]">
                                    <input type="hidden" id="oldName" value="${item.enterprisename}">
                                    <input type="hidden" id="printId" value="" name="printId">
                                    <input type="hidden" id="testId" value="" name="testId">
                                 </div>
                                 <label class="col-sm-2 control-label labels">企业名称（英文）</label>
                                 <div class="col-sm-4">
                                    <input type="text" name="englishname" id="englishname" value="${item.englishname}" class="form-control validate[custom[onlyLetterNumber]]">
                                 </div>
                              </div>
                              <div class="form-group">
                                 <label class="col-sm-2 control-label labels">详细地址<b class="red">*</b></label>
                                 <div class="col-sm-10">
                                 	<div style="float: left;">
	                                 	<select id="province" onchange="changePro()" name="province"><option value="">全部</option></select>
	                                 	<select id="city" onchange="changeCit()" name="city"></select>
	                                 	<select id="area" onchange="changeAre()" name="area"></select>
                                 	</div>
                                    <input type="text" id="address" name="address" value="${item.address}" class="form-control validate[required]" style="float: left;width: 50%">
                                 </div>
                              </div>
                              <div class="form-group">
                               	 <label class="col-sm-2 control-label labels">印刷经营许可证号码<b class="red">*</b></label>
                                 <div class="col-sm-4">
                                    <input type="text" name="certificateno" id="certificateno" value="${item.certificateno}" class="form-control validate[required]">
                                    <input type="hidden" name="qualityno" id="qualityno" value="${item.qualityno}" class="form-control">
                                 </div>	
                                 <label class="col-sm-1 control-label labels nopaddingleft">邮政编码<b class="red">*</b></label>
                                 <div class="col-sm-2">
                                    <input type="text" name="postalcode" id="postalcode" value="${item.postalcode}" class="form-control validate[required,custom[chinaZip]]">
                                 </div>
                               	 <label class="col-sm-1 control-label labels">传真</label>
                                 <div class="col-sm-2">
                                    <input type="text" name="fax" id="fax" value="${item.fax}" class="form-control validate[custom[fax]]">
                                 </div>
                              </div>
							  <div class="form-group">
                                 <label class="col-sm-2 control-label labels">营业执照号码<b class="red">*</b></label>
                                 <div class="col-sm-4">
                                    <input type="text" name="businessno" id="businessno" value="${item.businessno}" class="form-control validate[required,custom[code]]">
                                 </div>
								 <label class="col-sm-1 control-label labels nopaddingleft">企业性质<b class="red">*</b></label>
                                 <div class="col-sm-2">
                                    <select name="enterprisekind" id="enterprisekind" class="form-control validate[required]">
                                    	<option value="">请选择</option>
                                    	<option value="国有" <c:if test="${item.enterprisekind=='国有'}">selected="selected"</c:if>>国有</option>
                                    	<option value="集体" <c:if test="${item.enterprisekind=='集体'}">selected="selected"</c:if>>集体</option>
                                    	<option value="私营" <c:if test="${item.enterprisekind=='私营'}">selected="selected"</c:if>>私营</option>
                                    	<option value="有限责任公司" <c:if test="${item.enterprisekind=='有限责任公司'}">selected="selected"</c:if>>有限责任公司</option>
                                    	<option value="股份有限公司" <c:if test="${item.enterprisekind=='股份有限公司'}">selected="selected"</c:if>>股份有限公司</option>
                                    	<option value="股份合作" <c:if test="${item.enterprisekind=='股份合作'}">selected="selected"</c:if>>股份合作</option>
                                    	<option value="联营企业" <c:if test="${item.enterprisekind=='联营企业'}">selected="selected"</c:if>>联营企业</option>
                                    	<option value="中外合资合作企业（港澳台）" <c:if test="${item.enterprisekind=='中外合资合作企业（港澳台）'}">selected="selected"</c:if>>中外合资合作企业（港澳台）</option>
                                    	<option value="外商独资投资企业（港澳台）" <c:if test="${item.enterprisekind=='外商独资投资企业（港澳台）'}">selected="selected"</c:if>>外商独资投资企业（港澳台）</option>
                                    	<option value="其他企业" <c:if test="${item.enterprisekind=='其他企业'}">selected="selected"</c:if>>其他企业</option>
                                    </select>
                                 </div>
								 <label class="col-sm-1 control-label labels nopaddingleft">注册资本<b class="red">*</b></label>
                                 <div class="col-sm-2">
                                    <input type="text" name="registercapital" id="registercapital" valut="${item.registercapital}" class="form-control validate[required,custom[username]]">
                                 </div>
                              </div>
							  <div class="form-group">
                                 <label class="col-sm-2 control-label labels">法人代表<b class="red">*</b></label>
                                 <div class="col-sm-4">
                                    <input type="text" name="artificialperson" id="artificialperson" valut="${item.artificialperson}" class="form-control validate[required]">
                                 </div>
								 <label class="col-sm-1 control-label labels">职务</label>
                                 <div class="col-sm-2">
                                    <input type="text" name="apjob" id="apjob" valut="${item.apjob}" class="form-control">
                                 </div>
								 <label class="col-sm-1 control-label labels">电话<b class="red">*</b></label>
                                 <div class="col-sm-2">
                                    <input type="text" name="aptel" id="aptel" valut="${item.aptel}" class="form-control validate[required,custom[telephone]]">
                                 </div>
                              </div>
							  <div class="form-group">
                                 <label class="col-sm-2 control-label labels">联系人<b class="red">*</b></label>
                                 <div class="col-sm-4">
                                    <input type="text" name="linkman" id="linkman" valut="${item.linkman}" class="form-control validate[required]">
                                 </div>
								 <label class="col-sm-1 control-label labels">职务</label>
                                 <div class="col-sm-2">
                                    <input type="text" name="ljob" id="ljob" valut="${item.ljob}" class="form-control">
                                 </div>
								 <label class="col-sm-1 control-label labels">电话<b class="red">*</b></label>
                                 <div class="col-sm-2"> 
                                    <input type="text" name="ltel" id="ltel" valut="${item.ltel}" class="form-control validate[required,custom[telephone]]">
                                 </div>
                              </div>
                           </span>
                        </div>
                     </section>
                  </div>
               </div>
			   <!-- END ROW  -->
			  <div class="row">
                  <div class="col-lg-12">
                     <section class="panel">
                        <header class="panel-heading">
                           <span class="label label-primary">经营范围</span>
                           <span class="tools pull-right" style="margin-top:-8px;">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
                           <span class="form-horizontal tasi-form">
                              <div class="form-group">
                                 <label class="col-sm-1 control-label labels">主营<b class="red">*</b></label>
                                 <div class="col-sm-11">
                                    <input type="text" name="mainbusiness" id="mainbusiness" value="${item.mainbusiness}" class="form-control validate[required]">
                                 </div>
                              </div>
                              <div class="form-group">
                                 <label class="col-sm-1 control-label labels">兼营</label>
                                 <div class="col-sm-11">
                                    <input type="text" name="restbusiness" id="restbusiness" value="${item.restbusiness}" class="form-control">
                                 </div>
                              </div>							 
                           </span>
                        </div>
                     </section>
                  </div>
               </div>
			   <div class="row">
                  <div class="col-lg-12">
                     <section class="panel">
                        <header class="panel-heading">
                           <span class="label label-primary">人员情况</span>
                           <span class="tools pull-right" style="margin-top:-8px;">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
                           <span class="form-horizontal tasi-form">
                              <div class="form-group">
                                 <label class="col-sm-2 control-label labels">职工总数</label>
                                 <div class="col-sm-2">
                                    <input type="text" name="employeetotal" id="employeetotal" value="${item.employeetotal}" class="form-control validate[custom[number]]">
                                 </div>
								 <label class="col-sm-2 control-label labels">技术人员数量</label>
                                 <div class="col-sm-2">
                                    <input type="text" name="techniciantotal" id="techniciantotal" value="${item.techniciantotal}" class="form-control validate[custom[number]]">
                                 </div>
                                 
                              </div>
                              <div class="form-group">
                              <label class="col-sm-2 control-label labels">条码印刷技术负责人</label>
                                 <div class="col-sm-2">
                                    <input type="text" name="bcprincipal" id="bcprincipal" value="${item.bcprincipal}" class="form-control">
                                 </div>
								 <label class="col-sm-2 control-label labels">职务</label>
                                 <div class="col-sm-2">
                                    <input type="text" name="tpbusiness" id="tpbusiness" value="${item.tpbusiness}" class="form-control">
                                 </div>
								 <label class="col-sm-2 control-label labels">职称</label>
                                 <div class="col-sm-2">
                                    <input type="text" name="tpopost" id="tpopost" value="${item.tpopost}" class="form-control">
                                 </div>
                              </div>							 
                           </span>
                        </div>
                     </section>
                  </div>
               </div>
			   <div class="row">
                  <div class="col-lg-12">
                     <section class="panel">
                        <header class="panel-heading">
                           <span class="label label-primary">条码印刷技术类型<b class="red">*</b></span>
                           <span class="tools pull-right" style="margin-top:-8px;">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
                           <span class="form-horizontal tasi-form">
                              <div class="form-group">
                                 <div class="col-sm-12">
								 <label class="col-sm-2 checkbox-inline"></label>
                                    <label class="col-sm-2 checkbox-inline">
                                    <input type="checkbox" class="validate[funcCall[minCheckbox]]" data-flag="grp1" name="flat" id="flat" value="true" <c:if test="${item.flat==true}">checked="checked"</c:if>>平板胶印
                                    </label>
                                    <label class="col-sm-2 checkbox-inline">
                                    <input type="checkbox" class="validate[funcCall[minCheckbox]]" data-flag="grp1" name="gravure" id="gravure" value="true" <c:if test="${item.gravure==true}">checked="checked"</c:if>>凹版印刷
                                    </label>
                                    <label class="col-sm-2 checkbox-inline">
                                    <input type="checkbox" class="validate[funcCall[minCheckbox]]" data-flag="grp1" name="webby" id="webby" value="true" <c:if test="${item.webby==true}">checked="checked"</c:if>>丝网印刷
                                    </label>
									<label class="col-sm-2 checkbox-inline">
                                    <input type="checkbox" class="validate[funcCall[minCheckbox]]" data-flag="grp1" name="flexible" id="flexible" value="true" <c:if test="${item.flexible==true}">checked="checked"</c:if>>柔性版印刷
                                    </label>
                                 </div>
                              </div>
                              <div class="form-group">
                                 <label class="col-sm-2 control-label labels">其他（简述）</label>
                                 <div class="col-sm-10">
                                    <input type="text" name="elsetype" id="elsetype" value="${item.elsetype}" class="form-control">
                                 </div>
                              </div>							 
                           </span>
                        </div>
                     </section>
                  </div>
               </div>
			   <div class="row">
                  <div class="col-lg-12">
                     <section class="panel">
                        <header class="panel-heading">
                           <span class="label label-primary">印刷载体材料<b class="red">*</b></span>
                           <span class="tools pull-right" style="margin-top:-8px;">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
                           <span class="form-horizontal tasi-form">
                              <div class="form-group">
                                 <div class="col-sm-12">
									<label class="col-sm-2"></label>
                                    <label class="col-sm-2">
                                    <input type="checkbox" class="validate[funcCall[minCheckbox]]" data-flag="grp" name="papery" id="papery" style="margin-right:4px;" value="true" <c:if test="${item.papery==true}">checked="checked"</c:if>>&nbsp;纸质
                                    </label>
                                    <label class="col-sm-2">
                                    <input type="checkbox" class="validate[funcCall[minCheckbox]]" data-flag="grp" name="pastern" id="pastern" style="margin-right:4px;" value="true" <c:if test="${item.pastern==true}">checked="checked"</c:if>>不干胶
                                    </label>
                                    <label class="col-sm-2">
                                    <input type="checkbox" class="validate[funcCall[minCheckbox]]" data-flag="grp" name="frilling" id="frilling" style="margin-right:4px;" value="true" <c:if test="${item.frilling==true}">checked="checked"</c:if>>瓦楞纸
                                    </label>
									<label class="col-sm-2">
                                    <input type="checkbox" class="validate[funcCall[minCheckbox]]" data-flag="grp" name="metal" id="metal" style="margin-right:4px;" value="true" <c:if test="${item.metal==true}">checked="checked"</c:if>>金属
                                    </label>
									<label class="col-sm-2">
                                    <input type="checkbox" class="validate[funcCall[minCheckbox]]" data-flag="grp"  name="plastic" id="plastic" style="margin-right:4px;" value="true" <c:if test="${item.plastic==true}">checked="checked"</c:if>>塑料
                                    </label>
                                 </div>
                              </div>
                              <div class="form-group">
                                 <label class="col-sm-2 control-label labels">其他（简述）</label>
                                 <div class="col-sm-10">
                                    <input type="text" name="elsematerial" id="elsematerial" value="${item.elsematerial}" class="form-control">
                                 </div>
                              </div>							 
                           </span>
                        </div>
                     </section>
                  </div>
               </div>
              
               <div class="row">
                  <div class="col-lg-12">
                     <section class="panel">
                        <header class="panel-heading">
                           <span class="label label-primary">提交材料</span>
                           <span class="tools pull-right" style="margin-top:-8px;">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
                           <span class="form-horizontal tasi-form">
						   <div class="form-group">
                             <div class="col-sm-12">
	                             <label class="col-sm-2 control-label labels">营业执照(.jpg/.jpeg/.png/.bmp)</label>
	                             <div class="col-lg-3">
	                             	<div class="col-sm-4">
	                             		<button type="button" onclick="select1.click()" class="btn btn-info" style="float: left;">选择文件</button>
	                             			<input class="change validate[funcCall[checkimg]]" data-type="img" type="file" name="business" id="select1" accept="image/bmp,image/jpg,image/jpeg,image/png">
	                             		<p id="em1" style="float: left;padding: 7px;margin: 0">${path1}</p>
	                             	</div>
	                             	<a onclick="check(this)" id="dialog1" class="">
	                             		<input type="button" class="btn btn-info" value="查看">
	                             	</a>
	                             </div>
                             </div>
                             </div>
                             <div class="form-group">
                             <div class="col-sm-12">
                             <label class="col-sm-2 control-label labels">印刷经营许可证(.jpg/.jpeg/.png/.bmp)</label>
                             <div class="col-lg-3">
                             	<div class="col-sm-4">
                             		<button type="button" onclick="select2.click()" class="btn btn-info" style="float: left;">选择文件</button>
                             			<input class="change validate[funcCall[checkimg]]" data-type="img" type="file" name="certificate" id="select2" accept="image/bmp,image/jpg,image/jpeg,image/png">
                             		<p id="em2" style="float: left;padding: 7px;margin: 0">${path2}</p>
                             	</div>
                             	<a onclick="check(this)" id="dialog2" class="">
                             		<input type="button" class="btn btn-info" value="查看">
                             	</a>
	                           </div> 						 
    	                       </div> 
    	                       </div> 
    	                       <div class="form-group">
                           <div class="col-sm-12">
                             	<label class="col-sm-2 control-label labels">质量手册(.pdf)</label>
                             	<div class="col-lg-3">
                             		<div class="col-sm-4">
	                             		<button type="button" onclick="select3.click()" class="btn btn-info" style="float: left;">选择文件</button>
	                             			<input class="change validate[funcCall[checkimg]]" data-type="pdf" type="file" name="quality" id="select3" accept="application/pdf">
	                             		<p id="em3" style="float: left;padding: 7px;margin: 0">${path3}</p>
	                             	</div>	
                             		<a id="dialog3" class="" onclick="check(this)">
                             			<input type="button" class="btn btn-info" value="预览">
                             		</a>
                          		</div> 	
                           </div>
                           </div>							 
                           </span>
                        </div>
                     </section>
                  </div>
               </div>
			   <div class="row">
                  <div class="col-lg-12">
                     <section class="panel">
                        <header class="panel-heading">
                           <span class="label label-primary">主要印刷设备<b class="red">*</b></span>
                            <input type="text" style="display:none" id="updatePrintingId">
                            <a href="" data-toggle='modal' id="showUpdatePrinting" style="display:none"></a>
                           <span class="label label-primary" onclick="toAddPrinting()" data-toggle='modal' id="addPrinting" href="">添加</span>
                           <span class="tools pull-right" style="margin-top:-8px;">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
                           <span class="form-horizontal tasi-form">
						   <div>
                             <div class="col-lg-1"></div>
                             <div class="col-lg-10">
                                  <%--<textarea class="form-control validate[required]" rows="6" name="printEquipment" id="printEquipment" value="${item.printEquipment}"></textarea>--%>
                                <table id="printing" class="table table-bordered">
                                 	<thead style="display: none;">
                                  		<tr>
                                  			<td>印刷设备名称</td>
                                            <td>印刷设备型号</td>
                                  			<td>印刷设备产地</td>
                                  			<td>印刷设备数量</td>
                                  			<td>备注</td>
                                  			<td>修改</td>
                                  			<td>删除</td>
                                  		</tr>
                                  	</thead>
                                  	<tbody></tbody>
                                 </table>
                                 <input class="validate[funcCall[gettr]]" id="trcount" style="opacity: 0;height: 0px">
                             </div>
                           </div> 						 
                           </span>
                        </div>
                     </section>
                  </div>
               </div>
			   <div class="row">
                  <div class="col-lg-12">
                     <section class="panel">
                        <header class="panel-heading">
                           <span class="label label-primary">条码检测设备</span>
                           <a href="" data-toggle='modal' id="showUpdateTesting" style="display:none"></a>
                           <a class="label label-primary" onclick="toAddTesting()" data-toggle='modal' id="addTesting" href="">添加</a>
                           <span class="tools pull-right" style="margin-top:-8px;">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
                           <span class="form-horizontal tasi-form">
						   <div>
                             <div class="col-lg-1"></div>
                             <div class="col-lg-10">
                                  <%-- <textarea class="form-control" rows="4" name="testEquipment" id="testEquipment" value="${item.testEquipment}"></textarea> --%>
                                  <table id="testing" class="table table-bordered">
                                  	<thead style="display: none;">
                                  		<tr>
                                  			<td>设备名称</td>
                                  			<td>设备型号</td>
                                  			<td>数量</td>
                                  			<td>最近一次校准日期</td>
                                  			<td>备注</td>
                                  			<td>修改</td>
                                  			<td>删除</td>
                                  		</tr>
                                  	</thead>
                                  </table>
                             </div>  
                           </div> 						 
                           </span>
                        </div>
                     </section>
                  </div>
               </div>
			   <div class="row">
                  <div class="col-lg-12">
                     <section class="panel">
                        <header class="panel-heading">
                           <span class="label label-primary">备注</span>
                           <span class="tools pull-right" style="margin-top:-8px;">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
                           <span class="form-horizontal tasi-form">
						   <div class="form-group">
                             <div class="col-lg-1"></div>
                             <div class="col-lg-10">
                                  <textarea class="form-control" rows="2" name="remarks" id="remarks" value="${item.remarks}"></textarea>
                             </div>  
                           </div> 						 
                           </span>
                        </div>
                     </section>
                  </div>
               </div>
               <div id="Modal_show1" class="modal fade" style="margin-top:12%">
	          <div class="modal-dialog">
	            <div class="modal-content">
	              <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
	                  &times;
	                </button>
	                <h4 class="modal-title">条码检测设备</h4>
	              </div>
	              <div class="modal-body" style="height: 200px">
	               <form action="" method="post" id="updateTesting"  enctype="multipart/form-data">
	              	<input type="hidden" id="tid1s">
	               <div class="row">
		              <div class="form-group">
		              	<div class="col-sm-3 labels"><b class="red">*</b> 设备名称：</div>
		              	<div class="col-sm-3"><input name="name" id="tname1s" type="text" class="form-control validate[required]"></div>
		              	<div class="col-sm-2 labels"><b class="red">*</b> 设备名称：</div>
		              	<div class="col-sm-3"><input name="testingModel" id="tmodel1s" type="text" class="form-control validate[required]"></div>
		              </div>
		              </div>
		              <br>
		               <div class="row">
		              <div class="form-group">
		              	<div class="col-sm-3 labels"><b class="red">*</b> 最近一次校准日期：</div>
		              	<div class="col-sm-3"><input name="time" id="ttime1s" type="text" class="form-control validate[required,custom[date]]"></div>
		              	<div class="col-sm-2 labels"><b class="red">*</b> 数量：</div>
		              	<div class="col-sm-3"><input name="count" id="tcount1s" type="text" class="form-control validate[required,custom[plus]]"></div>
		              </div>
		              </div><br>
		              <div class="row">
		              <div class="form-group">
		              	<div class="col-sm-3 labels">备注：</div>
		              	<div class="col-sm-8"><input name="remark" id="tremark1s" type="text" class="form-control"></div>
		              </div></div>
		              </form>
		         </div>
		         <div class="modal-footer" style="margin-top:0px;">
	                <button id="updatetests" class="btn btn-success" type="button" onclick="updateTesting()">
	                 	确定
	                </button>
	              </div>
	              </div>
	            </div>
	          </div>
	          <div id="Modal_show7" class="modal fade" style="margin-top:12%">
	          <div class="modal-dialog">
	            <div class="modal-content">
	              <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
	                  &times;
	                </button>
	                <h4 class="modal-title">条码检测设备</h4>
	              </div>
	              <div class="modal-body" style="height: 200px">
	               <form action="" method="post" id="updateTesting"  enctype="multipart/form-data">
	              	<input type="hidden" id="tid1">
	               <div class="row">
		              <div class="form-group">
		              	<div class="col-sm-3 labels"><b class="red">*</b> 设备名称：</div>
		              	<div class="col-sm-3"><input name="name" id="tname1" type="text" class="form-control validate[required]"></div>
		              	<div class="col-sm-2 labels"><b class="red">*</b> 设备型号：</div>
		              	<div class="col-sm-3"><input name="testingModel" id="tmodel1" type="text" class="form-control validate[required]"></div>
		              </div>
		              </div>
		              <br>
		               <div class="row">
		              <div class="form-group">
		              	<div class="col-sm-3 labels"><b class="red">*</b> 最近一次校准日期：</div>
		              	<div class="col-sm-3"><input name="time" id="ttime1" type="text" class="form-control validate[required,custom[date]]"></div>
		              	<div class="col-sm-2 labels"><b class="red">*</b> 数量：</div>
		              	<div class="col-sm-3"><input name="count" id="tcount1" type="text" class="form-control validate[required,custom[plus]]"></div>
		              </div>
		              </div><br>
		              <div class="row">
		              <div class="form-group">
		              	<div class="col-sm-3 labels">备注：</div>
		              	<div class="col-sm-8"><input name="remark" id="tremark1" type="text" class="form-control"></div>
		              </div></div>
		              </form>
		         </div>
		         <div class="modal-footer" style="margin-top:0px;">
	                <button id="updatetest" class="btn btn-success" type="button" onclick="updateTesting()">
	                 	确定
	                </button>
	              </div>
	              </div>
	            </div>
	          </div>
              <div id="Modal_show2" class="modal fade" style="margin-top:12%">
                  <div class="modal-dialog">
                      <div class="modal-content">
                          <div class="modal-header">
                              <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                  &times;
                              </button>
                              <h4 class="modal-title">主要印刷设备</h4>
                          </div>
                          <div class="modal-body" style="height: 200px">
                          <form action="" method="post" id="updatePrint"  enctype="multipart/form-data">
                              <input type="hidden" id="pId">
                              <div class="row">
                                  <div class="form-group">
                                      <div class="col-sm-3 labels nopaddingleft nopaddingright"><b class="red">*</b> 印刷设备名称：</div>
                                      <div class="col-sm-3"><input name="PrintName" id="uPrintName" type="text" class="form-control validate[required]"></div>
                                      <div class="col-sm-2 labels"><b class="red">*</b> 印刷设备型号：</div>
                                      <div class="col-sm-3"><input name="printModel" id="uPrintModel" type="text" class="form-control validate[required]"></div>
                                  </div>
                              </div>
                              <br>
                              <div class="row">
                                  <div class="form-group">
                                      <div class="col-sm-3 labels nopaddingleft nopaddingright"><b class="red">*</b> 印刷设备产地：</div>
                                      <div class="col-sm-3"><input name="printPlace" id="uPrintPlace" type="text" class="form-control validate[required]"></div>
                                      <div class="col-sm-2 labels"><b class="red">*</b> 印刷设备数量：</div>
                                      <div class="col-sm-3"><input name="printNumber" id="uPrintNumber" type="text" class="form-control validate[required,custom[plus]]"></div>
                                  </div>
                              </div>
                              <br>
                              <div class="row">
                                  <div class="form-group">
                                      <div class="col-sm-3 labels nopaddingleft nopaddingright">备注：</div>
                                      <div class="col-sm-8"><input name="printNotes" id="uPrintNotes" type="text" class="form-control"></div>
                                  </div>
                              </div>
                              </form>
                          </div>
                          <div class="modal-footer" style="margin-top:0px;">
                              <button id="updatebtn" class="btn btn-success" type="button" onclick="updatePrinting()">
                                  确定
                              </button>
                          </div>
                      </div>
                  </div>
              </div>
              <div id="Modal_show3" class="modal fade" style="margin-top:12%">
                  <div class="modal-dialog">
                      <div class="modal-content">
                          <div class="modal-header">
                              <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                  &times;
                              </button>
                              <h4 class="modal-title">主要印刷设备</h4>
                          </div>
                          <div class="modal-body" style="height: 200px">
                          <form action="" method="post" id="addPrint"  enctype="multipart/form-data">
                              <div class="row">
                                  <div class="form-group">
                                      <div class="col-sm-3 labels"><b class="red">*</b> 设备名称：</div>
                                      <div class="col-sm-3"><input name="PrintName" id="iPrintName" type="text" class="form-control validate[required]"></div>
                                      <div class="col-sm-2 labels"><b class="red">*</b> 设备型号：</div>
                                      <div class="col-sm-3"><input name="printModel" id="iPrintModel" type="text" class="form-control validate[required]"></div>
                                  </div>
                              </div>
                              <br>
                              <div class="row">
                                  <div class="form-group">
                                      <div class="col-sm-3 labels"><b class="red">*</b> 设备产地：</div>
                                      <div class="col-sm-3"><input name="printPlace" id="iPrintPlace" type="text" class="form-control validate[required]"></div>
                                      <div class="col-sm-2 labels"><b class="red">*</b> 设备数量：</div>
                                      <div class="col-sm-3"><input name="printNumber" id="iPrintNumber" type="text" class="form-control validate[required,custom[plus]]"></div>
                                  </div>
                              </div>
                              <br>
                              <div class="row">
                                  <div class="form-group">
                                      <div class="col-sm-3 labels">备注：</div>
                                      <div class="col-sm-8"><input name="printNotes" id="iPrintNotes" type="text" class="form-control"></div>
                                  </div>
                              </div>
                              </form>
                          </div>
                          <div class="modal-footer" style="margin-top:0px;">
                              <button id="okbtns" class="btn btn-success" type="button" onclick="addPrinting()">
                                  确定
                              </button>
                          </div>
                      </div>
                  </div>
              </div>
              <div id="Modal_show4" class="modal fade" style="margin-top:12%">
	          <div class="modal-dialog">
	            <div class="modal-content">
	              <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
	                  &times;
	                </button>
	                <h4 class="modal-title">条码检测设备</h4>
	              </div>
	              <div class="modal-body" style="height: 200px">
	              <form action="" method="post" id="toAddTesting"  enctype="multipart/form-data">
	               <div class="row">
		              <div class="form-group">
		              	<div class="col-sm-3 labels"><b class="red">*</b> 设备名称：</div>
		              	<div class="col-sm-3"><input name="name" id="tname" type="text" class="form-control validate[required]"></div>
		              	<div class="col-sm-2 labels"><b class="red">*</b> 设备型号：</div>
		              	<div class="col-sm-3"><input name="testingModel" id="tmodel" type="text" class="form-control validate[required]"></div>
		              </div>
		              </div>
		              <br>
		               <div class="row">
		              <div class="form-group">
		              	<div class="col-sm-3 labels"><b class="red">*</b> 最近一次校准日期：</div>
		              	<div class="col-sm-3"><input name="time" id="ttime" type="text" class="form-control validate[required,custom[date]]"></div>
		              	<div class="col-sm-2 labels"><b class="red">*</b> 数量：</div>
		              	<div class="col-sm-3"><input name="count" id="tcount" type="text" class="form-control validate[required,custom[plus]]"></div>
		              </div>
		              </div><br>
		              <div class="row">
		              <div class="form-group">
		              	<div class="col-sm-3 labels">备注：</div>
		              	<div class="col-sm-8"><input name="remark" id="tremark" type="text" class="form-control"></div>
		              </div></div>
		              </form>
		         </div>
		         <div class="modal-footer" style="margin-top:0px;">
	                <button id="oktest" class="btn btn-success" type="button" onclick="addTesting()">
	                 	确定
	                </button>
	              </div>
	              </div>
	            </div>
	          </div>
			   <div class="row">
	               <section class="panel">
	               <div class="panel-body text-center">
	               		<div class="btn-group"><input type="button" onclick="save()" class="btn-info  btn" value="保存"></div>
	               		
	               		<div class="btn-group">
	               		<!-- <input type="button" onclick="submitForm()" class="btn-success btn-block btn" value="申请"> onclick="save()"-->
	               		<input type="button" id="nextbtn1" onclick="nextStep(1)" class="btn-success  btn disabled" value="下一步"></div>
	               		
	               </div>
	               </section>
	            </div>
	            
            </section>
               </section>
	    <!-- END SECTION -->
		</form>
		<div style="display: none;" id="load1">
				<section class="wrapper">
				<div class="row">
                  <div class="">
                     <section class="panel">
                        <div class="panel-body">
                           <span class="form-horizontal tasi-form">
                              <div class="form-group text-center">
                              	<b>第一步：请下载申请表；第二步：请上传申请表（盖章版）</b>
                              </div>
                              <div class="form-group">
                                 <div class="col-sm-6 text-right labels">申请表：</div>
                                 <div class="col-sm-2">
                                   <button type="button" class="btn btn-info" onclick="downfunc()">下载</button>
                                 </div> 
                                 <div class="col-sm-4"></div>
                              </div>
                             <form id="forms" method="post" target="nm_iframe" action="${ctx}/application/startApply.action" enctype="multipart/form-data">
                              <div class="form-group">
                                 <div class="col-sm-6 text-right labels">申请表（盖章版）<b class="red">*</b>：</div>
                                 <div class="col-sm-4">
                                 	<button type="button" onclick="sq.click()" class="btn btn-info" >选择文件</button>
									<input type="file" name="file" id="sq"  class="change validate[required]" accept="application/pdf">
									<input type="hidden" name="appId" id="appId" value="">
									<input type="hidden" name="titleNo" id="titleNo" value="">
									<b id="em" style="padding: 7px;margin: 0">${path4}</b>
<!--                                    <button class="btn btn-info" onclick="upfunc()">上传</button> -->
					              <div class="modal-body hidden">
					                <table border="0" cellspacing="100px" align="center" style="height: 70px;width:100%">
										<tr id="trs"><td style="height: 10%;" align="center">
										
										</td></tr> 
									</table>
						         </div>
                                 </div> 
                                 <div class="col-sm-2"></div>
                              </div>
                               <div class="form-group text-center">
 			               		<div class="btn-group"> 
 			               		<button type="button" class="btn btn-success" onclick="laststep()">
					                 	上一步
					                </button></div>
					            <div class="btn-group"> 
 			               		<button type="button" id="okbtn" class="btn btn-success" onclick="upfunc()">
					                 	确定
					                </button></div> 
 			               		</div>	
                              </form>		
                            <!--   <div class="form-group">	
                              <div class="col-lg-5 col-sm-5"></div>
			               		<div class="col-lg-2 col-sm-2">
			               		<!-- <input type="button" onclick="submitForm()" class="btn-success btn-block btn" value="申请"> 
			               		<input type="button" id="nextbtn2" onclick="nextStep(2)" class="btn-success btn-block btn disabled" value="下一步"></div>
			               		<div class="col-lg-5 col-sm-5"></div>	
			               		</div> -->		 
                           </span>
                        </div>
                     </section>
                  </div>
               </div>
	            </section>
              </div>
               
              <!-- <div style="display: none;" id="load2">
				<div class="row">
				  <section class="wrapper">
                  <div class="col-lg-12">
                     <section class="panel">
                        <div class="panel-body">
                           <span class="form-horizontal tasi-form">
                            <form id="forms" method="post" target="nm_iframe" action="${ctx}/application/startApply.action" enctype="multipart/form-data">
                              <div class="form-group">
                                 <div class="col-sm-5"></div>
                                 <div class="col-sm-2" style="text-align: center;">
<!--                                    <button class="btn btn-info" onclick="upfunc()">上传</button> 
					              <div class="modal-body">
					                <table border="0" cellspacing="100px" align="center" style="height: 70px;width:100%">
										<tr id="trs"><td style="height: 10%;" align="center">
										<button onclick="sq.click()" class="btn btn-info" >选择文件</button>
											<input type="file" name="file" id="sq"  class="change validate[required]">
											<input type="hidden" name="appId" id="appId" value="">
											<input type="hidden" name="titleNo" id="titleNo" value="">
											<p id="em" style="padding: 7px;margin: 0">未上传文件</p>
										</td></tr>
									</table>
						         </div>
                                 </div> 
                                 <div class="col-sm-5"></div>
                              </div>
                              <div class="form-group">
                               <div class="col-lg-5 col-sm-5"></div> 
 			               		<div class="col-lg-2 col-sm-2 text-center"> 
 			               		<button id="okbtn" class="btn btn-success" onclick="upfunc()">
					                 	确定
					                </button></div>
			               		<div class="col-lg-5 col-sm-5"></div>	
 			               		</div>	
                              </form>
                           </span>
                        </div>
                     </section>
                  </div>
                  </section>
               </div>
              </div> -->
	 <!-- BEGIN JS -->
    <!--datatable的汉化在assets/data-tables/jquery.dataTables.js中修改 -->
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
        	$("#appForm").validationEngine({
    	        scrollOffset: 98,//必须设置，因为Toolbar position为Fixed
    	        promptPosition: 'topLeft',
    	        autoHidePrompt: true,
    	    });
            getProvince();
            $('#yuview').prop('href','../viewPDF.jsp?flag=1&code='+$('#qualityno').val())
    	    $("#addPrint").validationEngine({
    	        scrollOffset: 98,//必须设置，因为Toolbar position为Fixed
    	        promptPosition: 'topLeft',
    	        autoHidePrompt: true,
    	    });
    	    $("#updatePrint").validationEngine({
    	        scrollOffset: 98,//必须设置，因为Toolbar position为Fixed
    	        promptPosition: 'topLeft',
    	        autoHidePrompt: true,
    	    });
    	    $("#toAddTesting").validationEngine({
    	        scrollOffset: 98,//必须设置，因为Toolbar position为Fixed
    	        promptPosition: 'topLeft',
    	        autoHidePrompt: true,
    	    });
    	    $("#updateTesting").validationEngine({
    	        scrollOffset: 98,//必须设置，因为Toolbar position为Fixed
    	        promptPosition: 'topLeft',
    	        autoHidePrompt: true,
    	    });
    	    "${path1}" == ""?($("#em1").text("未上传文件")):($("#em1").text("${path1}"))
    	   	"${path2}" == ""?($("#em2").text("未上传文件")):($("#em2").text("${path2}"))
    	   	"${path3}" == ""?($("#em3").text("未上传文件")):($("#em3").text("${path3}"))
        });
        function getProvince(){
    		$.post(
    			"${pageContext.request.contextPath}/divisionRegion/getRegionChildren.action",
    			"regioncode=000000000000",
    			function(data){
    				if(data!=null&&data.length>0){
    					for(var i=0;i<data.length;i++){
    						var result='<option id="'+ data[i].regioncode +'" value="'+data[i].regioncode+'">'+data[i].regionname+"</option>";
    						$("#province").append(result);
    					}
    				}
    			}
    		);
    	}
        
        function changePro(){
    		var pro = $("#province option:selected").attr("id");
    		//市
    		$.post(
        		"${pageContext.request.contextPath}/divisionRegion/getRegionChildren.action",
        		"regioncode="+pro,
        		function(data){
        			$("#city").empty();
        			$("#area").empty();
        			$("#city").append("<option value=''> 请选择市 </option>");
        			if(data!=null&&data.length>0){
        				for(var i=0;i<data.length;i++){
        					var result='<option id="'+ data[i].regioncode +'" value="'+data[i].regioncode+'">'+data[i].regionname+"</option>";
        					$("#city").append(result);
        				}
        			}
        		}
        	);
    		var proname = $("#province option:selected").text();
    		$("#address").val(proname);
    	}
        function changeCit(){
    		var city = $("#city option:selected").attr("id");
    		//区
    		$.post(
        		"${pageContext.request.contextPath}/divisionRegion/getRegionChildren.action",
        		"regioncode="+city,
        		function(data){
        			$("#area").empty();
					$("#area").append("<option value=''> 请选择地区 </option>");
        			if(data!=null&&data.length>0){
        				for(var i=0;i<data.length;i++){
        					var result='<option id="'+ data[i].regioncode +'" value="'+data[i].regioncode+'">'+data[i].regionname+"</option>";
        					$("#area").append(result);
        				}
        			}
        		}
        	);
    		var proname = $("#province option:selected").text();
    		var cityname = $("#city option:selected").text();
    		$("#address").val(proname+cityname);
    	}
        function changeAre(){
        	var pro = $("#province option:selected").text();
        	var cit = $("#city option:selected").text();
        	var are = $("#area option:selected").text();
        	$("#address").val(pro+cit+are);
        }
        function saveForm(){
            if($('#appForm').validationEngine()){
	        	var taskId=$("#taskId").val();
	        	if(taskId==0){//新任务的保存
	        		$("#appForm").attr("action","${ctx}/application/save.action");
	        	}else{//退回任务的保存
	        		$("#appForm").attr("action","${ctx}/application/saveReturn.action");
	        	}
	        	$("#appForm").submit();
            }
        }
        function submitForm(){
        	if($('#appForm').validationEngine()){
	        	var taskId=$("#taskId").val();
	        	if(taskId==0){//新任务的提交
	        		$("#appForm").attr("action","${ctx}/application/insert.action");
	        	}else{//退回任务的提交
	        		$("#appForm").attr("action","${ctx}/application/insertReturn.action");
	        	}
	        	$("#appForm").submit();
        	}
        }

         $('#select1').on('change', function( e ){
                 //e.currentTarget.files 是一个数组，如果支持多个文件，则需要遍历
                 var name = e.currentTarget.files[0].name;
                 $('#em1').text(name);
                 $("#dialog1").attr("onclick","checks(this)")
          });
         $('#select2').on('change', function( e ){
                 //e.currentTarget.files 是一个数组，如果支持多个文件，则需要遍历
                 var name = e.currentTarget.files[0].name;
                 $('#em2').text(name);
                 $("#dialog2").attr("onclick","checks(this)")
          });
         $('#select3').on('change', function( e ){
                 //e.currentTarget.files 是一个数组，如果支持多个文件，则需要遍历
                 var name = e.currentTarget.files[0].name;
                 $('#em3').text(name);
                 $("#dialog3").attr("onclick","checks(this)")
          });
 	    $('#select1').on('click', function(){
			$('#em1').text("未上传文件");
			$('#select1').val("")
	     });
	    $('#select2').on('click', function(){
			$('#em2').text("未上传文件");
			$('#select2').val("")
	     });
	    $('#select3').on('click', function(){
			$('#em3').text("未上传文件");
			$('#select3').val("")
	     });
         function save(){
        	 if($("#em1").text()=="未上传文件"){
	        		$("#select1").addClass("validate[required]");
	        	}
	        	if($("#em2").text()=="未上传文件"){
	        		$("#select2").addClass("validate[required]");
	        	}
        	 if($('#appForm').validationEngine('validate')){
      			$("#appForm").attr("action","");
     			var printData1=$('td[name="print"]');
     			var printData="";
     			for(var i=0;i<printData1.length;i++){
     				printData=printData+$(printData1[i]).text()+",";
     			}
     			var testData1=$('td[name="test"]');
     			var testData="";
     			for(var j=0;j<testData1.length;j++){
     				testData=testData+$(testData1[j]).text()+",";
     			}
     			$("#printId").val(printData.substring(0,printData.length-1));
     			$("#testId").val(testData.substring(0,testData.length-1));
 			var enterpriseName=$("#enterprisename").val();
 			var oldName=$("#oldName").val();
 			 $("body").mLoading("show");
 			$.post(
 				"${ctx}/application/ifApply.action",
 				"enterpriseName="+enterpriseName,
 				function(data){
 					$("body").mLoading("hide");
 					console.log(data)
 					if(data>1){
 						wzj.alert('温馨提醒', '该企业已进行申请！')
 					}else if(data==1){
 			 			if(oldName==enterpriseName){
 								$("#appForm").attr("action","${ctx}/application/updateApp.action?oldName="+oldName);
 					        	$("#appForm").submit();
 					        	wzj.alert('温馨提醒', '保存成功，请进行下一步！')
 								$('#nextbtn1').removeClass('disabled')
 			 			}else{
 			 				wzj.alert('温馨提醒', '该企业已进行申请！')
 			 			}
 			 		}else{
						$("#appForm").attr("action","${ctx}/application/updateApp.action?oldName="+oldName);
			        	$("#appForm").submit();
			        	wzj.alert('温馨提醒', '保存成功，请进行下一步！')
						$('#nextbtn1').removeClass('disabled')
 			 		}
 					$("#oldName").val(enterpriseName);
 				}
 		);}
 		}
		function getParam(){
//			var param=$('#appForm').serialize();
//			$.post(
//					"${ctx}/application/getParam.action",
//					param,
//					function(data){
//				 		wzj.alert('温馨提醒', '保存成功，请进行下一步！')
//						$('#nextbtn1').removeClass('disabled')
//						$("#titleNo").val(data.titleNo);
//				 		$("#appId").val(data.id);
//					}
//			);
			$.ajax({    
				  url: "${ctx}/application/getParam.action",//异步请求路径  
				  async:false,//默认设置下，所有请求均为异步请求。如果需要发送同步请求，请将此选项设置为 false。注意，同步请求将锁住浏览器，用户其它操作必须等待请求完成才可以执行。  
				  success: function(data){//回调函数  
						$("#titleNo").val(data.titleno);
				 		$("#appId").val(data.id);
				  }});  
		}
	    function nextStep(num){
	    	var ename=$("#enterprisename").val();
	    	$.post(
	    		"${ctx}/application/getParam.action",
	    		"name="+ename,
	    		function(data){
	    			$("#titleNo").val(data.titleno);
			 		$("#appId").val(data.id);
	    		}
	    	)
			$('#appForm').hide()
			$('#load1').hide()
			$('#load2').hide()
			$('#load'+num).show()
			$('#bar li:eq('+num+')').removeClass('disabled')
			$('#bar li:eq('+num+')').addClass('active').siblings().removeClass('active').addClass('disabled')
		} 

		function downfunc(){
			var titleNo=$("#titleNo").val();
			 window.open('${ctx}/common/downPdf.action?type=1&titleNo='+titleNo);
			 if(titleNo!=""){
				wzj.alert('温馨提醒', '下载成功！')
				$('#nextbtn2').removeClass('disabled')
			}
		}
		function upfunc(){
			var file=$('#em').text();
			if(file=="未上传文件"){
				wzj.alert('温馨提醒', '请上传盖章后的申请表文件！')
			}else if(file.split(".")[1]=="pdf"||file.split(".")[1]=="PDF"){
				$("body").mLoading("show");
				//$("#forms").submit();
				$("#forms").ajaxSubmit({//一下是判断是否上传成功
					type:'post',  
		            url: '${ctx}/application/startApply.action',  
		            success:function(data){ 
						if(data==-1){
							$("body").mLoading("hide");
							wzj.alert('温馨提醒', '上传失败！')
						}else{
							$("body").mLoading("hide");
							wzj.alert('温馨提醒', '上传成功！')
							setTimeout(first,3000);
						}
		            }
				});
			}else{
				wzj.alert('温馨提醒', '请上传pdf格式文件！')
			}
// 			if($("#sq").val()!=""&&$("#titleNO").val()!=""&&$("#appId").val()!=""){
				
// 			}
		}
		function first(){
			location.href="${ctx}/application/toNewForms.action";
		}
		 $('#sq').on('change', function( e ){
	            //e.currentTarget.files 是一个数组，如果支持多个文件，则需要遍历
	            var name = e.currentTarget.files[0].name;
	            $('#em').text(name);
	     	});
	    function laststep(){
	    	$('#appForm').hide()
			$('#load1').hide()
			$('#load2').hide()
			$('#appForm').show()
			$('#bar li:eq(0)').addClass('active').siblings().removeClass('active').addClass('disabled')
		}
        $.validationEngineLanguage.allRules.date = {  
	    	      "regex": /^(19[0-9]{2}|20[0-9]{2})-(0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-9]|3[0-1])$/,
	    	      "alertText": "* 请输入正确的日期格式"  
	   	}; 
        $.validationEngineLanguage.allRules.username = {  
        	      "regex": /^[\w\u4E00-\u9FA5]+$/,
        	      "alertText": "* 请输入正确的格式"  
      	   	};
        $.validationEngineLanguage.allRules.type = {  
      	      "regex": /^[-\w]+$/,
      	      "alertText": "* 请输入正确的格式"  
    	}; 
        $.validationEngineLanguage.allRules.firmname = {  
        	      "regex": /^[\w\(\)\（\）\u4e00-\u9fa5]+$/,
        	      "alertText": "* 请输入正确的格式"  
      	};   
        $.validationEngineLanguage.allRules.code = {  
        	      "regex": /^[\w]+$/,
        	      "alertText": "* 请输入正确的格式"  
      	   	};  
        $.validationEngineLanguage.allRules.telephone = {  
       	      "regex":/^(0[0-9]{2})-\d{7,8}$|^(0[0-9]{3}-(\d{7,8}))$|^\d{8}$|^\d{7}$|^1[3|4|5|7|8][0-9]{9}$/,  
       	      "alertText": "* 请输入正确的电话号码"  
        };

        function gettr(field, rules, i, options){  
            $('#trcount').val($('#printing tbody tr').size())
        	  if($('#printing tbody tr').size()==0){  
        	      return "* 请添加主要印刷设备";  
        	   }  
        	}  
        function minCheckbox(field, rules, i, options){  
            var str = $(field).attr('data-flag') 
            var flag = false
            
            $("input[data-flag='"+str+"']").each(function(){
				flag = flag || $(this)[0].checked
            })
            if(flag==false){
            	return "* 至少选择一项"; 
            }   
        } 
        function checkimg(field, rules, i, options){  
            var str = $(field).val().split(".").pop()
            var img = "jpgjpegpngbmp"
            var pdf = "pdf"
            if($(field).attr("data-type")=="img"){
            	if(str!="未上传文件"&&img.indexOf(str)==-1){  
          	      return "* 请添加正确格式的文件";  
          	   }  
            }else{
            	if(str!="未上传文件"&&pdf.indexOf(str)==-1){  
            	      return "* 请添加正确格式的文件";  
            	   }  
            }
        } 
     </script>
 
</body>
</html>