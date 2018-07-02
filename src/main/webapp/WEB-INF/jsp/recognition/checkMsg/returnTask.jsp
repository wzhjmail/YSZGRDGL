<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<%@ taglib prefix="myshiro" uri="/customize-tags" %>
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>中心复核退回任务</title>
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
	getTasks();
	$("#psb").click(
		function(){
			if(nRow==null){
				wzj.alert('温馨提示','请选择一个任务！');
			}else if(selectId==""){
				wzj.alert('温馨提示','数据为空！');
				return false;
			}else{
				$("#toShow").click();	
		}}	
	);
	
	$('#editable-sample tbody').on('click', 'tr', function () {//选中的行
		//获取选中行的第一列的值
		//alert($(this).children("td").eq(0).text());
		nRow = $(this).parent().find("tr").index($(this)[0]);
		selectId=$(this).children("td").eq(1).text();
		selectNo=$(this).children("td").eq(2).text();
		temptr.css("background-color","");
        temptr = $(this);
        temptr.css("background-color","#99ffff");
	});
});//初始化
function SetTableStyle() {
	var aTable = $('#editable-sample').dataTable();
    $("#editable-sample tbody tr").unbind("mouserover")
    $("#editable-sample tbody tr").bind("mousedown", (function (e) {
        if (e.which == 3) {
            var opertionn = {
                name: "",
                offsetX: 2,
                offsetY: 2,
                textLimit: 30,
                beforeShow: $.noop,
                afterShow: $.noop
            };
              
            aTable.$('td.td_selected').removeClass('td_selected');
            if ($(e.target).hasClass('td_selected')) {
                $(e.target).removeClass('td_selected');
            } else {
                $(e.target).addClass('td_selected');
            }
            $.smartMenu.remove();
            $(this).smartMenu(null, opertionn);
        }
         
    }));
    $("#editable-sample tbody tr").mouseover(function (e) { 
        aTable.$('td.td_selected').removeClass('td_selected');
        if ($(e.target).hasClass('td_selected')) {
            $(e.target).removeClass('td_selected');
        } else {
            $(e.target).addClass('td_selected');
        }
        if ($(this).hasClass('row_selected')) {
            $(this).removeClass('row_selected');
        }
        else {
            aTable.$('tr.row_selected').removeClass('row_selected');
            $(this).addClass('row_selected');
        }
    });
}
//以上是右键特效的js
var temptr = $();
var nRow = null;
var selectId=null;
var selectNo=null;
window.onload=function(){//给表添加滚动条
	$("#editable-sample").wrap('<div style="overflow-x:auto;width:100%"></div>');
	SetTableStyle();
};
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
function getTasks(){
	$.ajaxSetup({async : false});
	$.post(
		"${ctx}/application/expressMessage/getAllTask.action",
		"pointName=中心查看材料&status=21",
		function(data){
			$.each(data,function(i,item){
				$("#result").append("<tr>"
						+"<td class='' style='display:none;'>"+item.taskId+"</td>"
						+"<td class=''>"+(i+1)+"</td>"
						+"<td class=''>"+exchangeNull(item.titleno)+"</td>"
		             	+"<td class=''>"+item.enterprisename+"</td>"
		             	+"<td class=''>中心复核未通过</td>"
		             	+"<td class=''><button data-toggle='modal' href='' onclick='view("
		             			+item.id+");' class='btn btn-success green'>查看</button></td>"
		             	+"<td class=''><a data-toggle='modal' href='#Modal_show2' id='"+item.enterprisename+"' name='"+item.taskId+"' onClick='getComment(this)' class='btn btn-success green'>查看意见</a></td>"
		             	+"<td class=''><button data-toggle='modal' href='#Modal_show1' class='btn btn-success green'>审核</button></td></tr>");
			});
		}
	);
	
}
function view(id){
	//$("#group").attr("src","${ctx}/application/view.action?id="+id);
	location.href = "${ctx}/application/view.action?id="+id
}
function discuss(){
	if(selectId==null){
		wzj.alert('温馨提示', '请选择一条数据！')
		return false;
	}else if(selectId==""){
		wzj.alert('温馨提示','数据为空！');
		return false;
	}else{
		$("#group").attr("src","${ctx}/application/view.action?id="+selectId);
	}
}
function getComment(element){
	var taskId=element.name;
	var companyName=element.id;
	$("#comments").empty();//清空
	$("#comments").append("<tr><td>时间</td><td>意见人</td><td>意见</td></tr>");
	$.post(
		"${ctx}/application/getComment",
		"taskId="+taskId+"&companyName="+companyName,
		function(data){
			$.each(data,function(i,item){
				var str=DateHandle(item.time);
				$("#comments").append(
					"<tr><td>"+str+"</td><td>"
					+item.userId+"</td><td>"
					+item.fullMessage+"</td></tr>");
			});
		}
	);
}
function savePdfSQ(){
	if(nRow==null){
		wzj.alert('温馨提示','请选择一个任务！');
	}else if(selectId==""){
		wzj.alert('温馨提示','数据为空！');
		return false;
	}else{
		window.open('${ctx}/common/downPdf.action?type=2&titleNo='+selectNo);	
	}
}
function savePdfPS(){
	if(nRow==null){
		wzj.alert('温馨提示','请选择一个任务！');
	}else if(selectId==""){
		wzj.alert('温馨提示','数据为空！');
		return false;
	}else{
		window.open('${ctx}/common/downPdf.action?type=5&titleNo='+selectNo);	
	}
}
</script>
</head>
<body>
               <section class="panel">
                  <div class="panel-body">
                     <div class="adv-table editable-table">
                        <div class="clearfix">
                       <!--     <div class="btn-group pull-right">
                              <button class="btn btn-primary dropdown-toggle" data-toggle="dropdown">下载<i class="fa fa-angle-down"></i>
                              </button>
                              <ul class="dropdown-menu pull-right">
                              <li>
                              <a onclick="savePdfSQ()">
                            	 申请表下载</i>
                              </a>
                              </li>
                              <li>
							  <a onclick="savePdfPS()">
                            	 评审表下载</i>
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
                                 <th class="">编号</th>
                                 <th class="">流水号</th>
                                 <th class="">企业名称</th>
                                 <th class="">状态</th>
                                 <th class="">查看</th>
                                 <th class="">意见</th>
                                 <th class="">审核</th>
                              </tr>
                           </thead>
                           <tbody id="result">
                              <!-- 循环显示查询出来的结果 -->
                             
                           </tbody>
                        </table>
                        </div>
                     </div>
               </section>
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
              	<p><center>编码中心核对材料意见</center></p>
                <textarea id="comment" autocomplete="off" class="form-control placeholder-no-fix" placeholder="无"></textarea>
	         </div>
	         <div class="modal-footer">
	         	<button data-dismiss="modal" class="btn btn-default" type="button" onclick="processTask(false)">
                  	退回
                </button>
                <button data-dismiss="modal" class="btn btn-success" type="button" onclick="processTask(true)">
                 	通过
                </button>
              </div>
              </div>
            </div>
          </div>
          <div id="Modal_show2" class="modal fade">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                  &times;
                </button>
                <h4 class="modal-title">评审意见</h4>
              </div>
              <div class="modal-body" style="overflow-y: scroll;max-height: 300px;">
                <table id="comments" class="table table-bordered" style="width:100%">
                	
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
	<script src="${ctx}/js/editable-table.js" ></script><!-- EDITABLE TABLE JS  -->
     <!-- EDITABLE TABLE FUNCTION  -->
     <script>
        jQuery(document).ready(function() {
            EditableTable.init();
        });
        
        function processTask(result){//初审任务的处理
        	var taskId=$("#result").children("tr").eq(nRow).children("td").eq(0).text();
            var comment=$("#comment").val();
            var status=0;
            var role="中心审核人员";
            if(result){
            	status=18;
            }else{
            	status=19;
            	role="分中心审核人员";
            }
            $.post(
            	"${ctx}/recognition/climeAndcompleteTask.action",
            	"taskId="+taskId+"&comment="+comment+"&role="+role
            		+"&result="+result+"&status="+status,
            	function(data){
            		window.location.reload();
            	}
            );
        }
     </script>
  <!-- END JS --> 
</body>
</html>