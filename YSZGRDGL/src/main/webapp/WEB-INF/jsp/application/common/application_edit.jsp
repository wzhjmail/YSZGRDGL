<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	<script src="${ctx}/js/jquery.mloading.js"></script>
	<script src="${ctx}/js/jquery.form.js"></script>
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
	var obj_select = document.getElementById("back");
    var obj_div = document.getElementById("other");
    obj_select.onchange = function(){
        obj_div.style.display = this.value==1? "block" : "none";
    }
	$('.labels').css('text-align','right')
	getTask();
	
});
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
					$("#titleno").val(data.titleno);
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
					$("#titleNo").val(data.titleno);
				}
			);
	}
}
function getPrintEquipment(companyName) {//查看主要印刷设备
    $.post(
      "${ctx}/application/printEquipment/getByCompanyName.action",
      "companyName="+companyName,
      function (data) {
          if(data != null) {
              $("#print thead").show();
              $.each(data, function(i,item) {
                  $("#print").append("<tr><td style='display:none'>"+item.id+"</td><td>"+item.printName+"</td>"+
                      "<td>"+item.printModel+"</td><td>"+item.printPlace+"</td>" +
                      "<td>"+item.printNumber+"</td><td>"+item.printNotes+"</td></tr>")
              });
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
							item.name+"</td><td>"+item.count+"</td><td>"+(new Date(parseFloat(item.time))).format("yyyy-MM-dd")
							+"</td><td>"+item.remark+"</td></tr>");
				})
			}
		}
	);
}
// function check(show){
// 	if(show.getAttribute("id")=="dialog1"){
// 		//$("#display").attr("src","${ctx}/common/getImg.action?code="+$("#businessno").val());
// 		window.open('../viewPic.jsp?code='+$("#businessno").val())
// 	}else if(show.getAttribute("id")=="dialog2"){
// 		//$("#display").attr("src","${ctx}/common/getImg.action?code="+$("#certificateno").val());
// 		window.open('../viewPic.jsp?code='+$("#certificateno").val())
// 	}else if(show.getAttribute("id")=="dialog3"){
// 		window.open('${ctx}/application/uploadDisplay.action?code='+$("#qualityno").val());	
// 	}
// }
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
function notacross(){
	$('#reason').show()
}
function processTask(result){//初审任务的处理
	var taskId=$("#taskId").val();
    var comment="";
    var other=$("#back option:selected").text();
    var status=0;
    if(result){
    	status=5;
    	comment="无";
    }else{
    	status=2;
    	if(other=="其他"){
    		comment=$("#comment").val();
    	}else{
    		comment=other;
    	}
    	
    }
    if(comment=="-请选择-"){
		 wzj.confirm('温馨提醒', '请选择退回原因')
		 return false;
	 }else{
		 $.post(
	    	"${ctx}/application/climeTask.action",
	    	"taskId="+taskId+"&comment="+comment+"&result="+result+"&status="+status,
	    	function(data){
	    	}
	    ); 
		 $("#appForm").attr("action","");
		 var enterpriseName=$("#enterprisename").val();
			$.post(
				"${ctx}/application/ifApply.action",
				"enterpriseName="+enterpriseName,
				function(data){
			 		if(data>1){
			 			location.href="${ctx}/application/toPersonalTask.action";
			 		}else if(data==1){
			 			if(enterpriseName==$("#oldName").val()){
			 				$("#appForm").attr("action","${ctx}/application/update.action");
				        	$("#appForm").submit();
			 			}else{
			 				location.href="${ctx}/application/toPersonalTask.action";
			 			}
			 		}else{
			 			$("#appForm").attr("action","${ctx}/application/update.action");
			        	$("#appForm").submit();
			 		}
					$("#oldName").val(enterpriseName);	
				}
		); 
	 }
}

