<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="myshiro" uri="/customize-tags" %>
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>变更业务办理</title>
	<link href="${ctx}/assets/morris.js-0.4.3/morris.css" rel="stylesheet"><!-- MORRIS CHART CSS -->
	<link href="${ctx}/css/clndr.css" rel="stylesheet"><!-- CALENDER CSS -->
	<!-- 查询页面 -->
    <link href="${ctx}/css/bootstrap.min.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/css/bootstrap-reset.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="${ctx}/assets/font-awesome/css/font-awesome.css" rel="stylesheet"><!-- FONT AWESOME ICON STYLESHEET -->
    <link href="${ctx}/assets/data-tables/DT_bootstrap.css" rel="stylesheet" ><!-- DATATABLE CSS -->
    <link href="${ctx}/css/style.css" rel="stylesheet"><!-- THEME BASIC CSS -->
    <link href="${ctx}/css/style-responsive.css" rel="stylesheet"><!-- THEME BASIC RESPONSIVE  CSS -->
	<link href="${ctx}/css/style_self.css" rel="stylesheet"><!-- 右键特效的css -->
	<link href="${ctx}/css/fileinput.min.css" media="all" rel="stylesheet" type="text/css" />
	<link href="${ctx}/css/jquery.mloading.css" rel="stylesheet">
	<link rel="stylesheet" href="${ctx}/css/validationEngine.jquery.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/assets/bootstrap-datepicker/css/datepicker.css"><!-- BOOTSTRAP DATEPICKER PLUGIN CSS -->
	<script src="${ctx}/js/jquery-1.8.3.min.js"></script><!-- BASIC JS LIABRARY 1.8.3 -->
	<script src="${ctx}/js/style_self.js"></script><!-- 右键特效的js -->
	<script src="${ctx}/js/fileinput.min.js" type="text/javascript"></script>
	<script src="${ctx}/js/locales/zh.js" type="text/javascript"></script>
	<script src="${ctx}/js/themes/gly/theme.js" type="text/javascript"></script>
	<script src="${ctx}/js/themes/fa/theme.js" type="text/javascript"></script>
	<script src="${ctx}/js/jquery.form.js"></script>
	<script src="${ctx}/js/jquery.mloading.js"></script>
	<script src="${ctx}/assets/bootstrap-datepicker/js/bootstrap-datepicker.js"></script><!-- 日期显示 -->
	<script src="${ctx}/js/jquery.validationEngine-zh_CN.js"></script>
	<script src="${ctx}/js/jquery.validationEngine.js"></script>
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
			z-index: 1000;
			width: 101%;
			background: white;
			border: 1px solid #e5e6e6;	
		}
