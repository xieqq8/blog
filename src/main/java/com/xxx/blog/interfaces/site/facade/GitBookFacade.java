package com.xxx.blog.interfaces.site.facade;

import com.xxx.blog.application.BookService;
import com.xxx.blog.application.CatalogService;
import com.xxx.blog.domain.model.Book;
import com.xxx.blog.interfaces.site.facade.dto.GitBookDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiexx
 * @date 2016/12/27
 */
@Service
public class GitBookFacade {

    @Autowired
    private CatalogService catalogService;

    @Autowired
    private BookService bookService;

    public GitBookDto getGitBook(String bookId) {
        Book book = bookService.getBook(bookId);
        if (book == null) {
            return null;
        }
        GitBookDto gitBookDto = new GitBookDto();
        BeanUtils.copyProperties(book, gitBookDto);
        gitBookDto.setCatalogDisplay(catalogService.getCatalog(book.getCatalog()).getLabel());
        gitBookDto.setIndexs(bookService.listIndexsByBookId(bookId));
        return gitBookDto;
    }
}
