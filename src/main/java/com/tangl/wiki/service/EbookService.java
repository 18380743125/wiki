package com.tangl.wiki.service;

import com.tangl.wiki.po.EbookPO;
import com.tangl.wiki.vo.EbookVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tangl
 * @description
 * @create 2023-08-24 22:28
 */
@Service
public interface EbookService {

    List<EbookVO> list(EbookPO ebookPO);
}
