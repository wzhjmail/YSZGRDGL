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
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
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
	<link href="${ctx}/css/fileinput.min.css" media="all" rel="stylesheet" type="text/css" />
	<script src="${ctx}/js/fileinput.min.js" type="text/javascript"></script>
	<script src="${ctx}/js/locales/zh.js" type="text/javascript"></script>
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
        .modal-backdrop{
        	z-index: -1
        }
        .modal-content{
            border: 1px solid #ccc;
        }
        .kv-file-upload{
        	display: none; 
        }        
        .btn-group>.btn:first-child:not(:last-child):not(.dropdown-toggle) {
			border-top-right-radius: 4px;
    		border-bottom-right-radius: 4px;
		}
		.btn-group>.btn:last-child:not(:first-child){
		    border-top-left-radius: 4px;
		    border-bottom-left-radius: 4px;
		}
		
          .panel-bottom{
		    border-top: 1px solid #ddd;
		    position: fixed;
		    bottom: 0px;
		    background: #fff;
		    width: 100%;
		    height: 215px;
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
	//加载查看的资料
	$.post(
		"${ctx}/recognition/expressMessage/allFiles.action",
		"titleNo="+titleNo+"&comName="+comName,
		function(data){
			$.each(data,function(i,item){
				$("#datum").append('<option value="'+item.describeId+'">'+item.updescribe+'</option>');
			})
			
		}
	);
	$.post(//获取已经保存的意见
		"${ctx}/application/getSavedComment.action",
		"taskId="+taskId,
		function(data){
			if(data!=null&&data!=""&&data.fullMessage.length>0)
			$("#passComment2").val(data.fullMessage);
		}
	);
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
		comment=$("#passComment2").val();
		var passCheck = document.getElementsByName("isPass");
		if((passCheck[0].checked==false)&&(passCheck[1].checked==false)){
			wzj.alert('温馨提示','请选择单选按钮！');
		}else{
			$.post(
					"${ctx}/recognition/review/completeCommonTask.action",
					"taskId="+taskId+"&result="+flag+"&comment="+comment+"&backPage="+backPage,
					function(data){
						location.href='${ctx}/recognition/check/toTasks.action';
					}
				);
		}
	}
	function viewDatum(type){
		var arry=["1","2","3","4","5","6","7"];
		if(arry.indexOf(type)!=-1){
		//if(type>=1||type<=7){
			window.open("../../viewPic.jsp?flag=1&titleNo="+titleNo+"&type="+type);	
		}else if(type=="分中心检测报告"||type=="中心检测报告"){
			var sel=document.getElementById('datum');    
		    var name1=sel.options[sel.selectedIndex].text;
		    var name2;
			if(type=="分中心检测报告"){
				name2=name1.split("分中心检测报告")[0]; 
				$("#toAttaches").attr("href","#Modal_show1");
				$("#toAttaches").click();
			}else if(type=="中心检测报告"){
				name2=name1.split("中心检测报告")[0]; 
				$("#toAttaches").attr("href","#Modal_show1");
				$("#toAttaches").click();
			}
				$("#reportResult").html("");
				$.ajaxSetup({async : false});
				$.post(
						"${ctx}/commons/getSampleAttach.action",
						"titleNo="+titleNo+"&sampleId="+name2,
						function(data){
						$.each(data,function(i,item){
							var fileTypeStart=item.uprul.lastIndexOf('.');
							var fileType=item.uprul.substring(fileTypeStart+1,item.uprul.length);
							var fileNameStart=item.uprul.lastIndexOf('/');
							var fileName=item.uprul.substring(fileNameStart+1,item.uprul.length);
							var types=["pdf","txt","jpg","jpeg","png","bmp","gif","svg","psd","pcd","raw","tiff"];
							if(item.updescribe==type){
							$("#reportResult").append("<tr>"
									+"<td>"+(i+1)+"</td>"
									+"<td>"+fileName+"</td>"
									+"<td>"+fileType+"</td>"
									+'<td><button onclick="showView(this.value)" class="btn btn-info"  value="'+item.uprul+'">'
									+(types.indexOf(fileType)!=-1?"查看":"下载")+'<i class="fa fa-eye"></i></button></td></tr>'
							);
							}
						});
					});
		}else{
			$("#attachResult").html("");
			$.ajaxSetup({async : false});
			$.post(
					"${ctx}/commons/getSampleAttach.action",
					"titleNo="+titleNo+"&sampleId="+type,
					function(data){
					$.each(data,function(i,item){
						var fileTypeStart=item.uprul.lastIndexOf('.');
						var fileType=item.uprul.substring(fileTypeStart+1,item.uprul.length);
						var fileNameStart=item.uprul.lastIndexOf('/');
						var fileName=item.uprul.substring(fileNameStart+1,item.uprul.length);
						$("#URL").val(item.uprul);
						var types=["pdf","txt","jpg","jpeg","png","bmp","gif","svg","psd","pcd","raw","tiff"];
						if(item.updescribe=="样品附件"){
							$("#attachResult").append("<tr>"
									+"<td>"+(i+1)+"</td>"
									+"<td>"+fileName+"</td>"
									+"<td>"+fileType+"</td>"
									+'<td><button onclick="showView(this.value)" class="btn btn-info" value="'+item.uprul+'">'
									+(types.indexOf(fileType)!=-1?"查看":"下载")+'<i class="fa fa-eye"></i></button></td></tr>'
							);
						}
					});
				});
			$("#toAttaches").attr("href","#Modal_show");
			$("#toAttaches").click();
		}
	}
	function saveMsg(){
		var comment="";
		comment=$("#passComment2").val();
		if(comment!=null&&comment.length>0){
			$.post(
				"${ctx}/application/saveComment.action",
				"taskId="+taskId+"&comment="+comment,
				function(data){
					wzj.alert("温馨提示","意见保存成功");
				}
			);
		}
	}	
