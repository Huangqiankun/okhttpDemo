package com.hqk.http.zerenlian;
//主管（额度5000）：
public class Director extends Leader {
    @Override
    public int limit() {
        return 5000;
    }
    @Override
    public void handle(int money) {
        System.out.println("主管批复报销"+ money +"元");
    }
    @Override
    public String getLeader() {
        return "当前是主管";
    }
}
