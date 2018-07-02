<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<%@ taglib prefix="myshiro" uri="/customize-tags" %>
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>退回任务</title>
	<link href="${ctx}/assets/morris.js-0.4.3/morris.css" rel="stylesheet"><!-- MORRIS CHART CSS -->
	<link href="${ctx}/css/clndr.css" rel="stylesheet"><!-- CALENDER CSS -->
	<link href="${ctx}/css/style_self.css" rel="stylesheet">
	<!-- 查询页面 -->
    <link href="${ctx}/css/bootstrap.min.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="${ctx}/css/bootstrap-reset.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="${ctx}/assets/font-awesome/css/font-awesome.css" rel="stylesheet"><!-- FONT AWESOME ICON STYLESHEET -->
    <link href="${ctx}/assets/data-tables/DT_bootstrap.css" rel="stylesheet" ><!-- DATATABLE CSS -->
    <link href="${ctx}/css/style.css" rel="stylesheet"><!-- THEME BASIC CSS -->
    <link href="${ctx}/assets/fuelux/css/tree-style.css" rel="stylesheet">
    <link href="${ctx}/css/style-responsive.css" rel="stylesheet"><!-- THEME BASIC RESPONSIVE  CSS -->
<script src="${ctx}/js/jquery-1.8.3.min.js" ></script><!-- BASIC JS LIABRARY 1.8.3 -->
<script src="${ctx}/js/style_self.js"></script>
<style type="text/css">
	.label{
		line-height: unset;
	}
</style>
<script type="text/javascript">
$(function(){
	$.ajaxSetup({async : false});
	$.post(
		"${ctx}/alternation/getReturnTasks.action",
		function(data){
			$.each(data,function(i,item){
				$("#result").append("<tr>"
						+"<td class='' style='display:none;'>"+item.taskId+"</td>"
						+"<td class=''>"+(i+1)+"</td>"
						+"<td class=''>"+exchangeNull(item.titleno)+"</td>"
		             	+"<td class=''>"+item.companynameOld+"</td>"
		             	+"<td class=''>初审未通过</td>"
		             	+"<td class=''><a data-toggle='modal' href='#Modal_show' id='"+item.companynameOld+"' name='"+item.taskId+"' onclick='getComment(this)' class='label label-success green'>查看意见</a></td>"
		             	+"<td class=''><a data-toggle='modal' href='' onclick='view("+item.pid+")' class='label label-success green'>查看</a></td>"
		             	+"<td class=''><a href='${ctx}/alternation/toFormUpdate.action?taskId="+item.taskId
		             			+"&id="+item.id+"' class='label label-success green'>修改</a></td></tr>");
			});
		}
	);
});//初始化
//查看
function view(id){
	location.href = "${ctx}/application/view.action?id="+id;
}
Date.prototype.Format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}
function DateHandle(obj) {  
	  if(obj!=null){
	    var objDate=new Date(obj).Format("yyyy-MM-dd"); //创建一个日期对象表示当前时间     
	    return objDate; 
	  }else{
	    	return null;
	  }
	}  
//处理null值
function exchangeNull(data){
	if(data==null){
		data="";
	}else{
		data=data;
	}
	return data;
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

function getComment(element){
	var companyName=element.id;
	var taskId=element.name;
	$("#commentBody").empty();//清空
	$.post(
		"${ctx}/application/getComment.action",
		"taskId="+taskId+"&companyName="+companyName,
		function(data){
			$.each(data,function(i,item){
				var str=DateHandle(item.time);
				$("#commentBody").append(
					"<tr><td>"+str+"</td><td>"
					+item.userId+"</td><td>"
					+item.fullMessage+"</td></tr");
			});
			$('#comment').dataTable(oTable)
		}
	);
}
</script>
</head>
<body>
               <section class="panel">
                  <div class="panel-body">
                     <div class="adv-table editable-table">
                        <div class="clearfix">
                         <!--   <div class="btn-group pull-right">
                              <button class="btn btn-primary dropdown-toggle" data-toggle="dropdown">下载<i class="fa fa-angle-down"></i>
                              </button>
                              <ul class="dropdown-menu pull-right">
<!--                                  <li><a href="#">Print</a></li> -->
<!--                                  <li><a href="#">Save as PDF</a></li> --
                                 <li><a href="">导出到Excel</a></li>
                              </ul>
                           </div> -->
                        </div>
                        <div class="space15"></div>
                        <table class="table table-bordered" style="width:100%" id="editable-sample">
                           <thead>
                              <tr>
                                 <th class="" style="display:none">任务编号</th>
                                 <th class="">编号</th>
                                 <th class="">流水号</th>
                                 <th class="">企业名称</th>
                                 <th class="">状态</th>
                                 <th class="">意见</th>
                                 <th class="">查看</th>
                                 <th class="">修改</th>
                              </tr>
                           </thead>
                           <tbody id="result">
                              <!-- 循环显示查询出来的结果 -->
                            
                           </tbody>
                        </table>
                        </div>
                     </div>
               </section>
           
	    <!-- END SECTION -->
		<div id="Modal_show" class="modal fade">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                  &times;
                </button>
                <h4 class="modal-title">评审意见</h4>
              </div>
              <div class="modal-body" style="overflow-y: scroll;max-height: 300px;">
                <table id="comment" class="table table-bordered" style="width:100%">
    	          <thead><tr>
                  	<td>时间</td>
                  	<td>意见人</td>
                  	<td>意见</td>
                  </tr></thead>
                  <tbody id="commentBody">
                  </tbody>
                </table>
	         </div>
	         <div class="modal-footer">
                <button data-dismiss="modal" class="btn btn-success" type="button">确定</button>
              </div>
              </div>
            </div>
          </div>
		
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
	<script src="${ctx}/js/editable-table-log.js" ></script> <!--EDITABLE TABLE JS  -->
	<script src="${ctx}/js/commentTable.js" ></script>
     <script>
        jQuery(document).ready(function() {
            EditableTable.init();
        });
     </script>
  <!-- END JS --> 
    <!-- END JS -->
</body>
</html>