package net.kuper.jftp.server.service;

import net.kuper.jftp.server.dto.AdminPagination;
import net.kuper.jftp.server.dto.UserPagination;
import net.kuper.jftp.server.entity.FtpAdmin;
import net.kuper.jftp.server.entity.FtpUser;
import net.kuper.jftp.server.message.PageRes;

public interface AdminService {

    PageRes<FtpAdmin> adminPagination(AdminPagination pagination);

    FtpAdmin getObject(String adminId);

    void add(FtpAdmin admin);

    void update(FtpAdmin admin);

    void updatePwd(FtpAdmin admin);

    String resetPwd(String adminId);

    void delete(String adminId);
}
