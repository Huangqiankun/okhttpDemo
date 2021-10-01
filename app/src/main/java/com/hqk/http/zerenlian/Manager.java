package com.hqk.http.zerenlian;

//经理（额度10000）
public class Manager extends Leader {
    @Override
    public int limit() {
        return 10000;
    }
    @Override
    public void handle(int money) {
        System.out.println("经理批复报销"+ money +"元");
    }
    @Override
    public String getLeader() {
        return "当前是经理";
    }
}
