package net.kuper.jftp.server.service;

import net.kuper.jftp.server.dto.UserPagination;
import net.kuper.jftp.server.entity.FtpUser;
import net.kuper.jftp.server.message.PageRes;

public interface UserService {

    PageRes<FtpUser> userPagination(UserPagination pagination);

    FtpUser getObject(String userId);

    void add(FtpUser user);

    void update(FtpUser user);

    String resetPwd(String userId);

    void delete(String userId, boolean delFiles);
}
