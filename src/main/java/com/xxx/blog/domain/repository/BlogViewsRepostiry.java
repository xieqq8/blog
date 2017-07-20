package com.xxx.blog.domain.repository;

/**
 * blog阅读量仓储
 * Created by xiexx on 2017/1/10.
 */
public interface BlogViewsRepostiry {

    void addViews(String id);

    int getViews(String blogId);
}
