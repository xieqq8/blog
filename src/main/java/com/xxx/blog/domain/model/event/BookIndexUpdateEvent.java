package com.xxx.blog.domain.model.event;

import com.xxx.blog.domain.model.BookIndex;

/**
 * bookindex新增或修改事件，用于通知缓存更新
 *
 * @author xiexx
 * @date 2017/1/7
 */
public class BookIndexUpdateEvent {

    private BookIndex bookIndex;

    public BookIndexUpdateEvent(BookIndex blog) {
        this.bookIndex = blog;
    }

    public BookIndex getBookIndex() {
        return bookIndex;
    }
}
