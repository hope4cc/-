package com.paopao.commandLineRunner;


import com.paopao.cache.GlobalCache;
import com.paopao.config.ContantsContext;
import com.paopao.logger.SysLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitRunner implements CommandLineRunner {

    @Autowired
    GlobalCache globalCache;
    @Value("${paopao.baseurl}")
    public String baseURl;
    @Value("${paopao.pageSize}")
    public int pageSize;
    @Value("${server.servlet.context-path}")
    public String contextPath;

    @Override
    public void run(String... args) throws Exception {
        SysLogger.info("缓存预存备用数据中");
        globalCache.addCache("_author","caicai");
        globalCache.addCache("paopao","caicai daughter");

        String author = globalCache.getCacheValue("_author");
        String paopao = globalCache.getCacheValue("paopao");

        SysLogger.info("本系统作者为:" + author);
        SysLogger.info("paopao系统是什么:" + paopao);

        ContantsContext.BASE_URL = baseURl;
        ContantsContext.PAGER_SIZE = pageSize;
        ContantsContext.CONTEXT_PATH = contextPath;
        ContantsContext.FIRST_REGISTER_URL = ContantsContext.CONTEXT_PATH + "/main/userData";
        ContantsContext.COMMENT_PREFIX_URL = ContantsContext.CONTEXT_PATH + "/main/messageDetail";
        ContantsContext.FOLLOW_PREFIX_URL = ContantsContext.CONTEXT_PATH + "/main/userCard";
        ContantsContext.LIKE_PREFIX_URL = ContantsContext.CONTEXT_PATH + "/main/messageDetail";
        ContantsContext.NEW_MESSAGE_PREFIX_URL = ContantsContext.CONTEXT_PATH + "/main/messageDetail";
    }
}
