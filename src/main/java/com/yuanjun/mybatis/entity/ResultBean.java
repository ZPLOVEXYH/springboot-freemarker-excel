package com.yuanjun.mybatis.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 百达快递API响应结果
 */
@XmlRootElement(name = "RESULT")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResultBean {

    /**
     * 接口返回码（1：成功，0：失败）
     */
    @XmlElement(name = "RESULTCODE")
    private String resultCode;

    /**
     * 返回的成功或者错误信息
     */
    @XmlElement(name = "RESULTMSG")
    private String resultMsg;

    /**
     * 分配的快递单号
     */
    @XmlElement(name = "TRANSNUM")
    private String transNum;

    /**
     * 接收号
     */
    @XmlElement(name = "IDXNO")
    private String idxNo;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public String getTransNum() {
        return transNum;
    }

    public void setTransNum(String transNum) {
        this.transNum = transNum;
    }

    public String getIdxNo() {
        return idxNo;
    }

    public void setIdxNo(String idxNo) {
        this.idxNo = idxNo;
    }
}
