package net.kuper.jftp.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan({"net.kuper.jftp.server.dao"})
@SpringBootApplication(scanBasePackages = {"net.kuper.jftp"})
public class JFtpServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(JFtpServerApplication.class, args);
    }
}
