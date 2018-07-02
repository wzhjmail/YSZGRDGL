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
	
<script src="${ctx}/js/jquery-1.8.3.min.js"></script><!-- BASIC JS LIABRARY 1.8.3 -->
<script src="${ctx}/js/style_self.js"></script><!-- 右键特效的js -->
<style type="text/css">
	.labels{
		text-align:right;
		padding-top: 8px
	}
</style>
</head>
<body>
               <section class="panel">
           <section class="wrapper">
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
                        <div class="panel-body">
                           <form class="form-horizontal tasi-form" method="post" id="revForm" action="${ctx}/review/insert.action"  onSubmit="return checkEmpty()">
						    <div class="form-group">
                                 <label class="col-sm-4 ">评审项目与要求</label>
                                <label class="col-sm-3">评审方法</label>
								<label class="col-sm-3">评审记录</label>
								<label class="col-sm-2">单项评审结果</label>
                              </div>
                              <div class="form-group">
                                 <label class="col-sm-4 control-label">1.质量方针和目标<br>企业应制定总体质量方针和质量目标，并明确各工序各环节应达到的质量要求。</label>
                                 <div class="col-sm-3">
                                   有质量方针和目标并贯彻实施记“A”，否则记“C”，有缺陷记“B”。
                                 </div>
								 <div class="col-sm-3">
                                   <textarea class="form-control"  id="PSJL1" name="PSJL1">${item.PSJL1}</textarea>
                                 </div>
                                 <label class="col-sm-2">
									<input type="radio" name="PSJG1" id="or11" value="A" <c:if test="${item.PSJG1=='A'}">checked="checked"</c:if>>A<br>
									<input type="radio" name="PSJG1" id="or12" value="B" <c:if test="${item.PSJG1=='B'}">checked="checked"</c:if>>B<br>
									<input type="radio" name="PSJG1" id="or13" value="C" <c:if test="${item.PSJG1=='C'}">checked="checked"</c:if>>C
                                  </label>
                                 </div>
                                 <div class="form-group">
                                 <label class="col-sm-4 control-label">2.组织机构<br>
                                 企业应根据条码生产流程，明确主管领导、业务、技术、生产、检验、仓储等部门各自的职责和相互关系，使条码印刷各环节衔接配套，有工作流程和组织机构框架图，确保条码印刷品质量符合国家标准要求，并遵守国家有关条码管理的各项规定。</label>
                                 <div class="col-sm-3">机构设置合理，人员职责明确记"A"，反之记"C"，有缺陷记"B"。</div>
								 <div class="col-sm-3">
                                   <textarea class="form-control"  id="PSJL2" name="PSJL2">${item.PSJL2}</textarea>
                                 </div>
                                 <label class="col-sm-2">
									<input type="radio" name="PSJG2" id="or21" value="A" <c:if test="${item.PSJG2=='A'}">checked="checked"</c:if>>A<br>
									<input type="radio" name="PSJG2" id="or22" value="B" <c:if test="${item.PSJG2=='B'}">checked="checked"</c:if>>B<br>
									<input type="radio" name="PSJG2" id="or23" value="C" <c:if test="${item.PSJG2=='C'}">checked="checked"</c:if>>C
                                  </label>
                                 </div>
                                 <div class="form-group">
                                 <label class="col-sm-4 control-label">3.人员素质<br>3.1主管领导<br>企业内部应明确负责条码印刷管理工作的企业领导，该领导应了解条码知识、质量要求和国家有关条码管理的各项规定。</label>
                                 <div class="col-sm-3">符合要求记"A"，否则记"C"，部分符合记"B"。</div>
								 <div class="col-sm-3">
                                   <textarea class="form-control"  id="PSJL31" name="PSJL31">${item.PSJL31}</textarea>
                                 </div>
                                 <label class="col-sm-2">
									<input type="radio" name="PSJG31" id="or31" value="A" <c:if test="${item.PSJG31=='A'}">checked="checked"</c:if>>A<br>
									<input type="radio" name="PSJG31" id="or32" value="B" <c:if test="${item.PSJG31=='B'}">checked="checked"</c:if>>B<br>
									<input type="radio" name="PSJG31" id="or33" value="C" <c:if test="${item.PSJG31=='C'}">checked="checked"</c:if>>C
                                  </label>
                                 </div>
                                 <div class="form-group">
                                 <label class="col-sm-4 control-label">3.2技术负责人*<br>条码技术负责人应熟悉条码国家标准，掌握条码印刷技术，能解决条码印刷过程中出现的质量问题。</label>
                                 <div class="col-sm-3">符合要求记"A"，否则记"C"，部分符合记"B"。</div>
								 <div class="col-sm-3">
                                   <textarea class="form-control"  id="PSJL32" name="PSJL32">${item.PSJL32}</textarea>
                                 </div>
                                 <label class="col-sm-2">
									<input type="radio" name="PSJG32" id="or41" value="A" <c:if test="${item.PSJG32=='A'}">checked="checked"</c:if>>A<br>
									<input type="radio" name="PSJG32" id="or42" value="B" <c:if test="${item.PSJG32=='B'}">checked="checked"</c:if>>B<br>
									<input type="radio" name="PSJG32" id="or43" value="C" <c:if test="${item.PSJG32=='C'}">checked="checked"</c:if>>C
                                  </label>
                                 </div>
                                 <div class="form-group">
                                 <label class="col-sm-4 control-label">
                                 3.3业务人员<br>承接印刷业务的人员应了解条码基本知识和国家有关条码管理的规定。
                                 </label>
                                 <div class="col-sm-3">符合要求记"A"，否则记"C"，部分符合记"B"。</div>
								 <div class="col-sm-3">
                                   <textarea class="form-control"  id="PSJL33" name="PSJL33">${item.PSJL33}</textarea>
                                 </div>
                                 <label class="col-sm-2">
									<input type="radio" name="PSJG33" id="or51" value="A" <c:if test="${item.PSJG33=='A'}">checked="checked"</c:if>>A<br>
									<input type="radio" name="PSJG33" id="or52" value="B" <c:if test="${item.PSJG33=='B'}">checked="checked"</c:if>>B<br>
									<input type="radio" name="PSJG33" id="or53" value="C" <c:if test="${item.PSJG33=='C'}">checked="checked"</c:if>>C
                                  </label>                                                
                                 </div>
                                 <div class="form-group">
                                 <label class="col-sm-4 control-label">
                                 3.4设计审查人员<br>熟悉条码国家标准，掌握条码位置、尺寸、颜色等设计要求。
                                 </label>
                                 <div class="col-sm-3">符合要求记"A"，否则记"C"，部分符合记"B"。</div>
								 <div class="col-sm-3">
                                   <textarea class="form-control"  id="PSJL34" name="PSJL34">${item.PSJL34}</textarea>
                                 </div>
                                 <label class="col-sm-2">
									<input type="radio" name="PSJG34" id="or61" value="A" <c:if test="${item.PSJG34=='A'}">checked="checked"</c:if>>A<br>
									<input type="radio" name="PSJG34" id="or62" value="B" <c:if test="${item.PSJG34=='B'}">checked="checked"</c:if>>B<br>
									<input type="radio" name="PSJG34" id="or63" value="C" <c:if test="${item.PSJG34=='C'}">checked="checked"</c:if>>C
                                  </label>
                                 </div>
                                 <div class="form-group">
                                 <label class="col-sm-4 control-label">
                                 3.5现场操作人员<br>应了解条码的基本质量要求，并具备一定的现场质量控制能力。
                                 </label>
                                 <div class="col-sm-3">符合要求记"A"，否则记"C"，部分符合记"B"。</div>
								 <div class="col-sm-3">
                                   <textarea class="form-control"  id="PSJL35" name="PSJL35">${item.PSJL35}</textarea>
                                 </div>
                                 <label class="col-sm-2">
									<input type="radio" name="PSJG35" id="or71" value="A" <c:if test="${item.PSJG35=='A'}">checked="checked"</c:if>>A<br>
									<input type="radio" name="PSJG35" id="or72" value="B" <c:if test="${item.PSJG35=='B'}">checked="checked"</c:if>>B<br>
									<input type="radio" name="PSJG35" id="or73" value="C" <c:if test="${item.PSJG35=='C'}">checked="checked"</c:if>>C
                                  </label>
                                 </div>
                                 <div class="form-group">
                                 <label class="col-sm-4 control-label">
                                 3.6检验人员*<br>企业应配备检验人员负责条码印刷品检验检验人员应熟悉条码国家标准，掌握条码量检验技术。
                                 </label>
                                 <div class="col-sm-3">符合要求记"A"，否则记"C"，部分符合记"B"。</div>
								 <div class="col-sm-3">
                                   <textarea class="form-control"  id="PSJL36" name="PSJL36">${item.PSJL36}</textarea>
                                 </div>
                                 <label class="col-sm-2">
									<input type="radio" name="PSJG36" id="or81" value="A" <c:if test="${item.PSJG36=='A'}">checked="checked"</c:if>>A<br>
									<input type="radio" name="PSJG36" id="or82" value="B" <c:if test="${item.PSJG36=='B'}">checked="checked"</c:if>>B<br>
									<input type="radio" name="PSJG36" id="or83" value="C" <c:if test="${item.PSJG36=='C'}">checked="checked"</c:if>>C
                                  </label>
                                 </div>
                                 <div class="form-group">
                                 <label class="col-sm-4 control-label">
                                 4.印刷设备*<br>用于印刷条码的主要设备运行正常。
                                 </label>
                                 <div class="col-sm-3">符合记"A"，否则记"C"。</div>
								 <div class="col-sm-3">
                                   <textarea class="form-control"  id="PSJL4" name="PSJL4">${item.PSJL4}</textarea>
                                 </div>
                                 <label class="col-sm-2">
									<input type="radio" name="PSJG4" id="or91" value="A" <c:if test="${item.PSJG4=='A'}">checked="checked"</c:if>>A<br>
									<input type="radio" name="PSJG4" id="or92" value="C" <c:if test="${item.PSJG4=='C'}">checked="checked"</c:if>>C
                                  </label>
                                 </div>
                                 <div class="form-group">
                                 <label class="col-sm-4 control-label">
                                  5.适性实验<br>通过印刷适性试验，能有效控制条宽平均每益或减少，满足条码质量要求。
                                 </label>
                                 <div class="col-sm-3">符合记"A"，有缺陷记"B"，否则记"C"。</div>
								 <div class="col-sm-3">
                                   <textarea class="form-control"  id="PSJL5" name="PSJL5">${item.PSJL5}</textarea>
                                 </div>
                                 <label class="col-sm-2">
									<input type="radio" name="PSJG5" id="or101" value="A" <c:if test="${item.PSJG5=='A'}">checked="checked"</c:if>>A<br>
									<input type="radio" name="PSJG5" id="or102" value="B" <c:if test="${item.PSJG5=='B'}">checked="checked"</c:if>>B<br>
									<input type="radio" name="PSJG5" id="or103" value="C" <c:if test="${item.PSJG5=='C'}">checked="checked"</c:if>>C
                                  </label>
                                 </div>
                                 <div class="form-group">
                                 <label class="col-sm-4 control-label">
                                   6.印刷品抽检*<br>分支机构对企业印刷的条码印刷品并进行检验。
                                 </label>
                                 <div class="col-sm-3">样本检验合格记"A"，否则记"C"。</div>
								 <div class="col-sm-3">
                                   <textarea class="form-control"  id="PSJL6" name="PSJL6">${item.PSJL6}</textarea>
                                 </div>
                                 <label class="col-sm-2">
									<input type="radio" name="PSJG6" id="or111" value="A" <c:if test="${item.PSJG6=='A'}">checked="checked"</c:if>>A<br>
									<input type="radio" name="PSJG6" id="or112" value="B" <c:if test="${item.PSJG6=='B'}">checked="checked"</c:if>>B<br>
									<input type="radio" name="PSJG6" id="or113" value="C" <c:if test="${item.PSJG6=='C'}">checked="checked"</c:if>>C
                                  </label>
                                 </div>
                                 <div class="form-group">
                                 <label class="col-sm-4 control-label">
                                   7.企业内部检验<br>7.1条码检验*<br>拥有必要的检验手段。
                                 </label>
                                 <div class="col-sm-3">符合记"A"，否则记"C"。</div>
								 <div class="col-sm-3">
                                   <textarea class="form-control"  id="PSJL71" name="PSJL71">${item.PSJL71}</textarea>
                                 </div>
                                 <label class="col-sm-2">
									<input type="radio" name="PSJG71" id="or121" value="A" <c:if test="${item.PSJG71=='A'}">checked="checked"</c:if>>A<br>
									<input type="radio" name="PSJG71" id="or122" value="C" <c:if test="${item.PSJG71=='C'}">checked="checked"</c:if>>C
                                  </label>
                                 </div>
                                 <div class="form-group">
                                 <label class="col-sm-4 control-label">
                                   7.2检验工作程序及要求<br>按标准进行抽样和检验检验记录与合格证应规范统一、项目齐全、字迹工整。
                                 </label>
                                 <div class="col-sm-3">符合记"A"，否则记"C"，有缺陷记"B"。</div>
								 <div class="col-sm-3">
                                   <textarea class="form-control"  id="PSJL72" name="PSJL72">${item.PSJL72}</textarea>
                                 </div>
                                 <label class="col-sm-2">
									<input type="radio" name="PSJG72" id="or131" value="A" <c:if test="${item.PSJG72=='A'}">checked="checked"</c:if>>A<br>
									<input type="radio" name="PSJG72" id="or132" value="B" <c:if test="${item.PSJG72=='B'}">checked="checked"</c:if>>B<br>
									<input type="radio" name="PSJG72" id="or133" value="C" <c:if test="${item.PSJG72=='C'}">checked="checked"</c:if>>C
                                  </label>
                                 </div>
                                 <div class="form-group">
                                 <label class="col-sm-4 control-label">
                                   8.规章制度<br>8.1《条码印刷验证制度》<br>
                                   在承接条码印刷业务时，必须向委托单位索取有关证明。核查证明的有效性，并将证明复印件与印刷合同一起存档备查，保留期不得少于二年。
                                 </label>
                                 <div class="col-sm-3">符合记"A"，否则记"C"，有缺陷记"B"。</div>
								 <div class="col-sm-3">
                                   <textarea class="form-control"  id="PSJL81" name="PSJL81">${item.PSJL81}</textarea>
                                 </div>
                                 <label class="col-sm-2">
									<input type="radio" name="PSJG81" id="or141" value="A" <c:if test="${item.PSJG81=='A'}">checked="checked"</c:if>>A<br>
									<input type="radio" name="PSJG81" id="or142" value="B" <c:if test="${item.PSJG81=='B'}">checked="checked"</c:if>>B<br>
									<input type="radio" name="PSJG81" id="or143" value="C" <c:if test="${item.PSJG81=='C'}">checked="checked"</c:if>>C
                                  </label>
                                 </div>
                                 <div class="form-group">
                                 <label class="col-sm-4 control-label">
                                   8.2《条码设计审查制度》<br>印刷条码前要对设计稿件进行审查，做到设计不合格的稿样不投入制版印刷。</label>
                                 <div class="col-sm-3">有制度并严格执行记"A"，无制度记"C"，有制度，执行不严记"B"。</div>
								 <div class="col-sm-3">
                                   <textarea class="form-control"  id="PSJL82" name="PSJL82">${item.PSJL82}</textarea>
                                 </div>
                                 <label class="col-sm-2">
									<input type="radio" name="PSJG82" id="or151" value="A" <c:if test="${item.PSJG82=='A'}">checked="checked"</c:if>>A<br>
									<input type="radio" name="PSJG82" id="or152" value="B" <c:if test="${item.PSJG82=='B'}">checked="checked"</c:if>>B<br>
									<input type="radio" name="PSJG82" id="or153" value="C" <c:if test="${item.PSJG82=='C'}">checked="checked"</c:if>>C
                                  </label>
                                 </div>
                                 <div class="form-group">
                                 <label class="col-sm-4 control-label">
                                   8.3《条码印刷品、印版、胶片管理制度》<br>
                                                                                              明确条码印刷合格品、不合格品、残次品、印版、胶片的出入库登记、保管、移交、监销程序和负责人员。</label>
                                 <div class="col-sm-3">有制度并严格执行记"A"，无制度记"C"，有制度，执行不严记"B"。</div>
								 <div class="col-sm-3">
                                   <textarea class="form-control"  id="PSJL83" name="PSJL83">${item.PSJL83}</textarea>
                                 </div>
                                 <label class="col-sm-2">
									<input type="radio" name="PSJG83" id="or161" value="A" <c:if test="${item.PSJG83=='A'}">checked="checked"</c:if>>A<br>
									<input type="radio" name="PSJG83" id="or162" value="B" <c:if test="${item.PSJG83=='B'}">checked="checked"</c:if>>B<br>
									<input type="radio" name="PSJG83" id="or163" value="C" <c:if test="${item.PSJG83=='C'}">checked="checked"</c:if>>C
                                  </label>
                                 </div>
                                 <div class="form-group">
                                 <label class="col-sm-4 control-label">
                                  8.4《条码印刷品质量检验制度》*<br>
