package com.wzj.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wzj.pojo.ApplicationForm;
import com.wzj.pojo.CompanyInfo;
import com.wzj.pojo.Log;
import com.wzj.pojo.Misdeeds;
import com.wzj.pojo.SysUser;
import com.wzj.service.application.impl.ApplicationFormService;
import com.wzj.service.application.impl.CompanyInfoService;
import com.wzj.service.impl.MisdeedsService;
import com.wzj.util.AddLog;
import com.wzj.util.UploadFileUtil;

@Controller
@RequestMapping("misdeeds")
public class MisdeedsController {
	@Autowired
	private MisdeedsService misService;
	@Autowired
	private CompanyInfoService companyInfoService;
	
	@RequestMapping("toMisdeeds")
	public String toMisdeed(int companyId,String branchId,String companyName,ModelMap model){
		CompanyInfo com=companyInfoService.getComById(companyId);
		AddLog.addLog(Log.QUERY,"��ѯ"+com.getEnterprisename()+"��������¼��Ϣ");
		model.addAttribute("item", companyId);
		model.addAttribute("item2", branchId);
		model.addAttribute("companyName", companyName);
		return "common/misdeeds";
	}
	
	@RequestMapping("getMisdeeds")
	@ResponseBody
	public Object getMisdeed(int companyId,Integer isSortCol_0,String sSortDir_0,int iDisplayStart,int iDisplayLength,String sEcho,String sSearch){
		String sort="";
		if(isSortCol_0!=null){
			switch(isSortCol_0){
				case 5:sort+="operation";break;
				case 4:sort+="operationType";break;
				case 3:sort+="department";break;
				case 2:sort+="person";break;
				case 1:sort+="time";break;
			}
		}else{
			sort+="id";
		}
		if("asc".equals(sSortDir_0)){
			sort+=" asc";
		}else{
			sort+=" desc";
		}
		//ҳ��
        int page_num = (iDisplayStart / iDisplayLength) + 1;
        //����ҳ���ÿҳ���Ƚ��н�ȡ
        PageHelper.startPage(page_num, iDisplayLength,sort);
        List<Misdeeds> misByCompanyId = misService.getMisByCompanyId(companyId);
        //���з�ҳ����
        PageInfo<Misdeeds> pageInfo = new PageInfo<Misdeeds>(misByCompanyId);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("sEcho", sEcho);
        map.put("aaData", pageInfo.getList());
        map.put("iTotalRecords", (int)pageInfo.getTotal());
        map.put("iTotalDisplayRecords", (int)pageInfo.getTotal());
        return map;
	}
	
	@RequestMapping("insertMis")
	public String insertMis(Misdeeds mis,ModelMap model,HttpServletRequest request){
		CompanyInfo com=companyInfoService.getComById(mis.getCompanyid());
		AddLog.addLog(Log.ADD,"��'"+com.getEnterprisename()+"'���������¼'"+mis.getQrecord()+"'");
		mis.setQtime(new Date());
		SimpleDateFormat formate=new SimpleDateFormat("yyyy-mm-dd");
		String sTime=formate.format(mis.getRtime());
		if(sTime.equals("1970-00-01"))//format��1970-01-01��Ϊ1970-00-01
			mis.setRtime(null);
		misService.insertMis(mis,request);
		model.addAttribute("item", mis.getCompanyid());
		model.addAttribute("item2", mis.getBranchid());
		model.addAttribute("companyName",com.getEnterprisename());
		return "common/misdeeds";
	}
	
	@RequestMapping("updateMis")
	public String updateMis(Misdeeds mis,ModelMap model,HttpServletRequest request){
		CompanyInfo com=companyInfoService.getComById(mis.getCompanyid());
		AddLog.addLog(Log.MODIFY,"�޸�������¼��'"+mis.getQrecord()+"'����Ϣ");
		misService.updateMis(mis,request);
		model.addAttribute("item", mis.getCompanyid());
		model.addAttribute("item2", mis.getBranchid());
		model.addAttribute("companyName", com.getEnterprisename());
		return "common/misdeeds";
	}
	
	@RequestMapping("getMisdeedById")
	@ResponseBody
	public Misdeeds getMisdeedById(int misdeedId){
		Misdeeds mis=misService.getMisById(misdeedId);
		AddLog.addLog(Log.QUERY,"��ѯ������¼��'"+mis.getQrecord()+"'����Ϣ");
		return misService.getMisById(misdeedId);
	}
	
	@RequestMapping("deleteMisdeedById")
	@ResponseBody
	public void deleteMisdeedById(int misdeedId){
		//ɾ���ļ�
		Misdeeds mis=misService.getMisById(misdeedId);
		AddLog.addLog(Log.DELETE,"ɾ��������¼��'"+mis.getQrecord()+"'����Ϣ");
		String path="";
		String p=System.getProperty("user.dir");
		String projectPath = "";
		if(!mis.getEnclosure().equals("")||mis.getEnclosure()!=null){
			try {
				projectPath = URLDecoder.decode(p, "UTF-8").replace("\\", "/");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			path=projectPath+"/src/main/webapp/"+mis.getEnclosure();
		}
		if(StringUtils.isNotBlank(path)){
			File file = new File(path);
			if(file.exists()){//ɾ���ļ�
				file.delete();
			}
		}
		misService.deleteMisdeedById(misdeedId);
	}
}
