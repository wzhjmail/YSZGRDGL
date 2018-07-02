<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="myshiro" uri="/customize-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>劣迹记录</title>
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
    <link rel="stylesheet" href="${ctx}/css/validationEngine.jquery.css">
	<script src="${ctx}/js/jquery-1.8.3.min.js" ></script><!-- BASIC JS LIABRARY 1.8.3 -->
	<script src="${ctx}/js/style_self.js" ></script>
	<script src="${ctx}/js/jquery.validationEngine-zh_CN.js"></script>
	<script src="${ctx}/js/jquery.validationEngine.js"></script>
	<script src="${ctx}/assets/bootstrap-datepicker/js/bootstrap-datepicker.js"></script><!-- 日期显示 -->
	<link rel="stylesheet" type="text/css" href="${ctx}/assets/bootstrap-datepicker/css/datepicker.css"><!-- BOOTSTRAP DATEPICKER PLUGIN CSS -->
<style type="text/css">
	.label{
		line-height: unset;
	}		
	.change{
		    position: absolute;
		    overflow: hidden;
		    top: 0;
		    opacity: 0;
		    width: 0px;
		}
</style>
<script type="text/javascript">
var oTable = {
		"bFilter":false,
	    "bDestroy" : true,
	    "bRetrieve":true,
		"bProcessing": true,
	    "bServerSide": true,
	    "sAjaxSource":"${ctx}/misdeeds/getMisdeeds.action?companyId=${item}",
	    "aLengthMenu": [
	                    [5, 15, 20, -1],
	                    [5, 15, 20, "All"]
	                ],
	    "iDisplayLength": 15,
	    "sDom": "<'row'<'col-lg-6'l><'col-lg-6'f>r>t<'row'<'col-lg-6'i><'col-lg-6'p>>",
	    "sPaginationType": "bootstrap",
	    "oLanguage": {
	        "sLengthMenu": "_MENU_ 条记录每页",
	        "sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
	        "oPaginate": {
	            "sPrevious": "上一页",
	            "sNext": "下一页"
	        }
	    },
	    "aoColumns": [
	                  { "mData": "id", "sClass": "hidden" },
// 	                  { "mData": "i", "sClass": "center" },
	                  { "mData": "qrecord", "sClass": "center" },
	                  { "mData": "qtime", "sClass": "center",
	                    "fnRender": function(data) {
	                        sReturn = (new Date(parseFloat(data.aData.qtime))).Format("yyyy-MM-dd");
	                        
	                        return sReturn;
	                    } 
	                  },
	                  { "mData": "varify", "sClass": "center" ,"fnRender":function(data){
	                	  sReturn = data.aData.varify==true?"是":"否";
	                	  return sReturn;
	                  }},
	                  { "mData": "dealed", "sClass": "center" ,"fnRender":function(data){
	                	  sReturn = data.aData.dealed==true?"是":"否";
	                	  return sReturn;
	                  }},
	                  { "mData": "result", "sClass": "center" },
	                  { "mData": "opinion", "sClass": "center" },
	                  { "mData": "rtime", "sClass": "center",
		                    "fnRender": function(data) {
		                        sReturn = (new Date(parseFloat(data.aData.rtime))).Format("yyyy-MM-dd");
		                        if(sReturn=="NaN-aN-aN")return "";
		                        if(sReturn=="1970-01-01")return "";
		                        return sReturn;
		                    } 
		                  },
	                  { "mData": "enclosure", "sClass": "center","fnRender":function(data){
						  len = data.aData.enclosure.split('/').length
	                	  sReturn = data.aData.enclosure.split('/')[len-1];
	                	  return "<a href='javascript:;' onclick='viewFile(this)' id='"+data.aData.enclosure+"'>"+sReturn+"</a>"; }
	                  },
	                  { "mData": "available", "sClass": "center","fnRender":function(data){
	                	  sReturn = data.aData.available==true?"是":"否";
	                	  return sReturn; }
	                  }
	              ],
	}
