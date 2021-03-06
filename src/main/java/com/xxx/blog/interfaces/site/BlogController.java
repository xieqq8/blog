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
     * 首页
     * @param model
     * @return
     */
    @GetMapping(value = {"/", "/blog", "/index.html", "/index"})
    public String indexform(Model model){
        // 总条数，算分页

        //
        model.addAttribute("pageModel", blogFacade.pageQuery(1, siteConfigService.pageSize()));
        model.addAttribute("icatalog", "all");   // 分类
        model.addAttribute("page", 1);          // 当前页数
        model.addAttribute("isSearch",false);   // 不是搜索
        return "indexform";
    }

    /**
     * 查找
     * @param s
     * @param page
     * @param model
     * @return
     */
    @RequestMapping("/isearch")
    public String isearch(@RequestParam("s") String s, @RequestParam(value = "page", required = false, defaultValue = "1") int page, Model model) {
        model.addAttribute("pageModel", blogFacade.search(page, siteConfigService.pageSize(), s));
        model.addAttribute("icatalog", "all");
        model.addAttribute("isSearch",true);
        model.addAttribute("page", page);
        model.addAttribute("s",s);
        return "indexform";
    }

    /**
     * 分类显示
     * @param catalog
     * @param page
     * @param model
     * @return
     */
    @GetMapping("/iblog/icatalog/{catalog}")
    public String icatalog(@PathVariable("catalog") String catalog, @RequestParam(value = "page", required = false, defaultValue = "1") int page, Model model) {
        model.addAttribute("pageModel", blogFacade.queryByCatalog(page, siteConfigService.pageSize(), catalog));
        model.addAttribute("icatalog", catalog);

        System.out.println("--------------icatalog url match:" + catalog);

        model.addAttribute("page", page);
        model.addAttribute("isSearch",false);
        return "indexform";
    }

    /**
     * 博文详情
     * @param id
     * @param model
     * @param viewd
     * @param response
     * @return
     */
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

    @GetMapping("/indexform") // 默认路径调整
    public String index(Model model) {
        // 总条数，算分页
        model.addAttribute("pageModel", blogFacade.pageQuery(1, siteConfigService.pageSize()));
        model.addAttribute("catalog", "all");
        model.addAttribute("page", 1);
        model.addAttribute("isSearch",false);
        return "indexform";
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
