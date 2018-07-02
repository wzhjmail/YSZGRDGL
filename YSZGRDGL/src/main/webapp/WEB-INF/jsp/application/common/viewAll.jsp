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
	$("#info").attr("src","${ctx}/application/toView.action?id="+id+"&taskId="+taskId);
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
	$("#info").attr("src","${ctx}/application/toView.action?id="+id+"&taskId="+taskId);
}
//查看评审表
function viewReview(){
	$('#btn .btn-info').removeClass('btn-info').addClass('btn-default')
	$('#viewR').removeClass('btn-default').addClass('btn-info')
	$("#iframe").css("display","block");
	$("#editable-sample_wrapper").attr("style","display:none");
	$("#info").attr("src","${ctx}/application/toViewPS.action?titleNo="+titleNo+"&taskId="+taskId);
}
//样品信息
function showSample(){
	$('#btn .btn-info').removeClass('btn-info').addClass('btn-default')
	$('#sample').removeClass('btn-default').addClass('btn-info')
	$("#iframe").css("display","block");
	$("#editable-sample_wrapper").attr("style","display:none");
	$("#info").attr("src","${ctx}/pbtSample/toFRsample.action?comName="+comName+"&titleNo="+titleNo);
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
		if ($('#passform').validationEngine('validate')) {
			var p1=$('input[name="isPass"]').get(0).checked;
			var p2=$('input[name="isPass"]').get(1).checked;
			if(!(p1||p2)){
				wzj.alert("温馨提示","请选择单选按钮")
				return;
			}
			//通过
			if(flag){
				var comment="";
				var comment2=$("#passComment2").val();
				comment=comment2;
				$.post(
						"${ctx}/application/saveComment.action",
						"taskId="+taskId+"&comment="+comment,
						function(data){
							location.href="${ctx}/application/expressMessage/toSendMessage.action"
						}
					);
			}else{
				//驳回
				var reason="";
				var back="";
				if($("#back").val()==""){
					wzj.alert('温馨提示','请选择退回至哪一步！');
					return false;
				}else if($("#backReason").val()==""){
					wzj.alert('温馨提示','请选择退回原因！');
					return false;
				}else if($("#back").val()!=""&&$("#backReason").val()!=""){
					back=$("#back").val();
					if($("#backReason").val()==3){
						if($("#comment").val()==""){
							wzj.alert('温馨提示','请填写其他原因！');
							return false;
						}else{
							reason=$("#comment").val();
						}
					}else{
						reason=$("#backReason option:checked").text();
					}
				}
				$.post(
						"${ctx}/application/expressMessage/goBack.action",
						"taskId="+taskId+"&result="+back+"&comment="+reason,
						function(data){
							location.href="${ctx}/application/expressMessage/toSendMessage.action"
						}
					);
			}
		}
	}
</script>
</head>
<body style="height:100%">
               <section class="panel" style="height:90%">
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
                        <div id="iframe" style="height:100%">
                        <input type="hidden" value="${titleNo}" id="titleNo">
                        <input type="hidden" value="${id}" id="id">
                        <input type="hidden" value="${taskId}" id="taskId">
                        <input type="hidden" value="${comName}" id="comName">
                        <iframe id="info" style="min-height: 80%;" src="" frameborder="0" width="100%" leftmargin="0" topmargin="0"></iframe>
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
               <div style="position: fixed;width: 100%;bottom: 0px;"> 
                <div class="row">
                  <div class="col-lg-12">
                     <section class="panel">
                        <div class="panel-body">
                        <form id="passform">
                           <span class="form-horizontal tasi-form">
						   <div class="form-group">
                             <div class="col-sm-2"></div>
                             <div class="col-sm-2">
                             	<input type="radio" name="isPass" class="validate[required]" onclick="clickbox(true)">通过
                             </div>
                             <div class="col-sm-2">
                                <input type="radio" name="isPass" class="validate[required]" onclick="clickbox(false)">不通过
                             </div>  
                           </div> 
                           <div class="form-group" id="isPass" style="display: none;">
                            	<div class="col-sm-2 text-right">
			                            	<label>通过</label>
			                            </div>
			                            <div class="col-sm-10">
			                            	<textarea rows="" cols="" id="passComment2" class="form-control" >所抽查的样品经过检验，能够符合国家标注的要求， 基本具备商品条码的承印能力。</textarea>
			                            </div>
                           </div>
                           <div class="form-group" id="isNotPass" style="display: none;">
		                           	<div class="col-sm-2 text-right">
		                             	<label>退回至</label>
		                            </div>
		                           	<div class="col-sm-2">
		                           		<select id="back" style="width: 100%">
				                         <option value="">-请选择-</option>
				                         <option value="4">业务受理</option>
				                         <option value="3">现场评审</option>
				                         <option value="2">录取样品信息</option>
				                         <option value="1">上传检验报告</option>
                 						</select>
		                            </div>
		                           	<div class="col-sm-2 text-right">
		                             	<label>退回原因</label>
		                            </div>
		                           	<div class="col-sm-2">
		                           		<select id="backReason">
		                           			<option value="">-请选择-</option>
					                         <option value="1">营业执照不合格</option>
					                         <option value="2">资格认定不合格</option>
					                         <option value="3">其他</option>
		                           		</select>
		                            </div>
		                            <div class="col-sm-8"></div>
		                       </div>
		                      <div class="form-group" id="other" style="display: none;">
			                            <div class="col-sm-2 text-right">
			                            	<label>其他原因</label>
			                            </div>
			                            <div class="col-sm-10">
			                            	<textarea id="comment" rows="" cols="" class="form-control"></textarea>
			                            </div>
                           		</div>
                           <div class="form-group text-center">
                            <div class="btn-group">
                              <input type="button" class="btn-info  btn" onclick="window.history.go(-1)" value="返回">
                            </div>
                            <div class="btn-group">
                               <input id="pass1" type="button" class="btn-success  btn" value="提交" onclick="pass(true)">
                               <input id="pass2" style="display:none" type="button" class="btn-success  btn" value="提交" onclick="pass(false)">
                            </div>
                          </div> 	 						 					 
                           </span>
                           </form>
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
 	$(function(){
		 $("#passform").validationEngine({
    	        scrollOffset: 98,//必须设置，因为Toolbar position为Fixed
    	        promptPosition: 'topLeft',
    	        autoHidePrompt: true,
    	    });
    })
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
     $('#backReason').on('change',function(){
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