package com.xxx.blog.interfaces.weixin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.sd4324530.fastweixin.message.Article;
import com.github.sd4324530.fastweixin.message.BaseMsg;
import com.github.sd4324530.fastweixin.message.NewsMsg;
import com.github.sd4324530.fastweixin.message.TextMsg;
import com.github.sd4324530.fastweixin.message.req.BaseEvent;
import com.github.sd4324530.fastweixin.message.req.TextReqMsg;
import com.github.sd4324530.fastweixin.message.req.VoiceReqMsg;
import com.github.sd4324530.fastweixin.servlet.WeixinSupport;
import com.google.common.collect.Lists;
import com.xxx.blog.domain.model.Blog;
import org.apache.commons.lang3.StringUtils;
import com.xxx.blog.application.BlogService;
import com.xxx.blog.application.SiteConfigService;
import org.bumishi.toolbox.model.PageModel;
import org.bumishi.toolbox.tulin.TulinClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiexx on 2017/1/15.
 */
@RestController
@RequestMapping("/weixin")
public class WeixinController extends WeixinSupport implements InitializingBean{
    private static final Logger log = LoggerFactory.getLogger(WeixinController.class);

    @Autowired
    protected BlogService blogService;

    @Autowired
    private SiteConfigService siteConfigService;

    @Value("${weixin.token:''}")
    private String token;

    @Value("${weixin.subscribe:''}")
    private String subscribeText;

    @Value("${tulin.key:''}")
    private String tulinKey;

    @Value("${tulin.secret:''}")
    private String tulinSecret;

    private TulinClient tulinClient;

    //设置TOKEN，用于绑定微信服务器
    @Override
    protected String getToken() {
        return token;
    }
    //使用安全模式时设置：APPID
    //不再强制重写，有加密需要时自行重写该方法
    @Override
    protected String getAppId() {
        return null;
    }

    /**
     * 绑定微信服务器
     *
     * @param request 请求
     * @return 响应内容
     */
    @RequestMapping(method = RequestMethod.GET)
    public String bind(HttpServletRequest request) {
        if (isLegal(request)) {
            log.debug("bind weixin success");
            //绑定微信服务器成功
            return request.getParameter("echostr");
        } else {
            log.warn("bind weixin fail");
            //绑定微信服务器失败
            return "";
        }
    }

    /**
     * 微信消息交互处理
     *
     * @param request http 请求对象
     * @param response http 响应对象
     * @throws ServletException 异常
     * @throws IOException      IO异常
     */
    @RequestMapping(method = RequestMethod.POST)
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (isLegal(request)) {
            String result = processRequest(request);
            //设置正确的 content-type 以防止中文乱码
            response.setContentType("text/xml;charset=UTF-8");
            PrintWriter writer = response.getWriter();
            writer.write(result);
            writer.close();
            log.info("write msg:{}",result);
        }else{
            log.warn("not legal");
        }
    }

    //重写父类方法，处理对应的微信消息
    @Override
    protected BaseMsg handleTextMsg(TextReqMsg msg) {
        String content = msg.getContent();
        log.debug("用户发送到服务器的text内容:{}", content);
        return createNewsMsg(content,msg.getFromUserName());
    }


    @Override
    protected BaseMsg handleVoiceMsg(VoiceReqMsg msg) {
        log.debug("用户发送到服务器的voice内容:{}", msg.getRecognition());
        return createNewsMsg(msg.getRecognition(),msg.getFromUserName());
    }

    @Override
    protected BaseMsg handleSubscribe(BaseEvent event) {
        return new TextMsg(subscribeText);
    }

    private BaseMsg createNewsMsg(String content,String  userid) {
        NewsMsg newsMsg=new NewsMsg();
        List<Article> articles=new ArrayList<>();
        Article defaultArticle=new Article("去xiexx博客网站看看吧","http://xiexx.cn");
        if(content.startsWith("/:") || content.equals("收到不支持的消息类型，暂无法显示")){
            articles.add(defaultArticle);
            newsMsg.setArticles(articles);
            return newsMsg;
        }
        //微信图文信息不能超过8条，否则用户会收不到信息
        PageModel<Blog> pageModel= blogService.search(1,8,content);
        if(pageModel==null || CollectionUtils.isEmpty(pageModel.getList())){
            BaseMsg tulinMsg=searchByTulin(content,userid);
            if(tulinMsg==null) {
                articles.add(defaultArticle);
                newsMsg.setArticles(articles);
                return newsMsg;
            }
            return  tulinMsg;
        }

        pageModel.getList().stream().forEach(blog -> {
            String link= StringUtils.isBlank(blog.getWechatLink()) ? "http://xiexx.cn"+blog.getLink() : blog.getWechatLink();
            Article article=new Article(blog.getFullTitle().replaceAll("<span class='kw'>","").replaceAll("</span>",""),link);
            article.setPicUrl(blog.getImg());
            articles.add(article);
        });
        newsMsg.setArticles(articles);
        return newsMsg;
    }

    private BaseMsg searchByTulin(String content,String userid) {
        JSONObject jsonObject = JSON.parseObject(tulinClient.post(content, userid));
        int code=jsonObject.getIntValue("code");
        if ( code== 100000){
                  return new TextMsg(jsonObject.getString("text"));
        }
        if(code==200000){
            NewsMsg newsMsg=new NewsMsg(1);
            newsMsg.setArticles(Lists.newArrayList(new Article(jsonObject.getString("text"),jsonObject.getString("url"))));
            return newsMsg;
        }
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.tulinClient=new TulinClient(tulinKey,tulinSecret);
    }
}
