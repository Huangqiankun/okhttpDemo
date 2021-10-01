package com.hqk.http.fenfa;

//红包部件干他自己的事情
public class RedPackPart extends LivePart {
    @Override
    public void dispatch(BaseEvent event) {
        if(event instanceof LiveEvent) {
            //System.out.println("直播流来了，红包准备开始");
        }else{
            System.out.println("直播流来了，红包准备开始");
        }
    }
}
