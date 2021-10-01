package com.hqk.http.zerenlian;
//老板
public class Boss extends Leader {
    @Override
    public int limit() {
        return Integer.MAX_VALUE;
    }
    @Override
    public void handle(int money) {
        System.out.println("老板批复报销"+ money +"元");
    }
    @Override
    public String getLeader() {
        return "当前是老板";
    }
}
