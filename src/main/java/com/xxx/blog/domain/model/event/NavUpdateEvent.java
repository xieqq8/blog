package com.xxx.blog.domain.model.event;

import org.bumishi.toolbox.model.NavigationNode;

/**
 * nav新增或修改事件，用于通知缓存更新
 *
 * @author qiang.xie
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
