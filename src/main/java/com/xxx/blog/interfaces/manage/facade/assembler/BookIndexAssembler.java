package com.xxx.blog.interfaces.manage.facade.assembler;

import com.xxx.blog.domain.model.BookIndex;
import com.xxx.blog.interfaces.manage.facade.command.NavigationCreateCommand;
import com.xxx.blog.interfaces.manage.facade.command.NavigationUpdateCommond;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * Created by xiexx on 2017/12/18.
 */
@Component
public class BookIndexAssembler {

    public BookIndex createCommandToDomain(NavigationCreateCommand bookIndexCreateCommand, String bookId){
        BookIndex bookIndex=new BookIndex();
        BeanUtils.copyProperties(bookIndexCreateCommand,bookIndex);
        bookIndex.setBookId(bookId);
        return bookIndex;
    }

    public BookIndex updateCommandToDomain(NavigationUpdateCommond bookIndexCreateCommand, String id) {
        BookIndex bookIndex=new BookIndex();
        BeanUtils.copyProperties(bookIndexCreateCommand,bookIndex);
        bookIndex.setId(id);
        return bookIndex;
    }
}
