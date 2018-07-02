<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<%@ taglib prefix="myshiro" uri="/customize-tags" %>
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编码中心批准</title>
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
	<link rel="stylesheet" href="${ctx}/css/validationEngine.jquery.css">
	<script src="${ctx}/js/jquery.validationEngine-zh_CN.js"></script>
	<script src="${ctx}/js/jquery.validationEngine.js"></script>
<style type="text/css">
	.label{
		line-height: unset;
	}
	a{
		cursor: pointer;
	}
</style>
<script type="text/javascript">
$(function(){
	getTasks();
	$("#editable-sample tbody tr").dblclick(function(e){
		showAll();
	});
	
	$('#editable-sample tbody').on('click', 'tr', function () {//选中的行
		//获取选中行的第一列的值
		nRow = $(this).parent().find("tr").index($(this)[0]);
		selectId=$(this).children("td").eq(1).text();
		selectNo=$(this).children("td").eq(4).text();
		comName=$(this).children("td").eq(5).text();
		taskId=$(this).children("td").eq(0).text();
		temptr.css("background-color","");
        temptr = $(this);
        temptr.css("background-color","#99ffff");
	});
	
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
        }
        else {
            oTable.$('tr.row_selected').removeClass('row_selected');
            $(this).addClass('row_selected');
        }
    });
}
function GetRightMenu(index,value) { 
  
    var imageMenuData = new Array();
    var imageMenuData2 = new Array(); 
    imageMenuData.push({
        "text": "申请表下载", "func": function () {
        	if(nRow==null){
				wzj.alert('温馨提示','请选择一个任务！');
			}else if(selectId=="数据为空"){
				wzj.alert('温馨提示','数据为空！');
				return false;
			}else{
				exportPDF();
			}
        }
    });
    imageMenuData.push({
        "text": "评审表下载", "func": function () {
        	if(nRow==null){
    			wzj.alert('温馨提示','请选择一个任务！');
    		}else if(selectId=="数据为空"){
    			wzj.alert('温馨提示','数据为空！');
    			return false;
    		}else{
    			$("#pdf").click();
    		}
        }
        }); 
    imageMenuData2.push(imageMenuData);
    return imageMenuData2;
}
function showAll(){
	if(nRow==null){
		wzj.alert('温馨提示', '请选择一个任务！')
		return false;
	}else if(selectId==""){
		wzj.alert('温馨提示','数据为空！');
		return false;
	}else{
	location.href='${ctx}/application/check/allInfo.action?titleNo='+selectNo+'&appId='+selectId
		+'&taskId='+taskId+'&comName='+comName+"&backPage=批准";
	}
}
//以上是右键特效的js
var temptr = $();
var nRow = null;//选中的行
var selectId=null;//选中的业务编号
var selectNo=null;//选中的流水号
var comName=null;//选中的企业名称
window.onload=function(){//给表添加滚动条
	$("#editable-sample").wrap('<div style="overflow-x:auto;width:100%"></div>');
	SetTableStyle();
};
function getTasks(){
	$.ajaxSetup({async : false});
	$.post(
		"${ctx}/application/common/getAllTask.action",
		"pointId=zxfh",
		function(data){
			$.each(data,function(i,item){
				$("#result").append("<tr>"
						+"<td style='display:none;'>"+item.taskId+"</td>"
						+"<td style='display:none;'>"+item.id+"</td>"
						+"<td>"+(i+1)+"</td>"
						+"<td>"+item.branchName+"</td>"
						+"<td>"+exchangeNull(item.titleno)+"</td>"
		             	+"<td>"+item.enterprisename+"</td>"
		             	+"<td>"+item.enterprisekind+"</td>"
		             	+"<td>"+exchangeNull(item.address)+"</td>"
		             	+"<td>"+exchangeNull(item.postalcode)+"</td></tr>");
			});
		}
	);
}

function deleteRecord(nRow){
	var oTable = $('#editable-sample').dataTable();
	oTable.fnDeleteRow(nRow);
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
function downZip(){
	if(nRow==null){
		wzj.alert('温馨提示', '请选择一个任务！')
		return false;
	}else if(selectId==""){
		wzj.alert('温馨提示','数据为空！');
		return false;
	}else{
		window.open("${ctx}/commons/download.action?titleNo="+selectNo);
	}
}
</script>
</head>
<body>
               <section class="panel">
                  <div class="panel-body">
                     <div class="adv-table editable-table">
                        <div class="clearfix">
                           <div class=" nopaddingleft">
                              <a id="toShow" data-toggle="modal" href="" style="visibility:hidden"></a>
                           </div>
                           <div class="btn-group  nopaddingleft">
                             <button onclick="showAll()" class="btn btn-info" data-toggle='modal' href='' id="dialog">
                            	 信息批准 <i class="fa fa-eye"></i>
                              </button>
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
                                 <th class="" style="display:none;">任务编号</th>
                                 <th class="" style="display:none;">业务id</th>
                                 <th class="">编号</th>
                                 <th class="">分支机构</th>
                                 <th class="">流水号</th>
                                 <th class="">企业名称</th>
                                 <th class="">企业性质</th>
                                 <th class="">详细地址</th>
                                 <th class="">邮政编码</th>
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
<script src="${ctx}/js/editable-table.js" ></script><!--  EDITABLE TABLE JS  -->
		<script src="${ctx}/js/commentTable.js" ></script>
     <!-- EDITABLE TABLE FUNCTION  -->
     <script>
        jQuery(document).ready(function() {
            EditableTable.init();
            $("#forms").validationEngine({
    	        scrollOffset: 98,//必须设置，因为Toolbar position为Fixed
    	        promptPosition: 'topLeft',
    	        autoHidePrompt: true,
    	    });
        });
     </script>
  <!-- END JS --> 
    <!-- END JS -->
</body>
</html>