package com.xxx.blog.domain.repository;

import com.xxx.blog.domain.model.BookIndex;
import com.xxx.toolbox.model.repositry.TreeNodeCommandRepositry;
import com.xxx.toolbox.model.repositry.TreeNodeQueryRepositry;

import java.util.List;

/**
 * 可导航的节点仓储，菜单，分类都是可导航的对象
 * Created by xiexx on 2017/11/26.
 */
public interface BookIndexRepositry extends TreeNodeQueryRepositry<BookIndex>,TreeNodeCommandRepositry<BookIndex> {

    List<BookIndex> getByBook(String bookId);

    void removeByBookId(String bookId);
}
