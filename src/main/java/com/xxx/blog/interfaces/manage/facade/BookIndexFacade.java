package com.xxx.blog.interfaces.manage.facade;

import com.google.common.eventbus.EventBus;
import com.xxx.blog.application.BookService;
import com.xxx.blog.domain.model.BookIndex;
import com.xxx.blog.domain.model.event.BookIndexUpdateEvent;
import com.xxx.blog.domain.repository.BookIndexRepositry;
import com.xxx.blog.domain.model.event.BookIndexDeleteEvent;
import com.xxx.blog.interfaces.manage.facade.assembler.BookIndexAssembler;
import com.xxx.blog.interfaces.manage.facade.command.NavigationCreateCommand;
import com.xxx.blog.interfaces.manage.facade.command.NavigationUpdateCommond;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xiexx on 2017/12/18.
 */
@Service
public class BookIndexFacade {

    @Autowired
    protected BookIndexRepositry bookIndexRepositry;
    
    @Autowired
    protected BookIndexAssembler bookIndexAssembler;

    @Autowired
    protected BookService bookService;

    @Autowired
    protected EventBus eventBus;


    public void add(NavigationCreateCommand createCommand,String bookId){
        BookIndex bookIndex = bookIndexAssembler.createCommandToDomain(createCommand, bookId);
        bookIndexRepositry.add(bookIndex);
        eventBus.post(new BookIndexUpdateEvent(bookIndex));
    }

    public void update(NavigationUpdateCommond updateCommond, String indexId) {
        BookIndex bookIndex = bookIndexAssembler.updateCommandToDomain(updateCommond, indexId);
        bookIndexRepositry.update(bookIndex);
        eventBus.post(new BookIndexUpdateEvent(bookIndex));
    }

    public void switchStatus(String id, boolean disable) {
        if (disable) {
            bookIndexRepositry.disable(id);
        } else {
            bookIndexRepositry.enable(id);
        }
        eventBus.post(new BookIndexDeleteEvent(id));
    }

    public void delete(String id) {
        bookIndexRepositry.remove(id);
        eventBus.post(new BookIndexDeleteEvent(id));
    }

    public BookIndex get(String id) {
        return bookService.getBookIndex(id);
    }

}
