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
	<script src="${ctx}/js/style_self.js"></script><!-- 右键特效的js -->
	<script src="${ctx}/js/jquery.validationEngine-zh_CN.js"></script>
	<script src="${ctx}/js/jquery.validationEngine.js"></script>
	<script src="${ctx}/assets/bootstrap-datepicker/js/bootstrap-datepicker.js"></script><!-- 日期显示 -->
	<link rel="stylesheet" type="text/css" href="${ctx}/assets/bootstrap-datepicker/css/datepicker.css">
	<style type="text/css">
		.change{
		    position: absolute;
		    overflow: hidden;
		    right: 0;
		    top: 0;
		    opacity: 0;
		    width: 0px;
		    width: 0px;
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
	var $inputs = $(".remark"); 
	$inputs.keyup(function() { 
		$inputs.val($(this).val()); 
	});
	$('.labels').css('text-align','right')
	getPrintEquipment($("#id").val());//根据企业名称获取主要印刷设备
	getTestingEquip($("#id").val());//根据企业名称获取条码检测设备。
	
});
//查看主要印刷设备
function getPrintEquipment(id){
	$.post(
	        "${ctx}/application/printEquipment/getByCompanyId.action",
	        "companyId="+id,
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

//查看检测设备
function getTestingEquip(id){
	$.post(
			"${ctx}/application/testingEquip/getByCompanyId.action",
			"companyId="+id,
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
function logout(){
	wzj.confirm('温馨提醒', '您确定退出本系统吗?', function(flag) {
		if(flag) {
			location.href='${ctx}/logout.action';
		}
	});
}

function logoutRedirect(){
	$.post(
		"${ctx}/logout.action",
		function(data){
			location.href="${ctx}/login.action"
		}
	);
}
function check(show){
	var $file;
	if(show.getAttribute("id")=="dialog1"){
		//$("#display").attr("src","${ctx}/common/getImg.action?code="+$("#businessno").val());
		window.open('../viewPic.jsp?flag=1&code='+$("#businessno").val())
		/*$.post(
			"${ctx}/common/getI.action",
			"code="+$("#businessno").val(),
			function(data){
				console.log(data)
				$("#display").attr("src","${ctx}/"+data);
			}
		)*/
		
		$file = $("#select1");
		//$("#display").attr("src","${ctx}/upload/showPicture.jsp?code="+$("#businessno").val());
	}else if(show.getAttribute("id")=="dialog2"){
		//$("#display").attr("src","${ctx}/common/getImg.action?code="+$("#qualityno").val());
		/*$.post(
				"${ctx}/common/getI.action",
				"code="+$("#certificateno").val(),
				function(data){
					console.log(data)
					$("#displayX").attr("src","${ctx}/"+data);
				}
			)*/
			window.open('../viewPic.jsp?flag=1&code='+$("#certificateno").val())
		$file = $("#select2");
	}
	var fileObj = $file[0];
	var windowURL = window.URL || window.webkitURL;
	var dataURL;
	var $img = $("#display");
	 
	if(fileObj && fileObj.files && fileObj.files[0]){
	dataURL = windowURL.createObjectURL(fileObj.files[0]);
	$img.attr('src',dataURL);
	}else{
	dataURL = $file.val();
	var imgObj = document.getElementById("display");
	imgObj.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
	imgObj.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = dataURL;
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
//删除检测设备
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
//删除主要检测设备
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
function toUpdatePrinting(that){
    $("#updateTestingId").val(that);
    var printingId=$(that).parents("tr").children("td").eq(0).text();
    $("#showUpdatePrinting").attr('href',"#Modal_show6");
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
    var	companyName=$("#companyName").val();
    var	companyId=$("#id").val();
    $.post(
        "${ctx}/application/printEquipment/updateById.action",
        "id="+pId+"&printName="+uPrintName+"&printModel="+uPrintModel+"&printPlace="+uPrintPlace+"&printNumber="+uPrintNumber+"&printNotes="+uPrintNotes+"&companyId="+companyId+"&companyName="+companyName,
        function(data){
            $($('#printing tbody tr td:hidden')).each(function(){
                if($(this).html()==pId){
                    $(this).parent().children("td").eq(1).text(uPrintName);
                    $(this).parent().children("td").eq(2).text(uPrintModel);
                    $(this).parent().children("td").eq(3).text(uPrintPlace);
                    $(this).parent().children("td").eq(4).text(uPrintNumber);
                    $(this).parent().children("td").eq(5).text(uPrintNotes);
                }
            });
        }
    );
	}
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
	var	companyName=$("#companyName").val();
    var	companyId=$("#id").val();
	$.post(
		"${ctx}/application/testingEquip/updateById.action",
		"id="+tid1+"&name="+tname1+"&testingModel="+tmodel1+"&count="+tcount1+"&time="+time+"&remark="+tremark1+"&companyId="+companyId+"&companyName="+companyName,
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
    $("#addPrinting").attr("href","#Modal_show5");
    $("#Modal_show5").show();
}
function addPrinting() {
	$('#okbtn').removeAttr('data-dismiss',"modal")
	if($('#addPrint').validationEngine('validate')){
		$('#okbtn').attr('data-dismiss',"modal")
	    var printName = $("#iPrintName").val();
	    var printModel = $("#iPrintModel").val();
	    var printPlace = $("#iPrintPlace").val();
	    var printNumber = $("#iPrintNumber").val();
	    var printNotes = $("#iPrintNotes").val();
	    var companyName=$("#enterprisename").val();
	    var companyId=$("#id").val();
	    var param="companyName="+companyName+"&printName="+printName
	        +"&printModel="+printModel+"&printPlace="+printPlace
	        +"&printNumber="+printNumber+"&printNotes=" +printNotes+"&companyId="+companyId;
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
	$("#addTesting").attr("href","#Modal_show4");
	$("#Modal_show4").show();
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
	 var companyId=$("#id").val();
	var param="name="+name+"&testingModel="+model+"&count="+count+"&time="+time+"&remark="
		+remark+"&companyName="+companyName+"&companyId="+companyId;
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
	    <!-- BEGIN HEADER --> 
<!--          <form id="appForm" action="" method="post" enctype="multipart/form-data"> -->
               <section class="panel">
                  <section class="wrapper">
			  <center style="margin-top:-70px;color:#2a8ba7"><h1>企业信息</h1></center>
		<!-- 	  <div class="Noprint">
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
			   <form id="appForm" action="" method="post" enctype="multipart/form-data">
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
<!--                             <input type="hidden" name="qualityno" id="qualityno" value=""> -->
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
                                 <input type="hidden" id="name" name="oldName" value="${item.enterprisename}">
                                <input type="hidden" id="cId" value="" name="cId">
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
                                    <input type="text" name="registercapital" id="registercapital" value="${item.registercapital}" class="form-control validate[required,custom[username]]">
                                 </div>
                              </div>
							  <div class="form-group">
                                 <label class="col-sm-2 control-label labels">法人代表<b class="red">*</b></label>
                                 <div class="col-sm-4">
                                    <input type="text" name="artificialperson" id="artificialperson" value="${item.artificialperson}" class="form-control validate[required]">
                                 </div>
								 <label class="col-sm-1 control-label labels">职务</label>
                                 <div class="col-sm-2">
                                    <input type="text" name="apjob" id="apjob" value="${item.apjob}" class="form-control">
                                 </div>
								 <label class="col-sm-1 control-label labels">电话<b class="red">*</b></label>
                                 <div class="col-sm-2">
                                    <input type="text" name="aptel" id="aptel" value="${item.aptel}" class="form-control validate[required,custom[telephone]]">
                                 </div>
                              </div>
							  <div class="form-group">
                                 <label class="col-sm-2 control-label labels">联系人<b class="red">*</b></label>
                                 <div class="col-sm-4">
                                    <input type="text" name="linkman" id="linkman" value="${item.linkman}" class="form-control validate[required]">
                                 </div>
								 <label class="col-sm-1 control-label labels">职务</label>
                                 <div class="col-sm-2">
                                    <input type="text" name="ljob" id="ljob" value="${item.ljob}" class="form-control">
                                 </div>
								 <label class="col-sm-1 control-label labels">电话<b class="red">*</b></label>
                                 <div class="col-sm-2">
                                    <input type="text" name="ltel" id="ltel" value="${item.ltel}" class="form-control validate[required,custom[telephone]]">
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
                                 <div class="col-sm-4">
                                    <input type="text" name="employeetotal" id="employeetotal" value="${item.employeetotal}" class="form-control  validate[custom[number]]">
                                 </div>
								 <label class="col-sm-2 control-label labels">技术人员数量</label>
                                 <div class="col-sm-4">
                                    <input type="text" name="techniciantotal" id="techniciantotal" value="${item.techniciantotal}" class="form-control validate[custom[number]]">
                                 </div>
                              </div>
                              <div class="form-group">
                                 <label class="col-sm-2 control-label labels">条码印刷技术负责人</label>
                                 <div class="col-sm-2">
                                    <input type="text" name="bcprincipal" id="bcprincipal" value="${item.bcprincipal}" class="form-control">
                                 </div>
								 <label class="col-sm-2 control-label labels">职务<b class="red">*</b></label>
                                 <div class="col-sm-2">
                                    <input type="text" name="tpbusiness" id="tpbusiness" value="${item.tpbusiness}" class="form-control validate[required]">
                                 </div>
								 <label class="col-sm-2 control-label labels">职称<b class="red">*</b></label>
                                 <div class="col-sm-2">
                                    <input type="text" name="tpopost" id="tpopost" value="${item.tpopost}" class="form-control validate[required]">
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
                                    <input type="checkbox" class="validate[funcCall[minCheckbox]]" data-flag="grp" name="plastic" id="plastic" style="margin-right:4px;" value="true" <c:if test="${item.plastic==true}">checked="checked"</c:if>>塑料
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
               <textarea style="display:none" class="form-control remark" rows="2" name="remarks" id="remarks">${item.remarks}</textarea>
</form>
			   <div class="row">
                  <div class="col-lg-12">
                     <section class="panel">
                        <header class="panel-heading">
<!--                            <span class="label label-primary">主要印刷设备*</span> -->
<!--                            <span class="tools pull-right" style="margin-top:-8px;"> -->
<!--                            <a href="javascript:;" class="fa fa-chevron-down"></a> -->
<!--                            </span> -->
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
						   <div class="form-group">
                             <div class="col-lg-1"></div>
                             <div class="col-lg-10">
<%--                                   <textarea class="form-control validate[required]" rows="6" name="printEquipment" id="printEquipment" >${item.printEquipment}</textarea> --%>
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
<!--                            <span class="label label-primary">条码检测设备*</span> -->
<!--                            <span class="tools pull-right" style="margin-top:-8px;"> -->
<!--                            <a href="javascript:;" class="fa fa-chevron-down"></a> -->
<!--                            </span> -->
							<span class="label label-primary">条码检测设备</span>
                           <input type="text" style="display:none" id="updateTestingId">
                           <a href="" data-toggle='modal' id="showUpdateTesting" style="display:none"></a>
                           <a class="label label-primary" onclick="toAddTesting()" data-toggle='modal' id="addTesting" href="">添加</a>
                           <span class="tools pull-right" style="margin-top:-8px;">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
                           <span class="form-horizontal tasi-form">
						   <div class="form-group">
                             <div class="col-lg-1"></div>
                             <div class="col-lg-10">
<%--                                   <textarea class="form-control validate[required]" rows="4" name="testEquipment" id="testEquipment" >${item.testEquipment}</textarea> --%>
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
                                  <textarea class="form-control" rows="2" name="remark" id="remark">${item.remarks}</textarea>
                             </div>  
                           </div> 						 
                           </span>
                        </div>
                     </section>
                  </div>
               </div>
			   <div class="row" id="btn1">
	               <section class="panel">
	               <div class="panel-body">
	               		<div class="col-lg-3 col-sm-3"></div>
	               		<div class="col-lg-2 col-sm-2"><input type="button" onclick="saveForm()" class="btn-info btn-block btn" value="保存"></div>
	               		<div class="col-lg-2 col-sm-2"></div>
	               		<div class="col-lg-2 col-sm-2">
	               		<!-- <input type="button" onclick="submitForm()" class="btn-success btn-block btn" value="申请"> -->
	               		<input type="button" onclick="javascript:history.back(-1);" class="btn-success btn-block btn" value="返回"></div>
	               		<div class="col-lg-3 col-sm-3"></div>
	               </div>
	               </section>
	            </div>
	     <div id="Modal_show" class="modal fade">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                  &times;
                </button>
                <h4 class="modal-title">营业执照</h4>
              </div>
              <div class="modal-body">
                <table width="100%" border="0" cellspacing="100px" align="center">
					<tr id="trs"><td><img src="" width="100%" id="display"/></td></tr>
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
	     <div id="Modal_show2" class="modal fade">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                  &times;
                </button>
                <h4 class="modal-title">印刷经营许可证</h4>
              </div>
              <div class="modal-body">
                <table width="100%" border="0" cellspacing="100px" align="center">
					<tr id="trs"><td><img src="" width="100%" id="displayX"/></td></tr>
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
	                <h4 class="modal-title">质量手册</h4>
	              </div>
	              <div class="modal-body">
	              <iframe id="display1" src="" frameborder="0" width="100%" leftmargin="0" topmargin="0" ></iframe>
		         </div>
		         <div class="modal-footer" style="margin-top:0px;">
	                <button data-dismiss="modal" class="btn btn-success" type="button">
	                 	确定
	                </button>
	              </div>
	              </div>
	            </div>
	          </div>
              <div id="Modal_show5" class="modal fade" style="margin-top:12%">
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
                                      <div class="col-sm-3 labels"><b class="red">*</b>设备名称：</div>
                                      <div class="col-sm-3"><input name="PrintName" id="iPrintName" type="text" class="form-control validate[required]"></div>
                                      <div class="col-sm-2 labels"><b class="red">*</b>设备型号：</div>
                                      <div class="col-sm-3"><input name="printModel" id="iPrintModel" type="text" class="form-control validate[required]"></div>
                                  </div>
                              </div>
                              <br>
                              <div class="row">
                                  <div class="form-group">
                                      <div class="col-sm-3 labels"><b class="red">*</b>设备产地：</div>
                                      <div class="col-sm-3"><input name="printPlace" id="iPrintPlace" type="text" class="form-control validate[required]"></div>
                                      <div class="col-sm-2 labels"><b class="red">*</b>设备数量：</div>
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
                              <button class="btn btn-success" type="button" onclick="addPrinting()" id="okbtn">
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
		              	<div class="col-sm-3 labels"><b class="red">*</b>设备名称：</div>
		              	<div class="col-sm-3"><input name="name" id="tname" type="text" class="form-control validate[required]"></div>
		              	<div class="col-sm-2 labels"><b class="red">*</b>设备型号：</div>
		              	<div class="col-sm-3"><input name="testingModel" id="tmodel" type="text" class="form-control validate[required]"></div>
		              </div>
		              </div>
		              <br>
		               <div class="row">
		              <div class="form-group">
		              	<div class="col-sm-3 labels"><b class="red">*</b>最近一次校准日期：</div>
		              	<div class="col-sm-3"><input name="time" id="ttime" type="text" class="form-control validate[required,custom[date]]"></div>
		              	<div class="col-sm-2 labels"><b class="red">*</b>数量：</div>
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
               <div id="Modal_show6" class="modal fade" style="margin-top:12%">
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
                                      <div class="col-sm-3 labels nopaddingleft nopaddingright"><b class="red">*</b>印刷设备名称：</div>
                                      <div class="col-sm-3"><input name="PrintName" id="uPrintName" type="text" class="form-control validate[required]"></div>
                                      <div class="col-sm-2 labels"><b class="red">*</b>印刷设备型号：</div>
                                      <div class="col-sm-3"><input name="printModel" id="uPrintModel" type="text" class="form-control validate[required]"></div>
                                  </div>
                              </div>
                              <br>
                              <div class="row">
                                  <div class="form-group">
                                      <div class="col-sm-3 labels nopaddingleft nopaddingright"><b class="red">*</b>印刷设备产地：</div>
                                      <div class="col-sm-3"><input name="printPlace" id="uPrintPlace" type="text" class="form-control validate[required]"></div>
                                      <div class="col-sm-2 labels"><b class="red">*</b>印刷设备数量：</div>
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
		              	<div class="col-sm-3 labels"><b class="red">*</b>设备名称：</div>
		              	<div class="col-sm-3"><input name="name" id="tname1" type="text" class="form-control validate[required]"></div>
		              	<div class="col-sm-2 labels"><b class="red">*</b>设备型号：</div>
		              	<div class="col-sm-3"><input name="testingModel" id="tmodel1" type="text" class="form-control validate[required]"></div>
		              </div>
		              </div>
		              <br>
		               <div class="row">
		              <div class="form-group">
		              	<div class="col-sm-3 labels"><b class="red">*</b>最近一次校准日期：</div>
		              	<div class="col-sm-3"><input name="time" id="ttime1" type="text" class="form-control validate[required,custom[date]]"></div>
		              	<div class="col-sm-2 labels"><b class="red">*</b>数量：</div>
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
            </section>
			<!-- END WRAPPER  -->
        
		 <!-- END MAIN CONTENT -->
               </section>
	    <!-- END SECTION -->
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
            getProvince();
            $('#yuview').prop('href','../viewPDF.jsp?flag=1&code='+$('#qualityno').val())
    	    $("#appForm").validationEngine({
    	        scrollOffset: 98,//必须设置，因为Toolbar position为Fixed
    	        promptPosition: 'topLeft',
    	        autoHidePrompt: true,
    	    });
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
        	var length=$("#printing tbody tr").length;
        	if(length==0){
        		wzj.alert("温馨提示","主要印刷设备是必填项！");
        		return;
        	}
        	$("#cId").val($("#id").val());
        	if($('#appForm').validationEngine('validate')){
        	$("#appForm").attr("action","");
        	var enterpriseName=$("#enterprisename").val();
        	$.post(
    				"${ctx}/application/ifApply.action",
    				"enterpriseName="+enterpriseName,
    				function(data){
    			 		if(data>1){
    			 			wzj.alert('温馨提醒', '该企业已存在！')
    			 		}else if(data==1){
    			 			if($("#name").val()!=""){
    			 				if($("#name").val()==enterpriseName){
   			 		            	$("#appForm").attr("action","${ctx}/common/saveCompany.action");
   			 			        	$("#appForm").submit();
   			 			        	$("#name").val(enterpriseName);	
    				 			}else{
    				 				wzj.alert('温馨提醒', '该企业已存在！')
    				 			}
    			 			}else{
    			 				wzj.alert('温馨提醒', '该企业已存在！')
    			 			}
    			 			
    			 		}else{
	 		            	$("#appForm").attr("action","${ctx}/common/saveCompany.action");
	 			        	$("#appForm").submit();
	 			        	$("#name").val(enterpriseName);	
    			 		}
    					
    				}
    				
    		);
        	
        	}
        }

         $('#select1').on('change', function( e ){
                 //e.currentTarget.files 是一个数组，如果支持多个文件，则需要遍历
                 var name = e.currentTarget.files[0].name;
                 $('#em1').text(name);
          });
         $('#select2').on('change', function( e ){
                 //e.currentTarget.files 是一个数组，如果支持多个文件，则需要遍历
                 var name = e.currentTarget.files[0].name;
                 $('#em2').text(name);
          });
         $('#select3').on('change', function( e ){
                 //e.currentTarget.files 是一个数组，如果支持多个文件，则需要遍历
                 var name = e.currentTarget.files[0].name;
                 $('#em3').text(name);
          });
	        $.validationEngineLanguage.allRules.date = {  
		    	      "regex": /^(19[0-9]{2}|20[0-9]{2})-(0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-9]|3[0-1])$/,
		    	      "alertText": "* 请输入正确的日期格式"  
		   	}; 
	        $.validationEngineLanguage.allRules.username = {  
	        	      "regex": /^[\w\u4E00-\u9FA5]+$/,
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
          function minCheckbox(field, rules, i, options){  
              var str = $(field).attr('data-flag') 
              var flag = false
              
              $("input[data-flag='"+str+"']").each(function(){
              	console.log($(this)[0].checked)
  				flag = flag || $(this)[0].checked
              })
              console.log(flag)
              if(flag==false){
              	return "* 至少选择一项"; 
              }   
          } 
	        function gettr(field, rules, i, options){  
	        	$('#trcount').val($('#printing tbody tr').size())
	        		if($('#printing tbody tr').size()==0){  
	        			return "* 请添加主要印刷设备";  
	        	}  
	        } 
   </script>
     </script>
 
</body>
</html>