<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<%@ taglib prefix="myshiro" uri="/customize-tags" %>
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>模板管理</title>
	<link href="${ctx}/assets/morris.js-0.4.3/morris.css" rel="stylesheet"><!-- MORRIS CHART CSS -->
	<link href="${ctx}/css/clndr.css" rel="stylesheet"><!-- CALENDER CSS -->
	<!-- 查询页面 -->
    <link href="${ctx}/css/bootstrap.min.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="${ctx}/css/bootstrap-reset.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="${ctx}/assets/font-awesome/css/font-awesome.css" rel="stylesheet"><!-- FONT AWESOME ICON STYLESHEET -->
    <link href="${ctx}/assets/data-tables/DT_bootstrap.css" rel="stylesheet" ><!-- DATATABLE CSS -->
    <link href="${ctx}/css/style.css" rel="stylesheet"><!-- THEME BASIC CSS -->
    <link href="${ctx}/css/style-responsive.css" rel="stylesheet"><!-- THEME BASIC RESPONSIVE  CSS -->
    <link href="${ctx}/css/style_self.css" rel="stylesheet">
	<script src="${ctx}/js/jquery-1.8.3.min.js" ></script><!-- BASIC JS LIABRARY 1.8.3 -->
	<script src="${ctx}/js/style_self.js" ></script>
	<script src="${ctx}/assets/bootstrap-datepicker/js/bootstrap-datepicker.js"></script><!-- 日期显示 -->
	<link rel="stylesheet" type="text/css" href="${ctx}/assets/bootstrap-datepicker/css/datepicker.css"><!-- BOOTSTRAP DATEPICKER PLUGIN CSS -->
	<script src="${ctx}/js/jquery.form.js"></script>
	<link rel="stylesheet" href="${ctx}/css/validationEngine.jquery.css">
	<script src="${ctx}/js/jquery.validationEngine-zh_CN.js"></script>
	<script src="${ctx}/js/jquery.validationEngine.js"></script>
	<style type="text/css">
		.change{
		    position: absolute;
		    overflow: hidden;
		    left: 10%;
		    top: 0;
		    opacity: 0;
		    width: 0px;
		}
	</style>
<script type="text/javascript">
$(function(){
	getAll();
	$('.labels').css('text-align','right');
	$('.row').css('margin','5px 0px');
	
	$('#editable-sample tbody').on('click', 'tr', function () {//选中的行
		//获取选中行的第一列的值
		nRow = $(this).parent().find("tr").index($(this)[0]);
		selectId=$(this).children("td").eq(0).text();
		temptr.css("background-color","");
        temptr = $(this);
        temptr.css("background-color","#99ffff");
	});
});

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
function GetRightMenu(index,value) { 
    var imageMenuData = new Array();
    var imageMenuData2 = new Array(); 
    imageMenuData.push({
        "text": "添加", "func": function () {
             $("#add").click();
        }
    }); 
    imageMenuData2.push(imageMenuData);
    return imageMenuData2;
}
//以上是右键特效的js
var temptr = $();
var nRow = null;
var selectId=null;
window.onload=function(){//给表添加滚动条
	$("#editable-sample").wrap('<div style="overflow-x:auto;width:100%"></div>');
	SetTableStyle();
};

function getAll(){
	$.ajaxSetup({async : false});
	$.post(
		"${ctx}/templet/getAll.action",
		function(data){
		$.each(data,function(i,item){
			$("#showResult").append("<tr>"
				+"<td style='display:none;'>"+item.id+"</td>"
				+"<td>"+(i+1)+"</td>"
				+"<td>"+item.updescribe+"</td>"
				+"<td>"+DateHandle(item.uploadtime)+"</td>"
				+"<td><a href='javascript:;' onclick='downloadFile(this)' id='"+item.id+"'><span class='label label-success'>下载</span></a></td>"
			);
		});
	});
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
	var objDate=null;
	  if(obj!=null){
		objDate=new Date(obj).Format("yyyy-MM-dd"); //创建一个日期对象表示当前时间     
	  }else{
		  objDate="";
	  }
	  return objDate; 
	}  
//清空输入
function emptyInput(){
	$("#id").val("");
	$("#updescribe").val("");
	$("#softtype").val("");
	$("#mfile").val("");
	$("#em").html("未上传文件");
	$("#uid").val(-1);
	$("#span").html("添加");
	$("#updescribe").addClass("validate[required]")
	$("#mfile").addClass("validate[required]")
	$('#uid').removeAttr("data-dismiss","modal");
}

