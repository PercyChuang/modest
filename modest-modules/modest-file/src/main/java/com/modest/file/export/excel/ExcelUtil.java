package com.modest.file.export.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 处理excel的工具类
 * @author Edmond Chuang
 */
public class ExcelUtil {

	private static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

	/**
	 * @param templateFile 模板文件
	 * @param param	填充到模板的参数
	 * @param desFileName	生成文件后的名字
	 * @param response	HttpServletResponse
	 * @param request	HttpServletRequest
	 * @throws Exception
	 */
	public static void exportForWeb(File templateFile,
			Map<String, Object> param,String desFileName,
			HttpServletResponse response,HttpServletRequest request)throws Exception {
		exportForWeb(templateFile,param,desFileName,response,request,null);
	}
	
	/**
	 * @param templatePath 模板文件路径
	 * @param param	填充到模板的参数
	 * @param desFileName	生成文件后的名字
	 * @param response	HttpServletResponse
	 * @param request	HttpServletRequest
	 * @throws Exception
	 */
	public static void exportForWeb(String templatePath,
			Map<String, Object> param,String desFileName,
			HttpServletResponse response,HttpServletRequest request)throws Exception {
		File templateFile = new File(templatePath);
		exportForWeb(templateFile,param,desFileName,response,request);
	}
	
	/**
	 * @param templatePath 模板文件路径
	 * @param param	填充到模板的参数
	 * @param desFileName	生成文件后的名字
	 * @param response	HttpServletResponse
	 * @param request	HttpServletRequest
	 * @param sheetHandler 回调对象
	 * @throws Exception
	 */
	public static void exportForWeb(String templatePath,
			Map<String, Object> param,String desFileName,
			HttpServletResponse response,HttpServletRequest request,SheetHandler sheetHandler)throws Exception {
		File templateFile = new File(templatePath);
		exportForWeb(templateFile,param,desFileName,response,request,sheetHandler);
	}
	
	/**
	 * @param templateFile 模板文件
	 * @param param	填充到模板的参数
	 * @param desFileName	生成文件后的名字
	 * @param response	HttpServletResponse
	 * @param request	HttpServletRequest
	 * @param sheetHandler 回调对象
	 * @throws Exception
	 */
	public static void exportForWeb(File templateFile,
			Map<String, Object> param,String desFileName,
			HttpServletResponse response,HttpServletRequest request,SheetHandler sheetHandler)throws Exception {
		if (!templateFile.exists()) {
			logger.error("export template file not exists!");
			throw new RuntimeException("export template file not exists!");
		}
		if (param == null) {
			logger.error("export xls data is null");
			return;
		}
		httpHandler(response,request,desFileName);
		exportToOutputStream(templateFile,param,response.getOutputStream(),sheetHandler);
	}
	
	public static void exportToOutputStream(String templatePath,Map<String, Object> data,OutputStream resultOutputStream,SheetHandler sheetHandler) throws Exception {
		exportToOutputStream(new FileInputStream(templatePath),data,resultOutputStream,sheetHandler);
    }
	
	public static void exportToOutputStream(File templateFile,Map<String, Object> data,OutputStream resultOutputStream,SheetHandler sheetHandler) throws Exception {
		exportToOutputStream(new FileInputStream(templateFile),data,resultOutputStream,sheetHandler);
    }
	
	public static void exportToOutputStream(String templatePath,Map<String, Object> data,OutputStream resultOutputStream) throws Exception {
		exportToOutputStream(new FileInputStream(templatePath),data,resultOutputStream,null);
    }
	
	public static void exportToOutputStream(File templateFile,Map<String, Object> data,OutputStream resultOutputStream) throws Exception {
		exportToOutputStream(new FileInputStream(templateFile),data,resultOutputStream,null);
    }
	
	public static void exportToFile(File templateFile,Map<String, Object> data,File resultFile) throws Exception {
		exportToOutputStream(templateFile,data,new FileOutputStream(resultFile));
	}
	
	public static void exportToFile(String templatePath,Map<String, Object> data,File resultFile,SheetHandler sheetHandler) throws Exception {
		exportToOutputStream(templatePath,data,new FileOutputStream(resultFile),sheetHandler);
	}

	public static void exportToFile(File templateFile,Map<String, Object> data,File resultFile,SheetHandler sheetHandler) throws Exception {
		exportToOutputStream(templateFile,data,new FileOutputStream(resultFile),sheetHandler);
	}
	
	public static void exportToFile(String templatePath,Map<String, Object> data,File resultFile) throws Exception {
		exportToOutputStream(templatePath,data,new FileOutputStream(resultFile));
	}
	
	public static void exportToWindowsDown(String templatePath,Map<String, Object> data,File resultFile) throws Exception {
		exportToOutputStream(templatePath,data,new FileOutputStream(resultFile));
	}
	
	public static void exportToOutputStream(InputStream templateInputStream,Map<String, Object> data,OutputStream resultOutputStream,SheetHandler sheetHandler) throws Exception {
		XLSTransformer transformer = new XLSTransformer();
		Workbook workBook = transformer.transformXLS(templateInputStream, data);
		if (sheetHandler != null) {
			Sheet sheet = workBook.getSheetAt(0);
			sheetHandler.doExce(sheet,data);
		}
		workBook.write(resultOutputStream);
		resultOutputStream.flush();
		templateInputStream.close();
	}
	
	private static void httpHandler(HttpServletResponse response,HttpServletRequest request,String fileName) {
		if (null == fileName || "".equals(fileName) || !fileName.contains(".xls")) {
			fileName = System.currentTimeMillis()+".xls";
		}
		fileName = encodingFileNameForBoorw(request,fileName);
		response.setCharacterEncoding("utf-8");
		response.addHeader("Content-Disposition", "attachment;filename="
				+ fileName);
		response.setContentType("application/octet-stream");
	}
	
	private static String encodingFileNameForBoorw(HttpServletRequest request, String fileName) {
		String userAgent = request.getHeader("USER-AGENT");
		try {
			if (userAgent.contains("MSIE")) {// IE
				fileName = URLEncoder.encode(fileName, "UTF8");
			} else if (userAgent.contains("Mozilla")) {// google
				fileName = new String(fileName.getBytes(), "ISO8859-1");
			} else {
				fileName = URLEncoder.encode(fileName, "UTF8");//CHEN
			}
		} catch (UnsupportedEncodingException e) {
		}
		return fileName;
	}
	
}
