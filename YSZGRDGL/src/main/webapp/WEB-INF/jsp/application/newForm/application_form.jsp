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

	<link rel="stylesheet" href="${ctx}/css/jquery.mloading.css">
	<script src="${ctx}/js/jquery-1.8.3.min.js"></script><!-- BASIC JS LIABRARY 1.8.3 -->
	<script src="${ctx}/js/style_self.js"></script><!-- 右键特效的js -->
	<link rel="stylesheet" href="${ctx}/css/validationEngine.jquery.css">
	<script src="${ctx}/js/jquery.validationEngine-zh_CN.js"></script>
	<script src="${ctx}/js/jquery.validationEngine.js"></script>
	<script src="${ctx}/js/jquery.mloading.js"></script>
	<style type="text/css">
		.change{
		    position: absolute;
		    overflow: hidden;
		    right: 0;
		    top: 0;
		    opacity: 0;
		    width: 0px;
		}
	</style>
<script type="text/javascript">
$(function(){
    $('.labels').css('text-align','right')
})

function check(show){
	var $file;
	var imgObj;
	var $img;
	if(show.getAttribute("id")=="dialog1"){
		$file = $("#select1");
		$img = $("#display");
		imgObj = document.getElementById("display");
		var fileObj = $file[0];
		var windowURL = window.URL || window.webkitURL;
		var dataURL;
		if(fileObj && fileObj.files && fileObj.files[0]){
		dataURL = windowURL.createObjectURL(fileObj.files[0]);
		window.open('../viewPic.jsp?flag=0&code='+dataURL);
		}else{
		dataURL = $file.val();
		imgObj.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
		imgObj.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = dataURL;
		}
	}else if(show.getAttribute("id")=="dialog2"){
		$file = $("#select2");
		$img = $("#display1");
		imgObj = document.getElementById("display1");
		var fileObj = $file[0];
		var windowURL = window.URL || window.webkitURL;
		var dataURL;
		if(fileObj && fileObj.files && fileObj.files[0]){
		dataURL = windowURL.createObjectURL(fileObj.files[0]);
		window.open('../viewPic.jsp?flag=0&code='+dataURL);
		}else{
		dataURL = $file.val();
		imgObj.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
		imgObj.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = dataURL;
		}
	}else if(show.getAttribute("id")=="dialog3"){
		$file = $("#select3");
		$img = $("#display2");
		imgObj = document.getElementById("display2");
		var fileObj = $file[0];
		var windowURL = window.URL || window.webkitURL;
		var dataURL;
		if(fileObj && fileObj.files && fileObj.files[0]){
		dataURL = windowURL.createObjectURL(fileObj.files[0]);
		window.open('../viewPDF.jsp?flag=0&code='+dataURL);
	}else{
		dataURL = $file.val();
		imgObj.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
		imgObj.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = dataURL;
	}
	}
}


</script>
</head>
<body>
 <div id="foo"> 
					</div>
