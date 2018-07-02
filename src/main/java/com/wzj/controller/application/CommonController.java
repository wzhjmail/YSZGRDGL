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
		AddLog.addLog(Log.QUERY,"查询所有企业信息");
		ActiveUser activeUser=(ActiveUser) session.getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		model.addAttribute("branchId",branchId);
		return "common/enterpriseInfo";
	}
	
	@RequestMapping("toCancelledEnterpriseInfo")
	public String toCancelledEnterprises(HttpSession session,Model model){
		AddLog.addLog(Log.QUERY,"查询所有已注销的企业信息");
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
		//页码
        int page_num = (iDisplayStart / iDisplayLength) + 1;
        //根据页码和每页长度进行截取
        PageHelper.startPage(page_num, iDisplayLength,sort);
		//首先获取所属分支机构id
		ActiveUser activeUser=(ActiveUser) session.getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		//替换null值
		List<CompanyInfo> coms =null;
		if(StringUtils.isEmpty(sSearch)){//非查找功能
			coms = companyInfoService.getAll(branchId);
		}else{
			coms = companyInfoService.getAllByLike(branchId,"%"+sSearch+"%");
		}
        //进行分页配置
        PageInfo<CompanyInfo> pageInfo = new PageInfo<CompanyInfo>(coms);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("sEcho", sEcho);
        map.put("aaData", pageInfo.getList());
        map.put("iTotalRecords", (int)pageInfo.getTotal());
        map.put("iTotalDisplayRecords", (int)pageInfo.getTotal());
        return map;
	}
	
	//获取注销日期在该日期内的企业信息
	@RequestMapping("getInTime")
	@ResponseBody
	public Object getInTime(String sTime,String eTime) throws ParseException{
		return companyInfoService.getInTime(sTime,eTime);
	}
	//实现“注销”功能的action
	@RequestMapping("cancel")
	@ResponseBody
	public int cancel(int id){
		CompanyInfo com =companyInfoService.getComById(id);
		AddLog.addLog(Log.MODIFY,"注销'"+com.getEnterprisename()+"'的企业信息");
		//根据企业id获取对应业务表中的所有业务
		List<ApplicationForm> app=applicationFormService.getAppBycid(id);
		for (ApplicationForm applicationForm : app) {
			applicationForm.setCompanyId(id);
			applicationForm.setZhuxiao("已注销");
			applicationFormService.updateApp(applicationForm);
		}
		return companyInfoService.cancel(id);
	}
	//查找“注销”的企业信息
	/*@RequestMapping("getCancel")
	@ResponseBody
	public Object getCancel(HttpSession session){
		AddLog.addLog(Log.QUERY,"查看已注销的企业信息");
		String branchId=((ActiveUser)session.getAttribute("activeUser")).getRamusCenter();
		List<CompanyInfo> coms = companyInfoService.getCancel(branchId);
		List<CompanyInfo> lists=new ArrayList<>();
		for(CompanyInfo com:coms){//过滤null值为“”或者0
			CompanyInfo info=(CompanyInfo)FilterNull.filterNull(com);
			lists.add(info);
		}
		//return companyInfoService.getCancel();
		return lists;
	}*/
	
	@RequestMapping("getCancel")
	@ResponseBody
	public Object getCancel(Integer isSortCol_0,String sSortDir_0,int iDisplayStart,int iDisplayLength,String sEcho,String sSearch,HttpSession session){
		//页码
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
        //根据页码和每页长度进行截取
        PageHelper.startPage(page_num, iDisplayLength,sort);
		//首先获取所属分支机构id
		ActiveUser activeUser=(ActiveUser) session.getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		//替换null值
		List<CompanyInfo> coms = null;
		if(StringUtils.isEmpty(sSearch)){//非查找功能
			coms = companyInfoService.getCancel(branchId);
		}else{
			coms = companyInfoService.getCancel2(branchId,"%"+sSearch+"%");
		}
        //进行分页配置
        PageInfo<CompanyInfo> pageInfo = new PageInfo<CompanyInfo>(coms);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("sEcho", sEcho);
        map.put("aaData", pageInfo.getList());
        map.put("iTotalRecords", (int)pageInfo.getTotal());
        map.put("iTotalDisplayRecords", (int)pageInfo.getTotal());
        return map;
	}
	
	
	//实现新申请“激活”功能的action
	@RequestMapping("activate")
	@ResponseBody
	public void activate(int id){
		CompanyInfo company=companyInfoService.getComById(id);
		AddLog.addLog(Log.MODIFY,"激活'"+company.getEnterprisename()+"'的企业信息到新申请业务");
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
	
	//实现复认“激活”功能的action
	@RequestMapping("reActivate")
	@ResponseBody
	public void reAactivate(int id,HttpServletRequest request,HttpSession session) throws IOException{
		ActiveUser activeUser=(ActiveUser) session.getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		String titleNo=getNum("FR",branchId);
		CompanyInfo com =companyInfoService.getComById(id);
		AddLog.addLog(Log.MODIFY,"激活'"+com.getEnterprisename()+"'的企业信息到复认业务");
		int chan = companyInfoService.reActivate(id);
		if(chan>0){//如果状态修改成功，将数据插入到ys_title中
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
				
				//复制数据到数据库
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
	
	//实现变更“激活”功能的action
	@RequestMapping("reChange")
	@ResponseBody
	public void reChange(int id){
		CompanyInfo com =companyInfoService.getComById(id);
		AddLog.addLog(Log.MODIFY,"激活'"+com.getEnterprisename()+"'的企业信息到变更业务");
		int chan=companyInfoService.reChange(id);
		if(chan>0){//如果状态修改成功，将数据插入到ys_change中
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
	
	//图片显示
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
	//获取pdf路径
	@RequestMapping("getPath")
	@ResponseBody
	public Object getPath(String titleNo,String describe,HttpServletRequest request){
		UploadFile uploadFileByCode = applicationFormService.getUploadFile(titleNo,describe);
		String url="";
		Map<String,String> map=new HashMap<>();
		if(uploadFileByCode==null){
			url="未找到该文件!";
		}else{
			url=uploadFileByCode.getUprul();
		}
		map.put("url",url);
		return map;
	}
	//获取开始时间
		private Timestamp getStime(Date start){
			Date stime = null;
			if(start==null){//如果开始时间为null,则使用本年度的第一天作为开始时间
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
	//时间空值判断
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
	//高级查询
	@RequestMapping("supperSearch")
	@ResponseBody
	public Object supperSearch(Integer isSortCol_0,String sSortDir_0,EnterpriseSearchDTO esDTO,int iDisplayStart,int iDisplayLength,String sEcho,String sSearch,HttpSession session,String status) throws ParseException{
		AddLog.addLog(Log.QUERY,"检索企业信息");
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
		//页码
        int page_num = (iDisplayStart / iDisplayLength) + 1;
        //根据页码和每页长度进行截取
        PageHelper.startPage(page_num, iDisplayLength,sort);
		String branchId=esDTO.getBranchId();
		String appStartTime;//申请开始时间
		String appEndTime;//申请结束时间
		appStartTime=getTime(esDTO.getAppStartTime()).toString().substring(0, 10);
		appEndTime=getTime(esDTO.getAppEndTime()).toString().substring(0, 10);
		String certStartTime;//发证开始时间
		String certEndTime;//发证结束时间
		certStartTime=getTime(esDTO.getCertStartTime()).toString().substring(0, 10);
		certEndTime=getTime(esDTO.getCertEndTime()).toString().substring(0, 10);
		String expireStartTime;//到期开始时间
		String expireEndTime;//到期结
		expireStartTime=getTime(esDTO.getExpireStartTime()).toString().substring(0, 10);
		expireEndTime=getTime(esDTO.getExpireEndTime()).toString().substring(0, 10);
		String type=esDTO.getType();//业务类型  从前台传过来XB FR BG
		String enterpriseName=esDTO.getEnterpriseName();//企业名称
		String address=esDTO.getAddress();//所在地区
		String result= "";
		String term1=esDTO.getTerm1();//"or";//条件1
		if(StringUtils.isEmpty(sSearch)){//非查找功能
			if("0000".equals(branchNum)){
				if(branchId.equals("全部")||branchId.equals("")){
					result="select ID,branchId,TitleNo,branchName,EnterpriseName,CertAppDate,CertToDate,CreateDate,serial,status from ys_company where 1=1 ";
				}else{
					result="select ID,branchId,TitleNo,branchName,EnterpriseName,CertAppDate,CertToDate,CreateDate,serial,status from ys_company where branchId="+branchId+" ";
				}
			}else{
				result="select ID,branchId,TitleNo,branchName,EnterpriseName,CertAppDate,CertToDate,CreateDate,serial,status from ys_company where branchId="+branchNum+" ";
			}
		}else{
			if("0000".equals(branchNum)){
				if(branchId.equals("全部")||branchId.equals("")){
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
		
		String term2=esDTO.getTerm2();//条件2
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
		
		String term3=esDTO.getTerm3();//条件3
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
		String term4=esDTO.getTerm4();//条件4
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
		String term5=esDTO.getTerm5();//条件5
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
		String term6=esDTO.getTerm6();//条件6
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
			if(status.equals("已注销")){
				result+=" and zhuxiao like '%已注销%' ";
			}else{
				result+=" and (zhuxiao!='已注销' or zhuxiao is null)";
			}
		
		//替换null值
		List<CompanyInfo> coms = companyInfoService.getAllBySql(result);
        //进行分页配置
        PageInfo<CompanyInfo> pageInfo = new PageInfo<CompanyInfo>(coms);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("sEcho", sEcho);
        map.put("aaData", pageInfo.getList());
        map.put("iTotalRecords", (int)pageInfo.getTotal());
        map.put("iTotalDisplayRecords", (int)pageInfo.getTotal());
        return map;
	}
	//查询近三个月到期的企业
	@RequestMapping("recentSearch")
	@ResponseBody
	public Object recentSearch(Integer isSortCol_0,String sSortDir_0,int iDisplayStart,int iDisplayLength,String sEcho,String sSearch,HttpSession session) throws ParseException{
		AddLog.addLog(Log.QUERY,"检索近三个月到期的企业信息");
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
		//页码
        int page_num = (iDisplayStart / iDisplayLength) + 1;
        //根据页码和每页长度进行截取
        PageHelper.startPage(page_num, iDisplayLength,sort);
        Date dNow = new Date();   //当前时间
        Date dAfter = new Date();
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dNow);//把当前时间赋给日历
        calendar.add(calendar.MONTH, +3);  //设置为前3月
        dAfter = calendar.getTime();   //得到前3月的时间
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
        String defaultStartDate = sdf.format(dNow);    //格式化后3月的时间
        String defaultEndDate = sdf.format(dAfter); //格式化当前时间
		//获取近三个月到期的企业信息
        ActiveUser activeUser=(ActiveUser) session.getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		List<CompanyInfo> coms=null;
		if(StringUtils.isEmpty(sSearch)){//非查找功能
			coms=companyInfoService.getRecentCompany1(defaultStartDate,defaultEndDate,branchId);
		}else{
			coms=companyInfoService.getRecentCompany(defaultStartDate,defaultEndDate,branchId,"%"+sSearch+"%");
		}
//			getRecentCompany();
        //进行分页配置
        PageInfo<CompanyInfo> pageInfo = new PageInfo<CompanyInfo>(coms);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("sEcho", sEcho);
        map.put("aaData", pageInfo.getList());
        map.put("iTotalRecords", (int)pageInfo.getTotal());
        map.put("iTotalDisplayRecords", (int)pageInfo.getTotal());
        return map;
	}
	//根据区域检索企业信息
	@RequestMapping("checkArea")
	@ResponseBody
	public Object checkArea(Integer isSortCol_0,String sSortDir_0,String address,int iDisplayStart,int iDisplayLength,String sEcho,String sSearch,HttpSession session,String status) throws ParseException{
		AddLog.addLog(Log.QUERY,"检索地区是：'"+address+"'的企业信息");
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
		//页码
        int page_num = (iDisplayStart / iDisplayLength) + 1;
        //根据页码和每页长度进行截取
        PageHelper.startPage(page_num, iDisplayLength,sort);
		//获取用户所属分支机构
        ActiveUser activeUser=(ActiveUser) session.getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		List<CompanyInfo> coms = null;
		if(status.equals("已注销")){
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
        //进行分页配置
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
		AddLog.addLog(Log.EXPORT,"导出所有企业信息");
		ActiveUser activeUser=(ActiveUser) session.getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		//获取导出的文件夹
		String path = request.getSession().getServletContext().getRealPath("export/enterprise");
		String title = "企业信息";
		String fileName = title+".xlsx";
		String filePath = path+"/"+fileName;
		//表格名称及标题
		boolean result = applicationFormService.exportRecord(zhuxiao,title,filePath,branchId);
		if(!result){//出错
			return;
		}
		try{
			//根据不同的浏览器处理下载文件名乱码问题
			String userAgent = request.getHeader("User-Agent");
			//针对IE或者IE为内核的浏览器
			if(userAgent.contains("MSIE")||userAgent.contains("Trident")){
				fileName = URLEncoder.encode(fileName,"UTF-8");
			}else{//非IE浏览器的处理
				fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
			}
			//获取一个流
			InputStream in = new FileInputStream(new File(filePath));
			//设置下载的相应头
			response.setHeader("content-disposition","attachment;fileName="+fileName);
			response.setCharacterEncoding("UTF-8");
			//获取response字节流
			OutputStream out = response.getOutputStream();
			byte[] b = new byte[1024];
			int len = -1;
			while((len=in.read(b))!=-1){//将输入流循环写入到输出流】
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
	    	case 1 : //新办申请
				//获取导出的文件夹
	    		title = "XB";
				fileName = app.getTitleno()+"申请"+".pdf";
				//filePath = path+"/"+title+"/"+app.getTitleno()+"/"+fileName;
				filePath = path+"/"+title+"/"+app.getTitleno();
				File dir1=new File(filePath);
				if(!dir1.exists()&&!dir1.isDirectory()){//如果文件不存在则创建文件.注意mkdir和mkdirs二者的区别
					dir1.mkdirs();
				}
				filePath = filePath+"/"+fileName;
				pdf.createPDFApplication(app,filePath,printEquipments,testingEquips);
	    		break; 
	    	case 2 : //复认申请
	    		//获取导出的文件夹
				title = "FR";
				fileName = app.getTitleno()+"申请"+".pdf";
				//filePath = path+"/"+title+"/"+fileName;
				filePath = path+"/"+title+"/"+app.getTitleno();
				File dir2=new File(filePath);
				if(!dir2.exists()&&!dir2.isDirectory()){//如果文件不存在则创建文件.注意mkdir和mkdirs二者的区别
					dir2.mkdirs();
				}
				filePath = filePath+"/"+fileName;
				pdf.createPDFRecognition(app,filePath,printEquipments,testingEquips);
	    		break; 
	    	case 3 : //变更申请
	    		//获取导出的文件夹
				title = "BG";
				fileName = app.getTitleno()+"申请"+".pdf";
				//filePath = path+"/"+title+"/"+fileName;
				filePath = path+"/"+title+"/"+app.getTitleno();
				File dir3=new File(filePath);
				if(!dir3.exists()&&!dir3.isDirectory()){//如果文件不存在则创建文件.注意mkdir和mkdirs二者的区别
					dir3.mkdirs();
				}
				filePath = filePath+"/"+fileName;
				//pdf.createPDFRecognition(app,filePath);
	    		break; 
	    	default : 
	    		
		}
		//表格名称及标题
		try{
			//根据不同的浏览器处理下载文件名乱码问题
			String userAgent = request.getHeader("User-Agent");
			//针对IE或者IE为内核的浏览器
			if(userAgent.contains("MSIE")||userAgent.contains("Trident")){
				fileName = URLEncoder.encode(fileName,"UTF-8");
			}else{//非IE浏览器的处理
				fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
			}
			//获取一个流
			InputStream in = new FileInputStream(new File(filePath));
			//设置下载的相应头
			response.setHeader("content-disposition","attachment;fileName="+fileName);
			response.setCharacterEncoding("UTF-8");
			//获取response字节流
			OutputStream out = response.getOutputStream();
			byte[] b = new byte[1024];
			int len = -1;
			while((len=in.read(b))!=-1){//将输入流循环写入到输出流】
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
	    	case 1 : //新办评审
				//获取导出的文件夹
				title = "XB";
				fileName = rev.getTitleno()+"评审"+".pdf";
				filePath = path+"/"+title+"/"+rev.getTitleno();
				File dir1=new File(filePath);
				if(!dir1.exists()&&!dir1.isDirectory()){//如果文件不存在则创建文件.注意mkdir和mkdirs二者的区别
					dir1.mkdirs();
				}
				filePath = filePath+"/"+fileName;
				pdf.createPDFPS(rev,filePath,evalua);
	    		break; 
	    	case 2 : //复认评审
				//获取导出的文件夹
				title = "FR";
				fileName = rev.getTitleno()+"评审"+".pdf";
				filePath = path+"/"+title+"/"+rev.getTitleno();
				File dir2=new File(filePath);
				if(!dir2.exists()&&!dir2.isDirectory()){//如果文件不存在则创建文件.注意mkdir和mkdirs二者的区别
					dir2.mkdirs();
				}
				filePath = filePath+"/"+fileName;
				pdf.createPDFPS(rev,filePath,evalua);
	    		break; 
	    		
	    	case 3 : //新办评审+意见
	    		//获取导出的文件夹
				title = "XB";
				fileName = rev.getTitleno()+"评审"+".pdf";
				filePath = path+"/"+title+"/"+rev.getTitleno();
				File dir3=new File(filePath);
				if(!dir3.exists()&&!dir3.isDirectory()){//如果文件不存在则创建文件.注意mkdir和mkdirs二者的区别
					dir3.mkdirs();
				}
				filePath = filePath+"/"+fileName;
				pdf.createPDFPSComment(rev,filePath);
	    		break; 
	    	case 4 : //复认评审+意见
	    		//获取导出的文件夹
				title = "FR";
				fileName = rev.getTitleno()+"评审"+".pdf";
				filePath = path+"/"+title+"/"+rev.getTitleno();
				File dir4=new File(filePath);
				if(!dir4.exists()&&!dir4.isDirectory()){//如果文件不存在则创建文件.注意mkdir和mkdirs二者的区别
					dir4.mkdirs();
				}
				filePath = filePath+"/"+fileName;
				pdf.createPDFPSComment(rev,filePath);
	    		break; 
	    	default : 
	    		
		}
	}
	
	//将生成的pdf保存到相应的路径下
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
	    	case 1 : //新办申请
				//获取导出的文件夹
				title = "XB";
				fileName = app.getTitleno()+"申请"+".pdf";
				filePath = path+"/"+title+"/"+app.getTitleno();
				File dir1=new File(filePath);
				if(!dir1.exists()&&!dir1.isDirectory()){//如果文件不存在则创建文件.注意mkdir和mkdirs二者的区别
					dir1.mkdirs();
				}
				filePath = filePath+"/"+fileName;
				pdf.createPDFApplication(app,filePath,printEquipments,testingEquips);
	    		break; 
	    	case 2 : //复认申请
	    		//获取导出的文件夹
				title = "FR";
				fileName = app.getTitleno()+"申请"+".pdf";
				filePath = path+"/"+title+"/"+app.getTitleno();
				File dir2=new File(filePath);
				if(!dir2.exists()&&!dir2.isDirectory()){//如果文件不存在则创建文件.注意mkdir和mkdirs二者的区别
					dir2.mkdirs();
				}
				filePath = filePath+"/"+fileName;
				pdf.createPDFRecognition(app,filePath,printEquipments,testingEquips);
	    		break; 
	    	case 3 : //变更申请
	    		//获取导出的文件夹
				title = "BG";
				fileName = app.getTitleno()+"申请"+".pdf";
				filePath = path+"/"+title+"/"+app.getTitleno();
				File dir3=new File(filePath);
				if(!dir3.exists()&&!dir3.isDirectory()){//如果文件不存在则创建文件.注意mkdir和mkdirs二者的区别
					dir3.mkdirs();
				}
				filePath = filePath+"/"+fileName;
				//pdf.createPDFRecognition(app,filePath);
	    		break; 
	    	default : 
		}
	}
	
	//下载pdf
	@RequestMapping("downPdf")
	public void downPdf(int type,String titleNo,HttpServletRequest request,HttpServletResponse response){
		String path = request.getSession().getServletContext().getRealPath("/upload");
		String title = "";
		String fileName = "";
		String filePath = "";
		switch(type){
	    	case 1 : //新办申请
				//获取导出的文件夹
	    		AddLog.addLog(Log.EXPORT,"下载流水号为'"+titleNo+"'的新办业务申请表信息");
				title = "XB";
				fileName = titleNo+"申请"+".pdf";
	    		break; 
	    	case 2 : //复认申请
	    		//获取导出的文件夹
	    		AddLog.addLog(Log.EXPORT,"下载流水号为"+titleNo+"的复认业务申请表信息");
				title = "FR";
				fileName = titleNo+"申请"+".pdf";
	    		break; 
	    	case 3 : //变更申请
	    		//获取导出的文件夹
	    		AddLog.addLog(Log.EXPORT,"下载流水号为"+titleNo+"的变更业务申请表信息");
				title = "BG";
				fileName = titleNo+"申请"+".pdf";
	    		break; 
	    	case 4 : //新办评审
	    		//获取导出的文件夹
	    		AddLog.addLog(Log.EXPORT,"下载流水号为"+titleNo+"的新办业务评审表信息");
				title = "XB";
				fileName = titleNo+"评审"+".pdf";
	    		break; 
	    	case 5 : //复认评审
	    		//获取导出的文件夹
	    		AddLog.addLog(Log.EXPORT,"下载流水号为"+titleNo+"的复认业务评审表信息");
				title = "FR";
				fileName = titleNo+"评审"+".pdf";
	    		break; 
	    	case 6 : //变更申请表
	    		//获取导出的文件夹
	    		AddLog.addLog(Log.EXPORT,"下载流水号为"+titleNo+"的变更业务评审表信息");
				title = "BG";
				fileName = titleNo+"变更"+".pdf";
	    		break; 
	    	default : 
		}
		filePath = path+"/"+title+"/"+titleNo+"/"+fileName;
		//表格名称及标题
		try{
			//根据不同的浏览器处理下载文件名乱码问题
			String userAgent = request.getHeader("User-Agent");
			//针对IE或者IE为内核的浏览器
			if(userAgent.contains("MSIE")||userAgent.contains("Trident")){
				fileName = URLEncoder.encode(fileName,"UTF-8");
			}else{//非IE浏览器的处理
				fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
			}
			//获取一个流
			File fil=new File(filePath);
			if(fil.exists()){//如果文件存在
				InputStream in = new FileInputStream(fil);
				//设置下载的相应头
				response.setHeader("content-disposition","attachment;fileName="+fileName);
				response.setCharacterEncoding("UTF-8");
				//获取response字节流
				OutputStream out = response.getOutputStream();
				byte[] b = new byte[1024];
				int len = -1;
				while((len=in.read(b))!=-1){//将输入流循环写入到输出流】
					out.write(b,0,len);
				}
				out.close();
				in.close();
			}else{
				response.setHeader("Content-type", "text/html;charset=UTF-8");  
		        String data = "文件不存在。如需帮助，请联系管理员！";  
		        OutputStream ps = response.getOutputStream();  
		        ps.write(data.getBytes("UTF-8"));  
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	//下载盖章后的表单  
	@RequestMapping("downSeal")
	public void downSeal(int type,String titleNo,String appId,HttpServletRequest request,HttpServletResponse response){
		String path = request.getSession().getServletContext().getRealPath("/");
//		String title = "";
		String fileName = "";
		String filePath = "";
		String inputPath="";
		switch(type){
	    	case 1 : //新办申请
	    		fileName = titleNo+"申请(盖章)"+".pdf";
	    		inputPath=uploadFileService.getPathByCode("xb"+appId);
	    		filePath = path+"/"+inputPath;
	    		break; 
	    	case 2 : //评审表
	    		fileName = titleNo+"评审(盖章)"+".pdf";
	    		inputPath=uploadFileService.getPathByCode("xb_ps_"+appId);
	    		filePath = path+"/"+inputPath;
	    		break; 
	    	default : 
		}
		//表格名称及标题
		try{
			//根据不同的浏览器处理下载文件名乱码问题
			String userAgent = request.getHeader("User-Agent");
			//针对IE或者IE为内核的浏览器
			if(userAgent.contains("MSIE")||userAgent.contains("Trident")){
				fileName = URLEncoder.encode(fileName,"UTF-8");
			}else{//非IE浏览器的处理
				fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
			}
			//获取一个流
			File fil=new File(filePath);
			if(fil.exists()){//如果文件存在
				InputStream in = new FileInputStream(fil);
				//设置下载的相应头
				response.setHeader("content-disposition","attachment;fileName="+fileName);
				response.setCharacterEncoding("UTF-8");
				//获取response字节流
				OutputStream out = response.getOutputStream();
				byte[] b = new byte[1024];
				int len = -1;
				while((len=in.read(b))!=-1){//将输入流循环写入到输出流】
					out.write(b,0,len);
				}
				out.close();
				in.close();
			}else{
				response.setHeader("Content-type", "text/html;charset=UTF-8");  
		        String data = "文件不存在。如需帮助，请联系管理员！";  
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
	    	case 1 : //证书
				//获取导出的文件夹
	    		title = app.getTitleno().substring(0,2);
				fileName = app.getTitleno()+Print.companyCert+".pdf";
				filePath = path+"/"+title+"/"+app.getTitleno();
				File dir1=new File(filePath);
				if(!dir1.exists()&&!dir1.isDirectory()){//如果文件不存在则创建文件.注意mkdir和mkdirs二者的区别
					dir1.mkdirs();
				}
				filePath = filePath+"/"+fileName;
				pdf1.createPDFComCert(app,filePath);
	    		break; 
	    	case 2 : //副本
	    		//获取导出的文件夹
	    		title = app.getTitleno().substring(0,2);
				fileName = app.getTitleno()+Print.companyCertFB+".pdf";
				filePath = path+"/"+title+"/"+app.getTitleno();
				File dir2=new File(filePath);
				if(!dir2.exists()&&!dir2.isDirectory()){//如果文件不存在则创建文件.注意mkdir和mkdirs二者的区别
					dir2.mkdirs();
				}
				filePath = filePath+"/"+fileName;
				pdf1.createPDFComCertFB(app,filePath);
	    		break; 
	    	default : 
	    		
		}
		//表格名称及标题
		try{
			//根据不同的浏览器处理下载文件名乱码问题
			String userAgent = request.getHeader("User-Agent");
			//针对IE或者IE为内核的浏览器
			if(userAgent.contains("MSIE")||userAgent.contains("Trident")){
				fileName = URLEncoder.encode(fileName,"UTF-8");
			}else{//非IE浏览器的处理
				fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
			}
			//获取一个流
			File file=new File(filePath);
			if(file.exists()){
			InputStream in = new FileInputStream(new File(filePath));
			//设置下载的相应头
			response.setHeader("content-disposition","attachment;fileName="+fileName);
			response.setCharacterEncoding("UTF-8");
			//获取response字节流
			OutputStream out = response.getOutputStream();
			byte[] b = new byte[1024];
			int len = -1;
			while((len=in.read(b))!=-1){//将输入流循环写入到输出流】
				out.write(b,0,len);
			}
			out.close();
			in.close();
			}else{
				response.setHeader("Content-type", "text/html;charset=UTF-8");  
		        String data = "文件不存在。如需帮助，请联系管理员！";  
		        OutputStream ps = response.getOutputStream();  
		        ps.write(data.getBytes("UTF-8"));
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
	//打印寄送信息
	@RequestMapping("/exportPDFSendmsg")
	public void exportPDFSendmsg(ExpressInfo express,String companyName,HttpServletRequest request,HttpServletResponse response, String sendUnit, String reciveUnit){
		
		PdfUtil pdf = new PdfUtil();
		String path = request.getSession().getServletContext().getRealPath("/upload");
		String title = "";
		
		String fileName = "";
		String filePath = "";
		
		//获取导出的文件夹
		title = "寄送信息";
		fileName = express.getNumber()+"寄送信息"+".pdf";
		filePath = path+"/"+title+"/"+companyName;
		File dir1=new File(filePath);
		if(dir1.exists()){//删除文件
			dir1.delete();
		}
		if(!dir1.exists()&&!dir1.isDirectory()){//如果文件不存在则创建文件.注意mkdir和mkdirs二者的区别
			dir1.mkdirs();
		}
		filePath = filePath+"/"+fileName;
		pdf.createPDFSendmsg(express,filePath,companyName,sendUnit,reciveUnit);
		//表格名称及标题
		try{
			//根据不同的浏览器处理下载文件名乱码问题
			String userAgent = request.getHeader("User-Agent");
			//针对IE或者IE为内核的浏览器
			if(userAgent.contains("MSIE")||userAgent.contains("Trident")){
				fileName = URLEncoder.encode(fileName,"UTF-8");
			}else{//非IE浏览器的处理
				fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
			}
			//获取一个流
			File file=new File(filePath);
			if(file.exists()){
			InputStream in = new FileInputStream(new File(filePath));
			//设置下载的相应头
			response.setHeader("content-disposition","attachment;fileName="+fileName);
			response.setCharacterEncoding("UTF-8");
			//获取response字节流
			OutputStream out = response.getOutputStream();
			byte[] b = new byte[1024];
			int len = -1;
			while((len=in.read(b))!=-1){//将输入流循环写入到输出流】
				out.write(b,0,len);
			}
			out.close();
			in.close();
			}else{
				response.setHeader("Content-type", "text/html;charset=UTF-8");  
		        String data = "文件不存在。如需帮助，请联系管理员！";  
		        OutputStream ps = response.getOutputStream();  
		        ps.write(data.getBytes("UTF-8"));
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
	//批量下载
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
			//企业编号
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
		    	case 1 : //证书
		    		AddLog.addLog(Log.EXPORT,"下载'"+app.getEnterprisename()+"'的企业证书");
		    		title = app.getTitleno().substring(0,2);
					fileName = app.getTitleno()+"企业证书"+".pdf";
					filePath = path+"/"+title+"/"+app.getTitleno();
					File dir1=new File(filePath);
					if(!dir1.exists()){//如果文件不存在则创建文件.注意mkdir和mkdirs二者的区别
						dir1.mkdirs();
					}
					filePath=filePath+"/企业证书.pdf";
					File file1=new File(filePath);
					if(file1.exists()){
						file1.delete();
					}
					try {//创建文件
						file1.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
					pdf.createPDFComCert(app,filePath);
		    		break; 
		    	case 2 : //副本
		    		//获取导出的文件夹
		    		AddLog.addLog(Log.EXPORT,"下载"+app.getEnterprisename()+"的企业证书副本");
		    		title = app.getTitleno().substring(0,2);
					fileName = app.getTitleno()+"企业证书副本.pdf";
					filePath = path+"/"+title+"/"+app.getTitleno();
					File dir2=new File(filePath);
					if(!dir2.exists()){//如果文件不存在则创建文件.注意mkdir和mkdirs二者的区别
						dir2.mkdirs();
					}
					filePath = filePath+"/企业证书副本.pdf";;
					File file2=new File(filePath);
					if(file2.exists()){
						file2.delete();
					}
					try {//创建文件
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
			resultFilePath=path+"\\企业证书\\";
			File dir3=new File(resultFilePath);
			if(dir3.exists()){
				dir3.delete();
			}
			if(!dir3.exists()&&!dir3.isDirectory()){//如果文件不存在则创建文件.注意mkdir和mkdirs二者的区别
				dir3.mkdirs();
			}
			fileNames=names+"企业证书.pdf";
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
			resultFilePath=path+"\\企业证书副本\\";
			File dir4=new File(resultFilePath);
			if(dir4.exists()){
				dir4.delete();
			}
			if(!dir4.exists()&&!dir4.isDirectory()){//如果文件不存在则创建文件.注意mkdir和mkdirs二者的区别
				dir4.mkdirs();
			}
			fileNames=names+"企业证书副本.pdf";
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
			//根据不同的浏览器处理下载文件名乱码问题  "d:\\merge.pdf"
			String userAgent = request.getHeader("User-Agent");
			//针对IE或者IE为内核的浏览器
			if(userAgent.contains("MSIE")||userAgent.contains("Trident")){
				resultFileName = URLEncoder.encode(resultFileName,"UTF-8");
			}else{//非IE浏览器的处理
				resultFileName = new String(resultFileName.getBytes("UTF-8"),"ISO-8859-1");
			}
			if(resultFileName.equals(""))
				if(type==1)
					resultFileName=new String("证书.pdf".getBytes("UTF-8"),"ISO-8859-1");
				else if(type==2)
					resultFileName=new String("证书副本.pdf".getBytes("UTF-8"),"ISO-8859-1");
			File file=new File(resultFilePath);//获取一个流
			if(file.exists()){
				InputStream in = new FileInputStream(new File(resultFilePath));
				//设置下载的相应头
				response.setHeader("content-disposition","attachment;fileName="+resultFileName);
				response.setCharacterEncoding("UTF-8");//获取response字节流
				OutputStream out = response.getOutputStream();
				byte[] b = new byte[1024];
				int len = -1;
				while((len=in.read(b))!=-1){//将输入流循环写入到输出流】
					out.write(b,0,len);
				}
				out.close();
				in.close();
			}else{
				response.setHeader("Content-type", "text/html;charset=UTF-8");  
		        String data = "文件不存在。如需帮助，请联系管理员！";  
		        OutputStream ps = response.getOutputStream();  
		        ps.write(data.getBytes("UTF-8"));
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	//根据XB、FR、BG获取当前对应业务的对应值
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
		
		//根据XB、FR、BG获取当前对应业务的对应值
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

		//查看企业信息
		@RequestMapping("view")
		public String view(HttpServletRequest request,Model model) {
			String id1=request.getParameter("id");
			int id=Integer.parseInt(id1);
			CompanyInfo comById = companyInfoService.getComById(id);
			AddLog.addLog(Log.QUERY,"查询'"+comById.getEnterprisename()+"'的信息");
			//根据企业表中的业务编号得到业务表中相应的最后一条业务
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
//将生成的pdf保存到相应的路径下
@RequestMapping("/savebgApp")
public void savebgApp(int type, Integer id, HttpServletRequest request) {
		PdfUtil pdf = new PdfUtil();
		FormChange form=formChangeService.getChangeFormById(id);
		ApplicationForm af=applicationFormService.getAppFormById(form.getPid());
		//证书号
		int certNo=af.getCertNo();
		String path = request.getSession().getServletContext().getRealPath("/upload");
		String title = "";
		String fileName = "";
		String filePath = "";
		switch(type){
	    	case 1 : //变更申请
	    		//获取导出的文件夹
				title = "BG";
				fileName = form.getTitleno()+"申请"+".pdf";
				filePath = path+"/"+title+"/"+form.getTitleno();
				
				File dir1=new File(filePath);
				if(!dir1.exists()&&!dir1.isDirectory()){//如果文件不存在则创建文件.注意mkdir和mkdirs二者的区别
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
		AddLog.addLog(Log.QUERY,"获得'"+com.getEnterprisename()+"'的信息");
		model.addAttribute("id",id);
		model.addAttribute("item",com);
		return "common/updateEnterpriseInfo";
	}
	@RequestMapping("saveCompany")
	public String insertReturn(CompanyInfo com,HttpServletRequest request,String cId){//退回任务的提交。先更新信息，在执行任务
		String companyName=com.getEnterprisename();
		AddLog.addLog(Log.MODIFY,"修改'"+companyName+"'的信息");
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
	//获取企业编号
	@RequestMapping("getComId")
	@ResponseBody
	public int getComId(String enterpriseName){
		CompanyInfo com =companyInfoService.getComByName(enterpriseName);
//		AddLog.addLog(Log.MODIFY,"获取'"+com.getEnterprisename()+"'的信息");
		return com.getId();
	}
	//导出Excel
	@RequestMapping("/exportExcel")
	public void exportExcel(HttpServletRequest request,HttpServletResponse response,EnterpriseSearchDTO esDTO,String status,String sSearch,HttpSession session) throws ParseException{
		AddLog.addLog(Log.EXPORT,"导出企业检索结果");
		ActiveUser activeUser=(ActiveUser) session.getAttribute("activeUser");
		String branchNum=activeUser.getRamusCenter();
		String branchId=esDTO.getBranchId();
		String appStartTime;//申请开始时间
		String appEndTime;//申请结束时间
		appStartTime=getTime(esDTO.getAppStartTime()).toString().substring(0, 10);
		appEndTime=getTime(esDTO.getAppEndTime()).toString().substring(0, 10);
		String certStartTime;//发证开始时间
		String certEndTime;//发证结束时间
		certStartTime=getTime(esDTO.getCertStartTime()).toString().substring(0, 10);
		certEndTime=getTime(esDTO.getCertEndTime()).toString().substring(0, 10);
		String expireStartTime;//到期开始时间
		String expireEndTime;//到期结
		expireStartTime=getTime(esDTO.getExpireStartTime()).toString().substring(0, 10);
		expireEndTime=getTime(esDTO.getExpireEndTime()).toString().substring(0, 10);
		String type=esDTO.getType();//业务类型  从前台传过来XB FR BG
		String enterpriseName=esDTO.getEnterpriseName();//企业名称
		String address=esDTO.getAddress();//所在地区
		String result= "";
		String term1=esDTO.getTerm1();//"or";//条件1
		if(StringUtils.isEmpty(sSearch)){//非查找功能
			if("0000".equals(branchNum)){
				if(branchId.equals("全部")||branchId.equals("")){
					result="select * from ys_company where 1=1 ";
				}else{
					result="select * from ys_company where branchId="+branchId+" ";
				}
			}else{
				result="select * from ys_company where branchId="+branchNum+" ";
			}
		}else{
			if("0000".equals(branchNum)){
				if(branchId.equals("全部")||branchId.equals("")){
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
		
		String term2=esDTO.getTerm2();//条件2
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
		
		String term3=esDTO.getTerm3();//条件3
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
		String term4=esDTO.getTerm4();//条件4
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
		String term5=esDTO.getTerm5();//条件5
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
		String term6=esDTO.getTerm6();//条件6
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
			if(status.equals("已注销")){
				result+=" and zhuxiao like '%已注销%' order by CreateDate desc";
			}else{
				result+=" and (zhuxiao!='已注销' or zhuxiao is null) order by CreateDate desc";
			}
		
		List<CompanyInfo> coms = companyInfoService.getAllBySql(result);
		
		//获取导出的文件夹
		String path = request.getSession().getServletContext().getRealPath("./");
		String title = "企业信息";
		String fileName = title+".xlsx";
		String filePath = path+"/export/enterpriseResult/"+fileName;
		//表格名称及标题
		boolean exportresult = companyInfoService.exportExcel(title,filePath,coms);
		if(!exportresult){//出错
			return;
		}
		try{
			//根据不同的浏览器处理下载文件名乱码问题
			String userAgent = request.getHeader("User-Agent");
			//针对IE或者IE为内核的浏览器
			if(userAgent.contains("MSIE")||userAgent.contains("Trident")){
				fileName = URLEncoder.encode(fileName,"UTF-8");
			}else{//非IE浏览器的处理
				fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
			}
			//获取一个流
			InputStream in = new FileInputStream(new File(filePath));
			//设置下载的相应头
			response.setHeader("content-disposition","attachment;fileName="+fileName);
			response.setCharacterEncoding("UTF-8");
			//获取response字节流
			OutputStream out = response.getOutputStream();
			byte[] b = new byte[1024];
			int len = -1;
			while((len=in.read(b))!=-1){//将输入流循环写入到输出流】
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
	//根据近三个月到期导出Excel
	@RequestMapping("/exportExcelByRecent")
	public void exportExcelByRecent(HttpServletRequest request,HttpServletResponse response,EnterpriseSearchDTO esDTO,String status,String sSearch,HttpSession session) throws ParseException{
		AddLog.addLog(Log.EXPORT,"导出企业检索结果");
		Date dNow = new Date();   //当前时间
        Date dAfter = new Date();
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dNow);//把当前时间赋给日历
        calendar.add(calendar.MONTH, +3);  //设置为前3月
        dAfter = calendar.getTime();   //得到前3月的时间
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
        String defaultStartDate = sdf.format(dNow);    //格式化后3月的时间
        String defaultEndDate = sdf.format(dAfter); //格式化当前时间
		//获取近三个月到期的企业信息
        ActiveUser activeUser=(ActiveUser) session.getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		List<CompanyInfo> coms=null;
		if(StringUtils.isEmpty(sSearch)){//非查找功能
			coms=companyInfoService.getRecentCompany1(defaultStartDate,defaultEndDate,branchId);
		}else{
			coms=companyInfoService.getRecentCompany(defaultStartDate,defaultEndDate,branchId,"%"+sSearch+"%");
		}
		
		//获取导出的文件夹
		String path = request.getSession().getServletContext().getRealPath("./");
		String title = "企业信息";
		String fileName = title+".xlsx";
		String filePath = path+"/export/enterpriseResult/"+fileName;
		//表格名称及标题
		boolean exportresult = companyInfoService.exportExcel(title,filePath,coms);
		if(!exportresult){//出错
			return;
		}
		try{
			//根据不同的浏览器处理下载文件名乱码问题
			String userAgent = request.getHeader("User-Agent");
			//针对IE或者IE为内核的浏览器
			if(userAgent.contains("MSIE")||userAgent.contains("Trident")){
				fileName = URLEncoder.encode(fileName,"UTF-8");
			}else{//非IE浏览器的处理
				fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
			}
			//获取一个流
			InputStream in = new FileInputStream(new File(filePath));
			//设置下载的相应头
			response.setHeader("content-disposition","attachment;fileName="+fileName);
			response.setCharacterEncoding("UTF-8");
			//获取response字节流
			OutputStream out = response.getOutputStream();
			byte[] b = new byte[1024];
			int len = -1;
			while((len=in.read(b))!=-1){//将输入流循环写入到输出流】
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
