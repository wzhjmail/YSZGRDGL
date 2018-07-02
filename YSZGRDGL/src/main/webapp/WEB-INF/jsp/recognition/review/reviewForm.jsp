<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="myshiro" uri="/customize-tags" %>
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>评审表</title>
	<link href="${ctx}/assets/morris.js-0.4.3/morris.css" rel="stylesheet"><!-- MORRIS CHART CSS -->
	<link href="${ctx}/css/clndr.css" rel="stylesheet"><!-- CALENDER CSS -->
	<!-- 查询页面 -->
    <link href="${ctx}/css/bootstrap.min.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="${ctx}/css/bootstrap-reset.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="${ctx}/assets/font-awesome/css/font-awesome.css" rel="stylesheet"><!-- FONT AWESOME ICON STYLESHEET -->
    <link href="${ctx}/assets/data-tables/DT_bootstrap.css" rel="stylesheet" ><!-- DATATABLE CSS -->
    <link href="${ctx}/css/style.css" rel="stylesheet"><!-- THEME BASIC CSS -->
    <link href="${ctx}/css/style-responsive.css" rel="stylesheet"><!-- THEME BASIC RESPONSIVE  CSS -->
	<link href="${ctx}/css/style_self.css" rel="stylesheet"><!-- 右键特效的css -->
	<link rel="stylesheet" href="${ctx}/css/validationEngine.jquery.css">
	<script src="${ctx}/js/jquery-1.8.3.min.js"></script><!-- BASIC JS LIABRARY 1.8.3 -->
	<script src="${ctx}/js/style_self.js"></script><!-- 右键特效的js -->
	<script src="${ctx}/js/jquery.validationEngine-zh_CN.js"></script>
	<script src="${ctx}/js/jquery.validationEngine.js"></script>
			<script src="${ctx}/js/jquery.mloading.js"></script>
	<link href="${ctx}/css/jquery.mloading.css" rel="stylesheet">
	<script src="${ctx}/assets/bootstrap-datepicker/js/bootstrap-datepicker.js"></script><!-- 日期显示 -->
	<link rel="stylesheet" type="text/css" href="${ctx}/assets/bootstrap-datepicker/css/datepicker.css"><!-- BOOTSTRAP DATEPICKER PLUGIN CSS -->
	<style type="text/css">
		.row{
			width:100%
		}
		.change{
		    position: absolute;
		    overflow: hidden;
		    right: 0;
		    top: 0;
		    opacity: 0;
		    width: 0px;
		}
		#title{
			position: fixed;
			top: 0;
			left: 1%;
			z-index: 99999;
			width: 101%;
			background: white;
			border: 1px solid #e5e6e6;	
		}
		#Modal_show .modal-footer{padding: 5px 20px;}
		#Modal_show .modal-body{padding: 0px 20px;}
		#editable-sample_wrapper .dataTables_paginate {margin-bottom: 0px}
		#editable-sample_processing{display: none;}
	</style>
<script type="text/javascript">
var oTable = {
		"bFilter":true,
	    "bDestroy" : true,
	    "bRetrieve":true,
		"bProcessing": false,
	    "bServerSide": true,	 
	    "sAjaxSource":"${ctx}/comUser/findZj.action",
	    "aLengthMenu": [
	                    [5, 3, 2, 1, -1],
	                    [5, 3, 2, 1, "All"]
	                ],
	    "iDisplayLength": 3,
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
                      { "mData": null, 
                          "sClass": "center",
                          "fnRender": function(data) {
                              sReturn = "<input type='checkbox' name='zj' value='"+data.aData.id+"'/>";
                              return sReturn;
                          } 
                        },
	                  { "mData": "username", "sClass": "center" },
	                  { "mData": "unit", "sClass": "center" },
	                  { "mData": "post", "sClass": "center" },
	              ],
	}
