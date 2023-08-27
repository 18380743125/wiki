package com.tangl.wiki.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tangl.wiki.domain.Content;
import com.tangl.wiki.domain.Doc;
import com.tangl.wiki.domain.DocExample;
import com.tangl.wiki.mapper.ContentMapper;
import com.tangl.wiki.mapper.DocMapper;
import com.tangl.wiki.po.DocQueryPO;
import com.tangl.wiki.po.DocSavePO;
import com.tangl.wiki.service.DocService;
import com.tangl.wiki.util.CopyUtil;
import com.tangl.wiki.util.SnowFlake;
import com.tangl.wiki.vo.DocQueryVO;
import com.tangl.wiki.vo.PageVO;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tangl
 * @description
 * @create 2023-08-27 10:14
 */
@Service
public class DocServiceImpl implements DocService {
    @Resource
    private DocMapper docMapper;

    @Resource
    private ContentMapper contentMapper;

    @Resource
    private SnowFlake snowFlake;

    @Override
    public PageVO<DocQueryVO> list(DocQueryPO docPO) {
        PageHelper.startPage(docPO.getPage(), docPO.getSize());
        DocExample docExample = new DocExample();
        List<Doc> categories = docMapper.selectByExample(docExample);
        PageInfo<Doc> pageInfo = new PageInfo<>(categories);
        PageVO<DocQueryVO> pageVO = new PageVO<>();
        pageVO.setList(CopyUtil.copyList(categories, DocQueryVO.class));
        pageVO.setTotal(pageInfo.getTotal());
        return pageVO;
    }

    @Override
    public List<DocQueryVO> all(Long ebookId) {
        DocExample docExample = new DocExample();
        docExample.createCriteria().andEbookIdEqualTo(ebookId);
        docExample.setOrderByClause("sort asc");
        List<Doc> categories = docMapper.selectByExample(docExample);
        return CopyUtil.copyList(categories, DocQueryVO.class);
    }

    @Override
    public void save(@RequestBody DocSavePO docSavePO) {
        Doc doc = CopyUtil.copy(docSavePO, Doc.class);
        Content content = CopyUtil.copy(docSavePO, Content.class);
        if (ObjectUtils.isEmpty(doc.getId())) {
            // 新增
            doc.setId(snowFlake.nextId());
            docMapper.insert(doc);

            content.setId(doc.getId());
            contentMapper.insert(content);
        } else {
            // 修改
            docMapper.updateByPrimaryKey(doc);
            int count = contentMapper.updateByPrimaryKeyWithBLOBs(content);
            if (count == 0) {
                contentMapper.insert(content);
            }
        }
    }

    @Override
    public void delete(Long id) {
        docMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void delete(List<Long> ids) {
        DocExample docExample = new DocExample();
        DocExample.Criteria criteria = docExample.createCriteria();
        criteria.andIdIn(ids);
        docMapper.deleteByExample(docExample);
    }

    @Override
    public String findContent(Long id) {
        Content content = contentMapper.selectByPrimaryKey(id);
        if (ObjectUtils.isEmpty(content.getContent())) {
            return "";
        }
        return content.getContent();
    }

}
