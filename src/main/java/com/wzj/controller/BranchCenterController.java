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
		AddLog.addLog(Log.QUERY,"查询所有分支机构信息");
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
		}else{//分中心的话，查出一个加入到列表中
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
	public String insert(DivisionDTO divdto){//添加用户到数据库，返回的是用户ID
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
//		if(count<1){//判断分中心是否存在
//			AddLog.addLog(Log.ADD,"添加"+div.getDivisionname()+"分支机构");
//				divisionService.insert(div);//将新加的分中心添加到分中心数据表
//				for(int i=0;i<region.length;i++){//开始修改中间表
//					regioncode=region[i];
//					count1=branchCenterService.getDivRegionCount(regioncode);
//					if(count1>=1){//判断办事处是否存在
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
		if(count<1){//判断分中心是否存在
			AddLog.addLog(Log.ADD,"添加分支机构'"+div.getDivisionname()+"'");
				divisionService.insert(div);//将新加的分中心添加到分中心数据表
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
		AddLog.addLog(Log.DELETE,"删除分支机构'"+divisionByCode.getDivisionname()+"'的信息");
//		String divisioncode1="";
//		String divisioncode="";
//		String regioncode="";
//		int id=0;
//		List<DivRegionDTO> list=divisionRegionService.getDivRegByCode(code);//得到包含该中心编号的所有中间表对象
//		for(int i=0;i<list.size();i++){
//			divisioncode1=list.get(i).getDivisioncode();
//			regioncode=list.get(i).getRegioncode();
//			id=list.get(i).getId();
//			if(divisioncode1.indexOf(",")!=-1){//中间表的中心编号中包含逗号
//				divisioncode=divisioncode1.replace(","+code, "");
//				DivRegionDTO divregDTO=new DivRegionDTO(id,regioncode,divisioncode);
//				divisionRegionService.updateDivRegById(divregDTO);//修改中间表的中心编号
//			}else{
//				divisionService.deleteBranch(code);//删除中间表
//			}
//		}
		return divisionService.deleteBranchByCode(code);
	} 

	@RequestMapping("/toUpdateBranch")
	@ResponseBody
	public DivisionDTO toUpdateBranch(String code){
		Division divisionByCode = branchCenterService.getDivisionByCode(code);
		AddLog.addLog(Log.QUERY,"查询'"+divisionByCode.getDivisionname()+"'的信息");
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
			AddLog.addLog(Log.MODIFY,"修改分支机构'"+divdto.getName()+"'的信息");
			//先删除之前的数据
//			List<DivRegionDTO> list=divisionRegionService.getDivRegByCode(divdto.getBranchcode());//得到包含该中心编号的所有中间表对象
//			for(int i=0;i<list.size();i++){
//				divisioncode=list.get(i).getDivisioncode();
//				regioncode=list.get(i).getRegioncode();
//				id=list.get(i).getId();
//				if(divisioncode.indexOf(",")!=-1){//中间表的中心编号中包含逗号
//					divisioncode=divisioncode.replace(","+divdto.getBranchcode(), "");
//					DivRegionDTO divregDTO=new DivRegionDTO(id,regioncode,divisioncode);
//					divisionRegionService.updateDivRegById(divregDTO);//修改中间表的中心编号
//				}else{
//					divisionService.deleteBranch(divdto.getBranchcode());//删除中间表
//				}
//			}
			//再插入新的数据
//			String[] region1=divdto.getRegion();//再插入修改过后的
//			for(int i=0;i<region1.length;i++){//开始修改中间表
//				regioncode=region1[i];
//				count1=branchCenterService.getDivRegionCount(regioncode);
//				if(count1>=1){//判断办事处是否存在
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
//		获得分中心对应的地区编号
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
		AddLog.addLog(Log.EXPORT,"导出所有的分支机构信息");
		ExportUtil export = new ExportUtil();
		List<Object> branchs=(List<Object>)(Object)branchCenterService.findAll();
		
		//获取导出的文件夹
		//获取导出的文件夹
		String path = request.getSession().getServletContext().getRealPath("export/branch");
		File dir=new File(path);
		if(!(dir.exists())||!(dir.isDirectory()))
			dir.mkdirs();
		String title = "分支机构信息";
		String fileName = title+".xlsx";
		String filePath = path+"/"+fileName;
		String objPath="com.wzj.pojo.Division";
		String[] names=new String[]{"分中心名称","地址","邮编","联系人","联系电话(1)","联系电话(2)","传真"};
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
	//根据分支机构名称获取分支机构数量
	@RequestMapping("/countBranch")
	@ResponseBody
	public int countBranch(String name){
		return divisionService.countBranch(name);
	}
	
}