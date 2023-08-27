package com.tangl.wiki.vo;

import java.util.List;

/**
 * @author tangl
 * @description
 * @create 2023-08-26 21:35
 */
public class PageVO<T> {
    private Long total;

    private List<T> list;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PageVO{");
        sb.append("total=").append(total);
        sb.append(", list=").append(list);
        sb.append('}');
        return sb.toString();
    }
}
