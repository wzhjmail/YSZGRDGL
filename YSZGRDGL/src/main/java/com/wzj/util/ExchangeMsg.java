package com.wzj.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.wzj.controller.application.AppCommonsController;
import com.wzj.controller.recognition.RecognitionController;
import com.wzj.pojo.ApplicationForm;
import com.wzj.pojo.Division;
import com.wzj.pojo.PbtReport;
import com.wzj.pojo.PbtSample;
import com.wzj.pojo.PbtSample2;
import com.wzj.pojo.UploadFile;
import com.wzj.service.application.impl.ApplicationFormService;
import com.wzj.service.application.impl.UploadFileService;
import com.wzj.service.impl.DivisionService;
import com.wzj.service.impl.PbtReportService;
import com.wzj.service.impl.PbtSampleService;
import com.wzj.service.impl.PbtSampleService2;

public class ExchangeMsg implements ServletContextListener{
	static int num=1;
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Timer timer = new Timer();
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY,1);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		Date time=calendar.getTime();//每天的凌晨1点运行
		String path1=System.getProperty("user.dir");
		//使用WebApplicationContextUtils工具类，获取spring容器的引用
		WebApplicationContext context=WebApplicationContextUtils
				.getWebApplicationContext(sce.getServletContext());
		PbtSampleService2 pbtService2=context.getBean(PbtSampleService2.class);
		PbtSampleService pbtService=context.getBean(PbtSampleService.class);
		UploadFileService uploadFileService=context.getBean(UploadFileService.class);
		PbtReportService reportService=context.getBean(PbtReportService.class);
		ApplicationFormService applicationFormService=context.getBean(ApplicationFormService.class);
		DivisionService divisionService=context.getBean(DivisionService.class);
		AppCommonsController application=context.getBean(AppCommonsController.class);
		RecognitionController recognition=context.getBean(RecognitionController.class);
		timer.schedule(new TimerTask(){
			public void run(){
				try {
					if(num==2){
						//获取需要上传的样品信息
						List<ApplicationForm> apps=(List<ApplicationForm>)application.getAllTask("zlsh");
						List<ApplicationForm> recs=(List<ApplicationForm>)recognition.getAllTask("zlsh");
						apps.addAll(recs);
						List<PbtSample2> lists=new LinkedList<>();
						for(ApplicationForm app:apps){
							String cname=app.getEnterprisename();
							PbtSample pbt=pbtService.getSampleByStatus("0", cname);//获取未读的样品
							PbtSample2 sample=new PbtSample2();
							ApplicationForm af=applicationFormService.getAppFormByName(cname);//申请表信息
							sample.setUf_004761(af.getEnterprisename());//企业名称
							sample.setUf_004763(af.getLinkman());//联系人
							sample.setUf_004762(af.getLtel());//电话
							sample.setPostalcode(af.getPostalcode());//邮编
							String branchId=af.getBranchId();
							Division division=divisionService.getDivisionByCode(branchId);//分支机构信息
							sample.setBrachName(division.getDivisionname());//分支机构名称
							sample.setUf_004742(division.getDivisionlinkman());//联系人
							sample.setUf_004744(division.getDivisionaddress());//地址
							sample.setUf_004743(division.getDivisionphone1());//电话
							sample.setPostcode(division.getDivisionpostcode());//邮编
							//样品的基本信息上传
							sample.setUf_004757("0047000475");//检验类别：委托检验
							sample.setUf_004785("条码答号等级及分级法相关参数");//检验项目
							sample.setUf_004784("/");//检验报告总数
							sample.setUf_004791("0047000507");//是否涉及法律纠纷:否
							sample.setUf_0047228(pbt.getUf_0047228());//样品名称
							sample.setUf_0047251(pbt.getUf_0047251());//产品名称
							sample.setUf_0047232(pbt.getUf_0047232());//条码类型
							sample.setUf_0047220(pbt.getUf_0047220());//商标
							sample.setUf_0047218(pbt.getUf_0047218());//规格型号
							sample.setUf_0047231(pbt.getUf_0047231());//印刷载体
							sample.setUf_0047233(pbt.getUf_0047233());//条码号
							sample.setUf_0047332(pbt.getUf_0047332());//样品状态描述
							sample.setF_sample_count(pbt.getF_sample_count());//样品数量
							sample.setUf_004778("0047000481");//送样方式：邮寄
							sample.setUf_004780("0047000482");//保密要求：无
							sample.setUf_004779("0047000484");//样品处置：由检验方处置
							sample.setUf_004798("需要");//报告扫描件：需要
							sample.setUf_004758("0047000487");//报告交付：自取
							
							//附件的上传
							List<UploadFile> ufs=uploadFileService.getSampleAttach(af.getTitleno(),
									pbt.getF_sample_code());
							if(ufs.size()>0){
								UploadFile uf=ufs.get(0);
								String path2=path1+"/src/main/webapp/"+uf.getUprul();//获取文件的路径
								File file=new File(path2);
								FileInputStream fileInputStream=new FileInputStream(file);
								int flength=(int)file.length();
								byte[] fbate=new byte[flength];
								fileInputStream.read(fbate, 0, flength);
								fileInputStream.close();
								sample.setMfile(fbate);
							}
							pbtService.setStatus(pbt.getUf_sample_code(),"1");//修改pbt的状态:设置为已读
						}
						//切换数据库,将需要上传的样品复制到oracle表中
						DynamicDataSource.setCustomerType(DynamicDataSource.DATASOURCETWO);
						for(PbtSample2 sample:lists){//将本地的样品信息上传到oracle数据库中
							pbtService2.insert(sample);
						}
						//下载检测报告文件
						List<PbtReport> reports=reportService.getReports();
						if(!reports.isEmpty()){
						//下载到哪？
							File file=new File("F:/abc.pdf");
							if(!file.exists()){
								file.createNewFile();
							}
							OutputStream outputStream=new FileOutputStream(file);
							InputStream inputStream=new ByteArrayInputStream(reports.get(0).getF_report_fj()); 
							byte[] buffer=new byte[1024];
							int len=0;
							while((len=inputStream.read(buffer))!=-1){
								outputStream.write(buffer,0,len);
							}
							inputStream.close();
							outputStream.close();
							//修改检测报告表中的状态
							reportService.chageStatus(reports.get(0).getF_report_key());
						//这中间的代码是下载到一个文件中需要改
						}
					}else{
						num=2;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, time);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {}

}
