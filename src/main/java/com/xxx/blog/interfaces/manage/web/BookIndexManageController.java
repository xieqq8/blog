package com.xxx.blog.interfaces.manage.web;

import com.xxx.blog.interfaces.manage.facade.BookIndexFacade;
import com.xxx.blog.interfaces.manage.facade.command.NavigationUpdateCommond;
import com.xxx.blog.interfaces.manage.facade.command.NavigationCreateCommand;
import org.bumishi.toolbox.model.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author xiexx
 * @date 2016/9/18
 */
@RestController
@RequestMapping("/admin/bookindex")
public class BookIndexManageController {

    @Autowired
    protected BookIndexFacade bookIndexFacade;


    @PostMapping(value = "/{bookId}/add")
    public RestResponse create(@PathVariable("bookId")String bookId,@RequestBody @Valid NavigationCreateCommand menu) {
        bookIndexFacade.add(menu,bookId);
        return RestResponse.ok();
    }


    @PostMapping(value = "/{id}/modify")
    public RestResponse modify(@PathVariable("id") String id, @RequestBody @Valid NavigationUpdateCommond menu) {
        bookIndexFacade.update(menu, id);
        return RestResponse.ok();
    }

    @PostMapping(value = "/{id}/delete")
    public RestResponse delete(@PathVariable("id") String id) {
        bookIndexFacade.delete(id);
        return RestResponse.ok();
    }

    @PostMapping(value = "/{id}/status")
    @ResponseBody
    public RestResponse switchStatus(@PathVariable("id") String id, @RequestParam("disable") boolean disable) {
        bookIndexFacade.switchStatus(id, disable);
        return RestResponse.ok();
    }


    @GetMapping("/{id}")
    public RestResponse get(@PathVariable("id")String id){
        return RestResponse.ok(bookIndexFacade.get(id));
    }


}
