package com.xxx.blog.interfaces.api;

import com.xxx.blog.interfaces.manage.facade.BlogFacade;
import com.xxx.blog.interfaces.manage.facade.command.WriteBlogCommand;
import com.xxx.toolbox.model.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * 提供admin管理接口
 * Created by xiexx on 2017/11/27.
 */
@RestController("apiBlogController")  // 返回JSON
@RequestMapping("/api/blog")
public class ApiBlogController {

    @Autowired
    private BlogFacade blogFacade;


//    @PostMapping("/add")
//    public RestResponse addBlog(@RequestBody @Valid WriteBlogCommand blog){
//        blogFacade.createBlog(blog);
//        return RestResponse.ok();
//    }
//
//    @PostMapping("/{id}/update")
//    public RestResponse updateBlog(@PathVariable("id")String id,@RequestBody @Valid WriteBlogCommand blog){
//        blogFacade.updateBlog(id,blog);
//        return RestResponse.ok();
//    }
//
//    @PostMapping(value = "/{id}/delete")
//    public RestResponse delete(@PathVariable("id") String id) {
//        blogFacade.delete(id);
//        return RestResponse.ok();
//    }
//
//    @GetMapping("/{id}")
//    public RestResponse get(@PathVariable("id")String id){
//            return RestResponse.ok(blogFacade.getBlog(id));
//    }

    /**
     * 读blog
     * @param page
     * @return
     */
    @GetMapping()
    public RestResponse get(@RequestParam(value = "page",required = false,defaultValue = "1") int page){
        return RestResponse.ok(blogFacade.pageQuery(page,20));
    }

//    @PostMapping("/es_index/update")
//    public RestResponse updateEsIndex(){
//     blogFacade.updateIndex();
//        return RestResponse.ok();
//    }

    /**
     * 评论
     * @param blog
     * @return
     */
    @PostMapping("/comment")
    public  RestResponse commentBlog(@RequestBody @Valid WriteBlogCommand blog){
        return RestResponse.ok();
    }



    /**
     * 得到评论
     * @param page
     * @return
     */
    @GetMapping("/getcomment")
    public  RestResponse getcomment(@RequestParam(value = "page",required = false,defaultValue = "1") int page){
        return RestResponse.ok();
    }

// form表单的 简写的  就调用默认的@RequestParam来处理。
//    @RequestMapping(value = "/dologin", method = RequestMethod.POST)
//    public String dologin(String userName,String password,Model model) throws UnsupportedEncodingException {
//        System.out.println("userName is:"+userName);
//        System.out.println("password is:"+password);
//        if (!"123456".equals(password)) {
//            // http://www.cnblogs.com/wuduliantian/p/4936378.html :get请求URL传值时中文乱码解决办法
//            String ss = java.net.URLEncoder.encode("用户名或密码错误","utf-8");
//            return "redirect:/admin/login?message=" + ss;
//        } else {
//            return "redirect:/admin/index"; // 跳到新的连接
//        }
//    }


//    @RequestMapping("/pets/{petId}")   //  若方法参数名称和需要绑定的uri template中变量名称不一致，需要在@PathVariable("name")指定uri template中的名称。
////  public void findPet(@PathVariable String ownerId, @PathVariable String petId, Model model) {
//    public void findPet(@PathVariable("petId") String petId, Model model) {
//        // implementation omitted
//    }

//    /**
//     * 微信消息交互处理
//     *
//     * @param request http 请求对象
//     * @param response http 响应对象
//     * @throws ServletException 异常
//     * @throws IOException      IO异常
//     */
//    @RequestMapping(method = RequestMethod.POST)
//    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//        if (isLegal(request)) {
//            String result = processRequest(request);
//            //设置正确的 content-type 以防止中文乱码
//            response.setContentType("text/xml;charset=UTF-8");
//            PrintWriter writer = response.getWriter();
//            writer.write(result);
//            writer.close();
//            log.info("write msg:{}",result);
//        }else{
//            log.warn("not legal");
//        }
//    }

//    示例：
//    @RequestMapping ({"/", "/home"})
//    public String showHomePage(String key){
//
//        logger.debug("key="+key);
//
//        return "home";
//    }
//    这种情况下，就调用默认的@RequestParam来处理。
//
//    @RequestMapping (method = RequestMethod.POST)
//    public String doRegister(User user){
//        if(logger.isDebugEnabled()){
//            logger.debug("process url[/user], method[post] in "+getClass());
//            logger.debug(user);
//        }
//
//        return "user";
//    }
//
//    这种情况下，就调用@ModelAttribute来处理。
}
