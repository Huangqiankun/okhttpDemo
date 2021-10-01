package com.hqk.http.zeren;
public class WxPay extends AbstractPay {
    @Override
    public void barCode(PayRequest payRequest, AbstractPay abstractPay) {
        if (payRequest.getPayCode() == WX_PAY) {
            System.out.println("微信扫码支付");
        } else if(payRequest.getPayCode() == ALL_PAY){
            System.out.println("微信扫码支付完成，等待下一步");
            abstractPay.barCode(payRequest, abstractPay);
        }else {
            abstractPay.barCode(payRequest, abstractPay);
        }
    }
}

