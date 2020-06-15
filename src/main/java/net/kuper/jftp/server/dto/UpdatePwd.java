package net.kuper.jftp.server.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePwd {
    private String id;
    private String oldPwd;
    private String newPwd;
}
