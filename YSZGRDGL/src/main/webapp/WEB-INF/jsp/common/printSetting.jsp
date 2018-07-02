<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="myshiro" uri="/customize-tags" %>
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>打印设置</title>
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
	<link rel="stylesheet" href="${ctx}/css/validationEngine.jquery.css">
	<script src="${ctx}/js/jquery.validationEngine-zh_CN.js"></script>
	<script src="${ctx}/js/jquery.validationEngine.js"></script>
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
    imageMenuData.push({
        "text": "修改", "func": function () {
        	if(selectId==null){
        		wzj.alert('温馨提示', '请选择一条数据！')
        		return false;
        	}else if(selectId==""){
        		wzj.alert('温馨提示','数据为空！');
        		return false;
        	}else{
        		$("#update").click();
        	}
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
		"${ctx}/certposition/getAll.action",
		function(data){
		$.each(data,function(i,item){
			$("#showResult").append("<tr>"
				+"<td style='display:none;'>"+item.id+"</td>"
				+"<td>"+(i+1)+"</td>"
				+"<td>"+item.name+"</td>"
				+"<td>"+item.up+"px</td>"
				+"<td>"+item.down+"px</td>"
				+"<td>"+item.leftPadding+"px</td>"
				+"<td>"+item.rightPadding+"px</td>"
				+"<td>"+(item.used==true?"是":"否")+"</td></tr>"
			);
			if(item.used)
			$("#using").append("<option selected = 'selected' value = "+item.id+">"+item.name+"</option>");
			else
			$("#using").append("<option value = "+item.id+">"+item.name+"</option>");
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
	$("#span").html("添加");
	$("#name").val("");
	$("#up").val("");
	$("#down").val("");
	$("#right").val("");
	$("#left").val("");
	$("#uid").val(-1);
}

//添加和修改
function addSetting(uid){
	if($('#addForm').validationEngine('validate')){
		document.getElementById("addForm").action="";
		 var name=$("#name").val();
		if(uid==-1){
        $.post(
           	"${ctx}/certposition/getCount.action",
           	"name="+name,
           	function(data){//返回值是改名称的数量
           		if(data>=1){
           			wzj.alert('温馨提示','该打印设置已存在！');
           		}else{
           		 document.getElementById("addForm").action="${ctx}/certposition/insert.action"
           	        $("#addForm").submit();
           		}
           	}
        );
       
		}else{
        $.post(
               	"${ctx}/certposition/getCount.action",
               	"name="+name,
               	function(data){//返回值是改名称的数量
               		if(data>1){
               			wzj.alert('温馨提示','该打印设置已存在！');
               		}else if(data==1){
               			if(name==$("#oldName").val()){
               				document.getElementById("addForm").action="${ctx}/certposition/update.action";
               				$("#addForm").submit();
               			}else{
               				wzj.alert('温馨提示','该打印设置已存在！');
               			}
               		}else{
               			document.getElementById("addForm").action="${ctx}/certposition/update.action";
           				$("#addForm").submit();
               		}
               		$('#uid').attr("data-dismiss","modal");
               	}
            );
		}
	}
}

//预备更新
function toUpdate(){
	emptyInput();
	$("#span").html("修改");
	if(selectId=="数据为空"){
		wzj.alert('温馨提示','数据为空！');
	}else if(selectId==null){
		wzj.alert('温馨提示','请选择一个记录！');
		return false;
	}else{
		$("#update").attr("href","#Modal_show");
		$.post(
				"${ctx}/certposition/select",
				"id="+selectId,
				function(data){//刷新页面
					$("#span").html("修改");
					$("#id").val(data.id);
					$("#name").val(data.name);
					$("#oldName").val(data.name);
					$("#up").val(data.up);
					$("#down").val(data.down);
					$("#left").val(data.leftPadding);
					$("#right").val(data.rightPadding);
					$("#uid").val(data.id);
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
						"${ctx}/certposition/delete.action",
						"id="+selectId,
						function(data){//刷新页面
						location.href="${ctx}/certposition/toSetting.action"
						}
					);
			}
		});		
	}
}
function changeUsing(){
	if($("#using").val()==null){
		wzj.alert('温馨提示','数据为空！');
		return false;
	}else{
		wzj.confirm('温馨提示','确定更改打印机？',function(flag){
			if(flag){
				$.post(
						"${ctx}/certposition/changeUsed.action",
						"id="+$("#using").val(),
						function(data){//刷新页面
							window.location.reload();
						}
					);
			}
		});		
	}
	
}

</script>
</head>
<body style="overflow-x: hidden;overflow-y:hidden; ">
	<section class="panel">
	<div class="panel-body">
        <div class="adv-table editable-table ">
           <div class="clearfix">
              <div class="btn-group  nopaddingleft">
                 <button class="btn btn-success" data-toggle='modal' href='#Modal_show' onClick='emptyInput()' id="add">
             	添加 <i class="fa fa-plus"></i>
             </button>
          </div>
          <div class="btn-group ">
				<button class="btn btn-success" data-toggle='modal' href='' onClick='toUpdate()' id="update">
                     	修改 <i class="fa fa-edit"></i>
                     </button>
          </div>
		<div class="btn-group ">
				<button class="btn btn-danger" data-toggle='modal' href='' onClick='toDelete()'>
                     	删除 <i class="fa fa-minus"></i>
               </button>
          </div>

          <div class="btn-group ">
          		当前使用的打印机：<select id="using" name="using" onChange='changeUsing()'></select>
          </div>
          
               </div>
               <div class="space15"></div>
               <table class="table table-bordered" id="editable-sample">
                  <thead>
                     <tr>
                     	 <th class="" style="display:none">id</th>
                        <th class="">序号</th>
                        <th class="">名称</th>
                        <th class="">上边距</th>
                        <th class="">下边距</th>
                        <th class="">左边距</th>
                        <th class="">右边距</th>
                        <th class="">使用中</th>
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
				<label class="col-sm-2 labels">名称<b class="red">*</b></label>
           		<div class="col-sm-10">
               		<input type="text" name="name" class="form-control validate[required,custom[username]]" id="name" style="margin-top: 0px">
               	<input type="hidden" name="oldName" id="oldName" style="margin-top: 0px">
               	</div>
            </div>
			<!-- 第2行 -->
			<div class="row" style="width:100%">
				<label class="col-sm-2 labels">上边距<b class="red">*</b></label>
           		<div class="col-sm-4">
               		<input type="text" name="up" id="up" class="form-control validate[required,custom[number]]" style="width:90%;float:left"><label style="margin-top:5%">px</label>
               	</div>
               	<label class="col-sm-2 labels">下边距<b class="red">*</b></label>
           		<div class="col-sm-4">
               		<input type="text" name="down" id="down" class="form-control validate[required,custom[number]]" style="width:90%;float:left"><label style="margin-top:5%">px</label>
               	</div>
            </div>
            <div class="row" style="width:100%">
				<label class="col-sm-2 labels">左边距<b class="red">*</b></label>
           		<div class="col-sm-4">
               		<input type="text" name="leftPadding" id="left" class="form-control validate[required,custom[number]]" style="width:90%;float:left"><label style="margin-top:5%">px</label>
               	</div>
               	<label class="col-sm-2 labels">右边距<b class="red">*</b></label>
           		<div class="col-sm-4">
               		<input type="text" name="rightPadding" id="right" class="form-control validate[required,custom[number]]" style="width:90%;float:left"><label style="margin-top:5%">px</label>
               	</div>
            </div> 
			<div class="modal-footer">
				<button data-dismiss="modal" class="btn btn-danger" type="button">取消</button>
				<button class="btn btn-success" type="button" onclick="addSetting(this.value)" id="uid">确定</button>
			</div>
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
            $("#addForm").at
			 $("#addForm").validationEngine({
	    	        scrollOffset: 98,//必须设置，因为Toolbar position为Fixed
	    	        promptPosition: 'topLeft',
	    	        autoHidePrompt: true,
	    	    });
        });
        $.validationEngineLanguage.allRules.username = {  
        	      "regex": /^[\w\u4e00-\u9fa5]+$/,
        	      "alertText": "* 请输入正确的格式"  
      	}; 
        </script>
  <!-- END JS --> 
</body>
</html>