package com.tangl.wiki.service.impl;

import com.tangl.wiki.domain.Ebook;
import com.tangl.wiki.domain.EbookExample;
import com.tangl.wiki.mapper.EbookMapper;
import com.tangl.wiki.po.EbookPO;
import com.tangl.wiki.service.EbookService;
import com.tangl.wiki.util.CopyUtil;
import com.tangl.wiki.vo.EbookVO;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tangl
 * @description
 * @create 2023-08-24 22:31
 */
@Service
public class EbookServiceImpl implements EbookService {

    @Resource
    private EbookMapper ebookMapper;

    @Override
    public List<EbookVO> list(EbookPO ebookPO) {
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        if (!ObjectUtils.isEmpty(ebookPO.getName())) {
            criteria.andNameLike("%" + ebookPO.getName() + "%");
        }
        List<Ebook> ebooks = ebookMapper.selectByExample(ebookExample);
        return CopyUtil.copyList(ebooks, EbookVO.class);
    }
}
