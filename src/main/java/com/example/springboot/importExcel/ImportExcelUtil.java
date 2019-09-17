package com.example.springboot.importExcel;

import com.example.springboot.entity.User;
import com.example.springboot.entity.WPCEnum;
import com.example.springboot.entity.WPCModel;
import com.example.springboot.tool.StringUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author mawy
 * @date 2017/11/23
 */
public class ImportExcelUtil {
  //  private static Workbook wb;
   // private static InputStream fis;

    private static Workbook createWorkbook(MultipartFile file) throws IOException {
        Workbook wb=null;
        InputStream fis;
        if (!file.isEmpty()) {
            fis = file.getInputStream();
            if (file.getOriginalFilename().endsWith(".xls")) {
                wb = new HSSFWorkbook(fis);
            } else if (file.getOriginalFilename().endsWith(".xlsx")) {
                wb = new XSSFWorkbook(fis);
            }
            return wb;
        }
        return null;
    }


    /**
     * 遍历获取只存在单个列值的excel中的数据
     * @param file MultipartFile
     */
    public static List<String> readSingleStringExcel(MultipartFile file){
        List<String> list = new ArrayList<>();
        try {
            Workbook wb = createWorkbook(file);
            if(wb != null){
                Sheet sheet = wb.getSheetAt(0);
                sheet.forEach(row->{
                    if (row.getRowNum()>0) {
                        String cellStr = StringUtil.isNull(row.getCell(0));
                        if(!cellStr.equals("".trim())){list.add(cellStr);}
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 遍历获取只存在单个列值的excel中的数据
     * @param file MultipartFile
     */
    public static List<String> readSingleIntegerExcel(MultipartFile file){
        List<String> list = new ArrayList<>();
        try {
            Workbook wb = createWorkbook(file);
            if(wb != null){
                Sheet sheet = wb.getSheetAt(0);
                sheet.forEach(row->{
                    if (row.getRowNum()>0) {
                        Cell cell = row.getCell(0);
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        list.add(cell.getStringCellValue());
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 遍历获取excel中的数据
     * @param file MultipartFile
     */
    public static List<User> readOrderWayBillExcel(MultipartFile file) throws IOException {
        List<User> list = new ArrayList<>();
        //***********直接读取文件
        //Workbook wb = new XSSFWorkbook(new FileInputStream("D:\\用户表.xlsx"));
        //***********直接读取文件
        Workbook wb = createWorkbook(file);
        if(wb != null){
            Sheet sheet = wb.getSheetAt(0);
            sheet.forEach(row->{
                if (row.getRowNum()>0) {
                    User user = new User();
                    String name = StringUtil.isNull(row.getCell(0));
                    if(!name.equals("".trim())){
                        user.setName(name);
                    }
                    String phone = StringUtil.isNull(row.getCell(1));
                    if(!phone.equals("".trim())){
                        user.setPhone(phone);
                    }
                    list.add(user);
                }
            });
        }
        return list;
    }

    public static List<WPCModel> doSomething(MultipartFile files){
        List<WPCModel> dataList = new ArrayList<>();
        try {
            Workbook wb = createWorkbook(files);
            List<WPCModel> list = readExcel(wb);
            Map<String, List<WPCModel>> map = list.stream().collect(Collectors.groupingBy(wpcModel -> StringUtil.andString(wpcModel.getBillNo(),"@##@",wpcModel.getInOutType())));
            map.forEach((k,v)->{
                BigDecimal totalMoney = v.stream().map(wpcModel -> new BigDecimal(wpcModel.getAmountPaid()==null? "0":wpcModel.getAmountPaid())).reduce(BigDecimal.ZERO, BigDecimal::add);
                Integer totalAmount = v.stream().mapToInt(wpcModel-> Double.valueOf(wpcModel.getAmount()==null? "0":wpcModel.getAmount()).intValue()).sum();
                WPCModel model = v.get(0);
                model.setAmountPaid(totalMoney.toString());
                model.setAmount(totalAmount.toString());
                dataList.add(model);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * 遍历获取excel中的数据
     * @param wb
     */
    private static List<WPCModel> readExcel(Workbook wb){
        List<WPCModel> list = new ArrayList<>();
        if(wb != null){
            Sheet sheet = wb.getSheetAt(0);
            sheet.forEach(row->{
                if (row.getRowNum()>0) {
                    WPCModel wpc = new WPCModel();
                    /**商家编号*/wpc.setDeptNo(StringUtil.isNull(row.getCell(WPCEnum.DEPT_NO.getCode())));
                    /**商家名称*/wpc.setDeptName(StringUtil.isNull(row.getCell(WPCEnum.DEPT_NAME.getCode())));
                    /**库房*/wpc.setWhName(StringUtil.isNull(row.getCell(WPCEnum.WH_NAME.getCode())));
                    /**业务日期*/wpc.setBusinessDate(excelDateFormat(row.getCell(WPCEnum.BUSINESS_DATE.getCode())));
                    /**出入库类型*/wpc.setInOutType(StringUtil.isNull(row.getCell(WPCEnum.INOUT_TYPE.getCode())));
                    /**计费动作*/wpc.setChargingAction(StringUtil.isNull(row.getCell(WPCEnum.CHARGING_ACTION.getCode())));
                    /**单据编号*/wpc.setBillNo(StringUtil.isNull(row.getCell(WPCEnum.BILL_NO.getCode())));
                    /**单据类型*/wpc.setBillType(StringUtil.isNull(row.getCell(WPCEnum.BILL_TYPE.getCode())));
                    /**销售平台单据号*/wpc.setSpSourceBillNo(StringUtil.isNull(row.getCell(WPCEnum.SP_SOURCE_BILL_NO.getCode())).replace("\"",""));
                    /**商品代码*/wpc.setGoodsNo(StringUtil.isNull(row.getCell(WPCEnum.GOODS_NO.getCode())));
                    /**商品名称*/wpc.setGoodsName(StringUtil.isNull(row.getCell(WPCEnum.GOODS_NAME.getCode())));
                    /**数量*/wpc.setAmount(StringUtil.isNull(row.getCell(WPCEnum.AMOUNT.getCode())));
                    /**单价*/wpc.setUnitPrice(StringUtil.isNull(row.getCell(WPCEnum.UNIT_PRICE.getCode())));
                    /**实收金额*/wpc.setAmountPaid(StringUtil.isNull(row.getCell(WPCEnum.AMOUNT_PAID.getCode())));
                    /**数据状态*/wpc.setDataStatus(StringUtil.isNull(row.getCell(WPCEnum.DATA_STATUS.getCode())));
                    /**业务类型*/wpc.setBusinessType(StringUtil.isNull(row.getCell(WPCEnum.BUSINESS_TYPE.getCode())));
                    /**商品销售属性*/wpc.setSaleProperty(StringUtil.isNull(row.getCell(WPCEnum.SALE_PROPERTY.getCode())));
                    /**报价单名称*/wpc.setQuotationName(StringUtil.isNull(row.getCell(WPCEnum.QUOTATION_NAME.getCode())));
                    /**首件价格*/wpc.setFirstPrice(StringUtil.isNull(row.getCell(WPCEnum.FIRST_PRICE.getCode())));
                    /**首件数*/wpc.setFirstNum(StringUtil.isNull(row.getCell(WPCEnum.FIRST_NUM.getCode())));
                    /**续件价格*/wpc.setContinuationPrice(StringUtil.isNull(row.getCell(WPCEnum.CONTINUATION_PRICE.getCode())));
                    /**商家折扣*/wpc.setMerchantDiscount(StringUtil.isNull(row.getCell(WPCEnum.MERCHANT_DISCOUNT.getCode())));
                    /**计费分类*/wpc.setBillingType(StringUtil.isNull(row.getCell(WPCEnum.BILLING_TYPE.getCode())));
                    list.add(wpc);
                }
            });
        }
        return list;
    }

    public static String excelDateFormat(Cell cell){
        String dateStr = "";
        if(!isEmpty(cell)){
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                double value = cell.getNumericCellValue();
                Date date = DateUtil.getJavaDate(value);
                dateStr = sdf.format(date);
            } catch (Exception e) {
            }
        }
        return dateStr;

    }

    public static boolean isEmpty(Object obj){
        if (obj == null)
        {
            return true;
        }
        if (obj instanceof List)
        {
            return ((List) obj).size() == 0;
        }
        if(obj instanceof Map){
            return ((Map) obj).size() == 0;
        }
        if (obj instanceof String)
        {
            return ((String) obj).trim().equals("");
        }
        return false;
    }


}
