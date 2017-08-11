package com.xxx.blog.interfaces.site;

import com.xxx.blog.application.SiteConfigService;
import com.xxx.blog.domain.model.BookIndex;
import com.xxx.blog.domain.model.GitBookCurrentIndex;
import com.xxx.blog.interfaces.shard.BookQueryFacade;
import com.xxx.blog.interfaces.site.facade.SiteBlogFacade;
import com.xxx.blog.interfaces.site.facade.dto.GitBookDto;
import com.xxx.blog.interfaces.site.facade.GitBookFacade;
import com.xxx.blog.interfaces.site.facade.dto.BlogDetailDto;
import com.xxx.toolbox.model.TreeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author xiexx
 * @date 2016/12/27
 */
@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookQueryFacade bookFacade;

    @Autowired
    private GitBookFacade gitBookFacade;

    @Autowired
    protected SiteBlogFacade blogFacade;

    @Autowired
    private SiteConfigService siteConfigService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("pageModel", bookFacade.pageQuery(1, siteConfigService.pageSize()));
        model.addAttribute("catalog", "java");
        model.addAttribute("page", 1);
        return "book-catalog";
    }

    @GetMapping("/{id}")
    public String get(@PathVariable("id") String id, Model model) {
        GitBookDto book = gitBookFacade.getGitBook(id);
        if (book == null || CollectionUtils.isEmpty(book.getIndexs())) {
            return "404";
        }
        List<BookIndex> indexList = book.getIndexs();
        List<BookIndex> indexTree = (List<BookIndex>) new TreeModel(book.getIndexs()).buildTree();
        book.setIndexs(indexTree);

        model.addAttribute("book", book);
        BookIndex first = book.getIndexs().get(0);
        String blogId = first.getUrl().substring(first.getUrl().lastIndexOf("/") + 1);
        BlogDetailDto blog = blogFacade.getBlog(blogId);
        if (blog == null) {
            return "404";
        }
        model.addAttribute("blog", blog);
        GitBookCurrentIndex currentIndex = new GitBookCurrentIndex(indexList);
        currentIndex.build("/book/" + id + "/blog/" + blogId);
        model.addAttribute("currentIndex", currentIndex);
        return "book";
    }

    @GetMapping("/catalog/{catalog}")
    public String catalog(@PathVariable("catalog") String catalog, @RequestParam(value = "page", required = false, defaultValue = "1") int page, Model model) {
        model.addAttribute("pageModel", bookFacade.queryByCatalog(page, siteConfigService.pageSize(), catalog));
        model.addAttribute("catalog", catalog);
        model.addAttribute("page", page);
        return "book-catalog";
    }


    @GetMapping("/{bookId}/blog/{blogId}")
    public String getBookBlog(@PathVariable("bookId") String bookId, @PathVariable("blogId") String blogId, Model model) {
        GitBookDto book = gitBookFacade.getGitBook(bookId);
        if (book == null) {
            return "404";
        }
        model.addAttribute("book", book);
        BlogDetailDto blog = blogFacade.getBlog(blogId);
        if (blog == null) {
            return "404";
        }
        model.addAttribute("blog", blog);
        List<BookIndex> indexList = book.getIndexs();
        List<BookIndex> indexTree = (List<BookIndex>) new TreeModel(book.getIndexs()).buildTree();
        book.setIndexs(indexTree);
        GitBookCurrentIndex currentIndex = new GitBookCurrentIndex(indexList);
        currentIndex.build("/book/" + bookId + "/blog/" + blogId);
        model.addAttribute("currentIndex", currentIndex);
        return "book";
    }
}
