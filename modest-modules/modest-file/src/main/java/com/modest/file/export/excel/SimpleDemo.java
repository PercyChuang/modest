package com.modest.file.export.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * @author Edmond Chuang
 */
public class SimpleDemo {

	private static final String TEMPLATE_X = "F:/enterprise/modest-file/src/main/resources/com/modest/file/export/mx.xlsx";
	private static final String OUTPUT_X = "target/mx_output.xlsx";
	
	private static final String TEMPLATE = "F:/enterprise/modest-file/src/main/resources/com/modest/file/export/m.xls";
	private static final String OUTPUT = "target/m_output.xls";
	
	

	public static void main(String[] args) throws Exception {
		
		//最原始导出示例
		InputStream in = new FileInputStream(TEMPLATE);
		OutputStream out =new  FileOutputStream(OUTPUT);
		month(in,out);
		InputStream inX = new FileInputStream(TEMPLATE_X);
		OutputStream outX =new FileOutputStream(OUTPUT_X);
		month(inX,outX);
		
		
		//普通导出示例  无合并
		Map<String, Object> paMap = getDate();
		ExcelUtil.exportToOutputStream(TEMPLATE, paMap, new FileOutputStream("target/b_output.xls"));
		ExcelUtil.exportToFile(TEMPLATE, paMap, new File("F:/enterprise/modest-file/target/c.xls"));
		ExcelUtil.exportToFile(new File(TEMPLATE), paMap, new File("F:/enterprise/modest-file/target/d.xls"));
		
		//合并代码示例 有合并 可扩展成多个sheet回调的形式。
		ExcelUtil.exportToOutputStream(TEMPLATE, paMap, new FileOutputStream("target/ee_output.xls"),new SheetHandler() {
			@Override
			public void doExce(Sheet sheet, Map<String, Object> data) {
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> ls = (List<Map<String, Object>>) data.get("result");
				for (int i = 0; i < ls.size(); i++) {
					sheet.addMergedRegion(new CellRangeAddress(3 * i + 1, 3 * i + 3, 0,0)); 
				}
			}
		});
	}
	
	public static Map<String, Object> getDate() {
		Map<String, Object> root = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		root.put("result", list);

		for (int i = 1; i < 10; i++) { 
			Map<String, Object> obj = new HashMap<String, Object>();
			obj.put("month", "20180" + i);
			obj.put("newUserInverstG", 10);
			obj.put("newUserInverst", 10);
			obj.put("newUserInverstF", 100);

			obj.put("oldUserInverstG", 10);
			obj.put("oldUserInverst", 5);
			obj.put("oldUserInverstF", 50);

			obj.put("totalInverstG", 20);
			obj.put("totalInverst", 15);
			obj.put("totalInverstF", 70);
			list.add(obj);
		}
		return root;
	}
	
	
	/**
	 * You are trying to read xls with explicit implementation poi classes for
	 * xlsx. Either use HSSFWorkbook and HSSFSheet classes or make your
	 * implementation more generic by using shared interfaces, like;
	 * Change:
	 * XSSFWorkbook workbook = new XSSFWorkbook(file); To:
	 * org.apache.poi.ss.usermodel.HSSFWorkbook workbook =
	 * WorkbookFactory.create(file); 
	 * And Change:
	 * XSSFSheet sheet = workbook.getSheetAt(0); To:
	 * org.apache.poi.ss.usermodel.HSSFSheet sheet = workbook.getSheetAt(0);
	 * @throws Exception
	 */
	public static void month(InputStream in,OutputStream out) throws Exception {
		Map<String, Object> root = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		root.put("result", list);

		for (int i = 1; i < 10; i++) { 
			Map<String, Object> obj = new HashMap<String, Object>();
			obj.put("month", "20180" + i);
			obj.put("newUserInverstG", 10);
			obj.put("newUserInverst", 10);
			obj.put("newUserInverstF", 100);

			obj.put("oldUserInverstG", 10);
			obj.put("oldUserInverst", 5);
			obj.put("oldUserInverstF", 50);

			obj.put("totalInverstG", 20);
			obj.put("totalInverst", 15);
			obj.put("totalInverstF", 70);
			list.add(obj);
		}

		System.out.println(list.size());

		XLSTransformer transformer = new XLSTransformer();
		Workbook workBook = transformer.transformXLS(in, root);
		Sheet sheet = workBook.getSheetAt(0);
		
		for (int i = 0; i < list.size(); i++) {
			sheet.addMergedRegion(new CellRangeAddress(3 * i + 1, 3 * i + 3, 0,0)); 
		}
		workBook.write(out);
		out.flush();
		in.close();
	}

}
