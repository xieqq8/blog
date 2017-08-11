package com.xxx.blog.domain.model.event;


import com.xxx.toolbox.model.NavigationNode;

/**
 * nav新增或修改事件，用于通知缓存更新
 *
 * @author xiexx
 * @date 2017/1/7
 */
public class NavUpdateEvent {

    private NavigationNode nav;

    public NavUpdateEvent(NavigationNode nav) {
        this.nav = nav;
    }

    public NavigationNode getNav() {
        return nav;
    }
}
