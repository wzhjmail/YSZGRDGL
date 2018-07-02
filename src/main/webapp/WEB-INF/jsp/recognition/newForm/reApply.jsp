<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<%@ taglib prefix="myshiro" uri="/customize-tags" %>
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>复认申请表</title>
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
		#title{
			position: fixed;
			top: 0;
			left: 1%;
			z-index: 99999;
			width: 100%;
			background: white;
			border: 1px solid #e5e6e6;	
		}
	</style>
<script type="text/javascript">
$(function(){
	$('.labels').css('text-align','right')
	var id=$("#id").val();
	if(id!=null){//如果id存在
		param="id="+id;
		$.ajaxSetup({async : false });
		$.post(
				"${ctx}/application/getApp.action",
				param,
				function(data){
					$("#pro").val(data.province);
					$("#cit").val(data.city);
					$("#are").val(data.area);
					$("#enterprisename").val(data.enterprisename);
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
					$("#branchId").val(data.branchId);
					$("#titleno").val(data.titleno);
				}
			);
	}
});

function check(show){
	var $file;
	if(show.getAttribute("id")=="dialog1"){
		window.open('../viewPic.jsp?flag=1&titleNo='+$('#titleno1').val()+'&type=1')
		$file = $("#select1");
	}else if(show.getAttribute("id")=="dialog2"){
		window.open('../viewPic.jsp?flag=1&titleNo='+$('#titleno1').val()+'&type=2')
		$file = $("#select2");
	}else if(show.getAttribute("id")=="dialog3"){
 		imgObj.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = dataURL;
		window.open('../viewPic.jsp?flag=1&titleNo='+$('#titleno1').val()+'&type=3')
		}
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
								  <li class="active"><a href="#">填写申请表</a></li>
								  <li class="disabled"><a href="#">下载</a></li>
								  <li class="disabled"><a href="#">上传</a></li>
								</ul>
                              </div>							 
                           </span>
                        </div>
               </div>
	    <!-- BEGIN HEADER --> 
         <form id="appForm" action="" method="post" target="nm_iframe" enctype="multipart/form-data" style="margin-top:8%">
               <section class="panel">
                  <section class="wrapper">
			  <center style="margin-top:-70px;color:#2a8ba7"><h1>复认申请表</h1></center>
			  
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
                        	<input type="hidden" name="qualityno" id="qualityno" value="">
                        	<input type="hidden" name="branchId" id="branchId" value="">
                        	<input type="hidden" name="titleno1" id="titleno" value="${titleNo}">
                        	<input type="hidden" id="pro" value="">
                            <input type="hidden" id="cit" value="">
                            <input type="hidden" id="are" value="">
                        </header>
                        <div class="panel-body">
                           <span class="form-horizontal tasi-form">
                              <div class="form-group">
                                 <label class="col-sm-2 control-label labels">企业名称（中文）<b class="red">*</b></label>
                                 <div class="col-sm-6">
                                    <input type="text" id="enterprisename" name="enterprisename" value="${item.enterprisename}" class="form-control validate[required]">
                                 </div>
                                 
                              </div>
                              <div class="form-group">
                                 <label class="col-sm-2 control-label labels">详细地址<b class="red">*</b></label>
                                 <div class="col-sm-6">
                                    <input type="text" id="address" name="address" value="${item.address}" class="form-control validate[required]">
                                 </div>
                                  
                              </div>
							  <div class="form-group">
                                 <label class="col-sm-2 control-label labels">营业执照号码<b class="red">*</b></label>
                                 <div class="col-sm-2">
                                    <input type="text" name="businessno" id="businessno" value="${item.businessno}" class="form-control validate[required]">
                                 </div>
								 <label class="col-sm-2 control-label labels">企业性质</label>
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
								 <label class="col-sm-2 control-label labels">注册资本<b class="red">*</b></label>
                                 <div class="col-sm-2">
                                    <input type="text" name="registercapital" id="registercapital" valut="${item.registercapital}" class="form-control validate[required]">
                                 </div>
                              </div>
							  <div class="form-group">
                                 <label class="col-sm-2 control-label labels">法人代表<b class="red">*</b></label>
                                 <div class="col-sm-2">
                                    <input type="text" name="artificialperson" id="artificialperson" valut="${item.artificialperson}" class="form-control validate[required]">
                                 </div>
								 <label class="col-sm-2 control-label labels">职务</label>
                                 <div class="col-sm-2">
                                    <input type="text" name="apjob" id="apjob" valut="${item.apjob}" class="form-control">
                                 </div>
								 <label class="col-sm-2 control-label labels">电话<b class="red">*</b></label>
                                 <div class="col-sm-2">
                                    <input type="text" name="aptel" id="aptel" valut="${item.aptel}" class="form-control validate[required,custom[phone]]">
                                 </div>
                              </div>
							  <div class="form-group">
                                 <label class="col-sm-2 control-label labels">联系人<b class="red">*</b></label>
                                 <div class="col-sm-2">
                                    <input type="text" name="linkman" id="linkman" valut="${item.linkman}" class="form-control validate[required]">
                                 </div>
								 <label class="col-sm-2 control-label labels">职务</label>
                                 <div class="col-sm-2">
                                    <input type="text" name="ljob" id="ljob" valut="${item.ljob}" class="form-control">
                                 </div>
								 <label class="col-sm-2 control-label labels">电话<b class="red">*</b></label>
                                 <div class="col-sm-2">
                                    <input type="text" name="ltel" id="ltel" valut="${item.ltel}" class="form-control validate[required,custom[phone]]">
                                 </div>
                              </div>
                              <div class="form-group">
	                          	<label class="col-sm-2 control-label labels">邮政编码<b class="red">*</b></label>
	                            <div class="col-sm-2">
	                            	<input type="text" name="postalcode" id="postalcode" value="${item.postalcode}" class="form-control validate[required,custom[chinaZip]]">
	                          	</div>
	                          	<label class="col-sm-2 control-label labels">传真</label>
                                 <div class="col-sm-2">
                                    <input type="text" name="fax" id="fax" value="${item.fax}" class="form-control validate[custom[fax]]">
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
                                 <label class="col-sm-1 control-label">主营<b class="red">*</b></label>
                                 <div class="col-sm-11">
                                    <input type="text" name="mainbusiness" id="mainbusiness" value="${item.mainbusiness}" class="form-control validate[required]">
                                 </div>
                              </div>
                              <div class="form-group">
                                 <label class="col-sm-1 control-label">兼营</label>
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
                                    <input type="text" name="employeetotal" id="employeetotal" value="${item.employeetotal}" class="form-control ">
                                 </div>
								 <label class="col-sm-2 control-label labels">技术人员数量</label>
                                 <div class="col-sm-2">
                                    <input type="text" name="techniciantotal" id="techniciantotal" value="${item.techniciantotal}" class="form-control ">
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
                                    <input type="checkbox" name="flat" id="flat" value="true" <c:if test="${item.gravure==true}">checked="checked"</c:if>>平板胶印
                                    </label>
                                    <label class="col-sm-2 checkbox-inline">
                                    <input type="checkbox" name="gravure" id="gravure" value="true" <c:if test="${item.gravure==true}">checked="checked"</c:if>>凹版印刷
                                    </label>
                                    <label class="col-sm-2 checkbox-inline">
                                    <input type="checkbox" name="webby" id="webby" value="true" <c:if test="${item.webby==true}">checked="checked"</c:if>>丝网印刷
                                    </label>
									<label class="col-sm-2 checkbox-inline">
                                    <input type="checkbox" name="flexible" id="flexible" value="true" <c:if test="${item.flexible==true}">checked="checked"</c:if>>柔性版印刷
                                    </label>
                                 </div>
                              </div>
                              <div class="form-group">
                                 <label class="col-sm-2 control-label">其他（简述）</label>
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
                                    <label class="col-sm-2">
                                    <input type="checkbox" name="papery" id="papery" style="margin-right:4px;" value="true" <c:if test="${item.papery==true}">checked="checked"</c:if>>&nbsp;纸质
                                    </label>
                                    <label class="col-sm-2">
                                    <input type="checkbox" name="pastern" id="pastern" style="margin-right:4px;" value="true" <c:if test="${item.pastern==true}">checked="checked"</c:if>>不干胶
                                    </label>
                                    <label class="col-sm-2">
                                    <input type="checkbox" name="frilling" id="frilling" style="margin-right:4px;" value="true" <c:if test="${item.frilling==true}">checked="checked"</c:if>>瓦楞纸
                                    </label>
									<label class="col-sm-2">
                                    <input type="checkbox" name="metal" id="metal" style="margin-right:4px;" value="true" <c:if test="${item.metal==true}">checked="checked"</c:if>>金属
                                    </label>
									<label class="col-sm-2">
                                    <input type="checkbox" name="plastic" id="plastic" style="margin-right:4px;" value="true" <c:if test="${item.plastic==true}">checked="checked"</c:if>>塑料
                                    </label>
                                 </div>
                              </div>
                              <div class="form-group">
                                 <label class="col-sm-2 control-label">其他（简述）</label>
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
	                             <label class="col-sm-2 control-label labels">营业执照</label>
	                             <div class="col-lg-3">
	                             	<div class="col-sm-4">
	                             		<a href="javascript:;" class="btn btn-info" style="float: left;">选择文件
	                             			<input class="change" type="file" name="business" id="select1">
										</a>
	                             		<p id="em1" style="float: left;padding: 7px;margin: 0"></p>
	                             	</div>
	                             	<a onclick="check(this)" id="dialog1" class="col-sm-2">
	                             		<input type="button" class="btn btn-info" value="查看">
	                             	</a>
	                             </div>
                             </div>
                             </div>
                             <div class="form-group">
                             <div class="col-sm-12">
                             <label class="col-sm-2 control-label labels">印刷经营许可证</label>
                             <div class="col-lg-3">
                             	<div class="col-sm-4">
                             		<a href="javascript:;" class="btn btn-info" style="float: left;">选择文件
                             			<input class="change" type="file" name="certificate" id="select2">
									</a>
                             		<p id="em2" style="float: left;padding: 7px;margin: 0"></p>
                             	</div>
                             	<a onclick="check(this)" id="dialog2" class="col-sm-4">
                             		<input type="button" class="btn btn-info" value="查看">
                             	</a>
	                           </div> 						 
    	                       </div> 
    	                       </div> 
    	                       <div class="form-group">
                           <div class="col-sm-12">
                             	<label class="col-sm-2 control-label labels">质量手册</label>
                             	<div class="col-lg-3">
                             		<div class="col-sm-4">
	                             		<a href="javascript:;" class="btn btn-info" style="float: left;">选择文件
	                             			<input class="change" type="file" name="quality" id="select3">
										</a>
	                             		<p id="em3" style="float: left;padding: 7px;margin: 0"></p>
	                             	</div>	
                             		<a id="yuview" href=''class="col-sm-2">
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
                           <span class="label label-primary">主要印刷设备</span>
                           <span class="tools pull-right" style="margin-top:-8px;">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
                           <span class="form-horizontal tasi-form">
						   <div class="form-group">
                             <div class="col-lg-1"></div>
                             <div class="col-lg-10">
                                  <textarea class="form-control" rows="6" name="printEquipment" id="printEquipment" value="${item.printEquipment}"></textarea>
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
                           <span class="tools pull-right" style="margin-top:-8px;">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
                           <span class="form-horizontal tasi-form">
						   <div class="form-group">
                             <div class="col-lg-1"></div>
                             <div class="col-lg-10">
                                  <textarea class="form-control" rows="4" name="testEquipment" id="testEquipment" value="${item.testEquipment}"></textarea>
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
			   <div class="row" id="btn1">
	               <section class="panel">
	               <div class="panel-body">
	               		<div class="col-lg-3 col-sm-3"></div>
	               		<div class="col-lg-2 col-sm-2"><input type="button" onclick="save()" class="btn-info btn-block btn" value="保存"></div>
	               		<div class="col-lg-2 col-sm-2"></div>
	               		<div class="col-lg-2 col-sm-2">
	               		<!-- <input type="button" onclick="submitForm()" class="btn-success btn-block btn" value="申请"> onclick="save()"-->
	               		<input type="button" id="nextbtn1" onclick="nextStep(1)" class="btn-success btn-block btn disabled" value="下一步"></div>
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
            </section>
			<!-- END WRAPPER  -->
        
		 <!-- END MAIN CONTENT -->
               </section>
	    <!-- END SECTION -->
		</form>
		<div style="display: none;" id="load1">
				<section class="wrapper">
				<div class="row">
                  <div class="col-lg-12">
                     <section class="panel">
                        <div class="panel-body">
                           <span class="form-horizontal tasi-form">
                              <div class="form-group">
                                 <div class="col-sm-5"></div>
                                 <div class="col-sm-2" style="text-align: center;">
                                   <button class="btn btn-info" onclick="downfunc()">下载</button>
                                 </div> 
                                 <div class="col-sm-5"></div>
                              </div>		
                              <div class="form-group">	
                              <div class="col-lg-5 col-sm-5"></div>
			               		<div class="col-lg-2 col-sm-2">
			               		<!-- <input type="button" onclick="submitForm()" class="btn-success btn-block btn" value="申请"> -->
			               		<input type="button" id="nextbtn2" onclick="nextStep(2)" class="btn-success btn-block btn disabled" value="下一步"></div>
			               		<div class="col-lg-5 col-sm-5"></div>	
			               		</div>			 
                           </span>
                        </div>
                     </section>
                  </div>
               </div>
	            </section>
              </div>
                            <div style="display: none;" id="load2">
				<div class="row">
				  <section class="wrapper">
                  <div class="col-lg-12">
                     <section class="panel">
                        <div class="panel-body">
                           <span class="form-horizontal tasi-form">
                             <form id="forms" method="post" target="nm_iframe" action="${ctx}/recognition/startApply.action" enctype="multipart/form-data">
                              <div class="form-group">
                                 <div class="col-sm-5"></div>
                                 <div class="col-sm-2" style="text-align: center;">
<!--                                    <button class="btn btn-info" onclick="upfunc()">上传</button> -->

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
              </div>
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
	    $("#appForm").validationEngine({
	        scrollOffset: 98,//必须设置，因为Toolbar position为Fixed
	        promptPosition: 'topLeft',
	        autoHidePrompt: true,
	    });
        jQuery(document).ready(function() {
            getProvince();
             $('#yuview').prop('href','../viewPDF.jsp?code='+$('#qualityno').val())
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
        			$("#city").append("<option> 请选择市 </option>");
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
					$("#area").append("<option> 请选择地区 </option>");
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
	    
	    function save(){
			$("#appForm").attr("action","${ctx}/recognition/saveReApp.action");
        	$("#appForm").submit();
        	wzj.alert('温馨提醒', '保存成功，请进行下一步！')
			$('#nextbtn1').removeClass('disabled')
        	
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
			  url: "${ctx}/recognition/getParam.action",//异步请求路径  
			  async:false,//默认设置下，所有请求均为异步请求。如果需要发送同步请求，请将此选项设置为 false。注意，同步请求将锁住浏览器，用户其它操作必须等待请求完成才可以执行。  
			  success: function(data){//回调函数  
					$("#titleNo").val(data.titleno);
			 		$("#appId").val(data.id);
			  }});  
	}
    function nextStep(num){
    	if(num==1){
    		getParam();
    	}
		$('#appForm').hide()
		$('#load1').hide()
		$('#load2').hide()
		$('#load'+num).show()
		$('#bar li:eq('+num+')').removeClass('disabled')
		$('#bar li:eq('+num+')').addClass('active').siblings().removeClass('active').addClass('disabled')
	} 

	function downfunc(){
		var titleNo=$("#titleNo").val();
		 window.open('${ctx}/common/downPdf.action?type=2&titleNo='+titleNo);
		 if(titleNo!=""){
			wzj.alert('温馨提醒', '下载成功！')
			$('#nextbtn2').removeClass('disabled')
		}
	}
	function upfunc(){
		$("#forms").submit();
		if($("#sq").val()!=""&&$("#titleNO").val()!=""&&$("#appId").val()!=""){
			wzj.alert('温馨提醒', '上传成功！')
			$('#nextbtn3').removeClass('disabled')
			setTimeout(first,5000);
		}
	}
	function first(){
// 		location.href="${ctx}/recognition/reApply.action";
		location.href="${ctx}/common/toEnterpriseInfo.action";
	}
	 $('#sq').on('change', function( e ){
            //e.currentTarget.files 是一个数组，如果支持多个文件，则需要遍历
            var name = e.currentTarget.files[0].name;
            $('#em').text(name);
     	});
     </script>
 
</body>
</html>