$(function(){
	var oSettings = $('#editable-sample').dataTable(oTable).fnSettings();
    $('#editable-sample').dataTable(oTable)
		$('#editable-sample_wrapper .dataTables_filter input').addClass("form-control medium");
	 	$('#editable-sample_wrapper .dataTables_length select').addClass("form-control xsmall");
	$.ajax({
		type: 'post',
		url: '${ctx}/evaluation/getAll.action',
		cache: false,
		success: function (data) {
			$.each(data,function(i,items){
				str = '<form method="post" id="psjgForm'+items.num+'" action="" class="form-horizontal tasi-form"><div class="form-group"><label class="col-sm-4 control-label">'+items.required+'</label>'+
                		'<div class="col-sm-3 control-label"><br>'+items.method+'</div>'+
				 		'<div class="col-sm-3 "><input type="hidden" value="'+items.num+'" name="num"><br><textarea class="form-control validate[required]"  id="PSJL'+items.num+'" name="psjl">${ss}</textarea></div>'+
               			'<span class="col-sm-2" id="PSJG'+items.num+'"><br></span></div></form><hr>'
				$('#info').append(str)
				result = items.result.split(',')
				resultnote=items.resultnote.split('/');
// 				for(var i=0;i<result.length-1;i++){
// 					dispaly=items.num+"_"+resultnote[i];
// 					$("#info .form-group:last span:eq(0)").append('<input onclick="autoFill(this.id)" type="radio" name="psjg" class="validate[required] a" id="'+dispaly+'" value="'+result[i]+'">'+result[i]+'<br>')
// 				}
//给单选按钮添加id
				for(var i=0;i<result.length-1;i++){
					dispaly=items.num+"_"+result[i];
					$("#info .form-group:last span:eq(0)").append('<input onclick="autoFill(this.id)" type="radio" name="psjg" class="validate[required] a" id="'+dispaly+'" value="'+result[i]+'">'+result[i]+'<br>')
				}
			})
			$('#info').append('<div class="form-group"><label class="col-sm-12">注：带*号的项为重点项</label></div>')
			var num1=$("#num1").val().split(",")
			var psjl=$("#psjl").val().split("*")
			var psjg=$("#psjg").val().split(",")
		if($("#num1").val()!=""&&$("#psjl").val()!=""&&$("#psjg").val()!=""){
			for(var i=0;i<num1.length;i++){
				$("#PSJL"+num1[i]).html(psjl[i])
				$('#'+num1[i]+'_'+psjg[i]).attr('checked','checked')
			}
		}	
		}
	});
	//加载评审表的数据
	$.ajax({
		type: 'post',
		url: '${ctx}/application/review/getResult.action?taskId=${taskId}',
		cache: false,
		success: function (data) {
			console.log(data)
			$.each(data.psjl, function(i,items) {
			    $('#'+i).val(items)
			});
			$.each(data.psjg, function(i,items) {
				switch(items){
					case "A":
						$('#'+i+' .a').prop('checked','checked')
					  break;
					case "B":
						$('#'+i+' .b').prop('checked','checked')
					  break;
					case "C":
						$('#'+i+' .c').prop('checked','checked')
					  break;
					case "D":
						$('#'+i+' .d').prop('checked','checked')
					  break;
					default:
						break;
				}
			});
		}
	});
	
	$('#fzdate').datepicker({
		format:'yyyy-mm-dd',
	});
	$('#pzdate').datepicker({
		format:'yyyy-mm-dd',
	});
	$('#psdate').datepicker({
		format:'yyyy-mm-dd',
	}); 

	//获取当前用户所在分支机构的所有专家用户
	$.post(
		"${ctx}/comUser/getZjUser.action",
		"branchId="+$("#branchId").val(),
		function(data){
			if(data!=null&&data.length>0){
				for(var i=0;i<data.length;i++){
					var result='<option id="'+ data[i].id +'" value="'+data[i].id+'">'+data[i].username+"</option>";
					$("#addzj").append(result);
				}
			}
		}
	); 
	/* $.post(
		"${ctx}/sysUser/getZjUser.action",
		"branchId="+$("#branchId").val(),
		function(data){
			if(data!=null&&data.length>0){
				for(var i=0;i<data.length;i++){
					var result='<option id="'+ data[i].id +'" value="'+data[i].id+'">'+data[i].usercode+"</option>";
					$("#addzj").append(result);
				}
			}
		}
	); */
	//获取专家信息
	var ids=$("#expertIds").val();
	$.post(
			"${ctx}/comUser/findUserByIds.action",
			"ids="+ids,
			function(data){
				for(var i=0;i<data.length;i++){
					var result='<div class="panel-body" style="margin-top:-20px">'
						+'<span class="col-sm-1" style="display:none;">'+data[i].id+'</span>'
						+'<span class="col-sm-2">'+data[i].username+'</span>'
					    +'<span class="col-sm-3">'+data[i].unit+'</span>'
					    +'<span class="col-sm-3">'+data[i].post+'</span>'
					    +'<span class="col-sm-2"><input type="checkbox" name="grp" class="validate[minCheckbox[1]]" value="'+data[i].id+'" checked="checked"/></span>'
					    +'<span class="col-sm-1"><button class="btn btn-sm btn-danger" onclick="deleteZj(this)">移除</button></span>'
					    +'</div>' ;						 
						$("#zjyh").append(result);
						result='';
				}
			}
		);
	/* $.post(
		"${ctx}/sysUser/findUserByIds.action",
		"ids="+ids,
		function(data){
			for(var i=0;i<data.length;i++){
				var result='<div class="panel-body" style="margin-top:-20px">'
					+'<span class="col-sm-1" style="display:none;">'+data[i].id+'</span>'
					+'<span class="col-sm-2">'+data[i].username+'</span>'
				    +'<span class="col-sm-3">'+data[i].unit+'</span>'
				    +'<span class="col-sm-3">'+data[i].post+'</span>'
				    +'<span class="col-sm-2"><input type="checkbox" value="'+data[i].id+'" checked="checked"/></span>'
				    +'<span class="col-sm-1"><button class="btn btn-sm btn-danger" onclick="deleteZj(this)">移除</button></span>'
				    +'</div>' ;						 
					$("#zjyh").append(result);
					result='';
			}
		}
	); */
});
//自动填充
function autoFill(ids){
	var id=ids.split("_")[0];
	var val=ids.split("_")[1];
// 	$("#PSJL"+id).val(val);
	$.post(
			"${ctx}/evaluation/getResultNote.action",
			"evalId="+id+"&result="+val,
			function(data){
				$("#PSJL"+id).val(data.resultnote);
			}
		);
}

