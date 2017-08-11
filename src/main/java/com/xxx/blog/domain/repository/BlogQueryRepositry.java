package com.xxx.blog.domain.repository;

import com.xxx.blog.domain.model.Blog;
import com.xxx.toolbox.model.PageModel;

/**
 * 博客查询仓储,方便使用各种nosql存储及索引
 * Created by xiexx on 2017/11/27.
 */
public interface BlogQueryRepositry {

    Blog get(String id);

    PageModel<Blog> queryByKeyword(int page, int size, String keyword);

    PageModel<Blog> queryByCatalog(int page, int size, String catalog);

    PageModel<Blog> queryByTime(int page, int size);

    int getCount();
}
