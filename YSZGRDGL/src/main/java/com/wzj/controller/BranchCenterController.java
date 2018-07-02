package com.wzj.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wzj.DTO.DivisionDTO;
import com.wzj.pojo.ActiveUser;
import com.wzj.pojo.Division;
import com.wzj.pojo.Log;
import com.wzj.pojo.Region;
import com.wzj.service.impl.BranchCenterService;
import com.wzj.service.impl.DivisionService;
import com.wzj.service.impl.RegionService;
import com.wzj.util.AddLog;
import com.wzj.util.ExportUtil;

@Controller
@RequestMapping("/branchCenter")
public class BranchCenterController {

	@Autowired
	private BranchCenterService branchCenterService;
	
	@Autowired
	private DivisionService divisionService;
	
	@Autowired
	private RegionService RegionService;
	
	@RequestMapping("/getBranchCenter")
	public String toEdit(){
		AddLog.addLog(Log.QUERY,"��ѯ���з�֧������Ϣ");
		return "branchCenter";
	}
	@RequestMapping("/findAll")
	@ResponseBody
	public List<Division> findAll(HttpSession session){
		ActiveUser user=(ActiveUser)session.getAttribute("activeUser");
		String center=user.getRamusCenter();
		List<Division> divisions=new ArrayList<Division>();
		if("0000".equals(center)){
			divisions=branchCenterService.findAll();
		}else{//�����ĵĻ������һ�����뵽�б���
			divisions.add(branchCenterService.findByDId(center));
		}
		return divisions;
	}
	
	@RequestMapping("/getAllRegion")
	@ResponseBody
	public List<Region> getAllRegion(){
		return RegionService.getAllRegion();
	}
	
	@RequestMapping("/getAllregionName")
	@ResponseBody
	public Region getAllregionName(String regionCode){
		return RegionService.getRegionByCode(regionCode);
	}
	@RequestMapping("insert")
	@ResponseBody
	public String insert(DivisionDTO divdto){//����û������ݿ⣬���ص����û�ID
//		String[] region = divdto.getRegion();
		Division div=new Division();
		div.setDivisioncode(divdto.getCode());
		div.setDivisionaddress(divdto.getAddress());
		div.setDivisionlinkman(divdto.getLinkman());
		div.setDivisionname(divdto.getName());
		div.setDivisionpostcode(divdto.getPostcode());
		div.setDivisonfax(divdto.getFax());
		div.setDivisonphone1(divdto.getPhone1());
		div.setDivisonphone2(divdto.getPhone2());
		int count=divisionService.getDivCountByCode(divdto.getCode());
//		int count1=0;
//		String regioncode="";
//		String divisioncode="";
//		int id=0;
//		if(count<1){//�жϷ������Ƿ����
//			AddLog.addLog(Log.ADD,"���"+div.getDivisionname()+"��֧����");
//				divisionService.insert(div);//���¼ӵķ�������ӵ����������ݱ�
//				for(int i=0;i<region.length;i++){//��ʼ�޸��м��
//					regioncode=region[i];
//					count1=branchCenterService.getDivRegionCount(regioncode);
//					if(count1>=1){//�жϰ��´��Ƿ����
//						DivRegionDTO divreg = divisionRegionService.getDivRegion(region[i]);
//						divisioncode=divreg.getDivisioncode()+","+divdto.getCode();
//						id=divreg.getId();
//						DivRegionDTO drDTO=new DivRegionDTO(id,regioncode,divisioncode);
//						branchCenterService.updateDivRegion(drDTO);
//					}else{
//						divisionService.setRegionId(divdto.getCode(),regioncode);
//					}
//				}
//			return div.getDivisioncode();
//		}else{
//			return "0";
//		}
		if(count<1){//�жϷ������Ƿ����
			AddLog.addLog(Log.ADD,"��ӷ�֧����'"+div.getDivisionname()+"'");
				divisionService.insert(div);//���¼ӵķ�������ӵ����������ݱ�
			return div.getDivisioncode();
		}else{
			return "0";
		}
}
	@RequestMapping("/deleteBranch")
	@ResponseBody
	@Transactional
	public int deleteBranch(String code){
		Division divisionByCode = divisionService.getDivisionByCode(code);
		AddLog.addLog(Log.DELETE,"ɾ����֧����'"+divisionByCode.getDivisionname()+"'����Ϣ");
//		String divisioncode1="";
//		String divisioncode="";
//		String regioncode="";
//		int id=0;
//		List<DivRegionDTO> list=divisionRegionService.getDivRegByCode(code);//�õ����������ı�ŵ������м�����
//		for(int i=0;i<list.size();i++){
//			divisioncode1=list.get(i).getDivisioncode();
//			regioncode=list.get(i).getRegioncode();
//			id=list.get(i).getId();
//			if(divisioncode1.indexOf(",")!=-1){//�м������ı���а�������
//				divisioncode=divisioncode1.replace(","+code, "");
//				DivRegionDTO divregDTO=new DivRegionDTO(id,regioncode,divisioncode);
//				divisionRegionService.updateDivRegById(divregDTO);//�޸��м������ı��
//			}else{
//				divisionService.deleteBranch(code);//ɾ���м��
//			}
//		}
		return divisionService.deleteBranchByCode(code);
	} 

	@RequestMapping("/toUpdateBranch")
	@ResponseBody
	public DivisionDTO toUpdateBranch(String code){
		Division divisionByCode = branchCenterService.getDivisionByCode(code);
		AddLog.addLog(Log.QUERY,"��ѯ'"+divisionByCode.getDivisionname()+"'����Ϣ");
		DivisionDTO divreg=new DivisionDTO();
		divreg.setAddress(divisionByCode.getDivisionaddress());
		divreg.setCode(divisionByCode.getDivisioncode());
		divreg.setFax(divisionByCode.getDivisionfax());
		divreg.setLinkman(divisionByCode.getDivisionlinkman());
		divreg.setName(divisionByCode.getDivisionname());
		divreg.setPhone1(divisionByCode.getDivisionphone1());
		divreg.setPhone2(divisionByCode.getDivisionphone2());
		divreg.setPostcode(divisionByCode.getDivisionpostcode());
		return divreg;
	}
	
