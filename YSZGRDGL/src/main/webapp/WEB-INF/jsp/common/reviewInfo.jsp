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
 <link href="${ctx}/assets/simditor/styles/simditor.css" rel="stylesheet">
 <script src="${ctx}/assets/simditor/scripts/module.min.js"></script>
 <script src="${ctx}/assets/simditor/scripts/hotkeys.min.js"></script>
 <script src="${ctx}/assets/simditor/scripts/uploader.min.js"></script>
 <script src="${ctx}/assets/simditor/scripts/simditor.min.js"></script>
	<link rel="stylesheet" href="${ctx}/css/validationEngine.jquery.css">
	<script src="${ctx}/js/jquery.validationEngine-zh_CN.js"></script>
	<script src="${ctx}/js/jquery.validationEngine.js"></script>
<style type="text/css">
	.label{
		line-height: unset;
	}
	.error{
	    position: relative;
	    width: 120px;
	    padding: 4px 10px;
	    border-radius: 6px;
	    background-color: #fb3b3b;
	    color: #fff;
	    opacity: 0;
	    top:-10px;
	    cursor: pointer;
    	transition: opacity 1s;
		-ms-transition: opacity 1s; /* IE */
		-moz-transition: opacity 1s; /* Firefox 4 */
		-webkit-transition: opacity 1s; /* Safari 和 Chrome */
		-o-transition: opacity 1s; /* Opera */
	}
	.error::after {
	    content: "";
	    position: absolute;
	    top: 100%;
	    left: 50%;
	    margin-left: -5px;
	    border-width: 5px;
	    border-style: solid;
	    border-color: #fb3b3b transparent transparent transparent;
	}
</style>
<script type="text/javascript">
	var temptr = $();
	var nRow = null;
	var selectId=null;
	var revResults="";
	var revResult="";
	var revResult1="";
	
	$(function(){
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
		findAll();
	});//开始 
	
	function findAll(){
		$.ajaxSetup({async : false});
		$.post(
			"${ctx}/evaluation/getAllEva.action",
			function(data){
				if(data!=null){
					for(var i=0;i<data.length;i++){

				revResult1='A&nbsp;&nbsp;<input type="checkbox" disabled="disabled" value="A" name="ch" style="margin-right:4px;">B&nbsp;&nbsp;<input type="checkbox" disabled="disabled" value="B" name="ch" style="margin-right:4px;" >C&nbsp;&nbsp;<input type="checkbox" disabled="disabled" value="C" name="ch" style="margin-right:4px;" >D&nbsp;&nbsp;<input type="checkbox" value="D" name="ch" disabled="disabled" style="margin-right:4px;" >';
				var result="<tr class='' ><td class='' style='display:none'>"
					+data[i].id+"</td><td class=''>"  
					+(i+1)+"</td><td class=''>"
				    +data[i].required+"</td><td class=''>"
				    +data[i].method+"</td><td class=''>";
				    if(data[i].available){
				    	 result+="是&nbsp;&nbsp;<input type='radio' value='true' checked='checked' disabled='disabled'>否&nbsp;&nbsp;<input type='radio' value='false' disabled='disabled'></td></tr>"
				    }else if(!data[i].available){
				    	result+="是&nbsp;&nbsp;<input type='radio' value='true' disabled='disabled'>否&nbsp;&nbsp;<input type='radio' value='false' checked='checked' disabled='disabled'></td></tr>"
				    }
					$("#result").append(result);
				} 
			}
		});
	}
	String.prototype.getlength = function() 
	{ 
		return this.replace(/[^\x00-\xff]/g, 'xx').length
	}	
	function hideerror(){
		$('.error').css('opacity','0')
	}
	
	function add(){
		$('#addbtn').removeAttr('data-dismiss')
		$('#err1').css('opacity','0')
		var arr = $('#ta1 .simditor-body p')
		var flag = false
		var str = ""
		for(var i=0;i<arr.length;i++){
			console.log(arr[i].textContent)
			if(arr[i].textContent!=""){
				flag = true
				str += arr[i].textContent
			}
		}
		
		if(flag==false){
			$('#err1').css('opacity','0.89')
			$('#err1').html("* 此处不可空白")
			setTimeout(function (){ $('#err1').css('opacity','0') },10000)
			if($('#addForm').validationEngine('validate')){}
		}else{
			if(str.length>200){
				$('#err1').css('opacity','0.89')
				$('#err1').html("* 不可超过200个字符")
				setTimeout(function (){ $('#err1').css('opacity','0') },10000)
				if($('#addForm').validationEngine('validate')){}
			}else{
				if($('#addForm').validationEngine('validate')){
					$("#addForm").submit();
					$('#addbtn').attr('data-dismiss','modal')
				}
			}
		}
		
	}

