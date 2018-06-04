package com.cn.webApp.common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Excel文档解析工具类
 * 
 * @author es
 *
 */
public class ExcelUtils {

	/**
	 * 读取Excel的内容，第一维数组存储的是一行中格列的值，二维数组存储的是多少个行
	 * 
	 * @param is
	 *            读取的文件流
	 * @return 读出的Excel中数据的内容
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public static String[][] getData(InputStream is) throws IOException {
		List<String[]> result = new ArrayList<String[]>();
		int rowSize = 0;
		@SuppressWarnings("resource")
		XSSFWorkbook wb = new XSSFWorkbook(is);
		XSSFCell cell = null;
		for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
			XSSFSheet st = wb.getSheetAt(sheetIndex);
			for (int rowIndex = 0; rowIndex <= st.getLastRowNum(); rowIndex++) {
				XSSFRow row = st.getRow(rowIndex);
				if (row == null) {
					continue;
				}
				int tempRowSize = row.getLastCellNum() + 1;
				if (tempRowSize > rowSize) {
					rowSize = tempRowSize;
				}
				String[] values = new String[rowSize];
				Arrays.fill(values, "");
				boolean hasValue = false;
				int lastCellNum = row.getLastCellNum();
				for (short columnIndex = 0; columnIndex <= lastCellNum; columnIndex++) {
					cell = row.getCell(columnIndex);
					String value = getCellValue(cell);
					if (columnIndex == 0 && "".equals(value.trim())) {
						break;
					}
					values[columnIndex] = rightTrim(value) + "\t" + getCellComment(cell);
					hasValue = true;
				}
				if (hasValue) {
					result.add(values);
				}
			}
		}
		String[][] returnArray = new String[result.size()][rowSize];
		for (int i = 0; i < returnArray.length; i++) {
			returnArray[i] = (String[]) result.get(i);
		}
		is.close();
		return returnArray;
	}

	/**
	 * 解析Excel获得应用模块
	 * @param is
	 * @return
	 */
	public static JSONArray parseModule(InputStream is) {
		return parseExcel(is, "app_module");
	}

	/**
	 * 解析Excel获取菜单信息
	 * @param is
	 * @return
	 */
	public static JSONArray parseMenus(InputStream is) {
		return parseExcel(is, "app_menu");
	}
	
	/**
	 * 解析Excel获得数据信息
	 * @param is
	 * @param sheetName
	 * @return
	 */
	public static JSONArray parseExcel(InputStream is, String sheetName){
		XSSFWorkbook wb = null;
		try {
			wb = new XSSFWorkbook(is);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		if (wb == null) {
			return null;
		}
		return parseExcel(wb, sheetName);
	}

	//解析Excel，获得jsonArray对象
	private static JSONArray parseExcel(XSSFWorkbook wb, String sheetName) {
		JSONArray jsonArray = new JSONArray();
		int rowSize = 0;
		XSSFSheet st = wb.getSheet(sheetName);
		if (st == null) {
			return null;
		}
		XSSFCell cell = null;
		int loastRowNum = st.getLastRowNum();
		XSSFRow row;
		JSONObject jsonObj;
		boolean isTitle = true;
		String[] titles = null;
		for (int rowIndex = 0; rowIndex <= loastRowNum; rowIndex++) {
			row = st.getRow(rowIndex);
			if (row == null) {
				continue;
			}
			int tempRowSize = row.getLastCellNum() + 1;
			if (tempRowSize > rowSize) {
				rowSize = tempRowSize;
			}
			isTitle = rowIndex == 0;
			int lastCellNum = row.getLastCellNum();
			if(isTitle){
				titles = new String[lastCellNum];
			}
			jsonObj = new JSONObject();
			for (int columnIndex = 0; columnIndex <= lastCellNum; columnIndex++) {
				cell = row.getCell(columnIndex);
				if (cell == null) {
					continue;
				}
				if (isTitle) {
					titles[columnIndex] = getCellComment(cell);
				} else {
					if (!"".equals(titles[columnIndex])) {
						jsonObj.put(titles[columnIndex], getCellValue(cell));
					}
				}
			}
			if(!jsonObj.isEmpty()){
				jsonArray.add(jsonObj);
			}
		}
		return jsonArray;
	}

	// 获取单元格的批注
	public static String getCellComment(Cell cell) {
		if (cell == null) {
			return "";
		}
		Comment comment = cell.getCellComment();
		if (comment == null) {
			return "";
		}
		RichTextString rts = comment.getString();
		if (rts == null) {
			return "";
		}
		return rts.getString();
	}

	// 获取单元格的值
	public static String getCellValue(Cell cell) {
		if (cell == null) {
			return "";
		}
		String value = "";
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:
			value = cell.getStringCellValue();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			boolean isCDF = false;
			if(cell instanceof HSSFCell){
				isCDF = HSSFDateUtil.isCellDateFormatted((HSSFCell) cell);
			}
			if (isCDF) {
				Date date = cell.getDateCellValue();
				if (date != null) {
					value = new SimpleDateFormat("yyyy-MM-dd").format(date);
				} else {
					value = "";
				}
			} else {
				value = new DecimalFormat("0").format(cell.getNumericCellValue());
			}
			break;
		case HSSFCell.CELL_TYPE_FORMULA:
			// 导入时如果为公式生成的数据则无值
			if (!cell.getStringCellValue().equals("")) {
				value = cell.getStringCellValue();
			} else {
				value = cell.getNumericCellValue() + "";
			}
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			break;
		case HSSFCell.CELL_TYPE_ERROR:
			value = "";
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			value = (cell.getBooleanCellValue() == true ? "Y" : "N");
			break;
		default:
			value = "";
		}
		return value;
	}

	/**
	 * 去掉字符串右边的空格
	 * 
	 * @param str
	 *            要处理的字符串
	 * @return 处理后的字符串
	 */
	public static String rightTrim(String str) {
		if (str == null) {
			return "";
		}
		int length = str.length();
		for (int i = length - 1; i >= 0; i--) {
			if (str.charAt(i) != 0x20) {
				break;
			}
			length--;
		}
		return str.substring(0, length);
	}

}
