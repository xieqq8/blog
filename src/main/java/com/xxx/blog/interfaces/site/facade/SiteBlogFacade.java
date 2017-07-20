package com.xxx.blog.interfaces.site.facade;

import com.xxx.blog.application.BlogService;
import com.xxx.blog.application.CatalogService;
import com.xxx.blog.domain.model.Blog;
import com.xxx.blog.domain.repository.BlogViewsRepostiry;
import com.xxx.blog.interfaces.site.facade.dto.LinkBlog;
import com.xxx.blog.interfaces.site.facade.dto.SimpleBlog;
import com.xxx.blog.interfaces.site.facade.dto.BlogDetailDto;
import org.bumishi.toolbox.model.PageModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author qiang.xie
 * @date 2016/12/27
 */
@Service
public class SiteBlogFacade {

    @Autowired
    private BlogService blogService;

    @Autowired
    private BlogViewsRepostiry blogViewsRepostiry;

    @Autowired
    private CatalogService catalogService;


    public BlogDetailDto getBlog(String id) {
        Blog blog = blogService.getBlog(id);
        if (blog == null) {
            return null;
        }
        return toBlogDetailDto(blog);
    }

    private BlogDetailDto toBlogDetailDto(Blog blog) {
        BlogDetailDto dto = new BlogDetailDto();
        BeanUtils.copyProperties(blog, dto);
        dto.setCatalogDisplay(catalogService.getCatalog(blog.getCatalog()).getLabel());
        dto.setViews(blogViewsRepostiry.getViews(blog.getId()));
        return dto;
    }


    private SimpleBlog toSimpleBlog(Blog blog) {
        SimpleBlog dto = new SimpleBlog();
       dto.setPublishTime(blog.getPublishTime());
        dto.setCatalogDisplay(catalogService.getCatalog(blog.getCatalog()).getLabel());
        dto.setLink(blog.getLink());
        dto.setSummary(blog.getDisplay());
        dto.setCatalog(blog.getCatalog());
        dto.setTitle(blog.getFullTitle());
        return dto;
    }


    public PageModel<SimpleBlog> pageQuery(int page, int size) {
        PageModel<Blog> blogPageModel = blogService.queryByTime(page, size);
        return getSiteBlogDtoPageModel(blogPageModel);
    }


    public PageModel<SimpleBlog> queryByCatalog(int page, int size, String catalog) {
        PageModel<Blog> blogPageModel = blogService.queryByCatalog(page, size, catalog);
        return getSiteBlogDtoPageModel(blogPageModel);
    }

    public PageModel<SimpleBlog> search(int page, int size, String keywords) {
        PageModel<Blog> blogPageModel = blogService.search(page, size, keywords);
        return getSiteBlogDtoPageModel(blogPageModel);
    }

    private PageModel<SimpleBlog> getSiteBlogDtoPageModel(PageModel<Blog> blogPageModel) {
        if (blogPageModel == null) {
            return null;
        }
        PageModel<SimpleBlog> pageModel = new PageModel();
        pageModel.setHasNext(blogPageModel.isHasNext());
        pageModel.setPage(blogPageModel.getPage());
        pageModel.setSize(blogPageModel.getSize());
        if (!CollectionUtils.isEmpty(blogPageModel.getList())) {
            List<SimpleBlog> blogDtos = blogPageModel.getList().stream().map(blog -> toSimpleBlog(blog)).collect(Collectors.toList());
            pageModel.setList(blogDtos);
        }
        return pageModel;
    }

    public void addViews(String blogId) {
        blogViewsRepostiry.addViews(blogId);
    }


    public List<LinkBlog> getSimilarBlog(String id, String title){
        List<Blog> list = blogService.getSimilarBlogs(title);
        return list.stream().filter(blog->!blog.getId().equals(id)).map(item->{
                     LinkBlog linkBlog=new LinkBlog();
            linkBlog.setTitle(item.getFullTitle());
            linkBlog.setLink(item.getLink());
            return linkBlog;
        }).collect(Collectors.toList());
    }
}
