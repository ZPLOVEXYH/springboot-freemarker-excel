package com.yuanjun.mybatis.service;

import com.yuanjun.mybatis.entity.RestResult;
import com.yuanjun.mybatis.entity.ResultBean;
import com.yuanjun.mybatis.util.HttpUtil;
import com.yuanjun.mybatis.util.ResultGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

        return resultGenerator.getSuccessResult("创建、删除快递单号成功", resultBeanList);
    }
}
