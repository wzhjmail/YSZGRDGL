<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<%@ taglib prefix="myshiro" uri="/customize-tags" %>
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>分支机构初审</title>
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
	$('#editable-sample tbody').on('click', 'tr', function () {//选中的行
		//获取选中行的第一列的值
		nRow = $(this).parent().find("tr").index($(this)[0]);
		selectId=$(this).children("td").eq(1).text();
		taskId1=$(this).children("td").eq(0).text();
		temptr.css("background-color","");
        temptr = $(this);
        temptr.css("background-color","#99ffff");
	});
	
});//初始化
var temptr = $();
var nRow = null;//选中的行
var selectId=null;//选中的业务编号
var taskId1=null;//选中的任务编号
function getTasks(){
	$.ajaxSetup({async : false});
	$.post(
		"${ctx}/alternation/getTasks",
		"pointName=初审&status=1,4",//获取已申请，未审核状态下的
		function(data){
			$.each(data,function(i,item){
				if(item.status==1){
					$("#result").append("<tr ondblclick='trial()'>"
							+"<td style='display:none;'>"+item.taskId+"</td>"
							+"<td style='display:none;'>"+item.id+"</td>"
							+"<td>"+(i+1)+"</td>"
							+"<td>"+exchangeNull(item.titleno)+"</td>"
							+"<td>"+item.companynameOld+"</td>"
							+"<td>"+DateHandle(item.createdate)+"</td>"
							+"<td>"+item.addressOld+"</td>"
							+"<td>"+item.linkmanOld+"</td>"
							+"<td>"+DateHandle(item.certappdateOld)+"</td></tr>");
				}else{
					$("#result").append("<tr style='color:red' ondblclick='trial()'>"
							+"<td style='display:none;'>"+item.taskId+"</td>"
							+"<td style='display:none;'>"+item.id+"</td>"
							+"<td>"+(i+1)+"</td>"
							+"<td>"+exchangeNull(item.titleno)+"</td>"
							+"<td>"+item.companynameOld+"</td>"
							+"<td>"+DateHandle(item.createdate)+"</td>"
							+"<td>"+item.addressOld+"</td>"
							+"<td>"+item.linkmanOld+"</td>"
							+"<td>"+DateHandle(item.certappdateOld)+"</td></tr>");
				}
			});
		}
	);
}
function getComment(element){
	var taskId=element.id;
	var companyName=element.name;
	$("#commentBody").empty();//清空
	$.post(
		"${ctx}/application/getComment",
		"taskId="+taskId+"&companyName="+companyName,
		function(data){
			$.each(data,function(i,item){
				var str=DateHandle(item.time);
				$("#commentBody").append(
					"<tr><td>"+str+"</td><td>"
					+item.userId+"</td><td>"
					+item.fullMessage+"</td></tr>");
			});
			$('#comment1').dataTable(oTable)
		}
	);
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
var selectTaskId="";

function trial(){
	if(selectId==null){
		wzj.alert('温馨提示', '请选择一个任务！')
		return false;
	}else if(selectId==""){
		wzj.alert('温馨提示','数据为空！');
		return false;
	}else{
		//location.href = "${ctx}/alternation/edit.action?id="+selectId+"&taskId="+taskId1;
		location.href = "${ctx}/alternation/trial.action?id="+selectId+"&taskId="+taskId1;
	}
}
function setId(taskId){
	selectTaskId=taskId;
}
</script>
</head>
<body>
               <section class="panel">
                  <div class="panel-body">
                     <div class="adv-table editable-table">
                        <div class="clearfix">
                        <div class="btn-group nopaddingleft">
                              <button class="btn btn-info" onclick="trial()">
                            	  资料审查 <i class="fa fa-laptop"></i>
                              </button>
                              <a href="#Modal_show1" data-toggle='modal' id="show" style="display:none"></a>
                           </div>
                           <!-- 
                           <div class="btn-group pull-right">
                              <button class="btn btn-primary dropdown-toggle" data-toggle="dropdown">工具<i class="fa fa-angle-down"></i>
                              </button>
                              <ul class="dropdown-menu pull-right">
                                 <li><a href="">导出到Excel</a></li>
                              </ul>
                           </div> -->
                        </div>
                        <div class="space15"></div>
                        <table class="table table-bordered" style="width:100%" id="editable-sample">
                           <thead>
                              <tr>
           		                 <th class="" style="display:none"></th>
                             	 <th class="" style="display:none"></th>
                                 <th class="">编号</th>
                                 <th class="">流水号</th>
                                 <th class="">企业名称</th>
                                 <th class="">创建日期</th>
                                 <th class="">地址</th>
                                 <th class="">联系人</th>
                                 <th class="">发证日期</th>
<!--                                  <th class="">查看</th> -->
<!--                                  <th class="">意见</th> -->
<!--                                  <th class="">初审</th> -->
                              </tr>
                           </thead>
                           <tbody id="result">
                              <!-- 循环显示查询出来的结果 -->
                             
                           </tbody>
                        </table>
                        </div>
                     </div>
               </section>
               <div id="Modal_show" class="modal fade">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                  &times;
                </button>
                <h4 class="modal-title">分支机构初审组任务</h4>
              </div>
              <div class="modal-body">
                <table border="0" cellspacing="100px" align="center" style="height: 770px;width:100%">
					<tr id="trs"><td style="height: 100%"><iframe id="group" src="" frameborder="0" width="100%" scrolling="Yes" height="100%" leftmargin="0" topmargin="0"></iframe></td></tr>
				</table>
	         </div>
	         <div class="modal-footer" style="margin-top:0px;">
                <button data-dismiss="modal" class="btn btn-success" type="button">
                 	确定
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
                <table id="comment1" class="table table-bordered" style="width:100%">
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
	<script src="${ctx}/js/editable-table.js" ></script><!-- EDITABLE TABLE JS  -->
		<script src="${ctx}/js/commentTable.js" ></script>
     <!-- EDITABLE TABLE FUNCTION  -->
     <script>
        jQuery(document).ready(function() {
            EditableTable.init();
        });
        
     </script>
  <!-- END JS --> 
</body>
</html>