//添加和修改
function addFile(uid){
	 $("#uid").removeAttr("data-dismiss")
		document.getElementById("addForm").action="";
		if(uid==-1){//执行添加
			if($('#addForm').validationEngine('validate')){
				console.log("添加！");
				//document.getElementById("addForm").action="${ctx}/templet/insert.action"
				//$("#addForm").submit();
				$('#addForm').ajaxSubmit({  
		            type:'post',  
		            url: '${ctx}/templet/insert.action',  
		            success:function(data){ 
							location.href="${ctx}/templet/toTemplet.action";
		            }  
		        });
				$('#uid').attr("data-dismiss","modal");
			}
		}else if(uid==1){//执行修改
			if($('#addForm').validationEngine('validate')){
			console.log("修改！");
	   	 	//document.getElementById("addForm").action="${ctx}/templet/update.action"
			//$("#addForm").submit();?temId='+$("#id").val()
	   	 	if($("#mfile").val()==''){
	   	 		$.post(
	   				"${ctx}/templet/updateNoFile.action?temId="+$("#id").val()+"&temDes="+$("#updescribe").val(),
	   				function(data){
	   					location.href="${ctx}/templet/toTemplet.action";
	   			});
	   	 	}else{
	   	 	$('#addForm').ajaxSubmit({  
	            type:'post',  
	            url: '${ctx}/templet/update.action',  
	            success:function(data){ 
						location.href="${ctx}/templet/toTemplet.action";
	            }  
	        });
		}
		}
		}
}

function toUpdate(){
	emptyInput();
	$('#uid').attr("data-dismiss","modal");
	$("#mfile").removeClass("validate[required]")
	$("#span").html("修改");
	if(selectId=="数据为空"){
		wzj.alert('温馨提示','数据为空！');
	}else if(selectId==null){
		wzj.alert('温馨提示','请选择一个模板！');
		return false;
	}else{
		$("#update").attr("href","#Modal_show");
		$("#uid").val(1);
		$.post(
				"${ctx}/templet/getDesById.action",
				"id="+selectId,
				function(data){//刷新页面
					var url=data.uprul;
					$("#id").val(data.id);
					$("#updescribe").val(data.updescribe);
					$("#em").html(url.split("/")[url.split("/").length-1]);
				}
			);
	}
}

function toDelete(){
	if(selectId=="数据为空"){
		wzj.alert('温馨提示','数据为空！');
	}else if(selectId==null){
		wzj.alert('温馨提示','请选择一个记录！');
		return false;
	}else{
		wzj.confirm('温馨提示','您确定删除？',function(flag){
			if(flag){
				$.post(
						"${ctx}/templet/delete.action",
						"id="+selectId,
						function(data){//刷新页面
							window.location.reload();
						}
					);
			}
		});		
	}
}

function downloadFile(element){
	var id=element.id;
	$.post(
			"${ctx}/templet/download.action",
			"id="+id,
			function(data){
				$.post(
						"${ctx}/templet/getTemp.action",
						"path="+data,
						function(dat){
							if(dat){
								window.open("${ctx}/commons/downloadFileByPath.action?paths="+data);
							}else{
								wzj.alert('温馨提示','该模板不存在！');
							}
						}
					);
			}
		);
}

