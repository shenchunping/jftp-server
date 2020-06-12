package net.kuper.jftp.server.message;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class PageRes<T> {

    private int page;
    private int pageSize;
    private long count;
    private int pageCount;
    private int currentSize;
    private int startRow;
    private int endRow;
    private List<T> list;

    public PageRes() {
    }

    public PageRes(List<T> list) {
        if (list instanceof com.github.pagehelper.Page) {
            com.github.pagehelper.Page page = (com.github.pagehelper.Page) list;
            this.count = page.getTotal();
            this.page = page.getPageNum();
            this.pageSize = page.getPageSize();
            this.pageCount = page.getPages();
            this.currentSize = page.size();
            if (this.currentSize == 0) {
                this.startRow = 0;
                this.endRow = 0;
            } else {
                this.startRow = page.getStartRow() + 1;
                this.endRow = this.startRow - 1 + this.currentSize;
            }
            this.list = page.getResult();
        } else if (list instanceof Collection) {
            this.page = 1;
            this.pageSize = list.size();
            this.pageCount = this.pageSize > 0 ? 1 : 0;
            this.currentSize = list.size();
            this.startRow = 0;
            this.endRow = list.size() > 0 ? list.size() - 1 : 0;
            this.list = list;
        }
    }
}
