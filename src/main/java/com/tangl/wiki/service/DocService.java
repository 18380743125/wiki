package com.tangl.wiki.service;

import com.tangl.wiki.po.DocQueryPO;
import com.tangl.wiki.po.DocSavePO;
import com.tangl.wiki.vo.DocQueryVO;
import com.tangl.wiki.vo.PageVO;

import java.util.List;

/**
 * @author tangl
 * @description
 * @create 2023-08-27 10:14
 */
public interface DocService {
    PageVO<DocQueryVO> list(DocQueryPO docQueryPO);

    List<DocQueryVO> all(Long ebookId);

    void save(DocSavePO docSavePO);

    void delete(Long id);

    void delete(List<Long> ids);

    String findContent(Long id);
}