</script>
</head>
<body style="overflow-x: hidden;overflow-y:hidden; ">
	<section class="panel">
	<div class="panel-body">
        <div class="adv-table editable-table ">
        <shiro:hasRole name="中心管理人员"> 
           <div class="clearfix">
              <div class="btn-group  nopaddingleft">
                 <button class="btn btn-success" data-toggle='modal' href='#Modal_show' onClick='emptyInput()' id="add">
             	添加 <i class="fa fa-plus"></i>
            	 </button>
          	  </div>
          	  <div class="btn-group">
                 <button class="btn btn-info" data-toggle='modal' href='' id="update" onclick="toUpdate()">
             	修改 <i class="fa fa-edit"></i>
            	 </button>
          	  </div>
          	  <div class="btn-group">
                 <button class="btn btn-danger" data-toggle='modal' onClick='toDelete()'>
             	删除  <i class="fa fa-minus"></i>
            	 </button>
          	  </div>
               </div>
       </shiro:hasRole>
               <div class="space15"></div>
               <table class="table table-bordered" id="editable-sample">
                  <thead>
                     <tr>
                     	 <th class="" style="display:none">id</th>
                        <th class="">序号</th>
                        <th class="">描述</th>
                        <th class="">上传时间</th>
                        <th class="">下载</th>
                     </tr>
                  </thead>
                  <tbody id="showResult">
                     <!-- 循环显示查询出来的结果 -->
                  </tbody>
               </table>
            </div>
            </div>
            </section>
          <div id="Modal_show" class="modal fade">
          <div class="modal-dialog">
            <div class="modal-content" style="width:100%">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                  &times;
                </button>
                <h4 class="modal-title"><span id="span">添加</span></h4>
              </div>
              <div class="modal-body">
			<form action="" method="post" id="addForm" enctype="multipart/form-data">
			<!-- 第0行 -->
			<div class="row" style="width:100%;display:none">
				<label class="col-sm-2 labels">id</label>
           		<div class="col-sm-10">
               		<input type="text" name="id" class="form-control" id="id" style="margin-top: 0px">
               	</div>
            </div>
			<!-- 第1行 -->
			<div class="row" style="width:100%">
				<label class="col-sm-2 labels">描述<b class="red">*</b></label>
            			<div class="col-sm-10">
                			<input type="text" class="form-control validate[required]" name="updescribe" id="updescribe">
                		</div>
                		
          	</div>
          	<br>
			<!-- <div class="row" style="width:100%">
				<label class="col-sm-2 labels">文件描述</label>
           		<div class="col-sm-10">
               		<select  name="describeId" class="form-control" id="describeId" style="margin-top: 0px">
               		<option value="1">营业执照</option>
               		<option value="2">印刷资格证</option>
               		<option value="3">质量手册</option>
               		<option value="4">申请表</option>
               		<option value="5">申请表_盖章</option>
               		<option value="6">评审表</option>
               		<option value="7">评审表_盖章</option>
               		<option value="8">样品</option>
               		<option value="9">分中心检测报告</option>
               		<option value="10">中心检测报告</option>
               		</select>
               	</div>
            </div>-->
            <!-- 第2行 -->
			<div class="row" style="width:100%">
				<label class="col-sm-2 labels">附件<b class="red">*</b></label>
                <div class="col-sm-8">
                <button type="button" onclick="mfile.click()" class="btn btn-info" style="float: left;">选择文件</button>
                  	<input type="file" id="mfile" name="mfile" class="change validate[required]">
                  	<p id="em" style="float: left;padding: 7px;margin: 0">未上传文件</p>
                </div>
			</div>
			</form>
			</div>
			
			<div class="modal-footer">
				<button class="btn btn-success" type="button" onclick="addFile(this.value)" id="uid">确定</button>
			</div>
		</div>
		</div>
	</div>
          
         
	    <!-- END SECTION -->
	 <!-- BEGIN JS -->
   <!--datatable的汉化在assets/data-tables/jquery.dataTables.js中修改 -->
    <script src="${ctx}/js/jquery.sparkline.js"></script><!-- SPARKLINE JS -->
    <script src="${ctx}/js/sparkline-chart.js"></script><!-- SPARKLINE CHART JS -->
    <script src="${ctx}/js/count.js"></script><!-- COUNT JS -->
<!--<script src="${ctx}/js/advanced-form-components.js" ></script><!--  ADVANCE FORM COMPONENTS PAGE JS  -->
	<script src="${ctx}/js/bootstrap.min.js" ></script><!-- BOOTSTRAP JS  -->
	<script src="${ctx}/js/jquery.dcjqaccordion.2.7.js"></script><!-- ACCORDING JS -->
	<script src="${ctx}/js/jquery.scrollTo.min.js" ></script><!-- SCROLLTO JS  -->
	<script src="${ctx}/js/jquery.nicescroll.js" > </script><!-- NICESCROLL JS  -->
	<script src="${ctx}/assets/data-tables/jquery.dataTables.js"></script><!-- DATATABLE JS  -->
	<script src="${ctx}/assets/data-tables/DT_bootstrap.js"></script><!-- DATATABLE JS  -->
	<script src="${ctx}/js/respond.min.js" ></script><!-- RESPOND JS  -->
	<script src="${ctx}/js/common-scripts.js" ></script><!-- BASIC COMMON JS  -->
	<script src="${ctx}/js/editable-table-log.js" ></script><!-- EDITABLE TABLE JS  -->
     <!-- EDITABLE TABLE FUNCTION  -->
     <script>
        jQuery(document).ready(function() {
            EditableTable.init();
			 $("#addForm").validationEngine({
	    	        scrollOffset: 98,//必须设置，因为Toolbar position为Fixed
	    	        promptPosition: 'topLeft',
	    	        autoHidePrompt: true,
	    	 });
        });
	    $('#mfile').on('change', function( e ){
            //e.currentTarget.files 是一个数组，如果支持多个文件，则需要遍历
            var name = e.currentTarget.files[0].name;
            $('#em').text(name);
     });
        </script>
  <!-- END JS --> 
</body>
</html>