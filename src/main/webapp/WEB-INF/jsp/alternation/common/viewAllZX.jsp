<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<%@ taglib prefix="myshiro" uri="/customize-tags" %>
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="height:100%">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>分中心审批</title>
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
/*              body{font-size:12px;} */
/*              p,div{margin:0;padding:0} */
/*              .textarea{ */
/*             width:350px;height:80px;position:absolute;background:none;z-index:9 */
/*             } */
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
   // obj_select.onchange = function(){
   //     obj_div.style.display = this.value==3? "block" : "none";
   // }
   comName=$("#comName").val();
	taskId=$("#taskId").val();
	id=$("#id").val();
	titleNo=$("#titleNo").val();
	$("#info").attr("src","${ctx}/alternation/toView.action?id="+id+"&taskId="+taskId);
	
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
	$("#editable-sample_wrapper").attr("style","display:none");   
	taskId=$("#taskId").val();
	id=$("#id").val();
	$("#info").attr("src","${ctx}/alternation/toView.action?id="+id+"&taskId="+taskId);
}
//意见
function getComment(){
	$('#btn .btn-info').removeClass('btn-info').addClass('btn-default')
	$('#suggest').removeClass('btn-default').addClass('btn-info')
	$("#iframe").css("display","none");
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
	//提交
	function pass(flag){
		//通过
		if(flag){
			var comment="";
			var comment2=$("#passComment2").val();
			comment=comment2;
			$.post(
					"${ctx}/alternation/saveCommentZX.action",
					"taskId="+taskId+"&comment="+comment,
					function(data){
						location.href="${ctx}/alternation/expressMessage/toTasks.action"
					}
				);
		}else{
			//驳回
			var reason="";
			reason=$("#failComment").val();
			$.post(
					"${ctx}/alternation/climeAndcompleteTask.action",
					"taskId="+taskId+"&comment="+reason+"&role=中心复核人员&result=false&status=4",
					function(data){
						location.href="${ctx}/alternation/expressMessage/toTasks.action"
					}
				);
		}
	}
</script>
</head>
<body style="height:100%">
               <section class="panel" style="height:90%">
                  <div class="panel-body" style="height:95%">
                     <div class="adv-table editable-table" style="height:100%">
                        <div class="clearfix" id="btn">
                        <div class="btn-group nopaddingleft">
                              <button onclick="view();" class="btn btn-info"  data-toggle='modal' href='' id="dialog">
                            	  查看申请表 <i class="fa fa-eye"></i>
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
                        <div id="iframe" style="height:100%">
                        <input type="hidden" value="${titleNo}" id="titleNo">
                        <input type="hidden" value="${id}" id="id">
                        <input type="hidden" value="${taskId}" id="taskId">
                        <input type="hidden" value="${comName}" id="comName">
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
                     </div>
                    
               </section>
               <div style="position: fixed;width: 100%;bottom: 0px;"> 
                <div class="row">
                  <div class="col-lg-12">
                     <section class="panel">
                        <div class="panel-body">
                           <span class="form-horizontal tasi-form">
                           <div class="form-group text-center">
                            <div class="btn-group">
                              <input type="button" class="btn-info  btn" onclick="window.history.go(-1)" value="返回">
                            </div>
                          </div> 	 						 					 
                           </span>
                        </div>
                     </section>
                  </div>
               </div>

              </div>
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
			$('#other').hide()
			$('#pass1').show();
			$('#pass2').hide();
		}else{
			$('#isPass').hide()
			$('#isNotPass').show()
			$('#other').hide()
			$('#pass2').show();
			$('#pass1').hide();
		}
     }
     $('#reason').on('change',function(){
  		var str = $(this).find('option:selected').text();
  		if(str=='其他'){
				$('#other').show()
      	}else{
      		$('#other').hide()
        }
     });
     </script>
</body>
</html>