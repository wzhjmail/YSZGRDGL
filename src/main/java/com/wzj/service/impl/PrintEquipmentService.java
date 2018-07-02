package com.wzj.service.impl;

import com.wzj.dao.PrintEquipmentMapper;
import com.wzj.pojo.PrintEquipment;
import com.wzj.service.IPrintEquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("printEquipmentService")
public class PrintEquipmentService implements IPrintEquipmentService {
    @Autowired
    private PrintEquipmentMapper printEquipmentMapper;

    public int insert(PrintEquipment record) {
        return printEquipmentMapper.insert(record);
    }
    public int updateById(PrintEquipment record){
        return printEquipmentMapper.updateById(record);
    }
    public PrintEquipment getById(int id){
        return printEquipmentMapper.getById(id);
    }
    public int deleteById(int id) {
        return printEquipmentMapper.deleteById(id);
    }
    public List<PrintEquipment> getByCompanyId(int companyId){
        return printEquipmentMapper.getByCompanyId(companyId);
    }
    public int updateByCompanyName(String companyName,int companyId) {
        return printEquipmentMapper.updateByCompanyName(companyName,companyId);
    }
    public List<PrintEquipment> getByCompanyName(String companyName) {
        return printEquipmentMapper.getByCompanyName(companyName);
    }
	public void deleteByCompanyName(String companyName) {
		printEquipmentMapper.deleteByCompanyName(companyName);
	}
	public void updateCompanyName(String oldName, String companyName) {
		printEquipmentMapper.updateCompanyName(oldName,companyName);
	}
}