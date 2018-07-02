<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<%@ taglib prefix="myshiro" uri="/customize-tags" %>
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="height:100%">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编码中心核准</title>
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
	<link rel="stylesheet" href="${ctx}/css/validationEngine.jquery.css">
	<script src="${ctx}/js/jquery-1.8.3.min.js" ></script><!-- BASIC JS LIABRARY 1.8.3 -->
	<script src="${ctx}/js/style_self.js"></script>
	<script src="${ctx}/js/jquery.validationEngine-zh_CN.js"></script>
	<script src="${ctx}/js/jquery.validationEngine.js"></script>
	<style type="text/css">
		.label{
			line-height: unset;
		}
		a{
			cursor: pointer;
		}
		.textarea{
            width:350px;height:80px;position:absolute;background:none;z-index:9
            }
          .note{
          position:absolute;line-height:20px;padding:3px 5px;
          }
	</style>
<script type="text/javascript">
var titleNo=null;
var id=null;
var taskId=null;
var comName=null;
$(function(){
	var obj_select = document.getElementById("backReason");
    var obj_div = document.getElementById("otherReason");
    comName=$("#comName").val();
	taskId=$("#taskId").val();
	id=$("#id").val();
	titleNo=$("#titleNo").val();
	$("#info").attr("src","${ctx}/application/toView.action?titleNo="+titleNo+"&taskId="+taskId+"&id="+id);
	var backPage=$("#backPage").val();
	if(backPage=='发证'){
		$("#mess").attr("style","display:none");
		$("#radioFalse").attr("style","display:none");
		$("#pass1").attr("style","display:none");
	}
});//初始化
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
//查看申请表
function view(){
	$('#btn .btn-info').removeClass('btn-info').addClass('btn-default')
	$('#dialog').removeClass('btn-default').addClass('btn-info')
	$("#iframe").css("display","block");
	$("#editable-msg").attr("style","display:none");
	$("#editable-sample_wrapper").attr("style","display:none");
	$("#info").attr("src","${ctx}/application/toView.action?titleNo="+titleNo+"&taskId="+taskId+"&id="+id);
}
//查看评审表
function viewReview(){
	$('#btn .btn-info').removeClass('btn-info').addClass('btn-default')
	$('#viewR').removeClass('btn-default').addClass('btn-info')
	$("#iframe").css("display","block");
	$("#editable-msg").attr("style","display:none");
	$("#editable-sample_wrapper").attr("style","display:none");
	$("#info").attr("src","${ctx}/application/toViewPS.action?titleNo="+titleNo+"&taskId="+taskId);
}
//样品信息
function showSample(){
	$('#btn .btn-info').removeClass('btn-info').addClass('btn-default')
	$('#sample').removeClass('btn-default').addClass('btn-info')
	$("#iframe").css("display","block");
	$("#editable-msg").attr("style","display:none");
	$("#editable-sample_wrapper").attr("style","display:none");
	$("#info").attr("src","${ctx}/pbtSample/toFRsamplePZ.action?comName="+comName+"&titleNo="+titleNo);
}
//意见
function getComment(){
	$('#btn .btn-info').removeClass('btn-info').addClass('btn-default')
	$('#suggest').removeClass('btn-default').addClass('btn-info')
	$("#iframe").css("display","none");
	$("#editable-msg").attr("style","display:none");
	$("#editable-sample").attr("style","width:100%;");
	$("#editable-sample_wrapper").show();
	$("#commentBody").empty();//清空
	$.post(
		"${ctx}/application/getComment",
		"taskId="+taskId+"&companyName="+comName,
		function(data){
			$.each(data,function(i,item){
				var str=DateHandle(item.time);
				$("#commentBody").append(
					"<tr><td>"+str+"</td><td>"
					+item.userId+"</td><td>"
					+item.fullMessage+"</td></tr>");
			});
			$('#editable-sample').dataTable(oTable).fnDraw(false)
		}
	);
}
Date.prototype.Format = function (fmt) { //author: meizz 
    var o = {
        "M+": this. getMonth() + 1, //月份 
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
	//提交
	function pass(flag){
		//通过
		var backPage=$("#backPage").val();
		var comment="";
		if(flag){
			var comment2=$("#passComment2").val();
				comment=comment2;
		}else{
			var comment4=$("#notpassComment2").val();
				comment=comment4;
		}
		$.post(
				"${ctx}/recognition/review/completeCommonTask.action",
				"taskId="+taskId+"&result="+flag+"&comment="+comment+"&backPage="+backPage,
				function(data){
					location.href='${ctx}/recognition/check/toTasks.action';
				}
			);
	}
</script>
</head>
<body style="height:100%">
               <section class="panel" style="height:100%">
                  <div class="panel-body" style="height:100%">
                     <div class="adv-table editable-table" style="height:100%">
                        <div class="clearfix" id="btn">
                        <div class="btn-group nopaddingleft">
                              <button onclick="view();" class="btn btn-info"  data-toggle='modal' href='' id="dialog">
                            	  查看申请表 <i class="fa fa-eye"></i>
                              </button>
                           </div>
                        <div class="btn-group">
                           <button id = "viewR" onclick="viewReview()" class="btn btn-default"  target="_blank">
                            	查看评审表 <i class="fa fa-book"></i>
                           </button>
                           </div>
                           <div class="btn-group ">
                              <button onclick="showSample()" id="sample" class="btn btn-default" >
                            	 样品信息<i class="fa  fa-puzzle-piece"></i>
                              </button>
                           </div>
                           <div class="btn-group ">
                              <button id="suggest" class="btn btn-default" onClick='getComment()' data-toggle='modal' href=''>
                            	  意见 <i class="fa fa-comment"></i>
                              </button>
                           </div>
                        </div>
                        <div class="space15"></div>
                        <hr>
                        <div id="iframe" style="height:90%">
                        <input type="hidden" value="${titleNo}" id="titleNo">
                        <input type="hidden" value="${id}" id="id">
                        <input type="hidden" value="${taskId}" id="taskId">
                        <input type="hidden" value="${comName}" id="comName">
                        <input type="hidden" value="${backPage}" id="backPage">
                        <iframe id="info" style="min-height: 100%;" src="" frameborder="0" width="100%" leftmargin="0" topmargin="0"></iframe>
                       </div>
                       <table class="table table-bordered" style="width:100%;display:none;" id="editable-sample">
                           <thead>
                              <tr>
                                 <th class="">时间</th>
                                 <th class="">意见人</th>
                                 <th class="">意见</th>
                              </tr>
                           </thead>
                           <tbody id="commentBody">
                              <!-- 循环显示查询出来的结果 -->
                             
                           </tbody>
                        </table>
                        </div>
                <!--    <div class="row" style="width:100%">
                        <label class="col-sm-2 control-label labels"> 退回至：</label>
                         <div class="col-sm-2">
                         <select id="back" style="width: 100%">
                         <option value="">-请选择-</option>
                         <option value="return4">业务受理</option>
                         <option value="return3">现场评审</option>
                         <option value="return2">录取样品信息</option>
                         <option value="return1">上传检验报告</option>
                 </select>
                 
                 </div>
                 <div class="row" style="width:100%">
                 <label class="col-sm-2 control-label labels"> 退回原因：</label>
                         <div class="col-sm-2">
                         <select id="backReason" style="width: 100%">
                         <option value="">-请选择-</option>
                         <option value="1">营业执照不合格</option>
                         <option value="2">资格认定不合格</option>
                         <option value="3">其他</option>
                 </select>
                 </div>
                 <div id="otherReason" style="display:none" class="col-sm-8">
                 <label class="col-sm-3 control-label labels">其他原因</label>
                       <div class="col-sm-9">
                          <textarea class="form-control"  rows="2" id="comment"></textarea>
                       </div>
                  </div>
                 </div>
                 <div class="row">
                 <section class="panel">
	               <div class="panel-body">
	               <div class="col-lg-4 col-sm-4"></div>
	               		<div class="col-lg-4 col-sm-4"><input type="button" onclick="goBack()" class="btn-info btn-block btn" value="退回"></div>
	               		<div class="col-lg-4 col-sm-4"></div>
	               </div>
	               </section>
                 </div>-->
                     </div>
                    
               </section>
	    <!-- END SECTION -->
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
	<script src="${ctx}/js/editable-table.js" ></script> <!--EDITABLE TABLE JS  -->
		<script src="${ctx}/js/commentTable.js" ></script>
     <!-- EDITABLE TABLE FUNCTION  -->
     <script type="text/javascript">

     function clickbox(flag){
		if(flag){
			$('#isPass').show()
			$('#isNotPass').hide()	
			$('#pass2').hide()
			$('#pass1').show()
		}else{
			$('#isPass').hide()
			$('#isNotPass').show()
			$('#pass1').hide()
			$('#pass2').show()
		}
     }
     function goBack(){
    	var backPage=$("#backPage").val();
    	if(backPage=='发证'){
    		window.location.href="${ctx}/recognition/check/toIssuing.action";
    	}else{
    		window.location.href="${ctx}/recognition/check/toTasks.action";
    	}
     }
     </script>
</body>
</html>