function laststep(){
	$('#info').hide()
	$('#revForm').hide()
	$('#load1').hide()
	$('#load2').hide()
	$('#revForm').show()
	$('#info').show()
	$('#bar li:eq(0)').addClass('active').siblings().removeClass('active').addClass('disabled')
}
//求差集
function test(arr1,arr2){ 
	var arr3 = new Array();  
    for(var i=0; i < arr1.length; i++){   
        var flag = true;   
        for(var j=0; j < arr2.length; j++){   
            if(arr1[i] == arr2[j])   
            flag = false;   
        }   
        if(flag)   
        arr3.push(arr1[i]);   
    }  
    return arr3;
} 
//添加专家
function addZj(){
// 	$("#addzj option:first").removeAttr("selected");
// 	var id = $("#addzj option:selected").attr("id");
// 	$("#addzj option:selected").hide();
var ids=document.getElementsByName("zj");
var ids1=$("#expertIds").val();
var userIds="";
for(var i=0; i<ids.length; i++){ 
	if(ids[i].checked) {
		userIds+=ids[i].value+','; //如果选中，将value添加到变量userIds中 
	}
} 
userIds=userIds.substring(0,userIds.length-1);
var user1=userIds.split(",");
var user2=ids1.split(",");
var user3=test(user1,user2);
var id="";
for(var j=0;j<user3.length;j++){
	id+=user3[j]+",";
}
id=id.substring(0,id.length-1);
var uids="";
	$.post(
			"${ctx}/comUser/findUserByIds.action",
			"ids="+id,
			function(data){
				$.each(data,function(i,item){
				var result='<div class="panel-body" style="margin-top:-20px">'
					+'<span class="col-sm-1" style="display:none;">'+item.id+'</span>'
					+'<span class="col-sm-2">'+item.username+'</span>'
				    +'<span class="col-sm-3">'+item.unit+'</span>'
				    +'<span class="col-sm-3">'+item.post+'</span>'
				    +'<span class="col-sm-2"><input type="checkbox" name="grp" class="validate[minCheckbox[1]]" checked="checked" value="'+item.id+'"/></span>'
				    +'<span class="col-sm-1"><button class="btn btn-sm btn-danger" onclick="deleteZj(this)">移除</button></span>'
				    +'</div>' ;						 
					$("#zjyh").append(result);
					result='';
				});
				$("#zjyh input:checkbox:checked").each(function(){
	        		uids+=$(this).val()+",";
	        	});
	        	uids=(uids.substring(uids.length-1)==',')?uids.substring(0,uids.length-1):uids;
	        	$("#expertIds").val(uids);
			}
		);
}
//移除专家
function deleteZj(element){
	id=$(element).parent().parent().find('span:first').text()
	$('#'+id).show()
	$('#addzj option:first').attr('selected','selected')
	element.parentElement.parentElement.remove();
	var uids="";
	$("#zjyh input:checkbox:checked").each(function(){
		uids+=$(this).val()+",";
	});
	uids=(uids.substring(uids.length-1)==',')?uids.substring(0,uids.length-1):uids;
	$("#expertIds").val(uids);
}
//统计选项个数
function countSelect(){
	var a=0;
	var b=0;
	var c=0;
	$("input:radio:checked").each(function(){
		if(this.value=="A"){
			a=a+1;
		}else if(this.value=="B"){
			b=b+1;
		}else if(this.value=="C"){
			c=c+1;
		}
	});
	$("#syndic").val(a+"A、"+b+"B、"+c+"C");
}
function mavinIn(){
	var psjg= $("input[name='psjg']");
	var select1=[];
	//获取所有选项
	for(var i = 0; i < psjg.length; i++){  
	if (select1.indexOf($(psjg[i]).val()) == -1){
		select1.push($(psjg[i]).val());
	}   
	}
	//初始化所有变量初始值为0
	  var arr = new Array(select1.length);
	  for(var n = 0;n < select1.length;n++){
	  arr[n]=0;
	  }
	  //计算所有项总值
  var psjgResult=$("input[name='psjg']:checked");
  for(var k=0;k<psjgResult.length;k++){
	  for(var j=0;j<select1.length;j++){
		  if($(psjgResult[k]).val()==select1[j]){
			  arr[j]++;  
		  }
	  }
  }
	var mavin;
	var result="";
	for(var m=0;m<select1.length;m++){
		if(arr[m]==0){
			arr[m]="无";
		}else{
			arr[m]=arr[m]+"项";
		}
		result+=select1[m]+"级"+arr[m]+"、";
	}
	mavin="    在前期准备工作基本就绪的基础上，中国物品编码中心分支机构"+$("#branchName").val()+"组织评审组，对该单位："+$("#companyName").val()+"进行了商品条码印刷资格现场评审。"
	+"评审组通过座谈、询问、查看记录及观察现场操作等方式，依照《商品条码印刷资格评审表》规定的17项要求进行了逐项评审，结果为"
	+result.substring(0,result.length-1)+"，基本覆盖了申请认可的典型参数。该公司印刷方式为："
	+$("#printTyp").val()+"，印刷载体为："+$("#printMat").val()+"。";
	mavin+="\n  所抽查的样品经过检测，能够符合国家标准的要求，基本具备商品条码的承印能力。\n  评审组认为：该公司已具备商品条码的承印能力，其质量保证体系基本能够保证商品条码印刷质量的持续稳定，评审结论为同意“认定”。"
	$("#mavinIdea").val(mavin);
}
function frameworkIn(){
	var frame;
	frame="    中国物品编码中心分支机构"+$("#branchName").val()+"对该单位进行了商品条码印刷资格现场评审。考察了该公司的质量保证体系，认为该公司能够贯彻落实《条码印制质量手册》的内容，能够保证商品条码印刷质量。\n    建议上报中国物品编码中心审批。";
	$("#frameworkIdea").val(frame);
}
//选择专家
function selectZJ(){
	$("input[name='zj']").attr("checked",false);
	$("#zj").attr("href","#Modal_show");
}
</script>
</head>
<body style="overflow-x: hidden">
<iframe id="id_iframe" name="nm_iframe" style="display:none;">
</iframe>
				<div class="row" id="title"> 
                        <div class="panel-body">
                           <span class="form-horizontal tasi-form">
                              <div class="form-group">
                                 <ul class="nav nav-pills" id="bar" style="margin-left: 1%">
								  <li class="active"><a href="#">1.填写评审表</a></li>
								  <li class="disabled"><a href="#">2.资料上传</a></li>
								</ul>
                              </div>							 
                           </span>
                        </div>
               </div>
               <section class="panel">
