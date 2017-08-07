package com.xxx.blog.interfaces.manage.web;

import com.xxx.blog.interfaces.manage.facade.BlogFacade;
import com.xxx.blog.interfaces.manage.facade.command.WriteBlogCommand;
import org.bumishi.toolbox.model.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 提供admin管理接口
 * Created by xiexx on 2017/11/27.
 */
@RestController("adminBlogController")  // 返回JSON
@RequestMapping("/admin/blog")
public class BlogManageController {


    @Autowired
    private BlogFacade blogFacade;


    @PostMapping("/add")
    public RestResponse addBlog(@RequestBody @Valid WriteBlogCommand blog){
        blogFacade.createBlog(blog);
        return RestResponse.ok();
    }

    @PostMapping("/{id}/update")
    public RestResponse updateBlog(@PathVariable("id")String id,@RequestBody @Valid WriteBlogCommand blog){
        blogFacade.updateBlog(id,blog);
        return RestResponse.ok();
    }

    @PostMapping(value = "/{id}/delete")
    public RestResponse delete(@PathVariable("id") String id) {
        blogFacade.delete(id);
        return RestResponse.ok();
    }

    @GetMapping("/{id}")
    public RestResponse get(@PathVariable("id")String id){
            return RestResponse.ok(blogFacade.getBlog(id));
    }

    @GetMapping()
    public RestResponse get(@RequestParam(value = "page",required = false,defaultValue = "1") int page){
        return RestResponse.ok(blogFacade.pageQuery(page,20));
    }


    @PostMapping("/es_index/update")
    public RestResponse updateEsIndex(){
     blogFacade.updateIndex();
        return RestResponse.ok();
    }
}
