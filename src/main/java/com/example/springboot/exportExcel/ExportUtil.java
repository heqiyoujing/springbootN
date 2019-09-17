package com.example.springboot.exportExcel;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.StringUtil;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: yiqq
 * @date: 2018/9/28
 * @description:
 */
public class ExportUtil {

    private static final int sheetMaxCount = 1000000;//单个sheet最多写入行数

    /**
     * responseWorkbook
     *
     * @param title
     * @param wb
     * @param request
     * @param response
     * @throws IOException
     */
    public static void responseWorkbook(String title, Workbook wb, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String sFileName = title + ".xlsx";
        // 火狐浏览器导出excel乱码
        String agent = request.getHeader("User-Agent");
        // 判断是否火狐浏览器
        boolean isFirefox = agent != null && agent.contains("Firefox");
        if (isFirefox) {
            sFileName = new String(sFileName.getBytes("UTF-8"), "ISO-8859-1");
        } else {
            sFileName = URLEncoder.encode(sFileName, "UTF8");
        }
        response.setHeader("Content-Disposition", "attachment; filename=".concat(sFileName));
        response.setHeader("Connection", "close");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        wb.write(response.getOutputStream());
    }
    public static <T> void exportStatisticsExcel(String title, String[] headers, String[] fields, int startRow, XSSFWorkbook wb, List<T> data,int[] mergeCol) throws IOException {

        XSSFSheet sheet = null;
        startRow = startRow > 0 ? startRow + 2 : startRow;
        int pageRowNo = startRow, columnCount = headers.length; // 行号、页码、列数
        Map<String,Integer> sortMap = new LinkedHashMap<>();
        sheet = wb.getSheetAt(0);
        sheet.autoSizeColumn(1, true);
        //设置格式
        XSSFCellStyle headStyle = wb.createCellStyle();
        //边框
        headStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        headStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
        headStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        headStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);

