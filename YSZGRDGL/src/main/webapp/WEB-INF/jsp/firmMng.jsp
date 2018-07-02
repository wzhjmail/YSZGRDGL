<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<%@ taglib prefix="myshiro" uri="/customize-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>企业用户管理</title>
	<link href="${ctx}/assets/morris.js-0.4.3/morris.css" rel="stylesheet"><!-- MORRIS CHART CSS -->
	<link href="${ctx}/css/clndr.css" rel="stylesheet"><!-- CALENDER CSS -->
	<!-- 查询页面 -->
    <link href="${ctx}/css/bootstrap.min.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="${ctx}/css/bootstrap-reset.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="${ctx}/assets/font-awesome/css/font-awesome.css" rel="stylesheet"><!-- FONT AWESOME ICON STYLESHEET -->
    <link href="${ctx}/assets/data-tables/DT_bootstrap.css" rel="stylesheet" ><!-- DATATABLE CSS -->
    <link href="${ctx}/css/style.css" rel="stylesheet"><!-- THEME BASIC CSS -->
    <link href="${ctx}/css/style_self.css" rel="stylesheet">
    <link href="${ctx}/css/style-responsive.css" rel="stylesheet"><!-- THEME BASIC RESPONSIVE  CSS -->
<script src="${ctx}/js/jquery-1.8.3.min.js" ></script><!-- BASIC JS LIABRARY 1.8.3 -->
<script src="${ctx}/js/style_self.js"></script>
<script type="text/javascript">
	$(function(){
		findAll();
	});//开始 
	function findAll(){
		$("#result").html("");
		$.ajaxSetup({async : false});
		$.post(
			"${ctx}/sysUser/findAll.action",
			function(data){
				if(data!=null){
					for(var i=0;i<data.length;i++){
						var roleId="";
						var roleName="";
						$.post(//查询角色名
							"${ctx}/role/findRoleByUserId.action",
							"userId="+data[i].id,
							function(dat){
								if(dat!=null){
									roleId=dat.id;
									roleName=dat.name;
								}
							}
						);
						var result="<tr class='' id='"+data[i].id+"'><td>"+data[i].usercode+"</td><td class=''>"
                            +data[i].username+"</td><td class=''>"
                            +data[i].dept+"</td><td class=''>"
                            +data[i].password+"</td><td class='' id='"+roleId+"'>"
                            +roleName+"</td>"+
                            "<td><a class='edit' href='javascript:'><span class='label label-success'>修改</span></a></td>"+
                            "<td><a class='delete' href='javascript:;'><span class='label label-danger'>删除</span></a></td></tr>";
						$("#result").append(result);
					} 
				}
		});
	}
	
	function exportRecord(){
		window.open('${ctx}/sysUser/exportRecord.action');	
	}
	
	function importRecord(){
		var file = $("#fileImport").get(0).files[0];
		var filePath = $("#fileImport").val();
       //判断控件中是否存在文件内容，如果不存在，弹出提示信息，阻止进一步操作
       if (file == null) { wzj.alert('温馨提醒',"文件不能为空，请选择文件"); return; }
       //获取文件名称
       var fileName = file.name;
       //获取文件类型名称
       var file_typename = fileName.substring(fileName.lastIndexOf('.'), fileName.length);
       //这里限定上传文件文件类型必须为.xlsx，如果文件类型不符，提示错误信息
		if(file_typename == '.xlsx'){
			//计算文件大小
			var fileSize=0;
			 //如果文件大小大于1024字节X1024字节，则显示文件大小单位为MB，否则为KB
			 if(file.size > 1024*1024){
				 fileSize = Math.round(file.size * 100 / (1024 * 1024)) / 100;
				 if (fileSize > 10) {
	                   alert('错误，文件超过10MB，禁止上传！'); return;
	               }
				fileSize = fileSize.toString() + 'MB';
			 }else{
           		fileSize = (Math.round(file.size * 100 / 1024) / 100).toString() + 'KB';
      	 	 }
			//获取form数据
		    var formData = new FormData(document.getElementById("importFileForm"));
			//调用apicontroller后台action方法，将form数据传递给后台处理。contentType必须设置为"multipart/form-data"
			$.ajax({
				 url: "${ctx}/sysUser/importFile",
	             type: 'post',
	             data: formData,
	             enctype: "multipart/form-data",
	             processData:false,
	             contentType:false,
	             success: function (result) {
	            	 if(result==1){
	            		 wzj.alert('温馨提醒',"上传成功");
		        	     //上传成功后将控件内容清空，并显示上传成功信息
		        		 $("#fileImport").val("");
		        		 //重新加载数据
		        		 
	            	 }else{
	            		 wzj.alert('温馨提醒',"上传成功，加载失败！");
	            	 }
	             },
	             error: function (returnInfo) {
	            	 wzj.alert('温馨提醒',"上传失败");
	             } 
	                
			 }); 
		}else{
			wzj.alert('温馨提醒',"文件类型错误,请选择.xlsx文件");
		}
	}
	
	function importCancel(){
		$("#fileImport").val("");
	}
	
