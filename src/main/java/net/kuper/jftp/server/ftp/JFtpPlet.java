package net.kuper.jftp.server.ftp;

import lombok.extern.slf4j.Slf4j;
import org.apache.ftpserver.ftplet.*;

import java.io.IOException;

@Slf4j
public class JFtpPlet extends DefaultFtplet {

    @Override
    public FtpletResult onLogin(FtpSession session, FtpRequest request) throws FtpException, IOException {
        String name = session.getUser().getName();
        log.info("user:'{}', status: 'login success'", name);
        return super.onLogin(session, request);
    }

    @Override
    public FtpletResult onDisconnect(FtpSession session) throws FtpException, IOException {
        if (session.getUser() != null) {
            String name = session.getUser().getName();
            log.info("user:'{}', status: 'disconnected'", name);
        }
        return super.onDisconnect(session);
    }

    @Override
    public FtpletResult onUploadStart(FtpSession session, FtpRequest request)
            throws FtpException, IOException {
        //获取上传文件的上传路径
        String path = session.getUser().getHomeDirectory();
        //获取上传user
        String name = session.getUser().getName();
        //获取上传文件名
        String filename = request.getArgument();
//        request.getRequestLine()
        log.info("user:'{}', homeDir：'{}', fileName：'{}', status: 'upload starting'", name, path, filename);
        return super.onUploadStart(session, request);
    }


    @Override
    public FtpletResult onUploadEnd(FtpSession session, FtpRequest request)
            throws FtpException, IOException {
        String path = session.getUser().getHomeDirectory();
        String name = session.getUser().getName();
        String filename = request.getArgument();
        log.info("user:'{}', homeDir：'{}', fileName：'{}', status：'upload success'", name, path, filename);
        return super.onUploadEnd(session, request);
    }

    @Override
    public FtpletResult onDownloadStart(FtpSession session, FtpRequest request) throws FtpException, IOException {
        String path = session.getUser().getHomeDirectory();
        String name = session.getUser().getName();
        String filename = request.getArgument();
        log.info("user:'{}', homeDir：'{}', fileName：'{}', status：'download starting'", name, path, filename);
        return super.onDownloadStart(session, request);
    }

    @Override
    public FtpletResult onDownloadEnd(FtpSession session, FtpRequest request) throws FtpException, IOException {
        String path = session.getUser().getHomeDirectory();
        String name = session.getUser().getName();
        String filename = request.getArgument();
        log.info("user:'{}', homeDir：'{}', fileName：'{}', status：'download success'", name, path, filename);
        return super.onDownloadEnd(session, request);
    }

    @Override
    public FtpletResult onDeleteEnd(FtpSession session, FtpRequest request) throws FtpException, IOException {
        String path = session.getUser().getHomeDirectory();
        String name = session.getUser().getName();
        String filename = request.getArgument();
        log.info("user:'{}', homeDir：'{}', fileName：'{}', status：'delete starting'", name, path, filename);
        return super.onDeleteEnd(session, request);
    }

    @Override
    public FtpletResult onDeleteStart(FtpSession session, FtpRequest request) throws FtpException, IOException {
        String path = session.getUser().getHomeDirectory();
        String name = session.getUser().getName();
        String filename = request.getArgument();
        log.info("user:'{}', homeDir：'{}', fileName：'{}', status: 'delete success'", name, path, filename);
        return super.onDeleteStart(session, request);
    }

    @Override
    public FtpletResult onMkdirStart(FtpSession session, FtpRequest request) throws FtpException, IOException {
        String path = session.getUser().getHomeDirectory();
        String name = session.getUser().getName();
        String filename = request.getArgument();
        log.info("user:'{}', homeDir：'{}', fileName：'{}', status：'make dir starting '", name, path, filename);
        return super.onMkdirStart(session, request);
    }

    @Override
    public FtpletResult onMkdirEnd(FtpSession session, FtpRequest request) throws FtpException, IOException {
        String path = session.getUser().getHomeDirectory();
        String name = session.getUser().getName();
        String filename = request.getArgument();
        log.info("user:'{}', homeDir：'{}', fileName：'{}', status：'make dir success'", name, path, filename);
        return super.onMkdirEnd(session, request);
    }

    @Override
    public FtpletResult onRenameStart(FtpSession session, FtpRequest request) throws FtpException, IOException {
        String path = session.getUser().getHomeDirectory();
        String name = session.getUser().getName();
        String filename = request.getArgument();
        log.info("user:'{}', homeDir：'{}', fileName：'{}', status：'rename starting'", name, path, filename);
        return super.onRenameStart(session, request);
    }

    @Override
    public FtpletResult onRenameEnd(FtpSession session, FtpRequest request) throws FtpException, IOException {
        String path = session.getUser().getHomeDirectory();
        String name = session.getUser().getName();
        String filename = request.getArgument();
        log.info("user:'{}', homeDir：'{}', fileName：'{}', status：'rename success'", name, path, filename);
        return super.onRenameEnd(session, request);
    }
}
