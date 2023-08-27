package com.tangl.wiki.po;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/**
 * @author tangl
 * @description
 * @create 2023-08-26 21:25
 */
public class PagePO {
    @NotNull(message = "页码不能为空")
    private int page;

    @NotNull(message = "每页条数不能为空")
    @Max(value = 1000, message = "每页条数不能超过1000")
    private int size;

    public void setPage(int page) {
        this.page = page;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PagePO{");
        sb.append("page=").append(page);
        sb.append(", size=").append(size);
        sb.append('}');
        return sb.toString();
    }
}
