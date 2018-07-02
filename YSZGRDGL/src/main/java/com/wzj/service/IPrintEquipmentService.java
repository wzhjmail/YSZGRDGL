package com.wzj.service;

import com.wzj.pojo.PrintEquipment;

import java.util.List;

public interface IPrintEquipmentService {
    public int insert(PrintEquipment record);
    public int updateById(PrintEquipment record);
    public PrintEquipment getById(int id);
    public List<PrintEquipment> getByCompanyId(int companyId);
    public int deleteById(int id);
    public int updateByCompanyName(String companyName,int companyId);
    public List<PrintEquipment> getByCompanyName(String companyName);
}
