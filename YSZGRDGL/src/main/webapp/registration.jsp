<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
 <head>
    <link rel="shortcut icon" href="img/favicon.ico">
    <title>注册页面 </title>
    <!-- BEGIN STYLESHEET-->
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/bootstrap-reset.css" rel="stylesheet">
	<link href="assets/font-awesome/css/font-awesome.css" rel="stylesheet">
	<link href="css/style.css" rel="stylesheet">
	<link href="css/style-responsive.css" rel="stylesheet">
	<script type="text/javascript" src="js/jquery.js"></script>
	<script src="js/bootstrap.min.js" ></script>
	<script src="assets/bootstrap-datepicker/js/bootstrap-datepicker.js"></script><!-- 日期显示 -->
	<script src="assets/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script>
	<script src="assets/bootstrap-timepicker/js/bootstrap-timepicker.js"></script>
	<script src="assets/bootstrap-colorpicker/js/bootstrap-colorpicker.js"> </script>
	<script src="assets/jquery-multi-select/js/jquery.multi-select.js"></script>
	<script src="assets/fuelux/js/spinner.min.js"></script>
	<script src="assets/bootstrap-wysihtml5/bootstrap-wysihtml5.js"></script>
	<script src="js/style_self.js"></script>
	
	<link rel="stylesheet" type="text/css" href="css/style_self.css">
	<link rel="stylesheet" type="text/css" href="assets/bootstrap-datepicker/css/datepicker.css"><!-- BOOTSTRAP DATEPICKER PLUGIN CSS -->
	<link rel="stylesheet" type="text/css" href="assets/bootstrap-timepicker/compiled/timepicker.css"><!-- BOOTSTRAP TIMEPICKER PLUGIN CSS -->
	<link rel="stylesheet" type="text/css" href="assets/bootstrap-colorpicker/css/colorpicker.css"><!-- BOOTSTRAP COLORPICKER PLUGIN CSS -->
	<link rel="stylesheet" type="text/css" href="assets/bootstrap-daterangepicker/daterangepicker-bs3.css"><!-- DATERANGE PLUGIN CSS -->
	<link rel="stylesheet" type="text/css" href="assets/bootstrap-datetimepicker/css/datetimepicker.css"><!-- DATETIMEPICKER PLUGIN CSS -->
	<link rel="stylesheet" type="text/css" href="assets/jquery-multi-select/css/multi-select.css"><!-- JQUERY MULTI SELECT PLUGIN CSS -->
  <script src="js/advanced-form-components.js" ></script><!--  ADVANCE FORM COMPONENTS PAGE JS  -->
    <!-- END JS -->
<script type="text/javascript">
	//判断是否注册过
	function checkRegist(){
		var Regist=false;
		var username = $("#username").val();
		var param = "usercode="+username;
		     $.ajaxSetup({ 
		    	async : false 
		     });
		     $.post(
				"${pageContext.request.contextPath}/sysUser/checkRegist.action",
				param,
				function(data) {
					if("true"==data.result){
						Regist=true;
					}else{
						wzj.alert('温馨提示',"该用户名已存在！");
					}
			},"json"); 
		return Regist;
	}
	$(function(){
		$.post(
			"${pageContext.request.contextPath}/role/findRoles.action",
			function(data){
				if(data!=null&&data.length>0){
					for(var i=0;i<data.length;i++){
						var result='<option id="'+data[i].id+'">'+data[i].name+"</option>";
						$("#role").append(result);
					}
				}
			}
		); 
		$("#regist").click(function(){
			var username = $("#username").val();
			var name = $("#name").val();
			var pwd1 = $("#pwd1").val();
			var pwd2 = $("#pwd2").val();
			var locked=1;
			if (username == "") {
				 wzj.alert('温馨提示',"用户名不能为空");
				return false;
			}
			if(name==""){
				 wzj.alert('温馨提示',"真实姓名不能为空");
				return false;
			}
			if(pwd1==""||pwd2==""){
				wzj.alert('温馨提示',"密码不能为空");
				return false;
			}else if(pwd1!==pwd2){
				wzj.alert('温馨提示',"两次输入不一致");
				return false
			}
			if(checkRegist()){
				var param = "usercode="+username+"&username="+name+
				"&password="+pwd1+"&role="+$('#role option:selected').attr("id")+"&locked="+locked
				+"&dept="+$('#dept option:selected').text();//选中的值 
				$.ajaxSetup({async : false});
				$.post(
					"${pageContext.request.contextPath}/sysUser/register.action",
					param,
					function(data){
						if(data.toString().indexOf("username=") != -1){
// 							wzj.confirm('温馨提醒', '注册成功，是否登录？', function(flag) {
// 								if(flag) {
// 									$.ajaxSetup({async : false});
// 									$.post(
// 										"${pageContext.request.contextPath}/login.action",
// 										data,
// 										function(dat){
// 											if (dat.toString().indexOf("未知错误")!=-1) {
// 												wzj.alert('温馨提示',"注册成功，登录失败");
// 											} else {
// 												location.href = '${pageContext.request.contextPath}/first.action';
// 											}
// 										}
// 								);//login
// 								}else{
// 									location.href = "${pageContext.request.contextPath}/index.jsp"
// 								}
// 							});
							wzj.alert('注册成功',"等待审核！");
							setTimeout("location.href = '${pageContext.request.contextPath}/login.jsp'",5000);
// 							location.href = "${pageContext.request.contextPath}/login.jsp"
					}else {
						wzj.alert('温馨提示',"插入数据库出错,请修改username!");
					}
				},"text");//register
				}
			
		});//click
	});//开始
</script>
  </head>
  <body class="login-screen">
    <!-- BEGIN SECTION -->
    <div class="container">
      <form class="form-signin">
        <h2 class="form-signin-heading">
        	  即	刻	注	册
        </h2>
		<!-- LOGIN WRAPPER  -->
        <div class="login-wrap">
          <input type="text" class="form-control" placeholder="登录名" id="username" name="username">
          <input type="text" class="form-control" placeholder="真实姓名" id="name" name="name">
          <input type="password" class="form-control" placeholder="密码" id="pwd1" name="pwd1">
          <input type="password" class="form-control" placeholder="确认密码" id="pwd2" name="pwd2">
 		 <!-- 注册页面的角色申请该不该要？-->
 		  <select id="role" name="role" class="form-control" 
 		  	style="height:37px;font-size:12px;background-color:#eee;margin-bottom:15px;">
          </select>
          <select id="dept" name="dept" class="form-control" 
 		  	style="height:37px;font-size:12px;background-color:#eee;margin-bottom:15px;">
 		  	<option value="企业用户">企业用户</option>
          	<option value="分支机构用户">分中心用户</option>
          	<option value="中心用户">中心用户</option>
          	<option value="专家用户">专家用户</option>
          </select>
           <div data-date-viewmode="years" style="width:90%" data-date-format="yyyy-mm-dd" data-date="2012-02-12" class="input-append date dpYears">
            <input type="text" readonly="" id="birth" placeholder="生日" size="16" class="form-control">
          <span class="input-group-btn add-on" style="margin-top: -55px;z-index:10;">
            <button class="btn btn-info" type="button">
              <i class="fa fa-calendar" style="bg-color:41CAC0;">
              </i>
            </button>
          </span>
        </div>
            
           <button class="btn btn-lg btn-login btn-block" type="button" id="regist" >
           	 提&nbsp;&nbsp;&nbsp;&nbsp;交
          </button><!-- onclick="loginsubmit()" -->
         </div> 
      </form>
       
    </div>
  </body> 
  
</html>

 
 