function viewfunc(show){
	var type=show.getAttribute("name");
	var $file;
	var imgObj;
	var $img;
	if(show.getAttribute("id")=="dialog1"){
		$("#dialog1").attr("href","#Modal_show1");
		if($('#em1').text()!=""&&type==1){
			$file = $("#select1");
			$img = $("#display");
			imgObj = document.getElementById("display");
			var fileObj = $file[0];
			var windowURL = window.URL || window.webkitURL;
			var dataURL;
			if(fileObj && fileObj.files && fileObj.files[0]){
			dataURL = windowURL.createObjectURL(fileObj.files[0]);
			$img.attr('src',dataURL);
			}else{
			dataURL = $file.val();
			imgObj.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
			imgObj.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = dataURL;
			}
		}else{
			$("#dialog1").attr("href","#Modal_show");
	 		$('#media').html("");
	 		$("#media").css("height","500px");
	//	 	 id='media'
	 		$('#media').html("<a class='media' href=''></a>");
	 		document.getElementById("displayPic").src="";
	 		var upurl;
	 		var titleNo=$("#titleNo").val();
	 		$.post(
	 				'${ctx}/commons/viewFile.action?type='+type+'&titleNo='+titleNo,
	 				function(data){
	 					if(data!=null)
	 					$.each(data,function(i,item){
	 						$.ajax({
	 							   url: "${ctx}/"+item.uprul,
	 							   type: 'GET',
	 							   complete: function(response){
	 									if(response.status == 404){
	 									  	$('#media').html('<h3 id="notFound" class="text-center">未找到文件！</h3>')
	 									}
	 							   }
	 							 });
	 						upurl=item.uprul;
	 							$("#media").css("height","0");
	 							document.getElementById("displayPic").src="${ctx}/"+upurl;
	 					});
	 				}
	 			);
		}
	}
	if(show.getAttribute("id")=="dialog2"){
		$("#dialog2").attr("href","#Modal_show1");
		if($('#em2').text()!=""&&type==2){
			$file = $("#select2");
			$img = $("#display");
			imgObj = document.getElementById("display");
			var fileObj = $file[0];
			var windowURL = window.URL || window.webkitURL;
			var dataURL;
			if(fileObj && fileObj.files && fileObj.files[0]){
			dataURL = windowURL.createObjectURL(fileObj.files[0]);
			$img.attr('src',dataURL);
			}else{
			dataURL = $file.val();
			imgObj.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
			imgObj.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = dataURL;
			}
		}else{
			$("#dialog2").attr("href","#Modal_show");
	 		$('#media').html("");
	 		$("#media").css("height","500px");
	//	 	 id='media'
	 		$('#media').html("<a class='media' href=''></a>");
	 		document.getElementById("displayPic").src="";
	 		var upurl;
	 		var titleNo=$("#titleNo").val();
	 		$.post(
	 				'${ctx}/commons/viewFile.action?type='+type+'&titleNo='+titleNo,
	 				function(data){
	 					if(data!=null)
	 					$.each(data,function(i,item){
	 						$.ajax({
	 							   url: "${ctx}/"+item.uprul,
	 							   type: 'GET',
	 							   complete: function(response){
	 									if(response.status == 404){
	 									  	$('#media').html('<h3 id="notFound" class="text-center">未找到文件！</h3>')
	 									}
	 							   }
	 							 });
	 						upurl=item.uprul;
	 							$("#media").css("height","0");
	 							document.getElementById("displayPic").src="${ctx}/"+upurl;
	 					});
	 				}
	 			);
		}
	}
	if(show.getAttribute("id")=="dialog3"){
		$("#dialog3").attr("href","#Modal_show2");
		if($('#em3').text()!=""&&type==3){
			$file = $("#select3");
			$img = $("#display2");
			imgObj = document.getElementById("display2");
			var fileObj = $file[0];
			var windowURL = window.URL || window.webkitURL;
			var dataURL;
			if(fileObj && fileObj.files && fileObj.files[0]){
			dataURL = windowURL.createObjectURL(fileObj.files[0]);
			$img.attr('src',dataURL);
			}else{
			dataURL = $file.val();
			imgObj.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
			imgObj.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = dataURL;
			}
		}else{
			$("#dialog3").attr("href","#Modal_show");
	 		$('#media').html("");
	 		$("#media").css("height","500px");
	//	 	 id='media'
	 		$('#media').html("<a class='media' href=''></a>");
	 		document.getElementById("displayPic").src="";
	 		var upurl;
	 		var titleNo=$("#titleNo").val();
	 		$.post(
	 				'${ctx}/commons/viewFile.action?type='+type+'&titleNo='+titleNo,
	 				function(data){
	 					if(data!=null)
	 					$.each(data,function(i,item){
	 						$.ajax({
	 							   url: "${ctx}/"+item.uprul,
	 							   type: 'GET',
	 							   complete: function(response){
	 									if(response.status == 404){
	 									  	$('#media').html('<h3 id="notFound" class="text-center">未找到文件！</h3>')
	 									}
	 							   }
	 							 });
	 						upurl=item.uprul;
	 						var fileType=upurl.substring(upurl.length-3,upurl.length);
	 						console.log("fileType:"+fileType);
	 						if(fileType=="pdf"){
	 							$("#display3").attr("src","${ctx}/"+upurl);
	 						}
	 						else{
	 							$("#media").css("height","0");
	 							document.getElementById("displayPic").src="${ctx}/"+upurl;
	 						}
	 					});
	 				}
	 			);
		}
	}

}
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
function viewForm(type){
	var dataURL;
	var titleNo=$("#titleNo").val();
	window.open('../viewPic.jsp?flag=1&titleNo='+titleNo+"&type="+type);
}
</script>
</head>
<body>
<iframe id="id_iframe" name="nm_iframe" style="display:none;">
</iframe>
	    <!-- BEGIN HEADER --> 
         <form id="appForm" action="" method="post" enctype="multipart/form-data">
         <section class="panel">
		    <!-- BEGIN WRAPPER  -->
                  <section class="wrapper">
               <center style="margin-top:-70px;color:#2a8ba7"><h1>信息查看</h1></center>
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
                        </header>
                        <div class="panel-body">
                           <span class="form-horizontal tasi-form">
                              <div class="form-group">
                                 <label class="col-sm-2 control-label labels">企业名称（中文）</label>
                                 <div class="col-sm-4">
                                    <input type="text"   id="enterprisename" name="enterprisename" value="${item.enterprisename}" class="form-control">
                                    <input type="hidden"   id="oldName" value="${item.enterprisename}" class="form-control">
                                 <input type="hidden"   id="titleno" name="titleno" value="" class="form-control">
                                 <input type="hidden"   id="aId" name="id" value="" class="form-control">
                                 </div>
                                 <label class="col-sm-2 control-label labels">企业名称（英文）</label>
                                 <div class="col-sm-4">
                                    <input type="text"  name="englishname" id="englishname" value="${item.englishname}" class="form-control">
                                 </div>
                              </div>
                              <div class="form-group">
                                 <label class="col-sm-2 control-label labels">详细地址</label>
                                 <div class="col-sm-10">
                                    <input type="text"  id="address" name="address" value="${item.address}" class="form-control">
                                 </div>
                                 
                              </div>
                              <div class="form-group">
                               	 <label class="col-sm-2 control-label labels">印刷经营许可证号码</label>
                                 <div class="col-sm-4">
                                    <input type="text"  name="certificateno" id="certificateno" value="${item.certificateno}" class="form-control">
                                 </div>	
                                  <label class="col-sm-1 control-label labels nopaddingleft">邮政编码</label>
                                 <div class="col-sm-2">
                                    <input type="text"  name="postalcode" id="postalcode" value="${item.postalcode}" class="form-control">
                                 </div>
                               	 <label class="col-sm-1 control-label labels">传真</label>
                                 <div class="col-sm-2">
                                    <input type="text"  name="fax" id="fax" value="${item.fax}" class="form-control validate[custom[fax]]">
                                 </div>
                              </div>
							  <div class="form-group">
                                 <label class="col-sm-2 control-label labels">营业执照号码</label>
                                 <div class="col-sm-4">
                                    <input type="text"  name="businessno" id="businessno" value="${item.businessno}" class="form-control" required="true">
                                 </div>
								 <label class="col-sm-1 control-label labels nopaddingleft">企业性质</label>
                                 <div class="col-sm-2">
                                    <select name="enterprisekind" id="enterprisekind" class="form-control validate[required]" >
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
								 <label class="col-sm-1 control-label labels nopaddingleft">注册资本</label>
                                 <div class="col-sm-2">
                                    <input type="text"  name="registercapital" id="registercapital" valut="${item.registercapital}" class="form-control">
                                 </div>
                              </div>
							  <div class="form-group">
                                 <label class="col-sm-2 control-label labels">法人代表</label>
                                 <div class="col-sm-4">
                                    <input type="text"  name="artificialperson" id="artificialperson" valut="${item.artificialperson}" class="form-control">
                                 </div>
								 <label class="col-sm-1 control-label labels">职务</label>
                                 <div class="col-sm-2">
                                    <input type="text"  name="apjob" id="apjob" valut="${item.apjob}" class="form-control">
                                 </div>
								 <label class="col-sm-1 control-label labels">电话</label>
                                 <div class="col-sm-2">
                                    <input type="text"  name="aptel" id="aptel" valut="${item.aptel}" class="form-control">
                                 </div>
                              </div>
							  <div class="form-group">
                                 <label class="col-sm-2 control-label labels">联系人</label>
                                 <div class="col-sm-4">
                                    <input type="text"  name="linkman" id="linkman" valut="${item.linkman}" class="form-control">
                                 </div>
								 <label class="col-sm-1 control-label labels">职务</label>
                                 <div class="col-sm-2">
                                    <input type="text"  name="ljob" id="ljob" valut="${item.ljob}" class="form-control">
                                 </div>
								 <label class="col-sm-1 control-label labels">电话</label>
                                 <div class="col-sm-2">
                                    <input type="text"  name="ltel" id="ltel" valut="${item.ltel}" class="form-control">
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
                                    <input type="text"   name="mainbusiness" id="mainbusiness" value="${item.mainbusiness}" class="form-control">
                                 </div>
                              </div>
                              <div class="form-group">
                                 <label class="col-sm-1 control-label labels">兼营</label>
                                 <div class="col-sm-11">
                                    <input type="text"  name="restbusiness" id="restbusiness" value="${item.restbusiness}" class="form-control">
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
                                    <input type="text"  name="employeetotal" id="employeetotal" value="${item.employeetotal}" class="form-control">
                                 </div>
								 <label class="col-sm-2 control-label labels">技术人员数量</label>
                                 <div class="col-sm-2">
                                    <input type="text"  name="techniciantotal" id="techniciantotal" value="${item.techniciantotal}" class="form-control">
                                 </div>
                              </div>
                              <div class="form-group">
	                              <label class="col-sm-2 control-label labels">条码印刷技术负责人</label>
                                 <div class="col-sm-2">
                                    <input type="text"  name="bcprincipal" id="bcprincipal" value="${item.bcprincipal}" class="form-control">
                                 </div>
								 <label class="col-sm-2 control-label labels">职务</label>
                                 <div class="col-sm-2">
                                    <input type="text"  name="tpbusiness" id="tpbusiness" value="${item.tpbusiness}" class="form-control">
                                 </div>
								 <label class="col-sm-2 control-label labels">职称</label>
                                 <div class="col-sm-2">
                                    <input type="text"  name="tpopost" id="tpopost" value="${item.tpopost}" class="form-control">
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
								 <label class="col-sm-1 checkbox-inline"></label>
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
                                    <input type="text"  name="elsetype" id="elsetype" value="${item.elsetype}" class="form-control">
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
									<label class="col-sm-1"></label>
                                    <label class="col-sm-2 checkbox-inline">
                                    <input type="checkbox"  name="papery" id="papery" style="margin-right:4px;" value="true" <c:if test="${item.papery==true}">checked="checked"</c:if>>&nbsp;纸质
                                    </label>
                                    <label class="col-sm-2 checkbox-inline">
                                    <input type="checkbox"  name="pastern" id="pastern" style="margin-right:4px;" value="true" <c:if test="${item.pastern==true}">checked="checked"</c:if>>不干胶
                                    </label>
                                    <label class="col-sm-2 checkbox-inline">
                                    <input type="checkbox"  name="frilling" id="frilling" style="margin-right:4px;" value="true" <c:if test="${item.frilling==true}">checked="checked"</c:if>>瓦楞纸
                                    </label>
									<label class="col-sm-2 checkbox-inline">
                                    <input type="checkbox"   name="metal" id="metal" style="margin-right:4px;" value="true" <c:if test="${item.metal==true}">checked="checked"</c:if>>金属
                                    </label>
									<label class="col-sm-2 checkbox-inline">
                                    <input type="checkbox"  name="plastic" id="plastic" style="margin-right:4px;" value="true" <c:if test="${item.plastic==true}">checked="checked"</c:if>>塑料
                                    </label>
                                 </div>
                              </div>
                              <div class="form-group">
                                 <label class="col-sm-2 control-label labels">其他（简述）</label>
                                 <div class="col-sm-10">
                                    <input type="text"  name="elsematerial" id="elsematerial" value="${item.elsematerial}" class="form-control">
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
	                             		<button type="button" class="btn btn-info" style="float: left;" onclick="select1.click()">选择文件</button>
	                             			<input class="change validate[funcCall[checkimg]]" data-type="img" type="file" name="business" id="select1" accept="image/bmp,image/jpg,image/jpeg,image/png">
										
	                             		<p id="em1" style="float: left;padding: 7px;margin: 0">${path1}</p>
	                             	</div>
	                             	<a onclick="check(this)"  class="btn btn-info" id="dialog1" name="1" class="col-sm-2">
	                             		查看
	                             	</a>
	                             </div>
                             </div>
                             </div>
                             <div class="form-group">
                             <div class="col-sm-12">
                             	<label class="col-sm-2 control-label labels">印刷经营许可证(.jpg/.jpeg/.png/.bmp)</label>
                             	<div class="col-lg-3">
                             		<div class="col-sm-4">
	                             		<button type="button" class="btn btn-info" style="float: left;" onclick="select2.click()">选择文件</button>
	                             			<input class="change validate[funcCall[checkimg]]" data-type="img" type="file" name="certificate" id="select2" accept="image/bmp,image/jpg,image/jpeg,image/png">
	                             		<p id="em2" style="float: left;padding: 7px;margin: 0">${path2}</p>
	                             	</div>	
	                             	<a onclick="check(this)"  class="btn btn-info" id="dialog2" name="2" class="col-sm-2">
	                             		查看
	                             	</a>
