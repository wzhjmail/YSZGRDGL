<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="myshiro" uri="/customize-tags" %>
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看角色</title>
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
<script src="${ctx}/js/jquery-1.8.3.min.js" ></script><!-- BASIC JS LIABRARY 1.8.3 -->
<script src="${ctx}/js/style_self.js"></script>
<style type="text/css">
.label-successs {
    background-color: #A9D86E;
    background-color: #5cb85c;
}
.labels {
    padding: 0.5em 0.8em;
    display: inline;
    padding: .2em .6em .3em;
    font-size: 75%;
    font-weight: 700;
    line-height: 1;
    color: #fff;
    text-align: center;
    white-space: nowrap;
    vertical-align: baseline;
    border-radius: .25em;
</style>
<script type="text/javascript">
var status="添加";
var changeId="";
	$(function(){
		 $('#editable-sample').dataTable({
             "bDestroy" : true,
             "bRetrieve":true,
			 "bProcessing": true,
	         "bServerSide": true,
			 "sAjaxSource":'${ctx}/dictionary/findAll.action',
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
             "aoColumnDefs": [{
                 'bSortable': false,
                 'aTargets': [0]
             }],
             "aoColumns": [
                           { "mData": "id", "sClass": "center" },
                           { "mData": "type", "sClass": "center" },
                           { "mData": "number", "sClass": "center" },
                           { "mData": "meaning", "sClass": "center" },
                           {
                               "mData":null,
                               "sClass": "center",
                               "fnRender": function(data) {
                                   sReturn = "<a class='editDic' data-toggle='modal' href='#Modal_edit'><span class='label label-success '>修 改</span></a></td>";
                                   return sReturn;
                               }
                           },
                           {
                        	   "mData":null,
                               "sClass": "center",
                               "fnRender": function() {
                                       sReturn = "<a class='delete' href='#'><span class='label label-danger'>删 除</span></a></td>";
                                   return sReturn;
                               }
                           }
                       ],
		})

		$("#editable-sample_new").click(function(){//添加角色按钮触发的函数
			addDic();
		});//添加按钮
		$("#change").click(function(){//点击提交按钮
			if(status=="修改"){//修改功能
				editRoleSubmit();
			}else{//添加功能
				addDicSubmit();
			}
		});//regis
	});//开始 
	function addDic(){//点击添加按钮
		status="添加";
	}

	 $('#editable-sample a.editDic').live('click', function (e) {//点击修改按钮
	        e.preventDefault();
	        var nRow = $(this).parents('tr')[0];
	        var id=nRow.childNodes[0].innerHTML;
		console.log(id)
		status="修改";
		$.ajaxSetup({async : false});
		$.post(
			"${ctx}/dictionary/findDicById.action",
			"id="+id,
			function(data){
				$("#change").val(data.id);
				$("#type option[id='"+data.type+"']").attr("selected",true);
				$("#number").val(data.number);
				$("#meaning").val(data.meaning);
			}
		);
	})
	
	function addDicSubmit(){//角色添加,提交按钮
		var type=$("#type").val();
		var num = $("#number").val();
		var mean = $("#meaning").val();
		var param = "type="+type+"&number="+num+"&meaning="+mean;
		$.post(
			"${ctx}/dictionary/addDic.action",
			param,
			function(data){
				window.location.href="${ctx}/dictionary/find.cation";
			}
		);
	}
	
	function editRoleSubmit(){//角色修改,提交按钮
		var id=$("#change").val();
		var type=$("#type").val();
		var num = $("#number").val();
		var mean = $("#meaning").val();
		var param = "id="+id+"&type="+type+"&number="+num+"&meaning="+mean;
		$.post(
			"${ctx}/dictionary/update.action",
			param,
			function(data){//此处是js对象不能用val。
				$("#"+id).children('td').eq(1).html(type);
				$("#"+id).children('td').eq(2).html(num);
				$("#"+id).children('td').eq(3).html(mean);
			}
		);
	}
	
	function exportRecord(){
		window.open("${ctx}/dictionary/exportRecord.action");	
	}
	
	
// 	function cancel(){
// 		if(status=="添加"){//如果是添加时点击了取消按钮，则将页面中的第一行去掉
// 			$("#result tr:first").find("td").eq(5).find("a").click();
// 		}
// 	}
	
</script>
</head>
<body>

               <section class="panel">
                  <div class="panel-body">
                     <div class="adv-table editable-table ">
                        <div class="clearfix">
                           <div class="btn-group nopaddingleft">
                             <a data-toggle="modal" href="#Modal_edit">
                              <button id="editable-sample_new" class="btn btn-success green">
                              	添加 <i class="fa fa-plus"></i>
                              </button></a>
                           </div>
                         <!--   <div class="btn-group pull-right">
                              <button class="btn btn-primary dropdown-toggle" data-toggle="dropdown">下载<i class="fa fa-angle-down"></i>
                              </button>
                              <ul class="dropdown-menu pull-right">
                                 <li><a href="#">Print</a></li>
                                 <li><a href="#">Save as PDF</a></li>
                                 <li><a href="javascript:exportRecord();">导出到Excel</a></li>
                              </ul>
                           </div> -->
                        </div>
                        <div class="space15"></div>
                        <table class="table table-striped table-hover table-bordered" id="editable-sample">
                           <thead>
                              <tr>
                                 <th class="">序号</th>
                                 <th class="">类型</th>
                                 <th class="">编号</th>
                                 <th class="">含义</th>
                                 <th>修改</th>
                                 <th>删除</th>
                              </tr>
                           </thead>
                           <tbody id="result">
                              <!-- 循环显示查询出来的结果 -->
                             
                           </tbody>
                        </table>
                     </div>
                  </div>
		 <!-- 添加信息对话框 -->
      </section>
	  <!-- END SECTION -->
		<div id="Modal_show" class="modal fade">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                  &times;
                </button>
                <h4 class="modal-title">角色权限</h4>
              </div>
              <div class="modal-body">
                <table width="400" border="0" cellspacing="100px" align="center">
					<tr id="trs"></tr>
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

 		<div id="Modal_edit" class="modal fade">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                  &times;
                </button>
                <h4 class="modal-title">数据字典</h4>
              </div>
              <div class="modal-body">
                <select name="type" style="margin-bottom:15px;" id="type" class="form-control placeholder-no-fix">
                	<option value="业务状态">业务状态</option>
                </select>
                <input type="text" style="margin-bottom:15px;" name="number" id="number" class="form-control placeholder-no-fix" placeholder="编号" requeired="true">
                <input type="text" style="margin-bottom:15px;" name="meaning" id="meaning" class="form-control placeholder-no-fix" placeholder="含义" requeired="true">
         </div>
         <div class="modal-footer" style="margin-top:0px;">
                <button data-dismiss="modal" class="btn btn-default" type="button" id="cancel" >
                  	取消
                </button>
                <button data-dismiss="modal" class="btn btn-success" type="button" id="change">
                 	提交
                </button>
              </div>
              </div>
            </div>
          </div>
	
	 <!-- BEGIN JS -->
    <script src="${ctx}/js/jquery.sparkline.js"></script><!-- SPARKLINE JS -->
    <script src="${ctx}/js/sparkline-chart.js"></script><!-- SPARKLINE CHART JS -->
    <script src="${ctx}/js/count.js"></script><!-- COUNT JS -->
   
  <script src="${ctx}/js/jquery-1.8.3.min.js" ></script><!--重複，但是不能少 -->
		<script src="${ctx}/js/bootstrap.min.js" ></script><!-- BOOTSTRAP JS  -->
		<script src="${ctx}/js/jquery.dcjqaccordion.2.7.js"></script><!-- ACCORDING JS -->
		<script src="${ctx}/js/jquery.scrollTo.min.js" ></script><!-- SCROLLTO JS  -->
		<script src="${ctx}/js/jquery.nicescroll.js" > </script><!-- NICESCROLL JS  -->
		<script src="${ctx}/assets/data-tables/jquery.dataTables.js"></script><!-- DATATABLE JS  -->
		<script src="${ctx}/assets/data-tables/DT_bootstrap.js"></script><!-- DATATABLE JS  -->
		<script src="${ctx}/js/respond.min.js" ></script><!-- RESPOND JS  -->
		<script src="${ctx}/js/common-scripts.js" ></script><!-- BASIC COMMON JS  -->
    <script src="${ctx}/assets/fuelux/js/tree.min.js"></script><!-- TREE JS  -->
    <script >

//     $('#editable-sample_new').click(function (e) {
//         e.preventDefault();
//         var aiNew = oTable.fnAddData(['', '', '', '', '<a class="edit"><span class="label label-success">修改</span></a>', 
//                                       '<a class="delete" href="javascript:;"><span class="label label-danger">删除</span></a>']);
//         var nRow = oTable.fnGetNodes(aiNew[0]);
//     });
    
    $('#editable-sample a.delete').live('click', function (e) {
        e.preventDefault();
        var nRow = $(this).parents('tr')[0];
        var id=nRow.childNodes[0].innerHTML;
        if(id!=null&&id!=""){//删除数据库中的值
        	wzj.confirm('温馨提醒', '您确定删除吗?', function() {
                 $.post(
         				"deleteById",
         				"id="+id,
         				function(data){
         					if(data>0){//如果删除成功为1，失败为0
         					location.reload();
         					}
         			});
    		});
       }else{//添加时取消，删除页面的值
//     	   oTable.fnDeleteRow(nRow);
    	   wzj.confirm('温馨提醒', '删除失败！');
       }
    });//delete按钮
     </script>
  <!-- END JS --> 
</body>
</html>