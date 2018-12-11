package com.yuanjun.mybatis.service;

import com.yuanjun.mybatis.entity.RestResult;

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
}
