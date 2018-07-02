<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="myshiro" uri="/customize-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>分支机构管理</title>
	<link href="${ctx}/assets/morris.js-0.4.3/morris.css" rel="stylesheet"><!-- MORRIS CHART CSS -->
	<link href="${ctx}/css/clndr.css" rel="stylesheet"><!-- CALENDER CSS -->
    <link href="${ctx}/css/bootstrap.min.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="${ctx}/css/bootstrap-reset.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="${ctx}/assets/font-awesome/css/font-awesome.css" rel="stylesheet"><!-- FONT AWESOME ICON STYLESHEET -->
    <link href="${ctx}/assets/data-tables/DT_bootstrap.css" rel="stylesheet" ><!-- DATATABLE CSS -->
    <link href="${ctx}/css/style.css" rel="stylesheet"><!-- THEME BASIC CSS -->
    <link href="${ctx}/css/style-responsive.css" rel="stylesheet"><!-- THEME BASIC RESPONSIVE  CSS -->
    <link href="${ctx}/css/style_self.css" rel="stylesheet">
	<script src="${ctx}/js/jquery-1.8.3.min.js" ></script><!-- BASIC JS LIABRARY 1.8.3 -->
	<script src="${ctx}/js/style_self.js" ></script>
	<link rel="stylesheet" href="${ctx}/css/validationEngine.jquery.css">
	<script src="${ctx}/js/jquery.validationEngine-zh_CN.js"></script>
	<script src="${ctx}/js/jquery.validationEngine.js"></script>
<script type="text/javascript">
	$(function(){
// 		$("#editable-sample").wrap('<div style="overflow-x:auto;width:100%"></div>');
 		$(".row").css('margin','5px 0px');
		$('.labels').css('text-align','right')
		branchCenMag();
		getProvince();
		$('#editable-sample tbody').on('click', 'tr', function () {//选中的行
			//获取选中行的第一列的值
			nRow = $(this).parent().find("tr").index($(this)[0]);
			selectId=$(this).children("td").eq(1).text();
			taskId=$(this).children("td").eq(1).text();
			temptr.css("background-color","");
	        temptr = $(this);
	        temptr.css("background-color","#99ffff");
		});
// 		删除
		$("#delete").click(
				function(){
					if(nRow==null){
						wzj.alert('温馨提示','请选择一个分支机构！');
					}else if(selectId=="数据为空"){
						wzj.alert('温馨提示','数据为空！');
						return false;
					}else{
						wzj.confirm('温馨提示','您确定删除？',function(flag){
							if(flag){
								$.post(
										"${ctx}/branchCenter/deleteBranch.action",
										"code="+selectId,
										function(data){//刷新页面
											window.location.reload();
										}
									);
							}
						});		
				}}	
			);
			
		$("#editable-sample tbody tr").dblclick(function(e){
			$("#check").click();
		});
	});//开始 