<form class="form-horizontal tasi-form" method="post" target="nm_iframe" id="revInfo" action="" style="margin-top: 8%">
           <section class="wrapper" id="info" style="margin-top: 0px;">
	     	<center style="color:#2a8ba7"><h1>评审表</h1></center>
			   <!-- BEGIN ROW  ${ctx}/review/insert.action-->
               <div class="row">
                  <div class="col-lg-12">
                     <section class="panel">
                        <header class="panel-heading">
                           <span class="label label-primary">评审信息</span>
                           <span class="tools pull-right" style="margin-top:-8px;">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
						    <div class="form-group">
                                 <label class="col-sm-4 ">评审项目与要求</label>
                                <label class="col-sm-3">评审方法</label>
								<label class="col-sm-3">评审记录<b class="red">*</b></label>
								<label class="col-sm-2">单项评审结果<b class="red">*</b></label>
                              </div>
                              </div>
                              </section>
                        </div>
                        </div>
                     </section>
                     </form>
                     <form class="form-horizontal tasi-form" method="post" target="nm_iframe" id="revForm" action="">
			   <!-- END ROW  -->
			<div class="row">
                  <div class="col-lg-12">
                     <section class="panel">
                        <header class="panel-heading">
                           <span class="label label-primary">综合判定规定</span>
                           <span class="tools pull-right" style="margin-top:-8px;">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
			                <div class="col-sm-12">
								     评审结论分"认定"、"暂不认定"两种。<br>
								   申请企业经评审同时满足下述要求的，评审结论为"认定"；否则，评审结论为"暂不认定"。<br><br>
								<p>1、重点项（含*号标记项）为A的项数不得少于五项，且无C项;</p>
								<p>2、全项出现B项的个数不得超过6项</p>
								<p>3、非重点出现C项个数不得超过二项。当非重点项出现一个C项时，全项中出
								      现B项的数目不得超过4项；当出现二个C项时，全项中出现的数目不得超
								      过2项。</p>
                            </div>
			  			</div>
                     </section>
                  </div>
               </div>
                <div class="row">
                  <div class="col-lg-12">
                     <section class="panel" id="zjyh">
                        <header class="panel-heading">
                           <span class="label label-primary">评审专家</span>
                           <span class="label label-primary" onclick="selectZJ()" href="" data-toggle='modal' id="zj">选择专家</span>
                           <span class="tools pull-right" style="margin-top:-8px;">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        
                        <div class="panel-body">
                        <label class="col-sm-1" style='display:none;'>编号</label>
                        <label class="col-sm-2">姓名 </label>
                         <label class="col-sm-3">工作单位</label>
                         <label class="col-sm-3">职务/职称</label>
                         <label class="col-sm-2">是否签名</label>
                         <label class="col-sm-1">移除</label>
			  			</div>
			  			<input type="hidden" id="expertIds" name="expertIds" value="${item.expertIds}">
                     	<input type="hidden" id="num" value="">
			  			<input type="hidden" id="num1" value="${num}">
			  			<input type="hidden" id="psjg" value="${psjg}">
			  			<input type="hidden" id="psjl" value="${psjl}">
                     </section>
                  </div>
               </div>
             
               <div class="row">
                  <div class="col-lg-12">
                     <section class="panel">
                        <header class="panel-heading">
                           <span class="label label-primary">评审组意见及结论</span>
                           <span class="label label-primary" onclick="mavinIn()" href="">自动生成</span>
                           <span class="tools pull-right" style="margin-top:-8px;">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
						   <div class="form-group">
                             <div class="col-lg-1"></div>
                             <div class="col-lg-10">
                                  <textarea class="form-control validate[required]" rows="8" id="mavinIdea" name="mavinIdea">${item.mavinIdea}</textarea>
                             </div>  
                           </div>
                           <div class="form-group">
                             <div class="col-sm-2 text-right">评审组长签名：</div>
                             <div class="col-sm-3">
                                  <input type="text" id="psman" name="psman" class="form-control validate[custom[username]]" value="${item.psman}">
                             </div> 
                             <div class="col-sm-2"></div>
                             <div class="col-sm-1 text-right">日期：</div>
                             <div class="col-sm-3">
                        		<input class="input-medium default-date-picker form-control validate[custom[date]]" size="16"  id="psdate" name="psdate" value="${item.psdate}">
                      		 </div>
                           </div> 						 
			  			</div>
                     </section>
                  </div>
               </div>
               <div class="row">
                  <div class="col-lg-12">
                     <section class="panel">
                        <header class="panel-heading">
                           <span class="label label-primary">分支机构意见</span>
                           <span class="label label-primary" onclick="frameworkIn()" href="">自动生成</span>
                           <span class="tools pull-right" style="margin-top:-8px;">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
						   <div class="form-group">
                             <div class="col-lg-1"></div>
                             <div class="col-lg-10">
                                  <textarea class="form-control validate[required]" rows="4" id="frameworkIdea" name="frameworkIdea">${item.frameworkIdea}</textarea>
                             </div> 
                           </div> 
                           <div class="form-group">
                             <div class="col-sm-2 text-right">负责人签名 ：</div>
                             <div class="col-sm-3">
                                  <input type="text" id="fzman" name="fzman" class="form-control validate[custom[username]]" value="${item.fzman}">
                             </div> 
                             <div class="col-sm-2"></div>
                             <div class="col-sm-1 text-right">日期：</div>
                             <div class="col-sm-3">
                        		<input type="text" class="form_datetime form-control validate[custom[date]]" id="fzdate" name="fzdate" value="${item.fzdate}">
                      		 </div>
                             <div class="col-sm-2 text-right" style="margin-top:15px;">分支机构盖章：</div>
                           </div> 						 
			  			</div>
			  			<div class="col-sm-3">
                                 <input type="text" id="syndic" name="syndic" class="form-control" value="${item.syndic}" style="display:none;">
                        </div> 
                     </section>
                  </div>
               </div>
                 <div class="row">
	               <section class="panel">
	               <div class="panel-body text-center">
	               			<input type="text" style='display:none;' id="taskId" value="${taskId}">
	               			<input type="text" style='display:none;' id="titleno" value="${item.titleno}">
		               		<input type="text" style='display:none;' id="branchId" value="${branchId}">
		               		<input type="text" style='display:none;' id="branchName" value="${branchName}">
		               		<input type="text" style='display:none;' id="companyName" value="${companyName}">
	               			<input type="text" style='display:none;' id="printTyp" value="${printTyp}">
	               			<input type="text" style='display:none;' id="printMat" value="${printMat}">
	               		<div class="btn-group">
	               			<input type="button" onclick="submitForm()" class="btn-info  btn" value="保存"></div>
	               		<div class="btn-group">
	               			<input type="button" id="nextbtn1" onclick="nextStep(1)" class="btn-success  btn disabled" value="下一步"></div>
	               		</div>
	               </div>
	               </section>
	            </div>
               </form> 
		</section>
		 <!-- END MAIN CONTENT -->
        </section>
        <div id="Modal_show" class="modal" style="margin-top: 10%;margin-left: -18%;overflow: hidden;">
          <div class="modal-dialog">
            <div class="modal-content" style="width:750px">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                  &times;
                </button>
                <h4 class="modal-title">专家信息</h4>
              </div>
              <div class="modal-body">
               <table class="table table-bordered" style="width:100%" id="editable-sample">
			             <thead> 
                              <tr>
                              	<th class="">选项</th>
                                 <th class="">姓名</th>
                                 <th class="">工作单位</th>
                                 <th class="">职务/职称</th>
                              </tr>
                           </thead>
                           <tbody id="result">
                              <!-- 循环显示查询出来的结果 -->