</style>
<script type="text/javascript">
	function check_state(ckb,check_name){
		switch(check_name){
		case "企业名称":
			if(ckb.checked){$("#CompanyName_New").removeAttr("readonly");}
			else{$("#CompanyName_New").attr("readonly","readonly");}
			break;
		case "企业地址":
			if(ckb.checked){$("#Address_New").removeAttr("readonly");}
			else{$("#Address_New").attr("readonly","readonly");}
			break;
		case "企业邮编":
			if(ckb.checked){$("#PostCode_new").removeAttr("readonly");}
			else{$("#PostCode_new").attr("readonly","readonly");}
			break;
		case "企业法人":
			if(ckb.checked){$("#Coporation_New").removeAttr("readonly");}
			else{$("#Coporation_New").attr("readonly","readonly");}
			break;
		case "法人电话":
			if(ckb.checked){$("#CoporationTel_New").removeAttr("readonly");}
			else{$("#CoporationTel_New").attr("readonly","readonly");}
			break;
		case "联系人":
			if(ckb.checked){$("#Linkman_New").removeAttr("readonly");}
			else{$("#Linkman_New").attr("readonly","readonly");}
			break;
		case "联系人电话":
			if(ckb.checked){$("#LinkmanTel_new").removeAttr("readonly");}
			else{$("#LinkmanTel_new").attr("readonly","readonly");}
			break;
		case "条码印刷技术类型":
			if(ckb.checked){
				$("#jslx").removeAttr("readonly");
				$("#js1").removeAttr("disabled");
				$("#js2").removeAttr("disabled");
				$("#js3").removeAttr("disabled");
				$("#js4").removeAttr("disabled");
			}else{
				$("#jslx").attr("readonly","readonly");
				$("#js1").attr("disabled","disabled");
				$("#js2").attr("disabled","disabled");
				$("#js3").attr("disabled","disabled");
				$("#js4").attr("disabled","disabled");
			}
			break;
		case "条码载体材料":
			if(ckb.checked){
				$("#ztcl").removeAttr("readonly");
				$("#cl1").removeAttr("disabled");
				$("#cl2").removeAttr("disabled");
				$("#cl3").removeAttr("disabled");
				$("#cl4").removeAttr("disabled");
				$("#cl5").removeAttr("disabled");
			}else{
				$("#ztcl").attr("readonly","readonly");
				$("#cl1").attr("disabled","disabled");
				$("#cl2").attr("disabled","disabled");
				$("#cl3").attr("disabled","disabled");
				$("#cl4").attr("disabled","disabled");
				$("#cl5").attr("disabled","disabled");
			}
			break;
		case "发证日期":
			if(ckb.checked){$("#fzrq").removeAttr("disabled");}
			else{$("#fzrq").attr("disabled","disabled");}
			break;
		case "到期日期":
			if(ckb.checked){$("#dqrq").removeAttr("disabled");}
			else{$("#dqrq").attr("disabled","disabled");}
			break;
		}
	}
	$(function(){
		$('#fzrq').datepicker({
			format:'yyyy-mm-dd',
		});
		$('#dqrq').datepicker({
			format:'yyyy-mm-dd',
		});
		var id=$("#id").val();
		if(id!=null){//如果id存在
			param="id="+id;
			$.ajaxSetup({async : false });
			$.post(
				"${ctx}/alternation/getChange.action",
				param,
				function(data){
				$("#titleNo").val(data.titleno);
				$("#titleNo2").val(data.titleno);
				$("#pid").val(data.pid);
				$("#CompanyName_Old").val(data.companynameOld);
				$("#AddressOld").val(data.addressOld);
				$("#Linkman_Old").val(data.linkmanOld);
				$("#CertAppDate_Old").val(data.certappdateOld);
				$("#CertToDate_Old").val(data.certtodateOld);
				$("#Coporation_old").val(data.coporationOld);
				$("#LinkmanTel_old").val(data.linkmantelOld);
				$("#PostCodeold").val(data.postcodeOld);
				$("#CoporationTel_old").val(data.coporationtelOld);
				
				$("#CompanyName_New").val(data.companynameNew);
				$("#Address_New").val(data.addressNew);
				$("#Linkman_New").val(data.linkmanNew);
				$("#fzrq").val(data.certappdateNew);
				$("#dqrq").val(data.certtodateNew);
				$("#Coporation_New").val(data.coporationNew);
				$("#LinkmanTel_New").val(data.linkmantelNew);
				$("#PostCode_new").val(data.postcodeNew);
				$("#CoporationTel_New").val(data.coporationtelNew);
				var changeType=data.changeType.split(",");
				for(var i=0;i<changeType.length;i++){
					switch(changeType[i]){
						case '企业名称':
							$("#cb1").attr("checked","true");
							break;
						case '企业地址':
							$("#cb2").attr("checked","true");
							break;
						case '企业邮编':
							$("#cb3").attr("checked","true");
							break;
						case '企业法人':
							$("#cb4").attr("checked","true");
							break;
						case '法人电话':
							$("#cb5").attr("checked","true");
							break;
						case '联系人':
							$("#cb6").attr("checked","true");
							break;
						case '联系人电话':
							$("#cb7").attr("checked","true");
							break;
						case '条码印刷技术类型':
							$("#cb8").attr("checked","true");
							break;
						case '条码载体材料':
							$("#cb9").attr("checked","true");
							break;
						case '发证日期':
							$("#cb10").attr("checked","true");
							break;
						case '到期日期':
							$("#cb11").attr("checked","true");
							break;
						default:break;
					}
				}
				//印刷类型
				var printTypeOld=data.printtypeOld;
				var printTypeOld1 = printTypeOld.split(",");
				for(var i=0;i<printTypeOld1.length;i++){
					if("平板胶印"==printTypeOld1[i]){
						$("#ys1").attr("checked",true);
					}else if("凹版印刷"==printTypeOld1[i]){
						$("#ys2").attr("checked",true);
					}else if("丝网印刷"==printTypeOld1[i]){
						$("#ys3").attr("checked",true);
					}else if("柔性版印刷"==printTypeOld1[i]){
						$("#ys4").attr("checked",true);
					}else{
						$("#yso").val(printTypeOld1[i]);
					}
				}
				
				//印刷材料
				var materialOld=data.materialOld;
				var materialOld1 = materialOld.split(",");
				for(var i=0;i<materialOld1.length;i++){
					if("纸质"==materialOld1[i]){
						$("#bcl1").attr("checked",true);
					}else if("不干胶"==materialOld1[i]){
						$("#bcl2").attr("checked",true);
					}else if("瓦楞纸"==materialOld1[i]){
						$("#bcl3").attr("checked",true);
					}else if("金属"==materialOld1[i]){
						$("#bcl4").attr("checked",true);
					}else if("塑料"==materialOld1[i]){
						$("#bcl5").attr("checked",true);
					}else{
						$("#bclo").val(materialOld1[i]);
					}
				}
				
				var printTypeNew=data.printtypeNew;
				var printTypeNew1 = printTypeNew.split(",");
				for(var i=0;i<printTypeNew1.length;i++){
					if("平板胶印"==printTypeNew1[i]){
						$("#js1").attr("checked",true);
					}else if("凹版印刷"==printTypeNew1[i]){
						$("#js2").attr("checked",true);
					}else if("丝网印刷"==printTypeNew1[i]){
						$("#js3").attr("checked",true);
					}else if("柔性版印刷"==printTypeNew1[i]){
						$("#js4").attr("checked",true);
					}else{
						$("#jslx").val(printTypeNew1[i]);
					}
				}
				
				var materialNew=data.materialNew;
				var materialNew1 = materialNew.split(",");
				for(var i=0;i<materialNew1.length;i++){
					if("纸质"==materialNew1[i]){
						$("#cl1").attr("checked",true);
					}else if("不干胶"==materialNew1[i]){
						$("#cl2").attr("checked",true);
					}else if("瓦楞纸"==materialNew1[i]){
						$("#cl3").attr("checked",true);
					}else if("金属"==materialNew1[i]){
						$("#cl4").attr("checked",true);
					}else if("塑料"==materialNew1[i]){
						$("#cl5").attr("checked",true);
					}else{
						$("#ztcl").val(materialNew1[i]);
					}
				}
			});
		}
	});