// 	右键特效
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
	        	if(nRow==null){
					wzj.alert('温馨提示','请选择一个分支机构！');
				}else if(selectId==null){
					wzj.alert('温馨提示','数据为空！');
					return false;
				}else{
					$("#update").click();
				}
	        }
	    });
	    imageMenuData.push({
	        "text": "查看", "func": function () {
	        	if(nRow==null){
					wzj.alert('温馨提示','请选择一个分支机构！');
				}else if(selectId==null){
					wzj.alert('温馨提示','数据为空！');
					return false;
				}else{
					$("#check").click();
				}
	        }
	    });
	    imageMenuData.push({
	        "text": "<font color='red'>删除</font>", "func": function () {
	        	if(nRow==null){
					wzj.alert('温馨提示','请选择一个分支机构！');
				}else if(selectId==null){
					wzj.alert('温馨提示','数据为空！');
					return false;
				}else{	
					$("#delete").click();
					}
				}
	    });
	   
	       imageMenuData2.push(imageMenuData);
	       
	       return imageMenuData2;
	}
	var temptr = $();
	var nRow = null;
	var selectId=null;
	window.onload=function(){//给表添加滚动条
		$("#editable-sample").wrap('<div style="overflow-x:auto;width:100%"></div>');
		SetTableStyle();
	};
	
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
// 	更新回显  
	function toUpdateBranch(){
		if(nRow==null){
			wzj.alert('温馨提示','请选择一个分支机构！');
		}else if(selectId=="数据为空"){
			wzj.alert('温馨提示','数据为空！');
			return false;
		}else{
			$("#update").attr("href","#Modal_show1");
					$("#code1").val("");
					$("#name1").val("");
					$("#oldName").val("");
					$("#oldCode").val("");
					$("#address1").val("");
					$("#postcode1").val("");
					$("#linkman1").val("");
					$("#phone11").val("");
					$("#phone21").val("");
					$("#fax1").val("");
					var obj1 = document.getElementById("province1");    
			   		obj1.selectedIndex = 0; 
			   		var obj2 = document.getElementById("city1");    
			   		obj2.selectedIndex = -1; 
			   		var obj3 = document.getElementById("area1");    
			   		obj3.selectedIndex = -1; 
// 			   		var obj4 = document.getElementById("province3");    
// 			   		obj4.selectedIndex = 0; 
// 			   		var obj5 = document.getElementById("city3");    
// 			   		obj5.selectedIndex = -1; 
// 			   		var obj6 = document.getElementById("area4");    
// 			   		obj6.selectedIndex = -1; 
// 			   		$("#select1").empty();
					$.post(
							"${ctx}/branchCenter/toUpdateBranch.action",
							"code="+selectId,
							function(data){//刷新页面
								console.log(data)
								$("#code1").val(data.code);
								$("#oldCode").val(data.code);
								$("#name1").val(data.name);
								$("#oldName").val(data.name);
								$("#address1").val(data.address);
								$("#postcode1").val(data.postcode);
								$("#linkman1").val(data.linkman);
								$("#phone11").val(data.phone1);
								$("#phone21").val(data.phone2);
								$("#fax1").val(data.fax);
								$("#branchcode").val(selectId);
// 								var pro = data.province
// 								var cit = data.city
// 								var are = data.area
								
					    		$.post(
					            		"${pageContext.request.contextPath}/divisionRegion/getRegionChildren.action",
					            		"regioncode="+pro,
					            		function(data){
					            			$("#city1").empty();
					            			$("#area1").empty();
					            			$("#city1").append("<option value=''> 请选择市 </option>");
					            			if(data!=null&&data.length>0){
					            				for(var i=0;i<data.length;i++){
					            					var result='<option id="'+ data[i].regioncode +'" value="'+data[i].regioncode+'">'+data[i].regionname+"</option>";
					            					$("#city1").append(result);
					            				}
						       			  		 $("#city1 option").each(function(){
						     	 					if($(this).val() == cit){
						     	 						$(this).attr('selected','selected');
						      		  			   }
						     					}); 
					            			}
					            		}
					            	);
					    		$.post(
					            		"${pageContext.request.contextPath}/divisionRegion/getRegionChildren.action",
					            		"regioncode="+cit,
					            		function(data){
					            			$("#area1").empty();
					    					$("#area1").append("<option value=''> 请选择地区 </option>");
					            			if(data!=null&&data.length>0){
					            				for(var i=0;i<data.length;i++){
					            					var result='<option id="'+ data[i].regioncode +'" value="'+data[i].regioncode+'">'+data[i].regionname+"</option>";
					            					$("#area1").append(result);
					            				}
						       			  		 $("#area1 option").each(function(){
							     	 					if($(this).val() == are){
							     	 						$(this).attr('selected','selected');
							      		  			   }
							     				}); 
					            			}
					            		}
					            	);
								$.post(
										"${ctx}/branchCenter/getOffice.action",
										"code="+selectId,
										function(data){
											if(data!=null&&data.length>0){
							   					for(var i=0;i<data.length;i++){
							   						if(data[i].allregionname!=null){
							   							var result='<option id="'+data[i].regioncode+'" value="'+data[i].regioncode+'" name="selectArea">'+data[i].allregionname+"</option>";
			 											$("#select1").append(result);
							   						}
							   					}
							   				}
										}
									);
							}
						);
			}
		}	
//查看
	function viewBranch(){
		if(nRow==null){
			wzj.alert('温馨提示','请选择一个分支机构！');
		}else if(selectId=="数据为空"){
			wzj.alert('温馨提示','数据为空！');
			return false;
		}else{
			$("#check").attr("href","#Modal_show2");
			$("#code2").val("");
			$("#name2").val("");
			$("#address2").val("");
			$("#postcode2").val("");
			$("#linkman2").val("");
			$("#phone12").val("");
			$("#phone22").val("");
			$("#fax2").val("");
			$("#area2").empty();
			$.post(
					"${ctx}/branchCenter/toUpdateBranch.action",
					"code="+selectId,
					function(data){//刷新页面
						$("#code2").val(data.code);
						$("#name2").val(data.name);
						$("#address2").val(data.address);
						$("#postcode2").val(data.postcode);
						$("#linkman2").val(data.linkman);
						$("#phone12").val(data.phone1);
						$("#phone22").val(data.phone2);
						$("#fax2").val(data.fax);
					}
				);
			$.post(
					"${ctx}/branchCenter/getOffice.action",
					"code="+selectId,
					function(data){
						if(data!=null&&data.length>0){
		   					for(var i=0;i<data.length;i++){
		   						if(data[i].allregionname!=null){
		   							var result='<option>'+data[i].allregionname+"</option>";
		  		 					$("#area2").append(result);
		   						}
		   					}
		   				}else{
		   					var result='<option>无</option>';
			 					$("#area2").append(result);
		   				}
		   				console.log($('#area2 option').size())
		   				if($('#area2 option').size()<8){
							$('#area2').prop('size',$('#area2 option').size())
			   			}else{
			   				$('#area2').prop('size',8)
					   	}
					}
				);
			}
		}	
