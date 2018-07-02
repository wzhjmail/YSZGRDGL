<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
    <link rel="shortcut icon" href="${ctx}/img/favicon.ico">
    <title>用户信息修改 </title>
    <!-- BEGIN STYLESHEET-->
    <script type="text/javascript" src="${ctx}/js/jquery.js"></script>
	<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
	<link href="${ctx}/css/bootstrap-reset.css" rel="stylesheet">
	<link href="${ctx}/assets/font-awesome/css/font-awesome.css" rel="stylesheet">
	<link href="${ctx}/css/style.css" rel="stylesheet">
	<link href="${ctx}/css/style-responsive.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="${ctx}/assets/bootstrap-datepicker/css/datepicker.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/assets/bootstrap-datetimepicker/css/datetimepicker.css">
	<script src="${ctx}/js/bootstrap.min.js" ></script>
	<script src="${ctx}/assets/bootstrap-datepicker/js/bootstrap-datepicker.js"></script><!-- 日期显示 -->
	
	<link rel="stylesheet" type="text/css" href="${ctx}/assets/bootstrap-datepicker/css/datepicker.css"><!-- BOOTSTRAP DATEPICKER PLUGIN CSS -->
	<link rel="stylesheet" type="text/css" href="${ctx}/assets/bootstrap-timepicker/compiled/timepicker.css"><!-- BOOTSTRAP TIMEPICKER PLUGIN CSS -->
	<link rel="stylesheet" type="text/css" href="${ctx}/assets/bootstrap-colorpicker/css/colorpicker.css"><!-- BOOTSTRAP COLORPICKER PLUGIN CSS -->
	<link rel="stylesheet" type="text/css" href="${ctx}/assets/bootstrap-daterangepicker/daterangepicker-bs3.css"><!-- DATERANGE PLUGIN CSS -->
	<link rel="stylesheet" type="text/css" href="${ctx}/assets/bootstrap-datetimepicker/css/datetimepicker.css"><!-- DATETIMEPICKER PLUGIN CSS -->
	<link rel="stylesheet" type="text/css" href="${ctx}/assets/jquery-multi-select/css/multi-select.css"><!-- JQUERY MULTI SELECT PLUGIN CSS -->
    <script src="${ctx}/js/advanced-form-components.js" ></script><!--  ADVANCE FORM COMPONENTS PAGE JS  -->
	<style type="text/css">
	.form-signin {
	    max-width: 330px;
	    margin:10% auto;
	    background: #fff;
	    border-radius: 5px;
	    -webkit-border-radius: 5px;
	}
	</style>
<script type="text/javascript">
	$(function(){
		var roled="";
		var ID=$("#userId").val();
		$.ajaxSetup({async : false});
		$.post(//获取用户的角色
			"${ctx}/role/findRoleByUserId.action",
			"userId="+ID,
			function(data){
				roled=data.name;
			}
		);
		$.post(//获取所有的角色，并显示用户的当前角色
			"${ctx}/role/findAll.action",
			function(data){
				for(var i=0;i<data.length;i++){
					var result="<option id='"+data[i].id+"' ";
					if(roled==data[i].name){
						result+="selected='selected' ";
					}
					result+=">"+data[i].name+"</option>";
					$("#role").append(result);
				}
			}
		);
		$.post(//获取用户的信息，并显示
			"${ctx}/sysUser/findUserById.action",
			"ID="+ID,
			function(data){
				if(data!=null){
					$("#name").val(data.username);
					$("#username").val(data.usercode);
					$("#pwd1").val(data.password);
					$("#pwd2").val(data.password);
					$("#birth").val("1991-09-25");
				}
			}
		); 
		$("#bw1").click(function(){
			$("#pwd1").val("");
		});
		$("#bw2").click(function(){
			$("#pwd2").val("");
		});
		$("#update").click(function(){//修改提交
			var username = $("#username").val();
			var name = $("#name").val();
			var pwd1 = $("#pwd1").val();
			var pwd2 = $("#pwd2").val();
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
			var param = "usercode="+username+"&username="+name+
				"&password="+pwd1+"&role="+$('#role option:selected').attr("id");//选中的值
				$.ajaxSetup({async : false});
				$.post(
				"${ctx}/sysUser/update.action",
				param,
				function(data){
					if(data.toString()>0){
						location.href = '${ctx}/first.action';
					}else{
						wzj.alert('温馨提示',"有问题！");
					}
				}
			);
		});//提交*/
	});//开始
</script>
  </head>
  <body class="login-screen">
    <!-- BEGIN SECTION -->
    <div class="container">
		<form class="form-signin">
			<h2 class="form-signin-heading">修改信息</h2>
			<!-- LOGIN WRAPPER  -->
			<div class="login-wrap">
				<input type="hidden" id="userId" value="${activeUser.userid}">
				用户名 <input type="text" class="form-control" readonly="readonly" id="username" name="username">
				真实姓名<input type="text" class="form-control" id="name" name="name">
				<div style="width: 90%"class="input-append">
					密码<input type="password" id="pwd1" name="pwd1" size="16" class="form-control">
					 <span class="input-group-btn add-on" style="margin-top: -57px;z-index:10;">
						<button class="btn btn-default active" type="button" id="bw1" style="height: 39px;width: 40px;margin-left: -8px">
							<i class="fa fa-times" style="bg-color:#c2c2c2;"> </i>
						</button>
					</span>
				</div>
				<div style="width: 90%"class="input-append">
					确认密码<input type="password" id="pwd2" name="pwd2" size="16" class="form-control">
					 <span class="input-group-btn add-on" style="margin-top: -57px;z-index:10;">
						<button class="btn btn-default active" type="button" id="bw2" style="height: 39px;width: 40px;margin-left: -8px">
							<i class="fa fa-times" style="bg-color:#c2c2c2;"> </i>
						</button>
					</span>
				</div>
				<!-- 注册页面的角色申请该不该要？ -->
				分配角色<select id="role" name="role" class="form-control"
					style="height: 37px; font-size: 12px; background-color: #eee; margin-bottom: 15px;">
				</select>
				出生年月
				<div data-date-viewmode="years" style="width: 90%" data-date="2012-12-12"
					data-date-format="yyyy-mm-dd" class="input-append date dpYears">
					<input type="text" readonly="readonly" id="birth" size="16" class="form-control">
					 <span class="input-group-btn add-on" style="margin-top:-57px;z-index:10;">
						<button class="btn btn-info" type="button" style="height: 39px;width: 40px;margin-left: -8px">
							<i class="fa fa-calendar" style="bg-color: 41CAC0;"> </i>
						</button>
					</span>
				</div>
				<button class="btn btn-lg btn-login btn-block" type="button"
					id="update">提交</button>
			</div>
		</form>

	</div>
  </body> 
  
</html>

 
 