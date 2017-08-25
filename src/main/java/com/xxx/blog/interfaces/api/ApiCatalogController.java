package com.xxx.blog.interfaces.api;

import com.xxx.blog.interfaces.manage.facade.BlogFacade;
import com.xxx.blog.interfaces.manage.facade.CatalogFacade;
import com.xxx.blog.interfaces.manage.facade.command.NavigationCreateCommand;
import com.xxx.blog.interfaces.manage.facade.command.NavigationUpdateCommond;
import com.xxx.blog.interfaces.manage.facade.command.WriteBlogCommand;
import com.xxx.toolbox.model.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 提供admin管理接口
 * Created by xiexx on 2017/11/27.
 */
@RestController("apiCatalogController")  // 返回JSON
@RequestMapping("/api/blog_catalog")
public class ApiCatalogController {

    @Autowired
    private CatalogFacade catalogFacade;

    /**
     * 博客分类
     * @return
     */
    @GetMapping
    public RestResponse list() {
        return RestResponse.ok(catalogFacade.listByOrder());
    }
//    {
//        "success": true,
//        "code": null,
//        "msg": null,
//        "data": [
//        {
//            "id": "java",
//                "label": "Java",
//                "path": "0",
//                "order": 10,
//                "type": 0,
//                "style": null,
//                "disabled": false,
//                "childNodes": [],
//            "url": "/catalog/Java",
//                "level": 1
//        },
//        {
//            "id": "neihan",
//                "label": "男人就要有内涵",
//                "path": "0",
//                "order": 20,
//                "type": 0,
//                "style": null,
//                "disabled": false,
//                "childNodes": [],
//            "url": "",
//                "level": 1
//        },
//        {
//            "id": "spring",
//                "label": "spring",
//                "path": "0",
//                "order": 10,
//                "type": 1,
//                "style": null,
//                "disabled": false,
//                "childNodes": [],
//            "url": "",
//                "level": 1
//        },
//        {
//            "id": "yunwei",
//                "label": "女人就该有韵味",
//                "path": "0",
//                "order": 30,
//                "type": 0,
//                "style": null,
//                "disabled": false,
//                "childNodes": [],
//            "url": "",
//                "level": 1
//        }
//        ]
//    }
}
