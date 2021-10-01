package com.hqk.http.zeren;


public abstract class AbstractPay {

    /**
     * 支付宝支付
     */
    public static int ALI_PAY = 1;

    /**
     * 微信支付
     */
    public static int WX_PAY = 2;
    /**
     * 两者支付方式
     */
    public static int ALL_PAY = 3;

    /**
     * 条码支付
     *
     * @param payRequest
     * @param abstractPay
     */
    abstract protected void barCode(PayRequest payRequest, AbstractPay abstractPay);
}
