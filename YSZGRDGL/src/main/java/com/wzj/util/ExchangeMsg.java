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
		Date time=calendar.getTime();//ÿ����賿1������
		String path1=System.getProperty("user.dir");
		//ʹ��WebApplicationContextUtils�����࣬��ȡspring����������
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
						//��ȡ��Ҫ�ϴ�����Ʒ��Ϣ
						List<ApplicationForm> apps=(List<ApplicationForm>)application.getAllTask("zlsh");
						List<ApplicationForm> recs=(List<ApplicationForm>)recognition.getAllTask("zlsh");
						apps.addAll(recs);
						List<PbtSample2> lists=new LinkedList<>();
						for(ApplicationForm app:apps){
							String cname=app.getEnterprisename();
							PbtSample pbt=pbtService.getSampleByStatus("0", cname);//��ȡδ������Ʒ
							PbtSample2 sample=new PbtSample2();
							ApplicationForm af=applicationFormService.getAppFormByName(cname);//�������Ϣ
							sample.setUf_004761(af.getEnterprisename());//��ҵ����
							sample.setUf_004763(af.getLinkman());//��ϵ��
							sample.setUf_004762(af.getLtel());//�绰
							sample.setPostalcode(af.getPostalcode());//�ʱ�
							String branchId=af.getBranchId();
							Division division=divisionService.getDivisionByCode(branchId);//��֧������Ϣ
							sample.setBrachName(division.getDivisionname());//��֧��������
							sample.setUf_004742(division.getDivisionlinkman());//��ϵ��
							sample.setUf_004744(division.getDivisionaddress());//��ַ
							sample.setUf_004743(division.getDivisionphone1());//�绰
							sample.setPostcode(division.getDivisionpostcode());//�ʱ�
							//��Ʒ�Ļ�����Ϣ�ϴ�
							sample.setUf_004757("0047000475");//�������ί�м���
							sample.setUf_004785("�����ŵȼ����ּ�����ز���");//������Ŀ
							sample.setUf_004784("/");//���鱨������
							sample.setUf_004791("0047000507");//�Ƿ��漰���ɾ���:��
							sample.setUf_0047228(pbt.getUf_0047228());//��Ʒ����
							sample.setUf_0047251(pbt.getUf_0047251());//��Ʒ����
							sample.setUf_0047232(pbt.getUf_0047232());//��������
							sample.setUf_0047220(pbt.getUf_0047220());//�̱�
							sample.setUf_0047218(pbt.getUf_0047218());//����ͺ�
							sample.setUf_0047231(pbt.getUf_0047231());//ӡˢ����
							sample.setUf_0047233(pbt.getUf_0047233());//�����
							sample.setUf_0047332(pbt.getUf_0047332());//��Ʒ״̬����
							sample.setF_sample_count(pbt.getF_sample_count());//��Ʒ����
							sample.setUf_004778("0047000481");//������ʽ���ʼ�
							sample.setUf_004780("0047000482");//����Ҫ����
							sample.setUf_004779("0047000484");//��Ʒ���ã��ɼ��鷽����
							sample.setUf_004798("��Ҫ");//����ɨ�������Ҫ
							sample.setUf_004758("0047000487");//���潻������ȡ
							
							//�������ϴ�
							List<UploadFile> ufs=uploadFileService.getSampleAttach(af.getTitleno(),
									pbt.getF_sample_code());
							if(ufs.size()>0){
								UploadFile uf=ufs.get(0);
								String path2=path1+"/src/main/webapp/"+uf.getUprul();//��ȡ�ļ���·��
								File file=new File(path2);
								FileInputStream fileInputStream=new FileInputStream(file);
								int flength=(int)file.length();
								byte[] fbate=new byte[flength];
								fileInputStream.read(fbate, 0, flength);
								fileInputStream.close();
								sample.setMfile(fbate);
							}
							pbtService.setStatus(pbt.getUf_sample_code(),"1");//�޸�pbt��״̬:����Ϊ�Ѷ�
						}
						//�л����ݿ�,����Ҫ�ϴ�����Ʒ���Ƶ�oracle����
						DynamicDataSource.setCustomerType(DynamicDataSource.DATASOURCETWO);
						for(PbtSample2 sample:lists){//�����ص���Ʒ��Ϣ�ϴ���oracle���ݿ���
							pbtService2.insert(sample);
						}
						//���ؼ�ⱨ���ļ�
						List<PbtReport> reports=reportService.getReports();
						if(!reports.isEmpty()){
						//���ص��ģ�
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
							//�޸ļ�ⱨ����е�״̬
							reportService.chageStatus(reports.get(0).getF_report_key());
						//���м�Ĵ��������ص�һ���ļ�����Ҫ��
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