function showView(upurl){
	window.open('../../viewPic.jsp?flag=2&code='+upurl)
}
</script>
</head>
<body style="height:100%;overflow-y: hidden;">
               <section class="panel" id="panel" style="height:100%">
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
                           <div class="btn-group " style="display:none">
                             <button data-toggle='modal' href='' class="btn btn-info" id="toAttaches">
                            	 查看附件 <i class="fa fa-file"></i>
                              </button>
                           </div>
                           <div class="btn-group ">
                           <label>查看申请资料：</label>
                              <select id="datum" onchange="viewDatum(this.options[this.options.selectedIndex].value)"></select>
                           </div>
                        </div>
                        <div class="space15"></div>
                        <hr>
                        <div id="iframe" style="height:65%">
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
                     </div>
                    
               </section>
               <div class="panel-bottom">
                <div class="row">
                  <div class="col-lg-12" id="mess">
                     <section class="panel" >
                        <div class="panel-body">
                           <span class="form-horizontal tasi-form">
						   <div class="form-group">
                             <div class="col-sm-2"></div>
                             <div class="col-sm-2" id="radioTrue">
                             	<input type="radio" name="isPass" onclick="clickbox(true)">通过
                             </div>
                             <div class="col-sm-2" id="radioFalse">
                                <input type="radio" name="isPass" onclick="clickbox(false)">不通过
                             </div>  
                           </div> 
                           <div class="form-group" id="isPass">
                            	<div class="col-sm-2 text-right">
			                            	<label>意见</label>
			                            </div>
			                            <div class="col-sm-8">
			                            	<textarea rows="" cols="" id="passComment2" class="form-control" >经复认，该企业符合复认条件，准予发证。</textarea>
			                            </div>
                           </div>
		                    <div class="form-group text-center" >
                            <div class="btn-group">
                              <input type="button" class="btn-danger  btn" onclick="goBack()" value="返回">
                            </div>
                            <div class="btn-group">
                              <button class="btn-info  btn" onclick="saveMsg()">保存意见</button>
                            </div>
                            <div class="btn-group">
                               <input type="button" id="pass1" class="btn-success  btn" value="提交" onclick="pass(true)">
                               <input type="button" id="pass2" style="display:none" class="btn-success  btn" value="提交" onclick="pass(false)">
                            </div>
                          </div> 
                           </span>
                        </div>
                     </section>
                  </div>
               </div>
               </div>
               <div id="Modal_show" class="modal fade">
	          <div class="modal-dialog">
	            <div class="modal-content">
	              <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
	                  &times;
	                </button>
	                <h4 class="modal-title">附件</h4>
	              </div>
	              <div class="modal-body" style="height: 500px">
	              	<table class="table table-bordered" style="width:100%" id="editable-sample">
                           <thead>
                              <tr>
                              	 <th class="">序号</th>
                                 <th class="">文件名</th>
								 <th class="">类型</th>
								 <th class="">查看</th>
                              </tr>
                           </thead>
                           <tbody id="attachResult">
                              <!-- 循环显示查询出来的结果 -->
                             
                           </tbody>
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
	                <h4 class="modal-title">附件</h4>
	              </div>
	              <div class="modal-body" style="height: 500px">
	              	<table class="table table-bordered" style="width:100%" id="editable-sample">
                           <thead>
                              <tr>
                              	 <th class="">序号</th>
                                 <th class="">文件名</th>
								 <th class="">类型</th>
								 <th class="">查看</th>
                              </tr>
                           </thead>
                           <tbody id="reportResult">
                              <!-- 循环显示查询出来的结果 -->
                             
                           </tbody>
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
 
	 $("#ascrail2000-hr").remove()
	 $("#ascrail2000").remove()
     function clickbox(flag){
		if(flag){
			$("#sampleId").val($("#id").val());
			$("#titleno").val($("#titleNo").val());
			$("#mulfile").fileinput({
		    	uploadUrl:"${ctx}/commons/uploadSampleFile?titleNo="+$("#titleNo").val()+"&describe=扫描文件&sampleId="+$("#sampleId").val(),
				fileType: "any",
				language : 'zh',
				enctype : 'multipart/form-data',
				showUpload:false,
				showPreview:true,
			});
			//$('#isPass').show()
			$('#uploadForm').show()
			//$('#isNotPass').hide()	
			$('#pass2').hide()
			$('#pass1').show()
		}else{
			//$('#isPass').hide()
			$('#uploadForm').hide()
			//$('#isNotPass').show()
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
     console.log($("#content",parent.document)[0].clientHeight)
     var height = $("#content",parent.document)[0].clientHeight-215
     $("#panel1").css("height",height+"px")
     $("#iframe").css("height",(height-95)+"px")
     </script>
</body>
</html>