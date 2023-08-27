package com.tangl.wiki.service;

import com.tangl.wiki.po.CategoryQueryPO;
import com.tangl.wiki.po.CategorySavePO;
import com.tangl.wiki.vo.CategoryQueryVO;
import com.tangl.wiki.vo.PageVO;

import java.util.List;

/**
 * @author tangl
 * @description
 * @create 2023-08-27 9:12
 */
public interface CategoryService {
    PageVO<CategoryQueryVO> list(CategoryQueryPO categoryQueryPO);

    void save(CategorySavePO categorySavePO);

    void delete(Long id);

    List<CategoryQueryVO> all();
}
