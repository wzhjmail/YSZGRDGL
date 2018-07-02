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
	<style type="text/css">
		.table>thead>tr>th {
    		vertical-align: middle;
    	}
	</style>
<script type="text/javascript">
$(function(){
	getAll();
	$('#newdate').datepicker({
		format:'yyyy-mm-dd',
	});
	$('#buydate').datepicker({
		format:'yyyy-mm-dd',
	});
	$('#redate').datepicker({
		format:'yyyy-mm-dd',
	});
	$('#baddate').datepicker({
		format:'yyyy-mm-dd',
	});
	$('.labels').css('text-align','right');
	$('.row').css('margin','5px 0px');
	
	$('#editable-sample tbody').on('click', 'tr', function () {//选中的行
		console.log(666666)
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
				}else if(selectId==null){
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

function getAll(){
	$.ajaxSetup({async : false});
	$.post(
		"${ctx}/equipment/getAll.action",
		function(data){
			$.each(data,function(i,item){
				$("#result").append("<tr>"
					+"<td style='display:none;'>"+item.id+"</td>"
					+"<td>"+item.brand+"</td>"
					+"<td>"+item.model+"</td>"
					+"<td>"+item.func+"</td>"
					+"<td>"+item.price+"</td>"
					+"<td>"+item.seller+"</td>"
					+"<td>"+DateHandle(item.buyDate)+"</td>"
					+"<td>"+DateHandle(item.repairDate)+"</td>"
					+"<td>"+DateHandle(item.manuDate)+"</td>"
					+"<td>"+DateHandle(item.scrapDate)+"</td>"
					+"<td>"+(item.available==true?"是":"否")+"</td></tr>"
				);
			});
		});
}
function DateHandle(obj) {  
    var objDate=new Date(obj); //创建一个日期对象表示当前时间     
    var year=objDate.getFullYear();   //四位数字年     
    var month=objDate.getMonth()+1;   //getMonth()返回的月份是从0开始的，还要加1     
    var date=objDate.getDate();     
    var date = year+"/"+month+"/"+date;  
    return date;   
}

//清空输入
function emptyInput(){
	$("#brand").val("");
	$("#model").val("");
	$("#seller").val("");
	$("#price").val("");
	$("#newdate").val("");
	$("#buydate").val("");
	$("#redate").val("");
	$("#baddate").val("");
	$('input:radio[name=available]').attr('checked',false);
	$("#func").val("");
	$("#uid").val("");
}

//添加和修改
function addEqu(uid){
		var brand=$("#brand").val();
		var model=$("#model").val();
		var seller=$("#seller").val();
		var price=$("#price").val();
		var manuDate=$("#newdate").val();
		var buyDate=$("#buydate").val();
		var repairDate=$("#redate").val();
		var scrapDate=$("#baddate").val();
		var available=$('input[name="available"]:checked ').val();
		var func=$("#func").val();
		if(uid==""){//执行添加
		var param = "brand="+brand+"&model="+model
			+"&seller="+seller+"&price="+price+"&manuDate="+manuDate+"&buyDate="+buyDate+"&repairDate="+repairDate+"&scrapDate="+scrapDate+"&available="+available+"&func="+func;
		$.post(
           	"${ctx}/equipment/insert.action",
           	param,
           	function(data){//返回值是添加用的ID
           		if(data==0){
           			wzj.alert('温馨提示','添加失败！');
           		}else{
           			wzj.alert('温馨提示','添加成功！');
           			location.reload();
           		}
           	}
        );}
		else{//执行修改
			var param = "id="+uid+"&brand="+brand+"&model="+model
			+"&seller="+seller+"&price="+price+"&manuDate="+manuDate+"&buyDate="+buyDate+"&repairDate="+repairDate+"&scrapDate="+scrapDate+"&available="+available+"&func="+func;
		$.post(
           	"${ctx}/equipment/update.action",
           	param,
           	function(data){//返回值是添加用的ID
           		if(data==0){
           			wzj.alert('温馨提示','修改失败！');
           		}else{
           			wzj.alert('温馨提示','修改成功！');
           			location.reload();
           		}
           	}
        );}
}

//预备更新
function toUpdate(){
	if(selectId=="数据为空"){
		wzj.alert('温馨提示','数据为空！');
	}else if(selectId==null){
		wzj.alert('温馨提示','请选择一个设备！');
		return false;
	}else{
		emptyInput();
		$("#update").attr("href","#Modal_show");
		$.post(
				"${ctx}/equipment/getById.action",
				"id="+selectId,
				function(data){//刷新页面
					$("#uid").val(data.id);
					$("#brand").val(data.brand);
					$("#model").val(data.model);
					$("#seller").val(data.seller);
					$("#price").val(data.price);
					$("#newdate").val(DateHandle(data.manuDate));
					$("#buydate").val(DateHandle(data.buyDate));
					$("#redate").val(DateHandle(data.repairDate));
					$("#baddate").val(DateHandle(data.scrapDate));
					$('input:radio[name=available][value="' + data.available + '"]').prop("checked", "checked");
					$("#func").val(data.func);
				}
			);
	}
}

//删除
</script>
</head>
<body style="overflow-x: hidden;overflow-y:hidden; ">
					<section class="panel">
					<div class="panel-body">
                     <div class="adv-table editable-table ">
                        <div class="clearfix">
                           <div class="btn-group col-lg-1 nopaddingleft">
                              <button class="btn btn-success" data-toggle='modal' href='#Modal_show' onClick='emptyInput()' id="add">
                              	添加 <i class="fa fa-plus"></i>
                              </button>
                           </div>
                           <div class="btn-group col-lg-1">
							  <button class="btn btn-success" data-toggle='modal' href='' onClick='toUpdate()' id="update">
                              	修改 <i class="fa fa-edit"></i>
                              </button>
                           </div>
                           <div class="btn-group col-lg-1">
                              <button class="btn btn-danger" id="delete">
                              	删除 <i class="fa fa-minus"></i>
                              </button>
                           </div>
                           <div class="btn-group pull-right">
                              <button class="btn btn-primary dropdown-toggle" data-toggle="dropdown">工具<i class="fa fa-angle-down"></i>
                              </button>
                              <ul class="dropdown-menu pull-right">
                                 <li><a href="#">Print</a></li>
                                 <li><a href="#">Save as PDF</a></li>
                                 <li><a href="javascript:exportRecord();">导出到Excel</a></li>
                              </ul>
                           </div>
                        </div>
                        <div class="space15"></div>
                        <table class="table table-bordered" id="editable-sample" style="width: 200%">
                           <thead>
                              <tr>
                              	 <th class="" style="display:none">id</th>
                                 <th class="" rowspan="4">序号</th>
                                 <th class="" colspan="16">条码检测设备</th>
                                 <th class="" colspan="5">其他检测设备</th>
                              </tr>
                              <tr>
                                 <th class="" rowspan="3">品牌型号</th>
                                 <th class="" rowspan="3">出厂编号</th>
                                 <th class="" rowspan="3">购买时间</th>
                                 <th class="" rowspan="3">末次校准时间</th>
                                 <th class="" rowspan="3">校准机构名称</th>
                                 <th class="" rowspan="3">校准周期</th>
                                 <th class="" rowspan="3">是否做设备期间检查</th>
                                 <th class="" colspan="9">2015年校准计划</th>
                                 <th class="" rowspan="3">品牌型号</th>
                                 <th class="" rowspan="3">末次校准日期</th>
                                 <th class="" rowspan="3">校准机构名称</th>
                                 <th class="" rowspan="3">校准周期</th>
                                 <th class="" rowspan="3">是否做设备期间检查</th>
                              </tr>
                              <tr>
                                 <th class="" rowspan="2">有无校准计划（2015年）</th>
                                 <th class="" colspan="3">送检方式</th>
                                 <th class="" colspan="5">校准项目</th>
                              </tr>
                              <tr>
                                 <th class="">送检</th>
                                 <th class="">上门服务</th>
                                 <th class="">邮寄</th>
                                 <th class="">6mil</th>
                                 <th class="">10mil</th>
                                 <th class="">20mil</th>
                                 <th class="">印制品条空尺寸</th>
                                 <th class="">胶片条空尺寸</th>
                              </tr>
                           </thead>
                           <tbody id="result">
                              <!-- 循环显示查询出来的结果 -->
                             
                           </tbody>
                        </table>
                     </div>
                     </div>
                     </section>
             <div id="Modal_show1" class="modal fade">
          <div class="modal-dialog">
            <div class="modal-content" style="width:100%">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                  &times;
                </button>
                <h4 class="modal-title">角色查看</h4>
              </div>
              <div class="modal-body">
             <div class="row" style="width:100%">
              <label class="col-sm-2">角色：</label>
              <div class="col-sm-3">
              <select id="role2" name="role2" class="form-control" style="height:30px;font-size:12px;background-color:#eee;width:120px"></select>
             </div>
             </div>
	         </div>
	         <div class="modal-footer">
                
                 <button data-dismiss="modal" class="btn btn-info" type="button">确定</button>
              </div>
              </div>
            </div>
          </div>
          <div id="Modal_show" class="modal fade">
          <div class="modal-dialog">
            <div class="modal-content" style="width:100%">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                  &times;
                </button>
                <h4 class="modal-title">添加</h4>
              </div>
              <div class="modal-body">
			<form action="" method="post" id="addForm">
			<!-- 第一行 -->
			<div class="row" style="width:100%">
				<label class="col-sm-2 labels">品牌</label>
            		<div class="col-sm-4">
                		<input type="text" name="brand" class="form-control" id="brand" style="margin-top: 0px">
                	</div>
              	<label class="col-sm-2 labels">型号</label>
                	<div class="col-sm-4">
                  		<input type="text" name="model" class="form-control" id="model">
                	</div>
			</div> 
            <!-- 第二行 -->   
            <div class="row" style="width:100%">
            	<label class="col-sm-2 labels" >卖家</label>
            		<div class="col-sm-4">
                		<input type="text" name="seller" class="form-control" id="seller">
                	</div>
				<label class="col-sm-2 labels">价格</label>
					<div class="col-sm-4">
						<input type="text" name="price" class="form-control" id="price">
					</div>
			</div> 
			<!-- 第三行 -->
			<div class="row" style="width:100%">
				<label class="col-sm-2 labels">生产日期</label>
            		<div class="col-sm-4">
                		<input type="text" name="manuDate" id="newdate" class="form-control validate[custom[date]]">
                	</div>
              	<label class="col-sm-2 labels">购买日期</label>
                	<div class="col-sm-4">
                  		<input type="text" id="buydate" name="buyDate" class="form-control validate[custom[date]]">
                	</div>
			</div> 
			<!-- 第四行 -->
			<div class="row" style="width:100%">
				<label class="col-sm-2 labels">维修日期</label>
            		<div class="col-sm-4">
                		<input type="text" name="repairDate" id="redate" class="form-control validate[custom[date]]">
                	</div>
				<label class="col-sm-2 labels">报废日期</label>
            		<div class="col-sm-4">
                		<input type="text" name="scrapDate" id="baddate" class="form-control validate[custom[date]]">
                	</div>
			</div>
            <!-- 第五行 -->
			<div class="row" style="width:100%">
							<label class="col-sm-2 labels">是否可用</label>
					<div class="col-sm-4">
						<label class="col-sm-6"><input type="radio" name="available" value="true">是</label>
						<label class="col-sm-6"><input type="radio" name="available" value="false">否</label>
					</div>
			</div>
			<div class="row" style="width:100%">
				<label class="col-sm-2 labels">功能</label>
					<div class="col-sm-10">
						<textarea rows="3" name="" style="width: 100%" id="func"></textarea>
					</div>
			</div>
			<div class="modal-footer">
				<button data-dismiss="modal" class="btn btn-success" type="button" onclick="addEqu(this.value)" id="uid">确定</button>
				<button data-dismiss="modal" class="btn btn-info" type="button">取消</button>
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
	<script src="${ctx}/js/editable-table.js" ></script><!-- EDITABLE TABLE JS  -->
     <!-- EDITABLE TABLE FUNCTION  -->
     <script>
        jQuery(document).ready(function() {
            EditableTable.init();
            $("#addForm").at
        });
        $.validationEngineLanguage.allRules.date = {  
	    	      "regex": /^(19[0-9]{2}|20[0-9]{2})-(0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-9]|3[0-1])$/,
	    	      "alertText": "* 请输入正确的日期格式"  
	   	}; 
        </script>
  <!-- END JS --> 
</body>
</html>