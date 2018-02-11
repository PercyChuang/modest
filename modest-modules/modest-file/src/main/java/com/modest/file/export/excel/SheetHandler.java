package com.modest.file.export.excel;

import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;

/**
 * 对Sheet有特殊处理的时候使用。
 * @author Edmond Chuang
 */
public interface SheetHandler {
	void doExce(Sheet sheet,Map<String,Object> data);
}
