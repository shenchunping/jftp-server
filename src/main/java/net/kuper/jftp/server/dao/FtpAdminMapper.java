package net.kuper.jftp.server.dao;

import java.util.List;
import net.kuper.jftp.server.entity.FtpAdmin;

public interface FtpAdminMapper {
    int deleteByPrimaryKey(String id);

    int insert(FtpAdmin record);

    FtpAdmin selectByPrimaryKey(String id);

    List<FtpAdmin> selectAll();

    int updateByPrimaryKey(FtpAdmin record);
}