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
<script type="text/javascript">
$(function(){
	$('.labels').css('text-align','right')
	
});
function check(show){
	if(show.getAttribute("id")=="dialog1"){
		//$("#display").attr("src","${ctx}/common/getImg.action?code="+$("#businessno").val());
		window.open('../viewPic.jsp?code='+$("#businessno").val())
	}else if(show.getAttribute("id")=="dialog2"){
		//$("#display").attr("src","${ctx}/common/getImg.action?code="+$("#certificateno").val());
		window.open('../viewPic.jsp?code='+$("#certificateno").val())
	}else if(show.getAttribute("id")=="dialog3"){
		window.open('${ctx}/application/uploadDisplay.action?code='+$("#qualityno").val());	
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
	var dataURL;
	var titleNo=$("#titleNo").val();
	console.log(type);
	console.log(titleNo);
	$.post(
			'${ctx}/commons/viewFile.action?type='+type+'&titleNo='+titleNo,
			function(data){
				console.log(data);
				dataURL=decodeURI(data);
				console.log(dataURL);
				window.open('../viewPDF.jsp?flag=1&code='+data);
			}
		);
}
</script>
</head>
<body>
	    <!-- BEGIN HEADER --> 
		    <!-- BEGIN WRAPPER  -->
                  <section class="wrapper">
               <center style="margin-top:-70px;color:#2a8ba7"><h1>查看评审表</h1></center>
			   <!-- BEGIN ROW  -->
               <div class="row">
                  <div class="col-lg-12">
                     <section class="panel">
                        <header class="panel-heading">
                           <span class="label label-primary">评审表</span>
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
                                 <label class="col-sm-2 control-label labels">评审表：</label>
                                 <div class="col-sm-2 text-center">
                                    <button class="btn btn-info" onclick="viewfunc(6)">查看</button>
                                 </div>
                                 <div class="col-sm-2 text-center">
                                    <button class="btn btn-success" onclick="downfunc(6)">下载</button>
                                 </div>
                                 <div class="col-sm-3"></div>
                              </div>
                              <div class="form-group">
                                 <div class="col-sm-3"></div>
                                 <label class="col-sm-2 control-label labels">评审表（盖章版）：</label>
                                 <div class="col-sm-2 text-center">
                                    <button class="btn btn-info" onclick="viewfunc(7)">查看</button>
                                 </div>
                                 <div class="col-sm-2 text-center">
                                    <button class="btn btn-success" onclick="downfunc(7)">下载</button>
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
</body>
</html>