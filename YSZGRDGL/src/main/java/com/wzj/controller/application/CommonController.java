package com.wzj.controller.application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.net.URLEncoder;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wzj.pojo.*;
import com.wzj.pojo.Number;
import com.wzj.service.TestingEquipService;
import com.wzj.service.impl.PrintEquipmentService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wzj.service.alternation.impl.FormChangeService;
import com.wzj.service.application.impl.ApplicationFormService;
import com.wzj.service.application.impl.CompanyInfoService;
import com.wzj.service.application.impl.ReviewFormService;
import com.wzj.service.application.impl.UploadFileService;
import com.wzj.service.impl.EvaluationService;
import com.wzj.service.impl.NumberService;
import com.wzj.util.AddLog;
import com.wzj.util.ExportUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wzj.DTO.EnterpriseSearchDTO;
import com.wzj.common.Obj2Obj;
import com.wzj.common.PdfUtil;

@Controller
@RequestMapping("common")
public class CommonController {
	@Autowired
	private ApplicationFormService applicationFormService;
	@Autowired
	private CompanyInfoService companyInfoService;
	@Autowired
	private ReviewFormService reviewFormService;
	@Autowired
	private UploadFileService uploadFileService;
	@Autowired
	private NumberService numberService;
	@Autowired
	private FormChangeService formChangeService;
	@Autowired
	private EvaluationService evaluationService;
	@Autowired
	private PrintEquipmentService printEquipmentService;
	@Autowired
	private TestingEquipService testingEquipService;

	@RequestMapping("toEnterpriseInfo")
	public String toEnterprises(HttpSession session,Model model){
		AddLog.addLog(Log.QUERY,"��ѯ������ҵ��Ϣ");
		ActiveUser activeUser=(ActiveUser) session.getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		model.addAttribute("branchId",branchId);
		return "common/enterpriseInfo";
	}
	
	@RequestMapping("toCancelledEnterpriseInfo")
	public String toCancelledEnterprises(HttpSession session,Model model){
		AddLog.addLog(Log.QUERY,"��ѯ������ע������ҵ��Ϣ");
		ActiveUser activeUser=(ActiveUser) session.getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		model.addAttribute("branchId",branchId);
		return "common/cancelledEnterpriseInfo";
	}
	
	@RequestMapping("getAll")
	@ResponseBody
	public Object getAll(Integer isSortCol_0,String sSortDir_0,int iDisplayStart,int iDisplayLength,String sEcho,String sSearch,HttpSession session){
		String sort="";
		if(isSortCol_0!=null){
			switch(isSortCol_0){
				case 4:sort+="branchName";break;
				case 5:sort+="enterprisename";break;
				case 6:sort+="serial";break;
				case 7:sort+="certappdate";break;
				case 8:sort+="certtodate";break;
				case 9:sort+="createdate";break;
				case 10:sort+="branchId";break;
			}
		}else{
			sort+="createdate";
		}
		if(sSortDir_0==null||"desc".equals(sSortDir_0)){
			sort+=" asc";
		}else{
			sort+=" desc";
		}
		//ҳ��
        int page_num = (iDisplayStart / iDisplayLength) + 1;
        //����ҳ���ÿҳ���Ƚ��н�ȡ
        PageHelper.startPage(page_num, iDisplayLength,sort);
		//���Ȼ�ȡ������֧����id
		ActiveUser activeUser=(ActiveUser) session.getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		//�滻nullֵ
		List<CompanyInfo> coms =null;
		if(StringUtils.isEmpty(sSearch)){//�ǲ��ҹ���
			coms = companyInfoService.getAll(branchId);
		}else{
			coms = companyInfoService.getAllByLike(branchId,"%"+sSearch+"%");
		}
        //���з�ҳ����
        PageInfo<CompanyInfo> pageInfo = new PageInfo<CompanyInfo>(coms);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("sEcho", sEcho);
        map.put("aaData", pageInfo.getList());
        map.put("iTotalRecords", (int)pageInfo.getTotal());
        map.put("iTotalDisplayRecords", (int)pageInfo.getTotal());
        return map;
	}
	
	//��ȡע�������ڸ������ڵ���ҵ��Ϣ
	@RequestMapping("getInTime")
	@ResponseBody
	public Object getInTime(String sTime,String eTime) throws ParseException{
		return companyInfoService.getInTime(sTime,eTime);
	}
	//ʵ�֡�ע�������ܵ�action
	@RequestMapping("cancel")
	@ResponseBody
	public int cancel(int id){
		CompanyInfo com =companyInfoService.getComById(id);
		AddLog.addLog(Log.MODIFY,"ע��'"+com.getEnterprisename()+"'����ҵ��Ϣ");
		//������ҵid��ȡ��Ӧҵ����е�����ҵ��
		List<ApplicationForm> app=applicationFormService.getAppBycid(id);
		for (ApplicationForm applicationForm : app) {
			applicationForm.setCompanyId(id);
			applicationForm.setZhuxiao("��ע��");
			applicationFormService.updateApp(applicationForm);
		}
		return companyInfoService.cancel(id);
	}
	//���ҡ�ע��������ҵ��Ϣ
	/*@RequestMapping("getCancel")
	@ResponseBody
	public Object getCancel(HttpSession session){
		AddLog.addLog(Log.QUERY,"�鿴��ע������ҵ��Ϣ");
		String branchId=((ActiveUser)session.getAttribute("activeUser")).getRamusCenter();
		List<CompanyInfo> coms = companyInfoService.getCancel(branchId);
		List<CompanyInfo> lists=new ArrayList<>();
		for(CompanyInfo com:coms){//����nullֵΪ��������0
			CompanyInfo info=(CompanyInfo)FilterNull.filterNull(com);
			lists.add(info);
		}
		//return companyInfoService.getCancel();
		return lists;
	}*/
	
	@RequestMapping("getCancel")
	@ResponseBody
	public Object getCancel(Integer isSortCol_0,String sSortDir_0,int iDisplayStart,int iDisplayLength,String sEcho,String sSearch,HttpSession session){
		//ҳ��
		String sort="";
		if(isSortCol_0!=null){
			switch(isSortCol_0){
				case 4:sort+="branchName";break;
				case 5:sort+="enterprisename";break;
				case 6:sort+="serial";break;
				case 7:sort+="certappdate";break;
				case 8:sort+="certtodate";break;
				case 9:sort+="createdate";break;
				case 10:sort+="branchId";break;
			}
		}else{
			sort+="createdate";
		}
		if("asc".equals(sSortDir_0)){
			sort+=" desc";
		}else{
			sort+=" asc";
		}
        int page_num = (iDisplayStart / iDisplayLength) + 1;
        //����ҳ���ÿҳ���Ƚ��н�ȡ
        PageHelper.startPage(page_num, iDisplayLength,sort);
		//���Ȼ�ȡ������֧����id
		ActiveUser activeUser=(ActiveUser) session.getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		//�滻nullֵ
		List<CompanyInfo> coms = null;
		if(StringUtils.isEmpty(sSearch)){//�ǲ��ҹ���
			coms = companyInfoService.getCancel(branchId);
		}else{
			coms = companyInfoService.getCancel2(branchId,"%"+sSearch+"%");
		}
        //���з�ҳ����
        PageInfo<CompanyInfo> pageInfo = new PageInfo<CompanyInfo>(coms);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("sEcho", sEcho);
        map.put("aaData", pageInfo.getList());
        map.put("iTotalRecords", (int)pageInfo.getTotal());
        map.put("iTotalDisplayRecords", (int)pageInfo.getTotal());
        return map;
	}
	
	
	//ʵ�������롰������ܵ�action
	@RequestMapping("activate")
	@ResponseBody
	public void activate(int id){
		CompanyInfo company=companyInfoService.getComById(id);
		AddLog.addLog(Log.MODIFY,"����'"+company.getEnterprisename()+"'����ҵ��Ϣ��������ҵ��");
		companyInfoService.delete(id);
		Obj2Obj o2o=new Obj2Obj();
		ApplicationForm com2App = o2o.Com2App(company);
		com2App.setStatus(1);
		com2App.setZhuxiao("");
		String titleNo=getNum("XB",com2App.getBranchId());
		com2App.setTitleno(titleNo);
		com2App.setCreatedate(new Date());
		applicationFormService.insertApp(com2App);
	}
	