// 显示列表
    function branchCenMag(){
    	$.ajaxSetup({async : false});
		$.post(
			"${ctx}/branchCenter/findAll.action",
			function(data){
				if(data!=null){
					for(var i=0;i<data.length;i++){
// 						if(data[i].divisioncode!="0000"){
							var result="<tr class=''><td class=''>"
                            +(i+1)+"</td><td class='' style='display:none'>"+data[i].divisioncode+"</td><td class=''>"
 							+data[i].divisionname+"</td><td class=''>"
 							+data[i].divisioncode+"</td><td class=''>"
 							+data[i].divisionaddress+"</td><td class=''>"
 							+data[i].divisionpostcode+"</td><td class=''>"
 							+data[i].divisionlinkman+"</td><td class=''>"
 							+data[i].divisionphone1+"</td><td class=''>"
 							+data[i].divisionphone2+"</td><td class=''>"
 							+data[i].divisionfax+"</td></tr>";
						$("#result").append(result);
// 						}
							
					} 
				}
		});
    }
	
	function audit(id){
		var param = "id="+id;
		$.post(
				"${ctx}/sysUser/audit.action",
				param,
				function(data){
					if(data==1){
						wzj.alert('温馨提示',"审核通过！");
						setTimeout(function(){
							window.location.reload();
							},2000);          
					}else{
						wzj.alert('温馨提示',"审核失败！");
					}
				}
			);
		
		}
	function exportRecord(){
		window.open('${ctx}/branchCenter/exportRecord.action');	
	}

// 	更新
	function updateBranch(branchcode){
    	if($('#editInfo').validationEngine('validate')){
		var code=$("#code1").val();
    	var name=$("#name1").val();
		var address=$("#address1").val();
		var postcode=$("#postcode1").val();
		var linkman=$("#linkman1").val();
		var phone1=$("#phone11").val();
		var phone2=$("#phone21").val();
		var fax=$("#fax1").val();
		var regionCode=$("#area1 option:selected").val();
		var param = "name="+name+"&address="+address
			+"&postcode="+postcode+"&linkman="+linkman+"&phone1="+phone1+"&phone2="+phone2+"&fax="+fax+"&regionCode="+regionCode+"&code="+code+"&branchcode="+branchcode;
		$.post(
             	"${ctx}/branchCenter/countBranch.action",
             	"name="+name,
             	function(data){//返回值是添加用的ID
             		if(data>1){
             			wzj.alert('温馨提示','分支机构名称冲突！');
             		}else if(data==1){
             			if(name==$("#oldName").val()){
             				$.post(
                 					"${ctx}/branchCenter/updateBranch.action",
                 					param,
                 					function(data){//刷新页面
                 						if(data==0){
                 	             			wzj.alert('温馨提示','修改失败！');
                 	             		}else{
                 	             			location.reload();
                 	             		}
                 					}
                 				);
                 			  $('#branchcode').attr("data-dismiss","modal");
             			}else{
             				wzj.alert('温馨提示','分支机构名称冲突！');
             			}
             		}else{
             			$.post(
             					"${ctx}/branchCenter/updateBranch.action",
             					param,
             					function(data){//刷新页面
             						if(data==0){
             	             			wzj.alert('温馨提示','修改失败！');
             	             		}else{
             	             			location.reload();
             	             		}
             					}
             				);
             			  $('#branchcode').attr("data-dismiss","modal");
             		}
             	});
		
    	}
	}
	
//检查分中心编号是否存在
/*	function checkBranchCode(branchCode){
// 	var branchCode=$("#code").val();
	var oldCode=$("#oldCode").val();
	if(branchCode!=oldCode){
		if(branchCode==null||branchCode==''){
			wzj.alert('温馨提示','请输入分支机构代码！');
		}else{
			$.post(
				"${ctx}/divisionRegion/getDivisionByCode.action",
				"code="+branchCode,
				function(data){
					if(data==null||data=='')
						wzj.alert('温馨提示','分支机构代码可用！');
					else{
						wzj.alert('温馨提示','分支机构已存在，请重新输入！');
					}	
				});
		}
	}
}*/

    function checkBranchCode(field, rules, i, options){
    	  
    	var branchCode=field[0].value;
    	var oldCode=$("#oldCode").val();
    	var flag = 0
    	if(branchCode!=oldCode){
   			$.post(
   				"${ctx}/divisionRegion/getDivisionByCode.action",
   				"code="+branchCode,
   				function(data){
   					if(data!=""){
   	   	   				flag = 1
   	   				}
   			})
   			if(flag == 1){
				return "* 分支机构已存在"
   	   		}
    	}
   	} 