//修改回显
function toUpdate(){
	if(nRow==null){
		wzj.alert('温馨提示','请选择一条数据！');
	}else if(selectId=="数据为空"){
		wzj.alert('温馨提示','数据为空！');
		return false;
	}else{
	$("#require").val("");
	$("#method").val("");
	$("#result").attr("checked",false);
	$("#available").val("");
	$("#update").attr("href","#Modal_show");
	$.post(
			"${ctx}/evaluation/getById",
			"id="+selectId,
			function(data){
				var editor = new Simditor({   textarea: $('#require') });
				$("#revId").val(data.id);
				$("#require").val(data.required);
				$("#method").val(data.method);
				$('input:radio[name=update_available][value="' + data.available + '"]').prop("checked", "checked");
				editor.setValue(data.required);
			}
		);
	}
}
function tosetabc(){
	if(nRow==null){
		wzj.alert('温馨提示','请选择一条数据！');
	}else if(selectId=="数据为空"){
		wzj.alert('温馨提示','数据为空！');
		return false;
	}else{
		location.href="${ctx}/evaluation/toSetABC.action?eid="+selectId
	}
}
//修改
function updateRev(id){
	$('#revId').removeAttr('data-dismiss')
	$('#err2').css('opacity','0')
	var arr = $('#ta2 .simditor-body p')
	var flag = false
	var str = ""
	for(var i=0;i<arr.length;i++){
		console.log(arr[i].textContent)
		if(arr[i].textContent!=""){
			flag = true
			str += arr[i].textContent
		}
	}
	if(flag==false){
		$('#err2').css('opacity','0.89')
		$('#err2').html("* 此处不可空白")
		setTimeout(function (){ $('#err2').css('opacity','0') },10000)
		if($('#updateForm').validationEngine('validate')){}
	}else{
		if(str.length>200){
			$('#err2').css('opacity','0.89')
			$('#err2').html("* 不可超过200个字符")
			setTimeout(function (){ $('#err1').css('opacity','0') },10000)
			if($('#updateForm').validationEngine('validate')){}
		}else{
			if($('#updateForm').validationEngine('validate')){
				$('#revId').attr('data-dismiss','modal')
				var required=$("#require").val();
				var method=$("#method").val();
				var available=false;
				var avai=document.getElementsByName("update_available");
				for(var k=0;k<avai.length;k++){
					if(avai[k].checked){
						available=avai[k].value;
					}
				}
				if(available){
					$.post(
							"${ctx}/evaluation/countResult.action",
							"evalId="+id,
							function(data){
								if(data<=0){
									wzj.alert('温馨提示','请先设置评审结果！');
								}else{
									var param = "id="+id+"&required="+required
									+"&method="+method+"&available="+available;
								$.post(
										"${ctx}/evaluation/update.action",
										param,
										function(dat){
											location.reload();
										}
									);
								}
							}
						);
				}
			}
		}

	}
}
function deleteById(){
	if(nRow==null){
		wzj.alert('温馨提示','请选择一条数据！');
	}else if(selectId=="数据为空"){
		wzj.alert('温馨提示','数据为空！');
		return false;
	}else{
		$.post(
			"${ctx}/evaluation/deleteById.action",
			"id="+selectId,
			function(data){
				location.href="${ctx}/evaluation/toReviewInfo.action"
			}
		);
	}
}
</script>
</head>
<body>
               <section class="panel">
                 
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
							  <button class="btn btn-success" onClick='tosetabc()'>
                              	设置评审结果 <i class="fa fa-edit"></i>
                              </button>
                           </div>
                           <div class="btn-group nopaddingleft">
							  <button class="btn btn-danger" onClick="deleteById()">
                              	删除 <i class="fa fa fa-minus"></i>
                              </button>
                           </div>
                        </div>
                        <div class="space15"></div>
                        <br>
                        <table class="table table-bordered" id="editable-sample">
                           <thead>
                              <tr>
                                 <th class="" style="display:none">信息编号</th>
                                 <th class="" style="width: 50px">编号</th>
                                 <th class="">评审项目与需求</th>
                                 <th class="">评审方法</th>
                                 <th class="" style="width: 90px">是否可用</th>
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
          <div class="modal-dialog" style="width:90%">
            <div class="modal-content" style="width:100%">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                  &times;
                </button>
                <h4 class="modal-title"><span id="span">添加</span></h4>
              </div>
              <div class="modal-body">
			<form action="${ctx}/evaluation/addEvaluation.action" method="post" id="addForm">
			<!-- 第一行 -->
			<div class="row" style="width:100%">
				<label class="col-sm-2 labels">评审项目与需求</label>
            		<div class="col-sm-10" style="position: relative;" id="ta1">
            		    <div class="error" onclick="hideerror()" id="err1"></div>
                		<textarea name="required" class="form-control validate[required]" id="editor" style="margin-top: 0px" rows="3" cols=""></textarea> 
                	</div>
			</div> 
			<!-- 第三行 -->
			<div class="row" style="width:100%">
				<label class="col-sm-2 labels">方法</label>
                	<div class="col-sm-10">
                	<textarea name="method" class="form-control validate[required,funcCall[checknum]]" rows="3" cols=""></textarea> 
                	</div>
			</div> 
			<!-- 第四行 -->
			<div class="row" style="width:100%">
					<label class="col-sm-2 labels">是否可用</label>
					<div class="col-sm-10 labels">
						<label class="col-sm-2">是&nbsp;<input type="radio" name="available" value="true" class="validate[required]"></label>
						<label class="col-sm-2">否&nbsp;<input type="radio" name="available" value="false" class="validate[required]"></label>
					</div>
			</div>
			<div class="modal-footer">
				<button data-dismiss="modal" class="btn btn-info" type="button">取消</button>
				<button class="btn btn-success" type="button" onclick="add()" id="addbtn">确定</button>
			</div>
			</form>
              </div>
            </div>
          </div>
          </div>
          <div id="Modal_show" class="modal fade">
          <div class="modal-dialog" style="width:90%">
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
				<label class="col-sm-2 labels">评审项目与需求</label>
            		<div class="col-sm-10" style="position: relative;" id="ta2">
            		    <div class="error" onclick="hideerror()" id="err2"></div>
                		<textarea name="required" class="form-control validate[required]" id="require" style="margin-top: 0px" rows="3" cols=""></textarea> 
                	</div>
			</div> 
			<!-- 第三行 -->
			<div class="row" style="width:100%">
				<label class="col-sm-2 labels">方法</label>
                	<div class="col-sm-10">
                	<textarea name="method" class="form-control validate[required,funcCall[checknum]]" id="method" rows="3" cols=""></textarea> 
                	</div>
			</div> 
			<!-- 第四行 -->
			<div class="row" style="width:100%">
							<label class="col-sm-2 labels">是否可用</label>
					<div class="col-sm-10 labels">
						<label class="col-sm-2">是&nbsp;<input type="radio" name="update_available" value="true" class="validate[required]"></label>
						<label class="col-sm-2">否&nbsp;<input type="radio" name="update_available" value="false" class="validate[required]"></label>
					</div>
			</div>
			<div class="modal-footer">
				<button data-dismiss="modal" class="btn btn-info" type="button">取消</button>
				<button class="btn btn-success" type="button" onclick="updateRev(this.value)" id="revId">确定</button>
			</div>
			</form>
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
         EditableTable.init();
         $('#editable-sample thead th:eq(1)').css('width','50px')
         $('#editable-sample thead th:eq(2)').css('width','')
         $('#editable-sample thead th:eq(3)').css('width','')
         $('#editable-sample thead th:eq(4)').css('width','155px')
         $('#editable-sample thead th:eq(5)').css('width','90px')
         new Simditor({
      	  	textarea: $('#editor')
      	  	//optional options
     	});
 	    $("#addForm").validationEngine({
	        scrollOffset: 98,//必须设置，因为Toolbar position为Fixed
	        promptPosition: 'topLeft',
	        autoHidePrompt: true,
	    });
	    $("#updateForm").validationEngine({
	        scrollOffset: 98,//必须设置，因为Toolbar position为Fixed
	        promptPosition: 'topLeft',
	        autoHidePrompt: true,
	    });
     }); 
     function checknum(field, rules, i, options){  
		var str = field[0].value.getlength()

	      if(str>100) 
     	      return "* 不可超过100个字符";  
     	  
     	}   
     </script>
  <!-- END JS --> 
</body>
</html>