<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<%@ taglib prefix="myshiro" uri="/customize-tags" %>
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>权限管理</title>
	<link href="${ctx}/assets/morris.js-0.4.3/morris.css" rel="stylesheet"><!-- MORRIS CHART CSS -->
	<link href="${ctx}/css/clndr.css" rel="stylesheet"><!-- CALENDER CSS -->
	<!-- 查询页面 -->
    <link href="${ctx}/css/bootstrap.min.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="${ctx}/css/bootstrap-reset.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="${ctx}/assets/font-awesome/css/font-awesome.css" rel="stylesheet"><!-- FONT AWESOME ICON STYLESHEET -->
    <link href="${ctx}/assets/data-tables/DT_bootstrap.css" rel="stylesheet"><!-- DATATABLE CSS -->
    <link href="${ctx}/css/style.css" rel="stylesheet"><!-- THEME BASIC CSS -->
    <link href="${ctx}/css/style_self.css" rel="stylesheet">
    <link href="${ctx}/css/style-responsive.css" rel="stylesheet"><!-- THEME BASIC RESPONSIVE  CSS -->
	<script src="${ctx}/js/jquery-1.8.3.min.js" ></script><!-- BASIC JS LIABRARY 1.8.3 -->
	<script src="${ctx}/js/style_self.js"></script>
	<link rel="stylesheet" href="${ctx}/css/validationEngine.jquery.css">
	<script src="${ctx}/js/jquery.validationEngine-zh_CN.js"></script>
	<script src="${ctx}/js/jquery.validationEngine.js"></script>
<style type="text/css">
	.label{
		line-height: unset;
	}
</style>
<script type="text/javascript">
	$(function(){
		findAll();
	});//开始 
	function findAll(){
		$.ajaxSetup({async : false});
		$.post(
			"${ctx}/permission/findAll.action",
			function(data){
				if(data!=null){
					for(var i=0;i<data.length;i++){
						var url=data[i].url;
						if(url==null){
							url="";
						}
						var name=data[i].name;
						if(name==null){
							name="";
						}
						var type=data[i].type;
						if(type==null){
							type="";
						}
						var percode=data[i].percode;
						if(percode==null){
							percode="";
						}
						var parentids=data[i].parentids;
						if(parentids==null){
							parentids="";
						}
						var sortstring=data[i].sortstring;
						if(sortstring==null){
							sortstring="";
						}
						var available=data[i].available;
						if(available==null){
							available="";
						}
						var result="<tr class='' ><td>"
						    +data[i].id+"</td><td class='' style='width:80px;'>"
                            +name+"</td><td class=''>"
                            +type+"</td><td class=''>"
						    +url+"</td><td class=''>"
                            +data[i].parentid+"</td><td class=''>"
                            +sortstring+"</td><td class=''>"
                            +available+"</td>"+
                            "<td><a class='edit' href='javascript:'><span class='label label-success'>修改</span></a></td>"+
                            "<td><a class='delete' href='javascript:;'><span class='label label-danger'>删除</span></a></td></tr>";
						$("#result").append(result);
					} 
				}
		});
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
                              	添加 <i class="fa fa-plus"></i>
                              </button>
                           </div>
                      <!--      <div class="btn-group pull-right">
                              <button class="btn btn-primary dropdown-toggle" data-toggle="dropdown">下载<i class="fa fa-angle-down"></i>
                              </button>
                              <ul class="dropdown-menu pull-right">
<!--                                  <li><a href="#">Print</a></li> 
<!--                                  <li><a href="#">Save as PDF</a></li> 
                                 <li><a href="javascript:exportRecord();">导出到Excel</a></li>
                              </ul>
                           </div> -->
                        </div>
                        <div class="space15"></div>
                        
                        <form id="info">
                        <table class="table table-striped table-hover table-bordered" id="editable-sample">
                           <thead>
                              <tr>
                                 <th class="">编号</th>
                                 <th class="">权限名称</th>
                                 <th class="">权限类型</th>
                                 <th class="">定位</th>
                                 <!-- <th class=" hidden">每个编码</th> -->
                                 <th class="">父级菜单编码</th>
                                 <!--  <th class=" hidden">所有父级</th> -->
                                 <th class="">排序</th>
                                 <th class="">是否可用</th>
                                 <th>修改</th>
                                 <th>删除</th>
                              </tr>
                           </thead>
                           <tbody id="result">
                              <!-- 循环显示查询出来的结果 -->
                             
                           </tbody>
                        </table>
                        </form>
                     </div>
                  </div>
               </section>
	 <!-- BEGIN JS -->
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
		<script src="${ctx}/js/editable-table-pms.js" ></script><!-- EDITABLE TABLE JS  -->
     <!-- EDITABLE TABLE FUNCTION  -->
     <script>
        jQuery(document).ready(function() {
            EditableTable.init();
            $('.sorting').css('width','')
            $('.sorting_disabled').css('width','')
			 $("#info").validationEngine({
	    	        scrollOffset: 98,//必须设置，因为Toolbar position为Fixed
	    	        promptPosition: 'topLeft',
	    	        autoHidePrompt: true,
	    	    });
        });
     </script>
  <!-- END JS --> 
</body>
</html>