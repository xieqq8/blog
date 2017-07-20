package com.xxx.blog.domain.model.event;

/**
 * book删除事件，用于通知缓存更新
 *
 * @author xiexx
 * @date 2017/1/7
 */
public class BookDeleteEvent {

    private String bookId;

    public BookDeleteEvent(String blogId) {
        this.bookId = blogId;
    }

    public String getBookId() {
        return bookId;
    }
}
