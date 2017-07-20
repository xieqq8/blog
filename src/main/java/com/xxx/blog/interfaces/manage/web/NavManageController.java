package com.xxx.blog.interfaces.manage.web;

import com.xxx.blog.interfaces.manage.facade.NavFacade;
import com.xxx.blog.interfaces.manage.facade.command.NavigationUpdateCommond;
import com.xxx.blog.interfaces.manage.facade.command.NavigationCreateCommand;
import org.bumishi.toolbox.model.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author qiang.xie
 * @date 2016/9/18
 */
@RestController
@RequestMapping("/admin/nav")
public class NavManageController {

    @Autowired
    protected NavFacade navFacade;


    @PostMapping(value = "/add")
    public RestResponse create(@RequestBody @Valid NavigationCreateCommand menu) {
        navFacade.add(menu);
        return RestResponse.ok();
    }


    @PostMapping(value = "/{id}/modify")
    public RestResponse modify(@PathVariable("id") String id,@RequestBody @Valid NavigationUpdateCommond menu) {
        navFacade.update(id,menu);
        return RestResponse.ok();
    }

    @PostMapping(value = "/{id}/status")
    public RestResponse switchStatus(@PathVariable("id") String id, @RequestParam("disable") boolean disable) {
       navFacade.switchStatus(id,disable);
        return RestResponse.ok();
    }

    @PostMapping(value = "/{id}/delete")
    public RestResponse delete(@PathVariable("id") String id) {
        navFacade.delete(id);
        return RestResponse.ok();
    }


    @GetMapping("/{id}")
    public RestResponse get(@PathVariable("id")String id){
        return RestResponse.ok(navFacade.get(id));
    }

    @GetMapping
    public RestResponse list() {
        return RestResponse.ok(navFacade.listByOrder());
    }


}
