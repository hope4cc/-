package com.paopao.scheduled;


import com.paopao.logger.SysLogger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SystemScheduled {
    /**
     * 每天11点进行系统清理
     */
    @Scheduled(cron = "0 0 11 * * ?")
    public void systemClean(){
        SysLogger.info("系统清理");

    }
}
