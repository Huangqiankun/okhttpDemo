package com.hqk.http.zeren;

/**
 * 待支付商品
 */
public class PayRequest {
    //待选择的支付方式
    private int payCode=0;

    public int getPayCode() {
        return payCode;
    }

    public void setPayCode(int payCode) {
        this.payCode = payCode;
    }
}