</script>
</head>
<body style="overflow-x: hidden;overflow-y:hidden; ">
					<section class="panel">
					<div class="panel-body">
                     <div class="adv-table editable-table ">
                        <div class="clearfix">
                          <div class="btn-group nopaddingleft">
                              <button class="btn btn-info" data-toggle='modal' href='' onClick='viewBranch()' id="check">
                            	 查看 <i class="fa fa-eye"></i>
                              </button>
                           </div>
                           <shiro:hasRole name="中心管理人员">
                           <div class="btn-group ">
                              <button class="btn btn-success" data-toggle='modal' href='#Modal_show' onClick='emptyInput()' id="add">
                              	添加 <i class="fa fa-plus"></i>
                              </button>
                           </div>
                           </shiro:hasRole>
                           <div class="btn-group ">
                              <button class="btn btn-success" data-toggle='modal' href='' onClick='toUpdateBranch()' id="update">
                            	 修改 <i class="fa fa-edit"></i>
                              </button>
                           </div>
                           <shiro:hasRole name="中心管理人员">
                           <div class="btn-group ">
                              <button id="delete" class="btn btn-danger" >
                            	 删除 <i class="fa fa-minus"></i>
                              </button>
                           </div>
                           </shiro:hasRole>
                         <!--   <div class="btn-group pull-right">
                              <button class="btn btn-primary dropdown-toggle" data-toggle="dropdown">下载<i class="fa fa-angle-down"></i>
                              </button>
                              <ul class="dropdown-menu pull-right">
                                <!--  <li><a href="#">Print</a></li>
                                 <li><a href="#">Save as PDF</a></li> -
                                 <li><a href="javascript:exportRecord();">导出到Excel</a></li>
                              </ul>
                           </div>--> 
                        </div>
                        <div class="space15"></div>
                        <table class="table table-bordered" id="editable-sample">
                           <thead>
                              <tr>
                              	
                              	 <th class=" ths">序号</th>
                              	  <th class=" ths" style="display: none">中心编号</th>
                                 <th class=" ths">分支机构</th>
                                  <th class=" ths">分支机构代码</th>
                                 <th class=" ths">地址</th>
                                 <th class=" ths">邮政编码</th>
                                 <th class=" ths">联系人</th>
                                 <th class=" ths">联系电话(1)</th>
                                 <th class=" ths">联系电话(2)</th>
                                 <th class=" ths">传真</th>
                              </tr>
                           </thead>
                           <tbody id="result">
                              <!-- 循环显示查询出来的结果 -->
                             
                           </tbody>
                        </table>
                     </div>
                     </div>
                     </section>
                            <div id="Modal_show2" class="modal fade" >
          <div class="modal-dialog" style="width:60%;">
            <div class="modal-content" style="width:100%;">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                  &times;
                </button>
                <h4 class="modal-title" id="title">查看分支机构</h4>
              </div>
              <div class="modal-body">
			<div class="row" style="width:100%">
			<label class="col-sm-2 labels">分支机构</label>
                <div class="col-sm-10">
                   <input type="text" id="name2" name="name2" value="" class="form-control" readonly="readonly">
                </div>
              </div>
               <div class="row" style="width:100%">
			<label class="col-sm-2 labels">详细地址</label>
                <div class="col-sm-10">
                   <input type="text" id="address2" name="address2" value="" class="form-control" readonly="readonly">
                </div>
              </div>
             <div class="row" style="width:100%">
              <label class="col-sm-2 labels">邮政编码</label>
              <div class="col-sm-4">
              <input type="text" id="postcode2" name="postcode2" value="" class="form-control" readonly="readonly">
              </div>
               <label class="col-sm-2 labels">联系人</label>
              <div class="col-sm-4">
              <input type="text" id="linkman2" name="linkman2" value="" class="form-control" readonly="readonly">
              </div>
               </div>
             <div class="row" style="width:100%">
              <label class="col-sm-2 labels">电话(1)</label>
              <div class="col-sm-4">
              <input type="text" id="phone12" name="phone12" value="" class="form-control" readonly="readonly">
              </div>
               <label class="col-sm-2 labels">电话(2)</label>
              <div class="col-sm-4">
              <input type="text" id="phone22" name="phone22" value="" class="form-control" readonly="readonly">
              </div>
             </div>
             <div class="row" style="width:100%">
             <label class="col-sm-2 labels">传真</label>
                <div class="col-sm-4">
                   <input type="text" id="fax2" name="fax2" value="" class="form-control" readonly="readonly">
                </div>
              <label class="col-sm-2 labels">分支机构代码</label>
              <div class="col-sm-4">
                 <input type="text" id="code2" name="code2" value="" class="form-control" readonly="readonly">
              </div>
              </div>
<!--                <div class="row" style="width:100%"> -->
<!--              <label class="col-sm-2 labels">办事处</label> -->
<!--                                  <div class="col-sm-10"> -->
<!--                                  	<select id="area2" multiple="multiple"></select> -->
<!--                                  </div> -->
<!--                </div> -->
               <div class="modal-footer" style="margin-top: 5%">
<!--                	<button data-dismiss="modal" class="btn btn-info" type="button">取消</button> -->
	         	<button data-dismiss="modal" class="btn btn-success" type="button">确定</button>
              </div>
	         </div>
              </div>
            </div>
          </div>
            <div id="Modal_show1" class="modal fade" >
          <div class="modal-dialog" style="width:60%;">
            <div class="modal-content" style="width:100%;">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                  &times;
                </button>
                <h4 class="modal-title">修改分支机构</h4>
              </div>
              <div class="modal-body">
              <form id="editInfo">
			<div class="row" style="width:100%">
			<label class="col-sm-2 labels">分支机构<b class="red">*</b></label>
                 <div class="col-sm-10">
                    <input type="text" id="name1" name="name1" value="" class="form-control validate[required,custom[username]]">
                    <input type="hidden" id="oldName" name="oldName" value="">
                 </div>
              </div>
             <div class="row" style="width:100%">
             <label class="col-sm-2 labels">地址</label>
               <div class="col-sm-10">
