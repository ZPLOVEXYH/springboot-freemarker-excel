package com.yuanjun.mybatis.controller;

import com.yuanjun.mybatis.entity.RestResult;
import com.yuanjun.mybatis.entity.ResultBean;
import com.yuanjun.mybatis.service.IbkService;
import com.yuanjun.mybatis.util.ExcelUtils;
import com.yuanjun.mybatis.util.ResultGenerator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("ibk")
public class IbkController {

    @Resource
    IbkService ibkService;
    @Resource
    ResultGenerator resultGenerator;

    @ResponseBody
    @RequestMapping("/queryIbkInfo/{registNum}")
    public ModelAndView queryIbkInfo(@PathVariable int registNum) {
        RestResult restResult = ibkService.requestIbkApiResult(registNum);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("restResult", restResult);
        modelAndView.setViewName("show");

        return modelAndView;
    }

    @ResponseBody
    @RequestMapping("/exportExcel/{registNum}")
    public ModelAndView exportExcel(@PathVariable int registNum) {
        RestResult restResult = null;
        try {
            restResult = ibkService.requestExportExcel(registNum);
        } catch (IOException e) {
            restResult = resultGenerator.getFailResult("excel导出异常" + e.getMessage());
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("restResult", restResult);
        modelAndView.setViewName("show_excel");

        return modelAndView;
    }

    @ResponseBody
    @RequestMapping("/view")
    public ModelAndView view() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");

        return modelAndView;
    }

    @ResponseBody
    @RequestMapping("/excel/{registNum}")
    public void excel(@PathVariable int registNum) {
        RestResult restResult = null;
        try {
            restResult = ibkService.requestExportExcel(registNum);
        } catch (IOException e) {
            restResult = resultGenerator.getFailResult("excel导出异常" + e.getMessage());
        }

        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        HttpServletResponse response = servletRequestAttributes.getResponse();

        // excel标题
        String[] title = {"运单号"};

        // excel文件名
        String fileName = "运单号信息表" + System.currentTimeMillis() + ".xls";

        //sheet名
        String sheetName = "运单号信息表";
        List<ResultBean> list = (List<ResultBean>) restResult.getData();
        String[][] content = new String[list.size()][];
        for (int i = 0; i < list.size(); i++) {
            content[i] = new String[title.length];
            ResultBean obj = list.get(i);
            content[i][0] = obj.getTransNum();
        }

        //创建HSSFWorkbook
        HSSFWorkbook wb = ExcelUtils.getHSSFWorkbook(sheetName, title, content, null);

        //响应到客户端
        try {
            this.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //发送响应流方法
    public void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(), "ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
