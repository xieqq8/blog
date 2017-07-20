package com.xxx.blog.interfaces.manage.facade;

import com.google.common.eventbus.EventBus;
import com.xxx.blog.application.BookService;
import com.xxx.blog.domain.model.Book;
import com.xxx.blog.domain.model.BookIndex;
import com.xxx.blog.domain.model.event.BookUpdateEvent;
import com.xxx.blog.domain.repository.BookCommandRepositry;
import com.xxx.blog.interfaces.manage.facade.assembler.BookAssembler;
import com.xxx.blog.interfaces.manage.facade.command.BookUpdateCommand;
import com.xxx.blog.interfaces.shard.BookDto;
import com.xxx.blog.interfaces.shard.BookDtoAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xiexx on 2017/12/18.
 */
@Service
public class BookManageFacade {

    @Autowired
    private BookCommandRepositry bookCommandRepositry;

    @Autowired
    private BookAssembler bookAssembler;

    @Autowired
    private BookDtoAssembler bookDtoAssembler;

    @Autowired
    private BookService bookService;

    @Autowired
    private EventBus eventBus;
    

    public void createBook(BookUpdateCommand bookCommand){
        Book book = bookAssembler.createComandToDomain(bookCommand);
        bookCommandRepositry.save(book);
        eventBus.post(new BookUpdateEvent(book));
    }

    public void updateBook(String id,BookUpdateCommand bookCommand){
        Book book = bookAssembler.updateCommandToDomain(id, bookCommand);
        bookCommandRepositry.update(book);
        eventBus.post(new BookUpdateEvent(book));
    }

    public void delete(String id) {
        bookService.delete(id);
    }

    public BookDto getBook(String id) {
        return bookDtoAssembler.toDto(bookService.getBook(id));
    }

    public List<BookIndex> listByBookId(String bookId) {
        return bookService.listIndexsByBookId(bookId);
    }



}