<!--           <select id="area" onchange="changeAre()"><option>全部</option></select> -->
               <select id="province1" onchange="changePro1()"><option>全部</option></select>
               	<select id="city1" onchange="changeCit1()"></select>
               	<select id="area1" onchange="changeAre1()"></select>
               </div>
               </div>
               <div class="row" style="width:100%">
			<label class="col-sm-2 labels">详细地址<b class="red">*</b></label>
                <div class="col-sm-10">
                   <input type="text" id="address1" name="address1" value="" class="form-control validate[required]">
                </div>
              </div>
             <div class="row" style="width:100%">
              <label class="col-sm-2 labels">邮政编码<b class="red">*</b></label>
              <div class="col-sm-4">
              <input type="text" id="postcode1" name="postcode1" value="" class="form-control validate[required,custom[chinaZip]]">
              </div>
               <label class="col-sm-2 labels">联系人<b class="red">*</b></label>
              <div class="col-sm-4">
              <input type="text" id="linkman1" name="linkman1" value="" class="form-control validate[required]">
              </div>
               </div>
             <div class="row" style="width:100%">
              <label class="col-sm-2 labels">电话(1)<b class="red">*</b></label>
              <div class="col-sm-4">
              <input type="text" id="phone11" name="phone11" value="" class="form-control validate[required,custom[telePhone]]" >
              </div>
               <label class="col-sm-2 labels">电话(2)</label>
              <div class="col-sm-4">
              <input type="text" id="phone21" name="phone21" value="" class="form-control validate[custom[telePhone]]">
              </div>
             </div>
             <div class="row" style="width:100%">
             <label class="col-sm-2 labels">传真</label>
                <div class="col-sm-4">
                   <input type="text" id="fax1" name="fax1" value="" class="form-control validate[custom[fax]]">
                </div>
              <label class="col-sm-2 labels">分支机构代码<b class="red">*</b></label>
              <div class="col-sm-4">
                 <input type="text" id="code1" name="code1" value="" class="form-control validate[required,custom[number],funcCall[checkBranchCode]]" onblur="checkBranchCode(this.value)">
                 <input type="hidden" id="oldCode" name="oldCode" value="" class="form-control">
              </div>
              </div><br/>
<!--                 <div class="row" style="width:100%"> -->
<!--                 <label class="col-sm-2 labels">办事处</label> -->
<!--                  <div class="col-sm-10"> -->
<!--                                  <select id="province3" onchange="changePro3()"><option>全部</option></select> -->
<!--                                  	<select id="city3" onchange="changeCit3()"></select> -->
<!--                                  	<select id="area4" onchange="changeAre3()"></select> -->
<!--                                  </div> -->
<!--                  </div>   -->
<!--                   <div class="row" style="width:100%">              -->
<!-- 						<label class="col-sm-2 labels"></label> -->
<!-- 						<label class="col-sm-4"> -->
<!-- 						<select id="select1" size="8" multiple style="width: 150px"> -->
<!-- 						</select></label> -->
<!-- 						<input name="DoDel" type="button" value="x" onClick="DoDels()" class="btn btn-danger"> -->
<!-- 				</div> -->
               <div class="modal-footer" style="margin-top: 5%">
	              	<button data-dismiss="modal" class="btn btn-danger" type="button">取消</button>
		         	<button class="btn btn-success" type="button" onclick="updateBranch(this.value)" id="branchcode">确定</button>    
              </div>
              </form>
	         </div>
              </div>
            </div>
          </div>
          <div id="Modal_show" class="modal fade" >
          <div class="modal-dialog" style="width:60%;">
            <div class="modal-content" style="width:100%;">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                  &times;
                </button>
                <h4 class="modal-title" id="title">添加分支机构</h4>
              </div>
              <div class="modal-body">
              <form id="addInfo">
			<div class="row" style="width:100%">
			<label class="col-sm-2 labels">分支机构<b class="red">*</b></label>
                 <div class="col-sm-10">
                    <input type="text" id="name" name="name" value="" class="form-control validate[required,custom[username]]">
                 </div>
              </div>
             <div class="row" style="width:100%">
             <label class="col-sm-2 labels">地址</label>
                    <div class="col-sm-10">
