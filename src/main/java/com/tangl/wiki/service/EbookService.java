package com.tangl.wiki.service;

import com.tangl.wiki.po.EbookQueryPO;
import com.tangl.wiki.po.EbookSavePO;
import com.tangl.wiki.vo.EbookQueryVO;
import com.tangl.wiki.vo.PageVO;

/**
 * @author tangl
 * @description
 * @create 2023-08-24 22:28
 */
public interface EbookService {

    PageVO<EbookQueryVO> list(EbookQueryPO ebookPO);

    void save(EbookSavePO ebookSavePO);

    void delete(Long id);
}
