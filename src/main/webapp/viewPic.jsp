<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="myshiro" uri="/customize-tags" %>
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="height:100%">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看</title>
	<!-- 查询页面 -->
	<!-- 查询页面 -->
    <link href="${ctx}/css/bootstrap.min.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="${ctx}/css/bootstrap-reset.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="${ctx}/css/style.css" rel="stylesheet"><!-- THEME BASIC CSS -->
	<script src="${ctx}/js/jquery-1.8.3.min.js"></script><!-- BASIC JS LIABRARY 1.8.3 -->
	<script type="text/javascript" src="${ctx}/js/jquery.media.js"></script>
	<%
     String code = request.getParameter("code") ;
	 String flag = request.getParameter("flag") ;
	 String titleNo=request.getParameter("titleNo") ;
	 String type=request.getParameter("type") ;
	 String path=request.getParameter("path") ;
	%>
	<script type="text/javascript">
		$(function() {
			var code = '<%=code%>';
			var flag = '<%=flag%>';
			var titleNo = '<%=titleNo%>';
			var type = '<%=type%>';
			var path='<%=path%>';
			if(flag==0){
				$('#media').attr('href',code); 
				$("#subBtn").trigger("click"); 
				var type = code.split(".").pop()
				var types=["pdf","txt","jpg","jpeg","png","bmp","gif","svg","psd","pcd","raw","tiff"];
				if(types.indexOf(type)==-1){
					$("#subBtn").remove();
					$('#notFound').html('<h3 id="notFound" class="text-center">文件已下载到本地！</h3>')
				}
			}else if(flag==1){//查看质量手册、营业执照、资格认证
				$.post(
					"${ctx}/common/getPath.action",
					"titleNo="+titleNo+"&describe="+type,
					function(data){
						if(data.url=="未找到该文件!"){
							$("#notFound").html('<h3 class="text-center">未找到文件！</h3>')
						}else{
							console.log(data)
							$.ajax({
							   url: "${ctx}/"+data.url,
							   type: 'GET',
							   complete: function(response){
									if(response.status == 404){
									  	$('#notFound').html('<h3 id="notFound" class="text-center">未找到文件！</h3>')
									}else{
										$('#media').attr('href','${ctx}/'+data.url);
										 $("#subBtn").trigger("click");
											var type = code.split(".").pop()
											var types=["pdf","txt","jpg","jpeg","png","bmp","gif","svg","psd","pcd","raw","tiff"];
											if(types.indexOf(type)==-1){
												$("#subBtn").remove();
												$('#notFound').html('<h3 id="notFound" class="text-center">文件已下载到本地！</h3>')
											}
									}
							   }
							 });
						    
						}
						
				});
			}else if(flag==2){
				$.ajax({
					   url: "${ctx}/"+code,
					   type: 'GET',
					   complete: function(response){
						   console.log(response)
							if(response.status == 404){
							  	$('#notFound').html('<h3 id="notFound" class="text-center">未找到文件！</h3>')
							}else{
								$('#media').attr('href',code); 
								$("#subBtn").trigger("click");
								var type = code.split(".").pop()
								var types=["pdf","txt","jpg","jpeg","png","bmp","gif","svg","psd","pcd","raw","tiff"];
								if(types.indexOf(type)==-1){
									$("#subBtn").remove();
									$('#notFound').html('<h3 id="notFound" class="text-center">文件已下载到本地！</h3>')
								}
							}
					   }
					 });
			}else if(flag==3){
				$.post(
						"${ctx}/misdeeds/getMisdeedById.action",
						"misdeedId="+code,
						function(data){
							if(data.enclosure=="未找到该文件!"){
								$("#notFound").html('<h3 class="text-center">未找到文件！</h3>')
							}else{
								console.log(data)
								$.ajax({
								   url: "${ctx}/"+data.enclosure,
								   type: 'GET',
								   complete: function(response){
										if(response.status == 404){
										  	$('#notFound').html('<h3 id="notFound" class="text-center">未找到文件！</h3>')
										}else{
											$('#media').attr('href','${ctx}/'+data.enclosure);
											 $("#subBtn").trigger("click");
												var type = code.split(".").pop()
												var types=["pdf","txt","jpg","jpeg","png","bmp","gif","svg","psd","pcd","raw","tiff"];
												if(types.indexOf(type)==-1){
													$("#subBtn").remove();
													$('#notFound').html('<h3 id="notFound" class="text-center">文件已下载到本地！</h3>')
												}
										}
								   }
								 });
							    
							}
							
					});
			}else if(flag==4){//查看质量记录附件
				$.ajax({
					   url: "${ctx}/"+code,
					   type: 'GET',
					   complete: function(response){
							if(response.status == 404){
							  	$('#notFound').html('<h3 id="notFound" class="text-center">未找到文件！</h3>')
							}else{
								$('#media').attr('href','${ctx}/'+code);
								 $("#subBtn").trigger("click");
									var type = code.split(".").pop()
									var types=["pdf","txt","jpg","jpeg","png","bmp","gif","svg","psd","pcd","raw","tiff"];
									if(types.indexOf(type)==-1){
										$("#subBtn").remove();
										$('#notFound').html('<h3 id="notFound" class="text-center">文件已下载到本地！</h3>')
									}
							}
					   }
					 });
			}else if(flag==5){
				$.ajax({
					   url: "${ctx}/"+path,
					   type: 'GET',
					   complete: function(response){
							if(response.status == 404){
							  	$('#notFound').html('<h3 id="notFound" class="text-center">未找到文件！</h3>')
							}else{
								$('#media').attr('href','${ctx}/'+path);
								 $("#subBtn").trigger("click");
									var type = code.split(".").pop()
									var types=["pdf","txt","jpg","jpeg","png","bmp","gif","svg","psd","pcd","raw","tiff"];
									if(types.indexOf(type)==-1){
										$("#subBtn").remove();
										$('#notFound').html('<h3 id="notFound" class="text-center">文件已下载到本地！</h3>')
									}
							}
					   }
					 });
			}
		});
	</script>
</head>
<body style="height:100%" id="notFound">
	<div class="row" style="text-align: center;">
		<a id="media" href="" ><button class="new-btn-login" id="subBtn" type="button"></button></a>
	</div>
	<script type="text/javascript">


	</script>
</body>
</html>