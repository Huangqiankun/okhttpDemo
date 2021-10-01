package com.hqk.http.fenfa;

//事件分发机制
public class SmallVideoPart extends LivePart {
    @Override
    public void dispatch(BaseEvent event) {
        if(event instanceof LiveEvent)
        System.out.println("主播流来了，其他小视频窗口流要渲染出来了");
    }
}
