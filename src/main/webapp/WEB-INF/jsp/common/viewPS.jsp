<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="myshiro" uri="/customize-tags" %>
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>评审表信息</title>
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
	
<script src="${ctx}/js/jquery-1.8.3.min.js"></script><!-- BASIC JS LIABRARY 1.8.3 -->
<script src="${ctx}/js/style_self.js"></script><!-- 右键特效的js -->
<script type="text/javascript" src="${ctx}/js/jquery.media.js"></script>
<style type="text/css">
	.row{
			width:100%;
			margin: 0
		}
	.col-lg-12{
		padding: 0
	}
</style>
<script type="text/javascript">
$(function(){
	$('.labels').css('text-align','right')
	/* $.ajax({
		type: 'post',
		url: '${ctx}/evaluation/getAll.action',
		cache: false,
		success: function (data) {
			$.each(data,function(i,items){
				if(items.available==true){
				str = '<div class="form-group"><label class="col-sm-4 control-label">'+items.required+'</label>'+
                		'<div class="col-sm-3 control-label"><br>'+items.method+'</div>'+
				 		'<div class="col-sm-3 "><textarea class="form-control validate[required]" readonly="readonly" id="PSJL'+items.num+'" name="PSJL'+items.num+'">${ss}</textarea></div>'+
               			'<label class="col-sm-2" id="PSJG'+items.num+'"></label></div>'
				$('#info').append(str)
				result = items.result.split(',')
				for(var i=0;i<result.length;i++){
					switch(result[i]){
					case "A":
						$("#info .form-group:last label:eq(1)").append('<input type="radio" disabled="disabled" name="PSJG'+items.num+'" class="validate[required] a" id="or'+items.num+'1" value="A">A<br>')
					  break;
					case "B":
						$("#info .form-group:last label:eq(1)").append('<input type="radio" disabled="disabled" name="PSJG'+items.num+'" class="validate[required] b " id="or'+items.num+'2" value="B">B<br>')
					  break;
					case "C":
						$("#info .form-group:last label:eq(1)").append('<input type="radio" disabled="disabled" name="PSJG'+items.num+'" class="validate[required] c " id="or'+items.num+'3" value="C">C<br>')
					  break;
					case "D":
						$("#info .form-group:last label:eq(1)").append('<input type="radio" disabled="disabled" name="PSJG'+items.num+'" class="validate[required] d" id="or'+items.num+'4" value="D">D')
					  break;
					default:
						break;
				}
				}
				}
			})
			$('#info').append('<div class="form-group"><label class="col-sm-2">注：带*号的项为重点项</label></div>')
		}
	}); */
	$.ajax({
		type: 'post',
		url: '${ctx}/evaluation/getAll.action',
		cache: false,
		success: function (data) {
			$.each(data,function(i,items){
			str = '<form method="post" id="psjgForm" action="" class="form-horizontal tasi-form"><div class="form-group"><label class="col-sm-4 control-label">'+items.required+'</label>'+
          		'<div class="col-sm-3 control-label"><br>'+items.method+'</div>'+
	 		'<div class="col-sm-3 "><input type="hidden" value="'+items.num+'" name="num"><textarea class="form-control validate[required]" readonly="readonly" id="PSJL'+items.num+'" name="psjl">${ss}</textarea></div>'+
      			'<span class="col-sm-2" id="PSJG'+items.num+'"></span></div></form>'
			$('#info').append(str)
			result = items.result.split(',')
			//num+=items.num+",";
			resultnote=items.resultnote.split('/');
			for(var i=0;i<result.length-1;i++){
				dispaly=items.num+"_"+result[i];
				$("#info .form-group:last span:eq(0)").append('<input onclick="autoFill(this.id)" disabled type="radio" name="psjg" class="validate[required] a" id="'+dispaly+'" value="'+result[i]+'">'+result[i]+'<br>')
			}
			})
			$('#info').append('<div class="form-group"><label class="col-sm-2">注：带*号的项为重点项</label></div>')
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
	//获取专家信息
	var ids=$("#expertIds").val();
	if(ids!=""){
		$.post(
				"${ctx}/comUser/getZJUserByIds.action",
				"ids="+ids,
				function(data){
					console.log(data)
					for(var i=0;i<data.length;i++){
						var result='<div class="panel-body" style="margin-top:-20px">'
							+'<span class="col-sm-1" style="display:none;">'+data[i].id+'</span>'
							+'<span class="col-sm-2">'+data[i].username+'</span>'
						    +'<span class="col-sm-3">'+data[i].unit+'</span>'
						    +'<span class="col-sm-3">'+data[i].post+'</span>'
						    +'<span class="col-sm-2"><input type="checkbox" disabled="disabled" value="'+data[i].id+'" checked="checked"/></span>'
						    +'</div>' ;						 
							$("#zjyh").append(result);
							result='';
					}
				}
			);
	}
	
	/* $.post(
		"${ctx}/sysUser/getZJUserByIds.action",
		"ids="+ids,
		function(data){
			console.log(data)
			for(var i=0;i<data.length;i++){
				var result='<div class="panel-body" style="margin-top:-20px">'
					+'<span class="col-sm-1" style="display:none;">'+data[i].id+'</span>'
					+'<span class="col-sm-2">'+data[i].usercode+'</span>'
				    +'<span class="col-sm-3">'+data[i].unit+'</span>'
				    +'<span class="col-sm-3">'+data[i].post+'</span>'
				    +'<span class="col-sm-2"><input type="checkbox" disabled="disabled" value="'+data[i].id+'" checked="checked"/></span>'
				    +'</div>' ;						 
					$("#zjyh").append(result);
					result='';
			}
		}
	); */
});

function logout(){
	wzj.confirm('温馨提醒', '您确定退出本系统吗?', function(flag) {
		if(flag) {
			location.href='${ctx}/logout.action';
		}
	});
}

function logoutRedirect(){
	$.post(
		"${ctx}/logout.action",
		function(data){
			location.href="${ctx}/login.action"
		}
	);
}
function getTaskTime(strDate) {  
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
    if(strDate!=''){
        return str; 
    }else{
    	return ""
    }   
};
jQuery(document).ready(function() {
    $('#psdate').val(getTaskTime($('#psdate').val()).split(' ')[0])
    $('#fzdate').val(getTaskTime($('#fzdate').val()).split(' ')[0])
    //$('#pzdate').val(getTaskTime($('#pzdate').val()).split(' ')[0])
});
function downfunc(type){
	var titleNo=$("#titleNo").val();
	$.ajaxSetup({async : false});
	 $.post(
        "${ctx}/commons/fileExists.action",
        'type='+type+'&titleNo='+titleNo,
        function (data) {
            if(data) {
            	 window.open('${ctx}/commons/downloadFile.action?type='+type+'&titleNo='+titleNo);
            }else{
            	wzj.alert('温馨提示','下载失败！');
            }
        }
    );
}
function viewfunc(type){
// 	$('#media').html("");
// 	$('#media').html("<a id='media' class='media' href=''></a>");
// 	var dataURL;
	var titleNo=$("#titleNo").val();
// 	$.post(
// 			'${ctx}/commons/viewFile.action?type='+type+'&titleNo='+titleNo,
// 			function(data){
// 				if(data!=null)
// 				$.each(data,function(i,item){
// 					$.ajax({
// 						   url: "${ctx}/"+item.uprul,
// 						   type: 'GET',
// 						   complete: function(response){
// 								if(response.status == 404){
// 								  	$('#media').html('<h3 id="notFound" class="text-center">未找到文件！</h3>')
// 								}
// 						   }
// 						 });
// 					$('.media').prop('href',"${ctx}/"+item.uprul); 
// 				    $('a.media').media();  
// 				    $('.media').css('height','100%');
// 				});
// 			}
// 		);
	window.open('../viewPic.jsp?flag=1&titleNo='+titleNo+"&type="+type);
}
</script>
</head>
<body>
	    <!-- BEGIN HEADER --> 
		    <!-- BEGIN WRAPPER  -->
		    <section class="panel" style="margin-bottom:0px">
		    <div class="form-horizontal tasi-form">
                  <section class="wrapper"  >
               <center style="margin-top:-70px;color:#2a8ba7"><h1>查看评审表</h1></center>
			   <!-- BEGIN ROW  -->
			    <div class="row">
                  <div class="col-lg-12">
                     <section class="panel">
                        <header class="panel-heading">
                           <span class="label label-primary">评审信息</span>
                           <span class="tools pull-right" style="margin-top:-8px;">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body" id="info">
						    <div class="form-group">
                                 <label class="col-sm-4 ">评审项目与要求</label>
                                <label class="col-sm-3">评审方法</label>
								<label class="col-sm-3">评审记录</label>
								<label class="col-sm-2">单项评审结果</label>
                              </div>
                              
                              </div>
                              </section>
                        </div>
                        </div>
                                       
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
                           <span class="tools pull-right" style="margin-top:-8px;">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        
                        <div class="panel-body">
                        <label class="col-sm-1" style='display:none;'>编号</label>
                        <label class="col-sm-2">姓名 </label>
                         <label class="col-sm-3 mtop">工作单位</label>
                         <label class="col-sm-3 mtop">职务/职称</label>
                         <label class="col-sm-2 mtop">是否签名</label>
			  			</div>
			  			<input type="hidden" id="expertIds" name="expertIds" value="${rf.expertIds}">
			  			<input type="hidden" id="num" value="">
			  			<input type="hidden" id="num1" value="${num}">
			  			<input type="hidden" id="psjg" value="${psjg}">
			  			<input type="hidden" id="psjl" value="${psjl}">
<!-- 			  			<div class="panel-body" style="margin-top:-20px"> -->
<!--                             <span class="col-sm-2">王佑宁</span> -->
<!--                             <span class="col-sm-4">江苏烟花所</span> -->
<!--                              <span class="col-sm-3">高级工程师</span> -->
<!--                              <span class="col-sm-2"><input type="checkbox" name="status" value="2"/></span> -->
<!-- 			  			</div> -->
                     </section>
                  </div>
               </div>                            
               <div class="row">
                  <div class="col-lg-12">
                     <section class="panel">
                        <header class="panel-heading">
                           <span class="label label-primary">评审组意见及结论</span>
                           <span class="tools pull-right" style="margin-top:-8px;">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
						   <div class="form-group">
                             <div class="col-lg-1"></div>
                             <div class="col-lg-10">
                                  <textarea readonly="readonly" class="form-control" rows="8" id="mavinIdea" name="mavinIdea">${rf.mavinIdea}</textarea>
                             </div>  
                           </div>
                           <div class="form-group">
                             <div class="col-sm-2 text-right">评审组长签名： </div>
                             <div class="col-sm-3">
                             	<input readonly="readonly" type="text" id="psman" name="psman" class="form-control" value="${rf.psman}">
                             </div>
                             <div class="col-sm-2"></div>
                             <div class="col-sm-1 text-right">日期：</div>
                             <div class="col-sm-3">
                        		<input readonly="readonly" class="input-medium default-date-picker form-control" size="16"  id="psdate" name="psdate" value="${rf.psdate}">
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
                           <span class="tools pull-right" style="margin-top:-8px;">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
						   <div class="form-group">
                             <div class="col-lg-1"></div>
                             <div class="col-lg-10">
                                  <textarea readonly="readonly" class="form-control" rows="4" id="frameworkIdea" name="frameworkIdea">${rf.frameworkIdea }</textarea>
                             </div> 
                           </div> 
                           <div class="form-group">
                             <div class="col-sm-2 text-right"> 负责人签名：</div>
                             <div class="col-sm-3">
                                 <input type="text" readonly="readonly" id="fzman" name="fzman" class="form-control" value="${rf.fzman}">
                             </div> 
                             <div class="col-sm-2"></div>
                             <div class="col-sm-1  text-right">日期：</div>
                             <div class="col-sm-3">
                             	<input type="text" readonly="readonly" class="form_datetime form-control" id="fzdate" name="fzdate" value="${rf.fzdate}">
                      		 </div>
                             <!-- <div class="col-sm-2 text-right" style="margin-top:15px;">分支机构盖章：</div> -->
                             
                           </div> 						 
			  			</div>
			  			<div class="col-sm-3">
                                 <input type="text" id="syndic" name="syndic" class="form-control" value="" style="display:none;">
                        </div> 
                     </section>
                  </div>
               </div>
               <div class="row">
                  <div class="col-lg-12">
                     <section class="panel">
                        <header class="panel-heading">
                           <span class="label label-primary">评审表</span>
                           <span class="tools pull-right" style="margin-top:-8px;">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                            <input type="hidden" id="titleNo" name="titleNo" value="${item}" class="form-control">
                        	<input type="hidden" name="qualityno" id="qualityno" value="">
                        </header>
                        <div class="panel-body">
                           <span class="form-horizontal tasi-form">
                              <div class="form-group">
                              	<div class="col-sm-3"></div>
                                 <label class="col-sm-2 control-label labels">评审表：</label>
                                 <div class="col-sm-2 text-center">
                                    <button class="btn btn-info" onclick="viewfunc(6)">查看</button>
                                 </div>
                                 <div class="col-sm-2 text-center">
                                    <button class="btn btn-success" onclick="downfunc(6)">下载</button>
                                 </div>
                                 <div class="col-sm-3"></div>
                              </div>
                              <div class="form-group">
                                 <div class="col-sm-3"></div>
                                 <label class="col-sm-2 control-label labels">评审表（盖章版）：</label>
                                 <div class="col-sm-2 text-center">
                                    <button class="btn btn-info" onclick="viewfunc(7)">查看</button>
                                 </div>
                                 <div class="col-sm-2 text-center">
                                    <button class="btn btn-success" onclick="downfunc(7)">下载</button>
                                 </div>
                                 <div class="col-sm-3"></div>
                              </div>
                           </span>
                        </div>
                     </section>
                  </div>
               </div>
                     </section>
                  


			   <!-- END ROW  -->
			   </div>
            </section>
	    <!-- END SECTION -->
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
     <!-- EDITABLE TABLE FUNCTION  -->
</body>
</html>