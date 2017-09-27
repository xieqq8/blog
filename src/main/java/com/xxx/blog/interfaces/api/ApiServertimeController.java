package com.xxx.blog.interfaces.api;

import com.xxx.blog.interfaces.manage.facade.CatalogFacade;
import com.xxx.toolbox.model.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 提供admin管理接口
 * Created by xiexx on 2017/11/27.
 */
@RestController("ApiServertimeController")  // 返回JSON
@RequestMapping("/api/server_time")
public class ApiServertimeController {

//    {"success":true,"code":null,"msg":null,"data":null}

    /**
     * 博客分类
     * @return
     */
    @GetMapping
    public RestResponse list() {
        return RestResponse.ok();
    }

    class retcurdate{

    }
}