明确检验合格放行程序，做到检验不合格的半成品不投入下道生产工序；明确负责出厂检验人员和合格证签发程序，做到未经检验合格的条码印刷品不出厂。</label>
                                 <div class="col-sm-3">有制度并严格执行记"A"，无制度记"C"，有制度，执行不严记"B"。</div>
								 <div class="col-sm-3">
                                   <textarea class="form-control"  id="PSJL84" name="PSJL84">${item.PSJL84}</textarea>
                                 </div>
                                 <label class="col-sm-2">
									<input type="radio" name="PSJG84" id="or171" value="A" <c:if test="${item.PSJG84=='A'}">checked="checked"</c:if>>A<br>
									<input type="radio" name="PSJG84" id="or172" value="B" <c:if test="${item.PSJG84=='B'}">checked="checked"</c:if>>B<br>
									<input type="radio" name="PSJG84" id="or173" value="C" <c:if test="${item.PSJG84=='C'}">checked="checked"</c:if>>C
                                  </label>
                                 </div>
                                 <div class="form-group">
                                 <label class="col-sm-2">
                               	 注：带*号的项为重点项
                                  </label>
                                 </div>
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
                            <div class="col-sm-2 "></div>
			                <div class="col-sm-8">
								     评审结论分"认定"、"暂不认定"两种。<br>
								   申请企业经评审同时满足下述要求的，评审结论为"认定"；否则，评审结论为"暂不认定"。<br><br>
								<p>1、重点项（含*号标记项）为A的项数不得少于五项，且无C项;</p>
								<p>2、全项出现B项的个数不得超过6项</p>
								<p>3、非重点出现C项个数不得超过二项。当非重点项出现一个C项时，全项中出
								      现B项的数目不得超过4项；当出现二个C项时，全项中出现的数目不得超
								      过2项。</p>
                            </div>
                           <div class="col-sm-2"></div>
			  			</div>
                     </section>
                  </div>
               </div>
                <div class="row">
                  <div class="col-lg-12">
                     <section class="panel">
                        <header class="panel-heading">
                           <span class="label label-primary">评审专家</span>
                           <span class="tools pull-right" style="margin-top:-8px;">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
                            <label class="col-sm-2">姓名</label>
                            <label class="col-sm-4">工作单位</label>
                             <label class="col-sm-3">职务/职称</label>
                             <label class="col-sm-2">是否签名</label>
			  			</div>
			  			<div class="panel-body" style="margin-top:-20px">
                            <span class="col-sm-2">王佑宁</span>
                            <span class="col-sm-4">江苏烟花所</span>
                             <span class="col-sm-3">高级工程师</span>
                             <span class="col-sm-2"><input type="checkbox" name="status" value="1"/></span>
                           						 
			  			</div>
			  			<div class="panel-body" style="margin-top:-20px">
                            <span class="col-sm-2">王佑宁</span>
                            <span class="col-sm-4">江苏烟花所</span>
                             <span class="col-sm-3">高级工程师</span>
                             <span class="col-sm-2"><input type="checkbox" name="status" value="2"/></span>
			  			</div>
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
						   <div class="form-group row">
                             <div class="col-lg-1"></div>
                             <div class="col-lg-10">
                                  <textarea class="form-control" rows="8" id="mavinIdea" name="mavinIdea">${item.mavinIdea}</textarea>
                             </div>  
                           </div>
                           <div class="form-group row">
                             <div class="col-sm-2 labels">评审组长签名：</div>
                             <div class="col-sm-4">
                                <input type="text" id="psman" name="psman" class="form-control" value="${item.psman}">
                             </div> 
                             <div class="col-sm-2 labels">日期：</div>
                             <div class="col-sm-4">
                        		<input class="input-medium default-date-picker form-control" size="16" type="date" id="psdate" name="psdate" value="${item.psdate}">
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
						   <div class="form-group row">
                             <div class="col-lg-1"></div>
                             <div class="col-lg-10">
                                  <textarea class="form-control" rows="4" id="frameworkIdea" name="frameworkIdea">${item.frameworkIdea }</textarea>
                             </div> 
                           </div> 
                           <div class="form-group row">
                             <div class="col-sm-2 labels">负责人签名：</div>
                             <div class="col-sm-4">
                                  <input type="text" id="fzman" name="fzman" class="form-control">
                             </div>
                             <div class="col-sm-2 labels">分支机构盖章：</div>
                             <div class="col-sm-4"></div>
                            </div>
                             <div class="form-group row">
                             <div class="col-sm-2 labels">日期：</div>
                             <div class="col-sm-4">
                        		<input class="input-medium default-date-picker form-control" size="16" type="date" id="fzdate" name="fzdate" value="${item.fzdate}">
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
                           <span class="label label-primary">编码中心意见</span>
                           <span class="tools pull-right" style="margin-top:-8px;">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
						   <div class="form-group row">
                             <div class="col-lg-1"></div>
                             <div class="col-lg-10">
                                  <textarea class="form-control" rows="7" id="centerIdea" name="centerIdea">${item.centerIdea}</textarea>
                             </div> 
                           </div> 
                           <div class="form-group row">
                             <div class="col-sm-2 labels">批准人签名：</div>
                             <div class="col-sm-4">
                                  <input type="text" id="pzman" name="pzman" class="form-control" value="${item.pzman }"><br>
             				</div>
             				<div class="col-sm-2 labels">核准人签名：</div>
                             <div class="col-sm-4">                   
                                  <input type="text" id="hzman" name="hzman" class="form-control" value="${item.hzman }">
                             </div> 
                            </div> 
                            <div class="form-group row">
                             <div class="col-sm-2 labels">编码中心盖章：</div>
                             <div class="col-sm-4"></div>
                             <div class="col-sm-2 labels">日期：</div>
                             <div class="col-sm-4">
                        		<input class="input-medium default-date-picker form-control" size="16" type="date" id="pzdate" name="pzdate" value="${item.pzdate}">
                      		 </div>
                           </div>
			  			</div>
                     </section>
                  </div>
               </div>
               <div class="row">
	               <section class="panel">
	               <div class="panel-body">
	               		<div class="col-lg-5 col-sm-5"></div>
	               		<div class="col-lg-2 col-sm-2"><input type="button" onclick="history.back()" class="btn-info btn-block btn" value="返回"></div>
	               		<div class="col-lg-5 col-sm-5"></div>

	               </div>
	               </section>
	            </div>
               </form> 
		</section>
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
	<script src="${ctx}/js/editable-table.js" ></script><!-- EDITABLE TABLE JS  -->
     <!-- EDITABLE TABLE FUNCTION  -->
     <script>
        jQuery(document).ready(function() {
            EditableTable.init();
        });
     </script>
  <!-- END JS --> 
</body>
</html>