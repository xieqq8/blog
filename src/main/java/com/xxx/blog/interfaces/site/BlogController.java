package com.xxx.blog.interfaces.site;

import org.apache.commons.lang3.StringUtils;
import com.xxx.blog.application.SiteConfigService;
import com.xxx.blog.interfaces.site.facade.SiteBlogFacade;
import com.xxx.blog.interfaces.site.facade.dto.BlogDetailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by xiexx on 2017/11/27.
 */
@Controller
public class BlogController {

    @Autowired
    protected SiteBlogFacade blogFacade;

    @Autowired
    private SiteConfigService siteConfigService;

//    @GetMapping("/index1")
//    public String mainIndex(Model model){
//
//        return "index1";
//    }

    //form1 stye start///////////////////////////////
    /**
     * form1
     * @param model
     * @return
     */
    @GetMapping("/indexform")
    public String indexform(Model model){
        model.addAttribute("pageModel", blogFacade.pageQuery(1, siteConfigService.pageSize()));
        model.addAttribute("catalog", "java");
        model.addAttribute("page", 1);
        model.addAttribute("isSearch",false);
        return "indexform";
    }

    @RequestMapping("/isearch")
    public String isearch(@RequestParam("s") String s, @RequestParam(value = "page", required = false, defaultValue = "1") int page, Model model) {
        model.addAttribute("pageModel", blogFacade.search(page, siteConfigService.pageSize(), s));
        model.addAttribute("catalog", "all");
        model.addAttribute("isSearch",true);
        model.addAttribute("page", page);
        model.addAttribute("s",s);
        return "indexform";
    }

    @GetMapping("/iblog/icatalog/{catalog}")
    public String icatalog(@PathVariable("catalog") String catalog, @RequestParam(value = "page", required = false, defaultValue = "1") int page, Model model) {
        model.addAttribute("pageModel", blogFacade.queryByCatalog(page, siteConfigService.pageSize(), catalog));
        model.addAttribute("catalog", catalog);
        model.addAttribute("page", page);
        model.addAttribute("isSearch",false);
        return "indexform";
    }

    @GetMapping("/iblog/{id}")
    public String iget(@PathVariable("id") String id, Model model, @CookieValue(name = "viewd", required = false) String viewd, HttpServletResponse response) {
        BlogDetailDto blog = blogFacade.getBlog(id);
        if(blog==null){
            return "404";
        }
        model.addAttribute("blog",blog);
        model.addAttribute("similars",blogFacade.getSimilarBlog(blog.getId(),blog.getTitle()));
        if (StringUtils.isBlank(viewd) || !viewd.equals("1")) {
            blogFacade.addViews(id);
            response.addCookie(new Cookie("viewd", "1"));
        }
        return "iformdetail";
    }
    //form1 stye end///////////////////////////////

    @GetMapping(value = {"/", "/blog", "/index.html"})
    public String index(Model model) {
        // 分页
        model.addAttribute("pageModel", blogFacade.pageQuery(1, siteConfigService.pageSize()));
        model.addAttribute("catalog", "java");
        model.addAttribute("page", 1);
        model.addAttribute("isSearch",false);
        return "catalog";
    }

    @GetMapping("/blog/{id}")
    public String get(@PathVariable("id") String id, Model model, @CookieValue(name = "viewd", required = false) String viewd, HttpServletResponse response) {
        BlogDetailDto blog = blogFacade.getBlog(id);
        if(blog==null){
            return "404";
        }
        model.addAttribute("blog",blog);
        model.addAttribute("similars",blogFacade.getSimilarBlog(blog.getId(),blog.getTitle()));
        if (StringUtils.isBlank(viewd) || !viewd.equals("1")) {
            blogFacade.addViews(id);
            response.addCookie(new Cookie("viewd", "1"));
        }
        return "blog";
    }

    @GetMapping("/blog/catalog/{catalog}")
    public String catalog(@PathVariable("catalog") String catalog, @RequestParam(value = "page", required = false, defaultValue = "1") int page, Model model) {
        model.addAttribute("pageModel", blogFacade.queryByCatalog(page, siteConfigService.pageSize(), catalog));
        model.addAttribute("catalog", catalog);
        model.addAttribute("page", page);
        model.addAttribute("isSearch",false);
        return "catalog";
    }

    @RequestMapping("/search")
    public String search(@RequestParam("s") String s, @RequestParam(value = "page", required = false, defaultValue = "1") int page, Model model) {
        model.addAttribute("pageModel", blogFacade.search(page, siteConfigService.pageSize(), s));
        model.addAttribute("catalog", "all");
        model.addAttribute("isSearch",true);
        model.addAttribute("page", page);
        model.addAttribute("s",s);
        return "catalog";
    }

}
