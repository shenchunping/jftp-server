package net.kuper.jftp.server.service.impl;

import com.github.pagehelper.PageHelper;
import net.kuper.jftp.server.dao.FtpAdminMapper;
import net.kuper.jftp.server.dto.AdminPagination;
import net.kuper.jftp.server.entity.FtpAdmin;
import net.kuper.jftp.server.entity.FtpUser;
import net.kuper.jftp.server.message.PageRes;
import net.kuper.jftp.server.service.AdminService;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import sun.security.rsa.RSASignature;

import java.util.List;
import java.util.UUID;

public class AdminServiceImpl implements AdminService {

    @Autowired
    private FtpAdminMapper ftpAdminMapper;

    @Override
    public PageRes<FtpAdmin> adminPagination(AdminPagination pagination) {
        PageHelper.startPage(pagination.getPage(), pagination.getPageSize());
        List<FtpAdmin> list = ftpAdminMapper.selectAll();
        return new PageRes<>(list);
    }

    @Override
    public FtpAdmin getObject(String adminId) {
        return ftpAdminMapper.selectByPrimaryKey(adminId);
    }

    @Override
    public void add(FtpAdmin admin) {
        String salt = RandomStringUtils.randomAlphanumeric(20);
        String pwd = new Sha256Hash(admin.getPassword(), salt).toHex();
        FtpAdmin ftpAdmin = new FtpAdmin();
        ftpAdmin.setAccount(admin.getAccount());
        ftpAdmin.setPassword(pwd);
        ftpAdmin.setSalt(salt);
        ftpAdminMapper.insert(ftpAdmin);
    }

    @Override
    public void update(FtpAdmin admin) {

    }

    @Override
    public void updatePwd(FtpAdmin admin) {

    }

    @Override
    public String resetPwd(String adminId) {
        return null;
    }

    @Override
    public void delete(String adminId) {

    }
}