$(function(){
	 var oSettings = $('#editable-sample').dataTable(oTable).fnSettings();
// 	 oSettings.sAjaxSource = "${ctx}/misdeeds/getMisdeeds.action";//重新设置url
	 //$('#editable-sample').dataTable(oTable).fnDraw()
	 $('#editable-sample_wrapper .dataTables_filter input').addClass("form-control medium");
 	 $('#editable-sample_wrapper .dataTables_length select').addClass("form-control xsmall");
//  	 $.ajax({
// 		type: 'post',
// 		url: '${ctx}/misdeeds/getMisdeeds.action?companyId='+$("#companyId").val()+'&sEcho=1&iDisplayStart=0&iDisplayLength=15&sSearch=',
// 		cache: false,
// 		success: function (data) {
// 			console.log(data)
// 		}
// 	});
	$('.labels').css('text-align','right');
	$('.row').css('margin','5px 0px');
	$('#rtime').datepicker({
		format:'yyyy-mm-dd',
	});
	$('#editable-sample tbody').on('click', 'tr', function () {//选中的行
		//获取选中行的第一列的值
		nRow = $(this).parent().find("tr").index($(this)[0]);
		selectId=$(this).children("td").eq(0).text();
		temptr.css("background-color","");
        temptr = $(this);
        temptr.css("background-color","#99ffff");
	});
});

/* $(function(){
	$('#rtime').datepicker({
		format:'yyyy-mm-dd',
	});
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
}); */
function viewFile(element){
	window.open('../viewPic.jsp?flag=4&code='+element.id);
}
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
              
            var menudata = GetRightMenu(e.target.cellIndex, e.target.innerHTML);
            aTable.$('td.td_selected').removeClass('td_selected');
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
        aTable.$('td.td_selected').removeClass('td_selected');
        if ($(e.target).hasClass('td_selected')) {
            $(e.target).removeClass('td_selected');
        } else {
            $(e.target).addClass('td_selected');
        }
        if ($(this).hasClass('row_selected')) {
            $(this).removeClass('row_selected');
        }else {
            aTable.$('tr.row_selected').removeClass('row_selected');
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

// function getAll(){
// 	$.ajaxSetup({async : false});
// 	$.post(
// 		"${ctx}/misdeeds/getMisdeeds.action",
// 		"companyId="+$("#companyId").val(),
// 		function(data){
// 		$.each(data,function(i,item){
// 			$("#showResult").append("<tr>"
// 				+"<td style='display:none;'>"+item.id+"</td>"
// 				+"<td>"+(i+1)+"</td>"
// 				+"<td>"+item.qrecord+"</td>"
// 				+"<td>"+DateHandle(item.qtime)+"</td>"
// 				+"<td>"+(item.varify==true?"是":"否")+"</td>"
// 				+"<td>"+(item.dealed==true?"是":"否")+"</td>"
// 				+"<td>"+item.result+"</td>"
// 				+"<td>"+item.opinion+"</td>"
// 				+"<td>"+DateHandle(item.rtime)+"</td>"
// 				//+"<td><a data-toggle='modal' onclick='showFile("+(i+1)+")' href='#Modal_show2'><span class='label label-info'>附件</span></a></td>"
// 				+"<td><a data-toggle='modal' onclick='showFile("+item.id+")' href='#Modal_show2'><span class='label label-info'>附件</span></a></td>"
// 				+"<td>"+(item.available==true?"是":"否")+"</td></tr>"
// 			);
// 		});
// 	});
// }
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
	$("#em1").html("未上传文件");
	$("#qrecord").val("");
	$("#result").val("");
	$("#opinion").val("");
	$("#rtime").val("");
	$('input:radio').attr('checked',false);
	$("#mfile").val("");
	$("#uid").val(-1);
}

//添加和修改
function addMisD(uid){
	    $('#uid').removeAttr("data-dismiss");
		document.getElementById("addForm").action="";
		if($('#addForm').validationEngine('validate')){       
            console.log("认证通过") 
            $('#uid').attr("data-dismiss","modal");
            if($('#rtime').val()==""){
            	$('#rtime').val("1970-01-01");
            }
        	$('#rtime').val(timeChange('rtime'));
        	console.log($('#rtime').val())
            if(uid==-1){//执行添加
            	document.getElementById("addForm").action="${ctx}/misdeeds/insertMis.action"
            	document.getElementById('addForm').submit()
    		}else{//执行修改	
            	document.getElementById("addForm").action="${ctx}/misdeeds/updateMis.action"
            	document.getElementById('addForm').submit()
    		}
        }	
}

