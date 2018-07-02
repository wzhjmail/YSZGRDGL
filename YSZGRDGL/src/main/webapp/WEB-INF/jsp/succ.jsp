<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<%@ taglib prefix="myshiro" uri="/customize-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="overflow-x: hidden; height:100%">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品条码印刷资格认定管理系统</title>
    <link rel="icon" href="${ctx}/img/header.ico" type="image/x-icon"/>  
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<link href="${ctx}/assets/morris.js-0.4.3/morris.css" rel="stylesheet"><!-- MORRIS CHART CSS -->
	<link href="${ctx}/css/clndr.css" rel="stylesheet"><!-- CALENDER CSS -->
	<!-- -->
    <link href="${ctx}/css/bootstrap.min.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="${ctx}/css/bootstrap-reset.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="${ctx}/assets/font-awesome/css/font-awesome.css" rel="stylesheet"><!-- FONT AWESOME ICON STYLESHEET -->
    <link href="${ctx}/assets/data-tables/DT_bootstrap.css" rel="stylesheet" ><!-- DATATABLE CSS -->
    <link href="${ctx}/css/style.css" rel="stylesheet"><!-- THEME BASIC CSS -->
    <link href="${ctx}/css/style-responsive.css" rel="stylesheet"><!-- THEME BASIC RESPONSIVE  CSS -->
    <link href="${ctx}/css/style_self.css" rel="stylesheet">
<script src="${ctx}/js/jquery-1.8.3.min.js" ></script><!-- BASIC JS LIABRARY 1.8.3 -->
<script src="${ctx}/js/style_self.js" ></script>
<style type="text/css">
	img{
		margin-right: 5px
	}
</style>
<script type="text/javascript">
$(function(){
	$.post(
	"${ctx}/showMenu.action",
	function(data){
			for(var i=0;i<data.length;i++){
				result='<li class="sub-menu" name="menu"><a acc="a"  href="#content" id="menu'+data[i][0].id+'" value="${ctx}'+data[i][0].url+'" onclick="show(this)">'+
                		'<img alt="" src="img/icons/'+data[i][0].name+'.svg">'+
                		'<span>'+data[i][0].name+'</span><span class="label label-success span-sidebar">'+(data[i].length-1)+'</span></a>';
                if(data[i].length>1){
                	result = result + '<ul class="sub" id="sub'+data[i][0].id+'">'
					for(var j=1;j<data[i].length;j++){
						result = result + '<li acc="b" class="sub-menu a'+data[i][j].length+'" name="menu" value="${ctx}'+data[i][j][0].url+'" onclick="show(this)"><a href="#content" id="'+data[i][j][0].percode+'">'+data[i][j][0].name+'<span class="label label-danger span-sidebar">'+(data[i][j].length-1)+'</span></a>'
						if(data[i][j].length>1){
							result = result + '<ul class="sub" id="sub'+data[i][j][0].id+'">'
							for(var m=1;m<data[i][j].length;m++){
								result = result + '<li acc="c" class="sub-menu" id="'+i+j+m+'" name="menu" onclick="show(this)" value="${ctx}'+data[i][j][m].url+'"><a href="#content" >'+data[i][j][m].name+'</a></li>'
							}
							result = result + '</ul></li>'
						}
					}
                	result = result + '</ul></li>'
                }
				$('#nav-accordion').append(result)
			}
			if($('#sub64 li:first')[0]!=undefined){
				show($('#sub64 li:first')[0])
			}
			var label = $('.label')
			for(var i=0;i<label.length;i++){
				if(label[i].innerHTML=="0"){
					$('.label:eq('+i+')').hide()
				}
			}
			$.getScript("${ctx}/js/jquery.dcjqaccordion.2.7.js",function(){ 
				$.getScript("${ctx}/js/common-scripts.js",function(){ //加载test.js,成功后，并执行回调函数
// 					$.getScript("${ctx}/js/refresh1.js",function(){ //加载test.js,成功后，并执行回调函数
// 				  		refresh()
// 					});
				});
			});
		}
	);
	var roled="";
	var ID=$("#userId").val();
	$.ajaxSetup({async : false});
	$.post(//获取用户的角色
		"${pageContext.request.contextPath}/role/findRoleByUserId.action",
		"userId="+ID,
		function(data){
			roled=data.name;
		}
	);
	$.post(//获取所有的角色，并显示用户的当前角色
		"${pageContext.request.contextPath}/role/findAll.action",
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
		"${pageContext.request.contextPath}/sysUser/findUserById.action",
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
		/* var param = "usercode="+username+"&username="+name+
			"&password="+pwd1+"&role="+$('#role option:selected').attr("id")+"&id="+$("#userId").val();//选中的值 */
			var param = "usercode="+username+"&username="+name+
			"&password="+pwd1+"&id="+$("#userId").val();//选中的值
			$.ajaxSetup({async : false});
			$.post(
			"${pageContext.request.contextPath}/sysUser/update.action",
			param,
			function(data){
				if(data.toString()>0){
					$("#update").attr('data-dismiss','modal')
					wzj.alert('温馨提示',"修改成功！");
					//location.href = '${pageContext.request.contextPath}/first.action';
				}else{
					wzj.alert('温馨提示',"修改失败！");
				}
			}
		);
	});//提交*/
})
	function exportRecord(){
		window.open('${ctx}/sysUser/exportRecord.action');	
	}
	//退出
	function logout(){
		wzj.confirm('温馨提醒','您确定要退出本系统吗?',function(flag){
			if(flag){
				//location.href='${ctx}/logouts.action';
				location.href='${ctx}/logout.action';
			}
		});
	}
	//注销
	function logoutRedirect(){
		/* $.post(
			"${ctx}/logout.action",
			function(data){
				location.href="${ctx}/toLoginJSP.action"
			} 
		); */
		location.href="${ctx}/logouts.action"
	}
