package com.wzj.controller.application;

import com.wzj.pojo.Log;
import com.wzj.pojo.PrintEquipment;
import com.wzj.service.impl.PrintEquipmentService;
import com.wzj.util.AddLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller("application/printEquipment")
@RequestMapping("application/printEquipment")
public class PrintEquipmentController {
    @Autowired
    private PrintEquipmentService printEquipmentService;

    @RequestMapping("insert")
    @ResponseBody
    public int add(PrintEquipment record){
//    	printEquipmentService.deleteByCompanyName(record.getCompanyName());
    	AddLog.addLog(Log.ADD,"向'"+record.getCompanyName()+"'添加印刷设备'"+record.getPrintName()+"'的信息");
        printEquipmentService.insert(record);
        return record.getId();
    }

    @RequestMapping("getById")
    @ResponseBody
    public PrintEquipment getById(int id){
        return printEquipmentService.getById(id);
    }

    @RequestMapping("updateById")
    @ResponseBody
    public int updateById(PrintEquipment record){
    	int pringtId=record.getId();
    	PrintEquipment rc=printEquipmentService.getById(pringtId);
    	AddLog.addLog(Log.MODIFY,"修改'"+record.getCompanyName()+"'的印刷设备'"+rc.getPrintName()+"'的信息");
    	return printEquipmentService.updateById(record);
    }

    @RequestMapping("deleteById")
    @ResponseBody
    public int deleteById(int id){
        return printEquipmentService.deleteById(id);
    }

    @RequestMapping("getByCompanyId")
    @ResponseBody
    public Object getByCompanyId(int companyId) {
        List<PrintEquipment> lists = printEquipmentService.getByCompanyId(companyId);
        return lists;
    }

    @RequestMapping("getByCompanyName")
    @ResponseBody
    public Object getByCompanyId(String companyName) {
        List<PrintEquipment> lists = printEquipmentService.getByCompanyName(companyName);
        return lists;
    }
}