//预备更新
function toUpdate(){
	emptyInput();
	$("#span").html("修改");
	$("#em1").html("");
	$("#mfile").removeClass("validate[required]");
	if(selectId=="数据为空"){
		wzj.alert('温馨提示','数据为空！');
	}else if(selectId==null){
		wzj.alert('温馨提示','请选择一个记录！');
		return false;
	}else{
		$("#update").attr("href","#Modal_show");
		$.post(
				"${ctx}/misdeeds/getMisdeedById.action",
				"misdeedId="+selectId,
				function(data){//刷新页面
					enclosure=data.enclosure;
					$("#span").html("修改");
					$("#id").val(data.id);
					$("#qrecord").val(data.qrecord);
					$('input[type=radio][name="varify"][value="' + data.varify + '"]').attr("checked",'checked');
					$('input[type=radio][name="dealed"][value="' + data.dealed + '"]').attr("checked",'checked');
					$("#varify").val(data.varify);
					$("#result").val(data.result);
					$("#opinion").val(data.opinion);
					var rtime=DateHandle(data.rtime);
					$("#rtime").val(rtime=='1970-01-01'?"":rtime);
					$('input[type=radio][name="available"][value="' + data.available + '"]').attr("checked",'checked');
					$("#mfile").val(data.mfile);
					$("#em1").html(enclosure.substring(enclosure.lastIndexOf("/")+1,enclosure.length));
					$("#uid").val(1);
				}
			);
	}
}
function showFile(i){
	window.open('../viewPic.jsp?flag=3&code='+i)
}
function exportRecord(){
	window.open('${ctx}/equipment/exportRecord.action');	
}
function toDelete(){
	if(selectId=="数据为空"){
		wzj.alert('温馨提示','数据为空！');
	}else if(selectId==null){
		wzj.alert('温馨提示','请选择一个记录！');
		return false;
	}else{
		wzj.confirm('温馨提醒','您确定要删除?',function(flag){
			if(flag){
				$.post(
						"${ctx}/misdeeds/deleteMisdeedById.action",
						"misdeedId="+selectId,
						function(data){//刷新页面
							location.href="${ctx}/misdeeds/toMisdeeds.action?companyId="+$("#companyId").val()+"&branchId="+$("#branchId").val();
						}
					);
			}});
	}
}
//开始添加
function toAdd(){
	emptyInput();
	$("input[name='varify']").eq(1).prop('checked', 'checked');
	$("input[name='dealed']").eq(1).prop('checked', 'checked');
	$("input[name='available']").eq(1).prop('checked', 'checked');
}
</script>
</head>
<body style="overflow-x: hidden;overflow-y:hidden; ">
	<section class="panel">
	<div class="panel-body">
        <div class="adv-table editable-table ">
        <h3 class="text-center" style="margin-top: 10px">${companyName}</h3>
           <div class="clearfix">
              <div class="btn-group  nopaddingleft">
                 <button class="btn btn-success" data-toggle='modal' href='#Modal_show' onClick='toAdd()' id="add">
             	添加 <i class="fa fa-plus"></i>
             </button>
          </div>
          <div class="btn-group ">
	<button class="btn btn-success" data-toggle='modal' href='' onClick='toUpdate()' id="update">
                     	修改 <i class="fa fa-edit"></i>
                     </button>
                  </div>
                   <shiro:hasRole name="中心管理人员">
                  <div class="btn-group ">
                  <button class="btn btn-danger" onClick="toDelete()" id="delete">
                     	删除<i class="fa fa-edit"></i>
                     </button>
                  </div>
                  </shiro:hasRole>
               <!--    <div class="btn-group pull-right">
                     <button class="btn btn-primary dropdown-toggle" data-toggle="dropdown">下载<i class="fa fa-angle-down"></i>
                     </button>
                     <ul class="dropdown-menu pull-right">
                        <li><a href="javascript:exportRecord();">导出到Excel</a></li>
                     </ul>
                  </div> -->
               </div>
               <div class="space15"></div>
               <table class="table table-bordered" id="editable-sample">
                  <thead>
                     <tr>
                     	 <th class="" style="display:none">id</th>