// 展开右侧内容	
function show(select){
	var acc = $(select).attr('acc')
	var url;
	if(select.getAttribute("value")!="/YSZGRDGLnull"){
		$('.active').removeClass("active")
		url=select.getAttribute("value");
		if(acc=='a'){
			$(select).addClass("active")
		}else if(acc=='b'){
			$(select).addClass("active")
			$(select).parent().parent().find('a:first').addClass("active")
		}else if(acc=='c'){
			$(select).addClass("active")
			$(select).parent().parent().find('a:first').addClass("active")
			$(select).parent().parent().parent().parent().find('a:first').addClass("active")
		}
	}
	$("#content").attr("src",url);
	setInterval(function(){
		if(window.frames[0].location.href.indexOf("login.action") != -1){
			location.href="${ctx}/login.action"
		}
	},20)
}

</script>
</head>
<body style="height:100%;overflow-x: hidden">
	<section id="container" class="">
		<header class="header white-bg">
		<div class="container" style="width: 100%;padding-left: 0px;padding-right: 0px">
			<div class="sidebar-toggle-box">
			  <div  data-placement="right" class="fa fa-bars tooltips">
			  </div>
			</div>
			<a href="first.action" class="logo">
	        	<!--  -->
	          <span>商品条码印刷资格认定管理系统</span>
            </a>
            <div class="top-nav ">
            	 <ul class="nav pull-right top-menu">
            	 	<li>
		           <!--    <input type="text" class="form-control search" placeholder="查找"> -->
		            </li>
		             <li class="dropdown" style="margin-left: 0px;width: 100%">
		             	<a data-toggle="dropdown" class="dropdown-toggle" href="#">
			                <img alt="" src="img/icons/中心管理人员.svg">
			                <span class="username" id="rName">
			                  ${activeUser.realname}
			                </span>
			                <b class="caret">
			                </b>
		                </a>
		      <ul class="dropdown-menu extended logout">
                <li class="log-arrow-up">
                </li>
            <!--     <li>
                  <a href="#">
                    <i class=" fa fa-suitcase">
                    </i>
                    Profile
                  </a>
                </li> -->
                <li>
                  <a href="#Modal_show" data-toggle='modal'>
                    <i class="fa fa-cog">
                    </i>修改信息
                  </a>
                </li>
                <li>
                  <a href="javascript:logoutRedirect()">
                    <i class="fa  fa-unlock">
                    </i>注销登录
                  </a>
                </li>
                <li>
                  <a href="javascript:logout()">
                    <i class="fa  fa-sign-out">
                    </i>退出登录
                  </a>
                </li>
              </ul>
		             </li>
            	 </ul>
            </div>
            </div>
		</header>
		
		<aside>
	        <div id="sidebar" class="nav-collapse" style="z-index:10">
          	<ul class="sidebar-menu" id="nav-accordion">
          
	        </ul>
	        </div>
		</aside>
		<section id="container" class="">
	    <!-- BEGIN HEADER --> 
         
         <section id="main-content">
		    <!-- BEGIN WRAPPER  -->
            <section class="wrapper site-min-height">
                  <iframe id="content" src="" frameborder="0" width="100%" leftmargin="0" topmargin="0" style="min-height: 100%"></iframe>
            </section>
			<!-- END WRAPPER  -->
         </section>
        </section>
	    <!-- END SECTION -->
		<div id="Modal_show" class="modal fade">
	          <div class="modal-dialog">
	            <div class="modal-content" style="width:60%;margin:0 auto">
	              <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
	                  &times;
	                </button>
	                <h4 class="modal-title">修改信息</h4>
	              </div>
