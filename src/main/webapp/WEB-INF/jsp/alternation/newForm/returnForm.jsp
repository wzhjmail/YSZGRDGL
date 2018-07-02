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
    <link href="${ctx}/css/bootstrap-reset.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="${ctx}/assets/font-awesome/css/font-awesome.css" rel="stylesheet"><!-- FONT AWESOME ICON STYLESHEET -->
    <link href="${ctx}/assets/data-tables/DT_bootstrap.css" rel="stylesheet" ><!-- DATATABLE CSS -->
    <link href="${ctx}/css/style.css" rel="stylesheet"><!-- THEME BASIC CSS -->
    <link href="${ctx}/css/style-responsive.css" rel="stylesheet"><!-- THEME BASIC RESPONSIVE  CSS -->
	<link href="${ctx}/css/style_self.css" rel="stylesheet"><!-- 右键特效的css -->
	<link rel="stylesheet" href="${ctx}/css/validationEngine.jquery.css">
	<script src="${ctx}/js/jquery-1.8.3.min.js"></script><!-- BASIC JS LIABRARY 1.8.3 -->
	<script src="${ctx}/js/style_self.js"></script><!-- 右键特效的js -->
	<script src="${ctx}/assets/bootstrap-datepicker/js/bootstrap-datepicker.js"></script><!-- 日期显示 -->
	<script src="${ctx}/js/jquery.validationEngine-zh_CN.js"></script>
	<script src="${ctx}/js/jquery.validationEngine.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctx}/assets/bootstrap-datepicker/css/datepicker.css"><!-- BOOTSTRAP DATEPICKER PLUGIN CSS -->
<script type="text/javascript">
	$(function(){
		$('.labels').css('text-align','right')
		$('#fzrq').datepicker({
			format:'yyyy-mm-dd',
		});
		$('#dqrq').datepicker({
			format:'yyyy-mm-dd',
		});
	})
	function check_state(ckb,check_name){
		switch(check_name){
		case "企业名称":
			if(ckb.checked){$("#CompanyName_New").removeAttr("readonly");$("#CompanyName_New").addClass("validate[required]");}
			else{$("#CompanyName_New").attr("readonly","readonly");$("#CompanyName_New").removeClass("validate[required]");}
			break;
		case "企业地址":
			if(ckb.checked){$("#Address_New").removeAttr("readonly");$("#Address_New").addClass("validate[required]");}
			else{$("#Address_New").attr("readonly","readonly");$("#Address_New").removeClass("validate[required]");}
			break;
		case "企业邮编":
			if(ckb.checked){$("#PostCode_new").removeAttr("readonly");$("#PostCode_new").addClass("validate[required]");}
			else{$("#PostCode_new").attr("readonly","readonly");$("#PostCode_new").removeClass("validate[required]");}
			break;
		case "企业法人":
			if(ckb.checked){$("#Coporation_New").removeAttr("readonly");$("#Coporation_New").addClass("validate[required]");}
			else{$("#Coporation_New").attr("readonly","readonly");$("#Coporation_New").removeClass("validate[required]");}
			break;
		case "法人电话":
			if(ckb.checked){$("#CoporationTel_New").removeAttr("readonly");$("#CoporationTel_New").addClass("validate[required]");}
			else{$("#CoporationTel_New").attr("readonly","readonly");$("#CoporationTel_New").removeClass("validate[required]");}
			break;
		case "联系人":
			if(ckb.checked){$("#Linkman_New").removeAttr("readonly");$("#Linkman_New").addClass("validate[required]");}
			else{$("#Linkman_New").attr("readonly","readonly");$("#Linkman_New").removeClass("validate[required]");}
			break;
		case "联系人电话":
			if(ckb.checked){$("#LinkmanTel_new").removeAttr("readonly");$("#LinkmanTel_new").addClass("validate[required]");}
			else{$("#LinkmanTel_new").attr("readonly","readonly");$("#LinkmanTel_new").removeClass("validate[required]");}
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
			if(ckb.checked){$("#fzrq").removeAttr("disabled");$("#fzrq").addClass("validate[required]");}
			else{$("#fzrq").attr("disabled","disabled");$("#fzrq").removeClass("validate[required]");}
			break;
		case "到期日期":
			if(ckb.checked){$("#dqrq").removeAttr("disabled");$("#dqrq").addClass("validate[required]");}
			else{$("#dqrq").attr("disabled","disabled");$("#dqrq").removeClass("validate[required]");}
			break;
		}
	}
	$(function(){
		var id=$("#id").val();
		if(id!=null){//如果id存在
			param="id="+id;
			$.ajaxSetup({async : false });
			$.post(
				"${ctx}/application/getApp.action",
				param,
				function(data){
				$("#CompanyName_Old").val(data.enterprisename);
				$("#Address_Old").val(data.address);
				$("#Linkman_Old").val(data.linkman);
				$("#CertAppDate_Old").val(DateHandle(data.certappdate));
				$("#CertToDate_Old").val(DateHandle(data.certtodate));
				$("#Coporation_old").val(data.artificialperson);
				$("#LinkmanTel_old").val(data.ltel);
				$("#PostCode_old").val(data.postalcode);
				$("#CoporationTel_old").val(data.aptel);
				$("#LinkmanTel_old").val(data.ltel);
				//印刷类型
				if(data.flat==1){
					$("#ys1").attr("checked",true);
					$("#PrintType_Old").val("平板胶印,");
				}
				if(data.gravure==1){
					$("#ys2").attr("checked",true);
					$("#PrintType_Old").val($("#PrintType_Old").val()+"凹版印刷,");
				}
				if(data.webby==1){
					$("#ys3").attr("checked",true);
					$("#PrintType_Old").val($("#PrintType_Old").val()+"丝网印刷,");
				}
				if(data.flexible==1){
					$("#ys4").attr("checked",true);
					$("#PrintType_Old").val($("#PrintType_Old").val()+"柔性版印刷,");
				}
				if(data.elsetype!=null&&data.elsetype!=""){
					$("#yso").val(data.elsetype);
					$("#PrintType_Old").val($("#PrintType_Old").val()+data.elsetype);
				}
				
				//印刷材料
				if(data.papery==1){
					$("#bcl1").attr("checked",true);
					$("#Material_Old").val("资质,");
				}
				if(data.pastern==1){
					$("#bcl2").attr("checked",true);
					$("#Material_Old").val($("#Material_Old").val()+"不干胶,");
				}
				if(data.frilling==1){
					$("#bcl3").attr("checked",true);
					$("#Material_Old").val($("#Material_Old").val()+"瓦楞纸,");
				}
				if(data.metal==1){
					$("#bcl4").attr("checked",true);
					$("#Material_Old").val($("#Material_Old").val()+"金属,");
				}
				if(data.plastic==1){
					$("#bcl5").attr("checked",true);
					$("#Material_Old").val($("#Material_Old").val()+"塑料,");
				}
				if(data.elsematerial!=null&&data.elsematerial!=""){
					$("#bclo").val(data.elsematerial);
					$("#Material_Old").val($("#Material_Old").val()+data.elsematerial);
				}
			});
		}
	});