<!--                         <th class="">序号</th> -->
                        <th class="">名称</th>
                        <th class="">时间</th>
                        <th class="">是否核实</th>
                        <th class="">是否处理</th>
                        <th class="">处理结果</th>
                        <th class="">处理意见</th>
                        <th class="">处理时间</th>
                        <th class="">附件</th>
                        <th class="">是否可用</th>
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
				<input type="hidden" name="companyid" id="companyId" value="${item}">
				<input type="hidden" name="branchid" id="branchId" value="${item2}">
				<input type="hidden" name="companyName" id="companyName" value="${companyName}">
			<!-- 第一行 -->
			<div class="row" style="width:100%;display:none">
				<label class="col-sm-2 labels">id</label>
           		<div class="col-sm-10">
               		<input type="text" name="id" class="form-control" id="id" style="margin-top: 0px">
               	</div>
            </div>
			<!-- 第一行 -->
			<div class="row" style="width:100%">
				<label class="col-sm-2 labels">质量记录<b class="red">*</b></label>
           		<div class="col-sm-10">
               		<input type="text" name="qrecord" class="form-control validate[required]" id="qrecord" style="margin-top: 0px">
               	</div>
            </div>
            <!-- 第2行 -->
			<div class="row" style="width:100%">
              	<label class="col-sm-2 labels">是否核实</label>
               	<div class="col-sm-4">
                 	<label class="col-sm-6"><input type="radio" name="varify" value="true">是</label>
					<label class="col-sm-6"><input type="radio" name="varify" value="false">否</label>
               	</div>
               	<label class="col-sm-2 labels">是否处理</label>
               	<div class="col-sm-4">
                 	<label class="col-sm-6"><input type="radio" name="dealed" value="true">是</label>
					<label class="col-sm-6"><input type="radio" name="dealed" value="false">否</label>
               	</div>
			</div> 
			<!-- 第三行 -->
			<div class="row" style="width:100%">
				<label class="col-sm-2 labels">处理结果</label>
           		<div class="col-sm-10">
               		<input type="text" name="result" id="result" class="form-control">
               	</div>
               	</div>
             <!-- 第4行 -->
			<div class="row" style="width:100%">  	
              	<label class="col-sm-2 labels">处理意见</label>
               	<div class="col-sm-10">
                 	<input type="text" id="opinion" name="opinion" class="form-control">
               	</div>
			</div> 
			<div class="row" style="width:100%">
				<label class="col-sm-2 labels">处理时间</label>
            		<div class="col-sm-4">
                		<input type="text" name="rtime" id="rtime" class="form-control validate[custom[date]]">
                	</div>
              	<label class="col-sm-2 labels">附件<b class="red">*</b></label>
                	<div class="col-sm-4"><a href="javascript:;" class="btn btn-info col-sm-6" style="float: left;">选择文件
                  		<input class="change validate[required]"  type="file" id="mfile" name="mfile" ></a>
                	</div>
			</div> 
			<div class="row" style="width:100%">
			    <label class="col-sm-2 labels">是否可用</label>
               	<div class="col-sm-4">
                 	<label class="col-sm-6"><input type="radio" name="available" value="true">是</label>
					<label class="col-sm-6"><input type="radio" name="available" value="false">否</label>
               	</div>
               	<label class="col-sm-2 labels"></label>
               	<div class="col-sm-4"><p id="em1" style="float: left;padding: 7px;margin: 0">未上传文件</p></div>
			</div>
			<div class="modal-footer">
				<button data-dismiss="modal" class="btn btn-info" type="button">取消</button>
				<button class="btn btn-success" type="button" onclick="addMisD(this.value)" id="uid">确定</button>
			</div>
			</form>
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
	<script src="${ctx}/js/timeChange.js" ></script>
     <!-- EDITABLE TABLE FUNCTION  -->
     <script>
        jQuery(document).ready(function() {
            //EditableTable.init();
            $("#addForm").validationEngine({
    	        scrollOffset: 98,//必须设置，因为Toolbar position为Fixed
    	        promptPosition: 'topLeft',
    	        autoHidePrompt: true,
    	    });
        	$("#editable-sample thead tr th").css('width',"")
        });
	    $('#mfile').on('change', function( e ){
            //e.currentTarget.files 是一个数组，如果支持多个文件，则需要遍历
            var name = e.currentTarget.files[0].name;
            $('#em1').text(name);
     });
        $.validationEngineLanguage.allRules.date = {  
    	      "regex": /^(19[0-9]{2}|20[0-9]{2})-(0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-9]|3[0-1])$/,
    	      "alertText": "* 请输入正确的日期格式"  
	   	}; 
        </script>
  <!-- END JS --> 
</body>
</html>