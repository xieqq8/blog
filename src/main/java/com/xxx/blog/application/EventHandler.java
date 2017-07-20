package com.xxx.blog.application;

import com.google.common.eventbus.Subscribe;
import com.xxx.blog.application.thirdsync.jianshu.BlogToJianShu;
import com.xxx.blog.domain.model.event.*;
import com.xxx.blog.domain.repository.BlogCommandRepositry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author qiang.xie
 * @date 2017/1/7
 */
@Component
public class EventHandler {

    public static final String BLOG_CACHE = "blog";

    public static final String BLOG_PAGE_CACHE = "blog_page";

    public static final String BOOK_CACHE = "book";

    public static final String BOOK_PAGE_CACHE = "book_page";

    public static final String BOOK_INDEX_CACHE = "book_index";

    public static final String BOOK_INDEX_PAGE_CACHE = "book_index_page";

    public static final String NAV_CACHE = "nav";

    public static final String NAV_PAGE_CACHE = "nav_page";

    public static final String CATALOG_CACHE = "catalog";

    public static final String CATALOG_PAGE_CACHE = "catalog_page";

    private Logger logger= LoggerFactory.getLogger(EventHandler.class);

    @Autowired
    protected CacheManager cacheManager;

    @Autowired
    @Qualifier("blogCommandJdbcRepositry")
    protected BlogCommandRepositry blogCommandJdbcRepositry;

    @Autowired
    protected BlogToJianShu blogToJianShu;

    @Value("${sync.jianshu}")
    private boolean sync_jianshu;

    private ExecutorService executorService= Executors.newFixedThreadPool(1);

    @Subscribe
    public void onBlogUpdate(BlogUpdateEvent blogUpdateEvent) {
        cacheManager.getCache(BLOG_CACHE).put(blogUpdateEvent.getBlog().getId(), blogUpdateEvent.getBlog());
        cacheManager.getCache(BLOG_PAGE_CACHE).clear();
        blogCommandJdbcRepositry.save(blogUpdateEvent.getBlog());

        if(sync_jianshu) {
            executorService.execute(() -> {
                try {
                    blogToJianShu.sync(blogUpdateEvent.getBlog());
                } catch (Exception e) {
                    logger.warn("sync jianshu error", e);
                }
            });
        }
    }

    @Subscribe
    public void onBlogDelete(BlogDeleteEvent blogUpdateEvent) {
        cacheManager.getCache(BLOG_CACHE).evict(blogUpdateEvent.getBlogId());
        cacheManager.getCache(BLOG_PAGE_CACHE).clear();
        blogCommandJdbcRepositry.remove(blogUpdateEvent.getBlogId());
    }

    @Subscribe
    public void onBookUpdate(BookUpdateEvent blogUpdateEvent) {
        cacheManager.getCache(BOOK_CACHE).put(blogUpdateEvent.getBook().getId(), blogUpdateEvent.getBook());
        cacheManager.getCache(BOOK_PAGE_CACHE).clear();

    }

    @Subscribe
    public void onBookDelete(BookDeleteEvent blogUpdateEvent) {
        cacheManager.getCache(BOOK_CACHE).evict(blogUpdateEvent.getBookId());
        cacheManager.getCache(BOOK_PAGE_CACHE).clear();

        cacheManager.getCache(BOOK_INDEX_CACHE).clear();
        cacheManager.getCache(BOOK_INDEX_PAGE_CACHE).evict(blogUpdateEvent.getBookId());
    }

    @Subscribe
    public void onBookIndexUpdate(BookIndexUpdateEvent blogUpdateEvent) {
        cacheManager.getCache(BOOK_INDEX_CACHE).put(blogUpdateEvent.getBookIndex().getId(), blogUpdateEvent.getBookIndex());
        cacheManager.getCache(BOOK_INDEX_PAGE_CACHE).clear();
    }

    @Subscribe
    public void onBookIndexDelete(BookIndexDeleteEvent blogUpdateEvent) {
        cacheManager.getCache(BOOK_INDEX_CACHE).evict(blogUpdateEvent.getBookIndexId());
        cacheManager.getCache(BOOK_INDEX_PAGE_CACHE).clear();
    }

    @Subscribe
    public void onNavUpdate(NavUpdateEvent blogUpdateEvent) {
        cacheManager.getCache(NAV_CACHE).put(blogUpdateEvent.getNav().getId(), blogUpdateEvent.getNav());
        cacheManager.getCache(NAV_PAGE_CACHE).clear();
    }

    @Subscribe
    public void onNavDelete(NavDeleteEvent blogUpdateEvent) {
        cacheManager.getCache(NAV_CACHE).evict(blogUpdateEvent.getId());
        cacheManager.getCache(NAV_PAGE_CACHE).clear();
    }


    @Subscribe
    public void onCatalogvUpdate(CatalogUpdateEvent blogUpdateEvent) {
        cacheManager.getCache(CATALOG_CACHE).put(blogUpdateEvent.getCatalog().getId(), blogUpdateEvent.getCatalog());
        cacheManager.getCache(CATALOG_PAGE_CACHE).clear();
    }

    @Subscribe
    public void onCatalogDelete(CatalogDeleteEvent blogUpdateEvent) {
        cacheManager.getCache(CATALOG_CACHE).evict(blogUpdateEvent.getId());
        cacheManager.getCache(CATALOG_PAGE_CACHE).clear();
    }
}
