package com.xxx.blog.domain.model.event;

/**
 * book索引删除事件，用于通知缓存更新
 *
 * @author qiang.xie
 * @date 2017/1/7
 */
public class BookIndexDeleteEvent {

    private String bookIndexId;

    public BookIndexDeleteEvent(String blogId) {
        this.bookIndexId = blogId;
    }

    public String getBookIndexId() {
        return bookIndexId;
    }
}
