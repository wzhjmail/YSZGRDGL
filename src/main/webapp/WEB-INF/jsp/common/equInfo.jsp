<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="myshiro" uri="/customize-tags" %>
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查询所有</title>
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
	$('#newdate').datepicker({
		format:'yyyy-mm-dd',
	});
	$('#buyDate').datepicker({
		format:'yyyy-mm-dd',
	});
	$('#calibrationDate').datepicker({
		format:'yyyy-mm-dd',
	});
// 	$('#baddate').datepicker({
// 		format:'yyyy-mm-dd',
// 	});
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
	$("#delete").click(
			function(){
				if(nRow==null){
					wzj.alert('温馨提示','请选择一个设备！');
				}else if(selectId=="数据为空"){
					wzj.alert('温馨提示','数据为空！');
					return false;
				}else{
					wzj.confirm('温馨提示','您确定删除？',function(flag){
						if(flag){
							$.post(
									"${ctx}/equipment/delete.action",
									"id="+selectId,
									function(data){//刷新页面
										window.location.reload();
									}
								);
						}
					});		
			}}	
		);
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
    imageMenuData.push({
        "text": "删除", "func": function () {
        	if(selectId==null){
        		wzj.alert('温馨提示', '请选择一条数据！')
        		return false;
        	}else if(selectId==""){
        		wzj.alert('温馨提示','数据为空！');
        		return false;
        	}else{
        		$("delete").click();
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
//判断末次校准时间
function calibrationDateTest(time){
	var realTime="";
	if(time=="1111-11-18"){
		realTime="";
	}else{
		realTime=time;
	}
	return realTime;
}

function getAll(){
	$.ajaxSetup({async : false});
	$.post(
		"${ctx}/equipment/getAll.action",
		function(data){
			$.each(data,function(i,item){
				$("#result").append("<tr>"
					+"<td style='display:none;'>"+item.id+"</td>"
					+"<td>"+(i+1)+"</td>"
					+"<td>"+item.branchId+"</td>"
					+"<td>"+item.brand+"</td>"
					+"<td>"+item.model+"</td>"
					+"<td>"+DateHandle(item.buyDate)+"</td>"
					+"<td>"+calibrationDateTest(DateHandle(item.calibrationDate))+"</td>"
					+"<td>"+(calibrationDateTest(DateHandle(item.calibrationDate))==""?"":DateHandle(item.nextCailDate))+"</td>"
					+"<td>"+item.calibrationName+"</td>"
					+"<td>"+item.calibrationCycle+"</td>"
					+"<td>"+(item.checked==true?"是":"否")+"</td></tr>"
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
	$("#span").html("添加");
	$("#brand").val("");
	$("#model").val("");
	$("#buyDate").val("");
	$("#calibrationDate").val("");
	$("#calibrationName").val("");
	$('input:radio[name=checked]').attr('checked',false);
	$("#calibrationCycle").val("");
	$("#uid").val(-1);
}

//添加和修改
function addEqu(uid){
	 $('#uid').removeAttr("data-dismiss","modal");
	if($('#addForm').validationEngine('validate')){
		var brand=$("#brand").val();
		var model=$("#model").val();
		var buyDate=$("#buyDate").val();
		var calibrationDate=$("#calibrationDate").val();
		var calibrationName=$("#calibrationName").val();
		var calibrationCycle="";
		if($("#calibrationCycle").val()==""){
			calibrationCycle=0;
		}else{
			calibrationCycle=$("#calibrationCycle").val();
		}
		var check=$('input[name="checked"]:checked ').val();
		var bd=buyDate.replace(/-/g, '/');
		var cd=calibrationDate.replace(/-/g, '/');
		if(uid==-1){//执行添加
			var param ="";
			if(cd==""){
				param="brand="+brand+"&model="+model+"&buyDate="+bd
				+"&calibrationName="
				+calibrationName+"&calibrationCycle="+calibrationCycle+"&checked="+check;
			}else{
				param="brand="+brand+"&model="+model+"&buyDate="+bd
				+"&calibrationDate="+cd+"&calibrationName="
				+calibrationName+"&calibrationCycle="+calibrationCycle+"&checked="+check;
			}
		$.post(
           	"${ctx}/equipment/insert.action",
           	param,
           	function(data){//返回值是添加用的ID
           		if(data==0){
           			wzj.alert('温馨提示','添加失败！');
           		}else{
           			location.reload();
           		}
           	}
        );
		}else{//执行修改
			var param ="";
			if(cd==""){
				param ="id="+uid+"&brand="+brand+"&model="+model
				+"&buyDate="+bd+"&calibrationDate=1111/11/11"
				+"&calibrationName="+calibrationName+"&calibrationCycle="
				+calibrationCycle+"&checked="+check;
			}else{
				param ="id="+uid+"&brand="+brand+"&model="+model
				+"&buyDate="+bd+"&calibrationDate="+cd
				+"&calibrationName="+calibrationName+"&calibrationCycle="
				+calibrationCycle+"&checked="+check;
			}
		$.post(
           	"${ctx}/equipment/update.action",
           	param,
           	function(data){//返回值是添加用的ID
           		if(data==0){
           			wzj.alert('温馨提示','修改失败！');
           		}else{
           			location.reload();
           		}
           	}
        );}
		 $('#uid').attr("data-dismiss","modal");
	}
}

//预备更新
function toUpdate(){
	emptyInput();
	$("#span").html("修改");
	if(selectId=="数据为空"){
		wzj.alert('温馨提示','数据为空！');
	}else if(selectId==null){
		wzj.alert('温馨提示','请选择一个设备！');
		return false;
	}else{
		$("#update").attr("href","#Modal_show");
		$.post(
				"${ctx}/equipment/getById.action",
				"id="+selectId,
				function(data){//刷新页面
					$("#uid").val(data.id);
					$("#brand").val(data.brand);
					$("#model").val(data.model);
					$("#buyDate").val(DateHandle(data.buyDate));
					$("#calibrationDate").val(calibrationDateTest(DateHandle(data.calibrationDate)));
					$("#calibrationName").val(data.calibrationName);
					$('input:radio[name=checked][value="' + data.checked + '"]').prop("checked", "checked");
					$("#calibrationCycle").val(data.calibrationCycle);
				}
			);
	}
}
function exportRecord(){
	window.open('${ctx}/equipment/exportRecord.action');	
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
                              <button class="btn btn-danger" id="delete">
                              	删除 <i class="fa fa-minus"></i>
                              </button>
                           </div>
                       <!--     <div class="btn-group pull-right">
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
                                 <th class="">序号</th>
                                 <th class="">分支机构</th>
                                 <th class="">品牌型号</th>
                                 <th class="">出厂编号</th>
                                 <th class="">购买时间</th>
                                 <th class="">末次校准日期</th>
                                 <th class="">下次校准日期</th>
                                 <th class="">校准机构名称</th>
                                 <th class="">校准周期/年</th>
                                 <th class="">是否做设备期间核查</th>
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
            <div class="modal-content" style="width:100%">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                  &times;
                </button>
                <h4 class="modal-title"><span id="span">添加</span></h4>
              </div>
              <div class="modal-body">
			<form action="" method="post" id="addForm">
			<!-- 第一行 -->
			<div class="row" style="width:100%">
				<label class="col-sm-2 labels">品牌型号<b class="red">*</b></label>
            		<div class="col-sm-4">
                		<input type="text" name="brand" class="form-control validate[required] " id="brand" style="margin-top: 0px">
                	</div>
              	<label class="col-sm-2 labels">出厂编号<b class="red">*</b></label>
                	<div class="col-sm-4">
                  		<input type="text" name="model" class="form-control validate[required]" id="model">
                	</div>
			</div> 
			<!-- 第三行 -->
			<div class="row" style="width:100%">
				<label class="col-sm-2 labels">购买时间<b class="red">*</b></label>
            		<div class="col-sm-4">
                		<input type="text" name="buyDate" id="buyDate" class="form-control validate[required,custom[date]]">
                	</div>
              	<label class="col-sm-2 labels nopaddingleft">末次校准时间</label>
                	<div class="col-sm-4">
                  		<input type="text" id="calibrationDate" name="calibrationDate" class="form-control validate[custom[date]]">
                	</div>
			</div> 
			<!-- 第四行 -->
			<div class="row" style="width:100%">
				<label class="col-sm-2 labels nopaddingleft">校准机构名称<b class="red">*</b></label>
            		<div class="col-sm-4">
                		<input type="text" name="calibrationName" id="calibrationName" class="form-control validate[required,custom[branchname]]">
                	</div>
				<label class="col-sm-2 labels nopaddingleft">校准周期/年</label>
            		<div class="col-sm-4">
                		<input type="text" name="calibrationCycle" id="calibrationCycle" class="form-control validate[custom[integer]]">
                	</div>
			</div>
            <!-- 第五行 -->
			<div class="row" style="width:100%">
				<label class="col-sm-2 labels nopaddingleft">是否做设备期间核查<b class="red">*</b></label>
				<div class="col-sm-4">
					<label class="col-sm-6"><input type="radio" name="checked" value="true" class="validate[required]">是</label>
					<label class="col-sm-6"><input type="radio" name="checked" value="false" class="validate[required]">否</label>
				</div>
			</div>
			<div class="modal-footer">
				<button data-dismiss="modal" class="btn btn-danger" type="button">取消</button>
				<button class="btn btn-success" type="button" onclick="addEqu(this.value)" id="uid">确定</button>
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
<!--<script src="${ctx}/js/advanced-form-components.js"></script><!--  ADVANCE FORM COMPONENTS PAGE JS  -->
	<script src="${ctx}/js/bootstrap.min.js"></script><!-- BOOTSTRAP JS  -->
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
            $('.sorting').css('width','')
			 $("#addForm").validationEngine({
	    	        scrollOffset: 98,//必须设置，因为Toolbar position为Fixed
	    	        promptPosition: 'topLeft',
	    	        autoHidePrompt: true,
	    	 });
        });
        $.validationEngineLanguage.allRules.date = {  
	    	      "regex": /^(19[0-9]{2}|20[0-9]{2})-(0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-9]|3[0-1])$/,
	    	      "alertText": "* 请输入正确的日期格式"  
	   	}; 
        $.validationEngineLanguage.allRules.branchname = {  
      	      "regex": /^[\w\u4E00-\u9FA5]+$/,
      	      "alertText": "* 请输入正确的格式"  
    	   	};  
	</script>
	<!-- END JS --> 
</body>
</html>