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
	
<script src="${ctx}/js/jquery-1.8.3.min.js"></script><!-- BASIC JS LIABRARY 1.8.3 -->
<script src="${ctx}/js/style_self.js"></script><!-- 右键特效的js -->
<script src="${ctx}/js/zooming.min.js"></script>
<script type="text/javascript">
$(function(){
	$('.labels').css('text-align','right')
	var id=$("#id").val();
	if(id!=null){
		param="id="+id;
		$.ajaxSetup({ async : false });
		$.post(
				"${ctx}/application/getApp.action",
				param,
				function(data){
					$("#enterprisename").val(data.enterprisename);
					$("#englishname").val(data.englishname);
					$("#businessno").val(data.businessno);
					$("#certificateno").val(data.certificateno);
				}
			);
		var businessno=$("#businessno").val();
		code1="code1="+businessno;
		var certificateno=$("#certificateno").val();
		code2="code1="+certificateno;
		$.post(
				"${ctx}/application/uploadDisplay.action",
				code1,
				function(data){
						var result='<img src="${ctx}/'+data.uprul +'" alt="'+data.code+'" id="'+data.code+'" class="col-sm-3" data-toggle="modal" href="#Modal_show" onclick="zoom(this)"/>';
						$("#yyzz").append(result);
				}
			);
		$.post(
				"${ctx}/application/uploadDisplay.action",
				code2,
				function(data){
						var result='<div class="col-sm-3">'+
							'<img src="${ctx}/'+data.uprul +'" alt="'+data.code+'" id="'+data.code+'" class="col-sm-12"/><p></p></div>';
						$("#yszg").append(result);
				}
			);
		$.post(
				"${ctx}/application/uploadDisplay.action",
				code1,
				function(data){
						var result='<div class="col-sm-3">'+
						'<img src="${ctx}/'+data.uprul +'" alt="'+data.code+'" id="'+data.code+'" class="col-sm-12"/><p></p></div>';
						$("#yszg").append(result);
				}
			);
	}
});
//图片路径
//营业执照
function imgURL1(){
	var businessno=$("#businessno").val();
	param="code1="+businessno;
	$.post(
			"${ctx}/application/uploadDisplay.action",
			param,
			function(data){
				$("#display").attr("src","${ctx}/"+data.uprul);
			}
		);
}
//印刷经营许可证
function imgURL2(){
	var certificateno=$("#certificateno").val();
	param="code1="+certificateno;
	$.post(
			"${ctx}/application/uploadDisplay.action",
			param,
			function(data){
//				$("#select2").val(data.uprul);
				$("#display").attr("src","${ctx}/"+data.uprul);
			}
		);
}

function zoom(picture){
	var url=picture.getAttribute("src");
	console.log(url);
	$("#display").attr("src",url);
}



function check(show){
	var $file;
	if(show.getAttribute("id")=="dialog1"){
		imgURL1();
		$file = $("#select1");
	}else if(show.getAttribute("id")=="dialog2"){
		imgURL2();
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
</script>
</head>
<body>
	    <!-- BEGIN HEADER --> 
         <form id="appForm" action="${ctx}/application/insert.action" method="post" enctype="multipart/form-data">
         <section id="">
		    <!-- BEGIN WRAPPER  -->
                  <section class="wrapper">
               <center style="margin-top:-70px;color:#2a8ba7"><h1>企业相关材料</h1></center>
			  <div class="Noprint">
		<!-- 	  <div class="btn-group pull-right" style="margin-top:-45px;margin-bottom:10px;">
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
                           <span class="label label-primary">企业相关材料</span>
                           <span class="tools pull-right" style="margin-top:-8px;">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                            <input type="hidden" id="id" value="${id}" class="form-control">
                        </header>
                        <div class="panel-body">
                           <span class="form-horizontal tasi-form">
                              <div class="form-group">
                                 <label class="col-sm-2 control-label labels">企业名称（中文）</label>
                                 <div class="col-sm-4">
                                    <input type="text" readonly="true" id="enterprisename" name="enterprisename" value="${item.enterprisename}" class="form-control">
                                 </div>
                                 <label class="col-sm-2 control-label labels">企业名称（英文）</label>
                                 <div class="col-sm-4">
                                    <input type="text"  readonly="true" name="englishname" id="englishname" value="${item.englishname}" class="form-control">
                                 </div>
                              </div>
                              <div class="form-group">
                               	 <label class="col-sm-2 control-label labels">印刷经营许可证号码</label>
                                 <div class="col-sm-4">
                                    <input type="text"  readonly="true" name="certificateno" id="certificateno" value="${item.certificateno}" class="form-control">
                                 </div>	
                                 <label class="col-sm-2 control-label labels">营业执照号码</label>
                                 <div class="col-sm-4">
                                    <input type="text" readonly="true" name="businessno" id="businessno" value="${item.businessno}" class="form-control" required="true">
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
                           <span class="label label-primary">营业执照</span>
                           <span class="tools pull-right" style="margin-top:-8px;">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
                           <span class="form-horizontal tasi-form">
						   <div class="form-group">
						   	<div class="col-sm-10" id="yyzz">
						   	<div class="col-sm-3"><img src="../yyzz.jpg" class="col-sm-12"/><p class="text-center">营业执照</p></div>
						   	<div class="col-sm-3"><img src="../yyzz.jpg" class="col-sm-12"/><p class="text-center">营业执照</p></div>
						   	<div class="col-sm-3"><img src="../yyzz.jpg" class="col-sm-12"/><p class="text-center">营业执照</p></div>
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
                           <span class="label label-primary">印刷经营许可证</span>
                           <span class="tools pull-right" style="margin-top:-8px;">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
                           <span class="form-horizontal tasi-form">
						   <div class="form-group">
						   <div class="col-sm-10" id="yszg"></div>
                           </div>						 
                           </span>
                        </div>
                     </section>
                  </div>
               </div>
               <div class="row">
	               <section class="panel">
	               <div class="panel-body">
	               		<div class="col-lg-5 col-sm-5"></div>
	               		<div class="col-lg-2 col-sm-2"><input type="button" onclick="history.back()" class="btn-success btn-block btn" value="返回"></div>
	               		<div class="col-lg-5 col-sm-5"></div>
	               </div>
	               </section>
	           </div>
			   <!-- END ROW  -->
	            <div id="Modal_show" class="modal fade">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                  &times;
                </button>
                <h4 class="modal-title">查看图片</h4>
              </div>
              <div class="modal-body">
                <table width="100%" border="0" cellspacing="100px" align="center">
					<tr id="trs"><td><img id="display" src="" width="100%"/></td></tr>
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
    <script type="text/javascript">
    	//$('#yyzz img').zoomify();
    	var zooming = new Zooming({
    	    bgColor: 'rgb(0, 0, 0)',
    	    bgOpacity: '0.5'
    	});
    	zooming.listen('#yyzz img');
    	    	
    </script>
</body>
</html>