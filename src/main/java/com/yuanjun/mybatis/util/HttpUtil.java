package com.yuanjun.mybatis.util;

import com.alibaba.fastjson.JSON;
import com.yuanjun.mybatis.entity.ResultBean;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ZhangPeng
 */
@Component
@PropertySource(value = {"classpath:application.yml"})
public class HttpUtil {

    /**
     * 订单收据API（快递电子商务）
     */
    @Value("${ibk.order.regist.url}")
    private String orderRegistUrl;

    /**
     * 删除订单api接口地址
     */
    @Value("${ibk.order.delete.url}")
    private String orderDeleteUrl;

    /**
     * 用户id
     */
    @Value("${ibk.userid}")
    private String userId;

    /**
     * app key
     */
    @Value("${ibk.authkey}")
    private String authkey;

    /**
     * 发送Get请求
     *
     * @param url    : 请求的连接
     * @param params ： 请求参数，无参时传null
     * @return
     */
    public static String sendGet(String url, Map<String, String> params) {
        HttpRequest request = HttpRequest.get(url);
        if (params != null) {
            request.query(params);
        }
        HttpResponse response = request.send();
        String respJson = response.bodyText();
        return respJson;
    }


    /**
     * 发送Post请求-json数据
     *
     * @param url    : 请求的连接
     * @param params ：  请求参数，无参时传null
     * @return
     */
    public static String sendPostToJson(String url, Map<String, Object> params) {
        HttpRequest request = HttpRequest.post(url);
        request.contentType("application/json");
        request.charset("utf-8");

        //参数详情
        if (params != null) {
            request.body(JSON.toJSONString(params));
        }

        HttpResponse response = request.send();
        String respJson = response.bodyText();
        return respJson;
    }

    /**
     * 发送Post请求
     *
     * @param url           : 请求的连接
     * @param params        ：  请求参数，无参时传null
     * @param paramsDatails : 参数详情，没有时传null
     * @return
     */
    public static String sendPost(String url, Map<String, Object> params) {
        HttpRequest request = HttpRequest.post(url);

        //参数详情
        if (params != null) {
            request.form(params);
        }
        HttpResponse response = request.send();
        String respJson = response.bodyText();
        return respJson;
    }


    /**
     * 发送Delete请求
     *
     * @param url    : 请求的连接
     * @param params ：  请求参数，无参时传null
     * @return
     */
    public static String sendDelete(String url, Map<String, Object> params) {
        HttpRequest request = HttpRequest.delete(url);

        if (params != null) {
            request.form(params);
        }
        HttpResponse response = request.send();
        String respJson = response.bodyText();
        return respJson;
    }

    /**
     * 订单收据API（快递电子商务）接口调用
     *
     * @return
     */
    public ResultBean requestOrderRegist() {
        Map<String, Object> registMap = new HashMap<>();
        registMap.put("userid", userId);
        registMap.put("authkey", authkey);
        registMap.put("jisa", "WEI");
        registMap.put("shipper", "IBK");
        registMap.put("shipper_addr", "WEIHAI CHINA");
        registMap.put("consignee", "CJ GLS");
        registMap.put("consignee_telno", "010-2222-1111");
        registMap.put("zipcode", "13850");
        registMap.put("consignee_addr", "서울시 영등포구 신길동 푸른아파트 101-1202");
        registMap.put("produrl", "http://WWW.TAOBAO.COM");
        registMap.put("packagecnt", "1");
        registMap.put("weight", "10.5");
        registMap.put("category", "床");
        registMap.put("hscode", "90");
        registMap.put("prodname1", "Sealy Posturepedic Santa1");
        registMap.put("qty1", "2");
        registMap.put("cost1", "20.99");
        registMap.put("prodname2", "Simmons Beautyrest Luxury1");
        registMap.put("qty2", "1");
        registMap.put("cost2", "125.54");
        String responbody = HttpUtil.sendPost(orderRegistUrl, registMap);

        // 订单收据API（快递电子商务）接口调用
        ResultBean resultBean = JaxbUtil.converyToJavaBean(responbody, ResultBean.class);

        return resultBean;
    }

    /**
     * 删除订单表格API
     *
     * @return
     */
    public ResultBean requestOrderDelete(String transnum) {
        Map<String, Object> deleteMap = new HashMap<>();
        deleteMap.put("userid", userId);
        deleteMap.put("authkey", authkey);
        deleteMap.put("transnum", transnum);
        deleteMap.put("gubun", "E");
        String responbody = HttpUtil.sendPost(orderDeleteUrl, deleteMap);

        // 删除订单表格API 接口调用
        ResultBean resultBean = JaxbUtil.converyToJavaBean(responbody, ResultBean.class);

        return resultBean;
    }


    // 测试
    public static void main(String[] args) {
        // 打印 post
//        Map<String, Object> transMap = new HashMap<>();
//        transMap.put("userid","test55");
//        transMap.put("authkey","9EE98412A7BCC9B6FD48EDF6CB06DE91");
//        transMap.put("transnum","347865404746");
//        String responbody = HttpUtil.sendPost("http://www.inbko.co.kr/api/transnum_print.asp", transMap);

        Map<String, Object> transMap = new HashMap<>();
        transMap.put("userid", "test55");
        transMap.put("authkey", "9EE98412A7BCC9B6FD48EDF6CB06DE91");
        transMap.put("transnum", "347865404735");
        transMap.put("gubun", "E");
        String responbody = HttpUtil.sendPost("http://www.inbko.co.kr/api/order_delete.asp", transMap);


        ResultBean outDocumentXml = JaxbUtil.converyToJavaBean(responbody, ResultBean.class);

        System.out.println(outDocumentXml.toString());
    }

}
