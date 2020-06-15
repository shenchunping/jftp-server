package net.kuper.jftp.server.ftp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

@Slf4j
//@Component
public class StopListener implements ApplicationListener<ContextClosedEvent> {
    @Autowired
    private JFtpServer server;

    @Override
    public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
        server.stop();
        log.info("Apache Ftp server is stoped!");
    }
}
