package net.kuper.jftp.server.service.impl;

import com.github.pagehelper.PageHelper;
import net.kuper.jftp.server.dao.FtpAdminMapper;
import net.kuper.jftp.server.dto.AdminPagination;
import net.kuper.jftp.server.dto.UpdatePwd;
import net.kuper.jftp.server.entity.FtpAdmin;
import net.kuper.jftp.server.exception.ResException;
import net.kuper.jftp.server.message.PageRes;
import net.kuper.jftp.server.service.AdminService;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private FtpAdminMapper ftpAdminMapper;

    @Override
    public FtpAdmin login(String account, String password) {
        FtpAdmin ftpAdmin = ftpAdminMapper.selectByAccount(account);
        if (ftpAdmin == null) {
            throw new ResException("账号或密码不正确");
        }
        String vPwd = new Sha256Hash(password, ftpAdmin.getSalt()).toHex();
        if (!ftpAdmin.getPassword().equals(vPwd)) {
            throw new ResException("账号或密码不正确");
        }
        if(!ftpAdmin.getEnable()){
            throw new ResException("账号已锁定，请联系其他管理员");
        }
        return ftpAdmin;
    }

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
        FtpAdmin ftpAdmin = ftpAdminMapper.selectByAccount(admin.getAccount());
        if (ftpAdmin != null) {
            throw new ResException("账号已存在");
        }

        String salt = RandomStringUtils.randomAlphanumeric(20);
        String pwd = new Sha256Hash(admin.getPassword(), salt).toHex();
        ftpAdmin = new FtpAdmin();
        ftpAdmin.setAccount(admin.getAccount());
        ftpAdmin.setPassword(pwd);
        ftpAdmin.setSalt(salt);
        ftpAdmin.setEnable(true);
        ftpAdminMapper.insert(ftpAdmin);
    }

    @Override
    public void update(FtpAdmin admin) {

    }

    @Override
    public void updatePwd(UpdatePwd updatePwd) {
        FtpAdmin admin = ftpAdminMapper.selectByPrimaryKey(updatePwd.getId());
        String vPwd = new Sha256Hash(updatePwd.getOldPwd(), admin.getSalt()).toHex();
        if (!admin.getPassword().equals(vPwd)) {
            throw new ResException("密码错误");
        }
        String salt = RandomStringUtils.randomAlphanumeric(20);
        String pwd = new Sha256Hash(updatePwd.getNewPwd(), salt).toHex();
        admin.setSalt(salt);
        admin.setPassword(pwd);
        admin.setUpdateTime(new Date());
        ftpAdminMapper.updateByPrimaryKey(admin);
    }

    @Override
    public String resetPwd(String adminId) {
        String newPwd = RandomStringUtils.randomAlphanumeric(8);
        String salt = RandomStringUtils.randomAlphanumeric(20);
        String pwd = new Sha256Hash(newPwd, salt).toHex();
        FtpAdmin admin = ftpAdminMapper.selectByPrimaryKey(adminId);
        admin.setSalt(salt);
        admin.setPassword(pwd);
        admin.setUpdateTime(new Date());
        ftpAdminMapper.updateByPrimaryKey(admin);
        return newPwd;
    }

    @Override
    public void delete(String adminId, String currId) {
        if (adminId.equals(currId)) {
            throw new ResException("别开玩笑啦，怎么可以删除自己呢");
        }
        ftpAdminMapper.deleteByPrimaryKey(adminId);
    }
}
