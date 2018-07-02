<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<%@ taglib prefix="myshiro" uri="/customize-tags" %>
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编码中心核准</title>
	<script src="${ctx}/js/jquery-1.8.3.min.js" ></script><!-- BASIC JS LIABRARY 1.8.3 -->
	<script src="${ctx}/js/style_self.js"></script>
	<script src="${ctx}/js/fileinput.min.js" type="text/javascript"></script>
	<script src="${ctx}/js/locales/zh.js" type="text/javascript"></script>
	<link href="${ctx}/assets/morris.js-0.4.3/morris.css" rel="stylesheet"><!-- MORRIS CHART CSS -->
	<link href="${ctx}/css/clndr.css" rel="stylesheet"><!-- CALENDER CSS -->
	<link href="${ctx}/css/fileinput.min.css" media="all" rel="stylesheet" type="text/css" />
	<!-- 查询页面 -->
    <link href="${ctx}/css/bootstrap.min.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/css/bootstrap-reset.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="${ctx}/assets/font-awesome/css/font-awesome.css" rel="stylesheet"><!-- FONT AWESOME ICON STYLESHEET -->
    <link href="${ctx}/assets/data-tables/DT_bootstrap.css" rel="stylesheet" ><!-- DATATABLE CSS -->
    <link href="${ctx}/css/style.css" rel="stylesheet"><!-- THEME BASIC CSS -->
    <link href="${ctx}/css/style-responsive.css" rel="stylesheet"><!-- THEME BASIC RESPONSIVE  CSS -->
	<link href="${ctx}/css/style_self.css" rel="stylesheet"><!-- 右键特效的css -->