function check(){
	//条码印刷类型
	var result="";
	if($("#js1").attr("checked")){
		result+="平板胶印,";
	}
	if($("#js2").attr("checked")){
		result+="凹版印刷,"
	}
	if($("#js3").attr("checked")){
		result+="丝网印刷,"
	}
	if($("#js4").attr("checked")){
		result+="柔性版印刷,"
	}
	if($("#jslx").val()!=null&&$("#jslx").val()!="")
		result+=$("#jslx").val()
	$("#PrintType_New").val(result);
	//条码载体材料
	result="";
	if($("#cl1").attr("checked")){
		result+="纸质,"
	}
	if($("#cl2").attr("checked")){
		result+="不干胶,"
	}
	if($("#cl3").attr("checked")){
		result+="金属,"
	}
	if($("#cl4").attr("checked")){
		result+="塑料,"
	}
	if($("#ztcl").val()!=null&&$("#ztcl").val()!="")
		result+=$("#ztcl").val();
	$("#Material_New").val(result);
}
function uploadFiles(){
	$("#mulfile").fileinput({
    	uploadUrl:"${ctx}/commons/uploadFile?titleNo="+$("#titleNo").val()+"&describeId=11",
		fileType: "any",
		language : 'zh',
		enctype : 'multipart/form-data',
		
	});
}
function clearFile(){
	$("#file-content").html("");
	$("#file-content").html('<input id="mulfile" name="mfile" type="file" class="file-loading" multiple="multiple">');
}
</script>
</head>
<body>
<iframe id="id_iframe" name="nm_iframe" style="display:none;">
</iframe>
	<div class="row" id="title"> 
          <div class="panel-body">
             <span class="form-horizontal tasi-form">
                <div class="form-group">
                   <ul class="nav nav-pills" id="bar" style="margin-left: 1%">
				<li class="active"><a href="#">1.填写申请表</a></li>
				<li class="disabled"><a href="#">2.资料上传</a></li>
			</ul>
                 </div>							 
                </span>
          </div>
       </div>
			    <form id="changeForm" action="" method="post" onsubmit="check();" style="margin-top: 8%">
               <section class="panel">
                  <section class="wrapper">
			  <center style="margin-top:-70px;color:#2a8ba7"><h1>变更申请表</h1></center>
			  <div class="Noprint">
			<!--   <div class="btn-group pull-right" style="margin-top:-45px;margin-bottom:10px;">
                  <button class="btn btn-primary dropdown-toggle" data-toggle="dropdown">下载
                  <i class="fa fa-angle-down"></i>
                  </button>
                  <ul class="dropdown-menu pull-right">
                     <li><a href="javascript:printABC();">打印</a></li>
                     <li><a href="#">Save as PDF</a></li>
                     <li><a href="#">export to Excel</a></li>
                  </ul>
               </div> -->
               </div>
			   <!-- BEGIN ROW  -->
               <div class="row">
                  <div class="col-lg-12">
                     <section class="panel">
                        <header class="panel-heading">
                           <span class="label label-primary">需要变更的内容</span>
                        </header>
                        <div class="panel-body" style="text-align:center">  
                   		    <input type="checkbox" id="cb1" style="margin-left:10px" onclick="check_state(this,'企业名称')">企业名称
                            <input type="checkbox" id="cb2" style="margin-left:10px" onclick="check_state(this,'企业地址')">企业地址
                            <input type="checkbox" id="cb3" style="margin-left:10px" onclick="check_state(this,'企业邮编')">企业邮编
                            <input type="checkbox" id="cb4" style="margin-left:10px" onclick="check_state(this,'企业法人')">企业法人
                            <input type="checkbox" id="cb5" style="margin-left:10px" onclick="check_state(this,'法人电话')">法人电话
                            <input type="checkbox" id="cb6" style="margin-left:10px" onclick="check_state(this,'联系人')">联系人
                            <input type="checkbox" id="cb7" style="margin-left:10px" onclick="check_state(this,'联系人电话')">联系人电话
                            <input type="checkbox" id="cb8" style="margin-left:10px" onclick="check_state(this,'条码印刷技术类型')">条码印刷技术类型
                            <input type="checkbox" id="cb9" style="margin-left:10px" onclick="check_state(this,'条码载体材料')">条码载体材料
                            <input type="checkbox" id="cb10" style="margin-left:10px;" onclick="check_state(this,'发证日期')">发证日期
                            <input type="checkbox" id="cb11" style="margin-left:10px;" onclick="check_state(this,'到期日期')">到期日期   
                            <input type="hidden" id="changeType" name="changeType" value="">                                                                           
                        </div>
                     </section>
                  </div>
              </div>
			  <!-- END ROW  -->
			  <div class="row">
                  <div class="col-lg-12">
                     <section class="panel">
                        <header class="panel-heading">
                           <span class="label label-primary">企业名称</span>
                           <span class="tools pull-right" style="margin-top:-8px;">
                           	<a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                           <input type="hidden" id="id" name="id" value="${id}">
                           <input type="hidden" id="taskId" name="taskId" value="${taskId}">
                           <input type="hidden" id="pid" name="pid" value="">
                           <input type="hidden" id="titleNo" name="titleno" value="">
                        </header>
                        <div class="panel-body">
                           <span class="form-horizontal tasi-form">
                              <div class="form-group">
                                 <label class="col-sm-2  control-label" style="text-align: right;">变更前名称</label>
                                 <div class="col-sm-4">
                                    <input type="text" class="form-control" id="CompanyName_Old" name="companynameOld" readonly="readonly">
                                 </div>
                                 <label class="col-sm-2  control-label" style="text-align: right;">变更后名称</label>
                                 <div class="col-sm-4">
                                    <input type="text" id="CompanyName_New" name="companynameNew" class="form-control validate[custom[username]]" readonly="readonly">
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
                           <span class="label label-primary">企业地址和邮编</span>
                           <span class="tools pull-right" style="margin-top:-8px;">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
                           <span class="form-horizontal tasi-form">
                              <div class="form-group">
                                 <label class="col-sm-2 control-label" style="text-align: right;">变更前地址</label>
                                 <div class="col-sm-6">
                                    <input type="text" class="form-control" value="变更前企业地址" readonly="readonly" id="AddressOld" name="addressOld">
                                 </div>
								 
                              </div>
                              <div class="form-group">
                                 <label class="col-sm-2 control-label" style="text-align: right;">变更后地址</label>
                                 <div class="col-sm-6">
                                    <input type="text" class="form-control" id="Address_New" name="addressNew"  readonly="readonly">
                                 </div>
                              </div>
                              <div class="form-group">
                              <label class="col-sm-2 control-label" style="text-align: right;">变更前邮编</label>
                                 <div class="col-sm-3">
                                    <input type="text" class="form-control" value="变更前企业邮编" readonly="readonly" id="PostCodeold" name="postcodeOld">
                                 </div>
                                <label class="col-sm-2 control-label" style="text-align: right;">变更后邮编</label>
                                 <div class="col-sm-3">
                                    <input type="text" class="form-control validate[custom[chinaZip]]" id="PostCode_new" name="postcodeNew" readonly="readonly">
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
                           <span class="label label-primary">企业法人和联系人</span>
                           <span class="tools pull-right" style="margin-top:-8px;">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
                        <span class="form-horizontal tasi-form">
                          <div class="form-group">
					 		<label class="col-sm-2 control-label" style="text-align:right">变更前法人</label>
                              <div class="col-sm-3">
                              <input type="text" class="form-control" id="Coporation_old" name="coporationOld" readonly="readonly">
                              </div>
                              <label class="col-sm-2 control-label" style="text-align:right">变更后法人</label>
                              <div class="col-sm-3">
                              <input type="text" class="form-control validate[custom[username]]" id="Coporation_New" name="coporationNew"  readonly="readonly">
                              </div>
                              </div>
							<div class="form-group">                           
					 		<label class="col-sm-2 control-label" style="text-align:right">变更前法人电话</label>
                              <div class="col-sm-3">
                              <input type="text" class="form-control" id="CoporationTel_old" name="coporationtelOld" readonly="readonly">
                              </div>
                              <label class="col-sm-2 control-label" style="text-align:right">变更后法人电话</label>
                              <div class="col-sm-3">
                              <input type="text" class="form-control validate[custom[telephone]]" id="CoporationTel_New" name="coporationtelNew" readonly="readonly">
                              </div>
                            </div>
                            <div class="form-group">
                             <label class="col-sm-2 control-label" style="text-align:right">变更前联系人</label>
                                <div class="col-sm-3">
                              <input type="text" class="form-control" id="Linkman_Old" name="linkmanOld" readonly="readonly">
                              </div>
                              <label class="col-sm-2 control-label validate[custom[username]]" style="text-align:right">变更后联系人</label>
                              <div class="col-sm-3">
                              <input type="text" class="form-control" id="Linkman_New" name="linkmanNew"  readonly="readonly">
                              </div>
                              
                              </div>
                           <div class="form-group">
					 		<label class="col-sm-2 control-label" style="text-align:right">变更前联系人电话</label>
                              <div class="col-sm-3">
                              <input type="text" class="form-control" id="LinkmanTel_old" name="linkmantelOld" readonly="readonly">
                              </div>
                              <label class="col-sm-2 control-label" style="text-align:right">变更后联系人电话</label>
                              <div class="col-sm-3">
                              <input type="text" class="form-control validate[custom[telephone]]" id="LinkmanTel_new" name="linkmantelNew"  readonly="readonly">
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
                                 <label class="col-sm-1" style="text-align:right;">变更前：</label>
								<div class="col-sm-5">
									<input type="checkbox" class="validate[minCheckbox[1]]" name="grp1" id="ys1" disabled="disabled">平板胶印
									<input type="checkbox" class="validate[minCheckbox[1]]" name="grp1" id="ys2" disabled="disabled" style="margin-left:30px">凹版印刷
									<input type="checkbox" class="validate[minCheckbox[1]]" name="grp1" id="ys3" disabled="disabled" style="margin-left:30px">丝网印刷
									<input type="checkbox" class="validate[minCheckbox[1]]" name="grp1" id="ys4" disabled="disabled" style="margin-left:30px">柔性版印刷
								</div>
								<label class="col-sm-1">其他：</label>
                                <div class="col-sm-5">
                                	<input type="text" id="yso" class="form-control" readonly="readonly">
                                </div>
                              </div>
                              <div class="form-group">
                                  <label class="col-sm-1" style="text-align:right;">变更后：</label>
									<div class="col-sm-5">
										<input type="checkbox" class="validate[minCheckbox[1]]" name="grp2" id="js1" disabled="disabled">平板胶印
										<input type="checkbox" class="validate[minCheckbox[1]]" name="grp2" id="js2" disabled="disabled" style="margin-left:30px">凹版印刷
										<input type="checkbox" class="validate[minCheckbox[1]]" name="grp2" id="js3" disabled="disabled" style="margin-left:30px">丝网印刷
										<input type="checkbox" class="validate[minCheckbox[1]]" name="grp2" id="js4" disabled="disabled" style="margin-left:30px">柔性版印刷
									</div>
									<label class="col-sm-1">其他：</label>
                                    <div class="col-sm-5">
                                    	<input type="text" id="jslx" class="form-control"  readonly="readonly">
                                    	<input type="text" id="PrintType_New" name="printtypeNew" style="display:none;">
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
                           <span class="label label-primary">条码载体材料</span>
                           <span class="tools pull-right" style="margin-top:-8px;">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
                           <span class="form-horizontal tasi-form">
                              <div class="form-group">
                                 <label class="col-sm-1" style="text-align:right;">变更前：</label>
								<div class="col-sm-5">
									<input type="checkbox" id="bcl1" disabled="disabled">纸质
									<input type="checkbox" id="bcl2" disabled="disabled" style="margin-left:30px">不干胶
									<input type="checkbox" id="bcl3" disabled="disabled" style="margin-left:30px">瓦楞纸
									<input type="checkbox" id="bcl4" disabled="disabled" style="margin-left:30px">金属
									<input type="checkbox" id="bcl5" disabled="disabled" style="margin-left:30px">塑料
								</div>
								<label class="col-sm-1">其他：</label>
                                <div class="col-sm-5">
                                	<input type="text" id="bclo" class="form-control" readonly="readonly">
                                </div>
                              </div>
                              <div class="form-group">
                                  <label class="col-sm-1" style="text-align:right;">变更后：</label>
									<div class="col-sm-5">
										<input type="checkbox" id="cl1" disabled="disabled">纸质
										<input type="checkbox" id="cl2" disabled="disabled" style="margin-left:30px">不干胶
										<input type="checkbox" id="cl3" disabled="disabled" style="margin-left:30px">瓦楞纸
										<input type="checkbox" id="cl4" disabled="disabled" style="margin-left:30px">金属
										<input type="checkbox" id="cl5" disabled="disabled" style="margin-left:30px">塑料
									</div>
									<label class="col-sm-1">其他：</label>
                                    <div class="col-sm-5">
                                    	<input type="text" id="ztcl" class="form-control" readonly="readonly">
                                    	<input type="text" id="Material_New" name="materialNew"  style="display:none;">
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
                           <span class="label label-primary">有效日期</span>
                           <span class="tools pull-right" style="margin-top:-8px;">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
                         <span class="form-horizontal tasi-form">
                              <div class="form-group">
                                 <label class="col-sm-2  control-label" style="text-align: right;">原发证日期</label>
								 <div class="col-sm-3">
                        		   <input type="text" class="form-control" readonly="readonly" id="CertAppDate_Old" name="certappdateOld">
                      		     </div>
                      		     <label class="col-sm-2  control-label" style="text-align: right;">现发证日期</label>
								 <div class="col-sm-3">
                        		   <input class="input-medium default-date-picker form-control" id="fzrq" name="certappdateNew" size="16" type="text" disabled="disabled">
                      		     </div>
                      		     </div>
                      		     <div class="form-group">
                      		     <label class="col-sm-2 control-label" style="text-align: right;">原到期日期</label>
								 <div class="col-sm-3">
                        		   <input type="text" class="form-control" size="16" readonly="readonly" id="CertToDate_Old" name="certtodateOld">
                      		     </div>
                      		     <label class="col-sm-2 control-label" style="text-align: right;">现到期日期</label>
								 <div class="col-sm-3">
                        		   <input class="input-medium default-date-picker form-control" id="dqrq" name="certtodateNew" size="16" type="text" disabled="disabled">
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
                           <span class="label label-primary">变更原因</span>
                           <span class="tools pull-right" style="margin-top:-8px;">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
                           <span class="form-horizontal tasi-form">
						   <div class="form-group">
                             <div class="col-lg-1"></div>
                             <div class="col-lg-10">
                                  <textarea class="form-control" rows="2" id="UpdateCause" name="updatecause"></textarea>
                             </div>  
                           </div> 						 
                           </span>
                        </div>
                     </section>
                  </div>
               </div>
               </form>
               <div class="row">
               <section class="panel">
               <div class="panel-body text-center">
               		<div class="btn-group">
               			<input type="button" onclick="saveForm()" class="btn-info  btn" value="保存"></div>
               		<div class="btn-group">
               			<input type="button" id="nextbtn1" onclick="nextStep(1)" class="btn-success  btn disabled" value="下一步"></div>
               		
               </div>
               </section>
               </div>
             </section></section>
             <div style="display: none;" id="load1">
				<section class="wrapper">
				<div class="row">
                  <div class="">
                     <section class="panel">
                        <div class="panel-body">
                           <span class="form-horizontal tasi-form">
                              <div class="form-group text-center">
                              	<b>第一步：请下载申请表；第二步：请上传申请表（盖章版）；第三步：请上传其他资料</b>
                              </div>
                              <div class="form-group">
                                 <div class="col-sm-6 text-right">申请表：</div>
                                 <div class="col-sm-2">
                                   <button class="btn btn-info" onclick="downfunc()">下载</button>
                                 </div> 
                                 <div class="col-sm-4"></div>
                              </div>
                             <form id="forms" method="post" target="nm_iframe" action="${ctx}/alternation/startApply.action" enctype="multipart/form-data">
                              <div class="form-group">
                                 <div class="col-sm-6 text-right">申请表（盖章版）<b class="red">*</b>：</div>
                                 <div class="col-sm-4">
                                 	<button onclick="sq.click()" class="btn btn-info" >选择文件</button>
									<input type="file" name="file" id="sq"  class="change validate[required]">
									<input type="hidden" name="taskId" value="${taskId}">
									<input type="hidden" name="altId" value="${id}">
									<input type="hidden" id="titleNo2" name="titleNo" value="">
									<b id="em" style="padding: 7px;margin: 0">${path4}</b>
					              <div class="modal-body hidden">
					                <table border="0" cellspacing="100px" align="center" style="height: 70px;width:100%">
										<tr id="trs"><td style="height: 10%;" align="center">
										
										</td></tr> 
									</table>
						         </div>
                                 </div> 
                                 <div class="col-sm-2"></div>
                              </div>
                              <div class="form-group">
                              	 <div class="col-sm-6 text-right">其他材料：</div>
                              	 <div class="col-sm-4">
                             		<button data-toggle='modal' onclick="uploadFiles()" href='#Modal_show' class="btn btn-info">
                            	 		批量上传 <i class="fa fa-file"></i>
                              		</button>
                              	 </div>
                           </div>	
                               <div class="form-group text-center">
 			               		<div class="btn-group"> 
 			               		<button class="btn btn-success" onclick="laststep()">
					                 	上一步
					                </button></div>
					            <div class="btn-group"> 
 			               		<!-- <button id="okbtn" class="btn btn-success" onclick="upfunc()"> -->
 			               		<button class="btn btn-success" onclick="upfunc()">
					                 	确定
					                </button></div> 
 			               		</div>
 			               		</form>				
                           </span>
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
    <!-- END JS -->
    <script type="text/javascript">
    jQuery(document).ready(function() {
        $("#changeForm").validationEngine({
            scrollOffset: 98,//必须设置，因为Toolbar position为Fixed
            promptPosition: 'topLeft',
            autoHidePrompt: true,
        });
    });
    function saveForm(){
    	if($('#changeForm').validationEngine('validate')){
	    	var ct="";
	    	if($("#cb1").attr("checked")){
	    		ct+="企业名称,"
	    	}
	    	if($("#cb2").attr("checked")){
	    		ct+="企业地址,"
	    	}
	    	if($("#cb3").attr("checked")){
	    		ct+="企业邮编,"
	    	}
	    	if($("#cb4").attr("checked")){
	    		ct+="企业法人,"
	    	}
	    	if($("#cb5").attr("checked")){
	    		ct+="法人电话,"
	    	}
	    	if($("#cb6").attr("checked")){
	    		ct+="联系人,"
	    	}
	    	if($("#cb7").attr("checked")){
	    		ct+="联系人电话,"
	    	}
	    	if($("#cb8").attr("checked")){
	    		ct+="条码印刷技术类型,"
	    	}
	    	if($("#cb9").attr("checked")){
	    		ct+="条码载体材料,"
	    	}
	    	if($("#cb10").attr("checked")){
	    		ct+="发证日期,"
	    	}
	    	if($("#cb11").attr("checked")){
	    		ct+="到期日期,"
	    	}
	    	if(ct!=""){
	    		
	    	 $("#changeType").val(ct.substring(0,ct.length-1));
	    	$("body").mLoading("show");
	    	 var oldName=$("#CompanyName_Old").val();
	 		 var newName=$("#CompanyName_New").val();
	 		 if(newName!=""){
		 			$.post(
			 				"${ctx}/application/ifApply.action",
			 				"enterpriseName="+newName,
			 				function(data){
			 					if(data>=1){
			 						if(oldName==newName){
			 							//如果修改后的企业名称跟之前的一样
			 							$("#changeForm").ajaxSubmit({
			 	  				    		type:'post',
			 	  				    		url:'${ctx}/alternation/saveFormChange.action',
			 	  				    		success:function(data){
			 	  				    			$("body").mLoading("hide");
			 	  				    			wzj.alert('温馨提醒', '保存成功，请进行下一步！')
			 	  				    			$('#nextbtn1').removeClass('disabled')
			 	  				    		}
			 	  				    	});
			 						}else{
			 							//修改后的企业名称跟之前的不一样
//		 	 							$.post(
//		 	 		   			   				"${ctx}/common/getComId.action",
//		 	 		   			   				"enterpriseName="+newName,
//		 	 		   			   				function(obj){
//		 	 		   			   					if(obj==$("#comId").val()){
//		 	 		   			   				$("#changeForm").ajaxSubmit({
//		 	 		    				    		type:'post',
//		 	 		    				    		url:'${ctx}/alternation/saveFormChange.action',
//		 	 		    				    		success:function(data){
//		 	 		    				    			$("body").mLoading("hide");
//		 	 		    				    			wzj.alert('温馨提醒', '保存成功，请进行下一步！')
//		 	 		    				    			$('#nextbtn1').removeClass('disabled')
//		 	 		    				    		}
//		 	 		    				    	});
//		 	 		   			   					 }else{
			 		   			   				$("body").mLoading("hide");
			 		   			   				wzj.alert('温馨提醒', '该企业已进行申请！')
//		 	 		   			   					} 
//		 	 		   			   				})
			 						}
			 					}else{
			 						$("#changeForm").ajaxSubmit({
			 				    		type:'post',
			 				    		url:'${ctx}/alternation/saveFormChange.action',
			 				    		success:function(data){
			 				    			$("body").mLoading("hide");
			 				    			wzj.alert('温馨提醒', '保存成功，请进行下一步！')
			 				    			$('#nextbtn1').removeClass('disabled')
			 				    		}
			 				    	});
			 				    	
			 					}
			 				}); 
	 		 }else{
	 			 //如果不修改企业名称
	 			$("#changeForm").ajaxSubmit({
			    		type:'post',
			    		url:'${ctx}/alternation/saveFormChange.action',
			    		success:function(data){
			    			$("body").mLoading("hide");
			    			wzj.alert('温馨提醒', '保存成功，请进行下一步！')
			    			$('#nextbtn1').removeClass('disabled')
			    		}
			    	}); 
	 		 }

	    	}else{
	    		wzj.alert("温馨提示","请选择变更类型");
	    	}
    	}
    }
    function updateForm(){
    	$("#changeForm").attr("action","${ctx}/alternation/updateFormChange.action");
    	$("#changeForm").submit();
    }
    function nextStep(num){
		$('#changeForm').hide()
		$('#load1').hide()
		$('#load2').hide()
		$('#load'+num).show()
		$('#bar li:eq('+num+')').removeClass('disabled')
		$('#bar li:eq('+num+')').addClass('active').siblings().removeClass('active').addClass('disabled')
// 		saveForm();
	} 
    function laststep(){
    	$('#changeForm').hide()
		$('#load1').hide()
		$('#load2').hide()
		$('#changeForm').show()
		$('#bar li:eq(0)').addClass('active').siblings().removeClass('active').addClass('disabled')
	}
    function downfunc(){
		var titleNo=$("#titleNo").val();
		 window.open('${ctx}/common/downPdf.action?type=3&titleNo='+titleNo);
		if(titleNo!=null||titleNo!=""){
			wzj.alert('温馨提醒', '下载成功！')
		}
	}
    function upfunc(){
    	var file=$('#em').text();
    	if(file.split(".")[1]=="pdf"||file.split(".")[1]=="PDF"){
    		$("body").mLoading("show");
//     		$('#forms').ajaxSubmit({  
//                 type:'post',  
//                 url: '${ctx}/alternation/startApply.action',  
//                 success:function(data){ 
//                 	if(data==1){

//                 	    $("body").mLoading("hide");
//                 		wzj.alert('温馨提醒', '上传成功！')
//                     	setTimeout(first,5000);
//                 	}
//                	}  
//             });
    		$("#forms").submit();
			$("body").mLoading("hide");
			wzj.alert('温馨提醒', '上传成功！')
			$('#nextbtn3').removeClass('disabled')
			setTimeout(first,5000);
		}else{
			wzj.alert('温馨提醒', '请上传pdf格式文件！')
		    
		}
    }
    function first(){
    	location.href="${ctx}/alternation/toNewForms.action"
	}
	 $('#sq').on('change', function( e ){
         //e.currentTarget.files 是一个数组，如果支持多个文件，则需要遍历
         var name = e.currentTarget.files[0].name;
         $('#em').text(name);
  	});

     $.validationEngineLanguage.allRules.username = {  
     	      "regex": /^[\w\u4E00-\u9FA5\(\)\（\）]+$/,
     	      "alertText": "* 请输入正确的格式"  
   	 };  
	 $.validationEngineLanguage.allRules.telephone = {  
	   	      "regex":/^(0[0-9]{2})-\d{7,8}$|^(0[0-9]{3}-(\d{7,8}))$|^\d{8}$|^\d{7}$|^1[3|4|5|7|8][0-9]{9}$/,  
	   	      "alertText": "* 请输入正确的电话号码"  
	 };
    </script>
</body>
</html>