<!--                <select id="area" onchange="changeAre()"><option>全部</option></select> -->
                    <select id="province" onchange="changePro()" name="province"><option>全部</option></select>
                    	<select id="city" onchange="changeCit()" name="city"></select>
                    	<select id="area" onchange="changeAre()" name="area"></select>
                    </div>
               </div>
               <div class="row" style="width:100%">
			<label class="col-sm-2 labels">详细地址<b class="red">*</b></label>
                <div class="col-sm-10">
                   <input type="text" id="address" name="address" value="" class="form-control validate[required]">
                </div>
              </div>
             <div class="row" style="width:100%">
              <label class="col-sm-2 labels">邮政编码<b class="red">*</b></label>
              <div class="col-sm-4">
              <input type="text" id="postcode" name="postcode" value="" class="form-control validate[required,custom[chinaZip]]">
              </div>
               <label class="col-sm-2 labels">联系人<b class="red">*</b></label>
              <div class="col-sm-4">
              <input type="text" id="linkman" name="linkman" value="" class="form-control validate[required]">
              </div>
               </div>
             <div class="row" style="width:100%">
              <label class="col-sm-2 labels">电话(1)<b class="red">*</b></label>
              <div class="col-sm-4">
              <input type="text" id="phone1" name="phone1" value="" class="form-control validate[required,custom[telePhone]]">
              </div>
               <label class="col-sm-2 labels">电话(2)</label>
              <div class="col-sm-4">
              <input type="text" id="phone2" name="phone2" value="" class="form-control validate[custom[telePhone]]">
              </div>
             </div>
             <div class="row" style="width:100%">
             <label class="col-sm-2 labels">传真</label>
             <div class="col-sm-4">
                <input type="text" id="fax" name="fax" value="" class="form-control validate[custom[fax]]">
             </div>
              <label class="col-sm-2 labels">分支机构代码<b class="red">*</b></label>
              <div class="col-sm-4">
                 <input type="text" id="code" name="code" value="" class="form-control validate[required,custom[number],funcCall[checkBranchCode]]">
              </div>
              </div>
<!--                 <div class="row" style="width:100%"> -->
<!--                 <label class="col-sm-2 labels">办事处</label> -->
<!--                  <div class="col-sm-10"> -->
<!--                                  <select id="province2" onchange="changePro2()"><option>全部</option></select> -->
<!--                                  	<select id="city2" onchange="changeCit2()"></select> -->
<!--                                  	<select id="area3" onchange="changeAre2()"></select> -->
<!--                                  </div> -->
<!--                  </div>   -->
<!--                   <div class="row" style="width:100%">   -->
<!--                   <label class="col-sm-2 labels"></label>            -->
<!-- 						<label class="col-sm-4">  -->
<!-- 						<select id="select2" size="8" multiple style="width: 150px"> -->
<!-- 						</select>		 -->
<!-- 						</label> -->
<!-- 						<input name="DoDel" type="button" value="x" onClick="DoDels()" class="btn btn-danger"> -->
<!-- 				</div> -->
               <div class="modal-footer" style="margin-top: 5%">
               	<button data-dismiss="modal" class="btn btn-danger" type="button">取消</button>
              	<button id="uid" class="btn btn-success" type="button" onclick="addBranch()">确定</button>
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
	<script src="${ctx}/js/bootstrap.min.js" ></script><!-- BOOTSTRAP JS  -->
	<script src="${ctx}/js/jquery.dcjqaccordion.2.7.js"></script><!-- ACCORDING JS -->
	<script src="${ctx}/js/jquery.scrollTo.min.js" ></script><!-- SCROLLTO JS  -->
	<script src="${ctx}/js/jquery.nicescroll.js" > </script><!-- NICESCROLL JS  -->
	<script src="${ctx}/assets/data-tables/jquery.dataTables.js"></script><!-- DATATABLE JS  -->
	<script src="${ctx}/assets/data-tables/DT_bootstrap.js"></script><!-- DATATABLE JS  -->
	<script src="${ctx}/js/respond.min.js" ></script><!-- RESPOND JS  -->
	<script src="${ctx}/js/common-scripts.js" ></script><!-- BASIC COMMON JS  -->
	<script src="${ctx}/js/editable-table-branch.js" ></script><!-- EDITABLE TABLE JS  -->
     <!-- EDITABLE TABLE FUNCTION  -->
     <script>
        jQuery(document).ready(function() {
            EditableTable.init();
			 $("#addInfo").validationEngine({
	    	        scrollOffset: 98,//必须设置，因为Toolbar position为Fixed
	    	        promptPosition: 'topLeft',
	    	        autoHidePrompt: true,
	    	    });
			 $("#editInfo").validationEngine({
	    	        scrollOffset: 98,//必须设置，因为Toolbar position为Fixed
	    	        promptPosition: 'topLeft',
	    	        autoHidePrompt: true,
	    	    });
        });
        $.validationEngineLanguage.allRules.telePhone = {  
	    	      "regex": /^1[3|4|5|7|8][0-9]{9}$|^(0[0-9]{2})[-,‑]\d{7,8}$|^(0[0-9]{3}[-,‑](\d{7,8}))$|^\d{8}$|^\d{7}$/,  
	    	      "alertText": "* 请输入正确的电话号码"  
	   	}; 
	   	
        $(function(){
            $('.ths').css('width','')
        })
//        添加前清空记录
       function emptyInput(){
		$("#code").val("");
       	$("#name").val("");
   		$("#address").val("");
   		$("#postcode").val("");
   		$("#linkman").val("");
   		$("#phone1").val("");
   		$("#phone2").val("");
   		$("#fax").val("");
//    		$("#select2").empty();
   		var obj1 = document.getElementById("province");    
   		obj1.selectedIndex = 0; 
   		var obj2 = document.getElementById("city");    
   		obj2.selectedIndex = -1; 
   		var obj3 = document.getElementById("area");    
   		obj3.selectedIndex = -1; 
//    		var obj4 = document.getElementById("province2");    
//    		obj4.selectedIndex = 0; 
//    		var obj5 = document.getElementById("city2");    
//    		obj5.selectedIndex = -1; 
//    		var obj6 = document.getElementById("area3");    
//    		obj6.selectedIndex = -1; 
       }