	//ʵ�ָ��ϡ�������ܵ�action
	@RequestMapping("reActivate")
	@ResponseBody
	public void reAactivate(int id,HttpServletRequest request,HttpSession session) throws IOException{
		ActiveUser activeUser=(ActiveUser) session.getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		String titleNo=getNum("FR",branchId);
		CompanyInfo com =companyInfoService.getComById(id);
		AddLog.addLog(Log.MODIFY,"����'"+com.getEnterprisename()+"'����ҵ��Ϣ������ҵ��");
		int chan = companyInfoService.reActivate(id);
		if(chan>0){//���״̬�޸ĳɹ��������ݲ��뵽ys_title��
			Obj2Obj obj=new Obj2Obj();
			ApplicationForm af=obj.Com2App(com);
			af.setStatus(18);
			String titleNO=af.getTitleno();
			String substring = titleNO.substring(0,2);
			if("XB".equals(substring)||"BG".equals(substring)){
				Date date=new Date();
				af.setTitleno(titleNo);
				af.setCreatedate(date);
				applicationFormService.insertApp(af);
				
				//�������ݵ����ݿ�
				String path="";
				String type="1,2,3";
				List<UploadFile> ufList=uploadFileService.getByType(titleNO,type);
				if(ufList!=null){
					for (UploadFile uploadFile : ufList) {
						uploadFile.setCode(titleNO.replace(substring, "FR"));
						path=uploadFile.getUprul();
						String fileName=path.substring(path.lastIndexOf("/"), path.length());
						String newPath="FR/"+titleNo+"/"+fileName;
						uploadFile.setUprul(newPath);
						/*if("XB".equals(substring)){
							uploadFile.setUprul(path.replaceAll("XB", "FR"));
						}else if("BG".equals(substring)){
							uploadFile.setUprul(path.replaceAll("BG", "FR"));
						}*/
						
						uploadFile.setId(null);
						uploadFileService.insert(uploadFile);
					}
					
					String fileOldPath = request.getSession().getServletContext().getRealPath("/upload");
					if("XB".equals(substring)){
						fileOldPath+="/XB/"+titleNO;
					}else if("BG".equals(substring)){
						fileOldPath+="/BG/"+titleNO;
					}
					String fileNewPath=request.getSession().getServletContext().getRealPath("/upload");
					/*fileNewPath+="/FR/"+titleNO.replace(substring, "FR");
					ExportUtil.copy(fileOldPath, fileNewPath, true);*/
					fileNewPath+="/FR/"+titleNo;
					ExportUtil.copy(fileOldPath, fileNewPath, true);
				}
			}else{
				af.setCreatedate(new Date());
				applicationFormService.insertApp(af);
			}
			
			
		}
	}
	
	//ʵ�ֱ����������ܵ�action
	@RequestMapping("reChange")
	@ResponseBody
	public void reChange(int id){
		CompanyInfo com =companyInfoService.getComById(id);
		AddLog.addLog(Log.MODIFY,"����'"+com.getEnterprisename()+"'����ҵ��Ϣ�����ҵ��");
		int chan=companyInfoService.reChange(id);
		if(chan>0){//���״̬�޸ĳɹ��������ݲ��뵽ys_change��
			Obj2Obj obj=new Obj2Obj();
			FormChange fc=obj.App2Change(com);
			fc.setStatus(0);
			String titleNO=com.getTitleno();
			String substring = titleNO.substring(0,2);
//			String titleNo=getNum("BG",com.getBranchId());
			fc.setTitleno(titleNO.replace(substring, "BG"));
			fc.setCreatedate(new Date());
			formChangeService.insertFormChange(fc);
		}
	}
	
