package com.yuanjun.mybatis.service;

import com.yuanjun.mybatis.entity.RestResult;
import com.yuanjun.mybatis.entity.ResultBean;
import com.yuanjun.mybatis.util.ExcelSheet;
import com.yuanjun.mybatis.util.ExcelUtil;
import com.yuanjun.mybatis.util.HttpUtil;
import com.yuanjun.mybatis.util.ResultGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 实现ibk接口的调用
 *
 * @author ZhangPeng
 */
@Service
public class IbkServiceImpl implements IbkService {

    @Resource
    HttpUtil httpUtil;

    @Resource
    private ResultGenerator resultGenerator;

    /**
     * 请求IBK API接口得到的响应结果集合
     *
     * @return
     * @author ZhangPeng
     */
    public RestResult requestIbkApiResult(int registNum) {
        List<ResultBean> resultBeanList = new ArrayList<>();
        for (int i = 0; i < registNum; i++) {
            // 请求订单收据API（快递电子商务）接口
            ResultBean registBean = httpUtil.requestOrderRegist();
            // 如果返回成功
            if ("1".equals(registBean.getResultCode())) {
                // 获取得到百达分配的快递单号
                String transNum = registBean.getTransNum();
                ResultBean deleteBean = httpUtil.requestOrderDelete(transNum);
                if ("0".equals(deleteBean.getResultCode())) {
                    return resultGenerator.getFailResult("删除订单失败");
                }
            } else {
                return resultGenerator.getFailResult("创建快递单号失败");
            }

            resultBeanList.add(registBean);
        }

        return resultGenerator.getSuccessResult("先创建后删除快递单号成功", resultBeanList);
    }

    /**
     * 导出excel
     *
     * @param registNum
     * @return
     */
    @Override
    public RestResult requestExportExcel(int registNum) throws IOException {
        List<ResultBean> resultBeanList = new ArrayList<>();
        ExcelUtil excel = new ExcelUtil();
        ExcelSheet sheet = excel.createSheet();
        sheet.row(0).cell(0).cellValue("快递单号");

        for (int i = 0; i < registNum; i++) {
            // 请求订单收据API（快递电子商务）接口
            ResultBean registBean = httpUtil.requestOrderRegist();
            // 获取得到百达分配的快递单号
            String transNum = registBean.getTransNum();
            // 如果返回成功
            if ("1".equals(registBean.getResultCode())) {
                ResultBean deleteBean = httpUtil.requestOrderDelete(transNum);
                if ("0".equals(deleteBean.getResultCode())) {
                    return resultGenerator.getFailResult("删除订单失败");
                }
            } else {
                return resultGenerator.getFailResult("创建快递单号失败");
            }

            sheet.row(i + 1).cell(0).cellValue(transNum);

            resultBeanList.add(registBean);
        }

        // 指定保存excel路径
        excel.saveExcel(httpUtil.getExcelSavePath());
        return resultGenerator.getSuccessResult("excel导出成功", resultBeanList);
    }
}