//        添加显示
       function getProvince(){
   		$.post(
   			"${pageContext.request.contextPath}/divisionRegion/getRegionChildren.action",
   			"regioncode=000000000000",
   			function(data){
   				if(data!=null&&data.length>0){
   					for(var i=0;i<data.length;i++){
   						var result='<option id="'+ data[i].regioncode +'" value="'+ data[i].regioncode +'">'+data[i].regionname+"</option>";
   						$("#province").append(result);
   						$("#province1").append(result);
   						$("#province2").append(result);
   						$("#province3").append(result);
   					}
   				}
   			}
   		);
   	}
       
       function changePro(){
    	   var pro = $("#province option:selected").attr("id");
   		//市
	   		$.post(
	       		"${pageContext.request.contextPath}/divisionRegion/getRegionChildren.action",
	       		"regioncode="+pro,
	       		function(data){
	       			$("#city").empty();
	       			$("#city").append("<option> 请选择市 </option>");
	       			if(data!=null&&data.length>0){
	       				for(var i=0;i<data.length;i++){
	       					var result='<option id="'+ data[i].regioncode +'" value="'+ data[i].regioncode +'">'+data[i].regionname+"</option>";
	       					$("#city").append(result);
	       				}
	       			}
	       		} 
	       	);
	   		$("#address").empty();
	   		var proname = $("#province option:selected").html();
	   		$("#address").val(proname);
       }
       function changeCit(){
    	   var city = $("#city option:selected").attr("id");
   		//区
   		$.post(
       		"${pageContext.request.contextPath}/divisionRegion/getRegionChildren.action",
       		"regioncode="+city,
       		function(data){
       			$("#area").empty();
					$("#area").append("<option> 请选择地区 </option>");
       			if(data!=null&&data.length>0){
       				for(var i=0;i<data.length;i++){
       					var result='<option id="'+ data[i].regioncode +'" value="'+ data[i].regioncode +'">'+data[i].regionname+"</option>";
       					$("#area").append(result);
       				}
       			}
       		}
       	);
   		$("#address").empty();
   		var proname = $("#province option:selected").html();
   		var cityname = $("#city option:selected").html();
   		$("#address").val(proname+cityname);
       }
       
        function changeAre(){
        	$("#address").empty();
    		var proname = $("#province option:selected").html();
    		var cityname = $("#city option:selected").html();
    		var countryname = $("#area option:selected").html();
    		$("#address").val(proname+cityname+countryname);	
        }