</script>
</head>
<body>
	<section id="container" class="">
		<header class="header white-bg">
		<div class="container">
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
		              <input type="text" class="form-control search" placeholder="查找">
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
	           <%--  <li>
	                <a href="${ctx}/toLoginJSP.action">
		                <i class="fa fa-rocket"></i>
	                	<span>登录页面</span>
              		</a>
            	</li> --%>
            <myshiro:hasAnyPermission name="qyyh,fzjg,zjyh,zxyh"> 
	            <li class="sub-menu">
	              <a href="javascript:;" class="active">
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
                <li class="active">
                  <a href="#">企业用户管理</a>
                </li>
                </shiro:hasPermission>
	               </ul>
	            </li>
	        </myshiro:hasAnyPermission>
            <shiro:hasPermission name="role">
            <li class="sub-menu">
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
	              <a href="javascript:;">
	                <i class="fa fa-suitcase"></i>
	                <span>业务管理</span>
	                <span class="label label-info span-sidebar">4</span>
	              </a>
	          <ul class="sub">
                <li>
                  <a href="${ctx}/application_form2.jsp">新申请业务办理</a>
                </li>
		        <li>
                   <a href="${ctx}/reApplication_form2.jsp">复认业务办理</a>
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
                 
                  <div class="panel-body">
                     <div class="adv-table editable-table ">
                        <div class="clearfix">
                           <div class="btn-group">
                              <button id="editable-sample_new" class="btn btn-success green">
                              添加<i class="fa fa-plus"></i>
                              </button>
                           </div>
                         <!--   <div class="btn-group pull-right">
                              <button class="btn btn-primary dropdown-toggle" data-toggle="dropdown">下载<i class="fa fa-angle-down"></i>
                              </button>
                              <ul class="dropdown-menu pull-right">
                                 <li><a data-toggle="modal" href="#import-excel">上传</a></li>
                                 <li><a href="#">Save as PDF</a></li>
                                 <li><a href="javascript:exportRecord();">导出到Excel</a></li>
                              </ul>
                           </div> -->
                        </div>
                        <div class="space15"></div>
                        <table class="table table-striped table-hover table-bordered" id="editable-sample">
                           <thead>
                              <tr>
                                 <th class="">用户名</th>
                                 <th class="">真实姓名</th>
                                 <th class="">所属部门</th>
                                 <th class="">密码</th>
                                 <th class="" style="width:100px">角色</th>
                                 <th>修改</th>
                                 <th>删除</th>
                              </tr>
                           </thead>
                           <tbody id="result">
                              <!-- 循环显示查询出来的结果 -->
                             
                           </tbody>
                        </table>
                     </div>
                  </div>
               </section>
            </section>
			<!-- END WRAPPER  -->
         </section>
		 
        </section>
	    <!-- END SECTION -->

 <!-- 上传excel文档 -->
	<div  id="import-excel" class="modal fade">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="importCancel()">
                  &times;
                </button>
                <h4 class="modal-title">
                  	导入文件
                </h4>
              </div>
              <div class="modal-body">
               <form action="" id="importFileForm" method="post" enctype="multipart/form-data">
              	 请选择Excel格式的文件<input style="display:inline;margin-left:20px" type="file" id="fileImport" name="fileImport">
               </form>
              </div>
              <div class="modal-footer">
                <button data-dismiss="modal" class="btn btn-default" type="button" onclick="importCancel()">
                  	取消
                </button>
                <button data-dismiss="modal" class="btn btn-success" type="button" onclick="importRecord()">
                  	上传
                </button>
              </div>
            </div>
          </div>
        </div>
      
		
		<footer class="site-footer">
        <div class="text-center">
           版权所有：中国物品编码中心|京ICP备11036137号-10
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
</body>
</html>