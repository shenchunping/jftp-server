package net.kuper.jftp.server.ftp;

import lombok.extern.slf4j.Slf4j;
import org.apache.ftpserver.DataConnectionConfigurationFactory;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.Ftplet;
import org.apache.ftpserver.listener.Listener;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.ClearTextPasswordEncryptor;
import org.apache.ftpserver.usermanager.DbUserManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JFtpServer {

    private static final String USER_ADMIN_SQL = "SELECT userid FROM FTP_USER WHERE userid='{userid}' AND userid='admin'";
    private static final String USER_INSERT_SQL = "INSERT INTO FTP_USER (userid, userpassword, homedirectory, enableflag, writepermission, idletime, uploadrate, downloadrate) VALUES ('{userid}', '{userpassword}', '{homedirectory}', {enableflag}, {writepermission}, {idletime}, uploadrate}, {downloadrate})";
    private static final String USER_UPDATE_SQL = "UPDATE FTP_USER SET userpassword='{userpassword}',homedirectory='{homedirectory}',enableflag={enableflag},writepermission={writepermission},idletime={idletime},uploadrate={uploadrate},downloadrate={downloadrate},maxloginnumber={maxloginnumber}, maxloginperip={maxloginperip} WHERE userid='{userid}'";
    private static final String USER_DELETE_SQL = "DELETE FROM FTP_USER WHERE userid = '{userid}'";
    private static final String USER_SELECT_SQL = "SELECT userid, userpassword, concat('%s',homedirectory) as homedirectory , enableflag, writepermission, idletime, uploadrate, downloadrate, maxloginnumber, maxloginperip FROM FTP_USER WHERE userid = '{userid}'";
    private static final String USER_SELECT_ALL_SQL = "SELECT userid FROM FTP_USER ORDER BY userid";
    private static final String USER_SELECT_AUTH = "SELECT userid, userpassword FROM FTP_USER WHERE userid='{userid}'";

    //springboot配置好数据源直接注入即可
    @Autowired
    private DataSource dataSource;
    @Value("${ftp.home}")
    private String home;
    @Value("${ftp.port}")
    private Integer port;
    @Value("${ftp.passive-ports}")
    private String passivePorts;


    protected FtpServer server;

    //我们这里利用spring加载@Configuration的特性来完成ftp server的初始化
    public JFtpServer(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * ftp server init
     *
     * @throws IOException
     */
    public synchronized void initFtp() {
        if (server != null) {
            return;
        }
        FtpServerFactory serverFactory = new FtpServerFactory();
        ListenerFactory listenerFactory = new ListenerFactory();
        //1、设置服务端口
        listenerFactory.setPort(port);
        //2、设置被动模式数据上传的接口范围,云服务器需要开放对应区间的端口给客户端
        DataConnectionConfigurationFactory dataConnectionConfFactory = new DataConnectionConfigurationFactory();
        dataConnectionConfFactory.setPassivePorts(passivePorts);
        listenerFactory.setDataConnectionConfiguration(dataConnectionConfFactory.createDataConnectionConfiguration());
        //3、增加SSL安全配置
//        SslConfigurationFactory ssl = new SslConfigurationFactory();
//        ssl.setKeystoreFile(new File("src/main/resources/ftpserver.jks"));
//        ssl.setKeystorePassword("password");
        //ssl.setSslProtocol("SSL");
        // set the SSL configuration for the listener
//        listenerFactory.setSslConfiguration(ssl.createSslConfiguration());
//        listenerFactory.setImplicitSsl(true);
        //4、替换默认的监听器
        Listener listener = listenerFactory.createListener();
        serverFactory.addListener("default", listener);
        //5、配置自定义用户事件
        Map<String, Ftplet> ftpLets = new HashMap();
        ftpLets.put("ftpService", new JFtpPlet());
        serverFactory.setFtplets(ftpLets);
        //6.2、基于数据库来存储用户实例
        String dir = home;
        if (!home.endsWith("/")) {
            dir += "/";
        }
        DbUserManagerFactory dbUserManagerFactory = new DbUserManagerFactory();
        //todo....
        dbUserManagerFactory.setDataSource(dataSource);
        dbUserManagerFactory.setAdminName("admin");
        dbUserManagerFactory.setSqlUserAdmin(JFtpServer.USER_ADMIN_SQL);
        dbUserManagerFactory.setSqlUserInsert(JFtpServer.USER_INSERT_SQL);
        dbUserManagerFactory.setSqlUserDelete(JFtpServer.USER_DELETE_SQL);
        dbUserManagerFactory.setSqlUserUpdate(JFtpServer.USER_UPDATE_SQL);
        dbUserManagerFactory.setSqlUserSelect(String.format(JFtpServer.USER_SELECT_SQL, dir));
        dbUserManagerFactory.setSqlUserSelectAll(JFtpServer.USER_SELECT_ALL_SQL);
        dbUserManagerFactory.setSqlUserAuthenticate(JFtpServer.USER_SELECT_AUTH);
        dbUserManagerFactory.setPasswordEncryptor(new ClearTextPasswordEncryptor());
        serverFactory.setUserManager(dbUserManagerFactory.createUserManager());
        //7、实例化FTP Server
        server = serverFactory.createServer();
        log.info("FTP initialized with port {}", port);
    }


    /**
     * ftp server start
     */
    public void start() {
        try {
            initFtp();
            server.start();
            log.info("Apache Ftp server is starting!");
        } catch (FtpException e) {
            e.printStackTrace();
        }
    }


    /**
     * ftp server stop
     */
    public void stop() {
        server.stop();
        log.info("Apache Ftp server is stopping!");
    }
}
