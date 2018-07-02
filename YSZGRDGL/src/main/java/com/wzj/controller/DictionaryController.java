package com.wzj.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.impl.cmd.GetDeploymentProcessDiagramCmd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wzj.pojo.DataTablesResult;
import com.wzj.pojo.Dictionary;
import com.wzj.service.impl.DictionaryService;
import com.wzj.util.ExportUtil;

@Controller
@RequestMapping("dictionary")
public class DictionaryController {
	
	@Autowired
	DictionaryService dictionaryService;
	
	@RequestMapping("/find")
	public String find(){
		return "dictionary";
	}
	
	@RequestMapping("addDic")
	@ResponseBody
	public int addDic(Dictionary dic){
		return dictionaryService.insert(dic);
	}
	
	@RequestMapping("findAll")
	@ResponseBody
	public Object dataTableDatas(int iDisplayStart,int iDisplayLength,String sEcho) {
        //ҳ��
        int page_num = (iDisplayStart / iDisplayLength) + 1;
        //����ҳ���ÿҳ���Ƚ��н�ȡ
        PageHelper.startPage(page_num, iDisplayLength);
        //��ȡdictionary
        List<Dictionary> dic = this.dictionaryService.findAll();
        //���з�ҳ����
        PageInfo<Dictionary> pageInfo = new PageInfo<Dictionary>(dic);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("sEcho", sEcho);
        map.put("aaData", pageInfo.getList());
        map.put("iTotalRecords", (int)pageInfo.getTotal());
        map.put("iTotalDisplayRecords", (int)pageInfo.getTotal());
        return map;
    }
	
	@RequestMapping("findDicById")
	@ResponseBody
	public Dictionary findDicById(int id){
		return dictionaryService.find(id);
	}
	
	@RequestMapping("update")
	@ResponseBody
	public int update(Dictionary dic){
		return dictionaryService.update(dic);
	}
	
	@RequestMapping("deleteById")
	@ResponseBody
	public int deleteById(int id){
		return dictionaryService.delete(id);
	}
	
	@RequestMapping("selectByPage")
	@ResponseBody
	public Object selectByPage(int param){
		PageInfo<Dictionary> dics=dictionaryService.selectDicByPage(1, 10);
		return dics;
	}
	
	@RequestMapping("exportRecord")
	public void exportRecord(HttpServletRequest request,HttpServletResponse response){
		ExportUtil export = new ExportUtil();
		List<Object> dics = (List<Object>)(Object)dictionaryService.findAll();
	
		//��ȡ�������ļ���
		String path = request.getSession().getServletContext().getRealPath("export/dictionary");
		String title = "�����ֵ�";
		String fileName = title+".xlsx";
		String filePath = path+"/"+fileName;
		String objPath="com.wzj.pojo.Dictionary";
		String[] names=new String[]{"����","���","����"};
		String[] methods=new String[]{"getType","getNumber","getMeaning"};
		try{
			export.exportRecord(title,objPath,names,dics,methods,filePath);
			export.exportFile(request, response, fileName, filePath);
		}catch(Exception e){
			e.printStackTrace();
			return;
		}
	}
	
}
