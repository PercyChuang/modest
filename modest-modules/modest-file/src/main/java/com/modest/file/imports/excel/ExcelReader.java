package com.modest.file.imports.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 操作Excel表格的功能类
 */
public class ExcelReader {
	protected final static Logger logger = LoggerFactory.getLogger(ExcelReader.class);
	
	
	public static List<Map<String,String>> readExcel(File file) throws FileNotFoundException {
		return readExcel(new FileInputStream(file),file.getName().substring(file.getName().lastIndexOf(".")+1));
	}
	
	public static List<Map<String,String>> readExcel(InputStream is,String excelType) {
		 List<Map<String,String>> list =  null;
		if(excelType.equalsIgnoreCase("xls")){
			list=ExcelReader.readExcel(is);
		}else{
			list=ExcelReader.readExcelx(is);
		}
		List<Map<String,String>> contents = new ArrayList<Map<String,String>>();
		
		Map<String,String> title = list.get(0);
		for(int i =1;i<list.size();i++){
			Map<String,String> temp =  list.get(i);
			Map<String,String> content = new HashMap<String,String>();
			//在这里可以把它替换成某一个对象。
			for(String key:temp.keySet()){
				content.put(title.get(key),temp.get(key));
			}
			contents.add(content);
		}
		return contents;
	}
	private static List<Map<String,String>> readExcelx(InputStream is) {
		 XSSFWorkbook wb = null;
		 List<Map<String,String>> ret = new ArrayList<Map<String,String>>();
		  try {
	        	wb = new XSSFWorkbook(is);
	        	int sheetCount = wb.getNumberOfSheets();
	        	if(sheetCount!=0){
	        		for(int i=0;i<sheetCount;i++){
	        			XSSFSheet sheet = wb.getSheetAt(i);
	        			 // 得到总行数
	        	        int rowNum = sheet.getLastRowNum();
	        	        for (int j = 0; j <=rowNum; j++) {
	        	        	XSSFRow row = sheet.getRow(j);
	        	        	if (row == null) {
	        	        		System.out.println("row num null value:"+j);
	        	        		continue;
	        	        	}
	        	        	int colNum = row.getLastCellNum();
	        	        	Map<String,String> retMap = new HashMap<String,String>();
	        	        	for (int k = 0; k < colNum; k++) {
	        	        		String cellValue = getCellFormatValue(row.getCell(k)).trim();
	        	        		retMap.put(k+"",cellValue);
	            	        }
	        	        	ret.add(retMap);
	        	        }
	        		}
	        	}
	        } catch (IOException e) {
	        	logger.warn("无效的excel文件",e);
	        }finally{
	        	if(wb!=null){
	        		try {
						is.close();
					} catch (IOException e) {
						logger.warn("无效的excel文件",e);
					}
	        	}
	        }
	        return ret;
	 }

    /**
     * 读取Excel表格表头的内容
     * @param InputStream
     * @return 
     */
	private static List<Map<String,String>> readExcel(InputStream is) {
    	HSSFWorkbook wb = null;
    	List<Map<String,String>> ret = new ArrayList<Map<String,String>>();
        try {
        	wb = new HSSFWorkbook(new POIFSFileSystem(is));
        	int sheetCount = wb.getNumberOfSheets();
        	if(sheetCount!=0){
        		for(int i=0;i<sheetCount;i++){
        			HSSFSheet sheet = wb.getSheetAt(i);
        			 // 得到总行数
        	        int rowNum = sheet.getLastRowNum();
        	        for (int j = 0; j < rowNum; j++) {
        	        	HSSFRow row = sheet.getRow(j);
        	        	if (row == null) {
        	        		System.out.println("row num null value:"+j);
        	        		continue;
        	        	}
        	        	int colNum = row.getLastCellNum();
        	        	Map<String,String> retMap = new HashMap<String,String>();
        	        	for (int k = 0; k < colNum; k++) {
        	        		String cellValue = getCellFormatValue(row.getCell(k)).trim();
        	        		retMap.put(k+"",cellValue);
            	        }
        	        	ret.add(retMap);
        	        }
        		}
        	}
        } catch (Exception e) {
        	logger.warn("无效的excel文件",e);
        }finally{
        	if(wb!=null){
        		try {
					is.close();
				} catch (IOException e) {
					logger.warn("无效的excel文件",e);
				}
        	}
        }
        return ret;
    }
    
    /**
     * 根据HSSFCell类型设置数据
     * @param cell
     * @return
     */
    private static String getCellFormatValue(XSSFCell cell) {
        String cellvalue = "";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
            // 如果当前Cell的Type为NUMERIC
            case XSSFCell.CELL_TYPE_NUMERIC:
            case XSSFCell.CELL_TYPE_FORMULA: {
                // 判断当前的cell是否为Date
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    cellvalue = sdf.format(date);
                }else {
                    //cellvalue = String.valueOf(cell.getNumericCellValue());
                    cell.setCellType(1);
                    cellvalue = cell.getRichStringCellValue().getString();
                }
                break;
            }
            case XSSFCell.CELL_TYPE_STRING:
                cellvalue = cell.getRichStringCellValue().getString();
                break;
            default:
                cellvalue = "";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;
    }

    /**
     * 根据HSSFCell类型设置数据
     * @param cell
     * @return
     */
    private static String getCellFormatValue(HSSFCell cell) {
        String cellvalue = "";
        if (cell != null) {
            switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_STRING:
            	 cellvalue = cell.getStringCellValue();
            	 break;
            case HSSFCell.CELL_TYPE_NUMERIC:
            	if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    cellvalue = sdf.format(date);
                    break;
                }else{
                	cell.setCellType(1);
                	cellvalue = cell.getRichStringCellValue().getString();
            		break;
                }
            case HSSFCell.CELL_TYPE_FORMULA: {
                // 判断当前的cell是否为Date
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    cellvalue = sdf.format(date);
                    break;
                }
             }
            }
        }
        return cellvalue;
    }
}
