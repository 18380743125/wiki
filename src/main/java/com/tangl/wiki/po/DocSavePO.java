package com.tangl.wiki.po;

import javax.validation.constraints.NotNull;

/**
 * @author tangl
 * @description
 * @create 2023-08-27 10:16
 */
public class DocSavePO {
    private Long id;

    @NotNull(message = "电子书ID不能为空")
    private Long ebookId;

    @NotNull(message = "父文档不能为空")
    private Long parent;

    @NotNull(message = "名称不能为空")
    private String name;

    @NotNull(message = "排序不能为空")
    private Integer sort;

    private Integer viewCount;

    private Integer voteCount;

    private Integer content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEbookId() {
        return ebookId;
    }

    public void setEbookId(Long ebookId) {
        this.ebookId = ebookId;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Integer getContent() {
        return content;
    }

    public void setContent(Integer content) {
        this.content = content;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DocSavePO{");
        sb.append("id=").append(id);
        sb.append(", ebookId=").append(ebookId);
        sb.append(", parent=").append(parent);
        sb.append(", name='").append(name).append('\'');
        sb.append(", sort=").append(sort);
        sb.append(", viewCount=").append(viewCount);
        sb.append(", voteCount=").append(voteCount);
        sb.append(", content=").append(content);
        sb.append('}');
        return sb.toString();
    }
}
