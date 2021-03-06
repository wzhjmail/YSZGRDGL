<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<%@ taglib prefix="myshiro" uri="/customize-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>录取信息</title>
	<link href="${ctx}/assets/morris.js-0.4.3/morris.css" rel="stylesheet"><!-- MORRIS CHART CSS -->
	<link href="${ctx}/css/clndr.css" rel="stylesheet"><!-- CALENDER CSS -->
	<link href="${ctx}/css/style_self.css" rel="stylesheet">
	<!-- 查询页面 -->
    <link href="${ctx}/css/bootstrap.min.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/css/bootstrap-reset.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="${ctx}/assets/font-awesome/css/font-awesome.css" rel="stylesheet"><!-- FONT AWESOME ICON STYLESHEET -->
    <link href="${ctx}/assets/data-tables/DT_bootstrap.css" rel="stylesheet" ><!-- DATATABLE CSS -->
    <link href="${ctx}/css/style.css" rel="stylesheet"><!-- THEME BASIC CSS -->
    <link href="${ctx}/assets/fuelux/css/tree-style.css" rel="stylesheet">
    <link href="${ctx}/css/style-responsive.css" rel="stylesheet"><!-- THEME BASIC RESPONSIVE  CSS -->
	<link href="${ctx}/css/fileinput.min.css" media="all" rel="stylesheet" type="text/css" />
	
<script src="${ctx}/js/jquery-1.8.3.min.js" ></script><!-- BASIC JS LIABRARY 1.8.3 -->
<script src="${ctx}/js/style_self.js"></script>
<script src="${ctx}/js/fileinput.min.js" type="text/javascript"></script>
<script src="${ctx}/js/locales/zh.js" type="text/javascript"></script>
<script src="${ctx}/assets/bootstrap-datepicker/js/bootstrap-datepicker.js"></script><!-- 日期显示 -->
<link rel="stylesheet" type="text/css" href="${ctx}/assets/bootstrap-datepicker/css/datepicker.css"><!-- BOOTSTRAP DATEPICKER PLUGIN CSS -->
<script type="text/javascript" src="${ctx}/js/jquery.media.js"></script>
<script src="${ctx}/js/jquery.mloading.js"></script>
	<script src="${ctx}/js/jquery.form.js"></script>
	<style type="text/css">
		.modal-lg{
			width: 100%;
		    height: 100%;
		    margin: 0;
		}
		.change{
		    position: absolute;
		    overflow: hidden;
		    right: 0;
		    top: 0;
		    opacity: 0;
		    width: 0px;
		}
	</style>
