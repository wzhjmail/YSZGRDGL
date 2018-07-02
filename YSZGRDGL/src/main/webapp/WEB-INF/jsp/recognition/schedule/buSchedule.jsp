<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<%@ taglib prefix="myshiro" uri="/customize-tags" %>
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>分支机构评审</title>
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
#progressBar{
    width: 80%;
    height: 100px;
    position: relative;
    margin: 46px 0 0 80px;
}
#progressBar #schedule{
    width: 100%;
    height: 8px;
    position: absolute;
    top:50%;
    left: 0;
    margin-top:5px;
    border:1px solid #ddd;
    background: #ccc;
}
#progressBar #schedule span:nth-child(1){
    position: absolute;
    display: inline-block;
    background: #ccc;
    height: 5px;
    width: 12.5%;
    left:0%
}
#progressBar #schedule span:nth-child(2){
    position: absolute;
    display: inline-block;
    background: #ccc;
    height: 5px;
    width: 12.5%;
    left:12.5%
}
#progressBar #schedule span:nth-child(3){
    position: absolute;
    display: inline-block;
    background: #ccc;
    height: 5px;
    width:12.5%;
    left:25%
}
#progressBar #schedule span:nth-child(4){
    position: absolute;
    display: inline-block;
    background: #ccc;
    height: 5px;
    width:12.5%;
    left:37.5%
}
#progressBar #schedule span:nth-child(5){
    position: absolute;
    display: inline-block;
    background: #ccc;
    height: 5px;
    width: 12.5%;
    left:50%
}
#progressBar #schedule span:nth-child(6){
    position: absolute;
    display: inline-block;
    background: #ccc;
    height: 5px;
    width:12.5%;
    left:62.5%
}
#progressBar #schedule span:nth-child(7){
    position: absolute;
    display: inline-block;
    background: #ccc;
    height: 5px;
    width: 12.5%;
    left:75%
}
 #progressBar #schedule span:nth-child(8){ 
     position: absolute; 
     display: inline-block; 
     background: #ccc; 
     height: 5px; 
     width: 12.5%; 
     left:87.5% 
 } 

#progressBar>span{
    position: absolute;
    top:0;
    margin-top: 45px;
    width: 20px;
    height: 20px;
    border:1px solid #ddd;
    border-radius: 50%;
    background: #ccc;
    margin-left: -10px;
    color:#fff;
}
#progressBar>span:nth-child(1){
    left: 0%;
    background:#ccc;
}

#progressBar>span:nth-child(3){
    left: 12.5%;
    background:#ccc;
}
#progressBar>span:nth-child(4){
    left: 25%;
    background:#ccc;
}
#progressBar>span:nth-child(5){
    left: 37.5%;
    background:#ccc;
}
#progressBar>span:nth-child(6){
    left: 50%;
    background:#ccc;
}
#progressBar>span:nth-child(7){
    left: 62.5%;
    background:#ccc;
}
#progressBar>span:nth-child(8){
    left: 75%;
    background:#ccc;
}
 #progressBar>span:nth-child(9){ 
     left: 87.5%; 
     background:#ccc; 
 } 
