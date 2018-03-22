package com.modest.file.imports.excel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class ExcelReaderDemo {
	
	public static void main(String[] args) throws FileNotFoundException {
		//读取简单的excel demo
		 InputStream is = new FileInputStream("F:/gitwork/modest/modest-modules/modest-file/src/main/resources/com/modest/file/import/impt.xlsx");
		 List<Map<String,String>> result = ExcelReader.readExcel(is, "xlsx");
		 for (Map<String, String> map : result) {
			System.out.println(map);
		}
		 
	}
}
