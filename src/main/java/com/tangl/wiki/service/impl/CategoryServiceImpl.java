package com.tangl.wiki.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tangl.wiki.domain.Category;
import com.tangl.wiki.domain.CategoryExample;
import com.tangl.wiki.mapper.CategoryMapper;
import com.tangl.wiki.po.CategoryQueryPO;
import com.tangl.wiki.po.CategorySavePO;
import com.tangl.wiki.service.CategoryService;
import com.tangl.wiki.util.CopyUtil;
import com.tangl.wiki.util.SnowFlake;
import com.tangl.wiki.vo.CategoryQueryVO;
import com.tangl.wiki.vo.PageVO;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tangl
 * @description
 * @create 2023-08-27 9:12
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private SnowFlake snowFlake;

    @Override
    public PageVO<CategoryQueryVO> list(CategoryQueryPO categoryPO) {
        PageHelper.startPage(categoryPO.getPage(), categoryPO.getSize());
        CategoryExample categoryExample = new CategoryExample();
        List<Category> categories = categoryMapper.selectByExample(categoryExample);
        PageInfo<Category> pageInfo = new PageInfo<>(categories);
        PageVO<CategoryQueryVO> pageVO = new PageVO<>();
        pageVO.setList(CopyUtil.copyList(categories, CategoryQueryVO.class));
        pageVO.setTotal(pageInfo.getTotal());
        return pageVO;
    }

    @Override
    public List<CategoryQueryVO> all() {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("sort asc");
        List<Category> categories = categoryMapper.selectByExample(categoryExample);
        return CopyUtil.copyList(categories, CategoryQueryVO.class);
    }

    @Override
    public void save(@RequestBody CategorySavePO categorySavePO) {
        Category category = CopyUtil.copy(categorySavePO, Category.class);
        if (ObjectUtils.isEmpty(category.getId())) {
            // 新增
            category.setId(snowFlake.nextId());
            categoryMapper.insert(category);
        } else {
            // 修改
            categoryMapper.updateByPrimaryKey(category);
        }
    }

    @Override
    public void delete(Long id) {
        categoryMapper.deleteByPrimaryKey(id);
    }
}
