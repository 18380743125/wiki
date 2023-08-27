package com.tangl.wiki.controller;

import com.tangl.wiki.po.CategoryQueryPO;
import com.tangl.wiki.po.CategorySavePO;
import com.tangl.wiki.response.CommonResponse;
import com.tangl.wiki.service.CategoryService;
import com.tangl.wiki.vo.CategoryQueryVO;
import com.tangl.wiki.vo.PageVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author tangl
 * @description
 * @create 2023-08-27 8:58
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    @GetMapping("/list")
    public CommonResponse<PageVO<CategoryQueryVO>> list(@Valid CategoryQueryPO categoryQueryPO) {
        CommonResponse<PageVO<CategoryQueryVO>> commonResponse = new CommonResponse<>();
        PageVO<CategoryQueryVO> list = categoryService.list(categoryQueryPO);
        commonResponse.setData(list);
        return commonResponse;
    }

    @GetMapping("/all")
    public CommonResponse<List<CategoryQueryVO>> all() {
        CommonResponse<List<CategoryQueryVO>> commonResponse = new CommonResponse<>();
        List<CategoryQueryVO> list = categoryService.all();
        commonResponse.setData(list);
        return commonResponse;
    }

    @PostMapping("/save")
    public CommonResponse<?> save(CategorySavePO categorySavePO) {
        CommonResponse<?> commonResponse = new CommonResponse<>();
        categoryService.save(categorySavePO);
        return commonResponse;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResponse<?> delete(@PathVariable Long id) {
        CommonResponse<?> commonResponse = new CommonResponse<>();
        categoryService.delete(id);
        return commonResponse;
    }
}
