<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="myshiro" uri="/customize-tags" %>
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="height:100%">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>预览</title>
	<!-- 查询页面 -->
	<!-- 查询页面 -->
    <link href="${ctx}/css/bootstrap.min.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="${ctx}/css/bootstrap-reset.css" rel="stylesheet"><!-- B OOTSTRAP CSS -->
    <link href="${ctx}/css/style.css" rel="stylesheet"><!-- THEME BASIC CSS -->
	<script src="${ctx}/js/jquery-1.8.3.min.js"></script><!-- BASIC JS LIABRARY 1.8.3 -->
	<script type="text/javascript" src="${ctx}/js/jquery.media.js"></script>
	<%
    String code = request.getParameter("code") ;
	 String flag = request.getParameter("flag") ;
	 String titleNo=request.getParameter("titleNo") ;
	 String type=request.getParameter("type") ;
	%>
	<script type="text/javascript">
// 	function GetQueryString(name)
// 	{
// 	     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
// 	     var r = window.location.search.substr(1).match(reg);
// 	     if(r!=null)return  unescape(r[2]); return null;
// 	}
		$(function() {  
			var code = '<%=code%>';
			var flag = '<%=flag%>';
			var titleNo = '<%=titleNo%>';
			var type = '<%=type%>';
// 			console.log(GetQueryString("code"))
// 			GetQueryString("code")
			if(flag==0){
				$('#media').attr('src',code); 
			}else if(flag==1){
				$.post(
					"${ctx}/common/getI.action",
					"code="+code,
					function(data){
					    $('.media').prop('href',"${ctx}/"+data.url); 
					    $('a.media').media();  
					    $('.media').css('height','100%');  
				});
			}else if(flag==2){
				$.post(
						'${ctx}/commons/viewFile.action?type='+type+'&titleNo='+titleNo,
						function(data){
							if(data==""){
								$('#media').html('<h3 class="text-center">未找到文件！</h3>')
							}
							console.log(data)
							if(data!=null)
							$.each(data,function(i,item){
								$.ajax({
									   url: "${ctx}/"+item.uprul,
									   type: 'GET',
									   complete: function(response){
											if(response.status == 404){
											  	$('#media').html('<h3 id="notFound" class="text-center">未找到文件！</h3>')
											}
									   }
									 });
								
								$('.media').prop('href',"${ctx}/"+item.uprul); 
							    $('a.media').media();  
							    $('.media').css('height','100%');
							});
						}
					);
			}
		});  

	</script>
</head>
<body style="height:100%" id="media">
	<a class="media" href=""></a>  
	<script type="text/javascript">
	$(function() { 
		 
	});  

	</script>
</body>
</html>