<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="myshiro" uri="/customize-tags" %>
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品条码印刷资格复认申请表</title>
	<link href="${ctx}/assets/morris.js-0.4.3/morris.css" rel="stylesheet"><!-- MORRIS CHART CSS -->
	<link href="${ctx}/css/clndr.css" rel="stylesheet"><!-- CALENDER CSS -->
	<link rel="stylesheet" type="text/css" href="assets/bootstrap-datepicker/css/datepicker.css">
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
	<script src="assets/bootstrap-datepicker/js/bootstrap-datepicker.js"></script><!-- 日期显示 -->
	<script src="js/advanced-form-components.js" ></script> 
<script type="text/javascript">
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
	<section id="container">
		<header class="header white-bg">
			<div class="sidebar-toggle-box">
			  <div  data-placement="right" class="fa fa-bars tooltips">
			  </div>
			</div>
			<a href="index.html" class="logo">
	          <!--  -->
	          <span>商品条码印刷资格认定管理系统</span>
            </a>
            <nav class="nav notify-row" id="top_menu">
             	 <ul class="nav top-menu">
             	 	<li class="dropdown">
             	 		<a data-toggle="dropdown" class="dropdown-toggle" href="#">
			                <i class="fa fa-tasks">
			                </i>
			                <span class="badge bg-success">
			                  6
			                </span>
			             </a>
			              <ul class="dropdown-menu extended tasks-bar">
			                <li class="notify-arrow notify-arrow-blue">
			                </li>
			                <li>
			                  <p class="blue">
			                    You have 6 pending tasks
			                  </p>
			                </li>
			                 <li>
                  <a href="#">
                    <div class="task-info">
                      <div class="desc">
                        Dashboard v1.3
                      </div>
                      <div class="percent">
                        40%
                      </div>
                    </div>
                    <div class="progress progress-striped">
                      <div class="progress-bar progress-bar-success set-40" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" >
                        <span class="sr-only">
                          40% Complete (success)
                        </span>
                      </div>
                    </div>
                  </a>
                </li>
                <li class="external">
                  <a href="#">
                    See All Tasks
                  </a>
                </li>
			          </ul>
             	 	</li>
             	 	<li id="header_inbox_bar" class="dropdown">
             	 		<a data-toggle="dropdown" class="dropdown-toggle" href="#">
			                <i class="fa fa-envelope-o">
			                </i>
			                <span class="badge bg-important">
			                  5
			                </span>
			             </a>
			             <ul class="dropdown-menu extended inbox">
			             	<li class="notify-arrow notify-arrow-blue">
				            </li>
				            <li>
			                  <p class="blue">
			                    You have 5 new messages
			                  </p>
				            </li>
				            <li>
                  <a href="#">
                    <span class="photo">
                      <img alt="avatar" src="./img/avatar-mini.jpg">
                    </span>
                    <span class="subject">
                      <span class="from">
                        Chintan Pandya
                      </span>
                      <span class="time">
                        Just now
                      </span>
                    </span>
                    <span class="message">
                      Hello, this is an example msg.
                    </span>
                  </a>
                </li>
                <li>
                  <a href="#">
                    See all messages
                  </a>
                </li>
			             </ul>
             	 	</li>
             	 	 <li id="header_notification_bar" class="dropdown">
		              <a data-toggle="dropdown" class="dropdown-toggle" href="#">
		                <i class="fa fa-bell-o">
		                </i>
		                <span class="badge bg-warning">
		                  7
		                </span>
		              </a>
              		  <ul class="dropdown-menu extended notification">
              		  	 <li class="notify-arrow notify-arrow-blue">
			                </li>
			                <li>
			                  <p class="blue">
			                    You have 7 new notifications
			                  </p>
                			</li>
                			<li>
			                  <a href="#">
			                    <span class="label label-danger">
			                      <i class="fa fa-bolt">
			                      </i>
			                    </span>
			                    Server #3 overloaded.
			                    <span class="small italic">
			                      34 mins
			                    </span>
			                  </a>
			                </li>
			                <li>
			                  <a href="#">
			                    See all notifications
			                  </a>
			                </li>
              		  </ul>
              		</li>
             	 </ul>
            </nav>
            <div class="top-nav ">
            	 <ul class="nav pull-right top-menu">
            	 	<li>
		              <input type="text" class="form-control search" placeholder="Search">
		            </li>
		             <li class="dropdown">
		             	<a data-toggle="dropdown" class="dropdown-toggle" href="#">
			                <img alt="" src="img/avatar1_small.jpg">
			                <span class="username">
			                  ${activeUser.realname}
			                </span>
			                <b class="caret">
			                </b>
		                </a>
		                <ul class="dropdown-menu extended logout">
                <li class="log-arrow-up">
                </li>
                <li>
                  <a href="#">
                    <i class=" fa fa-suitcase">
                    </i>
                    Profile
                  </a>
                </li>
                <li>
                  <a href="${ctx}/sysUser/toEdit.action">
                    <i class="fa fa-cog">
                    </i>
                    	修改信息
                  </a>
                </li>
                <li>
                  <a href="javascript:logoutRedirect()">
                    <i class="fa fa-bell-o">
                    </i>
                   	 注销登录
                  </a>
                </li>
                <li>
                  <a href="javascript:logout()">
                    <i class="fa fa-key">
                    </i>
                    	退出登录
                  </a>
                </li>
              </ul>
		             </li>
            	 </ul>
            </div>
		</header>
		<aside>
			<div id="sidebar" class="nav-collapse" style="z-index:10">
          	<ul class="sidebar-menu" id="nav-accordion">
	            <li>
	              <a href="${ctx}/index.jsp">
	                <i class="fa fa-dashboard">
	                </i>
	                <span>Dashboard</span>
	              </a>
	            </li>
            <myshiro:hasAnyPermission name="qyyh,fzjg,zjyh,zxyh"> 
	            <li class="sub-menu">
	              <a href="javascript:;">
	                <i class="fa fa-laptop">
	                </i>
	                <span>用户管理</span>
	                <span class="label label-success span-sidebar">4</span>
	              </a>
	               <ul class="sub">
	               <shiro:hasPermission name="qyyh">
	               		<li>
		                  <a href="${ctx}/first.action">中心用户管理</a>
		                </li>
				   </shiro:hasPermission>	
				   <shiro:hasPermission name="fzjg">	               
		           <li>
                    <a href="#">分支机构用户管理</a>
                  </li>
                </shiro:hasPermission>
                 <shiro:hasPermission name="zjyh">
                <li>
                  <a href="#">专家管理</a>
                </li>
                </shiro:hasPermission>
                <shiro:hasPermission name="zxyh">
                <li>
                  <a href="${ctx}/toFirmMng.action">企业用户管理</a>
                </li>
                </shiro:hasPermission>
	               </ul>
	            </li>
	        </myshiro:hasAnyPermission>
            <shiro:hasPermission name="role">
            <li>
	             <a href="${ctx}/role/find.action">
	                <i class="fa fa-user"></i>
	                <span>角色管理</span>
		         </a>
	        </li>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="pms">
            <li>
              <a href="${ctx}/permission/toPermissionsJSP.action">
                <i class="fa fa-key"></i>
                <span>权限管理</span>
              </a>
            </li>
            </shiro:hasPermission>
            <li class="sub-menu">
              <a href="">
                <i class="fa fa-sitemap"></i>
                <span>分支机构管理</span>
              	<span class="label label-primary span-sidebar">3</span>
              </a>
              <ul class="sub">
               		<li>
	                  <a href="#">分支机构信息管理</a>
	                </li>
	                <li>
	                  <a href="#">设备信息管理</a>
	                </li>
	                <li>
	                  <a href="#">人员信息管理</a>
	                </li>
			 </ul>
            </li>
            <li class="sub-menu">
	              <a href="javascript:;" class="active">
	                <i class="fa fa-suitcase"></i>
	                <span>业务管理</span>
	                <span class="label label-info span-sidebar">4</span>
	              </a>
	          <ul class="sub">
                <li>
                  <a href="${ctx}/application_form2.jsp">新申请业务办理</a>
                </li>
		        <li>
                   <a href="#">复认业务办理</a>
                </li>
                <li>
                  <a href="${ctx}/ifm_change2.jsp">变更业务办理</a>
                </li>
                <li>
                  <a href="${ctx}/review_form2.jsp">评审业务办理</a>
                </li>
                <li>
                  <a href="">业务查询与统计</a>
                </li>
                 <li>
                  <a href="${ctx}/reissue_application.jsp">企业补证申请</a>
                </li>
                <li>
                  <a href="${ctx}/reApplication_apply.jsp">复认申请表</a>
                </li>
                <li>
                  <a href="${ctx}/info_change.jsp">企业信息变更表</a>
                </li>
	         </ul>
	       </li>
           <shiro:hasRole name="系统管理员">
            <li>
                <a href="${ctx}/log/find.action">
	                <i class="fa fa-book"></i>
                	<span>查看日志</span>
             	</a>
            </li>
            </shiro:hasRole>
            <shiro:hasRole name="系统管理员">
            <li>
                <a href="${ctx}/dictionary/find.action">
	                <i class="fa fa-book"></i>
                	<span>数据字典</span>
             	</a>
            </li>
            </shiro:hasRole>
	        </ul>
	        </div>
		</aside>
		<section id="container" class="">
	    <!-- BEGIN HEADER --> 
         
         <section id="main-content">
		    <!-- BEGIN WRAPPER  -->
            <section class="wrapper site-min-height">
               <section class="panel">
                  <section class="wrapper">
			  <center style="margin-top:-70px;color:#2a8ba7">
			  		<h1>复认申请表</h1>
   		  	  </center>
			  <div class="Noprint">
			  <div class="btn-group pull-right" style="margin-top:-45px;margin-bottom:10px;">
                  <button class="btn btn-primary dropdown-toggle" data-toggle="dropdown">工具
                  <i class="fa fa-angle-down"></i>
                  </button>
                  <ul class="dropdown-menu pull-right">
                     <li><a href="javascript:printABC();">打印</a></li>
                     <li><a href="#">Save as PDF</a></li>
                     <li><a href="#">export to Excel</a></li>
                  </ul>
               </div>
               </div>
			   <!-- BEGIN ROW  -->
               <div class="row">
                  <div class="col-lg-12">
                     <section class="panel">
                        <header class="panel-heading">
                           <span class="label label-primary">企业信息</span>
                           <span class="tools pull-right" style="margin-top:-8px;">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
                           <form class="form-horizontal tasi-form" method="get">
                              <div class="form-group">
                                 <label class="col-sm-1 col-sm-1 control-label">企业名称（中文）</label>
                                 <div class="col-sm-6">
                                    <input type="text" class="form-control">
                                 </div>
								 <label class="col-sm-1 col-sm-1 control-label">原证书号</label>
                                 <div class="col-sm-4">
                                    <input type="text" class="form-control">
                                 </div>
                              </div>
                              <div class="form-group">
                                 <label class="col-sm-1 col-sm-1 control-label">企业名称（英文）</label>
                                 <div class="col-sm-6">
                                    <input type="text" class="form-control">
                                 </div>
								 <label class="col-sm-1 col-sm-1 control-label">证书有效期</label>
                                 <div class="col-sm-4">
                                    <input type="text" class="form-control">
                                 </div>
                              </div>
                              <div class="form-group">
                                 <label class="col-sm-1 col-sm-1 control-label">注册地址</label>
                                 <div class="col-sm-6">
                                    <input type="text" class="form-control">
                                 </div>
								 <label class="col-sm-1 col-sm-1 control-label">邮政编码</label>
                                 <div class="col-sm-4">
                                    <input type="text" class="form-control">
                                 </div>
                              </div>
                              <div class="form-group">
                                 <label class="col-sm-1 col-sm-1 control-label">通讯地址</label>
                                 <div class="col-sm-6">
                                    <input type="text" class="form-control">
                                 </div>
								 <label class="col-sm-1 col-sm-1 control-label">传真</label>
                                 <div class="col-sm-4">
                                    <input type="text" class="form-control">
                                 </div>
                              </div>
							  <div class="form-group">
                                 <label class="col-sm-1 col-sm-1 control-label">法人代表</label>
                                 <div class="col-sm-6">
                                    <input type="text" class="form-control">
                                 </div>
								 <label class="col-sm-1 col-sm-1 control-label">联系电话</label>
                                 <div class="col-sm-4">
                                    <input type="text" class="form-control">
                                 </div>
                              </div>
							  <div class="form-group">
                                 <label class="col-sm-1 col-sm-1 control-label">联系人</label>
                                 <div class="col-sm-3">
                                    <input type="text" class="form-control">
                                 </div>
								 <label class="col-sm-1 col-sm-1 control-label">固定电话</label>
                                 <div class="col-sm-3">
                                    <input type="text" class="form-control">
                                 </div>
								 <label class="col-sm-1 col-sm-1 control-label">手机号</label>
                                 <div class="col-sm-3">
                                    <input type="text" class="form-control">
                                 </div>
                              </div>
                              <div class="form-group">
                                 <label class="col-sm-1 col-sm-1 control-label">企业网址</label>
                                 <div class="col-sm-3">
                                    <input type="text" class="form-control">
                                 </div>
								 <label class="col-sm-1 col-sm-1 control-label">E-mail</label>
                                 <div class="col-sm-3">
                                    <input type="text" class="form-control">
                                 </div>
                                 <label class="col-sm-1 col-sm-1 control-label">企业性质</label>
                                 <div class="col-sm-3">
                                    <select class="form-control">
                                    	<option value="国有">国有</option>
                                    	<option value="集体">集体</option>
                                    	<option value="私营">私营</option>
                                    	<option value="有限责任公司">有限责任公司</option>
                                    	<option value="股份有限公司">股份有限公司</option>
                                    	<option value="股份合作">股份合作</option>
                                    	<option value="联营企业">联营企业</option>
                                    	<option value="中外合资合作企业（港澳台）">中外合资合作企业（港澳台）</option>
                                    	<option value="外商独资投资企业（港澳台）">外商独资投资企业（港澳台）</option>
                                    	<option value="其他企业">其他企业</option>
                                    </select>
                                 </div>
                              </div>
                           </form>
                        </div>
                     </section>
                  </div>
               </div>
			   <!-- END ROW  -->
			  <div class="row">
                  <div class="col-lg-12">
                     <section class="panel">
                        <header class="panel-heading">
                           <span class="label label-primary">经营业务</span>
                           <span class="tools pull-right" style="margin-top:-8px;">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
                           <form class="form-horizontal tasi-form" method="get">
                              <div class="form-group">
                                 <label class="col-sm-1 col-sm-1 control-label">主营</label>
                                 <div class="col-sm-11">
                                    <input type="text" class="form-control">
                                 </div>
                              </div>
                              <div class="form-group">
                                 <label class="col-sm-1 col-sm-1 control-label">兼营</label>
                                 <div class="col-sm-11">
                                    <input type="text" class="form-control">
                                 </div>
                              </div>							 
                           </form>
                        </div>
                     </section>
                  </div>
               </div>
			   <div class="row">
                  <div class="col-lg-12">
                     <section class="panel">
                        <header class="panel-heading">
                           <span class="label label-primary">企业人员情况</span>
                           <span class="tools pull-right" style="margin-top:-8px;">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
                           <form class="form-horizontal tasi-form" method="get">
                              <div class="form-group">
                                 <label class="col-sm-1 control-label">职工总数</label>
                                 <div class="col-sm-5">
                                    <input type="text" class="form-control">
                                 </div>
								 <label class="col-sm-1 control-label">技术人员数量</label>
                                 <div class="col-sm-5">
                                    <input type="text" class="form-control">
                                 </div>
                              </div>
                              <div class="form-group">
                                 <label class="col-sm-1 control-label">条码印刷技术负责人</label>
                                 <div class="col-sm-3">
                                    <input type="text" class="form-control">
                                 </div>
								 <label class="col-sm-1 control-label">职务</label>
                                 <div class="col-sm-3">
                                    <input type="text" class="form-control">
                                 </div>
								 <label class="col-sm-1 control-label">职称</label>
                                 <div class="col-sm-3">
                                    <input type="text" class="form-control">
                                 </div>
                              </div>							 
                           </form>
                        </div>
                     </section>
                  </div>
               </div>
			   <div class="row">
                  <div class="col-lg-12">
                     <section class="panel">
                        <header class="panel-heading">
                           <span class="label label-primary">条码印刷方式</span>
                           <span class="tools pull-right" style="margin-top:-8px;">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
                           <form class="form-horizontal tasi-form" method="get">
                              <div class="form-group">
                                 <div class="col-sm-12">
								 <label class="col-sm-2 checkbox-inline"></label>
                                    <label class="col-sm-2 checkbox-inline">
                                    <input type="checkbox" id="inlineCheckbox1" value="平板胶印">平板胶印
                                    </label>
                                    <label class="col-sm-2 checkbox-inline">
                                    <input type="checkbox" id="inlineCheckbox2" value="凹版印刷">凹版印刷
                                    </label>
                                    <label class="col-sm-2 checkbox-inline">
                                    <input type="checkbox" id="inlineCheckbox3" value="丝网印刷">丝网印刷
                                    </label>
									<label class="col-sm-2 checkbox-inline">
                                    <input type="checkbox" id="inlineCheckbox4" value="柔性版印刷">柔性版印刷
                                    </label>
                                 </div>
                              </div>
                              <div class="form-group">
                                 <label class="col-sm-2 control-label">其他（简述）</label>
                                 <div class="col-sm-10">
                                    <input type="text" class="form-control">
                                 </div>
                              </div>							 
                           </form>
                        </div>
                     </section>
                  </div>
               </div>
			   <div class="row">
                  <div class="col-lg-12">
                     <section class="panel">
                        <header class="panel-heading">
                           <span class="label label-primary">条码印刷载体</span>
                           <span class="tools pull-right" style="margin-top:-8px;">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
                           <form class="form-horizontal tasi-form" method="get">
                              <div class="form-group">
                                 <div class="col-sm-12">
									<label class="col-sm-2"></label>
                                    <label class="col-sm-2">
                                    <input type="checkbox" id="inlineCheckbox1" style="margin-right:4px;" value="纸质">&nbsp;纸质
                                    </label>
                                    <label class="col-sm-2">
                                    <input type="checkbox" id="inlineCheckbox2" style="margin-right:4px;" value="不干胶">不干胶
                                    </label>
                                    <label class="col-sm-2">
                                    <input type="checkbox" id="inlineCheckbox3" style="margin-right:4px;" value="瓦楞纸">瓦楞纸
                                    </label>
									<label class="col-sm-2">
                                    <input type="checkbox" id="inlineCheckbox4" style="margin-right:4px;" value="金属">金属
                                    </label>
									<label class="col-sm-2">
                                    <input type="checkbox" id="inlineCheckbox5" style="margin-right:4px;" value="塑料">塑料
                                    </label>
                                 </div>
                              </div>
                              <div class="form-group">
                                 <label class="col-sm-2 control-label">其他（简述）</label>
                                 <div class="col-sm-10">
                                    <input type="text" class="form-control">
                                 </div>
                              </div>							 
                           </form>
                        </div>
                     </section>
                  </div>
               </div>
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
                           <form class="form-horizontal tasi-form" method="get">
						   <div class="form-group">
                             <label class="col-lg-1 control-label">名称</label>
                             <div class="col-lg-5">
                                  <input type="text" class="form-control">
                             </div>
                             <label class="col-lg-1 control-label">型号</label>
                             <div class="col-lg-5">
                                  <input type="text" class="form-control">
                             </div>  
                           </div> 
                           <div class="form-group">
                             <label class="col-lg-1 control-label">产地</label>
                             <div class="col-lg-5">
                                  <input type="text" class="form-control">
                             </div>
                             <label class="col-lg-1 control-label">使用年限</label>
                             <div class="col-lg-5">
                                  <input type="text" class="form-control">
                             </div>  
                           </div> 						 
                           </form>
                        </div>
                     </section>
                  </div>
               </div>
			   <div class="row">
                  <div class="col-lg-12">
                     <section class="panel">
                        <header class="panel-heading">
                           <span class="label label-primary">检测设备</span>
                           <span class="tools pull-right" style="margin-top:-8px;">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
                           <form class="form-horizontal tasi-form" method="get">
						    <div class="form-group">
                             <label class="col-lg-1 control-label">产地</label>
                             <div class="col-lg-5">
                                  <input type="text" class="form-control">
                             </div>
                             <label class="col-lg-1 control-label">使用年限</label>
                             <div class="col-lg-5">
                                  <input type="text" class="form-control">
                             </div>  
                           </div> 						 
                           </form>
                        </div>
                     </section>
                  </div>
               </div>
               <div class="row">
                  <div class="col-lg-12">
                     <section class="panel">
                        <header class="panel-heading">
                           <span class="label label-primary">分支机构初审</span>
                           <span class="tools pull-right" style="margin-top:-8px;">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
                           <form class="form-horizontal tasi-form" method="get">
						    <div class="form-group">
                             <label class="col-lg-1 control-label">负责人</label>
                             <div class="col-lg-5">
                                  <input type="text" class="form-control">
                             </div>
                             <label class="col-lg-1 control-label">审核日期</label>
                             <div class="col-lg-5">
                                  <div data-date-viewmode="years" style="width:80%" data-date-format="yyyy-mm-dd" data-date="2012-02-12" class="input-append date dpYears">
							            <input type="text" readonly="" id="birth" placeholder="日期" size="16" class="form-control">
							          	<span class="input-group-btn add-on" style="margin-top: -38px;z-index:10;">
							            <button class="btn btn-info" type="button">
							            <i class="fa fa-calendar" style="bg-color:41CAC0;"></i>
							            </button>
							          </span>
							        </div>
                             </div>  
                           </div> 						 
                           </form>
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
                           <form class="form-horizontal tasi-form" method="get">
						   <div class="form-group">
                             <div class="col-lg-1"></div>
                             <div class="col-lg-10">
                                  <textarea class="form-control" rows="2" id="ccomment" name="comment"></textarea>
                             </div> 
                           </div> 						 
                           </form>
                        </div>
                     </section>
                  </div>
               </div>
			   
            </section>
			<!-- END WRAPPER  -->
        
		 <!-- END MAIN CONTENT -->
               </section>
            </section>
			<!-- END WRAPPER  -->
         </section>
        </section>
	    <!-- END SECTION -->
		
		<footer class="site-footer">
        <div class="text-center">
           版权所有：中国物品编码中心|京ICP备11036137号-10
          <a href="" target="_blank">技术支持：海丰通航</a>
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
	<script src="${ctx}/js/jquery.dcjqaccordion.2.7.js"></script><!-- ACCORDING JS -->
	<script src="${ctx}/js/jquery.scrollTo.min.js" ></script><!-- SCROLLTO JS  -->
	<script src="${ctx}/js/jquery.nicescroll.js" > </script><!-- NICESCROLL JS  -->
	<script src="${ctx}/assets/data-tables/jquery.dataTables.js"></script><!-- DATATABLE JS  -->
	<script src="${ctx}/assets/data-tables/DT_bootstrap.js"></script><!-- DATATABLE JS  -->
	<script src="${ctx}/js/respond.min.js" ></script><!-- RESPOND JS  -->
	<script src="${ctx}/js/common-scripts.js" ></script><!-- BASIC COMMON JS  -->
	<script src="${ctx}/js/editable-table-user.js" ></script><!-- EDITABLE TABLE JS  -->
     <!-- EDITABLE TABLE FUNCTION  -->
     <script>
        jQuery(document).ready(function() {
            EditableTable.init();
        });
     </script>
  <!-- END JS --> 
    <!-- END JS -->
</body>
</html>