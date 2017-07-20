package com.xxx.blog.application.thirdsync.jianshu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import com.xxx.blog.domain.model.Blog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 同步博客到简书
 * @author xiexx
 * @date 2017/4/1
 */
@Component
public class BlogToJianShu {

    public final static String USER_AGENT="Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36";

    private Logger logger= LoggerFactory.getLogger(BlogToJianShu.class);

    public void sync(Blog blog) throws Exception{
        int i = 150 + new Random().nextInt(100000);
        String x_write_version = (i++) + "";
        //get id
        HttpPost noteidPost = new HttpPost("http://www.jianshu.com/writer/notes");
        noteidPost.addHeader("x-writer-version", x_write_version);
        JSONObject noteidJson = new JSONObject();
        noteidJson.put("id", "c-25");
        noteidJson.put("notebook_id", Integer.parseInt(catalogToJianshu(blog)));
        noteidJson.put("seq_in_nb", 0);
        noteidJson.put("title", blog.getTitle());
        noteidPost.setEntity(new StringEntity(noteidJson.toJSONString()));
        CloseableHttpClient httpClient=getHttpClient();
        JSONObject noteidRepJson = executeForJson(httpClient,noteidPost);
        if (noteidRepJson == null || noteidRepJson.getString("id") == null) {
            return;
        }
        //write note
        JSONObject jb = new JSONObject();
        jb.put("title", blog.getTitle());
        jb.put("content", blog.getMd());
        jb.put("shared", false);
        jb.put("seq_in_nb", 0);
        jb.put("notebook_id", Integer.parseInt(catalogToJianshu(blog)));
        jb.put("note_type", "plain");
        jb.put("last_compiled_at", 0);
        jb.put("content_updated_at", System.currentTimeMillis() + "");
        jb.put("content_size_status", "fine");
        jb.put("autosave_control", 3);
        jb.put("slug", noteidRepJson.getString("slug"));
        jb.put("id", noteidRepJson.getIntValue("id"));

        HttpPut httpPost = new HttpPut("http://www.jianshu.com/writer/notes/" + noteidRepJson.getIntValue("id"));
        httpPost.addHeader("x-writer-version", x_write_version);
        httpPost.addHeader("Referer", "http://www.jianshu.com/writer");
        httpPost.setEntity(new StringEntity(jb.toJSONString(),"utf-8"));
        executeForStr(httpClient,httpPost);

        //publish
        HttpPost publishPost = new HttpPost("http://www.jianshu.com/writer/notes/" + noteidRepJson.getIntValue("id") + "/publicize");
        publishPost.addHeader("x-writer-version", x_write_version);
        publishPost.addHeader("Referer", "http://www.jianshu.com/writer");
//                    publishPost.setEntity(new StringEntity(jb.toJSONString()));
        executeForStr(httpClient,publishPost);


    }

    private String catalogToJianshu(Blog blog){
        if("java".equalsIgnoreCase(blog.getCatalog())){
            return "9264954";
        }
        return "9264954";
    }

    public JSONObject executeForJson(CloseableHttpClient httpClient,HttpUriRequest target) throws IOException {
        return JSON.parseObject(executeForStr(httpClient,target));
    }


    public String executeForStr(CloseableHttpClient httpClient,HttpUriRequest target) throws IOException {
        String rep= EntityUtils.toString(httpClient.execute(target).getEntity());
        logger.info(target.getURI()+" "+target.getMethod());
        logger.info("response:{}",rep);
        return rep;

    }



    private CloseableHttpClient getHttpClient() throws IOException {
            List<Header> headers = new ArrayList<>();
        String cookine=PropertiesLoaderUtils.loadAllProperties("sync.properties").getProperty("jianshu.cookie");
        logger.warn("jianshu.cookie",cookine);
            headers.add(new BasicHeader("Cookie", cookine));
            headers.add(new BasicHeader("X-Requested-With", "XMLHttpRequest"));
            headers.add(new BasicHeader("Content-Type", "application/json"));

            headers.add(new BasicHeader("X-CSRF-Token", "S56DkKonpxvH4iwjB6mGUjclN/qhOpSEufxACE/Kh+mboc/WD6Uq7Ri9b9adZNwCOfCCFCXAgwJmFLrUoJQKJw=="));
            return HttpClients.custom().setUserAgent(USER_AGENT).setDefaultHeaders(headers).build();
    }


}
