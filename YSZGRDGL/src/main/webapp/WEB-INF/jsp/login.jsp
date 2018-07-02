<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
 <head>
    <title>系统登录页面 </title>
     <link rel="icon" href="${ctx}/img/header.ico" type="image/x-icon"/>  
    <!-- BEGIN STYLESHEET-->
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/bootstrap-reset.css" rel="stylesheet">
	<link href="assets/font-awesome/css/font-awesome.css" rel="stylesheet">
	<link href="css/style.css" rel="stylesheet">
	<link href="css/style_self.css" rel="stylesheet">
	<link href="css/style-responsive.css" rel="stylesheet">
	<script src="js/jquery.js" ></script><!-- BASIC JS LIABRARY -->
	<script src="js/bootstrap.min.js" ></script><!-- BOOTSTRAP JS  -->
	<script type="text/javascript" src="js/style_self.js"></script> 
	<link rel="stylesheet" href="css/supersized.css"></link>
	<script src="js/supersized.3.2.7.min.js"></script>
    <script src="js/supersized-init.js"></script>
    <script src="js/md5-min.js"></script>
	<style type="text/css">
		.zdy{	
			position: relative;
			font-size: 12px;
			height: auto;
			padding: 10px;
			-webkit-box-sizing: border-box;
			-moz-box-sizing: border-box;
			box-sizing: border-box;width:145px;color:#555;
			margin-right: 32px;
		}
		.form-signin input[type="text"],.form-signin input[type="password"] {
			border: 2px solid #999;
		}
	</style>
<script type="text/javascript">
	function randomcode_refresh(){
		var img= document.getElementById('img');
		img.src="${ctx}/validatecode.jsp?id="+Math.random();;
	}
	
	function checkCode(){
		var check=false;
		var randomcode = document.getElementById("randomcode").value;
		var param = "randomcode="+randomcode;
		     $.ajaxSetup({ //设置$.post为同步执行，否则会出现执行顺序问题
		    	async : false 
		     });
		     $.post(//要设置该地址不被shiro拦截
				"${ctx}/checkCode.action",
				param,
				function(data) {
					if("true"==data.result){
						check=true;
					}else{
						wzj.alert('温馨提示',"验证码错误！");
						randomcode_refresh();
					}
			},"json"); 
		return check;
	}
// 	判断是否审核
		function checkAudit(){
		var Audit=false;
		var username = document.getElementById("username").value;
		var param = "username="+username;
		     $.ajaxSetup({ 
		    	async : false 
		     });
		     $.post(
				"${ctx}/checkAudit.action",
				param,
				function(data) {
					if("true"==data.result){
						Audit=true;
					}else{
						wzj.alert('温馨提示',"该用户不存在或暂未审核！");
					}
			},"json"); 
		return Audit;
	}
	
	function loginsubmit(){
		var username = document.getElementById("username").value;
		var randomcode = document.getElementById("randomcode").value;
		var password = document.getElementById("pwd").value;
		var rememberMe = document.getElementById("rememberMe").checked;
		if (username == "") {
			wzj.alert('温馨提示',"用户名不能为空");
			return false;
		}
		if (password == "") {
			wzj.alert('温馨提示',"密码不能为空");
			return false;
		}
		if (randomcode == "") {
			wzj.alert('温馨提示',"请输入验证码");
			return false;
		}
		if(checkCode() && checkAudit()){
			password=hex_md5(password);
			password=hex_md5(password+randomcode.toLowerCase());
			var param = "username=" + username + "&password=" + password
			+"&rememberMe=" + rememberMe +"&randomcode="+randomcode;
			$.post(//实用shiro框架时，username,password,login,post都是固定的ctx   +"&dept="+$("#idt option:selected").text()
				"${ctx}/login.action",
				param,
				function(data) {
					console.log(data);
					 if (data.toString().indexOf("未知错误")!=-1) {
						 wzj.alert('温馨提示',"用户名或密码错误");
						 randomcode_refresh();
						 document.getElementById("pwd").value = '';
						 document.getElementById("username").value = '';
					}else{
						location.href = '${ctx}/first.action';
					}
			})
		} 
	}
			
	 $(function() {
		 $(window).keydown(function(event){
			if (event.keyCode == 13) {
				loginsubmit();
			}
		});
		$("#signIn").click(function(){
			loginsubmit();
		});//提交
});//$
</script>
  </head>
  <body class="login-screen">
    <!-- BEGIN SECTION -->
    <div class="container">
    <h2 class="form-signin-heading" style="font-weight:bold;color:white;text-align:center;margin-top:20%">
         	商品条码印刷资格认定管理系统
        </h2>
      <form class="form-signin" style="margin:30px auto;">
        
		<!-- LOGIN WRAPPER  -->
        <div class="login-wrap"  style="left:50%;top:50%">
          <input type="text" class="form-control" placeholder="用户名" autofocus id="username" name="username">
          <input type="password" class="form-control" placeholder="密码" id="pwd" name="password">
          <input type="text" id="randomcode" class="zdy" name="randomcode" placeholder="验证码">
		<a href="#" title="点击刷新">
			<img src="${ctx}/validatecode.jsp" id="img" alt="" width="105" 
			height="35" position="relative;" margin="0px;200px;"  onClick="javascript:randomcode_refresh()"></img> </a>
          <label class="checkbox">
            <input type="checkbox" name="rememberMe" id="rememberMe">
           		 记住我
         <!--    <span class="pull-right">
              <a data-toggle="modal" href="#myModal">
                	忘记密码？
              </a>
            </span> -->
          </label>
          <button class="btn btn-lg btn-login btn-block" type="button" id="signIn" >
            	登&nbsp;&nbsp;&nbsp;录
          </button>
        </div>
		
		<!-- END MODAL -->
      </form>
    </div>
  </body> 
</html>

 
 