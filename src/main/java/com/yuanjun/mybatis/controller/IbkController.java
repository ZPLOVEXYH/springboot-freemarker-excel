package com.yuanjun.mybatis.controller;

import com.yuanjun.mybatis.entity.RestResult;
import com.yuanjun.mybatis.service.IbkService;
import com.yuanjun.mybatis.util.ResultGenerator;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.IOException;

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
}
