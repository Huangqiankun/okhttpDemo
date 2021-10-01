package com.hqk.http.zeren;

import java.util.ArrayList;
import java.util.List;

public class PayChain extends AbstractPay {
    /**
     * 完整责任链列表
     */
    private List<AbstractPay> list = new ArrayList<>();

    /**
     * 索引
     */
    private int index = 0;

    /**
     * 添加责任对象
     *
     * @param abstractPay
     * @return
     */
    public PayChain add(AbstractPay abstractPay) {
        list.add(abstractPay);
        return this;
    }

    @Override
    public void barCode(PayRequest payRequest, AbstractPay abstractPay) {
        // 所有遍历完了，直接返回
        if (index == list.size()) {
            System.out.println("支付全部完成，请取商品");
            return;
        }
        // 获取当前责任对象
        AbstractPay current = list.get(index);
        // 修改索引值，以便下次回调获取下个节点，达到遍历效果
        index++;
        // 调用当前责任对象处理方法
        current.barCode(payRequest, this);
    }
}
