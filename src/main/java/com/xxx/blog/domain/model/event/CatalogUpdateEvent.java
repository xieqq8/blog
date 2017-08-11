package com.xxx.blog.domain.model.event;


import com.xxx.toolbox.model.NavigationNode;

/**
 * catalog新增或修改事件，用于通知缓存更新
 *
 * @author xiexx
 * @date 2017/1/7
 */
public class CatalogUpdateEvent {

    private NavigationNode catalog;

    public CatalogUpdateEvent(NavigationNode catalog) {
        this.catalog = catalog;
    }

    public NavigationNode getCatalog() {
        return catalog;
    }
}