	//ͼƬ��ʾ
	@RequestMapping("getImg")
	@ResponseBody
	public void getImg(String code,HttpServletResponse response){
		UploadFile uploadFileByCode = applicationFormService.getUploadFileByCode(code);
		String url=uploadFileByCode.getUprul();
		FileInputStream in;
		try {
			in = new FileInputStream(new File(url));
			OutputStream o = response.getOutputStream();
			  int l = 0;
			  byte[] buffer = new byte[4096];
			  while((l = in.read(buffer)) != -1){
			    o.write(buffer,0,l);
			  }
			  o.flush();
			  in.close();
			  o.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@RequestMapping("getI")
	@ResponseBody
	public Object getI(String code){
		UploadFile uploadFileByCode = applicationFormService.getUploadFileByCode(code);
		String url=uploadFileByCode.getUprul();
		Map<String,String> map=new HashMap<>();
		map.put("url",url);
		return map;
	}
	//��ȡpdf·��
	@RequestMapping("getPath")
	@ResponseBody
	public Object getPath(String titleNo,String describe,HttpServletRequest request){
		UploadFile uploadFileByCode = applicationFormService.getUploadFile(titleNo,describe);
		String url="";
		Map<String,String> map=new HashMap<>();
		if(uploadFileByCode==null){
			url="δ�ҵ����ļ�!";
		}else{
			url=uploadFileByCode.getUprul();
		}
		map.put("url",url);
		return map;
	}
	//��ȡ��ʼʱ��
		private Timestamp getStime(Date start){
			Date stime = null;
			if(start==null){//�����ʼʱ��Ϊnull,��ʹ�ñ���ȵĵ�һ����Ϊ��ʼʱ��
				Calendar calendar = Calendar.getInstance();  
		        calendar.clear();  
		        calendar.set(Calendar.YEAR,new Date().getYear());
		        stime = calendar.getTime();
			}else{
				stime=start;
			}
	        Timestamp s = new Timestamp(stime.getTime());
	        return s;
		}
	//ʱ���ֵ�ж�
		private Timestamp getTime(Date time) throws ParseException{
			Timestamp realTime=null;
//			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			if(!"".equals(time)){
				realTime=getStime(time);
			}else{
				realTime=getStime(null);
			}
			return realTime;
		}
	//�߼���ѯ
	@RequestMapping("supperSearch")
	@ResponseBody
	public Object supperSearch(Integer isSortCol_0,String sSortDir_0,EnterpriseSearchDTO esDTO,int iDisplayStart,int iDisplayLength,String sEcho,String sSearch,HttpSession session,String status) throws ParseException{
		AddLog.addLog(Log.QUERY,"������ҵ��Ϣ");
		String sort="";
		if(isSortCol_0!=null){
			switch(isSortCol_0){
				case 4:sort+="branchName";break;
				case 5:sort+="enterprisename";break;
				case 6:sort+="serial";break;
				case 7:sort+="certappdate";break;
				case 8:sort+="certtodate";break;
				case 9:sort+="createdate";break;
				case 10:sort+="branchId";break;
			}
		}else{
			sort+="createdate";
		}
		if(sSortDir_0==null||"desc".equals(sSortDir_0)){
			sort+=" asc";
		}else{
			sort+=" desc";
		}
		ActiveUser activeUser=(ActiveUser) session.getAttribute("activeUser");
		String branchNum=activeUser.getRamusCenter();
		//ҳ��
        int page_num = (iDisplayStart / iDisplayLength) + 1;
        //����ҳ���ÿҳ���Ƚ��н�ȡ
        PageHelper.startPage(page_num, iDisplayLength,sort);
		String branchId=esDTO.getBranchId();
		String appStartTime;//���뿪ʼʱ��
		String appEndTime;//�������ʱ��
		appStartTime=getTime(esDTO.getAppStartTime()).toString().substring(0, 10);
		appEndTime=getTime(esDTO.getAppEndTime()).toString().substring(0, 10);
		String certStartTime;//��֤��ʼʱ��
		String certEndTime;//��֤����ʱ��
		certStartTime=getTime(esDTO.getCertStartTime()).toString().substring(0, 10);
		certEndTime=getTime(esDTO.getCertEndTime()).toString().substring(0, 10);
		String expireStartTime;//���ڿ�ʼʱ��
		String expireEndTime;//���ڽ�
		expireStartTime=getTime(esDTO.getExpireStartTime()).toString().substring(0, 10);
		expireEndTime=getTime(esDTO.getExpireEndTime()).toString().substring(0, 10);
		String type=esDTO.getType();//ҵ������  ��ǰ̨������XB FR BG
		String enterpriseName=esDTO.getEnterpriseName();//��ҵ����
		String address=esDTO.getAddress();//���ڵ���
		String result= "";
		String term1=esDTO.getTerm1();//"or";//����1
		if(StringUtils.isEmpty(sSearch)){//�ǲ��ҹ���
			if("0000".equals(branchNum)){
				if(branchId.equals("ȫ��")||branchId.equals("")){
					result="select ID,branchId,TitleNo,branchName,EnterpriseName,CertAppDate,CertToDate,CreateDate,serial,status from ys_company where 1=1 ";
				}else{
					result="select ID,branchId,TitleNo,branchName,EnterpriseName,CertAppDate,CertToDate,CreateDate,serial,status from ys_company where branchId="+branchId+" ";
				}
			}else{
				result="select ID,branchId,TitleNo,branchName,EnterpriseName,CertAppDate,CertToDate,CreateDate,serial,status from ys_company where branchId="+branchNum+" ";
			}
		}else{
			if("0000".equals(branchNum)){
				if(branchId.equals("ȫ��")||branchId.equals("")){
					result="select ID,branchId,TitleNo,branchName,EnterpriseName,CertAppDate,CertToDate,CreateDate,serial,status from ys_company where 1=1 and concat_ws(EnterpriseName,englishname,Address,EnterpriseKind,Linkman,MainBusiness,branchName) like '%"+sSearch+"%'";
				}else{
					result="select ID,branchId,TitleNo,branchName,EnterpriseName,CertAppDate,CertToDate,CreateDate,serial,status from ys_company where branchId="+branchId+" and concat_ws(EnterpriseName,englishname,Address,EnterpriseKind,Linkman,MainBusiness,branchName) like '%"+sSearch+"%'";
				}
			}else{
				result="select ID,branchId,TitleNo,branchName,EnterpriseName,CertAppDate,CertToDate,CreateDate,serial from ys_company where branchId="+branchNum+" and concat_ws(EnterpriseName,englishname,Address,EnterpriseKind,Linkman,MainBusiness,branchName) like '%"+sSearch+"%'";
			}
		}
		
		if(term1.equals("or")||term1.equals("and")){
			result+=term1;
				if(esDTO.getAppStartTime()==null){
					result+=" CreateDate like '%"+appStartTime+"%' ";
				}else{
					result+=" CreateDate >= '"+appStartTime+" 00:00:00' and CreateDate <='"+appEndTime+" 24:00:00' ";
				}
			
		}else if(term1.equals("no")){
				if(esDTO.getAppStartTime()==null){
					result+=" and CreateDate not like '%"+appStartTime+"%' ";
				}else{
					result+=" and CreateDate not between '"+appStartTime+"' and '"+appEndTime+"' ";
			}
			
		}else{
			result+="";
		}
		
		String term2=esDTO.getTerm2();//����2
		if(term2.equals("or")||term2.equals("and")){
			result+=term2;
			if(esDTO.getCertStartTime()==null){
				result+=" CertAppDate like '%"+certStartTime+"%' ";
			}else{
				result+=" CertAppDate >= '"+certStartTime+" 00:00:00' and CertAppDate <='"+certEndTime+" 24:00:00' ";
			}
		}else if(term2.equals("no")){
			if(esDTO.getCertStartTime()==null){
				result+=" and CertAppDate not like '%"+certStartTime+"%' ";
			}else{
				result+=" and CertAppDate not between '"+certStartTime+"' and '"+certEndTime+"' ";
			}
		}else{
			result+="";
		}
		
		String term3=esDTO.getTerm3();//����3
		if(term3.equals("or")||term3.equals("and")){
			result+=term3;
			if(esDTO.getExpireStartTime()==null){
				result+=" CertToDate like '%"+expireStartTime+"%' ";
			}else{
				result+=" CertToDate >= '"+expireStartTime+" 00:00:00' and CertToDate <='"+expireEndTime+" 24:00:00' ";
			}
		}else if(term3.equals("no")){
			if(esDTO.getExpireStartTime()==null){
				result+=" and CertToDate not like '%"+expireStartTime+"%' ";
			}else{
				result+=" and CertToDate not between '"+expireStartTime+"' and '"+expireEndTime+"' ";
			}
			
		}else{
			result+="";
		}
		String term4=esDTO.getTerm4();//����4
		if(term4.equals("or")||term4.equals("and")){
			result+=term4;
			if("".equals(type)){
				result+=" TitleNo like '%XX%' ";
			}else{
				result+=" TitleNo like '%"+type+"%' ";
			}
		}else if(term4.equals("no")){
				if("".equals(type)){
					result+=" and TitleNo not like '%XX%' ";
				}else{
					result+=" and TitleNo not like '%"+type+"%' ";
				}
			
		}else{
			result+="";
		}
		String term5=esDTO.getTerm5();//����5
		if(term5.equals("or")||term5.equals("and")){
			result+=term5;
			if("".equals(enterpriseName)){
				result+=" EnterpriseName like '%??%' ";
			}else{
				result+=" EnterpriseName like '%"+enterpriseName+"%' ";
			}
		}else if(term5.equals("no")){
				if("".equals(enterpriseName)){
					result+=" and EnterpriseName not like '%??%' ";
				}else{
					result+=" and EnterpriseName not like '%"+enterpriseName+"%' ";
				}
			
		}else{
			result+="";
		}
		String term6=esDTO.getTerm6();//����6
		if(term6.equals("or")||term6.equals("and")){
			result+=term6;
			if("".equals(address)){
				result+=" Address like '%??%' ";
			}else{
				result+=" Address like '%"+address+"%' ";
			}
		}else if(term6.equals("no")){
				if("".equals(address)){
					result+=" and Address not like '%??%' ";
				}else{
					result+=" and Address not like '%"+address+"%' ";
				}
		}else{
			result+="";
		}
			if(status.equals("��ע��")){
				result+=" and zhuxiao like '%��ע��%' ";
			}else{
				result+=" and (zhuxiao!='��ע��' or zhuxiao is null)";
			}
		
		//�滻nullֵ
		List<CompanyInfo> coms = companyInfoService.getAllBySql(result);
        //���з�ҳ����
        PageInfo<CompanyInfo> pageInfo = new PageInfo<CompanyInfo>(coms);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("sEcho", sEcho);
        map.put("aaData", pageInfo.getList());
        map.put("iTotalRecords", (int)pageInfo.getTotal());
        map.put("iTotalDisplayRecords", (int)pageInfo.getTotal());
        return map;
	}
	//��ѯ�������µ��ڵ���ҵ
	@RequestMapping("recentSearch")
	@ResponseBody
	public Object recentSearch(Integer isSortCol_0,String sSortDir_0,int iDisplayStart,int iDisplayLength,String sEcho,String sSearch,HttpSession session) throws ParseException{
		AddLog.addLog(Log.QUERY,"�����������µ��ڵ���ҵ��Ϣ");
		String sort="";
		if(isSortCol_0!=null){
			switch(isSortCol_0){
				case 4:sort+="branchName";break;
				case 5:sort+="enterprisename";break;
				case 6:sort+="serial";break;
				case 7:sort+="certappdate";break;
				case 8:sort+="certtodate";break;
				case 9:sort+="createdate";break;
				case 10:sort+="branchId";break;
			}
		}else{
			sort+="createdate";
		}
		if(sSortDir_0==null||"desc".equals(sSortDir_0)){
			sort+=" asc";
		}else{
			sort+=" desc";
		}
		//ҳ��
        int page_num = (iDisplayStart / iDisplayLength) + 1;
        //����ҳ���ÿҳ���Ƚ��н�ȡ
        PageHelper.startPage(page_num, iDisplayLength,sort);
        Date dNow = new Date();   //��ǰʱ��
        Date dAfter = new Date();
        Calendar calendar = Calendar.getInstance(); //�õ�����
        calendar.setTime(dNow);//�ѵ�ǰʱ�丳������
        calendar.add(calendar.MONTH, +3);  //����Ϊǰ3��
        dAfter = calendar.getTime();   //�õ�ǰ3�µ�ʱ��
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); //����ʱ���ʽ
        String defaultStartDate = sdf.format(dNow);    //��ʽ����3�µ�ʱ��
        String defaultEndDate = sdf.format(dAfter); //��ʽ����ǰʱ��
		//��ȡ�������µ��ڵ���ҵ��Ϣ
        ActiveUser activeUser=(ActiveUser) session.getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		List<CompanyInfo> coms=null;
		if(StringUtils.isEmpty(sSearch)){//�ǲ��ҹ���
			coms=companyInfoService.getRecentCompany1(defaultStartDate,defaultEndDate,branchId);
		}else{
			coms=companyInfoService.getRecentCompany(defaultStartDate,defaultEndDate,branchId,"%"+sSearch+"%");
		}
//			getRecentCompany();
        //���з�ҳ����
        PageInfo<CompanyInfo> pageInfo = new PageInfo<CompanyInfo>(coms);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("sEcho", sEcho);
        map.put("aaData", pageInfo.getList());
        map.put("iTotalRecords", (int)pageInfo.getTotal());
        map.put("iTotalDisplayRecords", (int)pageInfo.getTotal());
        return map;
	}
	//�������������ҵ��Ϣ
	@RequestMapping("checkArea")
	@ResponseBody
	public Object checkArea(Integer isSortCol_0,String sSortDir_0,String address,int iDisplayStart,int iDisplayLength,String sEcho,String sSearch,HttpSession session,String status) throws ParseException{
		AddLog.addLog(Log.QUERY,"���������ǣ�'"+address+"'����ҵ��Ϣ");
		String sort="";
		if(isSortCol_0!=null){
			switch(isSortCol_0){
				case 4:sort+="branchName";break;
				case 5:sort+="enterprisename";break;
				case 6:sort+="serial";break;
				case 7:sort+="certappdate";break;
				case 8:sort+="certtodate";break;
				case 9:sort+="createdate";break;
				case 10:sort+="branchId";break;
			}
		}else{
			sort+="createdate";
		}
		if("asc".equals(sSortDir_0)){
			sort+=" desc";
		}else{
			sort+=" asc";
		}
		//ҳ��
        int page_num = (iDisplayStart / iDisplayLength) + 1;
        //����ҳ���ÿҳ���Ƚ��н�ȡ
        PageHelper.startPage(page_num, iDisplayLength,sort);
		//��ȡ�û�������֧����
        ActiveUser activeUser=(ActiveUser) session.getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		List<CompanyInfo> coms = null;
		if(status.equals("��ע��")){
			if(address.equals("")){
				coms=companyInfoService.getCancel(branchId);
			}else{
				coms=companyInfoService.getByAddress2(address,branchId);
			}
		}else{
			if(address.equals("")){
				coms=companyInfoService.getAll(branchId);
			}else{
				coms=companyInfoService.getByAddress(address,branchId);
			}
		}
		
//				getRecentCompany();
        //���з�ҳ����
        PageInfo<CompanyInfo> pageInfo = new PageInfo<CompanyInfo>(coms);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("sEcho", sEcho);
        map.put("aaData", pageInfo.getList());
        map.put("iTotalRecords", (int)pageInfo.getTotal());
        map.put("iTotalDisplayRecords", (int)pageInfo.getTotal());
        return map;
	}
	@RequestMapping("/exportRecord")
	public void exportRecord(Boolean zhuxiao,HttpSession session, HttpServletRequest request,HttpServletResponse response){
		AddLog.addLog(Log.EXPORT,"����������ҵ��Ϣ");
		ActiveUser activeUser=(ActiveUser) session.getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		//��ȡ�������ļ���
		String path = request.getSession().getServletContext().getRealPath("export/enterprise");
		String title = "��ҵ��Ϣ";
		String fileName = title+".xlsx";
		String filePath = path+"/"+fileName;
		//������Ƽ�����
		boolean result = applicationFormService.exportRecord(zhuxiao,title,filePath,branchId);
		if(!result){//����
			return;
		}
		try{
			//���ݲ�ͬ����������������ļ�����������
			String userAgent = request.getHeader("User-Agent");
			//���IE����IEΪ�ں˵������
			if(userAgent.contains("MSIE")||userAgent.contains("Trident")){
				fileName = URLEncoder.encode(fileName,"UTF-8");
			}else{//��IE������Ĵ���
				fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
			}
			//��ȡһ����
			InputStream in = new FileInputStream(new File(filePath));
			//�������ص���Ӧͷ
			response.setHeader("content-disposition","attachment;fileName="+fileName);
			response.setCharacterEncoding("UTF-8");
			//��ȡresponse�ֽ���
			OutputStream out = response.getOutputStream();
			byte[] b = new byte[1024];
			int len = -1;
			while((len=in.read(b))!=-1){//��������ѭ��д�뵽�������
				out.write(b,0,len);
			}
			out.close();
			in.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	@RequestMapping("/exportPDF")
	public void exportPDF(int type,int id,HttpServletRequest request,HttpServletResponse response){
		
		PdfUtil pdf = new PdfUtil();
		ApplicationForm app=applicationFormService.getAppFormById(id);
		List<PrintEquipment> printEquipments = printEquipmentService.getByCompanyName(app.getEnterprisename());
		List<TestingEquip> testingEquips = testingEquipService.getByCompanyName(app.getEnterprisename());
		String path = request.getSession().getServletContext().getRealPath("/upload");
		String title = "";
		String fileName = "";
		String filePath = "";
		
		switch(type){
	    	case 1 : //�°�����
				//��ȡ�������ļ���
	    		title = "XB";
				fileName = app.getTitleno()+"����"+".pdf";
				//filePath = path+"/"+title+"/"+app.getTitleno()+"/"+fileName;
				filePath = path+"/"+title+"/"+app.getTitleno();
				File dir1=new File(filePath);
				if(!dir1.exists()&&!dir1.isDirectory()){//����ļ��������򴴽��ļ�.ע��mkdir��mkdirs���ߵ�����
					dir1.mkdirs();
				}
				filePath = filePath+"/"+fileName;
				pdf.createPDFApplication(app,filePath,printEquipments,testingEquips);
	    		break; 
	    	case 2 : //��������
	    		//��ȡ�������ļ���
				title = "FR";
				fileName = app.getTitleno()+"����"+".pdf";
				//filePath = path+"/"+title+"/"+fileName;
				filePath = path+"/"+title+"/"+app.getTitleno();
				File dir2=new File(filePath);
				if(!dir2.exists()&&!dir2.isDirectory()){//����ļ��������򴴽��ļ�.ע��mkdir��mkdirs���ߵ�����
					dir2.mkdirs();
				}
				filePath = filePath+"/"+fileName;
				pdf.createPDFRecognition(app,filePath,printEquipments,testingEquips);
	    		break; 
	    	case 3 : //�������
	    		//��ȡ�������ļ���
				title = "BG";
				fileName = app.getTitleno()+"����"+".pdf";
				//filePath = path+"/"+title+"/"+fileName;
				filePath = path+"/"+title+"/"+app.getTitleno();
				File dir3=new File(filePath);
				if(!dir3.exists()&&!dir3.isDirectory()){//����ļ��������򴴽��ļ�.ע��mkdir��mkdirs���ߵ�����
					dir3.mkdirs();
				}
				filePath = filePath+"/"+fileName;
				//pdf.createPDFRecognition(app,filePath);
	    		break; 
	    	default : 
	    		
		}
		//������Ƽ�����
		try{
			//���ݲ�ͬ����������������ļ�����������
			String userAgent = request.getHeader("User-Agent");
			//���IE����IEΪ�ں˵������
			if(userAgent.contains("MSIE")||userAgent.contains("Trident")){
				fileName = URLEncoder.encode(fileName,"UTF-8");
			}else{//��IE������Ĵ���
				fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
			}
			//��ȡһ����
			InputStream in = new FileInputStream(new File(filePath));
			//�������ص���Ӧͷ
			response.setHeader("content-disposition","attachment;fileName="+fileName);
			response.setCharacterEncoding("UTF-8");
			//��ȡresponse�ֽ���
			OutputStream out = response.getOutputStream();
			byte[] b = new byte[1024];
			int len = -1;
			while((len=in.read(b))!=-1){//��������ѭ��д�뵽�������
				out.write(b,0,len);
			}
			out.close();
			in.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/exportPDFPS")
	@ResponseBody
	public void exportPDFPS(int type,String titleNo,HttpServletRequest request){
		PdfUtil pdf = new PdfUtil();
		ReviewForm rev = reviewFormService.getReviewByTitleNo(titleNo);
		List<Evaluation> evalua=evaluationService.getAll(); 
		String path = request.getSession().getServletContext().getRealPath("upload");
		String title = "";
		String fileName = "";
		String filePath = "";
		switch(type){
	    	case 1 : //�°�����
				//��ȡ�������ļ���
				title = "XB";
				fileName = rev.getTitleno()+"����"+".pdf";
				filePath = path+"/"+title+"/"+rev.getTitleno();
				File dir1=new File(filePath);
				if(!dir1.exists()&&!dir1.isDirectory()){//����ļ��������򴴽��ļ�.ע��mkdir��mkdirs���ߵ�����
					dir1.mkdirs();
				}
				filePath = filePath+"/"+fileName;
				pdf.createPDFPS(rev,filePath,evalua);
	    		break; 
	    	case 2 : //��������
				//��ȡ�������ļ���
				title = "FR";
				fileName = rev.getTitleno()+"����"+".pdf";
				filePath = path+"/"+title+"/"+rev.getTitleno();
				File dir2=new File(filePath);
				if(!dir2.exists()&&!dir2.isDirectory()){//����ļ��������򴴽��ļ�.ע��mkdir��mkdirs���ߵ�����
					dir2.mkdirs();
				}
				filePath = filePath+"/"+fileName;
				pdf.createPDFPS(rev,filePath,evalua);
	    		break; 
	    		
	    	case 3 : //�°�����+���
	    		//��ȡ�������ļ���
				title = "XB";
				fileName = rev.getTitleno()+"����"+".pdf";
				filePath = path+"/"+title+"/"+rev.getTitleno();
				File dir3=new File(filePath);
				if(!dir3.exists()&&!dir3.isDirectory()){//����ļ��������򴴽��ļ�.ע��mkdir��mkdirs���ߵ�����
					dir3.mkdirs();
				}
				filePath = filePath+"/"+fileName;
				pdf.createPDFPSComment(rev,filePath);
	    		break; 
	    	case 4 : //��������+���
	    		//��ȡ�������ļ���
				title = "FR";
				fileName = rev.getTitleno()+"����"+".pdf";
				filePath = path+"/"+title+"/"+rev.getTitleno();
				File dir4=new File(filePath);
				if(!dir4.exists()&&!dir4.isDirectory()){//����ļ��������򴴽��ļ�.ע��mkdir��mkdirs���ߵ�����
					dir4.mkdirs();
				}
				filePath = filePath+"/"+fileName;
				pdf.createPDFPSComment(rev,filePath);
	    		break; 
	    	default : 
	    		
		}
	}
	
	//�����ɵ�pdf���浽��Ӧ��·����
	@RequestMapping("/savePdf")
	public void savePdf(int type,int id,HttpServletRequest request){
		PdfUtil pdf = new PdfUtil();
		ApplicationForm app=applicationFormService.getAppFormById(id);
		List<PrintEquipment> printEquipments = printEquipmentService.getByCompanyName(app.getEnterprisename());
		List<TestingEquip> testingEquips = testingEquipService.getByCompanyName(app.getEnterprisename());
		String path = request.getSession().getServletContext().getRealPath("/upload");
		String title = "";
		String fileName = "";
		String filePath = "";
		switch(type){
	    	case 1 : //�°�����
				//��ȡ�������ļ���
				title = "XB";
				fileName = app.getTitleno()+"����"+".pdf";
				filePath = path+"/"+title+"/"+app.getTitleno();
				File dir1=new File(filePath);
				if(!dir1.exists()&&!dir1.isDirectory()){//����ļ��������򴴽��ļ�.ע��mkdir��mkdirs���ߵ�����
					dir1.mkdirs();
				}
				filePath = filePath+"/"+fileName;
				pdf.createPDFApplication(app,filePath,printEquipments,testingEquips);
	    		break; 
	    	case 2 : //��������
	    		//��ȡ�������ļ���
				title = "FR";
				fileName = app.getTitleno()+"����"+".pdf";
				filePath = path+"/"+title+"/"+app.getTitleno();
				File dir2=new File(filePath);
				if(!dir2.exists()&&!dir2.isDirectory()){//����ļ��������򴴽��ļ�.ע��mkdir��mkdirs���ߵ�����
					dir2.mkdirs();
				}
				filePath = filePath+"/"+fileName;
				pdf.createPDFRecognition(app,filePath,printEquipments,testingEquips);
	    		break; 
	    	case 3 : //�������
	    		//��ȡ�������ļ���
				title = "BG";
				fileName = app.getTitleno()+"����"+".pdf";
				filePath = path+"/"+title+"/"+app.getTitleno();
				File dir3=new File(filePath);
				if(!dir3.exists()&&!dir3.isDirectory()){//����ļ��������򴴽��ļ�.ע��mkdir��mkdirs���ߵ�����
					dir3.mkdirs();
				}
				filePath = filePath+"/"+fileName;
				//pdf.createPDFRecognition(app,filePath);
	    		break; 
	    	default : 
		}
	}
	
	//����pdf
	@RequestMapping("downPdf")
	public void downPdf(int type,String titleNo,HttpServletRequest request,HttpServletResponse response){
		String path = request.getSession().getServletContext().getRealPath("/upload");
		String title = "";
		String fileName = "";
		String filePath = "";
		switch(type){
	    	case 1 : //�°�����
				//��ȡ�������ļ���
	    		AddLog.addLog(Log.EXPORT,"������ˮ��Ϊ'"+titleNo+"'���°�ҵ���������Ϣ");
				title = "XB";
				fileName = titleNo+"����"+".pdf";
	    		break; 
	    	case 2 : //��������
	    		//��ȡ�������ļ���
	    		AddLog.addLog(Log.EXPORT,"������ˮ��Ϊ"+titleNo+"�ĸ���ҵ���������Ϣ");
				title = "FR";
				fileName = titleNo+"����"+".pdf";
	    		break; 
	    	case 3 : //�������
	    		//��ȡ�������ļ���
	    		AddLog.addLog(Log.EXPORT,"������ˮ��Ϊ"+titleNo+"�ı��ҵ���������Ϣ");
				title = "BG";
				fileName = titleNo+"����"+".pdf";
	    		break; 
	    	case 4 : //�°�����
	    		//��ȡ�������ļ���
	    		AddLog.addLog(Log.EXPORT,"������ˮ��Ϊ"+titleNo+"���°�ҵ���������Ϣ");
				title = "XB";
				fileName = titleNo+"����"+".pdf";
	    		break; 
	    	case 5 : //��������
	    		//��ȡ�������ļ���
	    		AddLog.addLog(Log.EXPORT,"������ˮ��Ϊ"+titleNo+"�ĸ���ҵ���������Ϣ");
				title = "FR";
				fileName = titleNo+"����"+".pdf";
	    		break; 
	    	case 6 : //��������
	    		//��ȡ�������ļ���
	    		AddLog.addLog(Log.EXPORT,"������ˮ��Ϊ"+titleNo+"�ı��ҵ���������Ϣ");
				title = "BG";
				fileName = titleNo+"���"+".pdf";
	    		break; 
	    	default : 
		}
		filePath = path+"/"+title+"/"+titleNo+"/"+fileName;
		//������Ƽ�����
		try{
			//���ݲ�ͬ����������������ļ�����������
			String userAgent = request.getHeader("User-Agent");
			//���IE����IEΪ�ں˵������
			if(userAgent.contains("MSIE")||userAgent.contains("Trident")){
				fileName = URLEncoder.encode(fileName,"UTF-8");
			}else{//��IE������Ĵ���
				fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
			}
			//��ȡһ����
			File fil=new File(filePath);
			if(fil.exists()){//����ļ�����
				InputStream in = new FileInputStream(fil);
				//�������ص���Ӧͷ
				response.setHeader("content-disposition","attachment;fileName="+fileName);
				response.setCharacterEncoding("UTF-8");
				//��ȡresponse�ֽ���
				OutputStream out = response.getOutputStream();
				byte[] b = new byte[1024];
				int len = -1;
				while((len=in.read(b))!=-1){//��������ѭ��д�뵽�������
					out.write(b,0,len);
				}
				out.close();
				in.close();
			}else{
				response.setHeader("Content-type", "text/html;charset=UTF-8");  
		        String data = "�ļ������ڡ��������������ϵ����Ա��";  
		        OutputStream ps = response.getOutputStream();  
		        ps.write(data.getBytes("UTF-8"));  
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	//���ظ��º�ı�  
	@RequestMapping("downSeal")
	public void downSeal(int type,String titleNo,String appId,HttpServletRequest request,HttpServletResponse response){
		String path = request.getSession().getServletContext().getRealPath("/");
//		String title = "";
		String fileName = "";
		String filePath = "";
		String inputPath="";
		switch(type){
	    	case 1 : //�°�����
	    		fileName = titleNo+"����(����)"+".pdf";
	    		inputPath=uploadFileService.getPathByCode("xb"+appId);
	    		filePath = path+"/"+inputPath;
	    		break; 
	    	case 2 : //�����
	    		fileName = titleNo+"����(����)"+".pdf";
	    		inputPath=uploadFileService.getPathByCode("xb_ps_"+appId);
	    		filePath = path+"/"+inputPath;
	    		break; 
	    	default : 
		}
		//������Ƽ�����
		try{
			//���ݲ�ͬ����������������ļ�����������
			String userAgent = request.getHeader("User-Agent");
			//���IE����IEΪ�ں˵������
			if(userAgent.contains("MSIE")||userAgent.contains("Trident")){
				fileName = URLEncoder.encode(fileName,"UTF-8");
			}else{//��IE������Ĵ���
				fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
			}
			//��ȡһ����
			File fil=new File(filePath);
			if(fil.exists()){//����ļ�����
				InputStream in = new FileInputStream(fil);
				//�������ص���Ӧͷ
				response.setHeader("content-disposition","attachment;fileName="+fileName);
				response.setCharacterEncoding("UTF-8");
				//��ȡresponse�ֽ���
				OutputStream out = response.getOutputStream();
				byte[] b = new byte[1024];
				int len = -1;
				while((len=in.read(b))!=-1){//��������ѭ��д�뵽�������
					out.write(b,0,len);
				}
				out.close();
				in.close();
			}else{
				response.setHeader("Content-type", "text/html;charset=UTF-8");  
		        String data = "�ļ������ڡ��������������ϵ����Ա��";  
		        OutputStream ps = response.getOutputStream();  
		        ps.write(data.getBytes("UTF-8"));  
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	@Autowired
	private PdfUtil pdf1;
	
	@RequestMapping("/exportPDFComCert")
	public void exportPDFComCert(int type,int id,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		
		//PdfUtil pdf = new PdfUtil();
		CompanyInfo app = companyInfoService.getComById(id);
		//ApplicationForm app=applicationFormService.getLastAppBycid(info.getId());
		
		String path = request.getSession().getServletContext().getRealPath("/upload");
		String title = "";
		String fileName = "";
		String filePath = "";
		
		switch(type){
	    	case 1 : //֤��
				//��ȡ�������ļ���
	    		title = app.getTitleno().substring(0,2);
				fileName = app.getTitleno()+Print.companyCert+".pdf";
				filePath = path+"/"+title+"/"+app.getTitleno();
				File dir1=new File(filePath);
				if(!dir1.exists()&&!dir1.isDirectory()){//����ļ��������򴴽��ļ�.ע��mkdir��mkdirs���ߵ�����
					dir1.mkdirs();
				}
				filePath = filePath+"/"+fileName;
				pdf1.createPDFComCert(app,filePath);
	    		break; 
	    	case 2 : //����
	    		//��ȡ�������ļ���
	    		title = app.getTitleno().substring(0,2);
				fileName = app.getTitleno()+Print.companyCertFB+".pdf";
				filePath = path+"/"+title+"/"+app.getTitleno();
				File dir2=new File(filePath);
				if(!dir2.exists()&&!dir2.isDirectory()){//����ļ��������򴴽��ļ�.ע��mkdir��mkdirs���ߵ�����
					dir2.mkdirs();
				}
				filePath = filePath+"/"+fileName;
				pdf1.createPDFComCertFB(app,filePath);
	    		break; 
	    	default : 
	    		
		}
		//������Ƽ�����
		try{
			//���ݲ�ͬ����������������ļ�����������
			String userAgent = request.getHeader("User-Agent");
			//���IE����IEΪ�ں˵������
			if(userAgent.contains("MSIE")||userAgent.contains("Trident")){
				fileName = URLEncoder.encode(fileName,"UTF-8");
			}else{//��IE������Ĵ���
				fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
			}
			//��ȡһ����
			File file=new File(filePath);
			if(file.exists()){
			InputStream in = new FileInputStream(new File(filePath));
			//�������ص���Ӧͷ
			response.setHeader("content-disposition","attachment;fileName="+fileName);
			response.setCharacterEncoding("UTF-8");
			//��ȡresponse�ֽ���
			OutputStream out = response.getOutputStream();
			byte[] b = new byte[1024];
			int len = -1;
			while((len=in.read(b))!=-1){//��������ѭ��д�뵽�������
				out.write(b,0,len);
			}
			out.close();
			in.close();
			}else{
				response.setHeader("Content-type", "text/html;charset=UTF-8");  
		        String data = "�ļ������ڡ��������������ϵ����Ա��";  
		        OutputStream ps = response.getOutputStream();  
		        ps.write(data.getBytes("UTF-8"));
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
	//��ӡ������Ϣ
	@RequestMapping("/exportPDFSendmsg")
	public void exportPDFSendmsg(ExpressInfo express,String companyName,HttpServletRequest request,HttpServletResponse response, String sendUnit, String reciveUnit){
		
		PdfUtil pdf = new PdfUtil();
		String path = request.getSession().getServletContext().getRealPath("/upload");
		String title = "";
		
		String fileName = "";
		String filePath = "";
		
		//��ȡ�������ļ���
		title = "������Ϣ";
		fileName = express.getNumber()+"������Ϣ"+".pdf";
		filePath = path+"/"+title+"/"+companyName;
		File dir1=new File(filePath);
		if(dir1.exists()){//ɾ���ļ�
			dir1.delete();
		}
		if(!dir1.exists()&&!dir1.isDirectory()){//����ļ��������򴴽��ļ�.ע��mkdir��mkdirs���ߵ�����
			dir1.mkdirs();
		}
		filePath = filePath+"/"+fileName;
		pdf.createPDFSendmsg(express,filePath,companyName,sendUnit,reciveUnit);
		//������Ƽ�����
		try{
			//���ݲ�ͬ����������������ļ�����������
			String userAgent = request.getHeader("User-Agent");
			//���IE����IEΪ�ں˵������
			if(userAgent.contains("MSIE")||userAgent.contains("Trident")){
				fileName = URLEncoder.encode(fileName,"UTF-8");
			}else{//��IE������Ĵ���
				fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
			}
			//��ȡһ����
			File file=new File(filePath);
			if(file.exists()){
			InputStream in = new FileInputStream(new File(filePath));
			//�������ص���Ӧͷ
			response.setHeader("content-disposition","attachment;fileName="+fileName);
			response.setCharacterEncoding("UTF-8");
			//��ȡresponse�ֽ���
			OutputStream out = response.getOutputStream();
			byte[] b = new byte[1024];
			int len = -1;
			while((len=in.read(b))!=-1){//��������ѭ��д�뵽�������
				out.write(b,0,len);
			}
			out.close();
			in.close();
			}else{
				response.setHeader("Content-type", "text/html;charset=UTF-8");  
		        String data = "�ļ������ڡ��������������ϵ����Ա��";  
		        OutputStream ps = response.getOutputStream();  
		        ps.write(data.getBytes("UTF-8"));
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
	//��������
	@RequestMapping("/exportPDFComCerts")
	public void exportPDFComCerts(int type,String ids,HttpServletRequest request,HttpServletResponse response) throws FileNotFoundException{
		String[] id = null;   
		id = ids.split(","); 
		String names=ids.replace(",","_");
		PdfUtil pdf = new PdfUtil();
		String resultFileName = "";
		String resultFilePath = "";
		String fileNames="";
		String path = request.getSession().getServletContext().getRealPath("/upload");
		OutputStream output =null;
		List<InputStream> pdfs = new ArrayList<InputStream>();
		for(int i=0;i<id.length;i++){
			CompanyInfo app = companyInfoService.getComById(Integer.parseInt(id[i]));
			String title = "";
			String fileName = "";
			String filePath = "";
			//��ҵ���
			String str = "";
			if(app.getSerial()!=null){
				str=app.getSerial().toString();
			int len = str.length();
		    len = 6-len;
		    for(int m=0;m<len;m++){
		  	  str = "0"+str;
		    }
		   }
			app.setRealSerial(str);
			switch(type){
		    	case 1 : //֤��
		    		AddLog.addLog(Log.EXPORT,"����'"+app.getEnterprisename()+"'����ҵ֤��");
		    		title = app.getTitleno().substring(0,2);
					fileName = app.getTitleno()+"��ҵ֤��"+".pdf";
					filePath = path+"/"+title+"/"+app.getTitleno();
					File dir1=new File(filePath);
					if(!dir1.exists()){//����ļ��������򴴽��ļ�.ע��mkdir��mkdirs���ߵ�����
						dir1.mkdirs();
					}
					filePath=filePath+"/��ҵ֤��.pdf";
					File file1=new File(filePath);
					if(file1.exists()){
						file1.delete();
					}
					try {//�����ļ�
						file1.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
					pdf.createPDFComCert(app,filePath);
		    		break; 
		    	case 2 : //����
		    		//��ȡ�������ļ���
		    		AddLog.addLog(Log.EXPORT,"����"+app.getEnterprisename()+"����ҵ֤�鸱��");
		    		title = app.getTitleno().substring(0,2);
					fileName = app.getTitleno()+"��ҵ֤�鸱��.pdf";
					filePath = path+"/"+title+"/"+app.getTitleno();
					File dir2=new File(filePath);
					if(!dir2.exists()){//����ļ��������򴴽��ļ�.ע��mkdir��mkdirs���ߵ�����
						dir2.mkdirs();
					}
					filePath = filePath+"/��ҵ֤�鸱��.pdf";;
					File file2=new File(filePath);
					if(file2.exists()){
						file2.delete();
					}
					try {//�����ļ�
						file2.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
					pdf.createPDFComCertFB(app,filePath);
		    		break; 
		    	default : 
			}
			pdfs.add(new FileInputStream(filePath));
		}
		if(type==1){
			resultFilePath=path+"\\��ҵ֤��\\";
			File dir3=new File(resultFilePath);
			if(dir3.exists()){
				dir3.delete();
			}
			if(!dir3.exists()&&!dir3.isDirectory()){//����ļ��������򴴽��ļ�.ע��mkdir��mkdirs���ߵ�����
				dir3.mkdirs();
			}
			fileNames=names+"��ҵ֤��.pdf";
			resultFilePath=resultFilePath+fileNames;
			File rfp=new File(resultFilePath);
			try {
				rfp.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			output = new FileOutputStream(resultFilePath);
			pdf.concatPDFs(pdfs, output, false,1);
		}else if(type==2){
			resultFilePath=path+"\\��ҵ֤�鸱��\\";
			File dir4=new File(resultFilePath);
			if(dir4.exists()){
				dir4.delete();
			}
			if(!dir4.exists()&&!dir4.isDirectory()){//����ļ��������򴴽��ļ�.ע��mkdir��mkdirs���ߵ�����
				dir4.mkdirs();
			}
			fileNames=names+"��ҵ֤�鸱��.pdf";
			resultFilePath=resultFilePath+fileNames;
			File rfp=new File(resultFilePath);
			try {
				rfp.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			output = new FileOutputStream(resultFilePath);
			pdf.concatPDFs(pdfs, output, false,2);
		}
		try{
			//���ݲ�ͬ����������������ļ�����������  "d:\\merge.pdf"
			String userAgent = request.getHeader("User-Agent");
			//���IE����IEΪ�ں˵������
			if(userAgent.contains("MSIE")||userAgent.contains("Trident")){
				resultFileName = URLEncoder.encode(resultFileName,"UTF-8");
			}else{//��IE������Ĵ���
				resultFileName = new String(resultFileName.getBytes("UTF-8"),"ISO-8859-1");
			}
			if(resultFileName.equals(""))
				if(type==1)
					resultFileName=new String("֤��.pdf".getBytes("UTF-8"),"ISO-8859-1");
				else if(type==2)
					resultFileName=new String("֤�鸱��.pdf".getBytes("UTF-8"),"ISO-8859-1");
			File file=new File(resultFilePath);//��ȡһ����
			if(file.exists()){
				InputStream in = new FileInputStream(new File(resultFilePath));
				//�������ص���Ӧͷ
				response.setHeader("content-disposition","attachment;fileName="+resultFileName);
				response.setCharacterEncoding("UTF-8");//��ȡresponse�ֽ���
				OutputStream out = response.getOutputStream();
				byte[] b = new byte[1024];
				int len = -1;
				while((len=in.read(b))!=-1){//��������ѭ��д�뵽�������
					out.write(b,0,len);
				}
				out.close();
				in.close();
			}else{
				response.setHeader("Content-type", "text/html;charset=UTF-8");  
		        String data = "�ļ������ڡ��������������ϵ����Ա��";  
		        OutputStream ps = response.getOutputStream();  
		        ps.write(data.getBytes("UTF-8"));
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	//����XB��FR��BG��ȡ��ǰ��Ӧҵ��Ķ�Ӧֵ
		@RequestMapping("getNum")
		@ResponseBody
		public String getNum(String type,String branchId){
			Number SeriNum = numberService.getNumber();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			DecimalFormat df=new DecimalFormat("000");
			String num="";
			switch(type){
				case "XB":
					num=df.format(SeriNum.getXbNum());
					SeriNum.setXbNum(SeriNum.getXbNum()+1);
					break;
				case "FR":
					num=df.format(SeriNum.getFrNum());
					SeriNum.setFrNum(SeriNum.getFrNum()+1);
					break;
				case "BG":
					num=df.format(SeriNum.getBgNum());
					SeriNum.setBgNum(SeriNum.getBgNum()+1);
					break;
			}
			numberService.setNumber(SeriNum);
			return type+sdf.format(new Date())+branchId+"-"+num;
		}
		
		//����XB��FR��BG��ȡ��ǰ��Ӧҵ��Ķ�Ӧֵ
		@RequestMapping("getTitle")
		@ResponseBody
		public String getTitle(String type,String branchId){
			Number SeriNum = numberService.getNumber();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			DecimalFormat df=new DecimalFormat("000");
			String num="";
			switch(type){
				case "XB":
					num=df.format(SeriNum.getXbNum());
					break;
				case "FR":
					num=df.format(SeriNum.getFrNum());
					break;
				case "BG":
					num=df.format(SeriNum.getBgNum());
					break;
			}
			return type+sdf.format(new Date())+branchId+"-"+num;
		}

		//�鿴��ҵ��Ϣ
		@RequestMapping("view")
		public String view(HttpServletRequest request,Model model) {
			String id1=request.getParameter("id");
			int id=Integer.parseInt(id1);
			CompanyInfo comById = companyInfoService.getComById(id);
			AddLog.addLog(Log.QUERY,"��ѯ'"+comById.getEnterprisename()+"'����Ϣ");
			//������ҵ���е�ҵ���ŵõ�ҵ�������Ӧ�����һ��ҵ��
//			ApplicationForm appFormByBussinessNo = applicationFormService.getLastAppFormByBussinessNo(comById.getBusinessno());
			//ApplicationForm appFormByBussinessNo=applicationFormService.getLastAppBycid(id);
			//int appId=appFormByBussinessNo.getId();
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			//session.setAttribute("id",appId);
			//model.addAttribute("titleNo", appFormByBussinessNo.getTitleno());
			session.setAttribute("id",id);
			model.addAttribute("titleNo", comById.getTitleno());
			return "application/common/application_view";
		}
//�����ɵ�pdf���浽��Ӧ��·����
@RequestMapping("/savebgApp")
public void savebgApp(int type, Integer id, HttpServletRequest request) {
		PdfUtil pdf = new PdfUtil();
		FormChange form=formChangeService.getChangeFormById(id);
		ApplicationForm af=applicationFormService.getAppFormById(form.getPid());
		//֤���
		int certNo=af.getCertNo();
		String path = request.getSession().getServletContext().getRealPath("/upload");
		String title = "";
		String fileName = "";
		String filePath = "";
		switch(type){
	    	case 1 : //�������
	    		//��ȡ�������ļ���
				title = "BG";
				fileName = form.getTitleno()+"����"+".pdf";
				filePath = path+"/"+title+"/"+form.getTitleno();
				
				File dir1=new File(filePath);
				if(!dir1.exists()&&!dir1.isDirectory()){//����ļ��������򴴽��ļ�.ע��mkdir��mkdirs���ߵ�����
					dir1.mkdirs();
				}
				filePath = filePath+"/"+fileName;
				pdf.createPDFAlteration(form,filePath,certNo);
	    		break; 
	    	default : 
		}
	}

	@RequestMapping("getEnterpriseInfo")
	public String getEnterpriseInfo(int id,Model model){
		CompanyInfo com=companyInfoService.getComById(id);
		AddLog.addLog(Log.QUERY,"���'"+com.getEnterprisename()+"'����Ϣ");
		model.addAttribute("id",id);
		model.addAttribute("item",com);
		return "common/updateEnterpriseInfo";
	}
	@RequestMapping("saveCompany")
	public String insertReturn(CompanyInfo com,HttpServletRequest request,String cId){//�˻�������ύ���ȸ�����Ϣ����ִ������
		String companyName=com.getEnterprisename();
		AddLog.addLog(Log.MODIFY,"�޸�'"+companyName+"'����Ϣ");
		companyInfoService.update(com);
		PrintEquipment pe=new PrintEquipment();
		pe.setCompanyName(companyName);
		pe.setCompanyId(Integer.parseInt(cId));
		printEquipmentService.updateById(pe);
		TestingEquip te=new TestingEquip();
		te.setCompanyName(companyName);
		te.setCompanyId(Integer.parseInt(cId));
		testingEquipService.updateById(te);
		return "common/enterpriseInfo";
	}
	
	@RequestMapping("getComById")
	@ResponseBody
	public Object getComById(int id){
		return companyInfoService.getComById(id);
	}
	//��ȡ��ҵ���
	@RequestMapping("getComId")
	@ResponseBody
	public int getComId(String enterpriseName){
		CompanyInfo com =companyInfoService.getComByName(enterpriseName);
//		AddLog.addLog(Log.MODIFY,"��ȡ'"+com.getEnterprisename()+"'����Ϣ");
		return com.getId();
	}
	//����Excel
	@RequestMapping("/exportExcel")
	public void exportExcel(HttpServletRequest request,HttpServletResponse response,EnterpriseSearchDTO esDTO,String status,String sSearch,HttpSession session) throws ParseException{
		AddLog.addLog(Log.EXPORT,"������ҵ�������");
		ActiveUser activeUser=(ActiveUser) session.getAttribute("activeUser");
		String branchNum=activeUser.getRamusCenter();
		String branchId=esDTO.getBranchId();
		String appStartTime;//���뿪ʼʱ��
		String appEndTime;//�������ʱ��
		appStartTime=getTime(esDTO.getAppStartTime()).toString().substring(0, 10);
		appEndTime=getTime(esDTO.getAppEndTime()).toString().substring(0, 10);
		String certStartTime;//��֤��ʼʱ��
		String certEndTime;//��֤����ʱ��
		certStartTime=getTime(esDTO.getCertStartTime()).toString().substring(0, 10);
		certEndTime=getTime(esDTO.getCertEndTime()).toString().substring(0, 10);
		String expireStartTime;//���ڿ�ʼʱ��
		String expireEndTime;//���ڽ�
		expireStartTime=getTime(esDTO.getExpireStartTime()).toString().substring(0, 10);
		expireEndTime=getTime(esDTO.getExpireEndTime()).toString().substring(0, 10);
		String type=esDTO.getType();//ҵ������  ��ǰ̨������XB FR BG
		String enterpriseName=esDTO.getEnterpriseName();//��ҵ����
		String address=esDTO.getAddress();//���ڵ���
		String result= "";
		String term1=esDTO.getTerm1();//"or";//����1
		if(StringUtils.isEmpty(sSearch)){//�ǲ��ҹ���
			if("0000".equals(branchNum)){
				if(branchId.equals("ȫ��")||branchId.equals("")){
					result="select * from ys_company where 1=1 ";
				}else{
					result="select * from ys_company where branchId="+branchId+" ";
				}
			}else{
				result="select * from ys_company where branchId="+branchNum+" ";
			}
		}else{
			if("0000".equals(branchNum)){
				if(branchId.equals("ȫ��")||branchId.equals("")){
					result="select * from ys_company where 1=1 and concat_ws(EnterpriseName,englishname,Address,EnterpriseKind,Linkman,MainBusiness,branchName) like '%"+sSearch+"%'";
				}else{
					result="select * from ys_company where branchId="+branchId+" and concat_ws(EnterpriseName,englishname,Address,EnterpriseKind,Linkman,MainBusiness,branchName) like '%"+sSearch+"%'";
				}
			}else{
				result="select * from ys_company where branchId="+branchNum+" and concat_ws(EnterpriseName,englishname,Address,EnterpriseKind,Linkman,MainBusiness,branchName) like '%"+sSearch+"%'";
			}
		}
		
		if(term1.equals("or")||term1.equals("and")){
			result+=term1;
				if(esDTO.getAppStartTime()==null){
					result+=" CreateDate like '%"+appStartTime+"%' ";
				}else{
					result+=" CreateDate >= '"+appStartTime+" 00:00:00' and CreateDate <='"+appEndTime+" 24:00:00' ";
				}
			
		}else if(term1.equals("no")){
				if(esDTO.getAppStartTime()==null){
					result+=" and CreateDate not like '%"+appStartTime+"%' ";
				}else{
					result+=" and CreateDate not between '"+appStartTime+"' and '"+appEndTime+"' ";
			}
			
		}else{
			result+="";
		}
		
		String term2=esDTO.getTerm2();//����2
		if(term2.equals("or")||term2.equals("and")){
			result+=term2;
			if(esDTO.getCertStartTime()==null){
				result+=" CertAppDate like '%"+certStartTime+"%' ";
			}else{
				result+=" CertAppDate >= '"+certStartTime+" 00:00:00' and CertAppDate <='"+certEndTime+" 24:00:00' ";
			}
		}else if(term2.equals("no")){
			if(esDTO.getCertStartTime()==null){
				result+=" and CertAppDate not like '%"+certStartTime+"%' ";
			}else{
				result+=" and CertAppDate not between '"+certStartTime+"' and '"+certEndTime+"' ";
			}
		}else{
			result+="";
		}
		
		String term3=esDTO.getTerm3();//����3
		if(term3.equals("or")||term3.equals("and")){
			result+=term3;
			if(esDTO.getExpireStartTime()==null){
				result+=" CertToDate like '%"+expireStartTime+"%' ";
			}else{
				result+=" CertToDate >= '"+expireStartTime+" 00:00:00' and CertToDate <='"+expireEndTime+" 24:00:00' ";
			}
		}else if(term3.equals("no")){
			if(esDTO.getExpireStartTime()==null){
				result+=" and CertToDate not like '%"+expireStartTime+"%' ";
			}else{
				result+=" and CertToDate not between '"+expireStartTime+"' and '"+expireEndTime+"' ";
			}
			
		}else{
			result+="";
		}
		String term4=esDTO.getTerm4();//����4
		if(term4.equals("or")||term4.equals("and")){
			result+=term4;
			if("".equals(type)){
				result+=" TitleNo like '%XX%' ";
			}else{
				result+=" TitleNo like '%"+type+"%' ";
			}
		}else if(term4.equals("no")){
				if("".equals(type)){
					result+=" and TitleNo not like '%XX%' ";
				}else{
					result+=" and TitleNo not like '%"+type+"%' ";
				}
			
		}else{
			result+="";
		}
		String term5=esDTO.getTerm5();//����5
		if(term5.equals("or")||term5.equals("and")){
			result+=term5;
			if("".equals(enterpriseName)){
				result+=" EnterpriseName like '%??%' ";
			}else{
				result+=" EnterpriseName like '%"+enterpriseName+"%' ";
			}
		}else if(term5.equals("no")){
				if("".equals(enterpriseName)){
					result+=" and EnterpriseName not like '%??%' ";
				}else{
					result+=" and EnterpriseName not like '%"+enterpriseName+"%' ";
				}
			
		}else{
			result+="";
		}
		String term6=esDTO.getTerm6();//����6
		if(term6.equals("or")||term6.equals("and")){
			result+=term6;
			if("".equals(address)){
				result+=" Address like '%??%' ";
			}else{
				result+=" Address like '%"+address+"%' ";
			}
		}else if(term6.equals("no")){
				if("".equals(address)){
					result+=" and Address not like '%??%' ";
				}else{
					result+=" and Address not like '%"+address+"%' ";
				}
		}else{
			result+="";
		}
			if(status.equals("��ע��")){
				result+=" and zhuxiao like '%��ע��%' order by CreateDate desc";
			}else{
				result+=" and (zhuxiao!='��ע��' or zhuxiao is null) order by CreateDate desc";
			}
		
		List<CompanyInfo> coms = companyInfoService.getAllBySql(result);
		
		//��ȡ�������ļ���
		String path = request.getSession().getServletContext().getRealPath("./");
		String title = "��ҵ��Ϣ";
		String fileName = title+".xlsx";
		String filePath = path+"/export/enterpriseResult/"+fileName;
		//������Ƽ�����
		boolean exportresult = companyInfoService.exportExcel(title,filePath,coms);
		if(!exportresult){//����
			return;
		}
		try{
			//���ݲ�ͬ����������������ļ�����������
			String userAgent = request.getHeader("User-Agent");
			//���IE����IEΪ�ں˵������
			if(userAgent.contains("MSIE")||userAgent.contains("Trident")){
				fileName = URLEncoder.encode(fileName,"UTF-8");
			}else{//��IE������Ĵ���
				fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
			}
			//��ȡһ����
			InputStream in = new FileInputStream(new File(filePath));
			//�������ص���Ӧͷ
			response.setHeader("content-disposition","attachment;fileName="+fileName);
			response.setCharacterEncoding("UTF-8");
			//��ȡresponse�ֽ���
			OutputStream out = response.getOutputStream();
			byte[] b = new byte[1024];
			int len = -1;
			while((len=in.read(b))!=-1){//��������ѭ��д�뵽�������
				out.write(b,0,len);
			}
			out.close();
			in.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	//���ݽ������µ��ڵ���Excel
	@RequestMapping("/exportExcelByRecent")
	public void exportExcelByRecent(HttpServletRequest request,HttpServletResponse response,EnterpriseSearchDTO esDTO,String status,String sSearch,HttpSession session) throws ParseException{
		AddLog.addLog(Log.EXPORT,"������ҵ�������");
		Date dNow = new Date();   //��ǰʱ��
        Date dAfter = new Date();
        Calendar calendar = Calendar.getInstance(); //�õ�����
        calendar.setTime(dNow);//�ѵ�ǰʱ�丳������
        calendar.add(calendar.MONTH, +3);  //����Ϊǰ3��
        dAfter = calendar.getTime();   //�õ�ǰ3�µ�ʱ��
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); //����ʱ���ʽ
        String defaultStartDate = sdf.format(dNow);    //��ʽ����3�µ�ʱ��
        String defaultEndDate = sdf.format(dAfter); //��ʽ����ǰʱ��
		//��ȡ�������µ��ڵ���ҵ��Ϣ
        ActiveUser activeUser=(ActiveUser) session.getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		List<CompanyInfo> coms=null;
		if(StringUtils.isEmpty(sSearch)){//�ǲ��ҹ���
			coms=companyInfoService.getRecentCompany1(defaultStartDate,defaultEndDate,branchId);
		}else{
			coms=companyInfoService.getRecentCompany(defaultStartDate,defaultEndDate,branchId,"%"+sSearch+"%");
		}
		
		//��ȡ�������ļ���
		String path = request.getSession().getServletContext().getRealPath("./");
		String title = "��ҵ��Ϣ";
		String fileName = title+".xlsx";
		String filePath = path+"/export/enterpriseResult/"+fileName;
		//������Ƽ�����
		boolean exportresult = companyInfoService.exportExcel(title,filePath,coms);
		if(!exportresult){//����
			return;
		}
		try{
			//���ݲ�ͬ����������������ļ�����������
			String userAgent = request.getHeader("User-Agent");
			//���IE����IEΪ�ں˵������
			if(userAgent.contains("MSIE")||userAgent.contains("Trident")){
				fileName = URLEncoder.encode(fileName,"UTF-8");
			}else{//��IE������Ĵ���
				fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
			}
			//��ȡһ����
			InputStream in = new FileInputStream(new File(filePath));
			//�������ص���Ӧͷ
			response.setHeader("content-disposition","attachment;fileName="+fileName);
			response.setCharacterEncoding("UTF-8");
			//��ȡresponse�ֽ���
			OutputStream out = response.getOutputStream();
			byte[] b = new byte[1024];
			int len = -1;
			while((len=in.read(b))!=-1){//��������ѭ��д�뵽�������
				out.write(b,0,len);
			}
			out.close();
			in.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
}
