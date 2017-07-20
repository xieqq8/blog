package com.xxx.blog.domain.model.event;

/**
 * 博客分类删除事件，用于通知缓存更新
 *
 * @author qiang.xie
 * @date 2017/1/7
 */
public class CatalogDeleteEvent {

    private String id;

    public CatalogDeleteEvent(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
