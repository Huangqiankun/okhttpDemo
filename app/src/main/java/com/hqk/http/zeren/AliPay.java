package com.hqk.http.zeren;

public class AliPay extends AbstractPay {
    @Override
    public void barCode(PayRequest payRequest, AbstractPay abstractPay) {
        if (payRequest.getPayCode() == ALI_PAY) {
            System.out.println("支付宝扫码支付");
        } else if(payRequest.getPayCode() == ALL_PAY){
            System.out.println("支付宝扫码支付完成，等待下一步");
            abstractPay.barCode(payRequest, abstractPay);
        }else {
            abstractPay.barCode(payRequest, abstractPay);
        }
    }
}