<!--                              <input type="checkbox" name="zj"> -->
                           </tbody>
                        </table>
	         </div>
	         <div class="modal-footer" style="margin-top:0px;">
                <button data-dismiss="modal" class="btn btn-success" type="button" onclick="addZj()">
                 	确定
                </button>
              </div>
              </div>
            </div>
          </div>
        <div style="display: none;" id="load1">
				<section class="wrapper" style="padding:15px 0;margin-top:0px">
				<div class="">
                  <div class="">
                     <section class="panel">
                        <div class="panel-body">
                           <span class="form-horizontal tasi-form">
                           	<div class="form-group text-center">
                              	<b>第一步：请下载评审表；第二步：请上传评审表（盖章版）</b>
                              </div>	
                              <div class="form-group">
                                 <div class="col-sm-6 text-right">评审表：</div>
                                 <div class="col-sm-2">
                                   <button class="btn btn-info" onclick="downfunc()">下载</button>
                                 </div> 
                                 <div class="col-sm-4"></div>
                              </div>	
                             <form id="forms" method="post" target="nm_iframe" action="" enctype="multipart/form-data">
                              <div class="form-group ">
                                 <div class="col-sm-6 text-right">评审表（盖章版） <b class="red">*</b>：</div>
                                 <div class="col-sm-4">
                                 			<a class="btn btn-info" onclick="sq.click()">选择文件
											<input type="file" name="file" id="sq"  class="change validate[required]" accept="application/pdf"></a>
											<input type="hidden" name="taskId" value="${taskId}">
											<b id="em" style="padding: 7px;margin: 0">未上传文件</b>
					              <div class="modal-body hidden">
					                <table border="0" cellspacing="100px" align="center" style="height: 70px;width:100%">
										<tr id="trs"><td style="height: 10%;" align="center">
										
										</td></tr>
									</table>
						         </div>
             				 </div> 
                                 <div class="col-sm-2"></div>
                              </div>
                               <div class="form-group text-center">
 			               		<div class="btn-group"> 
 			               		<button class="btn btn-success" onclick="laststep()">
					                 	上一步
					                </button></div>
					            <div class="btn-group"> 
 			               		<button id="okbtn" class="btn btn-success" onclick="upfunc()">
					                 	确定
					                </button></div>
 			               		</div>								
							</form>	
                           </span>
                        </div>
                     </section>
                  </div>
               </div>
	            </section>
              </div>
	 <!-- BEGIN JS -->
    <!--datatable的汉化在assets/data-tables/jquery.dataTables.js中修改 -->
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
	<script src="${ctx}/js/editable-table.js" ></script><!-- EDITABLE TABLE JS  -->
     <!-- EDITABLE TABLE FUNCTION  -->
     <script>
     
        jQuery(document).ready(function() {
//             EditableTable.init();
            $('#psdate').val(getTaskTime($('#psdate').val()).split(' ')[0])
            $('#fzdate').val(getTaskTime($('#fzdate').val()).split(' ')[0])
            $('#pzdate').val(getTaskTime($('#pzdate').val()).split(' ')[0])
            $("#revForm").validationEngine({
    	        scrollOffset: 98,//必须设置，因为Toolbar position为Fixed
    	        promptPosition: 'topLeft',
    	        autoHidePrompt: true,
    	    });
    	    $("#revInfo").validationEngine({
    	        scrollOffset: 98,//必须设置，因为Toolbar position为Fixed
    	        promptPosition: 'topLeft',
    	        autoHidePrompt: true,
    	    });
        });
        function submitForm(){
        	var length=$("#zjyh div").length;
        	if(length<=1){
        		wzj.alert("温馨提示","请选择评审专家！");
        		return;
        	}
        	if($('#revInfo').validationEngine('validate')){
        	var ids="";
        	var myDate = new Date();
        	if($('#psdate').val()==""){
        		$('#psdate').val(myDate.toLocaleDateString())
        	}
        	if($('#fzdate').val()==""){
        		$('#fzdate').val(myDate.toLocaleDateString())
        	}
        	var pdate1=$('#psdate').val();
        	var pdate2=$('#fzdate').val();
        	$('#psdate').val(pdate1.replace(/\//g,"-"));
        	$('#fzdate').val(pdate2.replace(/\//g,"-"));
        	$("#zjyh input:checkbox:checked").each(function(){
        		ids+=$(this).val()+",";
        	});
        	ids=(ids.substring(ids.length-1)==',')?ids.substring(0,ids.length-1):ids;
        	$("#expertIds").val(ids);
        	countSelect();
            if($('#revForm').validationEngine('validate')){
            	 $("body").mLoading("show");
    			var psdate = $('#psdate').val().split('-')
    			var fzdate = $('#fzdate').val().split('-')
    			var pstime=psdate[0]+'/'+psdate[1]+'/'+psdate[2];
            	var fztime=fzdate[0]+'/'+fzdate[1]+'/'+fzdate[2]
	        	var taskId=$("#taskId").val();
// 	        	$("#revForm").attr("action","${ctx}/recognition/review/insertReview.action?taskId="+taskId);
// 	        	$("#revForm").submit();
	        	$.post(
						"${ctx}/recognition/review/insertReview.action",
						"taskId="+taskId+"&expertIds="+$("#expertIds").val()+"&mavinIdea="+$("#mavinIdea").val()+"&psman="+$("#psman").val()+"&psdate="+pstime+"&frameworkIdea="+$("#frameworkIdea").val()+"&fzman="+$("#fzman").val()+"&fzdate="+fztime,
						function(data){
							var dat="";
							var nums= $("input[name='num']");
							var psjl= $("input[name='psjl']");
							var psjg= $("input[name='psjg']:checked");
							for(var i=0;i<nums.length;i++){
								dat+="reviewFormParts["+i+"].num="+$(nums[i]).val()+"&reviewFormParts["+i+"].psjg="+$(psjg[i]).val()+"&reviewFormParts["+i+"].psjl="+$("#PSJL"+$(nums[i]).val()).val()+"&reviewFormParts["+i+"].syndicid="+data+"&";
							}
							dat=dat.substring(0,dat.length-1);
							$.post(
									"${ctx}/recognition/review/insertReviewPart.action",
									dat,
									function(data){
										$("body").mLoading("hide");
										wzj.alert('温馨提醒', '保存成功，请进行下一步！')
										$('#nextbtn1').removeClass('disabled') 
									}
							);
							
						}
				);
// 	        	$("body").mLoading("hide");
// 	        	wzj.alert('温馨提醒', '保存成功，请进行下一步！')
// 	        	$('#nextbtn1').removeClass('disabled')  
        	}
        	}
        }
        function getTaskTime(strDate) {  
            console.log("原始时间格式："+strDate);  
            var date = new Date(strDate);  
            var y = date.getFullYear();   
            var m = date.getMonth() + 1;    
            m = m < 10 ? ('0' + m) : m;    
            var d = date.getDate();    
            d = d < 10 ? ('0' + d) : d;    
            var h = date.getHours();    
            var minute = date.getMinutes();    
            minute = minute < 10 ? ('0' + minute) : minute;  
            var str = y+"-"+m+"-"+d+" "+h+":"+minute;  
            console.log("转换时间格式："+str);  
            if(strDate!=''){
                return str; 
            }else{
            	return ""
            } 
        }; 
        function nextStep(num){
			$('#revForm').hide()
			$('#info').hide()
			$('#load1').hide()
			$('#load2').hide()
			$('#load'+num).show()
			$('#bar li:eq('+num+')').removeClass('disabled')
			$('#bar li:eq('+num+')').addClass('active').siblings().removeClass('active').addClass('disabled')
			var taskId=$("#taskId").val();
			$.post(
				"${ctx}/recognition/review/savePDF.action",
				"taskId="+taskId,
				function(){
					
				});
        } 
        function downfunc(){
			var titleNo=$("#titleno").val();
			$.ajaxSetup({async : false});
			$.post(
			        "${ctx}/commons/fileExists.action",
			        'type=6&titleNo='+titleNo,
			        function (data) {
			            if(data) {
			            	 window.open('${ctx}/commons/downloadFile.action?type=6&titleNo='+titleNo);
			            }else{
			            	wzj.alert('温馨提示','下载失败！');
			            }
			        }
			    );
			 if(titleNo!=""){
// 				wzj.alert('温馨提醒', '下载成功！')
				$('#nextbtn2').removeClass('disabled')
			}
		}
        function upfunc(){
        	var file=$('#em').text();
			if(file=="未上传文件"){
				wzj.alert('温馨提醒', '请上传盖章后的评审表文件！')
			}else if(file.split(".")[1]=="pdf"||file.split(".")[1]=="PDF"){
				
				$("body").mLoading("show");
				$("#forms").attr("action","${ctx}/recognition/review/applyReview.action");
			$("#forms").submit();
			setTimeout(first,2000);
			}else{
				wzj.alert('温馨提醒', '请上传pdf格式文件！')
			}
// 			if($("#sq").val()!=""&&$("#titleno").val()!=""&&$("#appId").val()!=""){
				
// 			}
		}
        function first(){
        	$("body").mLoading("hide");
			wzj.alert('温馨提醒', '上传成功！')
			$('#nextbtn3').removeClass('disabled')
			//location.href="${ctx}/recognition/review/toTasks.action";
		}  
		function jump(){
			location.href="${ctx}/recognition/review/toTasks.action";
		}  
		var wz = new function() {
			this.width = $(window).width() * 0.3;
			this.height = 172;

			this.close = function() {
				$('.win iframe').fadeOut();
				$('.win').fadeOut("fast");
				setTimeout(function() {
					$('.win iframe').remove();
					$('.win').remove();
				}, 200);
			};
			function messageBox(html, title, message, type) {
				var jq = $(html);
				if(type == "toast") {
					jq.find(".window-panel").width(message.length * 20).css("margin-left", -message.length * 20 / 2).css("margin-top", -wzj.height / 2);
				} else {
					jq.find(".window-panel").width(wzj.width).css("margin-left", -wzj.width / 2).css("margin-top", -wzj.height / 2 - 36);
				}
				if(valempty(title)) {
					jq.find(".title").remove();
					jq.find(".window-panel .body-panel").css("border-radius", "4px");
				} else {
					jq.find(".title").find(":header").html(title);
				}
				jq.find(".content").html(message.replace('\r\n', '<br/>'));
				jq.appendTo('body').fadeIn("fast");
				$(".win .w-btn:first").focus();
			}
			this.alert = function(title, message, ico) {
				var icon = "";
				if(!valempty(ico)) {
					icon = '<p class="btns" style="margin-bottom:-15px;"><img width="70px" height="70px" src="images/' + ico + '.png"></p>';
				}
				var html = '<div class="win"><div class="mask-layer"></div><div class="window-panel"><iframe class="title-panel" frameborder="0" marginheight="0" marginwidth="0" scrolling="no"></iframe><div class="title"><h3 style="margin-left: 10px;margin-top: 10px;"></h3></div><div class="body-panel">' + icon + '<p class="content"></p><p class="btns"><button class="w-btn" tabindex="1" onclick="jump();">确定</button></p></div></div></div>';
				messageBox(html, title, message);
			}
		};
		 $('#sq').on('change', function( e ){
	            //e.currentTarget.files 是一个数组，如果支持多个文件，则需要遍历
	            var name = e.currentTarget.files[0].name;
	            $('#em').text(name);
	     	});
   $.validationEngineLanguage.allRules.username = {  
       	      "regex": /^[\w\u4e00-\u9fa5]+$/,
       	      "alertText": "* 请输入正确的格式"  
     	}; 
     </script>
  <!-- END JS --> 
</body>
</html>