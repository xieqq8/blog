package com.xxx.blog.domain.repository;

import com.xxx.blog.domain.model.Book;

/**
 * gitbook书仓储
 * Created by xiexx on 2017/11/27.
 */
public interface BookCommandRepositry {

    void save(Book book);

    void update(Book book);

    void remove(String id);
    
}
