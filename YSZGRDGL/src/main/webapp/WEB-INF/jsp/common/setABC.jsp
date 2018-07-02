<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<%@ taglib prefix="myshiro" uri="/customize-tags" %>
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>评审信息管理</title>
	<link href="${ctx}/assets/morris.js-0.4.3/morris.css" rel="stylesheet"><!-- MORRIS CHART CSS -->
	<link href="${ctx}/css/clndr.css" rel="stylesheet"><!-- CALENDER CSS -->
	<!-- 查询页面 -->
    <link href="${ctx}/css/bootstrap.min.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="${ctx}/css/bootstrap-reset.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="${ctx}/assets/font-awesome/css/font-awesome.css" rel="stylesheet"><!-- FONT AWESOME ICON STYLESHEET -->
    <link href="${ctx}/assets/data-tables/DT_bootstrap.css" rel="stylesheet"><!-- DATATABLE CSS -->
    <link href="${ctx}/css/style.css" rel="stylesheet"><!-- THEME BASIC CSS -->
    <link href="${ctx}/css/style_self.css" rel="stylesheet">
    <link href="${ctx}/css/style-responsive.css" rel="stylesheet"><!-- THEME BASIC RESPONSIVE  CSS -->
<script src="${ctx}/js/jquery-1.8.3.min.js" ></script><!-- BASIC JS LIABRARY 1.8.3 -->
<script src="${ctx}/js/style_self.js"></script>
<style type="text/css">
	.label{
		line-height: unset;
	}
</style>
<script type="text/javascript">
	var temptr = $();
	var nRow = null;
	var selectId=null;
	var select="";
	var result="";
	var revResult1="";
	
	$(function(){
	$('.labels').css('text-align','right');
	$('.row').css('margin','5px 0px');
	$('#editable-sample tbody').on('click', 'tr', function () {//选中的行
		//获取选中行的第一列的值
		nRow = $(this).parent().find("tr").index($(this)[0]);
		selectId=$(this).children("td").eq(0).text();
		select=$(this).children("td").eq(2).text();
		result=$(this).children("td").eq(3).text();
		temptr.css("background-color","");
        temptr = $(this);
        temptr.css("background-color","#99ffff");
	});
		findAll();
	});//开始 
	
	function findAll(){
		$.ajaxSetup({async : false});
		$.post(
			"${ctx}/evaluationPart/getByEId.action",
			"eid="+$("#eid").val(),
			function(data){
				if(data!=null){
					for(var i=0;i<data.length;i++){
						var pice="<tr><td style='display:none'>"+data[i].id+"</td><td>"+(i+1)+
						"</td><td>"+data[i].result+"</td><td>"+data[i].resultnote+"</td></tr>";
						$("#result").append(pice);
					}
				} 
		});
	}
	function add(){
		var select=$("#select").val();
		var content=$("#content").val();
		var eid=$("#eid").val();
		$.post(
			"${ctx}/evaluationPart/add.action",
			"result="+select+"&resultnote="+content+"&evalid="+eid,
			function(data){
				location.reload();
			}
		);
		
	}
//修改回显
function toUpdate(){
	if(nRow==null){
		wzj.alert('温馨提示','请选择一条数据！');
	}else if(selectId==""){
		wzj.alert('温馨提示','数据为空！');
		return false;
	}else{
	$("#require1").val(select);
	$("#method1").val(result);
	$("#update").attr("href","#Modal_show");
	$("#revId").val(selectId);
	}
}

//修改
function updateRev(id){
	var result=$("#require1").val();
	var resultNote=$("#method1").val();
	
	var param = "id="+id+"&result="+result
		+"&resultnote="+resultNote;
	$.post(
			"${ctx}/evaluationPart/update.action",
			param,
			function(dat){
				location.reload();
			}
		);
}
function deleteById(){
	if(nRow==null){
		wzj.alert('温馨提示','请选择一条数据！');
	}else if(selectId==""){
		wzj.alert('温馨提示','数据为空！');
		return false;
	}else{
		$.post(
			"${ctx}/evaluationPart/delete.action",
			"id="+selectId,
			function(data){
				location.reload();
			}
		);
	}
}
function back(){
	location.href="${ctx}/evaluation/toReviewInfo.action";
}
</script>
</head>
<body>
               <section class="panel">
                 <input type=hidden value="${eid}" id="eid">
                  <div class="panel-body">
                     <div class="adv-table editable-table ">
                        <div class="clearfix">
                        	<div class="btn-group nopaddingleft">
							  <button class="btn btn-success" data-toggle='modal' href='#Modal_add' id="add">
                              	添加 <i class="fa fa-plus"></i>
                              </button>
                           </div>
                           <div class="btn-group nopaddingleft">
							  <button class="btn btn-success" data-toggle='modal' href='' onClick='toUpdate()' id="update">
                              	修改 <i class="fa fa-edit"></i>
                              </button>
                           </div>
                           <div class="btn-group nopaddingleft">
							  <button class="btn btn-danger" onClick="deleteById()">
                              	删除 <i class="fa fa-minus"></i>
                              </button>
                           </div>
                           <div class="btn-group nopaddingleft">
							  <button class="btn btn-success" onClick="back()">
                              	返回 <i class="fa fa-reply"></i>
                              </button>
                           </div>
                        </div>
                        <div class="space15"></div>
                        <div class="text-center"><h3 id="title">${describe}</h3></div>
                        <br>
                        <table class="table table-bordered" id="editable-sample">
                           <thead>
                              <tr>
                              	<th style="display:none">id</th>
                              	<th>序号</th>
                                 <th>选项</th>
                                 <th>选项说明</th>
                              </tr>
                           </thead>
                           <tbody id="result">
                              <!-- 循环显示查询出来的结果 -->
                           </tbody>
                        </table>
                     </div>
                  </div>
               </section>
          <div id="Modal_add" class="modal fade">
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
				<label class="col-sm-4 labels">选项</label>
            		<div class="col-sm-6">
                		<input  class="form-control" id="select"> 
                	</div>
			</div> 
			<!-- 第三行 -->
			<div class="row" style="width:100%">
				<label class="col-sm-4 labels">内容</label>
                	<div class="col-sm-6">
                	<textarea name="method" class="form-control" id="content" rows="3" cols=""></textarea> 
                	</div>
			</div> 
			<div class="modal-footer">
				<button data-dismiss="modal" class="btn btn-info" type="button">取消</button>
				<button data-dismiss="modal" class="btn btn-success" type="button" onclick="add()">确定</button>
			</div>
			</form>
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
                <h4 class="modal-title"><span id="span">修改</span></h4>
              </div>
              <div class="modal-body">
			<form action="" method="post" id="updateForm">
			<!-- 第一行 -->
			<div class="row" style="width:100%">
				<label class="col-sm-4 labels">选项</label>
            		<div class="col-sm-6">
                		<input  class="form-control" id="require1"> 
                	</div>
			</div> 
			<!-- 第三行 -->
			<div class="row" style="width:100%">
				<label class="col-sm-4 labels">内容</label>
                	<div class="col-sm-6">
                	<textarea name="method" class="form-control" id="method1" rows="3" cols=""></textarea> 
                	</div>
			</div> 
			<div class="modal-footer">
				<button data-dismiss="modal" class="btn btn-info" type="button">取消</button>
				<button data-dismiss="modal" class="btn btn-success" type="button" onclick="updateRev(this.value)" id="revId">确定</button>
			</div>
              </div>
            </div>
          </div>
          </div>
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
         //EditableTable.init();
     }); 
     </script>
  <!-- END JS --> 
</body>
</html>