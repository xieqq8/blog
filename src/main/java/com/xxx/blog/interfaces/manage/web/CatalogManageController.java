package com.xxx.blog.interfaces.manage.web;

import com.xxx.blog.interfaces.manage.facade.CatalogFacade;
import com.xxx.blog.interfaces.manage.facade.command.NavigationUpdateCommond;
import com.xxx.blog.interfaces.manage.facade.command.NavigationCreateCommand;
import com.xxx.toolbox.model.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author xiexx
 * @date 2016/9/18
 */
@RestController("adminCatalogController")
@RequestMapping("/admin/catalog")
public class CatalogManageController {

    @Autowired
  private CatalogFacade catalogFacade;



    @PostMapping(value = "/add")
    public RestResponse create(@RequestBody @Valid NavigationCreateCommand catalog) {
        catalogFacade.add(catalog);
        return RestResponse.ok();
    }


    @PostMapping(value = "/{id}/modify")
    public RestResponse modify(@PathVariable("id") String id,@RequestBody @Valid NavigationUpdateCommond catalog) {
            catalogFacade.update(id,catalog);
            return RestResponse.ok();
    }


    @PostMapping(value = "/{id}/status")
    @ResponseBody
    public RestResponse switchStatus(@PathVariable("id") String id, @RequestParam("disable") boolean disable) {
        catalogFacade.switchStatus(id,disable);
        return RestResponse.ok();
    }

    @PostMapping(value = "/{id}/delete")
    @ResponseBody
    public RestResponse delete(@PathVariable("id") String id) {
        catalogFacade.delete(id);
        return RestResponse.ok();
    }

    @GetMapping("/{id}")
    public RestResponse get(@PathVariable("id")String id){
        return RestResponse.ok(catalogFacade.get(id));
    }

    @GetMapping
    public RestResponse list() {
        return RestResponse.ok(catalogFacade.listByOrder());
    }


}
