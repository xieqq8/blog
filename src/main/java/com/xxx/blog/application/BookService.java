package com.xxx.blog.application;

import com.google.common.eventbus.EventBus;
import com.xxx.blog.domain.model.Book;
import com.xxx.blog.domain.model.BookIndex;
import com.xxx.blog.domain.model.event.BookDeleteEvent;
import com.xxx.blog.domain.repository.BookCommandRepositry;
import com.xxx.blog.domain.repository.BookIndexRepositry;
import com.xxx.blog.domain.repository.BookQueryRepositry;
import com.xxx.toolbox.model.PageModel;
import com.xxx.toolbox.model.TreeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xiexx
 * @date 2016/12/26
 */
@Service
public class BookService {

    @Autowired
    private BookCommandRepositry bookCommandRepositry;

    @Autowired
    private BookQueryRepositry bookQueryRepositry;

    @Autowired
    protected BookIndexRepositry bookIndexRepositry;

    @Autowired
    protected EventBus eventBus;


    public void delete(String bookId) {
        bookCommandRepositry.remove(bookId);
        bookIndexRepositry.removeByBookId(bookId);
        eventBus.post(new BookDeleteEvent(bookId));
    }

    @Cacheable(EventHandler.BOOK_INDEX_PAGE_CACHE)
    public List<BookIndex> listIndexsByBookId(String bookId) {
        List<BookIndex> list = bookIndexRepositry.getByBook(bookId);
        TreeModel.sortByTree(list);
        return list;
    }

    @Cacheable(EventHandler.BOOK_CACHE)
    public Book getBook(String id){
        return bookQueryRepositry.get(id);
    }

    @Cacheable(EventHandler.BOOK_INDEX_CACHE)
    public BookIndex getBookIndex(String indexId){
        return bookIndexRepositry.get(indexId);
    }

    @Cacheable(EventHandler.BOOK_PAGE_CACHE)
    public PageModel<Book> queryByTime(int page, int size){
        return bookQueryRepositry.queryByTime(page, size);
    }

    @Cacheable(EventHandler.BOOK_PAGE_CACHE)
    public PageModel<Book> queryByCatalog(int page,int size,String catalog){
        return bookQueryRepositry.queryByCatalog(page, size,catalog);
    }

}