<form id="appForm" action="${ctx}/application/insert.action" method="post" enctype="multipart/form-data">
<!--                <section class="panel"> -->
                  <section class="wrapper">
                 
			  <center style="margin-top:-70px;color:#2a8ba7"><h1>申请表</h1></center>
			<!--   <div class="Noprint">
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
                        </header>
                        <div class="panel-body">
                           <span class="form-horizontal tasi-form">
                              <div class="form-group">
                                 <label class="col-sm-2 control-label labels">企业名称（中文）<b class="red">*</b></label>
                                 <div class="col-sm-4">
                                    <input type="text" id="enterprisename" name="enterprisename" value="${item.enterprisename}" class="form-control validate[required]">
                                 </div>
                                 <label class="col-sm-2 control-label labels">企业名称（英文）</label>
                                 <div class="col-sm-4">
                                    <input type="text" name="englishname" id="englishname" value="${item.englishname}" class="form-control">
                                 </div>
                              </div>
                              <div class="form-group">
                                 <label class="col-sm-2 control-label labels">详细地址<b class="red">*</b></label>
                                 <div class="col-sm-10">
                                 	<div style="float: left;margin-right:5px">
	                                 	<select id="province" onchange="changePro()" name="province"><option selected="true" disabled="true">全部</option></select>
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
                                 <label class="col-sm-2 control-label labels ">营业执照号码<b class="red">*</b></label>
                                 <div class="col-sm-4">
                                    <input type="text" name="businessno" id="businessno" value="${item.businessno}" class="form-control validate[required]">
                                 </div>
								 <label class="col-sm-1 control-label labels nopaddingleft">企业性质</label>
                                 <div class="col-sm-2">
                                    <select name="enterprisekind" id="enterprisekind" class="form-control">
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
                                    <input type="text" name="registercapital" id="registercapital" valut="${item.registercapital}" class="form-control validate[required]">
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
                                    <input type="text" name="aptel" id="aptel" valut="${item.aptel}" class="form-control validate[required,custom[phone]]">
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
                                    <input type="text" name="ltel" id="ltel" valut="${item.ltel}" class="form-control validate[required,custom[phone]]">
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
                                 <div class="col-sm-5">
                                    <input type="text" name="mainbusiness" id="mainbusiness" value="${item.mainbusiness}" class="form-control validate[required]">
                                 </div>
                                 <label class="col-sm-1 control-label labels">兼营</label>
                                 <div class="col-sm-5">
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
                                    <input type="text" name="employeetotal" id="employeetotal" value="${item.employeetotal}" class="form-control ">
                                 </div>
								 <label class="col-sm-2 control-label labels">技术人员数量</label>
                                 <div class="col-sm-2">
                                    <input type="text" name="techniciantotal" id="techniciantotal" value="${item.techniciantotal}" class="form-control">
                                 </div>
                              </div>
                              <div class="form-group">
                                 <label class="col-sm-2 control-label labels">条码印刷技术负责人</label>
                                 <div class="col-sm-2">
                                    <input type="text" name="bcprincipal" id="bcprincipal" value="${item.bcprincipal}" class="form-control ">
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
                           <span class="label label-primary">条码印刷技术类型</span>
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
                                    <input type="checkbox"  name="flat" id="flat" value="true" <c:if test="${item.flat==true}">checked="checked"</c:if>>平板胶印
                                    </label>
                                    <label class="col-sm-2 checkbox-inline">
                                    <input type="checkbox"  name="gravure" id="gravure" value="true" <c:if test="${item.gravure==true}">checked="checked"</c:if>>凹版印刷
                                    </label>
                                    <label class="col-sm-2 checkbox-inline">
                                    <input type="checkbox"  name="webby" id="webby" value="true" <c:if test="${item.webby==true}">checked="checked"</c:if>>丝网印刷
                                    </label>
									<label class="col-sm-2 checkbox-inline">
                                    <input type="checkbox"  name="flexible" id="flexible" value="true" <c:if test="${item.flexible==true}">checked="checked"</c:if>>柔性版印刷
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
                           <span class="label label-primary">印刷载体材料</span>
                           <span class="tools pull-right" style="margin-top:-8px;">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
                           <span class="form-horizontal tasi-form">
                              <div class="form-group">
                                 <div class="col-sm-12">
									<label class="col-sm-2"></label>
                                    <label class="col-sm-2 checkbox-inline ">
                                    <input type="checkbox"  name="papery" id="papery" style="margin-right:4px;" value="true" <c:if test="${item.papery==true}">checked="checked"</c:if>>&nbsp;纸质
                                    </label>
                                    <label class="col-sm-2 checkbox-inline">
                                    <input type="checkbox"  name="pastern" id="pastern" style="margin-right:4px;" value="true" <c:if test="${item.pastern==true}">checked="checked"</c:if>>不干胶
                                    </label>
                                    <label class="col-sm-2 checkbox-inline">
                                    <input type="checkbox"  name="frilling" id="frilling" style="margin-right:4px;" value="true" <c:if test="${item.frilling==true}">checked="checked"</c:if>>瓦楞纸
                                    </label>
									<label class="col-sm-2 checkbox-inline">
                                    <input type="checkbox"  name="metal" id="metal" style="margin-right:4px;" value="true" <c:if test="${item.metal==true}">checked="checked"</c:if>>金属
                                    </label>
                                    <input type="checkbox"  name="plastic" id="plastic" style="margin-right:4px;" value="true" <c:if test="${item.plastic==true}">checked="checked"</c:if>>塑料
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
                             <label class="col-sm-2 control-label labels">营业执照<b class="red">*</b></label>
	                             <div class="col-sm-10">
	                             	<div class="col-sm-4">
	                             		<a href="javascript:;" class="btn btn-info" style="float: left;">选择文件
	                             			<input class="change validate[required]" type="file" name="business" id="select1"></a>
										
	                             		<p id="em1" style="float: left;padding: 7px;margin: 0">未上传文件</p>
	                             	</div>
	                             	<a onclick="check(this)" class="col-sm-2" id="dialog1">
	                             	<input type="button" class="btn btn-info" value="查看"></a>
	                             </div>
                             </div>
                             </div>
                             <div class="form-group">
                             <div class="col-sm-12">
                             <label class="col-sm-2 control-label labels">印刷经营许可证<b class="red">*</b></label>
                             <div class="col-sm-10">
                             	<div class="col-sm-4">
                             		<a href="javascript:;" class="btn btn-info" style="float: left;">选择文件
                             			<input class="change validate[required]" type="file" name="certificate" id="select2"></a>
                             		<p id="em2" style="float: left;padding: 7px;margin: 0">未上传文件</p>
                             	</div>
                             	<a class="col-sm-2"  onclick="check(this)" id="dialog2">
                             	<input type="button" class="btn btn-info" value="查看" ></a>
                           	</div> 	
                           	</div>
                           	</div>
                            <div class="form-group">
                            <div class="col-sm-12">
                             <label class="col-sm-2 control-label labels">质量手册</label>
	                             <div class="col-sm-10">
	                             	<div class="col-sm-4">
	                             		<a href="javascript:;" class="btn btn-info" style="float: left;">选择文件
	                             			<input class="change" type="file" name="quality" id="select3" accept="application/pdf"></a>
										
	                             		<p id="em3" style="float: left;padding: 7px;margin: 0">未上传文件</p>
	                             	</div>
	                             	<a class="col-sm-2"  onclick="check(this)" id="dialog3">
	                             	<input type="button" class="btn btn-info" value="预览"></a>
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
                           <span class="tools pull-right" style="margin-top:-8px;">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
                           <span class="form-horizontal tasi-form">
						   <div class="form-group">
                             <div class="col-lg-1"></div>
                             <div class="col-lg-10"> 
                                  <textarea class="form-control validate[required]" rows="6" name="printEquipment" id="printEquipment" value="${item.printEquipment}"></textarea>
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
                           <span class="label label-primary">条码检测设备*</span>
                           <span class="tools pull-right" style="margin-top:-8px;">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
                           <span class="form-horizontal tasi-form">
						   <div class="form-group">
                             <div class="col-lg-1"></div>
                             <div class="col-lg-10">
                                  <textarea class="form-control validate[required]" rows="4" name="testEquipment" id="testEquipment" value="${item.testEquipment}"></textarea>
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
			   <div class="row">
	               <section class="panel">
	               <div class="panel-body text-center">
	               		<div class="btn-group ">
	               			<button type="button" onclick="saveForm()" class="btn-info  btn">保存</button>
	               		</div>
	               		<div class="btn-group ">
	               			<button type="button"  onclick="javascript:history.back(-1);" class="btn-success  btn">返回</button>
	               		</div>
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
						<tr id="trs"><td><img id="display" src="" width="100%" alt="请上传照片"/></td></tr>
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
	           <div id="Modal_show1" class="modal fade">
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
						<tr id="trs"><td><img id="display1" src="" width="100%" alt="请上传照片"/></td></tr>
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
	                <h4 class="modal-title">质量手册</h4>
	              </div>
	              <div class="modal-body" style="height: 500px">
	              <iframe id="display2" src="" frameborder="0" width="100%" height="100%" leftmargin="0" topmargin="0" ></iframe>
		         </div>
		         <div class="modal-footer" style="margin-top:0px;">
	                <button data-dismiss="modal" class="btn btn-success" type="button">
	                 	确定
	                </button>
	              </div>
	              </div>
	            </div>
	          </div>
            </section>
		 <!-- END MAIN CONTENT -->
<!--                </section> -->
	    <!-- END SECTION -->
		</form>
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
            getProvince2();
    	    $("#appForm").validationEngine({
    	        scrollOffset: 98,//必须设置，因为Toolbar position为Fixed
    	        promptPosition: 'topLeft',
    	        autoHidePrompt: true,
    	    });
        });
        function getProvince(){
    		$.post(
    			"${ctx}/divisionRegion/getRegionChildren.action",
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
        		"${ctx}/divisionRegion/getRegionChildren.action",
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
        		"${ctx}/divisionRegion/getRegionChildren.action",
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
            if($('#appForm').validationEngine('validate')){       
                $("body").mLoading({text:"保存中..."});
	        	$("#appForm").attr("action","${ctx}/application/save.action");
	        	$("#appForm").submit();
            }
        }        
        function submitForm(){
            if($('#appForm').validationEngine('validate')){       
                $("body").mLoading({text:"提交中..."});
	        	$("#appForm").submit();
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
     </script>
 
</body>
</html>