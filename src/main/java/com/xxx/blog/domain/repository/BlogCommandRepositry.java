package com.xxx.blog.domain.repository;

import com.xxx.blog.domain.model.Blog;

/**
 * Created by xiexx on 2017/11/26.
 */
public interface BlogCommandRepositry {

    void save(Blog blog);

    void update(Blog blog);

    void remove(String id);

}