<!--                              		<a onclick="viewfunc(2)" data-toggle='modal' href='#Modal_show' id="dialog2" class="col-sm-2"> -->
<!--                              			<input type="button" class="btn btn-info" value="查看"> -->
<!--                              		</a> -->
                          		</div> 	
                           </div>	
                           </div>	
                            <div class="form-group">
                           <div class="col-sm-12">
                             	<label class="col-sm-2 control-label labels">质量手册(.pdf)</label>
                             	<div class="col-lg-3">
                             		<div class="col-sm-4">
	                             		<button type="button" class="btn btn-info" style="float: left;" onclick="select3.click()">选择文件</button>
	                             			<input class="change validate[funcCall[checkimg]]" data-type="pdf" type="file" name="quality" id="select3" accept="application/pdf">
	                             		<p id="em3" style="float: left;padding: 7px;margin: 0">${path3}</p>
	                             	</div>	
	                             	<a onclick="check(this)"  class="btn btn-info" id="dialog3" name="3" class="col-sm-2">
	                             		查看
	                             	</a>	
<!-- 	                             	<a onclick="viewfunc(3)" data-toggle='modal' href='#Modal_show' id="yuview" class="col-sm-2" target="_blank"> -->
<!--                              			<input type="button" class="btn btn-info" value="预览"> -->
<!--                              		</a> -->
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
						   <div>
                             <div class="col-lg-1"></div>
                             <div class="col-lg-10">
                                  <%--<textarea class="form-control"  rows="6" name="printEquipment" id="printEquipment">${item.printEquipment}</textarea>--%>
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
						   <div>
                             <div class="col-lg-1"></div>
                             <div class="col-lg-10">
                                  <!-- <textarea class="form-control"  rows="4" name="testEquipment" id="testEquipment"></textarea> -->
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
                                  <textarea class="form-control"  rows="2" name="remarks" id="remarks"></textarea>
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
                            <input type="hidden" id="titleNo" name="titleNo" value="${item}" class="form-control">
                        	<input type="hidden" name="qualityno" id="qualityno" value="">
                        </header>
                        <div class="panel-body">
                           <span class="form-horizontal tasi-form">
                              <div class="form-group">
                              	<div class="col-sm-3"></div>
                                 <label class="col-sm-2 control-label labels">申请表：</label>
                                 <p style="float: left;padding: 7px;margin: 0">${path5}</p>
                                 <div class="col-sm-2 text-center">
                                    <button type="button" class="btn btn-info"  onclick="viewForm(4)">查看</button>
                                 </div>
                                 <div class="col-sm-2 text-center">
                                    <button type="button" class="btn btn-success" onclick="downfunc(4)">下载</button>
                                 </div>
                                 <div class="col-sm-3"></div>
                              </div>
                              <div class="form-group">
                                 <div class="col-sm-3"></div>
                                 <label class="col-sm-2 control-label labels">申请表（盖章版）：</label>
                                 <p style="float: left;padding: 7px;margin: 0">${path4}</p>
                                 <div class="col-sm-2 text-center">
                                    <button type="button" class="btn btn-info"  onclick="viewForm(5)">查看</button>
                                 </div>
                                 <div class="col-sm-2 text-center">
                                    <button type="button" class="btn btn-success" onclick="downfunc(5)">下载</button>
                                 </div>
                                 <div class="col-sm-3"></div>
                              </div>
                           </span>
                        </div>
                     </section>
                  </div>
               </div>
			   <div class="row" id="reason" style="display: none;">
                  <div class="col-lg-12">
                     <section class="panel">
                        <header class="panel-heading">
                           <span class="label label-primary">驳回原因</span>
                           <span class="tools pull-right" style="margin-top:-8px;">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
                           <span class="form-horizontal tasi-form">
						   <div class="form-group">
                             <label class="col-sm-2 control-label labels">退回原因</label>
                                 <div class="col-sm-4">
                                    <select id="back"><option>-请选择-</option><option>营业执照不合格</option><option>资格认定不合格</option><option value="1">其他</option></select>
                                 </div>
                                 <div class="col-sm-4">
                                    <button type="button" class="btn btn-info" onclick="processTask(false)">确定退回</button>
                                 </div>
                                  </div>
                                 <div class="form-group" id="other" style="display:none">
                                 <label class="col-sm-2 control-label labels">其他原因</label>
                                 <div class="col-sm-10">
                                    <textarea class="form-control"  rows="2" id="comment"></textarea>
                                 </div>
                                 </div>
                                  </div>
                             </div>
                           </span>
                        </div>
                 <div class="row">
                 <div class="col-lg-12">
	               <section class="panel">
	               <div class="panel-body text-center">
	               		<div class="btn-group"><input type="button" onclick="goBack()" class="btn-info  btn" value="返回"></div>
	               		<div class="btn-group"><input type="button" onclick="notacross()" class="btn-danger  btn" value="退回"></div>
	               		<div class="btn-group"><input type="button" onclick="processTask(true)" class="btn-success  btn" value="通过"></div>
	               </div>
	               </section>
	               </div>
	            </div>
                     </section>
                     </section>
                  </div>
               </div>
 </form>

	            
	            <div id="Modal_show" class="modal fade" >
	          <div class="modal-dialog">
	            <div class="modal-content" style="width:120%;align:center">
	              <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
	                  &times;
	                </button>
	                <h4 class="modal-title">查看</h4>
	              </div>
	              <div>
	              <input type="hidden" id="URL" name="URL" value="" class="form-control">
	              </div>
	              <div>
	              </div>
	              <div class="modal-body" style="height: 500px">
	              <iframe id="display3" src="" frameborder="0" width="100%" height="100%" leftmargin="0" topmargin="0" ></iframe>
		         </div>
<!-- 	              <div id="media" class="modal-body" style="height: 500px"> -->
<!-- 	              <a class="media" href=""><button id="subBtn" type="button">vds</button></a> -->
<!-- 		         </div> -->
		         <div class="modal-body">
                <table width="100%" border="0" cellspacing="100px" align="center">
					<tr id="trs"><td><img id="displayPic" src="" width="100%"/></td></tr>
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
	    <!-- END SECTION -->
<!--          </section> -->
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
     <script type="text/javascript">
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
 function goBack(){
	 window.location.href="${ctx}/application/toPersonalTask.action";
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