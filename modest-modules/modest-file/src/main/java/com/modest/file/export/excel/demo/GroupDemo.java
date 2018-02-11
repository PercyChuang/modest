package com.modest.file.export.excel.demo;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import com.modest.file.export.excel.ExcelUtil;
import com.modest.file.export.excel.SheetHandler;

/**
 * 分组示例。
 * 有些时候，我们需要对复杂的数据进行行成excel.
 * 分组也许可以帮到您。
 * @author Edmond Chuang
 */
@SuppressWarnings("unchecked")
public class GroupDemo {

	private static final String TEMPLATE_GROUP = "F:/enterprise/modest-file/src/main/resources/com/modest/file/export/group.xls";
	private static final String OUTPUT_X = "target/group_output.xls";
	
	
	public static void main(String[] args) throws Exception {
		exort();
	}
	
	public static void exort() throws Exception {
		
		//非合并
		ExcelUtil.exportToOutputStream(TEMPLATE_GROUP, getData(), new FileOutputStream(OUTPUT_X));
		
		//需要合并的  很多时候要发挥你的想象和理解能力去书写比较复杂的表格。
		ExcelUtil.exportToOutputStream(TEMPLATE_GROUP, getData(), new FileOutputStream(OUTPUT_X),new SheetHandler (){
			@SuppressWarnings("rawtypes")
			@Override
			public void doExce(Sheet sheet, Map data) {
				List<Employee> employees = (List<Employee>) data.get("employees");
				Collections.sort(employees, new Comparator<Employee>() {
					@Override
					public int compare(Employee o1, Employee o2) {
						return  o2.getAge() - o1.getAge();
					}
				});
				int startIndex = 2;
				int endIndex = startIndex;
				int cAge = employees.get(0).getAge();
				for (int i = 0; i < employees.size(); i++) {
					if (cAge == employees.get(i).getAge()) {
						endIndex += 1;
					}else {
						System.out.println(startIndex+":"+endIndex);
						sheet.addMergedRegion(new CellRangeAddress(startIndex,endIndex, 0,0)); 
						startIndex = endIndex + 2;
						endIndex = startIndex;
						cAge = employees.get(i).getAge();
					}
				}
			}
		});
	}
	
	public static Map<String,List<Employee>> getData() {
		
		Map<String,List<Employee>> root = new HashMap<String,List<Employee>> ();
		
		List<Employee> group1 = new ArrayList<Employee>();
		root.put("employees", group1);   //按公司分为一个组
		
		for(int i =0 ; i < 10 ; i++){
			Employee e = new Employee();
			e.setAge(i);
			e.setName("name"+ i +1);
			e.setCmpy("lmw");
			group1.add(e);
		}
		
		for(int i =0 ; i < 10 ; i++){
			Employee e = new Employee();
			e.setAge(i);
			e.setName("name"+ i +1);
			e.setCmpy("lmw");
			group1.add(e);
		}
		
		for(int i =0 ; i < 10 ; i++){
			Employee e = new Employee();
			e.setAge(i);
			e.setName("name"+ i +1);
			e.setCmpy("lmw");
			group1.add(e);
		}
		
		return root;
	}
}
