package com.wzj.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wzj.pojo.ActiveUser;
import com.wzj.pojo.Division;
import com.wzj.pojo.Equipment;
import com.wzj.pojo.Log;
import com.wzj.service.impl.DivisionService;
import com.wzj.service.impl.EquipmentService;
import com.wzj.util.AddLog;
import com.wzj.util.ExportUtil;

@Controller
@RequestMapping("equipment")
public class EquipmentController {
	@Autowired
	private EquipmentService equipmentService;
	@Autowired
	private DivisionService divisionService;
	
	@RequestMapping("toEquip")
	public String toEquip(){
		AddLog.addLog(Log.QUERY,"查询所有设备信息");
		return "common/equInfo";
	}
	
	@SuppressWarnings({ "deprecation"})
	@RequestMapping("getAll")
	@ResponseBody
	public List<Equipment> getAll(HttpSession session){
		ActiveUser activeUser = (ActiveUser)session.getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		List<Equipment> all;
		if("0000".equals(branchId)){
			all = equipmentService.getAll();
		}else{
			all = equipmentService.selectByBId(branchId);
		}
		for (Equipment equipment : all) {
			 Date de =equipment.getCalibrationDate();
			 Date nextDate=equipment.getNextCailDate();
			 Calendar  cr = Calendar.getInstance(); 
			 if(de==null){
				 equipment.setNextCailDate(null);
			 }else{
				 cr.setTime(de);
				 int year = cr.get(Calendar.YEAR);
				 int year1 = year+equipment.getCalibrationCycle(); 
				 cr.set(Calendar.YEAR, year1); 
				 nextDate= cr.getTime();  
				 equipment.setNextCailDate(nextDate);
			 }
			 Division division=divisionService.getDivisionByCode(equipment.getBranchId());
			 equipment.setBranchId(division.getDivisionname());
		}
		return all;
	}
	
	@RequestMapping("getById")
	@ResponseBody
	public Equipment getById(int id){
		Equipment selectById = equipmentService.selectById(id);
		AddLog.addLog(Log.QUERY,"查询型号为'"+selectById.getModel()+"'的设备信息");
		return selectById;
	}
	
	@RequestMapping("update")
	@ResponseBody
	public int update(Equipment equ,HttpSession session){
		AddLog.addLog(Log.MODIFY,"修改型号为'"+equ.getModel()+"'的设备信息");
		return equipmentService.update(equ);
	}
	
	@RequestMapping("insert")
	@ResponseBody
	public int insert(Equipment equ,HttpSession session){
		ActiveUser au = (ActiveUser)session.getAttribute("activeUser");
		String branchId=au.getRamusCenter();
		Division div=divisionService.getDivisionByCode(branchId);
		AddLog.addLog(Log.ADD,"'"+div.getDivisionname()+"'添加型号为"+equ.getModel()+"的设备信息");
		equ.setBranchId(branchId);
		return equipmentService.insert(equ);
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public int delete(int id){
		Equipment selectById = equipmentService.selectById(id);
		AddLog.addLog(Log.DELETE,"删除型号为'"+selectById.getModel()+"'的设备信息");
		return equipmentService.deleteById(id);
	}
	
	@RequestMapping("exportRecord")
	public void exportRecord(HttpServletRequest request,HttpServletResponse response){
		AddLog.addLog(Log.EXPORT, "导出本中心的设备信息");
		ActiveUser au=(ActiveUser)request.getSession().getAttribute("activeUser");
		String branchId=au.getRamusCenter();
		//获取要导出的数据
		List<Equipment> lists = null;
		if("0000".equals(branchId)){
			lists=equipmentService.getAll();
		}else{
			lists=equipmentService.selectByBId(branchId);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<List<Object>> results=new ArrayList<List<Object>>();
		List<Object> tit=new ArrayList<Object>();
		tit.add("序号");
		tit.add("品牌型号");
		tit.add("出厂编号");
		tit.add("购买时间");
		tit.add("末次校准日期");
		tit.add("校准机构名称");
		tit.add("校准周期");
		tit.add("是否做设备期间核查");
		results.add(tit);
		int i=1;
		for(Equipment equip:lists){
			List<Object> result=new ArrayList<Object>();
			result.add(i++);
			result.add(equip.getBrand());
			result.add(equip.getModel());
			result.add(sdf.format(equip.getBuyDate()));
			result.add(sdf.format(equip.getCalibrationDate()));
			result.add(equip.getCalibrationName());
			result.add(equip.getCalibrationCycle());
			result.add(equip.isChecked()==true?"是":"否");
			results.add(result);
		}
		ExportUtil export = new ExportUtil(); 
		//获取导出的文件夹
		String path=request.getSession().getServletContext().getRealPath("export/equip");
		String name=au.getBranchName();
		String title=name+"的设备信息";
		String fileName=title+".xlsx";
		String filePath=path+fileName;
		try{
			export.exportRecord(title,results,filePath);
			export.exportFile(request, response, fileName, filePath);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