<script type="text/javascript">
	$(function(){
		$('.row').css('margin','3px')
		$('.nopaddingright').css('margin-top','5px') 
		$('#lydate').datepicker({
			format:'yyyy-mm-dd',
		});
		getTasks();
		$('#editable-sample tbody').on('click', 'tr', function () {//选中的行
			//获取选中行的第一列的值
			nRow = $(this).parent().find("tr").index($(this)[0]);
			selectId=$(this).children("td").eq(0).text();
			taskId=$(this).children("td").eq(0).text();
			selectCode=$(this).children("td").eq(4).text();
			temptr.css("background-color","");
	        temptr = $(this);
	        temptr.css("background-color","#99ffff");
		});
	});
	//以上是右键特效的js
	var temptr = $();
	var nRow = null;
	var selectId=null;
	var taskId=null;
	var selectCode=null;
	window.onload=function(){//给表添加滚动条
		$("#editable-sample").wrap('<div style="overflow-x:auto;width:100%"></div>');
		SetTableStyle();
		$("body").delegate("#editable-sample tbody tr","click",function(){
			SetTableStyle();
		});
	};
	
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
	            var menudata =null;
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
	        } else {
	            aTable.$('tr.row_selected').removeClass('row_selected');
	            $(this).addClass('row_selected');
	        }
	    });
	}
	
	function cancel(){
		if(selectId==null){
			wzj.alert('温馨提示', '请选一条择数据！')
			return false;
		}else if(selectId==""){
			wzj.alert('温馨提示','数据不能为空！');
			return false;
		}else{
			wzj.confirm('温馨提示','您确定要删除这条数据？',function(flag){
				$.post();
			});
		}
	}
	var simpleIds="";
	function getTasks(){
		$.ajaxSetup({async : false});
		$.post(
				"${ctx}/pbtSample/getSampleByCom.action",
				"comName="+$("#uf_0047225").val(),
				function(data){
				$.each(data,function(i,item){
					simpleIds+=item.uf_sample_code+","
					$("#result").append("<tr ondblclick=$('#view').click()>"
							+"<td>"+(i+1)+"</td>"
							+"<td>"+item.f_sample_name+"</td>"
							+"<td>"+item.uf_0047228+"</td>"
							+"<td>"+item.uf_0047233+"</td>"
							+"<td>"+item.uf_sample_code+"</td>"
							+"<td>"+item.uf_0047232+"</td>"
							+"<td>"+item.uf_0047251+"</td>"
							+"<td>"+item.uf_0047330+"</td>"
							+"<td>"+item.uf_code_type+"</td>"
							+"<td>"+item.uf_0047234+"</td>"
							+"<td>"+DateHandle(item.uf_004711)+"</td>"
							+"<td>"+item.f_sample_count+"</td>"
							+"<td>"+item.uf_0047332+"</td>"
							+"<td>"+item.uf_0047236+"</td>"
							+"<td>"+item.uf_0047231+"</td>"
							+"<td>"+item.f_batch_type+"</td>"
							+"<td>"+item.f_batch_code+"</td>"
							+"<td>"+item.uf_0047238+"</td>"
							+"<td>"+item.f_spot_code+"</td>"
							+"<td>"+item.uf_0047220+"</td>"
							+"<td>"+item.uf_0047218+"</td>"
							+"<td>"+item.uf_0047331+"</td>"
							+"<td>"+item.uf_0047141+"</td></tr>"
					);
				});
				if(simpleIds!=""){
	        		simpleIds=simpleIds.substr(0,simpleIds.length-1);
	        	}
			});
	}
    
	function DateHandle(obj) {  
	  if(obj!=null){
	    var objDate=new Date(obj); //创建一个日期对象表示当前时间     
	    var year=objDate.getFullYear();   //四位数字年     
	    var month=objDate.getMonth()+1;   //getMonth()返回的月份是从0开始的，还要加1     
	    var date=objDate.getDate();     
	    var date = year+"-"+month+"-"+date;  
	    return date; 
	  }else{
	    	return null;
	  }
	}  
	
	function view(){
		$("#uid").val(0);
		if(selectId=="数据为空"){
			wzj.alert('温馨提示','数据为空！');
		}else if(selectCode==null){
			wzj.alert('温馨提示','请选择一个样品！');
			return false;
		}else{
			$("#view").attr("href","#Modal_show");
			$.post(
					"${ctx}/pbtSample/getSampleByCode.action",
					"code="+selectCode+"&comName="+$("#uf_0047225").val(),
					function(data){//刷新页面
						$("#f_sample_name").val(data.f_sample_name);
						$("#f_sample_name").attr({ readonly: 'true' });
						$("#uf_0047233").val(data.uf_0047233);
						$("#uf_0047233").attr({ readonly: 'true' });
						$("#uf_0047232").val(data.uf_0047232);
						$("#uf_0047232").attr({ readonly: 'true' });
						$("#uf_0047330").val(data.uf_0047330);
						$("#uf_0047330").attr({ readonly: 'true' });
						$("#uf_0047234").val(data.uf_0047234);
						$("#uf_0047234").attr({ readonly: 'true' });
						$("#f_sample_count").val(data.f_sample_count);
						$("#f_sample_count").attr({ readonly: 'true' });
						$("#uf_0047236").val(data.uf_0047236);
						$("#uf_0047236").attr({ readonly: 'true' });
						$("#f_batch_type").val(data.f_batch_type);
						$("#f_batch_type").attr({ readonly: 'true' });
						$("#uf_0047238").val(data.uf_0047238);
						$("#uf_0047238").attr({ readonly: 'true' });
						$("#uf_0047220").val(data.uf_0047220);
						$("#uf_0047220").attr({ readonly: 'true' });
						$("#uf_0047331").val(data.uf_0047331);
						$("#uf_0047331").attr({ readonly: 'true' });
						
						$("#uf_0047228").val(data.uf_0047228);
						$("#uf_0047228").attr({ readonly: 'true' });
						$("#uf_sample_code").val(data.uf_sample_code);
						$("#uf_sample_code").attr({ readonly: 'true' });
						$("#uf_0047251").val(data.uf_0047251);
						$("#uf_0047251").attr({ readonly: 'true' });
						$("#uf_code_type").val(data.uf_code_type);
						$("#uf_code_type").attr({ readonly: 'true' });
						$("#lydate").val(DateHandle(data.uf_004711));
						$("#lydate").attr({ readonly: 'true' });
						$("#uf_0047332").val(data.uf_0047332);
						$("#uf_0047332").attr({ readonly: 'true' });
						$("#uf_0047231").val(data.uf_0047231);
						$("#uf_0047231").attr({ readonly: 'true' });
						$("#f_batch_code").val(data.f_batch_code);
						$("#f_batch_code").attr({ readonly: 'true' });
						$("#f_spot_code").val(data.f_spot_code);
						$("#f_spot_code").attr({ readonly: 'true' });
						$("#uf_0047218").val(data.uf_0047218);
						$("#uf_0047218").attr({ readonly: 'true' });
						$("#uf_0047141").val(data.uf_0047141);
						$("#uf_0047141").attr({ readonly: 'true' });
					}
				);
		}
	}
	
	function toAttach(){
		document.getElementById("display").src="";
		var dataURL;
		$.post(
				"${ctx}/pbtSample/getSampleByCode.action",
				"code="+selectCode,
				function(data){//刷新页面
					if(data.uf_attach!=null){
						dataURL=data.uf_attach;
						$.ajax({
						   url: "${ctx}/"+dataURL,
						   type: 'GET',
						   complete: function(response){
								if(response.status == 404){
								  	$('#notFound').show()
									$('#display').hide()
									$('#notFound').parent().css('height',"")
								}else{
								  	$('#notFound').hide()
									$('#display').show()
									$('#notFound').parent().css('height',"500px")
								}
						   }
						 });
					}else{
						$('#notFound').show()
						$('#display').hide()
						$('#notFound').parent().css('height',"")
					}
				}
			);
		if(dataURL!=null)
		document.getElementById("display").src="${ctx}/"+dataURL;
		else
			$('#display').show()
	}
	function apply(item){
		if(nRow==null){
			wzj.alert('温馨提示','请选择一个样品！');
		}else if(selectId=="数据为空"){
			wzj.alert('温馨提示','数据为空！');
			return false;
		}else{
			//$("#comName").val($("#uf_0047225").val());
			//$("#code").val(selectCode);
			$("#sampleId").val(selectCode);
			$(item).attr("href","#Modal_show3");	
		}
	}
	function toAttaches(){
		if(selectId=="数据为空"){
			wzj.alert('温馨提示','数据为空！');
		}else if(selectCode==null){
			wzj.alert('温馨提示','请选择一个样品！');
			return false;
		}else{
		$("#toAttaches").attr("href","#Modal_show4");
		$("#attachResult").html("");
		$.ajaxSetup({async : false});
		$.post(
				"${ctx}/commons/getSampleAttach2.action",
				"titleNo="+$("#titleNo").val()+"&sampleId="+selectCode,
				function(data){
					$.each(data,function(i,item){
						var fileTypeStart=item.uprul.lastIndexOf('.');
						var fileType=item.uprul.substring(fileTypeStart+1,item.uprul.length);
						var fileNameStart=item.uprul.lastIndexOf('/');
						var fileName=item.uprul.substring(fileNameStart+1,item.uprul.length);
						var types=["pdf","txt","jpg","jpeg","png","bmp","gif","svg","psd","pcd","raw","tiff"];
						$("#attachResult").append("<tr>"
								+"<td>"+(i+1)+"</td>"
								+"<td>"+fileName+"</td>"
								+"<td>"+fileType+"</td>"
								+'<td><button onclick="showView(this.value)" class="btn btn-info" value="'+item.uprul+'">'
								+(types.indexOf(fileType)!=-1?"查看":"下载")+'<i class="fa fa-eye"></i></button></td></tr>'
						);
					});
			});
		}
	}
	function showView(upurl){
		window.open('../viewPic.jsp?flag=2&code='+upurl)
	}
	/*function showView(upurl){
		$('#media').html("");
		$("#media").css("height","500px");
		$('#media').html("<a id='media' class='media' href=''></a>");
		document.getElementById("displayPic").src="";
		if(upurl!=null){
				$.ajax({
					url: "${ctx}/"+upurl,
					type: 'GET',
					complete: function(response){
							if(response.status == 404){
							$('#media').html('<h3 id="notFound" class="text-center">未找到文件！</h3>')
							}
					}
				});
				var fileType=upurl.substring(upurl.length-3,upurl.length);
				console.log("fileType:"+fileType);
				if(fileType=="pdf"){
					$('.media').prop('href',"${ctx}/"+upurl); 
					$('a.media').media();  
					$('.media').css('height','100%');
				}
				else{
					$("#media").css("height","0");
					document.getElementById("displayPic").src="${ctx}/"+upurl;
				}
		}
	}*/
	function upReports(){
		if(selectId=="数据为空"){
			wzj.alert('温馨提示','数据为空！');
		}else if(selectCode==null){
			wzj.alert('温馨提示','请选择一个样品！');
			return false;
		}else{
		$("#upReports").attr("href","#Modal_show6");
		$("#sampleId").val(selectCode);
		$("#mulfile").fileinput({
//         	uploadUrl:"${ctx}/pbtSample/uploadSampleReportNew?titleNo="+$("#titleNo").val()+"&describe=分中心检测报告&sampleId="+selectCode,
    		uploadUrl:"${ctx}/commons/PLuploadSampleFile?titleNo="+$("#titleNo").val()+"&describe=分中心检测报告&sampleId="+selectCode,
		    fileType: "any",
    		language : 'zh',
    		enctype : 'multipart/form-data',
    		
    	});
		}
	}
	function toReports(){
		if(selectId=="数据为空"){
			wzj.alert('温馨提示','数据为空！');
		}else if(selectId==null){
			wzj.alert('温馨提示','请选择一个样品！');
			return false;
		}else{
		$("#toReports").attr("href","#Modal_show7");
		$("#reportResult").html("");
		$.ajaxSetup({async : false});
		$.post(
				"${ctx}/commons/getSampleAttach.action",
				"titleNo="+$("#titleNo").val()+"&sampleId="+selectCode,
				function(data){
				$.each(data,function(i,item){
					var fileTypeStart=item.uprul.lastIndexOf('.');
					var fileType=item.uprul.substring(fileTypeStart+1,item.uprul.length);
					var fileNameStart=item.uprul.lastIndexOf('/');
					var fileName=item.uprul.substring(fileNameStart+1,item.uprul.length);
					var types=["pdf","txt","jpg","jpeg","png","bmp","gif","svg","psd","pcd","raw","tiff"];
					if(item.updescribe=="分中心检测报告"){
					$("#reportResult").append("<tr>"
							+"<td>"+(i+1)+"</td>"
							+"<td>"+fileName+"</td>"
							+"<td>"+fileType+"</td>"
							+'<td><button onclick="showView(this.value)" class="btn btn-info"  value="'+item.uprul+'">'
                      	  	+(types.indexOf(fileType)!=-1?"查看":"下载")+' <i class="fa fa-eye"></i></button></td>'
                      	  	+"<td><button onclick=deleteFile("+item.id+") class='btn btn-danger'>删除</button></td></tr>"
					);
					}
				});
			});
		}
	}
	function deleteFile(id){
		$.post(
			"${ctx}/commons/delFileById.action",
			"id="+id,
			function(data){
				toReports();
			}
		);
	}
	function clearFile(){
		$("#file-content").html("");
		$("#file-content").html('<input id="mulfile" name="mfile" type="file" class="file-loading" multiple="multiple">');
	}
