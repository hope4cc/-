package com.paopao.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SysLogger {

    private static final Logger logger = LoggerFactory.getLogger(SysLogger.class);

    private static String System_LOGGER_HEADER = "系统日志: ";

    public static void trace(String msg){
        logger.trace(System_LOGGER_HEADER + msg);
    }

    public static void debug(String msg){
        logger.debug(System_LOGGER_HEADER + msg);
    }

    public static void info(String msg){
        logger.info(System_LOGGER_HEADER + msg);
    }

    public static void warn(String msg){
        logger.warn(System_LOGGER_HEADER + msg);
    }

    public static void error(String msg){
        logger.error(System_LOGGER_HEADER + msg);
    }
}
