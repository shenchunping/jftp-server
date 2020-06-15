package net.kuper.jftp.server.service.impl;

import com.github.pagehelper.PageHelper;
import net.kuper.jftp.server.dao.FtpUserMapper;
import net.kuper.jftp.server.dto.UserPagination;
import net.kuper.jftp.server.entity.FtpAdmin;
import net.kuper.jftp.server.entity.FtpUser;
import net.kuper.jftp.server.exception.ResException;
import net.kuper.jftp.server.message.PageRes;
import net.kuper.jftp.server.service.UserService;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private FtpUserMapper ftpUserMapper;
    @Value("${ftp.home}")
    private String ftpHome;

    @Override
    public PageRes<FtpUser> userPagination(UserPagination pagination) {
        PageHelper.startPage(pagination.getPage(), pagination.getPageSize());
        List<FtpUser> list = ftpUserMapper.selectAll();
        return new PageRes<>(list);
    }

    @Override
    public FtpUser getObject(String userId) {
        return ftpUserMapper.selectByPrimaryKey(userId);
    }

    @Override
    public void add(FtpUser user) {
        FtpUser ftpUser = ftpUserMapper.selectByPrimaryKey(user.getUserid());
        if (ftpUser != null) {
            throw new ResException("账号已存在");
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String dir = user.getUserid() + "-" + dateFormat.format(new Date());
        user.setHomedirectory(dir);
        user.setEnableflag(true);
        user.setWritepermission(true);
        ftpUserMapper.insert(user);
    }

    @Override
    public void update(FtpUser user) {
        FtpUser ftpUser = ftpUserMapper.selectByPrimaryKey(user.getUserid());
        if (ftpUser == null) {
            throw new ResException("账号不存在");
        }
        ftpUser.setEnableflag(user.getEnableflag());
        ftpUserMapper.updateByPrimaryKey(ftpUser);
    }

    @Override
    public String resetPwd(String userId) {
        FtpUser ftpUser = ftpUserMapper.selectByPrimaryKey(userId);
        if (ftpUser == null) {
            throw new ResException("账号不存在");
        }
        String pwd = RandomStringUtils.random(8);
        ftpUser.setUserpassword(pwd);
        ftpUserMapper.updateByPrimaryKey(ftpUser);
        return pwd;
    }

    @Override
    public void delete(String userId, boolean delFiles) {
        FtpUser user = ftpUserMapper.selectByPrimaryKey(userId);
        if (user == null) {
            throw new ResException("账号不存在");
        }
        if (delFiles) {
            String path = ftpHome.endsWith("/") ? ftpHome : ftpHome + "/";
            File file = new File(path);
            boolean deleted = file.delete();
        }
        ftpUserMapper.deleteByPrimaryKey(userId);
    }
}
