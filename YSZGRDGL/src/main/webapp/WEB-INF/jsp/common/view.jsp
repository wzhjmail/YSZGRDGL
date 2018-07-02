<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="myshiro" uri="/customize-tags" %>
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>申请表信息</title>
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
<script type="text/javascript" src="${ctx}/js/jquery.media.js"></script>
<style type="text/css">
		.change{
		    position: absolute;
		    overflow: hidden;
		    right: 0;
		    top: 0;
		    opacity: 0;
		}
</style>
<script type="text/javascript">
$(function(){
	$('.labels').css('text-align','right')
	getTask();
});
function getTestingEquip(companyName){
	if($("#companyId").val()!=""){
		$.post(
				"${ctx}/application/testingEquip/getByCompanyId.action",
				"companyId="+$("#companyId").val(),
				function(data){
					if(data.length!=0){
						$("#testing thead").show()
						$.each(data,function(i,item){
							$("#testing").append("<tr><td style='display:none'>"+item.id+"</td><td>"+
									item.name+"</td><td>"+item.count+"</td><td>"+(new Date(parseFloat(item.time))).format("yyyy-MM-dd")
									+"</td><td>"+item.remark+"</td></tr>");
						})
					}
				}
			);
	}else{
		$.post(
				"${ctx}/application/testingEquip/getByCompanyName.action",
				"companyName="+companyName,
				function(data){
					if(data.length!=0){
						$("#testing thead").show()
						$.each(data,function(i,item){
							$("#testing").append("<tr><td style='display:none'>"+item.id+"</td><td>"+
									item.name+"</td><td>"+item.count+"</td><td>"+(new Date(parseFloat(item.time))).format("yyyy-MM-dd")
									+"</td><td>"+item.remark+"</td></tr>");
						})
					}
				}
			);
	}
	
}
function getTask(){
	var id=$("#id").val();
	if(id!=null){
		param="id="+id;
		$.ajaxSetup({ async : false });
		$.post(
				"${ctx}/application/getApp.action",
				param,
				function(data){
					$("#aId").val(data.id);
					$("#titleNo").val(data.titleno);
					$("#enterprisename").val(data.enterprisename);
					getTestingEquip(data.enterprisename);
                    getPrintEquipment(data.enterprisename);
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
					if(true==data.flat){
						$("#flat").attr("checked","checked");
					}
					if(true==data.gravure){
						$("#gravure").attr("checked","checked");
					}
					if(true==data.webby){
						$("#webby").attr("checked","checked");
					}
					if(true==data.flexible){
						$("#flexible").attr("checked","checked");
					}
					if(true==data.papery){
						$("#papery").attr("checked","checked");
					}
					if(true==data.pastern){
						$("#pastern").attr("checked","checked");
					}
					if(true==data.frilling){
						$("#frilling").attr("checked","checked");
					}
					if(true==data.metal){
						$("#metal").attr("checked","checked");
					}
					if(true==data.plastic){
						$("#plastic").attr("checked","checked");
					}
					$("#elsetype").val(data.elsetype);
					$("#elsematerial").val(data.elsematerial);
					$("#offshootorganiz").val(data.offshootorganiz);
					$("#evaluatingresult").val(data.evaluatingresult);
					$("#certificateno").val(data.certificateno);
					$("#certappdate").val(data.certappdate);
					$("#printEquipment").val(data.printEquipment);
					$("#testEquipment").val(data.testEquipment);
					$("#remarks").val(data.remarks);
					$("#qualityno").val(data.qualityno);
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
function downfunc(type){
	var titleNo=$("#titleNo").val();
	$.ajaxSetup({async : false});
	 $.post(
		        "${ctx}/commons/fileExists.action",
		        'type='+type+'&titleNo='+titleNo,
		        function (data) {
		            if(data) {
		            	 window.open('${ctx}/commons/downloadFile.action?type='+type+'&titleNo='+titleNo);
		            }else{
		            	wzj.alert('温馨提示','下载失败！');
		            }
		        }
		    );
}
function viewfunc(type){
	var titleNo=$("#titleNo").val();
// 	if(type==1||type==2){
		window.open("../viewPic.jsp?flag=1&titleNo="+titleNo+"&type="+type);
// 	}else{
// 		window.open("../viewPic.jsp?flag=2&titleNo="+titleNo+"&type="+type);
// 	}
}
function getPrintEquipment(companyName) {
	if($("#companyId").val()!=""){
		 $.post(
			        "${ctx}/application/printEquipment/getByCompanyId.action",
			        "companyId="+$("#companyId").val(),
			        function (data) {
			            if(data != null) {
			                $("#print thead").show();
			                $.each(data, function (i, item) {
			                    $("#print").append("<tr><td style='display:none'>"+item.id+"</td>" +
			                        "<td>"+item.printName+"</td><td>"+item.printModel+"</td><td>"+item.printPlace+"</td>" +
			                        "<td>"+item.printNumber+"</td><td>"+item.printNotes+"</td></tr>")
			                });
			            }
			        }
			    );
	}else{
		 $.post(
			        "${ctx}/application/printEquipment/getByCompanyName.action",
			        "companyName="+companyName,
			        function (data) {
			            if(data != null) {
			                $("#print thead").show();
			                $.each(data, function (i, item) {
			                    $("#print").append("<tr><td style='display:none'>"+item.id+"</td>" +
			                        "<td>"+item.printName+"</td><td>"+item.printModel+"</td><td>"+item.printPlace+"</td>" +
			                        "<td>"+item.printNumber+"</td><td>"+item.printNotes+"</td></tr>")
			                });
			            }
			        }
			    );
	}
   
}
</script>
</head>
<body>
	    <!-- BEGIN HEADER --> 
		    <!-- BEGIN WRAPPER  -->
		    <section class="panel" style="margin-bottom:0px">
		    <div class="form-horizontal tasi-form">
                  <section class="wrapper">
               <center style="margin-top:-70px;color:#2a8ba7"><h1>查看申请表</h1></center>
			   <!-- BEGIN ROW  -->
			   <div class="row">
                  <div class="col-lg-12">
                     <section class="panel">
                        <header class="panel-heading">
                           <span class="label label-primary">企业信息</span>
                           <span class="tools pull-right" style="margin-top:-8px;">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                            <input type="hidden" id="id" value="${id}" class="form-control">
                            <input type="hidden" id="taskId" value="${taskId}" class="form-control">
                        	<input type="hidden" name="qualityno" id="qualityno" value="">
                        	<input type="hidden" name="titleNo" id="titleNo" value="">
                        	<input type="hidden" name="" id="companyId" value="${companyId}">
                        </header>
                        <div class="panel-body">
                           <span class="form-horizontal tasi-form">
                              <div class="form-group">
                                 <label class="col-sm-2 control-label labels">企业名称（中文）</label>
                                 <div class="col-sm-4">
                                 <input type="text" readonly="readonly"  id="enterprisename" name="enterprisename" value="" class="form-control">
                                 <!-- <input type="hidden"   id="titleno" name="titleno" value="" class="form-control"> -->
                                 <input type="hidden"   id="aId" name="id" value="" class="form-control">
                                 </div>
                                 <label class="col-sm-2 control-label labels">企业名称（英文）</label>
                                 <div class="col-sm-4">
                                    <input type="text" readonly="readonly"  name="englishname" id="englishname" value="" class="form-control">
                                 </div>
                              </div>
                              <div class="form-group">
                                 <label class="col-sm-2 control-label labels">详细地址</label>
                                 <div class="col-sm-10">
                                    <input type="text" readonly="readonly" id="address" name="address" value="" class="form-control">
                                 </div>
                              </div>
                              <div class="form-group">
                               	 <label class="col-sm-2 control-label labels">印刷经营许可证号码</label>
                                 <div class="col-sm-4">
                                    <input type="text" readonly="readonly" name="certificateno" id="certificateno" value="" class="form-control">
                                 </div>	
                                  <label class="col-sm-1 control-label labels nopaddingleft">邮政编码</label>
                                 <div class="col-sm-2">
                                    <input type="text" readonly="readonly" name="postalcode" id="postalcode" value="" class="form-control">
                                 </div>
                               	 <label class="col-sm-1 control-label labels">传真</label>
                                 <div class="col-sm-2">
                                    <input type="text" readonly="readonly" name="fax" id="fax" value="" class="form-control">
                                 </div>
                              </div>
							  <div class="form-group">
                                 <label class="col-sm-2 control-label labels">营业执照号码</label>
                                 <div class="col-sm-4">
                                    <input type="text" readonly="readonly" name="businessno" id="businessno" value="" class="form-control" required="true">
                                 </div>
								 <label class="col-sm-1 control-label labels nopaddingleft">企业性质</label>
                                 <div class="col-sm-2">
                                    <select name="enterprisekind" id="enterprisekind" class="form-control" disabled="disabled">
                                    	<option value="国有" >国有</option>
                                    	<option value="集体" >集体</option>
                                    	<option value="私营">私营</option>
                                    	<option value="有限责任公司" >有限责任公司</option>
                                    	<option value="股份有限公司" >股份有限公司</option>
                                    	<option value="股份合作" >股份合作</option>
                                    	<option value="联营企业" >联营企业</option>
                                    	<option value="中外合资合作企业（港澳台）" >中外合资合作企业（港澳台）</option>
                                    	<option value="外商独资投资企业（港澳台）" >外商独资投资企业（港澳台）</option>
                                    	<option value="其他企业" >其他企业</option>
                                    </select>
                                 </div>
								 <label class="col-sm-1 control-label labels nopaddingleft">注册资本</label>
                                 <div class="col-sm-2">
                                    <input type="text" readonly="readonly" name="registercapital" id="registercapital" valut="" class="form-control">
                                 </div>
                              </div>
							  <div class="form-group">
                                 <label class="col-sm-2 control-label labels">法人代表</label>
                                 <div class="col-sm-4">
                                    <input type="text" readonly="readonly" name="artificialperson" id="artificialperson" valut="" class="form-control">
                                 </div>
								 <label class="col-sm-1 control-label labels">职务</label>
                                 <div class="col-sm-2">
                                    <input type="text" readonly="readonly" name="apjob" id="apjob" valut="" class="form-control">
                                 </div>
								 <label class="col-sm-1 control-label labels">电话</label>
                                 <div class="col-sm-2">
                                    <input type="text" readonly="readonly" name="aptel" id="aptel" valut="" class="form-control">
                                 </div>
                              </div>
							  <div class="form-group">
                                 <label class="col-sm-2 control-label labels">联系人</label>
                                 <div class="col-sm-4">
                                    <input type="text" readonly="readonly" name="linkman" id="linkman" valut="" class="form-control">
                                 </div>
								 <label class="col-sm-1 control-label labels">职务</label>
                                 <div class="col-sm-2">
                                    <input type="text" readonly="readonly" name="ljob" id="ljob" valut="" class="form-control">
                                 </div>
								 <label class="col-sm-1 control-label labels">电话</label>
                                 <div class="col-sm-2">
                                    <input type="text" readonly="readonly" name="ltel" id="ltel" valut="" class="form-control">
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
                                 <label class="col-sm-1 control-label labels">主营</label>
                                 <div class="col-sm-11">
                                    <input type="text" readonly="readonly" name="mainbusiness" id="mainbusiness" value="" class="form-control">
                                 </div>
                              </div>
                              <div class="form-group">
                                 <label class="col-sm-1 control-label labels">兼营</label>
                                 <div class="col-sm-11">
                                    <input type="text" readonly="readonly" name="restbusiness" id="restbusiness" value="" class="form-control">
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
                                    <input type="text" readonly="readonly" name="employeetotal" id="employeetotal" value="" class="form-control">
                                 </div>
								 <label class="col-sm-2 control-label labels">技术人员数量</label>
                                 <div class="col-sm-2">
                                    <input type="text" readonly="readonly" name="techniciantotal" id="techniciantotal" value="" class="form-control">
                                 </div>
                                 
                              </div>
                              <div class="form-group">
                              <label class="col-sm-2 control-label labels">条码印刷技术负责人</label>
                                 <div class="col-sm-2">
                                    <input type="text" readonly="readonly" name="bcprincipal" id="bcprincipal" value="" class="form-control">
                                 </div>
								 <label class="col-sm-2 control-label labels">职务</label>
                                 <div class="col-sm-2">
                                    <input type="text" readonly="readonly" name="tpbusiness" id="tpbusiness" value="" class="form-control">
                                 </div>
								 <label class="col-sm-2 control-label labels">职称</label>
                                 <div class="col-sm-2">
                                    <input type="text" readonly="readonly" name="tpopost" id="tpopost" value="" class="form-control">
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
								 <label class="col-sm-1 checkbox-inline"></label>
                                    <label class="col-sm-2 checkbox-inline">
                                    <input type="checkbox" disabled="disabled" name="flat" id="flat" value="true" >平板胶印
                                    </label>
                                    <label class="col-sm-2 checkbox-inline">
                                    <input type="checkbox" disabled="disabled" name="gravure" id="gravure" value="true" >凹版印刷
                                    </label>
                                    <label class="col-sm-2 checkbox-inline">
                                    <input type="checkbox" disabled="disabled" name="webby" id="webby" value="true" >丝网印刷
                                    </label>
									<label class="col-sm-2 checkbox-inline">
                                    <input type="checkbox" disabled="disabled" name="flexible" id="flexible" value="true" >柔性版印刷
                                    </label>
                                 </div>
                              </div>
                              <div class="form-group">
                                 <label class="col-sm-2 control-label labels">其他（简述）</label>
                                 <div class="col-sm-10">
                                    <input type="text" readonly="readonly" name="elsetype" id="elsetype" value="" class="form-control">
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
									<label class="col-sm-1"></label>
                                    <label class="col-sm-2 checkbox-inline">
                                    <input type="checkbox" disabled="disabled" name="papery" id="papery" style="margin-right:4px;" value="true" >&nbsp;纸质
                                    </label>
                                    <label class="col-sm-2 checkbox-inline">
                                    <input type="checkbox" disabled="disabled" name="pastern" id="pastern" style="margin-right:4px;" value="true" >不干胶
                                    </label>
                                    <label class="col-sm-2 checkbox-inline">
                                    <input type="checkbox" disabled="disabled" name="frilling" id="frilling" style="margin-right:4px;" value="true" >瓦楞纸
                                    </label>
									<label class="col-sm-2 checkbox-inline">
                                    <input type="checkbox" disabled="disabled"  name="metal" id="metal" style="margin-right:4px;" value="true" >金属
                                    </label>
									<label class="col-sm-2 checkbox-inline">
                                    <input type="checkbox" disabled="disabled" name="plastic" id="plastic" style="margin-right:4px;" value="true" >塑料
                                    </label>
                                 </div>
                              </div>
                              <div class="form-group">
                                 <label class="col-sm-2 control-label labels">其他（简述）</label>
                                 <div class="col-sm-10">
                                    <input type="text" readonly="readonly" name="elsematerial" id="elsematerial" value="" class="form-control">
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
                             <div class="col-sm-4">
                             	<label class="col-sm-6 control-label labels">营业执照</label>
	                             <div class="col-lg-3">

	                             	<!-- <a onclick="viewfunc(1)" href='#Modal_show' data-toggle='modal' id="dialog1" class="col-sm-2"> -->
	                             	<a onclick="viewfunc(1)" id="dialog1" class="col-sm-6">
	                             		<input type="button" class="btn btn-info" value="查看">
	                             	</a>
	                             </div>
                             </div>
                             <div class="col-sm-4">
                             	<label class="col-sm-6 control-label labels">印刷经营许可证</label>
                             	<div class="col-lg-3">
                             		
                             		<!-- <a onclick="viewfunc(2)" data-toggle='modal' href='#Modal_show' id="dialog2" class="col-sm-2"> -->
                             		<a onclick="viewfunc(2)" class="col-sm-6">
                             			<input type="button" class="btn btn-info" value="查看">
                             		</a>
                          		</div> 	
                           </div>
                           <div class="col-sm-4">
                             	<label class="col-sm-6 control-label labels">质量手册</label>
                             	<div class="col-lg-3">
                             			<a onclick="viewfunc(3)" class="col-sm-6">
	                             	<!-- <a onclick="viewfunc(3)" data-toggle='modal' href='#Modal_show' id="yuview" class="col-sm-2" target="_blank"> -->
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
<!--                <div class="row"> -->
<!--                   <div class="col-lg-12"> -->
<!--                      <section class="panel"> -->
<!--                         <header class="panel-heading"> -->
<!--                            <span class="label label-primary">提交材料</span> -->
<!--                            <span class="tools pull-right" style="margin-top:-8px;"> -->
<!--                            <a href="javascript:;" class="fa fa-chevron-down"></a> -->
<!--                            </span> -->
<!--                         </header> -->
<!--                         <div class="panel-body"> -->
<!--                            <span class="form-horizontal tasi-form"> -->
<!-- 						   <div class="form-group"> -->
<!--                              <label class="col-sm-2 control-label labels">营业执照</label> -->
<!--                              <div class="col-sm-2"><a  onclick="check(this)" id="dialog1"> -->
<!--                              <input type="button" class="btn btn-info" value="查看"></a></div> -->
<!--                              <label class="col-sm-2 control-label labels">印刷经营许可证</label> -->
<!--                              <div class="col-sm-2"> -->
<!--                              <a  onclick="check(this)" id="dialog2"> -->
<!--                              <input class="btn btn-info" type="button" value="查看"></a> -->
<!--                            </div>  -->
<!--                             <label class="col-sm-2 control-label labels">质量手册</label> -->
<!--                              <div class="col-sm-2"> -->
<!--                              <a  href='' onclick="check(this)" id="dialog3"> -->
<!--                              <input class="btn btn-info" type="button" value="下载"></a> -->
<!--                            </div> -->
<!--                            </div>						  -->
<!--                            </span> -->
<!--                         </div> -->
<!--                      </section> -->
<!--                   </div> -->
<!--                </div> -->
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
                                  <%--<textarea class="form-control" readonly="readonly" rows="6" name="printEquipment" id="printEquipment"></textarea>--%>
                                 <table id="print" class="table table-bordered">
                                  	<thead style="display: none;">
                                  		<tr>
                                  			<td>印刷设备名称</td>
                                            <td>印刷设备型号</td>
                                  			<td>印刷设备产地</td>
                                  			<td>印刷设备数量</td>
                                  			<td>备注</td>
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
                                  <!-- <textarea class="form-control" readonly="readonly" rows="4" name="testEquipment" id="testEquipment"></textarea> -->
                                 <table id="testing" class="table table-bordered">
                                  	<thead style="display: none;">
                                  		<tr>
                                  			<td>设备名称</td>
                                  			<td>数量</td>
                                  			<td>最近一次校准日期</td>
                                  			<td>备注</td>
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
                                  <textarea class="form-control" readonly="readonly" rows="2" name="remarks" id="remarks"></textarea>
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
                           <span class="label label-primary">申请表</span>
                           <span class="tools pull-right" style="margin-top:-8px;">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                            <%-- <input type="hidden" id="titleNo" name="titleNo" value="${titleNo}" class="form-control"> --%>
                        	<input type="hidden" name="qualityno" id="qualityno" value="">
                        </header>
                        <div class="panel-body">
                           <span class="form-horizontal tasi-form">
                              <div class="form-group">
                              	<div class="col-sm-3"></div>
                                 <label class="col-sm-2 control-label labels">申请表：</label>
                                 <div class="col-sm-2 text-center">
                                    <button  class="btn btn-info" onclick="viewfunc(4)">查看</button>
                                 </div>
                                 <div class="col-sm-2 text-center">
                                    <button class="btn btn-success" onclick="downfunc(4)">下载</button>
                                 </div>
                                 <div class="col-sm-3"></div>
                              </div>
                              <div class="form-group">
                                 <div class="col-sm-3"></div>
                                 <label class="col-sm-2 control-label labels">申请表（盖章版）：</label>
                                 <div class="col-sm-2 text-center">
                                    <button class="btn btn-info" onclick="viewfunc(5)">查看</button>
                                 </div>
                                 <div class="col-sm-2 text-center">
                                    <button class="btn btn-success" onclick="downfunc(5)">下载</button>
                                 </div>
                                 <div class="col-sm-3"></div>
                              </div>
                           </span>
                        </div>
                     </section>
                  </div>
               </div>
			   <!-- END ROW  -->
            </section>
            </div>
            </section>
	    <!-- END SECTION -->
		<div id="Modal_show" class="modal fade">
	          <div class="modal-dialog">
	            <div class="modal-content" style="width:120%">
	              <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
	                  &times;
	                </button>
	                <h4 class="modal-title">查看</h4>
	              </div>
	              <div id="media" class="modal-body" style="height: 550px">
	              <a class="media" href=""></a>
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
</body>
</html>