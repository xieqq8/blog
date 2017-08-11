package com.xxx.blog.interfaces.manage.facade.assembler;

import com.xxx.blog.domain.model.Blog;
import com.xxx.blog.domain.model.BlogId;
import com.xxx.blog.domain.model.MarkDownToHtml;
import com.xxx.blog.interfaces.manage.facade.command.WriteBlogCommand;
import com.xxx.blog.interfaces.manage.facade.dto.BlogDto;
import com.xxx.toolbox.model.repositry.NavigationNodeRepositry;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by xiexx on 2017/12/4.
 */
@Component
public class BlogAssember {

    @Autowired
    protected BlogId blogId;

    @Autowired
    @Qualifier("catalogJdbcRepositry")
    protected NavigationNodeRepositry navigationNodeRepositry;

    @Autowired
    protected MarkDownToHtml markDownToHtml;

    public Blog addBlogCommandToDomain(WriteBlogCommand command){
        Blog blog=new Blog();
        BeanUtils.copyProperties(command,blog);
        blog.setAuther("xiexx");//多用户博客程序情况下扩展字段
        blog.setPublishTime(new Date());
        blog.setId(blogId.id(blog));
        blog.setDisplay(markDownToHtml.convert(command));
        blog.setImg(getImg(blog.getDisplay()));
        return blog;
    }

    public Blog updateBlogCommandToDomain(String id,WriteBlogCommand command){
        Blog blog=new Blog();
        BeanUtils.copyProperties(command,blog);
        blog.setAuther("xiexx");//多用户博客程序情况下扩展字段
        blog.setPublishTime(new Date());
        blog.setId(id);
        blog.setDisplay(markDownToHtml.convert(command));
        blog.setImg(getImg(blog.getDisplay()));
        return blog;
    }

    public BlogDto toDto(Blog blog){
        BlogDto blogDto=new BlogDto();
        BeanUtils.copyProperties(blog,blogDto);
        blogDto.setCatalogDisplay(navigationNodeRepositry.get(blog.getCatalog()).getLabel());
        return blogDto;
    }

    private String getImg(String html){
       Elements elements= Jsoup.parse(html).getElementsByTag("img");
        if(elements==null || elements.size()==0){
            return "http://static.xiexx.cn/xiexx_slog.png";
        }
        return elements.get(0).attr("src");
    }
}