//         修改显示
function changePro1(){
    	   var pro = $("#province1 option:selected").attr("id");
   		//市
   		$.post(
       		"${pageContext.request.contextPath}/divisionRegion/getRegionChildren.action",
       		"regioncode="+pro,
       		function(data){
       			$("#city1").empty();
       			$("#city1").append("<option> 请选择市 </option>");
       			if(data!=null&&data.length>0){
       				for(var i=0;i<data.length;i++){
       					var result='<option id="'+ data[i].regioncode +'" value="'+ data[i].regioncode +'">'+data[i].regionname+"</option>";
       					$("#city1").append(result);
       				}
       			}
       		}
       	);
   		$("#address1").empty();
   		var proname = $("#province1 option:selected").html();
   		$("#address1").val(proname);
       }
       function changeCit1(){
    	   var city = $("#city1 option:selected").attr("id");
   		//区
   		$.post(
       		"${pageContext.request.contextPath}/divisionRegion/getRegionChildren.action",
       		"regioncode="+city,
       		function(data){
       			$("#area1").empty();
					$("#area1").append("<option> 请选择地区 </option>");
       			if(data!=null&&data.length>0){
       				for(var i=0;i<data.length;i++){
       					var result='<option id="'+ data[i].regioncode +'" value="'+ data[i].regioncode +'">'+data[i].regionname+"</option>";
       					$("#area1").append(result);
       				}
       			}
       		}
       	);
   		$("#address1").empty();
   		var proname = $("#province1 option:selected").html();
   		var cityname = $("#city1 option:selected").html();
   		$("#address1").val(proname+cityname);
       }
       
       
        function changeAre1(){
        	$("#address1").empty();
    		var proname = $("#province1 option:selected").html();
    		var cityname = $("#city1 option:selected").html();
    		var countryname = $("#area1 option:selected").html();
    		$("#address1").val(proname+cityname+countryname);	
        }
        //添加地区
        function changePro2(){
     	   var pro = $("#province2 option:selected").attr("id");
    		//市
    		$.post(
        		"${pageContext.request.contextPath}/divisionRegion/getRegionChildren.action",
        		"regioncode="+pro,
        		function(data){
        			$("#city2").empty();
        			$("#city2").append("<option> 请选择市 </option>");
        			if(data!=null&&data.length>0){
        				for(var i=0;i<data.length;i++){
        					var result='<option id="'+ data[i].regioncode +'" value="'+ data[i].regioncode +'">'+data[i].regionname+"</option>";
        					$("#city2").append(result);
        				}
        			}
        		}
        	);
    		
        }
        function changeCit2(){
     	   var city = $("#city2 option:selected").attr("id");
    		//区
    		$.post(
        		"${pageContext.request.contextPath}/divisionRegion/getRegionChildren.action",
        		"regioncode="+city,
        		function(data){
        			$("#area3").empty();
 					$("#area3").append("<option> 请选择地区 </option>");
        			if(data!=null&&data.length>0){
        				for(var i=0;i<data.length;i++){
        					var result='<option id="'+ data[i].regioncode +'" value="'+ data[i].regioncode +'">'+data[i].regionname+"</option>";
        					$("#area3").append(result);
        				}
        			}
        		}
        	);
        }
        
        
         function changeAre2(){
     		var proname = $("#province2 option:selected").html();
     		var cityname = $("#city2 option:selected").html();
     		var countryname = $("#area3 option:selected").html();
     		var areaid=$("#area3 option:selected").val();
     		var address=proname+cityname+countryname;
     		var result='<option id="'+areaid+'" value="'+areaid+'" name="selectArea">'+address+"</option>";
				$("#select2").append(result);
         }
         //修改地区
         function changePro3(){
      	   var pro = $("#province3 option:selected").attr("id");
     		//市
     		$.post(
         		"${pageContext.request.contextPath}/divisionRegion/getRegionChildren.action",
         		"regioncode="+pro,
         		function(data){
         			$("#city3").empty();
         			$("#city3").append("<option> 请选择市 </option>");
         			if(data!=null&&data.length>0){
         				for(var i=0;i<data.length;i++){
         					var result='<option id="'+ data[i].regioncode +'" value="'+ data[i].regioncode +'">'+data[i].regionname+"</option>";
         					$("#city3").append(result);
         				}
         			}
         		}
         	);
     		
         }
         function changeCit3(){
      	   var city = $("#city3 option:selected").attr("id");
     		//区
     		$.post(
         		"${pageContext.request.contextPath}/divisionRegion/getRegionChildren.action",
         		"regioncode="+city,
         		function(data){
         			$("#area4").empty();
  					$("#area4").append("<option> 请选择地区 </option>");
         			if(data!=null&&data.length>0){
         				for(var i=0;i<data.length;i++){
         					var result='<option id="'+ data[i].regioncode +'" value="'+ data[i].regioncode +'">'+data[i].regionname+"</option>";
         					$("#area4").append(result);
         				}
         			}
         		}
         	);
         }
         
          function changeAre3(){
      		var proname = $("#province3 option:selected").html();
      		var cityname = $("#city3 option:selected").html();
      		var countryname = $("#area4 option:selected").html();
      		var areaid=$("#area4 option:selected").val();
      		var address=proname+cityname+countryname;
      		var result='<option id="'+areaid+'" value="'+areaid+'" name="selectArea">'+address+"</option>";
 				$("#select1").append(result);
          }
//         添加
        function addBranch(){
        	if($('#addInfo').validationEngine('validate')){
        		var code=$("#code").val();
            	var name=$("#name").val();
        		var address=$("#address").val();
        		var postcode=$("#postcode").val();
        		var linkman=$("#linkman").val();
        		var phone1=$("#phone1").val();
        		var phone2=$("#phone2").val();
        		var fax=$("#fax").val();
        		var regionCode=$("#area option:selected").val();
        		var province=$("#province option:selected").val();
        		var city=$("#city option:selected").val();
        		var area=$("#area option:selected").val();
        		$.post(
                     	"${ctx}/branchCenter/countBranch.action",
                     	"name="+name,
                     	function(data){//返回值是添加用的ID
                     		if(data>=1){
                     			wzj.alert('温馨提示','分支机构名称冲突！');
                     		}else{
                     			var param = "name="+name+"&address="+address+"&province="+province+"&city="+city+"&area="+area
                    			+"&postcode="+postcode+"&linkman="+linkman+"&phone1="+phone1+"&phone2="+phone2+"&fax="+fax+"&regionCode="+regionCode+"&code="+code;
                        		    		$.post(//此处不能添加${pageContext.request.contextPath}
                                     	"${ctx}/branchCenter/insert.action",
                                     	param,
                                     	function(data){//返回值是添加用的ID
                                     		if(data==0){
                                     			wzj.alert('温馨提示','添加失败！');
                                     		}else{
                                     			wzj.alert('温馨提示','添加成功！');
                                     			location.reload();
                                     		}
                                     	}
                                     );
                        		  $('#uid').attr("data-dismiss","modal");
                     		}
                     	}
                     );
        	}
        }
  //去除选中项
      function DoDels(){
    	  var selectArea=document.getElementsByName("selectArea");
  			for(var i=0;i<selectArea.length;i++){
  			if(selectArea[i].selected ==true){
  				var del=$(selectArea[i]).attr("id");
  				$("#"+del).remove();
  				$("#"+del).remove();
  			}
        }
      }
      $.validationEngineLanguage.allRules.username = {  
    	      "regex": /^[\w\u4E00-\u9FA5]+$/,
    	      "alertText": "* 请输入正确的格式"  
  	   	};  
     </script>
  <!-- END JS --> 
</body>
</html>