function check(){
	//条码印刷类型
	var result="";
	if($("#js1").attr("chedked")){
		result+="平板胶印,";
	}
	if($("#js2").attr("chedked")){
		result+="凹版印刷,"
	}
	if($("#js3").attr("chedked")){
		result+="丝网印刷,"
	}
	if($("#js4").attr("chedked")){
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
function DateHandle(obj) {  
    var objDate=new Date(obj); //创建一个日期对象表示当前时间     
    var year=objDate.getFullYear();   //四位数字年     
    var month=objDate.getMonth()+1;   //getMonth()返回的月份是从0开始的，还要加1     
    var date=objDate.getDate();     
    var date = year+"-"+month+"-"+date;  
    return date;   
} 
</script>
</head>
<body>
			    <form id="appForm" action="${ctx}/alternation/insert.action" method="post" onsubmit="check();">
               <section class="panel">
                  <section class="wrapper">
			  <center style="margin-top:-70px;color:#2a8ba7"><h1>变更申请表</h1></center>
			 <!--  <div class="Noprint">
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
                           <span class="label label-primary">需要变更的内容</span>
                        </header>
                        <div class="panel-body" style="text-align:center">  
                   		    <input type="checkbox" class="validate[minCheckbox[1]]" name="grp" id="cb1" style="margin-left:10px" onclick="check_state(this,'企业名称')">企业名称
                            <input type="checkbox" class="validate[minCheckbox[1]]" name="grp" id="cb2" style="margin-left:10px" onclick="check_state(this,'企业地址')">企业地址
                            <input type="checkbox" class="validate[minCheckbox[1]]" name="grp" id="cb3" style="margin-left:10px" onclick="check_state(this,'企业邮编')">企业邮编
                            <input type="checkbox" class="validate[minCheckbox[1]]" name="grp" id="cb4" style="margin-left:10px" onclick="check_state(this,'企业法人')">企业法人
                            <input type="checkbox" class="validate[minCheckbox[1]]" name="grp" id="cb5" style="margin-left:10px" onclick="check_state(this,'法人电话')">法人电话
                            <input type="checkbox" class="validate[minCheckbox[1]]" name="grp" id="cb6" style="margin-left:10px" onclick="check_state(this,'联系人')">联系人
                            <input type="checkbox" class="validate[minCheckbox[1]]" name="grp" id="cb7" style="margin-left:10px" onclick="check_state(this,'联系人电话')">联系人电话
                            <input type="checkbox" class="validate[minCheckbox[1]]" name="grp" id="cb8" style="margin-left:10px" onclick="check_state(this,'条码印刷技术类型')">条码印刷技术类型
                            <input type="checkbox" class="validate[minCheckbox[1]]" name="grp" id="cb9" style="margin-left:10px" onclick="check_state(this,'条码载体材料')">条码载体材料
                            <input type="checkbox" class="validate[minCheckbox[1]]" name="grp" id="cb10" style="margin-left:10px;" onclick="check_state(this,'发证日期')">发证日期
                            <input type="checkbox" class="validate[minCheckbox[1]]" name="grp" id="cb11" style="margin-left:10px;" onclick="check_state(this,'到期日期')">到期日期                                                                              
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
                           <input type="hidden" id="id" name="pid" value="${id}">
                           <input type="hidden" id="taskId" value="${taskId}">
                        </header>
                        <div class="panel-body">
                           <span class="form-horizontal tasi-form">
                              <div class="form-group">
                                 <label class="col-sm-2 control-label labels">变更前</label>
                                 <div class="col-sm-4">
                                    <input type="text" class="form-control" id="CompanyName_Old" name="companynameOld" readonly="readonly">
                                 </div>
                                 <label class="col-sm-2 control-label labels">变更后</label>
                                <div class="col-sm-4">
                                    <input type="text" id="CompanyName_New" name="companynameNew" class="form-control" readonly="readonly">
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
                                 <label class="col-sm-2 control-label labels">变更前地址</label>
                                 <div class="col-sm-6">
                                    <input type="text" class="form-control" value="变更前企业地址" readonly="readonly" id="AddressOld" name="addressOld">
                                 </div>
								 <label class="col-sm-2 control-label labels">变更前邮编</label>
                                 <div class="col-sm-2">
                                    <input type="text" class="form-control" value="变更前企业邮编" readonly="readonly" id="PostCodeold" name="postcodeOld">
                                 </div>
                              </div>
                              <div class="form-group">
                                 <label class="col-sm-2 control-label labels">变更后地址</label>
                                 <div class="col-sm-6">
                                    <input type="text" class="form-control" id="Address_New" name="addressNew" value="" readonly="readonly">
                                 </div>
								 <label class="col-sm-2 control-label labels">变更后邮编</label>
                                 <div class="col-sm-2">
                                    <input type="text" class="form-control" id="PostCode_new" name="postcodeNew" value="" readonly="readonly">
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
					 		<label class="col-sm-2 control-label labels" style="text-align:right">变更前法人</label>
                              <div class="col-sm-4">
                              <input type="text" class="form-control" id="Coporation_old" name="coporationOld" value="略语" readonly="readonly">
                              </div>
                              <label class="col-sm-2 control-label labels" style="text-align:right">变更前法人电话</label>
                              <div class="col-sm-4">
                              <input type="text" class="form-control" id="CoporationTel_old" name="coporationtelOld" value="12345" readonly="readonly">
                              </div>
                              </div>
                           	<div class="form-group">
					 		<label class="col-sm-2 control-label labels" style="text-align:right">变更后法人</label>
                              <div class="col-sm-4">
                              <input type="text" class="form-control" id="Coporation_New" name="coporationNew" value="" readonly="readonly">
                              </div>
                              <label class="col-sm-2 control-label labels" style="text-align:right">变更后法人电话</label>
                              <div class="col-sm-4">
                              <input type="text" class="form-control" id="CoporationTel_New" name="coporationtelNew" value="" readonly="readonly">
                              </div>
                            </div>
                            <div class="form-group">
                             <label class="col-sm-2 control-label labels" style="text-align:right">变更前联系人</label>
                                <div class="col-sm-4">
                              <input type="text" class="form-control" id="Linkman_Old" name="linkmanOld" value="略语" readonly="readonly">
                              </div>
                              <label class="col-sm-2 control-label labels" style="text-align:right">变更前联系人电话</label>
                              <div class="col-sm-4">
                              <input type="text" class="form-control" id="LinkmanTel_old" name="linkmantelOld" value="12345" readonly="readonly">
                              </div>
                             </div>
                           	<div class="form-group">
					 		<label class="col-sm-2 control-label labels" style="text-align:right">变更后联系人</label>
                              <div class="col-sm-4">
                              <input type="text" class="form-control" id="Linkman_New" name="linkmanNew" value="" readonly="readonly">
                              </div>
                              <label class="col-sm-2 control-label labels" style="text-align:right">变更后联系人电话</label>
                              <div class="col-sm-4">
                              <input type="text" class="form-control" id="LinkmanTel_new" name="linkmantelNew" value="" readonly="readonly">
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
									<input type="checkbox" id="ys1" disabled="disabled">平板胶印
									<input type="checkbox" id="ys2" disabled="disabled" style="margin-left:30px">凹版印刷
									<input type="checkbox" id="ys3" disabled="disabled" style="margin-left:30px">丝网印刷
									<input type="checkbox" id="ys4" disabled="disabled" style="margin-left:30px">柔性版印刷
								</div>
								<label class="col-sm-1">其他：</label>
                                <div class="col-sm-5">
                                	<input type="text" id="yso" class="form-control" readonly="readonly">
                                	<input type="text" id="PrintType_Old" name="printtypeOld" value="" style="display:none">
                                </div>
                              </div>
                              <div class="form-group">
                                  <label class="col-sm-1" style="text-align:right;">变更后：</label>
									<div class="col-sm-5">
										<input type="checkbox" id="js1" disabled="disabled">平板胶印
										<input type="checkbox" id="js2" disabled="disabled" style="margin-left:30px">凹版印刷
										<input type="checkbox" id="js3" disabled="disabled" style="margin-left:30px">丝网印刷
										<input type="checkbox" id="js4" disabled="disabled" style="margin-left:30px">柔性版印刷
									</div>
									<label class="col-sm-1">其他：</label>
                                    <div class="col-sm-5">
                                    	<input type="text" id="jslx" class="form-control" value="" readonly="readonly">
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
                                	<input type="text" id="Material_Old" name="materialOld" value="" style="display:none">
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
                                    	<input type="text" id="Material_New" name="materialNew" value="" style="display:none;">
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
                                 <label class="col-sm-2 control-label labels">原发证日期</label>
								 <div class="col-sm-4">
                        		   <input type="text" class="form-control" readonly="readonly" id="CertAppDate_Old" name="certappdateOld">
                      		     </div>
                      		     <label class="col-sm-2 control-label labels">现发证日期</label>
								 <div class="col-sm-4">
                        		   <input class="input-medium default-date-picker form-control validate[custom[date]]" id="fzrq" name="certappdateNew" size="16" type="text" disabled="disabled">
                      		     </div>
                      		  </div>
                      		  <div class="form-group">
                      		     <label class="col-sm-2 control-label labels">原到期日期</label>
								 <div class="col-sm-4">
                        		   <input type="text" value="2016-16-16" class="form-control" size="16" readonly="readonly" id="CertToDate_Old" name="certtodateOld">
                      		     </div>
                      		     <label class="col-sm-2 control-label labels">现到期日期</label>
								 <div class="col-sm-4">
                        		   <input class="input-medium default-date-picker form-control validate[custom[date]]" id="dqrq" name="certtodateNew" size="16" type="text" disabled="disabled">
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
               <div class="row">
               <section class="panel">
               <div class="panel-body text-center">
               		<div class="btn-group"><input type="button" onclick="history.back()" class="btn-info  btn" value="返回"></div>
               		<div class="btn-group "><input type="submit" class="btn-success  btn" value="提交"></div>
               </div>
               </section>
               </div>
             </section></section></form>
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
	    $("#appForm").validationEngine({
	        scrollOffset: 98,//必须设置，因为Toolbar position为Fixed
	        promptPosition: 'topLeft',
	        autoHidePrompt: true,
	    });
        function submitForm(){
        	if($('#appForm').validationEngine()){
	        	$("#appForm").submit();
        	}
        }
        $.validationEngineLanguage.allRules.date = {  
	    	      "regex": /^(19[0-9]{2}|20[0-9]{2})-(0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-9]|3[0-1])$/,
	    	      "alertText": "* 请输入正确的日期格式"  
	   	}; 
    </script>
</body>
</html>