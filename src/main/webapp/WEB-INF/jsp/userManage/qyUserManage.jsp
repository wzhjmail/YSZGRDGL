<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<%@ taglib prefix="myshiro" uri="/customize-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查询所有</title>
	<link href="${ctx}/assets/morris.js-0.4.3/morris.css" rel="stylesheet"><!-- MORRIS CHART CSS -->
	<link href="${ctx}/css/clndr.css" rel="stylesheet"><!-- CALENDER CSS -->
	<!-- 查询页面 -->
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
	.label{
		line-height: unset;
	}
</style>
<script type="text/javascript">
	$(function(){
		userManage();
	});//开始 
    function userManage(){
    	param="dept="+"企业用户";
    	$.ajaxSetup({async : false});
		$.post(
			"${ctx}/sysUser/findAll.action",
			param,
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
									if(dat.name==undefined){
										roleName="未分配";
									}else{
										roleName=dat.name;
									}
								}
							}
						);
						var result="<tr class='' id='"+data[i].id+"'><td>"+data[i].usercode+"</td><td class=''>"
                            +data[i].username+"</td><td class=''>"
 							+data[i].dept+"</td><td class=''>"
 							+data[i].password+"</td>"
 							if(data[i].locked==1){
 								result+="<td><a href='javascript:'><span class='label label-info' onclick='audit("+data[i].id+")'>待审核</span></a></td>"+
 								"<td class=''style='display:none'>"
 	                            +data[i].locked+"</td>"+
 								"<td class='' id='"+roleId+"'>"
 	                            +roleName+"</td>"+
 	                            "<td><a class='edit' href='javascript:'><span class='label label-success'>修改</span></a></td>"+
 	                            "<td><a class='delete' href='javascript:;'><span class='label label-danger'>删除</span></a></td></tr>";
 							}else{
 								result+="<td><a href='javascript:'><span class='label label-success'>已审核</span></a></td>"+
 								"<td class=''style='display:none'>"
 	                            +data[i].locked+"</td>"+
 								"<td class='' id='"+roleId+"'>"
 	                            +roleName+"</td>"+
 	                            "<td><a class='edit' href='javascript:'><span class='label label-success'>修改</span></a></td>"+
 	                            "<td><a class='delete' href='javascript:;'><span class='label label-danger'>删除</span></a></td></tr>";
 							}
						$("#result").append(result);
					} 
				}
		});
    }
	
	function audit(id){
		var param = "id="+id;
		$.post(
				"${ctx}/sysUser/audit.action",
				param,
				function(data){
					if(data==1){
						wzj.alert('温馨提示',"审核通过！");
						setTimeout(function(){
							window.location.reload();
							},2000);          
					}else{
						wzj.alert('温馨提示',"审核失败！");
					}
				}
			);
		
		}
	function exportRecord(){
		window.open('${ctx}/sysUser/exportRecord.action');	
	}
	function logout(){
		wzj.confirm('温馨提醒','您确定要退出本系统吗?',function(flag){
			if(flag){
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
               <section class="panel">
                 
                  <div class="panel-body">
                     <div class="adv-table editable-table ">
                        <div class="clearfix">
                           <div class="btn-group nopaddingleft">
                              <button id="editable-sample_new" class="btn btn-success green">
                              	添加<i class="fa fa-plus"></i>
                              </button>
                           </div>
                         <!--  <div class="btn-group pull-right">
                              <button class="btn btn-primary dropdown-toggle" data-toggle="dropdown">下载<i class="fa fa-angle-down"></i>
                              </button>
                              <ul class="dropdown-menu pull-right">
                                 <li><a href="#">Print</a></li>
                                 <li><a href="#">Save as PDF</a></li>
                                 <li><a href="javascript:exportRecord();">导出到Excel</a></li>
                              </ul>
                           </div>-->
                        </div>
                        <div class="space15"></div>
                        <table class="table table-striped table-hover table-bordered" id="editable-sample">
                           <thead>
                              <tr>
                                 <th class="">用户名</th>
                                 <th class="">真实姓名</th>
                                 <th class="">所属部门</th>
                                 <th class="">密码</th>
                                 <th class="">审核</th>
                                 <th class="" style="display: none">审核1</th>
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