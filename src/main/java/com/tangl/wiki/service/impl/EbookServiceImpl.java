package com.tangl.wiki.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tangl.wiki.domain.Ebook;
import com.tangl.wiki.domain.EbookExample;
import com.tangl.wiki.mapper.EbookMapper;
import com.tangl.wiki.po.EbookQueryPO;
import com.tangl.wiki.po.EbookSavePO;
import com.tangl.wiki.service.EbookService;
import com.tangl.wiki.util.CopyUtil;
import com.tangl.wiki.util.SnowFlake;
import com.tangl.wiki.vo.EbookQueryVO;
import com.tangl.wiki.vo.PageVO;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;

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

    @Resource
    private SnowFlake snowFlake;

    @Override
    public PageVO<EbookQueryVO> list(EbookQueryPO ebookPO) {
        PageHelper.startPage(ebookPO.getPage(), ebookPO.getSize());
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        if (!ObjectUtils.isEmpty(ebookPO.getName())) {
            criteria.andNameLike("%" + ebookPO.getName() + "%");
        }
        if (!ObjectUtils.isEmpty(ebookPO.getCategoryId2())) {
            criteria.andCategory2IdEqualTo(ebookPO.getCategoryId2());
        }
        List<Ebook> ebooks = ebookMapper.selectByExample(ebookExample);
        PageInfo<Ebook> pageInfo = new PageInfo<>(ebooks);
        PageVO<EbookQueryVO> pageVO = new PageVO<>();
        pageVO.setList(CopyUtil.copyList(ebooks, EbookQueryVO.class));
        pageVO.setTotal(pageInfo.getTotal());
        return pageVO;
    }

    @Override
    public void save(@RequestBody EbookSavePO ebookSavePO) {
        Ebook ebook = CopyUtil.copy(ebookSavePO, Ebook.class);
        if (ObjectUtils.isEmpty(ebook.getId())) {
            // 新增
            ebook.setId(snowFlake.nextId());
            ebook.setDocCount(0);
            ebook.setViewCount(0);
            ebook.setVoteCount(0);
            ebookMapper.insert(ebook);
        } else {
            // 修改
            ebookMapper.updateByPrimaryKey(ebook);
        }
    }

    @Override
    public void delete(Long id) {
        ebookMapper.deleteByPrimaryKey(id);
    }
}