	@RequestMapping("updateBranch")
	@ResponseBody
	public int updateBranch(DivisionDTO divdto){
		Boolean flag=false;
//		String divisioncode="";
//		String regioncode="";
//		int id=0;
		Division div=new Division();
		div.setDivisioncode(divdto.getCode());
		div.setDivisionaddress(divdto.getAddress());
		div.setDivisionlinkman(divdto.getLinkman());
		div.setDivisionname(divdto.getName());
		div.setDivisionpostcode(divdto.getPostcode());
		div.setDivisonfax(divdto.getFax());
		div.setDivisonphone1(divdto.getPhone1());
		div.setDivisonphone2(divdto.getPhone2());
		int count=divisionService.getDivCountByCode(divdto.getCode());
//		int count1=0;
		if(!divdto.getBranchcode().equals(divdto.getCode())){
			if(count>=1){
				flag=false;
			}else{
				flag=true;
			}
		}else{
			flag=true;
		}
		if(flag){
			AddLog.addLog(Log.MODIFY,"�޸ķ�֧����'"+divdto.getName()+"'����Ϣ");
			//��ɾ��֮ǰ������
//			List<DivRegionDTO> list=divisionRegionService.getDivRegByCode(divdto.getBranchcode());//�õ����������ı�ŵ������м�����
//			for(int i=0;i<list.size();i++){
//				divisioncode=list.get(i).getDivisioncode();
//				regioncode=list.get(i).getRegioncode();
//				id=list.get(i).getId();
//				if(divisioncode.indexOf(",")!=-1){//�м������ı���а�������
//					divisioncode=divisioncode.replace(","+divdto.getBranchcode(), "");
//					DivRegionDTO divregDTO=new DivRegionDTO(id,regioncode,divisioncode);
//					divisionRegionService.updateDivRegById(divregDTO);//�޸��м������ı��
//				}else{
//					divisionService.deleteBranch(divdto.getBranchcode());//ɾ���м��
//				}
//			}
			//�ٲ����µ�����
//			String[] region1=divdto.getRegion();//�ٲ����޸Ĺ����
//			for(int i=0;i<region1.length;i++){//��ʼ�޸��м��
//				regioncode=region1[i];
//				count1=branchCenterService.getDivRegionCount(regioncode);
//				if(count1>=1){//�жϰ��´��Ƿ����
//					DivRegionDTO divreg = divisionRegionService.getDivRegion(region1[i]);
//					divisioncode=divreg.getDivisioncode()+","+divdto.getCode();
//					id=divreg.getId();
//					DivRegionDTO drDTO=new DivRegionDTO(id,regioncode,divisioncode);
//					branchCenterService.updateDivRegion(drDTO);
//				}else{
//					divisionService.setRegionId(divdto.getCode(),regioncode);
//				}
//			}
			return divisionService.updateBranchByCode(divdto);
		}else{
			return 0;
		}
	}
	
	@RequestMapping("/getOffice")
	@ResponseBody
	public List<Region> getOffice(String code){
		List<Region> rlist=new ArrayList<Region>();
		String name="";
//		��÷����Ķ�Ӧ�ĵ������
		@SuppressWarnings("unchecked")
		List<String> list=branchCenterService.getregionCodeByCode(code);
		for(int i=0;i<list.size();i++){
			Region regionByCode = RegionService.getRegionByCode(list.get(i));
			if(regionByCode.getAllregionname()!=null){
				if(-1==regionByCode.getAllregionname().indexOf("\\")){
					name=regionByCode.getAllregionname();
					regionByCode.setAllregionname(name);
				}else{
					name=regionByCode.getAllregionname().replace("\\", "");
					regionByCode.setAllregionname(name);
				}
			}else{
				name="";
			}
			rlist.add(regionByCode);
		}
		return rlist;
	}
	
	@RequestMapping("exportRecord")
	public void exportRecord(HttpServletRequest request,HttpServletResponse response){
		AddLog.addLog(Log.EXPORT,"�������еķ�֧������Ϣ");
		ExportUtil export = new ExportUtil();
		List<Object> branchs=(List<Object>)(Object)branchCenterService.findAll();
		
		//��ȡ�������ļ���
		//��ȡ�������ļ���
		String path = request.getSession().getServletContext().getRealPath("export/branch");
		File dir=new File(path);
		if(!(dir.exists())||!(dir.isDirectory()))
			dir.mkdirs();
		String title = "��֧������Ϣ";
		String fileName = title+".xlsx";
		String filePath = path+"/"+fileName;
		String objPath="com.wzj.pojo.Division";
		String[] names=new String[]{"����������","��ַ","�ʱ�","��ϵ��","��ϵ�绰(1)","��ϵ�绰(2)","����"};
		String[] methods=new String[]{"getDivisionname","getDivisionaddress","getDivisionpostcode"
				,"getDivisionlinkman","getDivisionphone1","getDivisionphone2","getDivisionfax"};
		try{
			export.exportRecord(title,objPath,names,branchs,methods,filePath);
			export.exportFile(request, response, fileName, filePath);
		}catch(Exception e){
			e.printStackTrace();
			return;
		}
	}
	//���ݷ�֧�������ƻ�ȡ��֧��������
	@RequestMapping("/countBranch")
	@ResponseBody
	public int countBranch(String name){
		return divisionService.countBranch(name);
	}
	
}