package com.yuanjun.mybatis.service;

import com.yuanjun.mybatis.entity.RestResult;

import java.io.IOException;

/**
 * 实现ibk接口的调用
 *
 * @author ZhangPeng
 */
public interface IbkService {

    /**
     * 请求IBK API接口得到的响应结果集合
     *
     * @author ZhangPeng
     */
    RestResult requestIbkApiResult(int registNum);

    /**
     * 导出excel
     *
     * @author ZhangPeng
     */
    RestResult requestExportExcel(int registNum) throws IOException;
}
