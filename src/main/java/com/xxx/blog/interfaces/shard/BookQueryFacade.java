package com.xxx.blog.interfaces.shard;

import com.xxx.blog.application.BookService;
import com.xxx.blog.domain.model.Book;
import org.bumishi.toolbox.model.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by xiexx on 2017/1/6.
 */
@Service
public class BookQueryFacade {

    @Autowired
    private BookDtoAssembler bookDtoAssembler;

    @Autowired
    private BookService bookService;


    public PageModel<BookDto> pageQuery(int page, int size) {
        PageModel<Book> blogPageModel = bookService.queryByTime(page, size);
        return getBookDtoPageModel(blogPageModel);
    }

    public PageModel<BookDto> queryByCatalog(int page, int size,String catalog) {
        PageModel<Book> blogPageModel = bookService.queryByCatalog(page, size,catalog);
        return getBookDtoPageModel(blogPageModel);
    }

    private PageModel<BookDto> getBookDtoPageModel(PageModel<Book> blogPageModel) {
        PageModel<BookDto> pageModel = new PageModel();
        pageModel.setHasNext(blogPageModel.isHasNext());
        pageModel.setPage(blogPageModel.getPage());
        pageModel.setSize(blogPageModel.getSize());
        if (!CollectionUtils.isEmpty(blogPageModel.getList())) {
            List<BookDto> blogDtos = blogPageModel.getList().stream().map(blog -> bookDtoAssembler.toDto(blog)).collect(Collectors.toList());
            pageModel.setList(blogDtos);
        }
        return pageModel;
    }


}
