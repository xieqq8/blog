package com.xxx.blog.domain.model;

import org.bumishi.toolbox.model.NavigationNode;

/**
 * 书的目录索引
 * Created by xiexx on 2017/11/27.
 */
public class BookIndex extends NavigationNode{

    private String bookId;//书籍id

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
}
