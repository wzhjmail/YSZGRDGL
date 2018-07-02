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
	
<script src="${ctx}/js/jquery-1.8.3.min.js"></script><!-- BASIC JS LIABRARY 1.8.3 -->
<script src="${ctx}/js/style_self.js"></script><!-- 右键特效的js -->
<style type="text/css">
	#ascrail2000-hr{
		display: none;
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
		$('.labels').css('text-align','right')
		var id=$("#id").val();
		if(id!=null){//如果id存在
			param="id="+id;
			$.ajaxSetup({async : false });
			$.post(
				"${ctx}/alternation/getChange.action",
				param,
				function(data){
				$("#CompanyName_Old").val(data.companynameOld);
				$("#AddressOld").val(data.addressOld);
				$("#Linkman_Old").val(data.linkmanOld);
				$("#CertAppDate_Old").val(data.certappdateOld);
				$("#CertToDate_Old").val(data.certtodateOld);
				$("#Coporation_old").val(data.coporationOld);
				$("#LinkmanTel_old").val(data.linkmantelOld);
				$("#PostCodeold").val(data.postcodeOld);
				$("#CoporationTel_old").val(data.coporationtelOld);
				//现在
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
					$("#chType").append("<input type='checkbox' checked='true' style='margin-left:10px'>"+changeType[i]);
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
				$("#UpdateCause").val(data.updatecause);
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
</script>
</head>
<body>
               <section class="panel">
			   <!-- BEGIN ROW  -->
			   <div class="row">
                  <div class="col-lg-12">
                     <section class="panel">
                        <header class="panel-heading">
                           <span class="label label-primary">变更类型</span>
                        </header>
                        <div class="panel-body" style="text-align:left" id="chType">  
                        
                        </div>
                     </section>
                  </div>
              </div>
			  <div class="row">
                  <div class="col-lg-12">
                     <section class="panel">
                        <header class="panel-heading">
                           <span class="label label-primary">企业名称</span>
                           <span class="tools pull-right" style="margin-top:-8px;">
                           	<a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                           <input type="hidden" id="id" name="pid" value="${id}">
                        </header>
                        <div class="panel-body">
                           <span class="form-horizontal tasi-form">
                              <div class="form-group">
                                 <label class="col-sm-2 control-label  labels labels">变更前名称</label>
                                 <div class="col-sm-4">
                                    <input type="text" class="form-control" id="CompanyName_Old" name="companynameOld" readonly="readonly">
                                 </div>
                                 <label class="col-sm-2 control-label labels">变更后名称</label>
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
                              </div>
                              <div class="form-group">
                                 <label class="col-sm-2 control-label labels">变更后地址</label>
                                 <div class="col-sm-6">
                                    <input type="text" class="form-control" id="Address_New" name="addressNew" value="" readonly="readonly">
                                 </div>
								 
                              </div>
                               <div class="form-group">
	                              	 <label class="col-sm-2 control-label labels">变更前邮编</label>
	                                 <div class="col-sm-3">
	                                    <input type="text" class="form-control" value="变更前企业邮编" readonly="readonly" id="PostCodeold" name="postcodeOld">
	                                 </div>
	                                 <label class="col-sm-2 control-label labels">变更后邮编</label>
	                                 <div class="col-sm-3">
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
                              <div class="col-sm-3">
                              <input type="text" class="form-control" id="Coporation_old" name="coporationOld" value="略语" readonly="readonly">
                              </div>
                              <label class="col-sm-2 control-label labels" style="text-align:right">变更后法人</label>
                              <div class="col-sm-3">
                              <input type="text" class="form-control" id="Coporation_New" name="coporationNew" value="" readonly="readonly">
                              </div>
                              
                           </div>
                            <div class="form-group">
					 			<label class="col-sm-2 control-label labels" style="text-align:right">变更前法人电话</label>
                              <div class="col-sm-3">
                              <input type="text" class="form-control" id="CoporationTel_old" name="coporationtelOld" value="12345" readonly="readonly">
                              </div>
                              <label class="col-sm-2 control-label labels" style="text-align:right">变更后法人电话</label>
                              <div class="col-sm-3">
                              <input type="text" class="form-control" id="CoporationTel_New" name="coporationtelNew" value="" readonly="readonly">
                              </div>
                            </div>
                            <div class="form-group">
                             <label class="col-sm-2 control-label labels" style="text-align:right">变更前联系人</label>
                                <div class="col-sm-3">
                              <input type="text" class="form-control" id="Linkman_Old" name="linkmanOld" value="略语" readonly="readonly">
                              </div>
                              <label class="col-sm-2 control-label labels" style="text-align:right">变更后联系人</label>
                              <div class="col-sm-3">
                              <input type="text" class="form-control" id="Linkman_New" name="linkmanNew" value="" readonly="readonly">
                              </div>
                              </div>
                           
                            <div class="form-group">
					 			<label class="col-sm-2 control-label labels" style="text-align:right">变更前联系人电话</label>
                              <div class="col-sm-3">
                              <input type="text" class="form-control" id="LinkmanTel_old" name="linkmantelOld" value="12345" readonly="readonly">
                              </div>
                              <label class="col-sm-2 control-label labels" style="text-align:right">变更后联系人电话</label>
                              <div class="col-sm-3">
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
                                 <label class="col-sm-2 labels">变更前：</label>
								<div class="col-sm-5">
									<input type="checkbox" id="ys1" disabled="disabled">平板胶印
									<input type="checkbox" id="ys2" disabled="disabled" style="margin-left:30px">凹版印刷
									<input type="checkbox" id="ys3" disabled="disabled" style="margin-left:30px">丝网印刷
									<input type="checkbox" id="ys4" disabled="disabled" style="margin-left:30px">柔性版印刷
								</div>
								<label class="col-sm-1 labels">其他：</label>
                                <div class="col-sm-4">
                                	<input type="text" id="yso" class="form-control" readonly="readonly">
                                	<input type="text" id="PrintType_Old" name="printtypeOld" value="" style="display:none">
                                </div>
                              </div>
                              <div class="form-group">
                                  <label class="col-sm-2 labels">变更后：</label>
									<div class="col-sm-5">
										<input type="checkbox" id="js1" disabled="disabled">平板胶印
										<input type="checkbox" id="js2" disabled="disabled" style="margin-left:30px">凹版印刷
										<input type="checkbox" id="js3" disabled="disabled" style="margin-left:30px">丝网印刷
										<input type="checkbox" id="js4" disabled="disabled" style="margin-left:30px">柔性版印刷
									</div>
									<label class="col-sm-1 labels">其他：</label>
                                    <div class="col-sm-4 ">
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
                                 <label class="col-sm-2 labels">变更前：</label>
								<div class="col-sm-5">
									<input type="checkbox" id="bcl1" disabled="disabled">纸质
									<input type="checkbox" id="bcl2" disabled="disabled" style="margin-left:30px">不干胶
									<input type="checkbox" id="bcl3" disabled="disabled" style="margin-left:30px">瓦楞纸
									<input type="checkbox" id="bcl4" disabled="disabled" style="margin-left:30px">金属
									<input type="checkbox" id="bcl5" disabled="disabled" style="margin-left:30px">塑料
								</div>
								<label class="col-sm-1 labels">其他：</label>
                                <div class="col-sm-4">
                                	<input type="text" id="bclo" class="form-control" readonly="readonly">
                                	<input type="text" id="Material_Old" name="materialOld" value="" style="display:none">
                                </div>
                              </div>
                              <div class="form-group">
                                  <label class="col-sm-2 labels">变更后：</label>
									<div class="col-sm-5">
										<input type="checkbox" id="cl1" disabled="disabled">纸质
										<input type="checkbox" id="cl2" disabled="disabled" style="margin-left:30px">不干胶
										<input type="checkbox" id="cl3" disabled="disabled" style="margin-left:30px">瓦楞纸
										<input type="checkbox" id="cl4" disabled="disabled" style="margin-left:30px">金属
										<input type="checkbox" id="cl5" disabled="disabled" style="margin-left:30px">塑料
									</div>
									<label class="col-sm-1 labels">其他：</label>
                                    <div class="col-sm-4">
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
								 <div class="col-sm-3">
                        		   <input type="text" class="form-control" readonly="readonly" id="CertAppDate_Old" name="certappdateOld">
                      		     </div>
                      		     <label class="col-sm-2 control-label labels">现发证日期</label>
								 <div class="col-sm-3">
                        		   <input class="input-medium default-date-picker form-control" id="fzrq" name="certappdateNew" size="16" type="text" disabled="disabled">
                      		     </div>
                      		     </div>
                      		     <div class="form-group">
                      		     <label class="col-sm-2 control-label labels">原到期日期</label>
								 <div class="col-sm-3">
                        		   <input type="text" value="2016-16-16" class="form-control" size="16" readonly="readonly" id="CertToDate_Old" name="certtodateOld">
                      		     </div>
                      		     <label class="col-sm-2 control-label labels">现到期日期</label>
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
                                  <textarea class="form-control" readonly="true" rows="2" id="UpdateCause" name="updatecause"></textarea>
                             </div>  
                           </div> 						 
                           </span>
                        </div>
                     </section>
                  </div>
               </div>
               <form id="subForm" action="${ctx}/alternation/firstTrial/trial.action" method="post">
                 <div class="row">
                  <div class="col-lg-12">
                     <section class="panel">
                        <header class="panel-heading">
                           <span class="label label-primary">业务受理</span>
                           <span class="tools pull-right" style="margin-top:-8px;">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                   
	               <section class="panel">
	               <div class="panel-body">
		               <div class="form-group">
	                     <div class="col-sm-2"></div>
	                     <div class="col-sm-2">
	                     	<input type="radio" name="result" value="true" onclick="clickbox(true)">通过
	                     </div>
	                     <div class="col-sm-2">
	                        <input type="radio" name="result" value="false" onclick="clickbox(false)">不通过
	                     </div>  
	                   </div>
	                   <br> 
	                   
	               		<input type="hidden" id="taskId" name="taskId" value="${taskId}">
                        <div class="form-group" id="isPass" style="display: none;">
                         	<div class="col-sm-2 text-right"><label>意见</label></div>
                          <div class="col-sm-8">
                          	<textarea rows="" cols="" name="comment" id="passComment2" class="form-control" ></textarea>
                          </div>
                        </div>
	               </div>
	               </section>
	               </section>
	               </div>
	               </div>
</section>
             	<div class="row">
	               <section class="panel">
	               <div class="panel-body text-center">
	               		<div class="btn-group">
	               		<input type="button" onclick="history.back()" class="btn-info  btn" value="返回"></div>
	               		<div class="btn-group">
	               		<!-- <input type="submit" class="btn-info  btn" value="提交"> -->
	               		<button class="btn-info  btn" id="okbtn">提交</button>
	               		</div>
	               </div>
	               </section>
	            </div>
	            </form>
             </section>
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
	<script type="text/javascript">
	    function clickbox(flag){
	    	$('#isPass').show()
	     }
	    $("#okbtn").click(function(e){
	    	e.preventDefault();
	    	var r1=$("input[name='result']").get(0).checked;
	    	var r2=$("input[name='result']").get(1).checked;
	    	if(r1||r2){
	    		$("#subForm").submit();
	    	}else{
	    		wzj.alert("温馨提示","请选择业务受理结果");
	    	}
	    })
	    
	</script>
    <!-- END JS -->
</body>
</html>