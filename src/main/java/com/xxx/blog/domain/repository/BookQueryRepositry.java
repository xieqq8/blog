package com.xxx.blog.domain.repository;

import com.xxx.blog.domain.model.Book;
import com.xxx.toolbox.model.PageModel;

/**
 * Created by xiexx on 2017/11/27.
 */
public interface BookQueryRepositry {

    Book get(String id);

    PageModel<Book> queryByCatalog(int page, int size, String catalog);

    PageModel<Book> queryByTime(int page, int size);
}
