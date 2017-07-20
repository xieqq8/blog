package com.xxx.blog.interfaces.manage.facade.assembler;

import com.xxx.blog.application.CatalogService;
import com.xxx.blog.domain.model.Book;
import com.xxx.blog.interfaces.manage.facade.command.BookUpdateCommand;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

/**
 * Created by xiexx on 2017/12/18.
 */
@Component
public class BookAssembler {

    @Autowired
    protected CatalogService catalogService;

    public Book createComandToDomain(BookUpdateCommand bookUpdateCommand){
        Book book=new Book();
        book.setId(UUID.randomUUID().toString());
        book.setPublishTime(new Date());
        BeanUtils.copyProperties(bookUpdateCommand,book);
        return book;
    }

    public Book updateCommandToDomain(String id,BookUpdateCommand bookUpdateCommand){
        Book book=new Book();
        BeanUtils.copyProperties(bookUpdateCommand,book);
        book.setPublishTime(new Date());
        book.setId(id);
        return book;
    }

}
