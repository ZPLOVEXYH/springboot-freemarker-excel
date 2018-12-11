package com.yuanjun.mybatis.controller;

import com.yuanjun.mybatis.entity.RestResult;
import com.yuanjun.mybatis.service.IbkService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@RestController
@RequestMapping("ibk")
public class IbkController {

    @Resource
    IbkService ibkService;

    @ResponseBody
    @RequestMapping("/queryIbkInfo/{registNum}")
    public ModelAndView queryIbkInfo(@PathVariable int registNum) {
        RestResult restResult = ibkService.requestIbkApiResult(registNum);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("restResult", restResult);
        modelAndView.setViewName("show");

        return modelAndView;
    }
}
