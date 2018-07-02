<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
		<title>
			三级地区测试
		</title>
		<!-- BEGIN STYLESHEET-->
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/bootstrap-reset.css" rel="stylesheet">
	<link href="assets/font-awesome/css/font-awesome.css" rel="stylesheet">
	<link href="css/style.css" rel="stylesheet">
	<link href="css/style-responsive.css" rel="stylesheet">
	<script type="text/javascript" src="js/jquery.js"></script>
	<script src="js/bootstrap.min.js" ></script>
	<script src="assets/bootstrap-datepicker/js/bootstrap-datepicker.js"></script><!-- 日期显示 -->
	<script src="assets/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script>
	<script src="assets/bootstrap-timepicker/js/bootstrap-timepicker.js"></script>
	<script src="assets/bootstrap-colorpicker/js/bootstrap-colorpicker.js"> </script>
	<script src="assets/jquery-multi-select/js/jquery.multi-select.js"></script>
	<script src="assets/fuelux/js/spinner.min.js"></script>
	<script src="assets/bootstrap-wysihtml5/bootstrap-wysihtml5.js"></script>
	<script src="js/style_self.js"></script>
	
	<link rel="stylesheet" type="text/css" href="css/style_self.css">
	<link rel="stylesheet" type="text/css" href="assets/bootstrap-datepicker/css/datepicker.css"><!-- BOOTSTRAP DATEPICKER PLUGIN CSS -->
	<link rel="stylesheet" type="text/css" href="assets/bootstrap-timepicker/compiled/timepicker.css"><!-- BOOTSTRAP TIMEPICKER PLUGIN CSS -->
	<link rel="stylesheet" type="text/css" href="assets/bootstrap-colorpicker/css/colorpicker.css"><!-- BOOTSTRAP COLORPICKER PLUGIN CSS -->
	<link rel="stylesheet" type="text/css" href="assets/bootstrap-daterangepicker/daterangepicker-bs3.css"><!-- DATERANGE PLUGIN CSS -->
	<link rel="stylesheet" type="text/css" href="assets/bootstrap-datetimepicker/css/datetimepicker.css"><!-- DATETIMEPICKER PLUGIN CSS -->
	<link rel="stylesheet" type="text/css" href="assets/jquery-multi-select/css/multi-select.css"><!-- JQUERY MULTI SELECT PLUGIN CSS -->
    <script src="js/advanced-form-components.js" ></script><!--  ADVANCE FORM COMPONENTS PAGE JS  -->
    <!-- END JS -->
    
    <script type="text/javascript">
    
    	jQuery(document).ready(function() {
    		getProvince();
    	});
    	
    	//获取省信息
    	function getProvince(){
    		$.post(
    			"${pageContext.request.contextPath}/divisionRegion/getRegionChildren.action",
    			"regioncode=000000000000",
    			function(data){
    				if(data!=null&&data.length>0){
    					for(var i=0;i<data.length;i++){
    						var result='<option id="'+ data[i].regioncode +'">'+data[i].regionname+"</option>";
    						$("#region-1").append(result);
    					}
    				}
    			}
    		);
    	}
		
    	//选择省后获取市信息
    	function changeProvince(){
    		var pro = $("#region-1 option:selected").attr("id");
    		//市
    		$.post(
        		"${pageContext.request.contextPath}/divisionRegion/getRegionChildren.action",
        		"regioncode="+pro,
        		function(data){
        			$("#region-2-opt").empty();
        			$("#region-2-opt").append("<option> 请选择市 </option>");
        			if(data!=null&&data.length>0){
        				for(var i=0;i<data.length;i++){
        					var result='<option id="'+ data[i].regioncode +'">'+data[i].regionname+"</option>";
        					$("#region-2").append(result);
        				}
        			}
        		}
        	);
    		$("#p-test").empty();
    		var proname = $("#region-1 option:selected").val();
    		$("#p-test").append(proname);
    	}
		
    	//选择市后获取区信息
    	function changeCity(){
    		var city = $("#region-2 option:selected").attr("id");
    		//区
    		$.post(
        		"${pageContext.request.contextPath}/divisionRegion/getRegionChildren.action",
        		"regioncode="+city,
        		function(data){
        			$("#region-3-opt").empty();
					$("#region-3-opt").append("<option> 请选择地区 </option>");
        			if(data!=null&&data.length>0){
        				for(var i=0;i<data.length;i++){
        					var result='<option id="'+ data[i].regioncode +'">'+data[i].regionname+"</option>";
        					$("#region-3").append(result);
        				}
        			}
        		}
        	);
    		$("#p-test").empty();
    		var proname = $("#region-1 option:selected").val();
    		var cityname = $("#region-2 option:selected").val();
    		$("#p-test").append(proname+'/'+cityname);
    	}
    	
    	//选择区后获取分支机构
    	function changeCountry(){

    		
    		$("#p-test").empty();
    		var proname = $("#region-1 option:selected").val();
    		var cityname = $("#region-2 option:selected").val();
    		var countryname = $("#region-3 option:selected").val();
    		$("#p-test").append(proname+'/'+cityname+'/'+countryname);	
    		
    		//分支机构
    		$("#division").empty();
    		var country = $("#region-3 option:selected").attr("id");
    		$.post(
            		"${pageContext.request.contextPath}/divisionRegion/getDivisionByRegion.action",
            		"regioncode="+country,
            		function(data){
            			if(data!=null&&data.length>0){
            				for(var i=0;i<data.length;i++){
            					var code=data[i].divisioncode;
            					var name=data[i].divisionname;
            					$("#division").append(code+'&nbsp'+name+'<br>');
            				}
            			}
            		}
            );
    		
    		$("#division-table").empty();
    		$.post(
            		"${pageContext.request.contextPath}/divisionRegion/getDivisionByRegion.action",
            		"regioncode="+country,
            		function(data){
            			if(data!=null&&data.length>0){
            				for(var i=0;i<data.length;i++){
            						var code=data[i].divisioncode;
            						var name=data[i].divisionname;
            						$("#division-table").append('<tr>'+'<th>'+code+'</th>'+'<th>'+name+'</th>'+'</tr>');
            				}
            			}
            		}
            );
    	}
    </script>
	</head>
	
	
	<body class="region-test">
		<div class="container">
			<form class="form-signin">
			<h2 class="form-signin-heading">
				地区三级级联下拉菜单
			</h2>
			<div class="login-wrap">
				
				<!-- 地区三级级联下拉菜单 -->
				<select id="region-1" class="form-control" onchange="changeProvince()"
 		  			style="height:37px;font-size:12px;background-color:#eee;margin-bottom:15px;">
          			<option selected="true" disabled="true"> 请选择省份 </option>
          		</select>
          		<select id="region-2" class="form-control" onchange="changeCity()"
 		  			style="height:37px;font-size:12px;background-color:#eee;margin-bottom:15px;">
          			<option selected="true" disabled="true"> 请选择市 </option>
          			<div id="region-2-opt"></div>
          		</select>
          		<select id="region-3" class="form-control" onchange="changeCountry()"
 		  			style="height:37px;font-size:12px;background-color:#eee;margin-bottom:15px;">
          			<option selected="true" disabled="true"> 请选择地区 </option>
          			<div id="region-3-opt"></div>
          		</select>

          		<!-- 所选地区 -->	
          		<h4 style="height:20px;margin-bottom:15px;">所选地区</h4>
				<p id="p-test" style="font-size:12px;margin-bottom:15px;"></p>
				
				<!-- 分支机构/简易-->
				<h4 style="height:20px;margin-bottom:15px;">分支机构/简易</h4>
				<p id="division" style="font-size:12px;margin-bottom:15px;"></p>
				
				<!-- 分支机构/表格 -->
				<h4 style="height:20px;margin-bottom:15px;">分支机构/表格</h4>
				<table id="division-table" class="table table-bordered"></table>
			</div>
			</form>
		</div>
	</body>
</html>