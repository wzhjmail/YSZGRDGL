<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<%@ taglib prefix="myshiro" uri="/customize-tags" %>
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>设定初始证书编号</title>
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
<script type="text/javascript">
var max=0;
var oTable = {
		"bFilter":true,
	    "bDestroy" : true,
	    "bRetrieve":true,
		"bProcessing": true,
	    "bServerSide": true,	 
	    "sAjaxSource":"${ctx}/application/getCerts.action",
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
//                       { "mData": "num", "sClass": "center"},
	                  { "mData": "certno", "sClass": "center" ,
	                	  "fnRender": function(data) {
	                    	  var str = "";
							if(data.aData.certno!=null){
								str=data.aData.certno;
							var len = data.aData.certno.toString().length
		                      len = 6-len
		                      for(var i=0;i<len;i++){
		                    	  str = "0"+str
				              }
		                     }
	                          return str;
	                      } 
	                  },
	                  { "mData": "comName", "sClass": "center" },
	                  { "mData": "startno", "sClass": "center",
	                	  "fnRender": function(data) {
	                    	  var str = "";
							if(data.aData.startno!=null){
								str=data.aData.startno;
							var len = data.aData.startno.toString().length
		                      len = 6-len
		                      for(var i=0;i<len;i++){
		                    	  str = "0"+str
				              }
		                     }
	                          return str;
	                      }   
	                  },
	              ],
	}
$(function(){
	var oSettings = $('#editable-sample').dataTable(oTable).fnSettings();
    $('#editable-sample').dataTable(oTable)
		$('#editable-sample_wrapper .dataTables_filter input').addClass("form-control medium");
	 	$('#editable-sample_wrapper .dataTables_length select').addClass("form-control xsmall");
	$.post(
		"${ctx}/application/getMaxCertNo.action",
		function(data){
			var max="";
			if(data!=null){
				max=data;
			var len = max.toString().length
              len = 6-len
              for(var i=0;i<len;i++){
            	  max = "0"+max
              }
             }
			$("#max").html("当前最大编号为:"+max);
		}
	);
});//初始化
function setMax(){
	$("#startNo").attr("placeholder","该编号要大于"+max);
}
</script>
</head>
<body>
               <section class="panel">
                  <div class="panel-body">
                     <div class="adv-table editable-table">
                        <div class="clearfix">
                          <div class="btn-group col-sm-2 nopaddingleft">
                             <button class="btn btn-success" data-toggle='modal' href='#Modal_show' onClick="setMax()">
                            	  设定初始值  <i class="fa fa-cog"></i>
                              </button>
                           </div>
                           <div class="btn-group col-sm-3 nopaddingleft">
                             	<div id="max" style="margin-top: 7px"></div>
                           </div>
                         <!--   <div class="btn-group pull-right">
                              <button class="btn btn-primary dropdown-toggle" data-toggle="dropdown">下载<i class="fa fa-angle-down"></i>
                              </button>
                              <ul class="dropdown-menu pull-right">
                                 <li><a href="#">Print</a></li>
                                 <li><a href="#">Save as PDF</a></li>
                                 <li><a href="">export Excel</a></li>
                              </ul>
                           </div> -->
                        </div>
                        <div class="space15"></div>
                        <table class="table table-bordered" style="width:100%" id="editable-sample">
                           <thead>
                              <tr>
<!--                                  <th class="">编号</th> -->
                                 <th class="">证书编号</th>
                                 <th class="" width="40%">企业名称</th>
                                 <th class="">初始编号</th>
                              </tr>
                           </thead>
                           <tbody id="result">
                              <!-- 循环显示查询出来的结果 -->
                             
                           </tbody>
                        </table>
                        </div>
                     </div>
               </section>
            </section>
			<div id="Modal_show" class="modal fade">
	          <div class="modal-dialog">
	            <div class="modal-content">
	              <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
	                  &times;
	                </button>
	                <h4 class="modal-title">设定证书编号初始值</h4>
	              </div>
	              <div class="modal-body">
	                <table border="0" cellspacing="100px" align="center" style="height: 50px;width:100%">
						<tr><td><font size='3'>请输入初始编号：</font></td>
						<td><input type="text" id="startNo" placeholder="该编号要大于所有编号" class="form-control placeholder-no-fix"></td></tr>
					</table>
		         </div>
		         <div class="modal-footer" style="margin-top:0px;">
		         	<button data-dismiss="modal" class="btn btn-default" type="button">
                  		取消
                	</button>
	                <button class="btn btn-success" type="button" onclick="insert();">
	                 	确定
	                </button>
	              </div>
	              </div>
	            </div>
	          </div>
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
    <script src="${ctx}/js/editable-table-log.js"></script>
     <!-- EDITABLE TABLE FUNCTION  -->
     <script>
        jQuery(document).ready(function() {
            //EditableTable.init();
        });
        function insert(){
        	var startNo=$("#startNo").val();
        	if(startNo<max){
        		wzj.alert('温馨提示','输入的初始编号不合法！');
        	}else{
        		$("#Modal_show").modal('hide');
        		$.post(
        			"${ctx}/application/setCertStart.action",
        			"startNo="+startNo,
        			function(data){
        				window.location.reload();
        			}
        		);
        	}
        }
     </script>
  <!-- END JS --> 
    <!-- END JS -->
</body>
</html>