</script>
</head>
<body>
	<iframe id="id_iframe" name="nm_iframe" style="display:none;"></iframe>
               <section class="panel">
                  <div class="panel-body">
                     <div class="adv-table editable-table">
                        <div class="clearfix">
                        	<div class="btn-group  nopaddingleft" >
                             <button onclick="view()" class="btn btn-info" data-toggle='modal' href='' id="view">
                            	  查看 <i class="fa fa-eye"></i>
                              </button>
                           </div>
                           <div class="btn-group ">
                             <button data-toggle='modal' onclick="toAttaches()" href='' class="btn btn-info" id="toAttaches">
                            	 查看附件 <i class="fa fa-file"></i>
                              </button>
                           </div>
                           <!--  <div class="btn-group ">
                              <button  data-toggle="modal" href="" onclick="apply(this)" class="btn btn-info" >
                            	上传报告 <i class="fa fa-file"></i>
                              </button>
                           </div>-->
                           <div class="btn-group ">
                              <button  data-toggle="modal" href="" onclick="upReports()" class="btn btn-info" id="upReports">
                            	上传报告 <i class="fa fa-file"></i>
                              </button>
                           </div>
                           <div class="btn-group ">
                             <button data-toggle='modal' onclick="toReports()" href='' class="btn btn-info" id="toReports">
                            	 查看报告 <i class="fa fa-file"></i>
                              </button>
                           </div>
                           <div class="btn-group ">
                             <button onclick="javascript :history.back(-1)" class="btn btn-danger" id="delbtn">
                            	 返回 <i class="fa fa-reply"></i>
                              </button>
                           </div>
                       <!--     <div class="btn-group pull-right">
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
                        <table class="table table-bordered" style="width:460%" id="editable-sample">
                           <thead>
                              <tr>
                                 <th class="">序号</th>                                 
                                 <th class="">检测对象</th>
                                 <th class="">样品名称</th>
                                 <th class="">条码号</th>
                                 <th class="">样品序号</th>
                                 <th class="">条码类型</th>
                                 <th class="">产品名称</th>
                                 <th class="">商标</th>
                                 <th class="">规格/包装</th>
                                 <th class="">样品数量</th>
                                 <th class="">应用场景</th>
                                 <th class="">样品来源</th>
                                 <th class="">来样日期</th>
                                 <th class="">样品序号类型</th>
                                 <th class="">样品状态描述</th>
                                 <th class="">保密要求</th>
                                 <th class="">印刷载体</th>
                                 <th class="">批次编号类型</th>
                                 <th class="">样品批次编号</th>
                                 <th class="">送样人</th>
                                 <th class="">业务类别</th>
                                 <th class="">包装上条码的数量</th>
                                 <th class="">备注</th>
                              </tr>
                           </thead>
                           <tbody id="result">
                              <!-- 循环显示查询出来的结果 -->
                             
                           </tbody>
                        </table>
                     </div>
                                      <div class="row" style="width:100%">
                <form method="post" id="thisform" action="${ctx}/application/report/issueReport.action">  
  				<label class="col-sm-1 control-label labels" style="margin-top: 15px">意见:</label>
                <div class="col-sm-6">
                   <textarea class="form-control" placeholder="完成上传样品信息！" rows="2" name="comment" id="comment"></textarea>
                </div>
                <input type="hidden" name="taskId" value="${taskId}" >
                <input type="hidden" name="titleno" id="titleno" value="">
                <div class="col-sm-1" style="margin-top: 10px">
                	<button class="btn btn-info" id="thissubmit">提交</button>
                </div>
                </form>
			</div>
                  </div>
 
               </section>
	<div id="Modal_show" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" id="close" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
                	<h4 class="modal-title">样品信息</h4>
              	</div>
				<div class="modal-body">
				
				<input type="hidden" name="uf_0047225" id="uf_0047225" value="${item}">
				<!-- 第一行 -->
					<div class="row" style="width:100%">
						<label class="col-sm-2 text-right nopaddingright">检测对象</label>
            			<div class="col-sm-4">
                			<input type="text" class="form-control" name="f_sample_name" id="f_sample_name">
                		</div>
              			<label class="col-sm-2 text-right nopaddingright">样品名称</label>
                		<div class="col-sm-4">
                  			<input type="text" class="form-control" name="uf_0047228" id="uf_0047228">
                		</div>
					</div> 
				<!-- 第二行 -->
					<div class="row" style="width:100%">
						<label class="col-sm-2 text-right nopaddingright">条码号</label>
            			<div class="col-sm-4">
                			<input type="text" class="form-control" name="uf_0047233" id="uf_0047233">
                		</div>
              			<label class="col-sm-2 text-right nopaddingright">样品序号</label>
                		<div class="col-sm-4">
                  			<input type="text" class="form-control" name="uf_sample_code" id="uf_sample_code">
                		</div>
					</div> 
				<!-- 第三行 -->
					<div class="row" style="width:100%">
						<label class="col-sm-2 text-right nopaddingright ">条码类型</label>
            			<div class="col-sm-4">
                			<input type="text" class="form-control" name="uf_0047232" id="uf_0047232">
                		</div>
              			<label class="col-sm-2 text-right nopaddingright">产品名称</label>
                		<div class="col-sm-4">
                  			<input type="text" class="form-control" name="uf_0047251" id="uf_0047251">
                		</div>
					</div> 
				<!-- 第十行 -->
					<div class="row" style="width:100%">
						<label class="col-sm-2 text-right nopaddingright">商标</label>
            			<div class="col-sm-4">
                			<input type="text" class="form-control" name="uf_0047220" id="uf_0047220">
                		</div>
              			<label class="col-sm-2 text-right nopaddingright">规格/包装</label>
                		<div class="col-sm-4">
                  			<input type="text" class="form-control" name="uf_0047218" id="uf_0047218">
                		</div>
					</div>
				<!-- 第四行 -->
					<div class="row" style="width:100%">
						<label class="col-sm-2 text-right nopaddingright">样品数量</label>
            			<div class="col-sm-4">
                			<input type="text" class="form-control" name="f_sample_count" id="f_sample_count">
                		</div>
						<label class="col-sm-2 text-right nopaddingright">应用场景</label>
            			<div class="col-sm-4">
                			<input type="text" class="form-control" name="uf_0047330" id="uf_0047330">
                		</div>
					</div> 
				<!-- 第五行 -->
					<div class="row" style="width:100%">
						<label class="col-sm-2 text-right nopaddingright">样品来源</label>
            			<div class="col-sm-4">
                			<input type="text" class="form-control" name="uf_0047234" id="uf_0047234">
                		</div>
              			<label class="col-sm-2 text-right nopaddingright">来样日期</label>
                		<div class="col-sm-4">
                  			<input class="form-control validate[custom[date]]" id="lydate" name="uf_004711" id="uf_004711">
                		</div>
					</div> 
				<!-- 第六行 -->
					<div class="row" style="width:100%">
              			<label class="col-sm-2 text-right nopaddingleft nopaddingright">样品编号类型</label>
                		<div class="col-sm-4">
                  			<input type="text" class="form-control" name="uf_code_type" id="uf_code_type">
                		</div>
              			<label class="col-sm-2 text-right nopaddingleft nopaddingright">样品状态描述</label>
                		<div class="col-sm-4">
                  			<input type="text" class="form-control" name="uf_0047332" id="uf_0047332">
                		</div>
					</div> 
				<!-- 第七行 -->
					<div class="row" style="width:100%">
						<label class="col-sm-2 text-right nopaddingright">保密要求</label>
            			<div class="col-sm-4">
                			<input type="text" class="form-control" name="uf_0047236" id="uf_0047236">
                		</div>
              			<label class="col-sm-2 text-right nopaddingright">印刷载体</label>
                		<div class="col-sm-4">
                  			<input type="text" class="form-control" name="uf_0047231" id="uf_0047231">
                		</div>
					</div> 
				<!-- 第八行 -->
					<div class="row" style="width:100%">
						<label class="col-sm-2 text-right nopaddingleft nopaddingright">批次编号类型</label>
            			<div class="col-sm-4">
                			<input type="text" class="form-control" name="f_batch_type" id="f_batch_type">
                		</div>
              			<label class="col-sm-2 text-right nopaddingleft nopaddingright">样品批次编号</label>
                		<div class="col-sm-4">
                  			<input type="text" class="form-control" name="f_batch_code" id="f_batch_code">
                		</div>
					</div>
				<!-- 第九行 -->
					<div class="row" style="width:100%">
						<label class="col-sm-2 text-right nopaddingright">送样人</label>
            			<div class="col-sm-4">
                			<input type="text" class="form-control" name="uf_0047238" id="uf_0047238">
                		</div>
              			<label class="col-sm-2 text-right nopaddingright">业务类别</label>
                		<div class="col-sm-4">
                  			<input type="text" class="form-control" name="f_spot_code" id="f_spot_code">
                		</div>
					</div>
				
				<!-- 第十一行 -->
					<div class="row" style="width:100%">
						<label class="col-sm-2 text-right nopaddingleft nopaddingright">包装上条码数量</label>
            			<div class="col-sm-4">
                			<input type="text" class="form-control" name="uf_0047331" id="uf_0047331">
                		</div>
              			<label class="col-sm-2 text-right nopaddingright">备注</label>
                		<div class="col-sm-4">
                  			<input type="text" class="form-control" name="uf_0047141" id="uf_0047141">
                		</div>
					</div>
				</div>
				<div class="modal-footer" style="margin-top:0px;" name="" id="">
					<button data-dismiss="modal" class="btn btn-success text-right" id="uid">确定</button>
				</div>
			</div>
		</div>
	</div>
	<div id="Modal_show2" class="modal fade">
	          <div class="modal-dialog">
	            <div class="modal-content">
	              <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
	                  &times;
	                </button>
	                <h4 class="modal-title">附件</h4>
	              </div>
	              <div class="modal-body" style="height: 500px">
	              <h3 id="notFound" class="text-center" style="display: none;">未找到文件！</h3>
	              <iframe id="display" src="" frameborder="0" width="100%" height="100%" leftmargin="0" topmargin="0" ></iframe>
		         </div>
		         <div class="modal-footer" style="margin-top:0px;">
	                <button data-dismiss="modal" class="btn btn-success" type="button">
	                 	确定
	                </button>
	              </div>
	              </div>
	            </div>
	          </div>
	      <div id="Modal_show3" class="modal fade">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                  &times;
                </button>
                <h4 class="modal-title">上传检测报告</h4>
              </div>
              <form id="forms" method="post" target="nm_iframe" action="" enctype="multipart/form-data">
				<div class="form-group">
                            <div class="modal-body">
                            <div style="margin-left: 30%">
                            <button type="button" onclick="sq.click()" class="btn btn-info" >选择文件</button>
			<input type="file" name="file" id="sq"  class="change validate[required]">
			<input type="hidden" name="titleNo" id="titleNo" value="${titleNo}">
			<input type="hidden" name="describe" id="describe" value="分中心检测报告">
			<input type="hidden" name="sampleId" id="sampleId" value="">
			<b id="em" style="padding: 7px;margin: 0">未上传文件</b>
                            </div>
              <div class="modal-body hidden">
                <table border="0" cellspacing="100px" align="center" style="height: 70px;width:100%">
					<tr id="trs"><td style="height: 10%;" align="center">
					
					</td></tr>
				</table>
	         </div>
             				 </div> 
                                 <div class="col-sm-2"></div>
                              </div>
			 <div class="modal-footer" style="margin-top:0px;">
                <button data-dismiss="modal" class="btn btn-success" onclick="submits()">
                 	确定
                </button>
              </div>
              </form>
              </div>
            </div>
          </div>
          <div id="Modal_show4" class="modal fade">
	          <div class="modal-dialog">
	            <div class="modal-content">
	              <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
	                  &times;
	                </button>
	                <h4 class="modal-title">附件</h4>
	              </div>
	              <div class="modal-body" style="height: 500px">
	              	<table class="table table-bordered" style="width:100%" id="editable-sample">
                           <thead>
                              <tr>
                              	 <th class="">序号</th>
                                 <th class="">文件名</th>
								 <th class="">类型</th>
								 <th class="">查看</th>
                              </tr>
                           </thead>
                           <tbody id="attachResult">
                              <!-- 循环显示查询出来的结果 -->
                             
                           </tbody>
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
	          <div id="Modal_show5" class="modal fade" >
	          <div class="modal-dialog">
	            <div class="modal-content" style="width:120%;align:center">
	              <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
	                  &times;
	                </button>
	                <h4 class="modal-title">查看</h4>
	              </div>
	              <div>
	              <input type="hidden" id="URL" name="URL" value="" class="form-control">
	              </div>
	              <div>
	              </div>
	              <div id="media" class="modal-body" style="height: 500px">
	              <a class="media" href=""></a>
		         </div>
		         <div class="modal-body">
                <table width="100%" border="0" cellspacing="100px" align="center">
					<tr id="trs"><td><img id="displayPic" src="" width="100%"/></td></tr>
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
	          <div id="Modal_show6" class="modal fade">
	          <div class="modal-dialog">
	            <div class="modal-content">
	              <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
	                  &times;
	                </button>
	                <h4 class="modal-title">附件</h4>
	              </div>
	              
	              <form action="" method="post" enctype="multipart/form-data" id="uploadForm">
                  <div class="form-group" id="file-content" style="padding: 15px">
                    <input id="mulfile" name="mfile" type="file" class="file-loading" multiple="multiple">
                  </div>	
               	  </form>
		         <div class="modal-footer" style="margin-top:0px;">
	                <button data-dismiss="modal" class="btn btn-success text-right" onclick="clearFile()">
	                	确定
	                </button>
	              </div>
	              </div>
	            </div>
	          </div>
	          <div id="Modal_show7" class="modal fade">
	          <div class="modal-dialog">
	            <div class="modal-content">
	              <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
	                  &times;
	                </button>
	                <h4 class="modal-title">附件</h4>
	              </div>
	              <div class="modal-body" style="height: 500px">
	              	<table class="table table-bordered" style="width:100%" id="editable-sample">
                           <thead>
                              <tr>
                              	 <th class="">序号</th>
                                 <th class="">文件名</th>
								 <th class="">类型</th>
								 <th class="">查看</th>
								 <th class="">删除</th>
                              </tr>
                           </thead>
                           <tbody id="reportResult">
                              <!-- 循环显示查询出来的结果 -->
                             
                           </tbody>
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
	<script src="${ctx}/js/editable-table-role.js" ></script><!-- EDITABLE TABLE JS  -->
	<script src="${ctx}/js/timeChange.js" ></script>
    <script src="${ctx}/assets/fuelux/js/tree.min.js"></script><!-- TREE JS  -->
    <!-- EDITABLE TABLE FUNCTION  -->
    <script>
        jQuery(document).ready(function() {
            EditableTable.init();
        }); 
        function submits(){
        	var file=$('#em').text();
        	if(file.split(".")[1]!="pdf"){
				wzj.alert('温馨提醒', '请上传pdf格式文件！')
			}else{
				$('#forms').attr("action","${ctx}/pbtSample/uploadSampleReport.action");
				$('#forms').submit();
			}
        }
        $("#thissubmit").click(function(e){
        	e.preventDefault()
        	$.post(
        		"${ctx}/pbtSample/checkUploadedReport.action",
        		"titleNo="+$("#titleNo").val()+"&UpDescribe=分中心检测报告&sampleIds="+simpleIds,
        		function(data){
        			if(data.result=="true"){
        				if($('#comment').val()==""){
        					$('#comment').val('完成上传样品信息！')
        				}
        				$('#titleno').val($('#titleNo').val())
        				$('#thisform').submit()
        			}else{
        				wzj.alert("温馨提示",data.result);
        			}
        		}
        	);
        })
    
        $('#sq').on('change', function( e ){
            //e.currentTarget.files 是一个数组，如果支持多个文件，则需要遍历
            var name = e.currentTarget.files[0].name;
            $('#em').text(name);
     	});
        $.validationEngineLanguage.allRules.date = {  
	    	      "regex": /^(19[0-9]{2}|20[0-9]{2})-(0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-9]|3[0-1])$/,
	    	      "alertText": "* 请输入正确的日期格式"  
	   	}; 
</script>
   </script>
  <!-- END JS --> 
</body>
</html>