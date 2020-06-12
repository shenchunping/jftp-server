package net.kuper.jftp.server.message;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by shenchunping on 2018/6/22.
 */
@Getter
@Setter
public class Pagination implements Serializable {

    private int page = 1;
    private int pageSize = 10;
}
