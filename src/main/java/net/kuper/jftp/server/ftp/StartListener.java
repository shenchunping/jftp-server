package net.kuper.jftp.server.ftp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Slf4j
//@Component
public class StartListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private JFtpServer server;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        try {
            //项目启动时已经加载好了
            server.start();
            log.info("Apache Ftp server is started!");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Apache Ftp server start failed!", e);
        }
    }
}
