package com.hqk.http.fenfa;

public class GouwuchePart extends LivePart {
    @Override
    public void dispatch(BaseEvent event) {
        if(event instanceof LiveEvent) {
            System.out.println("主播流来了，购物车要显示出来了");
        }
    }
}
