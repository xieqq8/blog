package com.xxx.blog.domain.model.event;

/**
 * nav删除事件，用于通知缓存更新
 *
 * @author qiang.xie
 * @date 2017/1/7
 */
public class NavDeleteEvent {

    private String id;

    public NavDeleteEvent(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