<script type="text/javascript">
$(function(){
	$.ajaxSetup({async : false});
	$.post(
		"${ctx}/alternation/getTasks",
		"pointName=中心审核&status=2",
		function(data){
			$.each(data,function(i,item){
				$("#result").append("<tr  ondblclick='viewInfo()'>"
						+"<td style='display:none;'>"+item.taskId+"</td>"
						+"<td style='display:none;'>"+item.id+"</td>"
						+"<td>"+(i+1)+"</td>"
						+"<td>"+exchangeNull(item.titleno)+"</td>"
						+"<td>"+item.branchName+"</td>"
						+"<td>"+item.companynameOld+"</td>"
						+"<td>"+item.linkmanOld+"</td>"
						+"<td>"+item.addressOld+"</td>"
		             	+"<td><button onclick='setId("+item.taskId
		             			+")' class='btn btn-info' data-toggle='modal' href='#Modal_show1'>审核</td></tr>");
			});
		}
	);
	$('#editable-sample tbody').on('click', 'tr', function () {//选中的行
		//获取选中行的第一列的值
		nRow = $(this).parent().find("tr").index($(this)[0]);
		selectId=$(this).children("td").eq(1).text();
		taskId=$(this).children("td").eq(0).text();
		selectNo=$(this).children("td").eq(4).text();
		comName=$(this).children("td").eq(5).text();
		temptr.css("background-color","");
	    temptr = $(this);
	    temptr.css("background-color","#99ffff");
	});
});//初始化
var temptr = $();
var nRow = null;
var selectId=null;
window.onload=function(){//给表添加滚动条
	$("#editable-sample").wrap('<div style="overflow-x:auto;width:100%"></div>');
	SetTableStyle();
};
function SetTableStyle() {
	var oTable = $('#editable-sample').dataTable();
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
              
            var menudata = GetRightMenu(e.target.cellIndex, e.target.innerHTML);
            oTable.$('td.td_selected').removeClass('td_selected');
            if ($(e.target).hasClass('td_selected')) {
                $(e.target).removeClass('td_selected');
            } else {
                $(e.target).addClass('td_selected');
            }
            $.smartMenu.remove();
            $(this).smartMenu(menudata, opertionn);
        }
         
    }));
    $("#editable-sample tbody tr").mouseover(function (e) { 
        oTable.$('td.td_selected').removeClass('td_selected');
        if ($(e.target).hasClass('td_selected')) {
            $(e.target).removeClass('td_selected');
        } else {
            $(e.target).addClass('td_selected');
        }
        if ($(this).hasClass('row_selected')) {
            $(this).removeClass('row_selected');
        }else {
            oTable.$('tr.row_selected').removeClass('row_selected');
            $(this).addClass('row_selected');
        }
    });
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
function view(id){
	//$("#group").attr("src","${ctx}/alternation/view.action?id="+id);
	location.href = "${ctx}/alternation/view.action?id="+id
}
function setId(taskId,id){
	selectTaskId=taskId;
}
function show(id){
	var param="companyId="+id+"_change";
	$.post(
		"${ctx}/application/expressMessage/getExpress.action",
		//"companyId="+id+"_change",//id_change表示变更业务的id
		param,
		function(data){
			$("#name").html(data.name);
			$("#num").html(data.number);
			$("#concat").html(data.contact);
			$("#tel").html(data.phoneNumber);
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
function getComment(element){
	var taskId=element.id;
	var companyName=element.name;
	$("#comment1").empty();//清空
	$("#comment1").append("<tr><td>时间</td><td>意见人</td><td>意见</td></tr>");
	$.post(
		"${ctx}/application/getComment",
		"taskId="+taskId+"&companyName="+companyName,
		function(data){
			$.each(data,function(i,item){
				var str=DateHandle(item.time);
				$("#comment1").append(
					"<tr><td>"+str+"</td><td>"
					+item.userId+"</td><td>"
					+item.fullMessage+"</td></tr>");
			});
		}
	);
}
function upCheckResult(){//审核结果上传
	if(nRow==null){
		wzj.alert('温馨提示','请选择一个任务！');
	}else if(selectNo==""){
		wzj.alert('温馨提示','数据为空！');
		return false;
	}else{
	$("#upCheckResult").attr("href","#Modal_show4");
	$("#mulfile").fileinput({
		uploadUrl:"${ctx}/commons/PLuploadSampleFile?titleNo="+selectNo+"&describe=中心审核结果&sampleId=12",
	    fileType: "any",
		language : 'zh',
		enctype : 'multipart/form-data',
	});
	}
}
function downZip(){
	if(nRow==null){
		wzj.alert('温馨提示', '请选择一个任务！')
		return false;
	}else if(selectNo==""){
		wzj.alert('温馨提示','数据为空！');
		return false;
	}else{
		window.open("${ctx}/commons/download.action?titleNo="+selectNo);
	}
}
function viewInfo(){
	if(nRow==null){
		wzj.alert('温馨提示','请选择一个任务！');
	}else if(taskId=="数据为空"){
		wzj.alert('温馨提示','数据为空！');
		return false;
	}else{
		location.href='${ctx}/alternation/expressMessage/viewAllZX.action?titleNo='+selectNo+'&appId='+selectId+'&taskId='+taskId+'&comName='+comName;	
	}
}
</script>
</head>
<body>
               <section class="panel">
                  <div class="panel-body">
                     <div class="adv-table editable-table">
                        <div class="clearfix">
                      		<div class="btn-group nopaddingleft">
                              <button onclick="viewInfo()" class="btn btn-info" data-toggle='modal' href=''>
                            	 信息查看 <i class="fa fa-envelope"></i>
                              </button>
                           </div>
                           <div class="btn-group ">
                           <a onclick="upCheckResult()" class="btn btn-info" id="upCheckResult" href="" data-toggle='modal'>
                            	 审核结果上传<i class="fa fa-upload"></i>
                           </a>
                           </div>
                           <div class="btn-group">
                             <button onclick="downZip()" class="btn btn-info" data-toggle='modal' href='' id="dialog">
                            	 资料下载 <i class="fa fa-download"></i>
                              </button>
                           </div>
                        </div>
                        <div class="space15"></div>
                        <table class="table table-bordered" style="width:100%" id="editable-sample">
                           <thead>
                              <tr>
                              	 <th class="" style="display:none"></th>
                             	 <th class="" style="display:none"></th>
                                 <th class="">编号</th>
                                 <th class="">流水号</th>
                                 <th class="">分支机构</th>
                                 <th class="">企业名称</th>
                                 <th class="">联系人</th>
                                 <th class="">详细地址</th>
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
               <div id="Modal_show" class="modal fade">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                  &times;
                </button>
                <h4 class="modal-title">中心审核任务</h4>
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
          <div id="Modal_show1" class="modal fade">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                  &times;
                </button>
                <h4 class="modal-title">审核任务</h4>
              </div>
              <div class="modal-body">
              	<p><center>中心审核意见</center></p>
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
                <h4 class="modal-title">快递信息</h4>
              </div>
              <div class="modal-body">
                <table class="table table-bordered" style="width:100%">
					<tr><td>快递名</td><td>单号</td><td>联系人</td><td>联系方式</td></tr>
					<tr><td id="name"></td><td id="num"></td><td id="concat"></td><td id="tel"></td></tr>
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
          <div id="Modal_show3" class="modal fade">
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
                	
                </table>
	         </div>
	         <div class="modal-footer">
                <button data-dismiss="modal" class="btn btn-success" type="button">确定</button>
              </div>
              </div>
            </div>
          </div>
          <div id="Modal_show4" class="modal fade">
	          <div class="modal-dialog">
	            <div class="modal-content">
	              <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
	                  &times;
	                </button>
	                <h4 class="modal-title">附件</h4>
	              </div>
	              <form action="" method="post" enctype="multipart/form-data" id="uploadForm">
                  <div class="form-group" id="file-content" style="padding: 15px">
                    <input id="mulfile" name="mfile" type="file" class="file-loading" multiple="multiple">
                  </div>	
               	  </form>
		         <div class="modal-footer" style="margin-top:0px;">
	                <button data-dismiss="modal" class="btn btn-success text-right" onclick="clearFile()">
	                	确定
	                </button>
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
     <!-- EDITABLE TABLE FUNCTION  -->
     <script>
        jQuery(document).ready(function() {
            EditableTable.init();
        });
        function processTask(result){//初审任务的处理
        	var taskId=selectTaskId;
            var comment=$("#comment").val();
            var role="分中心审核人员";
            var status=4
          	//删除寄送信息
          	$.post(
          		"${ctx}/alternation/expressMessage/delExpressInfo",
          		"taskId="+taskId,
          		function(data){}
          	);
            if(result){
            	status=5;
            }
           	$.post(
              	"${ctx}/alternation/climeAndcompleteTask.action",
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