#progressBar>span:nth-child(10){
    left: 100%;
    background:#ccc;
}
#prompt>span{
	position: absolute;
    width: 20px;
    height: 20px;
    border: 1px solid #ddd;
    border-radius: 50%;
    background: #ccc;
    margin-left: -10px;
    color: #fff;
}
#prompt>p{
	margin-left: 5%
}
</style>
<script type="text/javascript">
// setTimeout("location=location; ", 5000);
$(function(){
	var id=$("#ysId").val();
	$.post(
			"${ctx}/application/getApp.action",
    		"id="+id,
    		function(data){
				switch(data.status)
				{
				case 12:
					break;
				case 13:
				$("#pass1").css("background","#72d0eb");
				$("#pass2").css("background","#72d0eb");
				$("#one").css("background","#72d0eb");
				break;
				case 14:
					$("#pass1").css("background","#72d0eb");
					$("#pass2").css("background","#72d0eb");
					$("#pass3").css("background","green");
					$("#pass4").css("background","#72d0eb");
					$("#one").css("background","#72d0eb");
					$("#two").css("background","#72d0eb");
					$("#three").css("background","#72d0eb");
					break;
				case 15:
					$("#pass1").css("background","#72d0eb");
					$("#pass2").css("background","#72d0eb");
					$("#pass3").css("background","red");
					$("#one").css("background","#72d0eb");
					$("#two").css("background","#72d0eb");
					break;
				case 16:
					$("#pass1").css("background","#72d0eb");
					$("#pass2").css("background","#72d0eb");
					$("#pass3").css("background","green");
					$("#pass4").css("background","#72d0eb");
					$("#pass5").css("background","green");
					$("#pass6").css("background","#72d0eb");
					$("#one").css("background","#72d0eb");
					$("#two").css("background","#72d0eb");
					$("#three").css("background","#72d0eb");
					$("#four").css("background","#72d0eb");
					$("#five").css("background","#72d0eb");
					break;
				case 17:
					$("#pass1").css("background","#72d0eb");
					$("#pass2").css("background","#72d0eb");
					$("#pass3").css("background","green");
					$("#pass4").css("background","#72d0eb");
					$("#pass5").css("background","red");
					$("#one").css("background","#72d0eb");
					$("#two").css("background","#72d0eb");
					$("#three").css("background","#72d0eb");
					$("#four").css("background","#72d0eb");
					break;
				case 18:
					$("#pass1").css("background","#72d0eb");
					$("#pass2").css("background","#72d0eb");
					$("#pass3").css("background","green");
					$("#pass4").css("background","#72d0eb");
					$("#pass5").css("background","green");
					$("#pass6").css("background","#72d0eb");
					$("#pass7").css("background","green");
					$("#pass8").css("background","#72d0eb");
					$("#one").css("background","#72d0eb");
					$("#two").css("background","#72d0eb");
					$("#three").css("background","#72d0eb");
					$("#four").css("background","#72d0eb");
					$("#five").css("background","#72d0eb");
					$("#six").css("background","#72d0eb");
					$("#seven").css("background","#72d0eb");
					break;
				case 19:
					$("#pass1").css("background","#72d0eb");
					$("#pass2").css("background","#72d0eb");
					$("#pass3").css("background","green");
					$("#pass4").css("background","#72d0eb");
					$("#pass5").css("background","green");
					$("#pass6").css("background","#72d0eb");
					$("#pass7").css("background","red");
					$("#one").css("background","#72d0eb");
					$("#two").css("background","#72d0eb");
					$("#three").css("background","#72d0eb");
					$("#four").css("background","#72d0eb");
					$("#five").css("background","#72d0eb");
					$("#six").css("background","#72d0eb");
					break;
				case 20:
					$("#pass1").css("background","#72d0eb");
					$("#pass2").css("background","#72d0eb");
					$("#pass3").css("background","green");
					$("#pass4").css("background","#72d0eb");
					$("#pass5").css("background","green");
					$("#pass6").css("background","#72d0eb");
					$("#pass7").css("background","green");
					$("#pass8").css("background","#72d0eb");
					$("#pass9").css("background","green");
// 					$("#pass10").css("background","#72d0eb");
					$("#one").css("background","#72d0eb");
					$("#two").css("background","#72d0eb");
					$("#three").css("background","#72d0eb");
					$("#four").css("background","#72d0eb");
					$("#five").css("background","#72d0eb");
					$("#six").css("background","#72d0eb");
					$("#seven").css("background","#72d0eb");
					$("#eight").css("background","#72d0eb");
// 					$("#nine").css("background","#72d0eb");
					break;
				case 21:
					$("#pass1").css("background","#72d0eb");
					$("#pass2").css("background","#72d0eb");
					$("#pass3").css("background","green");
					$("#pass4").css("background","#72d0eb");
					$("#pass5").css("background","green");
					$("#pass6").css("background","#72d0eb");
					$("#pass7").css("background","green");
					$("#pass8").css("background","#72d0eb");
					$("#pass9").css("background","red");
					$("#one").css("background","#72d0eb");
					$("#two").css("background","#72d0eb");
					$("#three").css("background","#72d0eb");
					$("#four").css("background","#72d0eb");
					$("#five").css("background","#72d0eb");
					$("#six").css("background","#72d0eb");
					$("#seven").css("background","#72d0eb");
					$("#eight").css("background","#72d0eb");
					break;
				default:
				  $("#progressBar span").css("background","#ccc");
				}
    		}
    	);
	});
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

<section class="panel" style="margin-bottom:0px">
                 <div class="panel-body">
                   <div id="user" style="margin-bottom: 10px;font-size: 20px;width: 40%;" >
                  用户：<span style="color:#54B6D1">${activeUser.realname}</span>的复认业务进度
                  <input type="hidden" id="ysId" value="${id}">
                  </div>
                   <div id="prompt" style="width: 40%;" >
                   <span id="b" class="" style="background:#72d0eb "></span><p>:进行中</p><br/>
                   <span id="r" class="" style="background:red "></span><p>:未通过</p><br/>
                   <span id="g" class="" style="background:green "></span><p>:通过</p>
                  </div>
      <div id="progressBar">
     
      <div id="business" style="margin-bottom: -20px">
         <span style="margin-left: -5%">复认申请中</span>
         <span style="margin-left: 4%">初审中</span>
	     <span style="margin-left: 4%">初审通过</span>
	     <span style="margin-left: 4%">评审中</span>
	     <span style="margin-left: 4%">评审通过</span>
	     <span style="margin-left: 3%">中心审核中</span>
	     <span style="margin-left: 4%">中心审核通过</span>
	     <span style="margin-left: 2%">中心复核中</span>
	     <span style="margin-left: 1%">通过</span>
     </div>
     <!-- 进度条 -->
     <div id="schedule">
         <span id="one"></span>
         <span id="two"></span>
         <span id="three"></span>
         <span id="four"></span>
         <span id="five"></span>
         <span id="six"></span>
         <span id="seven"></span>
         <span id="eight"></span>
     </div>
     <!-- 五个圆 -->
     <span id="pass2"></span>
     <span id="pass3"></span>
     <span id="pass4"></span>
     <span id="pass5"></span>
     <span id="pass6"></span>
     <span id="pass7"></span>
     <span id="pass8"></span>
     <span id="pass9"></span>
     <span id="pass1"></span>
</div>
		</div>
		</section>      
  <!-- END JS --> 
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
	<script src="${ctx}/js/editable-table.js" ></script><!-- EDITABLE TABLE JS  -->
</body>
</html>