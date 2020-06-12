package net.kuper.jftp.server.dao;

import java.util.List;
import net.kuper.jftp.server.entity.FtpUser;

public interface FtpUserMapper {
    int deleteByPrimaryKey(String userid);

    int insert(FtpUser record);

    FtpUser selectByPrimaryKey(String userid);

    List<FtpUser> selectAll();

    int updateByPrimaryKey(FtpUser record);
}