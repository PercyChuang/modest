package com.modest.web.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.modest.file.export.excel.ExcelUtil;
import com.modest.file.export.excel.SheetHandler;

@Controller
@RequestMapping("/lmw")
public class ExcelExportController {

	
	/**
	 * web情况下的导出示例
	 * @param request
	 * @param response
	 * @return
	 * @throws FileNotFoundException
	 * @throws Exception
	 */
	@RequestMapping(value = "/export")
	public void export(HttpServletRequest request,
			HttpServletResponse response) throws FileNotFoundException, Exception {
		String templat = request.getSession().getServletContext().getRealPath("/WEB-INF/xls") + File.separator + "m.xls";
		
		ExcelUtil.exportForWeb(templat, getDate(), "导出示例", response, request,new SheetHandler() {
			@Override
			public void doExce(Sheet sheet, Map data) {
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> ls = (List<Map<String, Object>>) data
						.get("result");
				for (int i = 0; i < ls.size(); i++) {
					sheet.addMergedRegion(new CellRangeAddress(3 * i + 1,
							3 * i + 3, 0, 0));
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
	
}
