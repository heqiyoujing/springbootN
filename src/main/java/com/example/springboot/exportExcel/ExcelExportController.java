package com.example.springboot.exportExcel;

import com.example.springboot.SpringbootNApplication;
import com.example.springboot.controller.UserController;
import com.example.springboot.dao.UserDao;
import com.example.springboot.dao.UserDaoIml;
import com.example.springboot.entity.User;
import com.example.springboot.importExcel.ImportExcelUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author: yiqq
 * @date: 2018/9/28
 * @description: 导出Excel表
 */
@RestController
public class ExcelExportController {
    private Logger logger = LoggerFactory.getLogger(ExcelExportController.class);
    private static final String TITLE = "学生信息";
    String[] HEADER = {"用户Id","年龄","姓名","角色", "邮箱","电话"};
    String[] FIELDS = {"id","age","name","role",  "email", "phone"};


    UserDao userDao = new UserDaoIml(SpringbootNApplication.sqlSessionFactory);

    @RequestMapping("/importUser")
    public  List<User> importExcel(MultipartFile file) throws IOException {
        List<User> list = ImportExcelUtil.readOrderWayBillExcel(file);
        return list;
    }

    @RequestMapping("/exportStudent")
    public String exportExcelAll(HttpServletRequest request, HttpServletResponse response)throws IOException{
        String result = null;
        try {
            String title = "学生信息";
            XSSFWorkbook wb = exportExcel();
            ExportUtil.responseWorkbook(title, wb, request, response);
            result = "success";
        } catch (IOException e) {
            result = "fail";
            e.printStackTrace();
        }
        return result;
    }

    public XSSFWorkbook exportExcel()throws IOException {
        List<User> model = userDao.selectall();
        XSSFWorkbook wb = ExportUtil.createWorkbook();
        ExportUtil.exportExcel(TITLE, HEADER, FIELDS, 0, wb, model);
        return wb;
    }

}
