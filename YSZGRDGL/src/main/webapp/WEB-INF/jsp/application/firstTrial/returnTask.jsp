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
<script type="text/javascript">
$(function(){
	$('#editable-sample tbody').on('click', 'tr', function () {//选中的行
		//获取选中行的第一列的值
		//alert($(this).children("td").eq(0).text());
		nRow = $(this).parent().find("tr").index($(this)[0]);
		taskId=$(this).children("td").eq(0).text();
		titleNo=$(this).children("td").eq(1).text();
		selectId=$(this).children("td").eq(2).text();
		temptr.css("background-color","");
        temptr = $(this);
        temptr.css("background-color","#99ffff");
	});
	$.ajaxSetup({async : false});
	$.post(
		"${ctx}/application/review/getReturnTasks.action",
		"pointName=分支机构初审&status=6,8",
		function(data){
			$.each(data,function(i,item){
				$("#result").append("<tr>"
						+"<td class='' style='display:none;'>"+item.taskId+"</td>"
						+"<td class='' style='display:none;'>"+item.titleno+"</td>"
						+"<td class='' style='display:none;'>"+item.id+"</td>"
						+"<td class=''>"+(i+1)+"</td>"
						+"<td class=''>"+exchangeNull(item.titleno)+"</td>"
						+"<td class=''>"+item.enterprisename+"</td>"
		             	+"<td class=''>审核未通过</td>"
		             	+"<td class=''><a data-toggle='modal' href='#Modal_show' name='"+item.enterprisename+"' id='"+item.taskId+"' onClick='getComment(this)' class='btn btn-success green'>查看意见</a></td>"
		             	+"<td class=''><a data-toggle='modal' href='' onClick='view("+item.id+")' class='btn btn-success green'>查看</a></td>"
// 		             	+"<td class=''><a href='${ctx}/common/downPdf.action?type=1&titleNo="+item.titleno+"' class='btn btn-success green'>申请表下载</a></td>"
// 		             	+"<td class=''><a href='${ctx}/common/downPdf.action?type=4&titleNo="+item.titleno+"' class='btn btn-success green'>评审表下载</a></td>"
             			+"<td class=''><button class='btn btn-success green' data-toggle='modal' href='#Modal_show1' onclick='setTaskId("+item.taskId+")'>初审</button></td></td></tr>");
			});
		}
	);
});//初始化
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
	var taskId=element.id;
	var companyName=element.name;
	$("#comment").empty();//清空
	$("#comment").append("<tr><td>时间</td><td>意见人</td><td>意见</td></tr>");
	$.post(
		"${ctx}/application/getComment",
		"taskId="+taskId+"&companyName="+companyName,
		function(data){
			$.each(data,function(i,item){
				var str=DateHandle(item.time);
				$("#comment").append(
					"<tr><td>"+str+"</td><td>"
					+item.userId+"</td><td>"
					+item.fullMessage+"</td></tr>");
			});
		}
	);
}
var temptr = $();
var nRow = null;
var taskId=null;
var titletNo=null;
var selectId=null;
function setTaskId(tId){
	taskId=tId;
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
//查看
function view(id){
	location.href = "${ctx}/application/view.action?id="+id;
}
function savePdf(){
	if(nRow==null){
		wzj.alert('温馨提示','请选择一个任务！');
	}else if(titleNo==""){
		wzj.alert('温馨提示','数据为空！');
		return false;
	}else{
		location.href='${ctx}/common/downPdf.action?type=1&titleNo='+titleNo;	
	}
}
function saveReview(){
	if(nRow==null){
		wzj.alert('温馨提示','请选择一个任务！');
	}else if(titleNo==""){
		wzj.alert('温馨提示','数据为空！');
		return false;
	}else{
		window.open('${ctx}/common/downPdf.action?type=4&titleNo='+titleNo);	
	}
}
//盖章申请表下载
function saveSQ(){
	if(nRow==null){
		wzj.alert('温馨提示','请选择一个任务！');
	}else if(selectId==""){
		wzj.alert('温馨提示','数据为空！');
		return false;
	}else{
		window.open('${ctx}/common/downSeal.action?type=1&titleNo='+titleNo+'&appId='+selectId);	
	}
}

//盖章评审表下载
function savePS(){
	if(nRow==null){
		wzj.alert('温馨提示','请选择一个任务！');
	}else if(selectId==""){
		wzj.alert('温馨提示','数据为空！');
		return false;
	}else{
		window.open('${ctx}/common/downSeal.action?type=2&titleNo='+titleNo+'&appId='+selectId);	
	}
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
                              <li>
                              <a onclick="savePdf()">
                            	 申请表下载</i>
                              </a>
                              </li>
                              <li>
							  <a onclick="saveReview()">
                            	 评审表下载</i>
                              </a>
                              </li>
                              <li>
							  <a onclick="saveSQ()">
                            	 申请表下载(盖章)</i>
                              </a>
                              </li>
                              <li>
							  <a onclick="savePS()">
                            	 评审表下载(盖章)</i>
                              </a>
                              </li>
                                 <li><a href="">导出到Excel</a></li>
                              </ul>
                           </div> -->
                        </div>
                        <div class="space15"></div>
                        <table class="table table-bordered" style="width:100%" id="editable-sample">
                           <thead>
                              <tr>
                                 <th class="" style="display:none">任务编号</th>
                                 <th class="" style="display:none">状态编号</th>
                                 <th class="" style="display:none">业务编号</th>
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
                	
                </table>
	         </div>
	         <div class="modal-footer">
                <button data-dismiss="modal" class="btn btn-success" type="button">确定</button>
              </div>
              </div>
            </div>
          </div>
		<div id="Modal_show1" class="modal fade">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                  &times;
                </button>
                <h4 class="modal-title">评审任务</h4>
              </div>
              <div class="modal-body">
              	<p><center>分支机构评审意见</center></p>
                <textarea id="comments" autocomplete="off" class="form-control placeholder-no-fix" placeholder="无"></textarea>
	         </div>
	         <div class="modal-footer">
	         	<button id="bh" class="btn btn-default" type="button" onclick="processTask(false)">
                  	退回
                </button>
                <button data-dismiss="modal" class="btn btn-success" type="button" onclick="processTask(true)">
                 	通过
                </button>
              </div>
              </div>
            </div>
          </div>
	 <!-- BEGIN JS -->
 <script src="${ctx}/js/jquery.sparkline.js"></script><!-- SPARKLINE JS -->
    <script src="${ctx}/js/sparkline-chart.js"></script><!-- SPARKLINE CHART JS -->
    <script src="${ctx}/js/count.js"></script><!-- COUNT JS -->
   
  <script src="${ctx}/js/jquery-1.8.3.min.js" ></script><!--重複，但是不能少 -->
		<script src="${ctx}/js/bootstrap.min.js" ></script><!-- BOOTSTRAP JS  -->
		<script src="${ctx}/js/jquery.dcjqaccordion.2.7.js"></script><!-- ACCORDING JS -->
		<script src="${ctx}/js/jquery.scrollTo.min.js" ></script><!-- SCROLLTO JS  -->
		<script src="${ctx}/js/jquery.nicescroll.js" > </script><!-- NICESCROLL JS  -->
		<script src="${ctx}/assets/data-tables/jquery.dataTables.js"></script><!-- DATATABLE JS  -->
		<script src="${ctx}/assets/data-tables/DT_bootstrap.js"></script><!-- DATATABLE JS  -->
		<script src="${ctx}/js/respond.min.js" ></script><!-- RESPOND JS  -->
		<script src="${ctx}/js/common-scripts.js" ></script><!-- BASIC COMMON JS  -->
		<script src="${ctx}/js/editable-table.js" ></script> <!--EDITABLE TABLE JS  -->
     <script>
        jQuery(document).ready(function() {
            EditableTable.init();
        });
        function processTask(result){//初审任务的处理
            var comment=$("#comment").val();
            var status=0;
            if(result){
            	status=3;
                $.post(
                    	"${ctx}/application/climeAndcompleteTask.action",
                    	"taskId="+taskId+"&comment="+comment+"&role=分中心评审人员&candidate=fzjg"
                    	+"&result="+result+"&status="+status,
                    	function(data){
                    		window.location.reload();
                    	}
                    );
            }else{
            	status=4;
            	if($('#comments').val()){
                    $.post(
                        	"${ctx}/application/climeAndcompleteTask.action",
                        	"taskId="+taskId+"&comment="+comment+"&role=分中心评审人员&candidate=fzjg"
                        	+"&result="+result+"&status="+status,
                        	function(data){
                        		window.location.reload();
                        	}
                        );
            	}else{
            		wzj.alert('温馨提示','请填写评审意见！');
                }         	
            }
        }
     </script>
  <!-- END JS --> 
    <!-- END JS -->
</body>
</html>