        Font ztFont = wb.createFont();
        ztFont.setFontName("微软雅黑");
        headStyle.setFont(ztFont);
        //对齐方式
        headStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        headStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        int num = 0;
        for (T obj : data) {
            Map<String, Object> map = obj instanceof Map ? (Map<String, Object>) obj : beanToMap(obj);
            XSSFRow nRow = sheet.createRow(pageRowNo++); // 新建行对象
            for (int j = 0; j < columnCount; j++) {
                XSSFCell cell = nRow.createCell(j);
                setCellValue(cell, map.get(fields[j]));
                //sheet.setColumnWidth(j, 15 * 256);
                cell.setCellStyle(headStyle);
            }
            Integer count = sortMap.get(map.get(fields[0]));
            sortMap.put((String) map.get(fields[0]),(count == null) ? 1 : count + 1);
            if(null==count){
                if(data.indexOf(obj)!=0&&data.indexOf(obj)!=data.size()-2&&num>1){
                    for(int col:mergeCol){
                        mergeCell(sheet, pageRowNo - num - 1, pageRowNo - 2, col, col);
                    }
                }
            }
            num = (count == null) ? 1 : count + 1;
            if(data.indexOf(obj)==data.size()-2){
                for(int col:mergeCol){
                    mergeCell(sheet, pageRowNo - num, pageRowNo - 1, col, col);
                }
            }
        }
    }

    public static XSSFWorkbook createWorkbook() {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFCellStyle hcs = wb.createCellStyle();
//        hcs.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        hcs.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        hcs.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        hcs.setBorderRight(XSSFCellStyle.BORDER_THIN);
        hcs.setBorderTop(XSSFCellStyle.BORDER_THIN);
        hcs.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        Font hfont = wb.createFont();
        hfont.setFontName("宋体");
        hfont.setFontHeightInPoints((short) 16);// 设置字体大小
        hfont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        hcs.setFont(hfont);

        XSSFCellStyle tcs = wb.createCellStyle();
//        tcs.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        tcs.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        tcs.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        tcs.setBorderRight(XSSFCellStyle.BORDER_THIN);
        tcs.setBorderTop(XSSFCellStyle.BORDER_THIN);
        Font tfont = wb.createFont();
        tfont.setFontName("宋体");
        tfont.setFontHeightInPoints((short) 16);// 设置字体大小
        tcs.setFont(tfont);

        XSSFCellStyle cs = wb.createCellStyle();
        cs.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        cs.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        cs.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cs.setBorderTop(XSSFCellStyle.BORDER_THIN);
//        cs.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cs.setWrapText(true);
        Font font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 16);// 设置字体大小

        return wb;
    }
    public static <T> void exportExcel(String title, String[] headers, String[] fields, int startRow, XSSFWorkbook wb, List<T> data) throws IOException {

        XSSFSheet sheet = null;
        startRow = startRow > 0 ? startRow + 2 : startRow;
        int index = startRow, pageRowNo = startRow, columnCount = headers.length; // 行号、页码、列数
        for (T obj : data) {
            int sheetIndex = index / sheetMaxCount;
            if (index % sheetMaxCount == 0) {
                wb.createSheet(title + "_" + (sheetIndex + 1));
                sheet = wb.getSheetAt(sheetIndex);
                sheet.setDisplayGridlines(false);// 设置表标题是否有表格边框
                pageRowNo = 2;
                createHeader(sheet, title, headers);
            } else {
                sheet = wb.getSheetAt(sheetIndex);
            }
            index++;
            Map<String, Object> map = obj instanceof Map ? (Map<String, Object>) obj : beanToMap(obj);
            XSSFRow nRow = sheet.createRow(pageRowNo++); // 新建行对象
            for (int j = 0; j < columnCount; j++) {
                XSSFCell cell = nRow.createCell(j);
                setCellValue(cell,zero2middleLine(map.get(fields[j])));
                sheet.setColumnWidth(j, 20 * 256);
            }
        }
    }
    public static <T> void exportExcel(String title, String[] headers, String[] fields, int startRow, XSSFWorkbook wb, List<T> data,int[] mergeCol) throws IOException {

        XSSFSheet sheet = null;
        startRow = startRow > 0 ? startRow + 2 : startRow;
        int index = startRow, pageRowNo = startRow, columnCount = headers.length; // 行号、页码、列数
        Map<String,Integer> sortMap = new LinkedHashMap<>();
        int num = 0;
        for (T obj : data) {
            int sheetIndex = index / sheetMaxCount;
            if (index % sheetMaxCount == 0) {
                wb.createSheet(title + "_" + (sheetIndex + 1));
                sheet = wb.getSheetAt(sheetIndex);
                sheet.setDisplayGridlines(false);// 设置表标题是否有表格边框
                pageRowNo = 2;
                createHeader(sheet, title, headers);
            } else {
                sheet = wb.getSheetAt(sheetIndex);
            }
            index++;
            Map<String, Object> map = obj instanceof Map ? (Map<String, Object>) obj : beanToMap(obj);
            XSSFRow nRow = sheet.createRow(pageRowNo++); // 新建行对象
            for (int j = 0; j < columnCount; j++) {
                XSSFCell cell = nRow.createCell(j);
                setCellValue(cell, zero2middleLine(map.get(fields[j])));
                sheet.setColumnWidth(j, 20 * 256);
            }
            Integer count = sortMap.get(map.get(fields[0]));
            sortMap.put((String) map.get(fields[0]),(count == null) ? 1 : count + 1);
            if(null==count){
                if(data.indexOf(obj)!=0&&num>1){
                    for(int col:mergeCol){
                        mergeCell(sheet,pageRowNo-num-1,pageRowNo-2,col,col);
                    }
                }
            }
            num = (count == null) ? 1 : count + 1;
            if(data.indexOf(obj)==data.size()-1){
                for(int col:mergeCol){
                    mergeCell(sheet,pageRowNo-num,pageRowNo-1,col,col);
                }
            }
        }
    }

    public static <T> void exportExcel(String[] headers, String[] fields, int startRow, XSSFWorkbook wb, List<T> data) throws IOException {
        XSSFSheet sheet = null;
        startRow = startRow > 0 ? startRow + 1 : startRow;
        int index = startRow, pageRowNo = startRow, columnCount = headers.length; // 行号、页码、列数
        for (T obj : data) {
            int sheetIndex = index / sheetMaxCount;
            if (index % sheetMaxCount == 0) {
                wb.createSheet((sheetIndex + 1)+"");
                sheet = wb.getSheetAt(sheetIndex);
                sheet.setDisplayGridlines(false);// 设置表标题是否有表格边框
                pageRowNo = 1;
                createHeader(sheet, headers);
            } else {
                sheet = wb.getSheetAt(sheetIndex);
            }
            index++;
            Map<String, Object> map = obj instanceof Map ? (Map<String, Object>) obj : beanToMap(obj);
            XSSFRow nRow = sheet.createRow(pageRowNo++); // 新建行对象
            for (int j = 0; j < columnCount; j++) {
                XSSFCell cell = nRow.createCell(j);
                setCellValue(cell,map.get(fields[j]));
                sheet.setColumnWidth(j, 20 * 256);
            }
        }
    }

    /**
     * 设置单元格的值
     *
     * @param cell
     * @param cellVal
     */
    public static void setCellValue(XSSFCell cell, Object cellVal) {
        if (cellVal == null || String.class.equals(cellVal.getClass())) {
            cell.setCellValue( new XSSFRichTextString(isNull(cellVal)));
        } else if (cellVal instanceof Integer || int.class.equals(cellVal.getClass())) {
            cell.setCellValue(Integer.valueOf(cellVal.toString()));
        } else if (Long.class.equals(cellVal.getClass()) || long.class.equals(cellVal.getClass())) {
            cell.setCellValue(Integer.valueOf(cellVal.toString()));
        } else if (Double.class.equals(cellVal.getClass()) || double.class.equals(cellVal.getClass())) {
            cell.setCellValue(Double.valueOf(cellVal.toString()));
        } else if (Float.class.equals(cellVal.getClass()) || float.class.equals(cellVal.getClass())) {
            cell.setCellValue(Float.valueOf(cellVal.toString()));
        } else if (BigDecimal.class.equals(cellVal.getClass())) {
//            cell.setCellValue(new BigDecimal(cellVal.toString()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            cell.setCellValue(cellVal.toString());
        } else if(Timestamp.class.equals(cellVal.getClass())) {
            cell.setCellValue(formatDateTime((Timestamp) cellVal));
        }else if (cellVal instanceof byte[]) {
            XSSFDrawing drawing = cell.getSheet().createDrawingPatriarch();
            // 有图片时，设置行高为60px;
            cell.getRow().setHeightInPoints(60);
//            // 设置图片所在列宽度为80px,注意这里单位的一个换算
            cell.getSheet().setColumnWidth(cell.getColumnIndex(), (short) (35.7 * 80));
            XSSFClientAnchor anchor = drawing.createAnchor(5, 0,500, 122,(short)cell.getColumnIndex(),cell.getRowIndex(), (short)cell.getColumnIndex()+1,cell.getRowIndex()+1);
            anchor.setAnchorType(2);
            drawing.createPicture(anchor,cell.getSheet().getWorkbook().addPicture((byte[]) cellVal, XSSFWorkbook.PICTURE_TYPE_JPEG));
        }else {
            cell.setCellValue(isNull(cellVal));
        }
        cell.setCellStyle(cell.getSheet().getWorkbook().getCellStyleAt((short)3));
    }

    public static String formatDateTime(Timestamp time){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(time);
        return date;
    }

    public static String isNull(Object str){
        String ss = "";
        if(str != null && !"".equals(str.toString().trim())){
            ss = str.toString();
        }
        return ss;
    }

    //对象转Map，保留属性类型
    synchronized static Map<String, Object> beanToMap(Object obj) {
        Map<String, Object> params = new HashMap<>(0);
        try {
            PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
            PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj);
            for (PropertyDescriptor descriptor : descriptors) {
                String name = descriptor.getName();
                if (!StringUtils.equals(name, "class")) {
                    params.put(name, propertyUtilsBean.getNestedProperty(obj, name));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }
    /**
     * 创建表头
     *
     * @param sheet
     * @param headers
     */
    private static void createHeader(XSSFSheet sheet, String title, String[] headers) {

        //设置标题
        XSSFRow tRow = sheet.createRow(0);
        XSSFCell hc = tRow.createCell(0);
        hc.setCellValue(new XSSFRichTextString(title));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headers.length-1));// 合并标题行：起始行号，终止行号， 起始列号，终止列号
//        XSSFCell cellEnd = tRow.createCell(headers.length-1);
//        cellEnd.setCellValue(" ");//
//        cellEnd.setCellStyle(sheet.getWorkbook().getCellStyleAt((short)1));
        hc.setCellStyle(sheet.getWorkbook().getCellStyleAt((short)1));

        //设置表头
        XSSFRow nRow = sheet.createRow(1);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = nRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(sheet.getWorkbook().getCellStyleAt((short)2));
        }
    }

    private static void createHeader(XSSFSheet sheet, String[] headers) {
        //设置表头
        XSSFRow nRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = nRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(sheet.getWorkbook().getCellStyleAt((short)2));
        }
    }

    public static Object zero2middleLine(Object obj){
        if (obj instanceof Integer)
        {
            if(obj.equals("0.00")){
                return "-";
            }
        }
        if (obj instanceof String)
        {
            if(obj.equals("0.00")||obj.equals("0")){
                return "-";
            }
        }
        return obj;
    }
    public static void mergeCell(XSSFSheet mSheet,int firstRow, int lastRow, int firstCol, int lastCol) {
        CellRangeAddress address = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
        mSheet.addMergedRegion(address);
    }
}