<!-- 	              <div id="media" class="modal-body" style="height: 550px"> -->
	              		<form class="form-signin" style="max-width: unset;margin: unset">
			<!-- LOGIN WRAPPER  -->
			<div class="login-wrap">
				<input type="hidden"  name="id" id="userId" value="${activeUser.userid}">
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
				<!-- 注册页面的角色申请该不该要？
				分配角色<select id="role" name="role" class="form-control" disabled="disabled"
					style="height: 37px; font-size: 12px; background-color: #eee; margin-bottom: 15px;">
				</select>
				出生日期
				<div data-date-viewmode="years" style="width: 90%" data-date="2012-12-12"
					data-date-format="yyyy-mm-dd" class="input-append date dpYears">
					<input type="text" readonly="readonly" id="birth" size="16" class="form-control">
					 <span class="input-group-btn add-on" style="margin-top:-57px;z-index:10;">
						<button class="btn btn-info" type="button" style="height: 39px;width: 40px;margin-left: -8px">
							<i class="fa fa-calendar" style="bg-color: 41CAC0;"> </i>
						</button>
					</span>
				</div> -->
				<button class="btn btn-lg btn-login btn-block btn-success" type="button"
					id="update">提交</button>
			</div>
		</form>
	              </div>
	            </div>
	          </div>
		<footer class="site-footer">
        <div class="text-center">
           版权所有：中国物品编码中心|
          <a href="" target="_blank">
            	技术支持：海丰通航
          </a>
          <a href="#" class="go-top">
            <i class="fa fa-angle-up">
            </i>
          </a>
        </div>
      </footer>
	</section>
	 <!-- BEGIN JS -->
	 <!--datatable的汉化在assets/data-tables/jquery.dataTables.js中修改 -->
    <script src="${ctx}/js/jquery.sparkline.js"></script><!-- SPARKLINE JS -->
    <script src="${ctx}/js/sparkline-chart.js"></script><!-- SPARKLINE CHART JS -->
    <script src="${ctx}/js/count.js"></script><!-- COUNT JS -->
	<script src="${ctx}/js/bootstrap.min.js" ></script><!-- BOOTSTRAP JS  -->
	<script src="${ctx}/js/jquery.scrollTo.min.js" ></script><!-- SCROLLTO JS  -->
	<script src="${ctx}/js/jquery.nicescroll.js" > </script><!-- NICESCROLL JS  -->
	<script src="${ctx}/assets/data-tables/jquery.dataTables.js"></script><!-- DATATABLE JS  -->
	<script src="${ctx}/assets/data-tables/DT_bootstrap.js"></script><!-- DATATABLE JS  -->
	<script src="${ctx}/js/respond.min.js" ></script><!-- RESPOND JS  -->
	<script src="${ctx}/js/editable-table-user.js" ></script><!-- EDITABLE TABLE JS  -->
    <script src="${ctx}/js/refresh1.js" ></script>
  <!-- END JS --> 
</body>
</html>